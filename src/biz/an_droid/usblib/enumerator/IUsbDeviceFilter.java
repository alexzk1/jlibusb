package biz.an_droid.usblib.enumerator;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 21:08
 */
public interface IUsbDeviceFilter
{
    boolean isincluded (int vid, int pid);
    String  description(int vid, int pid);
}
