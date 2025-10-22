package Decorators;
import interfaces.Device;
import java.time.LocalTime;

public class ScheduledDecorator extends Decorator {
    private LocalTime scheduledOnTime = LocalTime.of(8, 0);
    private LocalTime scheduledOffTime = LocalTime.of(23, 0);

    public ScheduledDecorator(Device device) {
        super(device);
    }

    @Override
    public void operation() {
        LocalTime currentTime = LocalTime.now();
        if (scheduledOnTime.isAfter(scheduledOnTime) && scheduledOnTime.isBefore(scheduledOffTime)) {
            System.out.println("Time to turn the light on");
            super.operation();
        } else {
            System.out.println("Time to turn the light off");
        }
    }

    @Override
    public String getName() {
        return device.getName() + "Scheduled Control";
    }
}