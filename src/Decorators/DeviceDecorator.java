package Decorators;

import interfaces.Device;

public abstract class DeviceDecorator implements Device {
    protected Device decoratedDevice;

    public DeviceDecorator(Device decoratedDevice) {
        this.decoratedDevice = decoratedDevice;
    }

    @Override
    public void operation() {
        decoratedDevice.operation();
    }

    public Device getDecoratedDevice() {
        return decoratedDevice;
    }
}
