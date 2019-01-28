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
                int bmpId = context.getResources().getIdentifier("ic_menu_help",
                        "drawable", context.getPackageName());
                switch (ext) {
                    case FILETYPE_TXT:
                    case FILETYPE_txt:
                    case FILETYPE_PDF:
                    case FILETYPE_pdf:
                    case FILETYPE_DOC:
                    case FILETYPE_doc:
                        Log.d(TAG, "text file add");
                        isItemAdded = true;
                        bmpId = context.getResources().getIdentifier("icon_text",
                                "drawable", context.getPackageName());
                        Log.d(TAG,"img = " + bmpId);
                        break;

                    default:
                        break;
                }
                if (isItemAdded) {
                    ListItem item = new ListItem(bmpId, f.getName(),
                            f.getParent() + "/" + f.getName(), ext);
                    listItems.add(item);
                    item.setThumbnailId(bmpId);
                }
            }
        }
        return;
    }

    /**
     * AbstractDisplayをOverride
     */
    @Override
    public void clearList(final Context context) {

    }

}
