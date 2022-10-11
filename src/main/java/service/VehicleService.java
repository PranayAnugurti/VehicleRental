package service;

import enums.VehicleType;
import model.Vehicle;
import repository.VehicleRepository;
import strategy.VehicleSelectionStrategy;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    public VehicleRepository vehicleRepository;
    public BranchService branchService;
    public VehicleSelectionStrategy vehicleSelectionStrategy;

    public VehicleService(VehicleRepository vehicleRepository, BranchService branchService, VehicleSelectionStrategy vehicleSelectionStrategy) {
        this.vehicleRepository = vehicleRepository;
        this.branchService = branchService;
        this.vehicleSelectionStrategy = vehicleSelectionStrategy;
    }

    public boolean onboardVehicle(String branchName, VehicleType vehicleType, String vehicleId, double price) {
        boolean isValid =branchService.addVehicle(branchName, vehicleType, vehicleId);
        if(isValid) {
            Vehicle vehicle = new Vehicle(vehicleId, branchName, vehicleType, price);
            vehicleRepository.addVehicle(vehicle);
        }
        return isValid;
    }

    public List<Vehicle> getAllAvailableVehicles(String branchName, int startTime, int endTime) {
        List<String> vehicleIds = branchService.getAllVehicles(branchName);
        return vehicleSelectionStrategy.availableVehicles(filterAvailableVehicles(vehicleIds, startTime, endTime), branchName, startTime, endTime);
    }

    public List<String>getAllVehiclesOfBranch(String branchName){
        return branchService.getAllVehicles(branchName);
    }

    private List<Vehicle> getAvailableVehiclesOfType(String branchName, VehicleType vehicleType, int startTime, int endTime) {
        List<String> vehicleIds = branchService.getVehicles(branchName, vehicleType);
        return filterAvailableVehicles(vehicleIds, startTime, endTime);
    }

    private List<Vehicle> filterAvailableVehicles(List<String> vehicleIds, int startTime, int endTime) {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (String vehicleId : vehicleIds) {
            Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
            if (checkVehicleAvailability(vehicle, startTime, endTime))
                availableVehicles.add(vehicle);
        }
        return availableVehicles;
    }

    private boolean checkVehicleAvailability(Vehicle vehicle, int startTime, int endTime) {
        int slot = startTime;
        while (slot < endTime) {
            if (vehicle.getBookedSlots().contains(slot))
                return false;
            slot++;
        }
        return true;

    }

    public Vehicle getAvailableVehicle(String branchName, VehicleType vehicleType, int startTime, int endTime) {
        Vehicle vehicle = vehicleSelectionStrategy.selectVehicle(getAvailableVehiclesOfType(branchName, vehicleType, startTime, endTime), branchName, vehicleType, startTime, endTime);
        return vehicle;
    }

    public void blockSlots(String vehicleId, int startTime, int endTime) {
        Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);
        int slot = startTime;
        while (slot < endTime) {
            vehicle.blockSlot(slot);
            slot++;
        }
    }


}
