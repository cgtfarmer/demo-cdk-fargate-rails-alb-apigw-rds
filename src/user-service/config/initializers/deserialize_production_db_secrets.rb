require 'json'

Rails.application.config.before_configuration do
  unless Rails.env.production? return;

  db_secret_string = ENV['DB_SECRET']

  db_secret = JSON.parse(db_secret_string)
  ENV['DB_USERNAME'] = db_secret['username']
  ENV['DB_PASSWORD'] = db_secret['password']
end
