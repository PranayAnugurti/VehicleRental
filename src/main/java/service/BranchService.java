package service;

import enums.VehicleType;
import model.Branch;
import repository.BranchRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchService {
    public BranchRepository branchRepository = new BranchRepository();

    public boolean onboardBranch(String name, List<VehicleType> vehicleTypes) {
        try {
            Map<VehicleType, List<String>> vehicles = new HashMap<>();
            for (VehicleType vehicleType : vehicleTypes) {
                vehicles.put(vehicleType, new ArrayList<>());
            }
            Branch branch = new Branch(name, vehicles);
            branchRepository.addBranch(branch);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addVehicle(String branchName, VehicleType vehicleType, String vehicleId) {
        try {
            branchRepository.getBranch(branchName).getVehicles().get(vehicleType).add(vehicleId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAllVehicles(String name) {
        Branch branch = branchRepository.getBranch(name);
        Map<VehicleType, List<String>> vehicleTypeListMap = branch.getVehicles();

        List<String> vehicleIds = new ArrayList<>();
        for (Map.Entry<VehicleType, List<String>> vehicleEntry : vehicleTypeListMap.entrySet()) {
            vehicleIds.addAll(vehicleEntry.getValue());
        }
        return vehicleIds;
    }

    public List<String> getVehicles(String name, VehicleType vehicleType) {
        return branchRepository.getVehiclesOfType(name, vehicleType);
    }
}
