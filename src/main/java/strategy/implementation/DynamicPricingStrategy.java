package strategy.implementation;

import model.Vehicle;
import service.VehicleService;
import strategy.PricingStrategy;

import java.util.List;

public class DynamicPricingStrategy implements PricingStrategy {
    VehicleService vehicleService;

    public DynamicPricingStrategy(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public double calculateFare(Vehicle vehicle, int startTime, int endTime) {
        List<String> allVehiclesOfBranch = vehicleService.getAllVehiclesOfBranch(vehicle.getBranchName());
        List<Vehicle> allAvailableVehiclesOfBranch = vehicleService.getAllAvailableVehicles(vehicle.getBranchName(), startTime, endTime);
        if (isDynamicPriceApplicable(allVehiclesOfBranch.size(), allAvailableVehiclesOfBranch.size())) {
            return (endTime - startTime) * vehicle.getPricePerHour() * (1.10);
        }
        return (endTime - startTime) * vehicle.getPricePerHour();
    }

    private boolean isDynamicPriceApplicable(int allVehiclesCount, int availableVehiclesCount) {
        return availableVehiclesCount <= Math.round(allVehiclesCount * 0.2);
    }
}
