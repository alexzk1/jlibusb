package biz.an_droid.usblib;

import biz.an_droid.jars.NativesJarLoader;
import biz.an_droid.jars.OsCheck;
import biz.an_droid.usblib.enumerator.IUsbDeviceEnum;
import biz.an_droid.usblib.enumerator.UsbDeviceEnum;
import biz.an_droid.usblib.excepts.LibUsbError;
import biz.an_droid.usblib.jlibusb.*;

import java.io.IOException;

import static biz.an_droid.jars.OsCheck.getOperatingSystemType;

/**
 * Created by alex (alexzkhr@gmail.com) on 11/13/14.
 * At 00:34
 */
public class UsbContext implements AutoCloseable
{
    private final libusb_context usbContext;
    private final UsbDeviceEnum enumerator;

    static
    {
        String s = null;
        try
        {
            s = UsbContext.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (s==null || s.toLowerCase().endsWith(".jar"))
                tryLoadUsbLibFromJar();
            else
            {
                if (getOperatingSystemType() != OsCheck.OSType.Android)
                    tryLoadUsbLibFromProject();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public UsbContext()
    {
        usbContext = new libusb_context();
        usbContext.setNull();
        /*
        libusb requires that the VFS usbfs is mounted. After adding the following line to /etc/fstab the problem (err -99 on _init) was solved:
        usbfs   /proc/bus/usb   usbfs   defaults   0   0
         */
        LibUsbError.buildErr(LibUsbGlobals.libusb_init(usbContext)).RiseIf();
        enumerator = new UsbDeviceEnum(usbContext);
    }

    @Override
    public void close() throws Exception
    {
        if (enumerator != null)
            enumerator.close();

        if (!usbContext.isNull())
            LibUsbGlobals.libusb_exit(usbContext);
    }

    public IUsbDeviceEnum getEnumerator()
    {
        return enumerator;
    }

    public static void tryLoadUsbLibFromProject() throws IOException
    {
        String s = UsbContext.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        s+="../../../lib/tojar/lib/lin64/libjnilibusb.so";
        System.loadLibrary("usb-1.0");
        System.load(s);
    }

    public static void tryLoadUsbLibFromJar() throws IOException
    {
        String p = OsCheck.getJarLoadPath();
        boolean loaded = false;

        try
        {
            System.loadLibrary("usb-1.0");
            loaded = true;
        }
        catch (UnsatisfiedLinkError e)
        {
           System.err.println("No native usb-1.0 found, trying from jar.");
        }

        if (getOperatingSystemType() == OsCheck.OSType.Android)
        {
            System.loadLibrary("jnilibusb");
        }
        else
        {
            //System.out.println("Loading path: "+p);
            if (getOperatingSystemType() == OsCheck.OSType.Windows)
            {
                if (OsCheck.getBitness() == OsCheck.CpuBitness.b32)
                    NativesJarLoader.loadLibraryFromJar(p + "/libgcc_s_sjlj-1.dll");
                else
                    NativesJarLoader.loadLibraryFromJar(p + "/libgcc_s_seh-1.dll");
                NativesJarLoader.loadLibraryFromJar(p + "/libstdc++-6.dll");
                if (!loaded)
                    NativesJarLoader.loadLibraryFromJar(p + "/libusb-1.0.dll");
                NativesJarLoader.loadLibraryFromJar(p + "/jnilibusb.dll");
            } else
            {
                if (!loaded)
                    NativesJarLoader.loadLibraryFromJar(p + "/libusb-1.0.so");
                NativesJarLoader.loadLibraryFromJar(p + "/libjnilibusb.so");
            }
        }
    }
}
