package biz.an_droid.usblib.enumerator;

import biz.an_droid.usblib.excepts.LibUsbException;

/**
 * Created by alex (alexzkhr@gmail.com) on 11/13/14.
 * At 00:36
 */
public interface IUsbDeviceEnum
{
    IDeviceList updateDevices(IUsbDeviceFilter filter) throws LibUsbException;
    IDeviceList getCachedDeviceList();
}
