package biz.an_droid.usblib.enumerator;

import biz.an_droid.usblib.jlibusb.libusb_device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 21:29
 */
class DeviceList implements IDeviceList
{
    private final List<libusb_device> devices = new ArrayList<>(10);

    protected DeviceList()
    {
    }

    protected void add(libusb_device device)
    {
        devices.add(device.copy());
    }

    protected void clear()
    {
        for (libusb_device d:devices)
            try
            {
                d.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        devices.clear();
    }
    @Override
    public int size()
    {
        return devices.size();
    }

    @Override
    public libusb_device get(int i)
    {
        return devices.get(i).copy();
    }

}
