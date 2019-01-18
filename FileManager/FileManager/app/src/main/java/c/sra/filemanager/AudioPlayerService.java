package c.sra.filemanager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AudioPlayerService extends Service{
    private MediaPlayer mediaPlayer;
    boolean isPlayback;
    String fileName;
    String TAG = "FileManager";


    @Override
    public void onCreate() {
        super.onCreate();
        isPlayback = false;
        Log.d(TAG, "AudioPlayerService: onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "AudioPlayerService: onStartCommand()");

        fileName = intent.getStringExtra("KEY");
        isPlayback = intent.getBooleanExtra("BOOL", false);

        if (isPlayback) {
            audioStop();
        } else {
            audioPlay();
        }
        return START_REDELIVER_INTENT; // 再起動する
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isPlayback = false;

        Log.d(TAG, "AudioPlayerService: onDestroy()");

        if(mediaPlayer != null){
            Log.d(TAG,"end of audio");
            audioStop();
        }
        // Service終了
        stopSelf();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private boolean audioSetup(){
        boolean fileCheck = false;

        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        String filePath = Environment.getExternalStorageDirectory()
                + "/Music/" + fileName;
        Log.d(TAG, "AudioPlayerService: " + filePath);

        // assetsから mp3 ファイルを読み込み
        try{
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(filePath);
            //setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }

    private void audioPlay() {

        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (!audioSetup()){
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            // 繰り返し再生する場合
            mediaPlayer.stop();
            isPlayback = false;
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();
        }

        // 再生する
        Log.d(TAG, "AudioPlayerService: Playback");
        mediaPlayer.start();
        isPlayback = true;
        // 終了を検知するリスナー
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG,"end of audio");
                audioStop();
            }
        });
    }

    private void audioStop() {
        // 再生終了
        mediaPlayer.stop();
        isPlayback = false;
        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();

        mediaPlayer = null;
    }
}
