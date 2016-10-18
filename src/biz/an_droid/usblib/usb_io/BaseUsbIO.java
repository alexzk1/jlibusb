package biz.an_droid.usblib.usb_io;

import biz.an_droid.usblib.buffers.TByteArrayListFast;
import biz.an_droid.usblib.excepts.LibUsbError;
import biz.an_droid.usblib.excepts.LibUsbException;
import biz.an_droid.usblib.jlibusb.LibUsbGlobals;
import biz.an_droid.usblib.jlibusb.device_handle;
import biz.an_droid.usblib.jlibusb.libusb_device;
import com.sun.istack.internal.NotNull;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;

import java.io.Closeable;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/23/14.
 * At 05:36
 */
public abstract class BaseUsbIO implements Closeable
{
    protected final libusb_device device;
    protected device_handle handle = null;

    private final static int DEFAULT_BULK_TIMEOUT = 5000;
    private final static int DEFAULT_USB_CHUNK_SIZE = 16 * 32 * 512;

    private int BULK_TIMEOUT = DEFAULT_BULK_TIMEOUT;
    private BytePointer bpBuffer = null;
    private final IntPointer transferredBytes;


    protected BaseUsbIO(@NotNull libusb_device device) throws LibUsbException
    {
        this.device = device.copy();
        setBulkSize(DEFAULT_USB_CHUNK_SIZE);
        transferredBytes = new IntPointer(1);
        openHandle();
    }

    protected BaseUsbIO(@NotNull device_handle hndl) throws LibUsbException
    {

        this.device = null;
        setBulkSize(DEFAULT_USB_CHUNK_SIZE);
        transferredBytes = new IntPointer(1);
        handle = hndl;
        handle.set_auto_detach_kernel_driver(true);
    }

    protected void openHandle() throws LibUsbException
    {
        /*if (handle!=null)
            try
            {
                handle.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }*/
        handle = device.getHandle();
        handle.set_auto_detach_kernel_driver(true);
    }

    public int claimInterface(int id)
    {
        return handle.claim_interface(id);
    }

    public int setConfiguration(int config)
    {
        return handle.set_configuration(config);
    }

    public void releaseInterface(int id)
    {
        handle.release_interface(id);
    }

    public void resetDevice()
    {
        handle.reset_device();
    }

    @Override
    public void close()
    {
        handle.close();
        if (device != null)
            device.close();
    }

    protected static int endpoint_read(int addr)
    {
        return strip8(strip8(addr) | (1<<7));
    }

    protected static int endpoint_write(int addr)
    {
        return strip8(strip8(addr) & (~(1<<7)));
    }

    public static int strip8(long v)
    {
        return (int)(v & 0xFF);
    }

    public static int strip16(long v)
    {
        return (int)(v & 0xFFFF);
    }


    protected int control_transfer(int request_type, int bRequest, int wValue, int wIndex,
                                   BytePointer data, int wLength, long timeout) throws LibUsbException
    {
        request_type = strip8(request_type);
        bRequest = strip8(bRequest);
        wValue = strip16(wValue);
        wIndex = strip16(wIndex);
        wLength = strip16(wLength);
        int transf;
        LibUsbError.buildErr(transf = LibUsbGlobals.libusb_control_transfer(handle,
                request_type, bRequest, wValue, wIndex, data.position(0).limit(wLength-1), wLength, timeout)).RiseIf();
        return transf;
    }

    public void setBULK_TIMEOUT(int BULK_TIMEOUT)
    {
        this.BULK_TIMEOUT = BULK_TIMEOUT;
    }

    public int setBulkSize(int USB_CHUNK_SIZE)
    {

        synchronized (this)
        {
            if (USB_CHUNK_SIZE == 0 || USB_CHUNK_SIZE % 512 != 0)
                USB_CHUNK_SIZE  = roundTo512(USB_CHUNK_SIZE);//+= (512 - (USB_CHUNK_SIZE % 512));

            if (bpBuffer == null || USB_CHUNK_SIZE != bpBuffer.capacity())
                bpBuffer = new BytePointer(USB_CHUNK_SIZE);
            return USB_CHUNK_SIZE;
        }
    }

    public static int roundTo512(int val)
    {
        return val + (512 - (val % 512));
    }

    public void bulkRead(int endpoint, TByteArrayListFast dest) throws LibUsbException
    {
        synchronized (this)
        {
            bpBuffer.position(0);
            transferredBytes.position(0);
            transferredBytes.put(0, 0);
            final int cp = bpBuffer.capacity();
            dest.ensureCapacity(cp);
            dest.resetQuick();
            final int err = LibUsbGlobals.libusb_bulk_transfer(handle, endpoint_read(endpoint),
                    bpBuffer, cp, transferredBytes.position(0).limit(1), BULK_TIMEOUT);
            transferredBytes.position(0);
            final int read = transferredBytes.get();
            if (err > -1)
            {
                if (read > 0)
                {
                    dest.fill(0, read, (byte) 0);
                    bpBuffer.position(0);
                    bpBuffer.get(dest.toArrayNoCopy(),0,read);
                    bpBuffer.position(0);
                }
            } else
            {
                LibUsbError.buildErr(err).RiseIf();
            }
        }
    }

    public void bulkWrite(int endpoint, TByteArrayListFast dest) throws LibUsbException
    {
        synchronized (this)
        {
            int cp = bpBuffer.capacity();
            for (int wrote = 0; wrote < dest.size();)
            {
                final int toWrite = Math.min(cp, dest.size() - wrote);

                bpBuffer.position(0);
                bpBuffer.put(dest.toArrayNoCopy(), wrote, toWrite);
                bpBuffer.position(0);

                transferredBytes.position(0);
                transferredBytes.put(0, 0);

                int err = LibUsbGlobals.libusb_bulk_transfer(handle, endpoint_write(endpoint),
                        bpBuffer, toWrite, transferredBytes.position(0).limit(1), BULK_TIMEOUT);
                transferredBytes.position(0);
                final int justwrote = transferredBytes.get();
                if (err > -1 || ( -7 == err && justwrote>=toWrite))
                {
                    wrote+=justwrote;
                }
                else
                {
                    LibUsbError.buildErr(err).RiseIf();
                    break;
                }
            }
        }
    }
}
