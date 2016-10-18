package biz.an_droid.usblib.buffers;

import gnu.trove.TByteCollection;
import gnu.trove.list.array.TByteArrayList;

/**
* Created by alex (alexzkhr@gmail.com) on 4/28/14.
* At 09:54
*/
public class TByteArrayListFast extends TByteArrayList
{
    public TByteArrayListFast()
    {
    }

    public TByteArrayListFast(int capacity)
    {
        super(capacity);
    }

    public TByteArrayListFast(int capacity, byte no_entry_value)
    {
        super(capacity, no_entry_value);
    }

    public TByteArrayListFast(TByteCollection collection)
    {
        super(collection);
    }

    public TByteArrayListFast(byte[] values)
    {
        super(values);
    }

    public TByteArrayListFast(byte[] values, byte no_entry_value, boolean wrap)
    {
        super(values, no_entry_value, wrap);
    }

    public byte[] toArrayNoCopy()
    {
        return _data;
    }

}
