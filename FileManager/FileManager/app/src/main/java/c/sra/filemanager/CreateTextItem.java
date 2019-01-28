package c.sra.filemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateTextItem extends AbstractDisplay {

    private final String FILETYPE_TXT = "TXT";
    private final String FILETYPE_txt = "txt";
    private final String FILETYPE_PDF = "PDF";
    private final String FILETYPE_pdf = "pdf";
    private final String FILETYPE_DOC = "DOC";
    private final String FILETYPE_doc = "doc";

    private ListView listView;
    ArrayList<ListItem> listItems = new ArrayList<>();

    File mPath = new File(Environment.getExternalStorageDirectory() + "/");

    /**
     * コンストラクタ
     * @param listView
     */
    public CreateTextItem(ListView listView) {
        this.listView = listView;
    }

    /**
     * ListItemを返すだけ
     * @param context
     * @return
     */
    @Override
    public ArrayList<ListItem> makeListItem(Context context) {
        createList(context, mPath);
        return listItems;
    }


    /**
     * List作成
     * @param context
     * @param path
     */
    public void createList(Context context, File path) {
        File[] files = path.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            boolean isItemAdded = false;
            if (!f.exists()) {
                continue;
            } else if (f.isDirectory()) {
                createList(context, f);
            } else if (f.isFile()) {
                String ext = getExtension(f.getName());
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                        android.R.drawable.ic_menu_help);
                switch (ext) {
                    case FILETYPE_TXT:
                    case FILETYPE_txt:
                    case FILETYPE_PDF:
                    case FILETYPE_pdf:
                    case FILETYPE_DOC:
                    case FILETYPE_doc:
                        Log.d(TAG, "text file add");
                        isItemAdded = true;
                        bmp = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.icon_text);
                        break;

                    default:
                        break;
                }
                if (isItemAdded) {
                    ListItem item = new ListItem(bmp, f.getName(),
                            f.getParent() + "/" + f.getName());
                    listItems.add(item);
                }
            }
        }
        return;
    }

    /**
     * 拡張子を取得
     * @param fileName 拡張子を含めたファイルの名前
     * @return 拡張子
     */
    private String getExtension(String fileName) {
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

    /**
     * AbstractDisplayをOverride
     */
    @Override
    public void clearList(final Context context) {

    }

}
