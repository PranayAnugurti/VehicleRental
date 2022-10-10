package strategy.implementation;

import enums.VehicleType;
import exception.NoVehicleAvailableException;
import model.Vehicle;
import strategy.VehicleSelectionStrategy;

import java.util.Collections;
import java.util.List;

public class DefaultVehicleSelectionStrategy implements VehicleSelectionStrategy {

    @Override
    public Vehicle selectVehicle(List<Vehicle> vehicles, String branchName, VehicleType vehicleType, int startTime, int endTime) throws NoVehicleAvailableException {
        Collections.sort(vehicles);
        if (vehicles.size() > 0)
            return vehicles.get(0);
        throw new NoVehicleAvailableException("No Vehicle Available for requested time slots!!");
    }

    @Override
    public List<Vehicle> availableVehicles(List<Vehicle> vehicles, String branchName, int startTime, int endTime) {
        Collections.sort(vehicles);
        return vehicles;
    }
}
