package Decorators;
import interfaces.Device;
public class MobileAppDecorator extends Decorator {
    private boolean isConnected;

    public MobileAppDecorator(Device device) {
        super(device);
    }

    @Override
    public void operation() {
        if (isConnected) {


        }
    }
    public void connectApp() {
        if (!isConnected) {
            isConnected = true;
            System.out.println("Mobile app connected to " + device.getName());
        } else {
            System.out.println("Mobile app already connected to " + device.getName());
        }
    }


    public void disconnectApp() {
        isConnected = false;
        System.out.println("Mobile app disconnected from " + device.getName());
    }

    @Override
    public String getName() {
        return device.getName() + " (Mobile app controll)";

    }
    public Device getDecoratedDevice() {
        return device;
    }

}