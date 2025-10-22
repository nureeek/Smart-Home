package Decorators;
import interfaces.Device;

public class EnergyUsageDecorator extends DeviceDecorator{
    private double energyUsage=0.0;
    public EnergyUsageDecorator(Device decoratedDevice){
        super(decoratedDevice);
    }
    @Override
    public void operation(){
        decoratedDevice.operation();
        energyUsage+=5.1;
        System.out.println(decoratedDevice.getName()+ "EnergyUsage: "+energyUsage+ " Kwh");
    }
    public void trackEnergyUsage(){
        energyUsage+= Math.random()*3+1;
    }
    public double getEnergyUsage(){
        return energyUsage;
    }
    public Device getDecoratedDevice(){
        return decoratedDevice;
    }
    @Override
    public String getName(){

        return decoratedDevice.getName()+" + Energy Usage Tracking";
    }
}

