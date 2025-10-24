package Decorators;
import interfaces.Device;

public class MotionSensorDecorator extends DeviceDecorator {
    private boolean motionDetected = false;

    public MotionSensorDecorator(Device device) {
        super(device);
    }

    @Override
    public void operation() {
        if (motionDetected) {
            System.out.println("Motion Detected → LIGHT ON: " + device.getName());
            device.operation();
        } else {
            System.out.println("No Motion → LIGHT OFF: " + device.getName());
            device.operation();
        }
    }

    @Override
    public void turnOn() {
        device.turnOn();
    }

    @Override
    public void turnOff() {
        device.turnOff();
    }

    @Override
    public boolean isOn() {
        return device.isOn();
    }

    @Override
    public String getName() {
        return device.getName() + " Motion Sensor";
    }

    public void detectMotion(boolean detected) {
        this.motionDetected = detected;
        System.out.println("Motion sensor updated: " + (detected ? "MOTION DETECTED" : "NO MOTION"));
    }

    public Device getDecoratedDevice() {
        return device;
    }
}
