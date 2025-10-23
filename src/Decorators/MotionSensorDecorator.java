package Decorators;
import interfaces.Device;

public class MotionSensorDecorator extends Decorator{
    private boolean motionDetected=false;

    public MotionSensorDecorator(Device device){
        super(device);
    }
    @Override
    public void operation(){
        if (motionDetected){
            System.out.println("Motion Detected, LIGHT On" + device.getName());
            device.turnOn();
        }
        else{
            System.out.println("No Motion , lights off" +device.getName());
            device.turnOff();
        }
    }
    public void detectMotion(boolean detected){
        this.motionDetected=detected;
        System.out.println("Motion sensor updated: "+(detected ? "MOTION DETECTED" :"NO MOTION"));
    }
    public boolean isOn(){
        return device.isOn();
    }
    @Override
    public String getName(){
        return device.getName()+  " Motion Sensor";
    }
    public Device getDecoratedDevice() {
        return device;
    }

}
