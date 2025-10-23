package Objects;
import interfaces.Device;

public class Lights implements Device {
    private boolean isOn=false;
    private boolean isBroken=false;

    public void burnOut(){
        System.out.println("Lights burned out");
        isOn=false;
        isBroken=true;
    }

    public void replace(){
        System.out.println("Lights replaced");
        isBroken=false;
    }
    @Override
    public void turnOn(){
        if (isBroken){
            System.out.println("Cant turn on,lights is burned out");
            return;
        }
        if(isOn){
            isOn=true;
            System.out.println("Lights are now ON");
        }else{
            System.out.println("Lights are already On");
        }
    }
    @Override
    public void turnOff(){
        if (isOn){
            isOn=false;
            System.out.println("Lights are now Off");
        }else{
            System.out.println("Lights are already off");
        }
    }
    @Override
    public boolean isOn(){
        return isOn;
    }
    @Override
    public void operation(){
        if(isOn){
            System.out.println("Lights is already on");
        }
        else{
            System.out.println("Lights is now on");
        }
    }

    @Override
    public String getName(){
        return ("Lights");
    }
}



