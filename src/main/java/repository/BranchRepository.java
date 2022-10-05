package repository;

import enums.VehicleType;
import model.Branch;

import java.util.HashMap;
import java.util.List;

public class BranchRepository {
    private HashMap<String, Branch> branchHashMap = new HashMap<>();

    public Branch getBranch(String name){
        return branchHashMap.get(name);
    }

    public void addBranch(Branch branch){
        branchHashMap.put(branch.getName(),branch);
    }

    public List<String> getVehiclesOfType(String name, VehicleType vehicleType){
        return branchHashMap.get(name).getVehicles().get(vehicleType);
    }

}
