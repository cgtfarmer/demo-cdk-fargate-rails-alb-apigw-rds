# Make sure RUBY_VERSION matches the Ruby version in .ruby-version and Gemfile
FROM ruby:3.3.4 AS user-service-development

ENV LANG=C.UTF-8 \
  APP_HOME=/usr/src/app \
  BUNDLE_PATH=/usr/local/bundle

WORKDIR $APP_HOME

CMD bin/rails server -b 0.0.0.0


FROM user-service-development AS user-service

ENV RAILS_ENV="production" \
    BUNDLE_DEPLOYMENT="1" \
    BUNDLE_PATH="/usr/local/bundle" \
    BUNDLE_WITHOUT="development"

RUN apt-get update -qq && \
    apt-get install --no-install-recommends -y build-essential git libpq-dev libvips pkg-config curl postgresql-client

COPY Gemfile Gemfile.lock ./

RUN bundle install && \
    bundle exec bootsnap precompile --gemfile

COPY . .

# Precompile bootsnap code for faster boot times
RUN bundle exec bootsnap precompile app/ lib/

CMD ./bin/rails server
