package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity3 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ImageButton but = findViewById(R.id.imageButton1);
        ImageButton but3 = findViewById(R.id.imageButton38);


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {


                    return true;
                } else if (itemId == R.id.action_search) {
                    // Navigate to the Search activity or fragment
                    startActivity(new Intent(MainActivity3.this, SearchActivity.class));
                    return true;
                } else if (itemId == R.id.action_profile) {
                    // Navigate to the Profile activity or fragment
                    startActivity(new Intent(MainActivity3.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
    public void goTo(View view){
        Intent intent = new Intent(MainActivity3.this, PlayList.class);
        startActivity(intent);

    }
}
