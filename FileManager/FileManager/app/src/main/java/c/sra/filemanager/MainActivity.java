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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "FileManager";
    final static int REQUEST_PERMISSION = 1002;

    File file = Environment.getExternalStorageDirectory();
    String storagePath = file.getPath();
    ListView listView;

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

                AbstractDisplay ld = new ListDisplay(storagePath, listView);
                ld.display(this);
            }
        }else {
            Log.d(TAG, "call ld.display()");

            AbstractDisplay ld = new ListDisplay(storagePath, listView);
            ld.display(this);
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
                                Log.d(TAG, "call ld.display()");

                AbstractDisplay ld = new ListDisplay(storagePath, listView);
                ld.display(this);
            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this,
                        "ファイルのアクセス許可が得られませんでした", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:

                AbstractDisplay ld = new ListDisplay(storagePath, listView);
                ld.clear(this);
                ld.display(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // パーミッションの確認
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

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return false;
        }
    }
}
