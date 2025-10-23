package Objects;
import interfaces.Device;

public class Kettle implements Device {
    private boolean isOn = false;

    @Override
    public void operation() {
        if (isOn) {
            System.out.println("Castle is already on ");
        } else {
            System.out.println("Castle is off");
        }
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("Castle is turned on (boiling water)");
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("Castle is turned off");
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public String getName() {
        return "Castle";
    }
}
