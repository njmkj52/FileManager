package c.sra.filemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG = "FileManager";
    final static int REQUEST_PERMISSION = 1002;

    ArrayList<ListItem> listItem;
    ListView listView;

    private void setList() {
        AbstractDisplay dai = new CreateAudioItem(listView);
        AbstractDisplay dti = new CreateTextItem(listView);
        listItem = dai.display(this);
        listItem.addAll(dti.display(this));

        SetListView slv = new SetListView(listItem, listView);
        slv.setListener(this);
        slv.addListView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        if (Build.VERSION.SDK_INT >= 23) {
            // permissionの確認
            Log.d(TAG, "call checkPermission()");
            if (!checkPermission()) {
                Toast toast = Toast.makeText(this,
                        "ファイルのアクセス許可がありません。", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                setList();
            }
        }else {
            Log.d(TAG, "call dai.display()");
            setList();
        }
    }

    // パーミッション確認結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                Log.d(TAG, "call dai.display()");
                setList();
            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this,
                        "ファイルのアクセス許可が得られませんでした", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }
    }

    // オプションメニューの作成
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // オプションメニューの処理を追加
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // パーミッションの確認ｃ
    boolean checkPermission(){
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            // 拒否していた場合
            Log.d(TAG, "call requestLocationPermission()");
            if(requestLocationPermission()){
                return true;
            }else {
                return false;
            }
        }
    }

    // パーミッション許可を求める
    private boolean requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Log.d(TAG, "call ActivityCompat.requestPermissions()");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

            return true;
        } else {
            return false;
        }
    }
}
