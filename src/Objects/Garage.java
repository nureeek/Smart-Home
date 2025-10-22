package Objects;
import interfaces.Device;

public class Garage implements Device{
    @Override
    public void operation(){
        System.out.println("Garage is opened");
    }
    @Override
    public String getName(){
        return ("Garage");
    }
}
