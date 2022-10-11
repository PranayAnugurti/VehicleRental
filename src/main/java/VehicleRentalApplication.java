import enums.VehicleType;
import model.Command;
import model.Vehicle;
import repository.BookingRepository;
import repository.VehicleRepository;
import service.BookingService;
import service.BranchService;
import service.VehicleService;
import strategy.implementation.DefaultVehicleSelectionStrategy;
import strategy.implementation.FlatPricingStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VehicleRentalApplication {
    static BookingRepository bookingRepository = new BookingRepository();
    static VehicleRepository vehicleRepository = new VehicleRepository();
    static BranchService branchService = new BranchService();
    static VehicleService vehicleService = new VehicleService(vehicleRepository, branchService, new DefaultVehicleSelectionStrategy());

    static BookingService bookingService = new BookingService(bookingRepository, vehicleService, new FlatPricingStrategy());
    //Dynamic Price Booking Service
//    static BookingService bookingService = new BookingService(bookingRepository, vehicleService,new DynamicPricingStrategy(vehicleService));


    public static void main(String[] args) throws IOException, URISyntaxException {
        //Execute for given set of commands
//        executeDefaultCommands();

        //Execute from input text file
        String path = "input.txt";
//        executeFromInputFile(path);

        executeFromCommandLine();

    }


    static void executeFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input != null) {
            Command command = new Command(input);
            try {
                processCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
            input = scanner.nextLine();
        }
    }


    static void executeFromInputFile(String path) throws IOException, URISyntaxException {
        File file = new File(String.valueOf(Paths.get("src/main/resources/", path)));

        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
            return;
        }

        String input = reader.readLine();
        while (input != null) {
            Command command = new Command(input);
            try {
                processCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
            input = reader.readLine();
        }
    }

    static void executeDefaultCommands() {
        System.out.println(branchService.onboardBranch("B1", Arrays.asList(VehicleType.CAR, VehicleType.BIKE, VehicleType.VAN)) ? "TRUE" : "FALSE");
        System.out.println(vehicleService.onboardVehicle("B1", VehicleType.CAR, "V1", 500) ? "TRUE" : "FALSE");
        System.out.println(vehicleService.onboardVehicle("B1", VehicleType.CAR, "V2", 1000) ? "TRUE" : "FALSE");
        System.out.println(vehicleService.onboardVehicle("B1", VehicleType.BIKE, "V3", 250) ? "TRUE" : "FALSE");
        System.out.println(vehicleService.onboardVehicle("B1", VehicleType.BIKE, "V4", 300) ? "TRUE" : "FALSE");
        System.out.println(vehicleService.onboardVehicle("B1", VehicleType.BUS, "V5", 2500) ? "TRUE" : "FALSE");

        double booking1Price = bookingService.createBooking("B1", VehicleType.VAN, 1, 5);
        double booking2Price = bookingService.createBooking("B1", VehicleType.CAR, 1, 3);
        double booking3Price = bookingService.createBooking("B1", VehicleType.BIKE, 2, 3);
        double booking4Price = bookingService.createBooking("B1", VehicleType.BIKE, 2, 5);

        System.out.println((int) booking1Price);
        System.out.println((int) booking2Price);
        System.out.println((int) booking3Price);
        System.out.println((int) booking4Price);

        List<Vehicle> vehicles = vehicleService.getAllAvailableVehicles("B1", 1, 5);
        for (Vehicle vehicle : vehicles)
            System.out.print(vehicle.getId() + " ");
    }

    static void processCommand(Command command) {
        List<String> params = command.getParams();
        switch (command.getCommandName()) {
            case "ADD_BRANCH":
                List<VehicleType> vehicleTypes = new ArrayList<>();
                String[] types = params.get(1).split(",");
                for (String type : types) {
                    vehicleTypes.add(VehicleType.valueOf(type));
                }
                System.out.println(branchService.onboardBranch(params.get(0), vehicleTypes) ? "TRUE" : "FALSE");
                break;

            case "ADD_VEHICLE":
                System.out.println(vehicleService.onboardVehicle(params.get(0), VehicleType.valueOf(params.get(1)), params.get(2), Double.valueOf(params.get(3))) ? "TRUE" : "FALSE");
                break;

            case "BOOK":
                double bookingPrice = bookingService.createBooking(params.get(0), VehicleType.valueOf(params.get(1)), Integer.valueOf(params.get(2)), Integer.valueOf(params.get(3)));
                System.out.println((int) bookingPrice);
                break;

            case "DISPLAY_VEHICLES":
                List<Vehicle> vehicles = vehicleService.getAllAvailableVehicles(params.get(0), Integer.valueOf(params.get(1)), Integer.valueOf(params.get(2)));
                int n = vehicles.size();
                if (n != 0) {
                    for (int i = 0; i < n - 1; i++) {
                        System.out.print(vehicles.get(i).getId() + ",");
                    }
                    System.out.print(vehicles.get(n - 1).getId());
                    System.out.println();
                } else {
                    System.out.println(-1);
                }
                break;
        }
    }

}
