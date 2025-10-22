package Decorators;
import interfaces.Device;

public abstract class Decorator implements Device {
    protected Device device;

    public Decorator(Device device){
        this.device=device;
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
