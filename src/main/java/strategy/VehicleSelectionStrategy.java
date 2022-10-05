package strategy;

import enums.VehicleType;
import model.Vehicle;

import java.util.List;

public interface VehicleSelectionStrategy {
    public Vehicle selectVehicle(List<Vehicle> vehicles,String branchName, VehicleType vehicleType, int startTime, int endTime);
    public List<Vehicle> availableVehicles(List<Vehicle> vehicles,String branchName, int startTime, int endTime);
}
