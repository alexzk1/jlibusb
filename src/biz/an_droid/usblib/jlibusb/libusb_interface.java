package biz.an_droid.usblib.jlibusb;

import org.bytedeco.javacpp.annotation.Name;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 05:48
 */
@Name("libusb_interface")
public class libusb_interface extends BaseCppInterface
{
    /** Array of interface descriptors. The length of this array is determined
     * by the num_altsetting field. */

    public libusb_interface_descriptor altsetting;

    /** The number of alternate settings that belong to this interface */
    public int num_altsetting;
}
