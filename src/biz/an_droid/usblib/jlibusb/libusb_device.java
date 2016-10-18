package biz.an_droid.usblib.jlibusb;

import biz.an_droid.usblib.excepts.LibUsbError;
import biz.an_droid.usblib.excepts.LibUsbException;
import org.bytedeco.javacpp.annotation.Opaque;

import java.io.Closeable;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 05:54
 *
 * That class which will list connected device and produce this object must increment/decrement references
 */
@Opaque
public class libusb_device extends BaseCppInterface implements Closeable
{
    private libusb_device()
    {
        //constructor is NEVER called actually ...because object is allocated at C++ code
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return LibUsbGlobals.libusb_ref_device(this);
    }

    public libusb_device copy()
    {
        try
        {
            return (libusb_device)clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private libusb_device_descriptor tmpDescr = null;
    public libusb_device_descriptor getDescriptor() throws LibUsbException
    {
        if (tmpDescr == null)
        {
            tmpDescr = new libusb_device_descriptor();
            LibUsbError.buildErr(LibUsbGlobals.libusb_get_device_descriptor(this, tmpDescr)).RiseIf();
        }
        return tmpDescr;
    }

    public device_handle getHandle() throws LibUsbException
    {
        device_handle h = new device_handle();
        LibUsbError.buildErr(LibUsbGlobals.libusb_open(this, h)).RiseIf();
        return h;
    }

    @Override
    public void close()
    {
        LibUsbGlobals.libusb_unref_device(this);
    }
}
