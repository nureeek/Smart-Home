package Facad;

import Decorators.EnergyUsageDecorator;
import Decorators.MobileAppDecorator;
import Decorators.MotionSensorDecorator;
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

    public Facade(Device music, Device garage,Device kettle,Device livingRoomLight,Device kitchenLight,Device bedroomLight,DatabaseManager db) {
        this.music=music;
        this.garage=garage;
        this.kettle=kettle;
        this.livingRoomLight=livingRoomLight;
        this.kitchenLight=kitchenLight;
        this.bedroomLight=bedroomLight;
        this.db=db;
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

    public void detectMotion(Device device,boolean detected){
        if (device instanceof MotionSensorDecorator) {
            MotionSensorDecorator sensor = (MotionSensorDecorator) device;
            sensor.detectMotion(detected);
            System.out.println("Motion detection updated for: " + sensor.getName());
            try {
                db.logEvent(device.getName(), "Motion Detected");
            } catch (SQLException e) {
                System.out.println("Error logging motion event " + e.getMessage());
            }
        }else{
            System.out.println("This device has no motion sensor "+device.getName());
        }
    }


    public void turnOnLivingRoom(){
        System.out.println("Living room has been turned on");
        livingRoomLight.operation();
        music.operation();
        try {
            db.logEvent("Living Room Light ", "Turned On");
            db.logEvent("Music system", "turned on");
        } catch (SQLException e) {
            System.out.println("Error logging living room event " + e.getMessage());
        }
    }
    public void turnOffLivingRoom(){
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
    public void turnOnBedroom(){
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

        livingRoomLight.operation();

        music.turnOn();
        music.operation();

        garage.operation();

        Device current=music;
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
        if (musicDevice != null) {

            logEvents(
                    new String[][]{
                            {"Living Room Light", "Turned On (Party Mode)"},
                            {"Music System", "Playing " + musicDevice.getCurrentSong() + " Turned On (Party Mode)"},
                            {"Garage", "Opened (Party Mode)"}
                    }
            );
        } else {
            logEvents(new String[][]{
                            {"Living Room Light", "Turned On (Party Mode)"},
                            {"Music System", "Turned On (Party Mode)"},
                            {"Garage", "Opened (Party Mode)"}
                    }
            );
        }
    }
        public void activateNightMode () {
            System.out.println("Night Mode has started");
            System.out.println("Turning off all lights and music");

            logEvents(
                    new String[][]{
                            {"All lights"  , "turned Off (Night Mode)"},
                            {"All music", "turned Off (Night Mode)"}
                    }
            );
        }

        public void leaveHome () {
            System.out.println("Leaving home");
            System.out.println("Turning off all devices");
            closeGarage();
            logEvents(
                    new String[][]{
                            {"All Devices", "turned Off (Leaving home)"},
                            {"All Music ","turned Off (Leaving home)"},
                            {"Garage ", "closed (Leaving home)"}
                    }
            );
        }
    }



