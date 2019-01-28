package c.sra.filemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SetListView {
    final String TAG = "FileManager";

    ArrayList<ListItem> listItem;
    ListView listView;

    private final String FILETYPE_MP3 = "MP3";
    private final String FILETYPE_mp3 = "mp3";
    private final String FILETYPE_TXT = "TXT";
    private final String FILETYPE_txt = "txt";

    ListAdapter adapter;

    /**
     * コンストラクタ
     * @param listItem
     * @param listView
     */
    public SetListView(ArrayList<ListItem> listItem, ListView listView) {
        this.listItem = listItem;
        this.listView = listView;
    }

    /**
     * ListViewに追加したListItemのListenerを作成
     * @param context コンテキスト
     * @return Listener
     */
    public void setListener(final Context context) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // タップしたアイテムの取得
                ListView listView = (ListView) parent;
                ListItem item = (ListItem) listView.getItemAtPosition(position);
                item.setHistory();
                String title = item.getTitle();
                String path = item.getPath();
                String ext = getExtension(title);
                if (ext != null){
                    switch (ext) {
                        case FILETYPE_MP3:
                        case FILETYPE_mp3:
                            // mp3 ファイルのときの処理
                            Intent intent = new Intent(context, AudioPlayerService.class);
                            intent.putExtra("KEY", path);
                            // Serviceの開始
                            context.startService(intent);
                            break;

                        case FILETYPE_TXT:
                        case FILETYPE_txt:
                            String content = "";
                            try {
                                content = readTextFromFileAll(path);
                            } catch (IOException e) {
                                Log.d(TAG, "can not file read." + e);
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(title);
                            builder.setMessage(content);
                            builder.show();
                            break;

                        default:
                            break;
                    }
                }
                // Viewの更新
                adapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * ListActivityに表示する
     * @param context
     */
    public void addListView(final Context context) {
        // ファイルをリストに追加
        adapter = new ListAdapter(context, R.layout.custom_list, listItem);
        listView.setAdapter(adapter);
    }

    private void history() {

    }

    /**
     * テキストファイルの中身を読み込む
     * @param path テキストファイルのパス
     * @return テキストファイルの中身
     * @throws IOException
     */
    private static String readTextFromFileAll(String path) throws IOException {
        File f = new File(path);
        byte[] data = new byte[(int) f.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        bis.read(data);
        bis.close();
        String fs = new String(data, "Shift-JIS");
        return fs;
    }

    /**
     * 拡張子を取得
     * @param fileName 拡張子を含めたファイルの名前
     * @return 拡張子
     */
    private String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        // "."の位置を取得します。
        int lastDotPosition = fileName.lastIndexOf(".");
        // 存在する場合は、"."以降を返します。
        if (lastDotPosition != -1) {
            return fileName.substring(lastDotPosition + 1);
        }
        return null;
    }

}
