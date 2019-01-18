package c.sra.filemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.List;

public class ListDisplay extends AbstractDisplay {
    private String path;
    private ListView listView;
    private List<String> listDirectory = new ArrayList<>();
    final String TAG = "FileManager";

    // 再生管理
    boolean isPlayback = false;

    public ListDisplay(String path, ListView listView) {
        this.path = path;
        this.listView = listView;
    }

    /**
     * AbstractDisplayをOverride
     */
    @Override
    public void makeList(Context context) {
        listDirectory.add(path);
        // リストビューに表示する要素を設定
        ArrayList<ListItem> listItems = new ArrayList<>();

        int m = 0;
        int n = 0;
        String[] fileName;
        String fullPath;

        // dirList.size() は動的変化あり注意
        while (listDirectory.size() > m) {
            Log.d(TAG, "listDirectory.size()=" + listDirectory.size() + ", m=" + m);

            // get(m) リスト内の指定された位置 m にある要素を返す
            File directory = new File(listDirectory.get(m));
            // java.io.File クラスのメソッド list()
            // 指定したディレクトリに含まれるファイル、ディレクトリの一覧を String 型の配列で返す。
            fileName = directory.list();

            n = 0;
            if (fileName != null) {
                while (fileName.length > n) {
                    String ext = getExtension(fileName[n]);
                    Bitmap bmp;
                    ListItem item;
                    fullPath = directory.getPath() + "/" + fileName[n];
                    listDirectory.add(fullPath);

                    //ファイルタイプを見て処理を変える
                    if (ext != null) {
                        switch (ext) {
                            case "mp3":
                                Log.d(TAG, "ext = mp3");
                                // mp3 ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_music);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            case "wma":
                                Log.d(TAG, "ext = wma");
                                // mp3 ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_music);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            case "wav":
                                Log.d(TAG, "ext = wav");
                                // mp3 ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_music);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            case "txt":
                                Log.d(TAG, "ext = txt");
                                // txt ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_text);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            case "pdf":
                                Log.d(TAG, "ext = pdf");
                                // txt ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_text);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            case "doc":
                                Log.d(TAG, "ext = doc");
                                // txt ファイルのときの処理
                                bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_text);
                                item = new ListItem(bmp, fileName[n], fullPath);
                                listItems.add(item);
                                break;

                            default:
                                Log.d(TAG, "ext = otherfile");

                                break;
                        }
                    } else {
                        Log.d(TAG, "ext = null");
                    }
                    n++;
                }
            }
            m++;
        }
        // 出力結果をリストビューに表示
        Log.d(TAG, "result output");
        ListAdapter adapter = new ListAdapter(context, R.layout.custom_list, listItems);

        Log.d(TAG, "add list");
        listView.setAdapter(adapter);
    }

    public String getExtension(String fileName) {
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

    /**
     * AbstractDisplayをOverride
     */
    @Override
    public void makeListener(final Context context) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // タップしたアイテムの取得
                ListView listView = (ListView) parent;
                ListItem item = (ListItem) listView.getItemAtPosition(position);  // ListItemにキャスト
                String title = item.getTitle();
                String ext = getExtension(title);

                if (ext != null) {
                    switch (ext) {
                        case "mp3":
                            Log.d(TAG, "ext = mp3");
                            // mp3 ファイルのときの処理
                            Intent intent = new Intent(context, AudioPlayerService.class);
                            intent.putExtra("KEY", title);
                            intent.putExtra("BOOL", isPlayback);
                            // Serviceの開始
                            context.startService(intent);
                            if (isPlayback) {
                                isPlayback = false;
                            } else {
                                isPlayback = true;
                            }
                            break;

                        case "txt":
                            // txt ファイルのときの処理
                            Log.d(TAG, "ext = txt");

                            String content = "";
                            try {
                                content = readTextFromFileAll(item.getPath());
                            } catch (IOException e) {
                                Log.d(TAG, "can not file read");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(title);
                            builder.setMessage(content);
                            builder.show();
                            break;

                        default:
                            Log.d(TAG, "ext = other file");
                            break;
                    }
                }

            }
        });
    }

    public static String readTextFromFileAll(String path) throws IOException {
        File f = new File(path);
        byte[] data = new byte[(int) f.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        bis.read(data);
        bis.close();
        String fs = new String(data, "Shift-JIS");
        return fs;
    }

    /**
     * AbstractDisplayをOverride
     */
    @Override
    public void clearList(final Context context) {

    }
}
