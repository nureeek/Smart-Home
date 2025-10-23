package Objects;
import interfaces.Device;

public class Garage implements Device{
    private boolean isOpen = false;

    @Override
    public void turnOn(){
        isOpen = true;
        System.out.println("Garaage opened");
    }
    @Override
    public void turnOff(){
        isOpen = false;
        System.out.println("Garage closed");
    }
    @Override
    public boolean isOn(){
        return isOpen;
    }

    @Override
    public void operation() {
        if (isOpen) {
            System.out.println("Garage is opened");
        } else {
            System.out.println("Opening the garage...");
            turnOn();
        }
    }
    @Override
    public String getName(){
        return ("Garage");
    }
}
