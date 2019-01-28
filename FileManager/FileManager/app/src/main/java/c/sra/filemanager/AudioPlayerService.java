package c.sra.filemanager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
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
    private String filePath;
    private String beforeFilePath;
    private String TAG = "FileManager";

    @Override
    public void onCreate() {
        super.onCreate();
        isPlayback = false;
        Log.d(TAG, "AudioPlayerService: onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "AudioPlayerService: onStartCommand()");
        filePath = intent.getStringExtra("KEY");

        if (isPlayback && filePath.equals(beforeFilePath)) {
            audioStop();
        } else {
            if (isPlayback) {
                audioStop();
            }
            audioPlay();
            beforeFilePath = filePath;
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

    private void audioPlay() {

        Boolean fileCheck = false;
        mediaPlayer = new MediaPlayer();

        Log.d(TAG, "AudioPlayerService: " + filePath);

        // mediaPlayerにファイルをセット
        fileCheck = audioFileSetup(filePath);
        if (!fileCheck){
            Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
            return;
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

    private boolean audioFileSetup(String filePath){
        boolean fileCheck = false;
        try{
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }

}
