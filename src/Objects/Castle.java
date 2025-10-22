package Objects;
import interfaces.Device;

public class Castle implements Device {
    @Override
    public void operation(){
        System.out.println("Objects.Castle is opened");
    }

    @Override
    public String getName(){
        return ("Objects.Castle");
    }
}
