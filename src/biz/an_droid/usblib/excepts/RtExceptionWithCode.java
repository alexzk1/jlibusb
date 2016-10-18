package biz.an_droid.usblib.excepts;

/**
 * Created by alex (alexzkhr@gmail.com) on 4/25/14.
 * At 11:44
 */
public class RtExceptionWithCode extends RuntimeException
{
    private final int errCode;
    public RtExceptionWithCode(String msg, int code)
    {
        super(msg + String.format(" (code %d).",code));
        errCode = code;
    }

    public int getErrCode()
    {
        return errCode;
    }
}
