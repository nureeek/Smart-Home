package Decorators;
import interfaces.Device;

public class EnergyUsageDecorator extends DeviceDecorator {
    private double energyUsage = 0.0;

    public EnergyUsageDecorator(Device decoratedDevice) {
        super(decoratedDevice);
    }

    private boolean energyCounted = false;

    @Override
    public void operation() {
        if (!decoratedDevice.isOn()) {
            decoratedDevice.turnOn();
            decoratedDevice.operation();
            if (!energyCounted) {
                energyUsage += 5.1;
                System.out.println(decoratedDevice.getName() + " EnergyUsage: " + energyUsage + " kWh");
                energyCounted = true;
            }
        }
    }



    public void trackEnergyUsage(){
        energyUsage+= Math.random()*3+1;
    }
    public double getEnergyUsage(){
        return energyUsage;
    }
    @Override
    public boolean isOn() {
        return decoratedDevice.isOn();
    }
    @Override
    public String getName(){
        return decoratedDevice.getName()+" + Energy Usage Tracking";
    }
    @Override
    public void turnOn(){
        decoratedDevice.turnOn();
        trackEnergyUsage();
    }
    @Override
    public void turnOff(){
        decoratedDevice.turnOff();
    }

    public Device getDecoratedDevice(){
        return decoratedDevice;
    }


}

