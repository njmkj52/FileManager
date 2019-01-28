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

public class CreateAudioItem extends AbstractDisplay {

    private final String FILETYPE_MP3 = "MP3";
    private final String FILETYPE_mp3 = "mp3";
    private final String FILETYPE_WMA = "WMA";
    private final String FILETYPE_wma = "wma";
    private final String FILETYPE_WAV = "WAV";
    private final String FILETYPE_wav = "wav";

    private ListView listView;
    ArrayList<ListItem> listItems = new ArrayList<>();
    File mPath = new File(Environment.getExternalStorageDirectory() + "/");

    /**
     * コンストラクタ
     * @param listView
     */
    public CreateAudioItem( ListView listView) {
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
                    case FILETYPE_MP3:
                    case FILETYPE_mp3:
                    case FILETYPE_WMA:
                    case FILETYPE_wma:
                    case FILETYPE_WAV:
                    case FILETYPE_wav:
                        Log.d(TAG, "music file add");
                        isItemAdded = true;
                        bmpId = context.getResources().getIdentifier("icon_music",
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

