package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.annotation.*;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 04:38
 *
 * Interface to some C-style jlibusb to lib usb.
 */
@Platform(include="<libusb-1.0/libusb.h>",link="usb-1.0", library="jnilibusb")
public class LibUsbGlobals
{
    @Cast("const char *") @StdString
    public static native String libusb_strerror(@Cast("enum libusb_error") int errcode);
    public static native int libusb_set_auto_detach_kernel_driver(device_handle dev, @Cast("int") boolean enable);

    @MemberGetter
    public static final native int LIBUSB_REQUEST_TYPE_STANDARD();
    /** Class */
    @MemberGetter
    public static final native int LIBUSB_REQUEST_TYPE_CLASS();
    /** Vendor */
    @MemberGetter
    public static final native int LIBUSB_REQUEST_TYPE_VENDOR();
    /** Reserved */
    @MemberGetter
    public static final native int LIBUSB_REQUEST_TYPE_RESERVED();

    @MemberGetter
    public static final native int LIBUSB_ERROR_TIMEOUT();

    @MemberGetter
    public static final native int LIBUSB_ENDPOINT_IN();

    @MemberGetter
    public static final native int LIBUSB_ENDPOINT_OUT();

    @Name("libusb_open")
    public static native int libusb_open(@ByPtr libusb_device dev, @ByPtrPtr device_handle handle);

    @Name("libusb_close")
    public static native void libusb_close(@ByPtr device_handle dev_handle);

    @Name("libusb_init")
    public static native int  libusb_init(@ByPtrPtr libusb_context ctx);

    @Name("libusb_exit")
    public static native void libusb_exit(@ByPtr libusb_context ctx);

    @Name("libusb_get_device_list")
    @Cast("ssize_t")
    public static native int libusb_get_device_list(libusb_context ctx, @ByPtrPtr plibusb_device list);

    @Name("libusb_get_device_descriptor")
    public static native int libusb_get_device_descriptor(libusb_device dev, @ByPtr libusb_device_descriptor desc);

    @Name("libusb_free_device_list")
    public static native void libusb_free_device_list(plibusb_device list, @Cast("int") boolean unref_devices);

    @Name("libusb_ref_device")
    public static native libusb_device libusb_ref_device(libusb_device dev);

    @Name("libusb_unref_device")
    public static native void libusb_unref_device(libusb_device dev);

    public static native int libusb_set_configuration(device_handle dev, int configuration);
    public static native int libusb_claim_interface(device_handle dev, int interface_number);
    public static native int libusb_release_interface(device_handle dev, int interface_number);
    public static native int libusb_reset_device(device_handle dev);

    public static native int libusb_kernel_driver_active(device_handle dev, int interface_number);
    public static native int libusb_detach_kernel_driver(device_handle dev,int interface_number);
    public static native int libusb_attach_kernel_driver(device_handle dev, int interface_number);


    /* sync I/O */

    public static native int libusb_control_transfer(device_handle dev_handle,
                                            @Cast("uint8_t")int request_type,@Cast("uint8_t")int bRequest,
                                            @Cast("uint16_t")int wValue, @Cast("uint16_t")int wIndex,
                                            @Cast("unsigned char *")BytePointer data,
                                            @Cast("uint16_t")int wLength, @Cast("unsigned int") long timeout);

    public static native int libusb_bulk_transfer(device_handle dev_handle,
                                                  @Cast("uint8_t")int endpoint,
                                                  @Cast("unsigned char *")BytePointer data,
                                                  int length,
                                                  IntPointer actual_length,
                                                  @Cast("unsigned int") long  timeout);

    public static native int libusb_interrupt_transfer(device_handle dev_handle,
                                                       @Cast("uint8_t")int endpoint,
                                                       @Cast("unsigned char *")BytePointer data,
                                                       int length,
                                                       IntPointer actual_length,
                                                       @Cast("unsigned int") long  timeout);

}
