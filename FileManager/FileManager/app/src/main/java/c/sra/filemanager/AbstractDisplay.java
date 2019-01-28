package c.sra.filemanager;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public abstract class AbstractDisplay{
    final String TAG = "FileManager";

    protected abstract ArrayList<ListItem> makeListItem(final Context context);
    protected abstract void clearList(final Context context);

    public ArrayList<ListItem> display(Context context) {
        ArrayList<ListItem> listItems = makeListItem(context);
        return listItems;
    }

    public void clear(Context context) {
        clearList(context);
    }
}
