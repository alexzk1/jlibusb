package biz.an_droid.usblib.enumerator;

import biz.an_droid.usblib.excepts.LibUsbException;
import biz.an_droid.usblib.jlibusb.*;


/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 04:35
 */
public class UsbDeviceEnum implements IUsbDeviceEnum
{
    private final libusb_context usbContext;
    private final DeviceList deviceList = new DeviceList();

    public UsbDeviceEnum(libusb_context context)
    {
        usbContext = context;
    }

    @Override
    public IDeviceList updateDevices(IUsbDeviceFilter filter) throws LibUsbException
    {
        plibusb_device list = new plibusb_device();
        int total = LibUsbGlobals.libusb_get_device_list(usbContext, list);
        LibUsbException.RiseIf(total);

        deviceList.clear();
        for (int i = 0; i < total; i++)
        {
            libusb_device dev = list.get(libusb_device.class,i);
            libusb_device_descriptor descr = dev.getDescriptor();
            if (filter==null || filter.isincluded(unsigned(descr.idVendor()), unsigned(descr.idProduct())))
              deviceList.add(dev);
        }
        LibUsbGlobals.libusb_free_device_list(list, true);

        return deviceList;
    }

    @Override
    public IDeviceList getCachedDeviceList()
    {
        return deviceList;
    }

    public void close()
    {
        deviceList.clear();
    }

    public static int unsigned(byte b)
    {
        return (int)b & 0xFF;
    }

    public static int unsigned(short s)
    {
        return (int)s & 0xFFFF;
    }

    public static long unsigned(int i)
    {
        return (long)i & 0xFFFFFFFF;
    }
}
