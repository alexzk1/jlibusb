package biz.an_droid.usblib.excepts;

import biz.an_droid.usblib.jlibusb.LibUsbGlobals;

/**
 * Created by alex (alexzkhr@gmail.com) on 4/25/14.
 * At 12:00
 */
final public class LibUsbException extends RtExceptionWithCode
{
    public LibUsbException(int code)
    {
        super( (code>-13)?"Usb error: "+ LibUsbGlobals.libusb_strerror(code):getCustom(code) , code);
        //super( "No text descr." , code);
    }

    public static String getCustom(int code)
    {
        return "Error";
    }

    public static void RiseIf(int error) throws LibUsbException
    {
        if (error < 0)
            throw new LibUsbException(error);
    }

}