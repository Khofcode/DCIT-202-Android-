package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayList extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private String directoryPath;
    private ListView listView;
   private ArrayList<String> audioFilesList;   private ArrayList<String> FilesList;
   private ArrayList<String> mp3Files;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        directoryPath = getExternalFilesDir(null) + "/Songs";

        ImageButton button = findViewById(R.id.back);
        listView = findViewById(R.id.mykl);
        mp3Files = new ArrayList<>();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteFile(position);
                return true;
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with searching MP3 files
            getAudioFilesFromDirectory();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayList.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String filePath = mp3Files.get(position);

                Intent intent = new Intent(PlayList.this, MainActivity2.class);
                intent.putExtra("mp3Files", mp3Files);
                intent.putExtra("currentIndex", position);
                startActivity(intent);
            }
        });
    }

    private void getAudioFilesFromDirectory() {
       audioFilesList = new ArrayList<>();
        FilesList= new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isAudioFile(file.getName())) {
                        mp3Files.add(file.getPath());
                        audioFilesList.add(file.getName());
                    }
                }
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, audioFilesList);
        listView.setAdapter(adapter);
    }
    private void deleteFile(int position) {
        if (position >= 0 && position < audioFilesList.size()) {
            String filePath = FilesList.get(position);
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    audioFilesList.remove(position);
                    FilesList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    private boolean isAudioFile(String fileName) {
        String extension = getFileExtension(fileName);
        return extension != null && (extension.equals("mp3") || extension.equals("wav") || extension.equals("ogg") || extension.equals("m4a"));
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }
}


