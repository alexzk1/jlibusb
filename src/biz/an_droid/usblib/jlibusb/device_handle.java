package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Opaque;

import java.io.Closeable;
import java.util.BitSet;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 05:55
 */
@Name("libusb_device_handle")
@Opaque
public class device_handle extends BaseCppInterface implements Closeable
{
    protected device_handle()
    {
        //constructor is NEVER called actually ...because object is allocated at C++ code
    }

    @Override
    public void close()
    {
        //System.err.println("Closing handles...");
        releaseAll();
        LibUsbGlobals.libusb_close(this);
    }

    public int kernel_driver_active(int interface_number)
    {
        return LibUsbGlobals.libusb_kernel_driver_active(this, interface_number);
    }

    public int detach_kernel_driver(int interface_number)
    {
        return LibUsbGlobals.libusb_detach_kernel_driver(this, interface_number);
    }

    public int attach_kernel_driver(int interface_number)
    {
        return LibUsbGlobals.libusb_attach_kernel_driver(this, interface_number);
    }

    public int set_auto_detach_kernel_driver(boolean enable)
    {
        return LibUsbGlobals.libusb_set_auto_detach_kernel_driver(this, enable);
    }

    public int set_configuration(int configuration)
    {
        return LibUsbGlobals.libusb_set_configuration(this, configuration);
    }

    private BitSet claimed = null;
    protected synchronized void allocJavaVars()
    {
        if (claimed == null)
        {
            claimed = new BitSet(64);
        }
    }

    public int claim_interface(int interface_number)
    {
        allocJavaVars();
        synchronized (claimed)
        {
            int u = LibUsbGlobals.libusb_claim_interface(this, interface_number);
            claimed.set(interface_number, 0 == u);
            return u;
        }
    }

    public int release_interface(int interface_number)
    {
        allocJavaVars();
        synchronized (claimed)
        {
            claimed.set(interface_number,false);
            return LibUsbGlobals.libusb_release_interface(this, interface_number);
        }
    }

    public int reset_device()
    {
        return LibUsbGlobals.libusb_reset_device(this);
    }

    public void releaseAll()
    {
        allocJavaVars();
        synchronized (claimed)
        {
            for (int i = claimed.nextSetBit(0); i >= 0; i = claimed.nextSetBit(i + 1))
            {
                release_interface(i);
            }
        }
    }


}
