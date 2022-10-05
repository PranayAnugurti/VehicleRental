package strategy.implementation;

import model.Vehicle;
import strategy.PricingStrategy;

public class FlatPricingStrategy implements PricingStrategy {


    @Override
    public double calculateFare(Vehicle vehicle, int startTime, int endTime) {
        return (endTime - startTime) * vehicle.getPricePerHour();
    }
}
