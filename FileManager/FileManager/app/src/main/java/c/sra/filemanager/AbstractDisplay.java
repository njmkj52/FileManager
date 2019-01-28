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

    /**
     * 拡張子を取得
     * @param fileName 拡張子を含めたファイルの名前
     * @return 拡張子
     */
    protected String getExtension(String fileName) {
        if (fileName == null) {
            return "default";
        }
        // "."の位置を取得します。
        int lastDotPosition = fileName.lastIndexOf(".");
        // 存在する場合は、"."以降を返します。
        if (lastDotPosition != -1) {
            return fileName.substring(lastDotPosition + 1);
        }
        return "default";
    }
}
