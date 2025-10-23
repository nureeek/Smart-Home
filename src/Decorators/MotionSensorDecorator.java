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
            System.out.println("Motion Detected → LIGHT ON: " + decoratedDevice.getName());
            decoratedDevice.operation();
        } else {
            System.out.println("No Motion → LIGHT OFF: " + decoratedDevice.getName());
            decoratedDevice.operation();
        }
    }

    @Override
    public void turnOn() {
        decoratedDevice.turnOn();
    }

    @Override
    public void turnOff() {
        decoratedDevice.turnOff();
    }

    @Override
    public boolean isOn() {
        return decoratedDevice.isOn();
    }

    @Override
    public String getName() {
        return decoratedDevice.getName() + " Motion Sensor";
    }

    public void detectMotion(boolean detected) {
        this.motionDetected = detected;
        System.out.println("Motion sensor updated: " + (detected ? "MOTION DETECTED" : "NO MOTION"));
    }

    public Device getDecoratedDevice() {
        return decoratedDevice;
    }
}
