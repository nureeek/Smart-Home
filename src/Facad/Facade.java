package Facad;

import Decorators.DeviceDecorator;
import Decorators.EnergyUsageDecorator;
import Decorators.MobileAppDecorator;
import Decorators.MotionSensorDecorator;
import com.sun.tools.javac.Main;
import interfaces.Device;
import Database.DatabaseManager;
import Objects.Music;
import java.sql.SQLException;
import java.util.Scanner;


public class Facade {
    private Device music;
    private Device garage;
    private Device kettle;
    private Device livingRoomLight;
    private Device kitchenLight;
    private Device bedroomLight;
    private DatabaseManager db;

    public Facade(Device music, Device garage, Device kettle, Device livingRoomLight, Device kitchenLight, Device bedroomLight, DatabaseManager db) {
        this.music = music;
        this.garage = garage;
        this.kettle = kettle;
        this.livingRoomLight = livingRoomLight;
        this.kitchenLight = kitchenLight;
        this.bedroomLight = bedroomLight;
        this.db = db;
    }

    private void logEvents(String[][] events) {
        for (String[] event : events) {
            try {
                db.logEvent(event[0], event[1]);
            } catch (SQLException e) {
                System.out.println("Error logging event: " + event[0] + ": " + e.getMessage());
            }
        }
    }

    public void detectMotion(Device device, boolean detected) {
        if (!(device instanceof MotionSensorDecorator)) {
            System.out.println("This device has no motion sensor " + device.getName());
            return;
        }
        MotionSensorDecorator sensor = (MotionSensorDecorator) device;
        sensor.detectMotion(detected);
        device.operation();

        System.out.println("Motion detection updated for: " + sensor.getName());
        double energyusage=findEnergyUsage(device);

        try {
            db.logEvent(device.getName(), "Motion Detected",energyusage);
        } catch (SQLException e) {
            System.out.println("Error logging motion event " + e.getMessage());
        }
    }


    public void turnOnLivingRoom() {
        System.out.println("Living room has been turned on");
        try {
            db.logEvent("Living Room Light", "Turned On");
            db.logEvent("Music system", "turned on");
        } catch (SQLException e) {
            System.out.println("Error logging living room event " + e.getMessage());
        }
    }

    public void turnOffLivingRoom() {
        System.out.println("Living room has been turned off");
        livingRoomLight.operation();
        music.operation();
        try {
            db.logEvent("Living Room Light ", "Turned off");
            db.logEvent("Music system", "turned off");
        } catch (SQLException e) {
            System.out.println("Error logging living room event " + e.getMessage());
        }
    }

    public void turnOnKitchen() {
        System.out.println("Kitchen has been turned on");
        kitchenLight.operation();
        music.operation();
        kettle.operation();
        try {
            db.logEvent("Kitchen Light", "Turned On");
            db.logEvent("Music System", "Turned On");
            db.logEvent("Kettle", "Turned On");
        } catch (SQLException e) {
            System.out.println("Error logging kitchen event: " + e.getMessage());
        }
    }

    public void turnOffKitchen() {
        System.out.println("Kitchen has been turned off");
        kitchenLight.operation();
        music.operation();
        kettle.operation();
        try {
            db.logEvent("Kitchen Light", "Turned off");
            db.logEvent("Music System", "Turned off");
            db.logEvent("Kettle", "Turned off");
        } catch (SQLException e) {
            System.out.println("Error logging kitchen event: " + e.getMessage());
        }
    }

    public void turnOnBedroom() {
        System.out.println("Bedroom has been turned on");
        bedroomLight.operation();
        music.operation();
        try {
            db.logEvent("Bedroom Light", "Turned On");
            db.logEvent("Music System", "Turned On");
        } catch (SQLException e) {
            System.out.println("Error logging bedroom event: " + e.getMessage());
        }
    }

    public void turnOffBedroom() {
        System.out.println("Bedroom has been turned off");
        bedroomLight.operation();
        music.operation();
        try {
            db.logEvent("Bedroom Light", "Turned off");
            db.logEvent("Music System", "Turned off");
        } catch (SQLException e) {
            System.out.println("Error logging bedroom event: " + e.getMessage());
        }
    }

    public void openGarage() {
        System.out.println("Garage has opened");
        garage.operation();
        try {
            db.logEvent("Garage", "Opened");
        } catch (SQLException e) {
            System.out.println("Error logging garage event: " + e.getMessage());
        }
    }

    public void closeGarage() {
        System.out.println("Garage has been closed");
        try {
            db.logEvent("Garage", "closed   ");
        } catch (SQLException e) {
            System.out.println("Error logging garage event: " + e.getMessage());
        }
    }

    public void startPartyMode(Scanner scanner) {
        System.out.println("Party Mode has started");

        if (livingRoomLight instanceof EnergyUsageDecorator) {
            Device decorated = ((EnergyUsageDecorator) livingRoomLight).getDecoratedDevice();
            if (decorated instanceof MobileAppDecorator) {
                ((MobileAppDecorator) decorated).connectApp();
            }
        }




        music.turnOn();
        music.operation();

        garage.operation();

        double livingEnergy = findEnergyUsage(livingRoomLight);
        double bedroomEnergy = findEnergyUsage(bedroomLight);
        double kitchenEnergy = findEnergyUsage(kitchenLight);
        double garageEnergy = findEnergyUsage(garage);
        double musicEnergy = findEnergyUsage(music);
        try {
            db.logEvent("Living Room Light", "Turned On (Party Mode)", livingEnergy);
            db.logEvent("Bedroom Light", "Turned On (Party Mode)", bedroomEnergy);
            db.logEvent("Kitchen Light", "Turned On (Party Mode)", kitchenEnergy);

        } catch (SQLException e) {
            System.out.println("Error logging energy usage in Party Mode: " + e.getMessage());
        }
        Device current = music;
        Music musicDevice = null;

        if (current instanceof Music) {
            musicDevice = (Music) current;
            System.out.println("Available songs;");
            for (String song : musicDevice.getPlaylist()) {
                System.out.println(" - " + song);
            }
            System.out.println("Enter the song name to play ");
            String songName = scanner.nextLine();
            musicDevice.chooseSong(songName);
        }
    }
    public void activateNightMode() {
        System.out.println("Night Mode has started");
        System.out.println("Turning off all lights and music");

        logEvents(
                new String[][]{
                        {"All lights", "turned Off (Night Mode)"},
                        {"All music", "turned Off (Night Mode)"}
                }
        );
    }

    public void leaveHome() {
        System.out.println("Leaving home");
        System.out.println("Turning off all devices");
        closeGarage();
        logEvents(
                new String[][]{
                        {"All Devices", "turned Off (Leaving home)"},
                        {"All Music ", "turned Off (Leaving home)"},
                        {"Garage ", "closed (Leaving home)"}
                }
        );
    }


    public void printEnergyUsage(String name, Device device) {
        double totalEnergy = findEnergyUsage(device);
        if (totalEnergy >= 0) {
            System.out.println(name + ": " + String.format("%.2f", totalEnergy) + " kwh used");
            db.saveEnergyUsage(name, totalEnergy);
        } else {
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
