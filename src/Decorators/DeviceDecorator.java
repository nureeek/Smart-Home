package Decorators;

import interfaces.Device;

public abstract class DeviceDecorator implements Device {
    protected Device device;

    public DeviceDecorator(Device decoratedDevice) {
        this.device = decoratedDevice;
    }

    @Override
    public void operation() {
        device.operation();
    }

    public Device getDecoratedDevice() {
        return device;
    }
}
