package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 09:12
 */

@Platform(include="libusb-1.0/libusb.h", link="usb-1.0",library="jnilibusb")
@Name("libusb_device*")
public class plibusb_device extends PointerPointer<libusb_device>
{

}
