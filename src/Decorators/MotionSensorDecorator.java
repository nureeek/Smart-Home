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
            super.operation();
        }
        else{
            System.out.println("No Motion" +device.getName()+ " lights off");
        }
    }
    public void detectMotion(boolean detected){
        this.motionDetected=detected;
    }
    @Override
    public String getName(){
        return device.getName()+  "Motion Sensor";
    }
}
