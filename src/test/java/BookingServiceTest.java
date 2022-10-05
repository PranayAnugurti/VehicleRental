import enums.VehicleType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.BookingRepository;
import repository.VehicleRepository;
import service.BookingService;
import service.BranchService;
import service.VehicleService;
import strategy.implementation.DefaultVehicleSelectionStrategy;
import strategy.implementation.FlatPricingStrategy;

import java.util.Arrays;

public class BookingServiceTest {

    static BookingRepository bookingRepository = new BookingRepository();
    static VehicleRepository vehicleRepository = new VehicleRepository();
    static BranchService branchService = new BranchService();
    static VehicleService vehicleService = new VehicleService(vehicleRepository, branchService, new DefaultVehicleSelectionStrategy());
    static BookingService bookingService = new BookingService(bookingRepository, vehicleService,new FlatPricingStrategy());

    @BeforeClass
    public static void init(){
        branchService.onboardBranch("B1", Arrays.asList(VehicleType.CAR, VehicleType.BIKE, VehicleType.VAN));

        vehicleService.onboardVehicle("B1", VehicleType.CAR, "V2", 1000);
        vehicleService.onboardVehicle("B1", VehicleType.BIKE, "V3", 250);
        vehicleService.onboardVehicle("B1", VehicleType.BIKE, "V4", 300);
        vehicleService.onboardVehicle("B1", VehicleType.BUS, "V5", 2500);
    }

    @Test
    public void testUnavailableVehicleBooking(){
        Assert.assertEquals(-1.0, bookingService.createBooking("B1", VehicleType.VAN, 1, 5), 0.0);
    }

    @Test
    public void testAvailableVehicleBooking(){
        Assert.assertEquals(2000.0,bookingService.createBooking("B1", VehicleType.CAR, 1, 3),0.0);
    }

}
