package users.userUI;

import users.user.service.organizer_service.OrganizerService;
import users.user.service.user_service.AttendeeService;
import users.user.service.user_service.UserService;
import users.user.service.vendor_service.VendorService;

import java.util.Scanner;

public class UserUI {
    private final UserService userService = new UserService();
    private final AttendeeService attendeeService=new AttendeeService();
    private final OrganizerService organizerService = new OrganizerService();
    private final VendorService vendorService = new VendorService();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== RAFIKA CLI ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleRegistration(scanner);
                    break;
                case 2:
                    handleLogin(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    private void handleRegistration(Scanner scanner) {
        System.out.println("\n1. Register as Organizer");
        System.out.println("2. Register as Vendor");
        System.out.println("3. Register as Attendee");
        System.out.print("Select option: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        switch (roleChoice) {
            case 1:
                organizerService.registerOrganizer(scanner);
                break;
            case 2:
                vendorService.registerVendor(scanner);
                break;
            case 3:
                attendeeService.registerAttendee(scanner);
                break;
            default:
                System.out.println("Invalid option. Registration cancelled.");
                break;
        }
    }
    private void handleLogin(Scanner scanner) {
        userService.login(scanner);
    }
}
