package biz.an_droid.usblib.excepts;

/**
 * Created by alex (alexzkhr@gmail.com) on 6/22/14.
 * At 04:19
 */
public class LibUsbError
{
    public static final LibUsbError Success = new LibUsbError(0);


    private final int code;

    private LibUsbError(int code)
    {
        this.code = code;
    }

    public boolean isSuccess()
    {
        return equals(Success);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibUsbError that = (LibUsbError) o;

        if (code != that.code) return false;

        return true;
    }

    public void RiseIf() throws LibUsbException
    {
        LibUsbException.RiseIf(code);
    }

    //all this for 1 thing: optimize GC, success code should not create new objects
    public static LibUsbError buildErr(int code)
    {
        if (code<0)
            return new LibUsbError(code);
        return Success;
    }


}
