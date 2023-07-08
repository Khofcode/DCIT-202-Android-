package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.Nullable;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
public class MainActivity2 extends AppCompatActivity {

    private TextView tt;

    private ImageButton previousButton;
    private ImageButton nextButton;
    private ImageButton addsButton;
    private boolean isPlaying = false;
    private ArrayList<String> mp3Files;
    private int currentIndex = 0;
    private ImageButton playButton;

    // Singleton instance of MediaPlayer
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playButton = findViewById(R.id.play);
        previousButton = findViewById(R.id.button3);
        nextButton = findViewById(R.id.next);
        addsButton = findViewById(R.id.adds);
        tt = findViewById(R.id.text45);
        ImageView imageView = findViewById(R.id.imageView);

        mp3Files = getIntent().getStringArrayListExtra("mp3Files");
        currentIndex = getIntent().getIntExtra("currentIndex", 0);

        String filePath = mp3Files.get(currentIndex);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // Handle playback completion if needed
                }
            });
        } else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSong = mp3Files.get(currentIndex);

                int num = saveSong(currentSong);
                if (num == 1) {
                    Toast.makeText(getApplicationContext(), "Song Added To PlayList", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Add To PlayList", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    public void playMusic() {
        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
            playButton.setImageResource(R.drawable.pause);
        }
    }

    public void pauseMusic(View view) {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            playButton.setImageResource(R.drawable.play);
        } else {
            playMusic();
            isPlaying = true;
            playButton.setImageResource(R.drawable.pause);
        }
    }

    public void playPrevious(View view) {
        if (currentIndex > 0) {
            mediaPlayer.stop();
            currentIndex--;
            String filePath = mp3Files.get(currentIndex);
            playMusicFromFilePath(filePath);
        }
    }

    public void playNext(View view) {
        if (currentIndex < mp3Files.size() - 1) {
            mediaPlayer.stop();
            currentIndex++;
            String filePath = mp3Files.get(currentIndex);
            playMusicFromFilePath(filePath);
        }
    }

    private void playMusicFromFilePath(String filePath) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int saveSong(String filePath) {
        if (mp3Files != null && !mp3Files.isEmpty()) {
            File directory = new File(getExternalFilesDir(null), "Songs");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File sourceFile = new File(filePath);
            String fileName = sourceFile.getName();
            File destination = new File(directory, fileName);

            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                FileOutputStream fileOutputStream = new FileOutputStream(destination);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                fileInputStream.close();

                mp3Files.add(destination.getAbsolutePath());

                return 1; // Successful saving
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0; // Saving failed
    }


}
