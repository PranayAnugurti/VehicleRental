package service;

import enums.VehicleType;
import exception.NoVehicleAvailableException;
import model.Booking;
import model.Vehicle;
import repository.BookingRepository;
import strategy.PricingStrategy;
import strategy.VehicleSelectionStrategy;

import java.util.UUID;

public class BookingService {
    public BookingRepository bookingRepository;
    public VehicleService vehicleService;
    public PricingStrategy pricingStrategy;

    public BookingService(BookingRepository bookingRepository, VehicleService vehicleService, PricingStrategy pricingStrategy) {
        this.bookingRepository = bookingRepository;
        this.vehicleService = vehicleService;
        this.pricingStrategy = pricingStrategy;
    }

    public double createBooking(String branchName, VehicleType vehicleType, int startTime, int endTime) {
        try {
            Vehicle vehicle = vehicleService.getAvailableVehicle(branchName, vehicleType, startTime, endTime);
            Booking booking = new Booking(UUID.randomUUID().toString(), vehicle.getId(), startTime, endTime, pricingStrategy.calculateFare(vehicle,startTime, endTime));
            bookingRepository.addBooking(booking);
            vehicleService.blockSlots(vehicle.getId(), startTime, endTime);
            return booking.getPrice();
        } catch (NoVehicleAvailableException e) {
            return -1;
        }
    }

}
