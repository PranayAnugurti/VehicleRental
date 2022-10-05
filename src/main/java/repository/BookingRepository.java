package repository;

import model.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingRepository {
    Map<String, Booking>bookingMap = new HashMap<>();

    public void addBooking(Booking booking){
        bookingMap.put(booking.getId(),booking);
    }

    public Booking getBooking(String id){
        return bookingMap.get(id);
    }
}
