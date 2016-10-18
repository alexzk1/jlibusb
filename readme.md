#libusb for Java

It is a particular port of libusb to java  binaries in OOP manner. Feel free to add missing features. Currently it works with many devices like SDR dongles using bulk transfers.
Project was created using next tools:


- IntelliJ IDEA.

- Oracle JDK 1.8 / ANT.

- Arch-Linux 64 bit as host machine.

- GCCs for i686/x86_64/arm linux/windows (mingw) - set of crosscompilers.

- javacpp - included patched jar to match my machine/ant script.

- Android NDK.


You can [download](https://github.com/alexzk1/jlibusb/blob/master/out/artifacts/jlibusb_jar/jlibusb.jar) precompiled jar file and just add to your project.



##Sample to list USB devices connected:

```java
public class TestLibUsb
{
    public static void main(String[] argv) throws IOException
    {

        try (UsbContext cnt = new UsbContext())
        {
            IUsbDeviceEnum device = cnt.getEnumerator();
            device.updateDevices(null); //maybe instance of IUsbDeviceFilter passed here to catch only exact devices
            IDeviceList list = device.getCachedDeviceList();

            for (int i = 0; i< list.size(); i++)
                try(libusb_device d = list.get(i))
                {
                    System.out.println(d.getDescriptor().pidvid());
                }

        } catch (LibUsbException le)
        {
            System.err.println(le.getMessage());
            le.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
```

