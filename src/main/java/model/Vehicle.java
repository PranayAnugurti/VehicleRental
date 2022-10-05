package model;

import enums.VehicleType;

import java.util.HashSet;

public class Vehicle implements Comparable<Vehicle> {
    private String id; //we can store registration_id
    private String branchName;
    private VehicleType type;
    private double pricePerHour;
    private HashSet<Integer> bookedSlots;

    public Vehicle(String id, String branchName, VehicleType type, double pricePerHour) {
        this.id = id;
        this.branchName = branchName;
        this.type = type;
        this.pricePerHour = pricePerHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public HashSet<Integer> getBookedSlots() {
        return bookedSlots;
    }

    public void blockSlot(int slotHour){
        this.bookedSlots.add(slotHour);
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setBookedSlots(HashSet<Integer> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    @Override
    public int compareTo(Vehicle v) {
        return Double.compare(this.getPricePerHour(), v.getPricePerHour());
    }
}
