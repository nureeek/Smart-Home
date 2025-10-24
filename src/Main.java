import Database.DatabaseManager;
import Decorators.*;
import Objects.*;
import interfaces.Device;
import Facad.Facade;
import java.sql.Timestamp;
import java.util.Scanner;

public class Main {
    static Facade home;
    static Device kettle;
    static Device livingRoomLight;
    static Device bedroomLight;
    static Device kitchenLight;
    static Device music;
    static Device garage;
    static DatabaseManager db;


    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        db = new DatabaseManager();
        db.setSessionStart(new Timestamp(System.currentTimeMillis()));

        livingRoomLight = new MotionSensorDecorator(
                new EnergyUsageDecorator(
                        new MobileAppDecorator(
                                (new Lights())
                        )
                )
        );

        bedroomLight = new MotionSensorDecorator(
                new EnergyUsageDecorator(
                        new MobileAppDecorator(
                                (new Lights())
                        )
                )
        );

        kitchenLight = new MotionSensorDecorator(
                new EnergyUsageDecorator(
                        new MobileAppDecorator(
                                (new Lights())
                        )
                )
        );

        music = new Music();
        garage = new Garage();
        kettle=new Kettle();


        home = new Facade(music, garage, kettle, livingRoomLight, kitchenLight, bedroomLight, db);
        musicControl(music,scanner);
        modeControl(scanner);
        scanner.close();
    }
    public static void musicControl(Device music, Scanner scanner) {
        while (true) {
            System.out.println("Music control: 'next' > next song \n" +
                    " 'prev' > previous song \n" +
                    " 'pause' > stop \n " +
                    "'choose' > select song \n" +
                    " 'exit' > leave from music control");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("on")) {
                music.turnOn();
            }else if (command.equalsIgnoreCase("off")) {
                music.turnOff();
            }else if (command.equalsIgnoreCase("next")) {
                ((Music) music).nextSong();
            } else if (command.equalsIgnoreCase("prev")) {
                ((Music) music).prevSong();
            } else if (command.equalsIgnoreCase("pause")) {
                ((Music) music).stop();
            } else if (command.equalsIgnoreCase("choose")) {
                System.out.println("Select a song: ");
                String song = scanner.nextLine();
                ((Music)music).chooseSong(song);
            } else if (command.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Nothing to do");
            }
        }
    }
    public static void modeControl(Scanner scanner) {
        while (true) {
            System.out.println("Enter the number of claps to activate: 1-Party mode, 2-Night mode, 3-Leave home, 0-exit");
            System.out.println("Your choice: ");
            if (!scanner.hasNextInt()) {
                String invalid = scanner.nextLine();
                System.out.println("Invalid input" + invalid);
                continue;
            }
            int numberOfClaps = scanner.nextInt();
            scanner.nextLine();
            if (numberOfClaps == 1) {


                home.startPartyMode(scanner);

            } else if (numberOfClaps == 2) {
                home.activateNightMode();
            } else if (numberOfClaps == 3) {
                home.leaveHome();
            } else if (numberOfClaps == 0) {
                System.out.println("Exiting");
                break;
            } else {
                System.out.println("Nothing to do");
            }
        }

        System.out.println("Motion detection simulation ");
        home.detectMotion(livingRoomLight, true);
        home.detectMotion(bedroomLight, true);
        home.detectMotion(kitchenLight, true);
        home.detectMotion(music, true);
        home.detectMotion(garage, true);
        db.showNewEvents();



        System.out.println("Motion detection check:");
        ((MotionSensorDecorator) livingRoomLight).detectMotion(true);
        livingRoomLight.operation();
    }
}

