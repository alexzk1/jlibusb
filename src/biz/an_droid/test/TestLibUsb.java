package biz.an_droid.test;

import biz.an_droid.usblib.UsbContext;
import biz.an_droid.usblib.enumerator.IDeviceList;
import biz.an_droid.usblib.enumerator.IUsbDeviceEnum;
import biz.an_droid.usblib.excepts.LibUsbException;
import biz.an_droid.usblib.jlibusb.libusb_device;

import java.io.IOException;


/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 19:10
 */
public class TestLibUsb
{
    public static void main(String[] argv) throws IOException
    {

        try (UsbContext cnt = new UsbContext())
        {
            IUsbDeviceEnum device = cnt.getEnumerator();
            device.updateDevices(null);
            IDeviceList list = device.getCachedDeviceList();

            for (int i = 0; i< list.size(); i++)
                try(libusb_device d = list.get(i))
                {
                    System.out.println(d.getDescriptor().pidvid());
                }

        } catch (LibUsbException le)
        {
            System.err.println(le.getMessage());
            le.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
