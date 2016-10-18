package biz.an_droid.usblib.enumerator;

import biz.an_droid.usblib.jlibusb.libusb_device;

/**
 * Created by alex (alexzkhr@gmail.com) on 11/12/14.
 * At 14:20
 */
public interface IDeviceList
{
    int size();
    libusb_device get(int i);
}
