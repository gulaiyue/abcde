package com.example.administrator.ShouYe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ShangPin_Xiangqing extends AppCompatActivity {
    ImageView pic;
    TextView tv_name;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        pic = findViewById(R.id.img_animal_detail);
        tv_name = findViewById(R.id.tv_name_detail);
        toolbar = findViewById(R.id.tool_bar_detail);
        fab = findViewById(R.id.fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("pic", R.drawable.a);
            String name = intent.getStringExtra("name");

            Glide.with(this).load(id).into(pic);
            tv_name.setText(name + name + name + name + name + name + name + name);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShangPin_Xiangqing.this, "FUCK", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

