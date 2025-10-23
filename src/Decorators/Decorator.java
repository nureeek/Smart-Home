package Decorators;
import interfaces.Device;

public abstract class Decorator implements Device {
    protected Device device;

    public Decorator(Device device){
        this.device=device;
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
    public void operation(){
        device.operation();
    }
    @Override
    public String getName(){
        return device.getName();
    }
}
