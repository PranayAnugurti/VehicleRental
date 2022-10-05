package strategy;

import model.Vehicle;

public interface PricingStrategy {
    double calculateFare(Vehicle vehicle, int startTime, int endTime);
}
