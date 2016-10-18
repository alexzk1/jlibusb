package biz.an_droid.usblib.jlibusb;


import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Platform;

/**
 * Created by alex (alexzkhr@gmail.com) on 3/21/14.
 * At 13:57
 *
 * Help on making build scripts:
 * https://code.google.com/p/javacpp/wiki/BuildScript
 */
@Platform(include="libusb-1.0/libusb.h", link="usb-1.0",library="jnilibusb")
class BaseCppInterface extends Pointer
{
    protected BaseCppInterface(){super();};
    protected void allocJavaVars()
    {

    }
}
