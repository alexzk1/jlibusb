package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 05:43
 *
 * /** \ingroup desc
 * A structure representing the standard USB interface descriptor. This
 * descriptor is documented in section 9.6.5 of the USB 3.0 specification.
 * All multiple-byte fields are represented in host-endian format.
 *
 */
@Name("libusb_interface_descriptor")
public class libusb_interface_descriptor extends BaseCppInterface
{
    /** Size of this descriptor (in bytes) */
    @MemberGetter
    public native byte  bLength();

    /** Descriptor type. Will have value
     * \ref libusb_descriptor_type::LIBUSB_DT_INTERFACE LIBUSB_DT_INTERFACE
     * in this context. */
    @MemberGetter
    public native byte  bDescriptorType();

    /** Number of this interface */
    @MemberGetter
    public native byte  bInterfaceNumber();

    /** Value used to select this alternate setting for this interface */
    @MemberGetter
    public native byte  bAlternateSetting();

    /** Number of endpoints used by this interface (excluding the control
     * endpoint). */
    @MemberGetter
    public native byte  bNumEndpoints();

    /** USB-IF class code for this interface. See \ref libusb_class_code. */
    @MemberGetter
    public native byte  bInterfaceClass();

    /** USB-IF subclass code for this interface, qualified by the
     * bInterfaceClass value */
    @MemberGetter
    public native byte  bInterfaceSubClass();

    /** USB-IF protocol code for this interface, qualified by the
     * bInterfaceClass and bInterfaceSubClass values */
    @MemberGetter
    public native byte  bInterfaceProtocol();

    /** Index of string descriptor describing this interface */
    @MemberGetter
    public native byte  iInterface();

    /** Array of endpoint descriptors. This length of this array is determined
     * by the bNumEndpoints field. */
    @MemberGetter
    @Cast("const libusb_endpoint_descriptor*")
    public native libusb_endpoint_descriptor endpoint();

    /** Extra descriptors. If libusbx encounters unknown interface descriptors,
     * it will store them here, should you wish to parse them. */
    @MemberGetter
    @Cast("unsigned char*")
    public native BytePointer extra();

    /** Length of the extra descriptors, in bytes. */
    @MemberGetter
    public native int extra_length();

    public native void allocate();  // Define allocator

    public libusb_interface_descriptor()
    {
        allocate();
    }
}
