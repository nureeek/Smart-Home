import Database.DatabaseManager;
import Decorators.*;
import Objects.*;
import interfaces.Device;
import Facad.Facade;

import java.sql.SQLOutput;
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
        livingRoomLight = new Lights();
        bedroomLight = new Lights();
        kitchenLight = new Lights();
        music = new Music();
        Scanner scanner = new Scanner(System.in);
        garage = new Garage();
        db = new DatabaseManager();

        home = new Facade(music, garage, kettle, livingRoomLight, kitchenLight, bedroomLight, db);
        musicControl(music,scanner);
        modeControl(scanner);
        db.showAllEvents();
        scanner.close();
    }
    public static void musicControl(Device music, Scanner scanner) {
        while (true) {
            System.out.println("Music control: 'next' > next song, 'prev' > previous song, 'pause' > stop, 'choose' > select song, 'exit' > leave from music control");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("next")) {
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
    public static void modeControl(Scanner scanner){
                while (true) {
                    System.out.println("Enter the number of claps to activate: 1-Party mode, 2-Night mode, 3-Leave home, 0-exit");
                    System.out.println("Your choice: ");
                    if(!scanner.hasNextInt()){
                        String invalid=scanner.nextLine();
                        System.out.println("Invalid input" + invalid );
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



        Device livingRoomLight = new EnergyUsageDecorator(
                new MobileAppDecorator(
                        new ScheduledDecorator(
                                new MotionSensorDecorator(new Lights())
                        )
                )
        );

        Device bedroomLight = new EnergyUsageDecorator(
                new MobileAppDecorator(
                        new ScheduledDecorator(
                                new MotionSensorDecorator(new Lights()
                        )
                )
        )
        );
        Device kitchenLight = new EnergyUsageDecorator(
                new MobileAppDecorator(
                        new ScheduledDecorator(
                                new MotionSensorDecorator(
                                        new Lights()
                                )
                        )
                )
        );


        System.out.println("Operations for Living Room light ");
        livingRoomLight.operation();

        System.out.println("Operations for Bedroom light ");
        bedroomLight.operation();

        System.out.println("Operations for Kitchen Light ");
        kitchenLight.operation();

        System.out.println("Motion detection simulation ");
        home.detectMotion(livingRoomLight,true);
        home.detectMotion(bedroomLight,false);

        livingRoomLight.operation();
        bedroomLight.operation();
        kitchenLight.operation();
        db.showAllEvents();

        System.out.println("Energu used summary");
        printEnergyUsage("Living Room Light", livingRoomLight);
        printEnergyUsage("Bedroom Light", bedroomLight);
        printEnergyUsage("Kitchen Light", kitchenLight);
    }
    public static void printEnergyUsage(String name,Device device){
        double totalEnergy=findEnergyUsage(device);
        if(totalEnergy>=0){
            System.out.println(name + ": "+ String.format("%.2f",totalEnergy)+ " kwh used");
            db.saveEnergyUsage(name,totalEnergy);
        }
        else {
            System.out.println(name + "No energy data available");
        }

    }
    public static double findEnergyUsage(Device device) {
        if (device instanceof EnergyUsageDecorator) {
            return ((EnergyUsageDecorator) device).getEnergyUsage();
        } else if (device instanceof DeviceDecorator) {
            return findEnergyUsage(((DeviceDecorator) device).getDecoratedDevice());
        }
        return -1;
}
    }