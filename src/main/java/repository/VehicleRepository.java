package repository;

import model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class VehicleRepository {
    private Map<String, Vehicle> vehicleMap = new HashMap<>();
    private Map<String, List<String>>vehicleBranchMap = new HashMap<>();

    public Vehicle getVehicle(String id){
        return vehicleMap.get(id);
    }

    public void addVehicle(Vehicle vehicle){
        vehicle.setBookedSlots(new HashSet<>());
        vehicleMap.put(vehicle.getId(),vehicle);
        List<String>vehicles = vehicleBranchMap.getOrDefault(vehicle.getBranchName(),new ArrayList<>());
        vehicles.add(vehicle.getId());
        vehicleBranchMap.put(vehicle.getBranchName(),vehicles);
    }



}
