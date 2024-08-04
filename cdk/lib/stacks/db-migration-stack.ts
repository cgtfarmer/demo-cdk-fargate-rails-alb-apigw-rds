import { homedir } from 'os';
import { join } from 'path';
import { BundlingOutput, Duration, Stack, StackProps } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { DatabaseProxy } from 'aws-cdk-lib/aws-rds';
import { Port, Vpc } from 'aws-cdk-lib/aws-ec2';
import { ISecret } from 'aws-cdk-lib/aws-secretsmanager';
import { TriggerFunction } from 'aws-cdk-lib/triggers';
import { Code, ParamsAndSecretsLayerVersion, ParamsAndSecretsLogLevel, ParamsAndSecretsVersions, Runtime } from 'aws-cdk-lib/aws-lambda';

interface DbMigrationStackProps extends StackProps {
  vpc: Vpc;

  rdsProxy: DatabaseProxy;

  rdsSecret: ISecret;

  rdsPort: string;

  rdsDbName: string;
}

export class DbMigrationStack extends Stack {

  constructor(scope: Construct, id: string, props: DbMigrationStackProps) {
    super(scope, id, props);

    const paramsAndSecrets = ParamsAndSecretsLayerVersion.fromVersion(
      ParamsAndSecretsVersions.V1_0_103,
      {
        cacheSize: 500,
        logLevel: ParamsAndSecretsLogLevel.DEBUG,
      }
    );

    const dbMigrationLambda = new TriggerFunction(this, 'DbMigrationLambda', {
      vpc: props.vpc,
      runtime: Runtime.JAVA_17,
      handler: 'com.cgtfarmer.demo.Handler',
      code: Code.fromAsset(join(__dirname, '../../../src/db-migration-service'), {
        bundling: {
          image: Runtime.JAVA_17.bundlingImage,
          user: 'root',
          command: [
            '/bin/sh',
            '-c',
           'mvn clean install && cp /asset-input/target/demo-cdk-lambda-java-0.0.1.jar /asset-output/'
          ],
          // Mounting local ~/.m2 repo to avoid re-downloading all the dependencies
          volumes: [
            {
              hostPath: join(homedir(), '.m2/repository'),
              containerPath: '/root/.m2/repository/'
            }
          ],
          outputType: BundlingOutput.ARCHIVED
        }
      }),
      environment: {
        DB_JDBC_URL: `jdbc:postgresql://${props.rdsProxy.endpoint}:${props.rdsPort}/${props.rdsDbName}`,
        DB_CREDS_SECRET_ID: props.rdsSecret.secretName,
        DB_CHANGELOG_FILE: 'db/db.changelog-root.yaml',
      },
      memorySize: 1024,
      timeout: Duration.seconds(30),
      paramsAndSecrets: paramsAndSecrets,
    });

    props.rdsSecret.grantRead(dbMigrationLambda);
    props.rdsProxy.grantConnect(dbMigrationLambda);
    // This direction is incorrect due to causing cyclic dependencies
    // props.rdsProxy.connections.allowFrom(dbMigrationLambda, Port.POSTGRES);
    dbMigrationLambda.connections.allowTo(props.rdsProxy, Port.POSTGRES);
  }
}
