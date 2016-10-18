package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 05:22
 *
 * All bytes/shorts must be used as unsigned.
 *
 * A structure representing the standard USB device descriptor. This
 * descriptor is documented in section 9.6.1 of the USB 3.0 specification.
 * All multiple-byte fields are represented in host-endian format.
 */

@Name("libusb_device_descriptor")
public class libusb_device_descriptor extends BaseCppInterface
{
    /** Size of this descriptor (in bytes) */
    @MemberGetter
    public native byte bLength();

    /** Descriptor type. Will have value
     * \ref libusb_descriptor_type::LIBUSB_DT_DEVICE LIBUSB_DT_DEVICE in this
     * context. */
    @MemberGetter
    public native byte  bDescriptorType();

    /** USB specification release number in binary-coded decimal. A value of
     * 0x0200 indicates USB 2.0, 0x0110 indicates USB 1.1, etc. */
    @MemberGetter
    public native short bcdUSB();

    /** USB-IF class code for the device. See \ref libusb_class_code. */
    @MemberGetter
    public native byte  bDeviceClass();

    /** USB-IF subclass code for the device, qualified by the bDeviceClass
     * value */
    @MemberGetter
    public native byte  bDeviceSubClass();

    /** USB-IF protocol code for the device, qualified by the bDeviceClass and
     * bDeviceSubClass values */
    @MemberGetter
    public native byte  bDeviceProtocol();

    /** Maximum packet size for endpoint 0 */
    @MemberGetter
    public native byte  bMaxPacketSize0();

    /** USB-IF vendor ID */
    @MemberGetter
    public native short idVendor();

    /** USB-IF product ID */
    @MemberGetter
    public native short idProduct();

    /** Device release number in binary-coded decimal */
    @MemberGetter
    public native short bcdDevice();

    /** Index of string descriptor describing manufacturer */
    @MemberGetter
    public native byte  iManufacturer();

    /** Index of string descriptor describing product */
    @MemberGetter
    public native byte  iProduct();

    /** Index of string descriptor containing device serial number */
    @MemberGetter
    public native byte  iSerialNumber();

    /** Number of possible configurations */
    @MemberGetter
    public native byte  bNumConfigurations();

    public native void allocate();  // Define allocator

    public libusb_device_descriptor()
    {
        allocate();
    }

    public String pidvid()
    {
        return pidvid(idVendor(), idProduct());
    }

    public static String pidvid(int vid, int pid)
    {
        return String.format("%04x:%04x", (0xFFFF & vid), (0xFFFF & pid) ).toUpperCase();
    }
}
