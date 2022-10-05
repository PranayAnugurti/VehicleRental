package model;

import enums.VehicleType;

import java.util.List;
import java.util.Map;

public class Branch {
//    private String id;
//    private String cityId;
    private String name;
    private Map<VehicleType,List<String>> vehicles;

    public Branch(String name, Map<VehicleType,List<String>> vehicles) {
        this.name = name;
        this.vehicles = vehicles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<VehicleType, List<String>> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<VehicleType, List<String>> vehicles) {
        this.vehicles = vehicles;
    }
}
