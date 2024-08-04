class HealthController < ApplicationController

  def index
    render json: { message: 'Healthy' }, status: 200
  end
end
