package Decorators;
import interfaces.Device;

public class EnergyUsageDecorator extends DeviceDecorator {
    private double energyUsage = 0.0;

    public EnergyUsageDecorator(Device decoratedDevice) {
        super(decoratedDevice);
    }

    @Override
    public void operation() {
        if (!device.isOn()) {
            device.turnOn();
            energyUsage+= Math.random()*3+1;
            System.out.println(device.getName() + " EnergyUsage: " + energyUsage + " kWh");
        }
    }




    public double getEnergyUsage(){
        return energyUsage;
    }
    @Override
    public boolean isOn() {
        return device.isOn();
    }
    @Override
    public String getName(){
        return device.getName()+" + Energy Usage Tracking";
    }
    @Override
    public void turnOn() {
        if (!device.isOn()) {
            device.turnOn();

        }
    }

    @Override
    public void turnOff(){
        device.turnOff();
    }

    public Device getDecoratedDevice(){
        return device;
    }


}

