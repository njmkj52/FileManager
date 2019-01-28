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
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                        android.R.drawable.ic_menu_help);
                switch (ext) {
                    case FILETYPE_MP3:
                    case FILETYPE_mp3:
                    case FILETYPE_WMA:
                    case FILETYPE_wma:
                    case FILETYPE_WAV:
                    case FILETYPE_wav:
                        Log.d(TAG, "music file add");
                        isItemAdded = true;
                        bmp = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.icon_music);
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

