package Objects;
import interfaces.Device;

public class Lights implements Device {
    private boolean isOn=false;

    public void burnOut(){
        System.out.println("Lights burned out");
        isOn=false;
    }

    public void replace(){
        System.out.println("Lights replaced");
        isOn=false;
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



