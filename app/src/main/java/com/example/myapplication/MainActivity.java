package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mp3Files;
    private ArrayList<String> mp3Files1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        mp3Files = new ArrayList<>();
        mp3Files1 = new ArrayList<>();

        // Check if the READ_EXTERNAL_STORAGE permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with searching MP3 files
            searchMP3Files();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String filePath = mp3Files.get(position);

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);


                intent.putExtra("filePath", filePath);
                intent.putStringArrayListExtra("mp3Files", mp3Files);
                intent.putExtra("currentIndex", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with searching MP3 files
                searchMP3Files();
            } else {
                // Permission denied, handle it accordingly (e.g., show a message, disable functionality)
            }
        }
    }

    private void searchMP3Files() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = getContentResolver().query(uri, null, selection, null, sortOrder);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            while (cursor.moveToNext()) {
                if (columnIndex != -1) {
                    String filePath = cursor.getString(columnIndex);
                    mp3Files.add(filePath);
                    File file = new File(filePath);
                    String songName = file.getName();
                    mp3Files1.add(songName);
                }
            }
            cursor.close();

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mp3Files1);
            listView.setAdapter(adapter);
        }
    }
}
