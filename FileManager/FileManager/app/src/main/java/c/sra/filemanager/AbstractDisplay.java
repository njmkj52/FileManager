package c.sra.filemanager;

import android.content.Context;

public abstract class AbstractDisplay{
    public abstract void makeList(final Context context);
    public abstract void makeListener(final Context context);
    public abstract void clearList(final Context context);


    public final void display(Context context) {
        makeList(context);
        makeListener(context);
    }
    public final void clear(Context context) {
        clearList(context);
    }
}
