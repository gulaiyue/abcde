package com.example.administrator.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.LinktoMysql;
import com.example.administrator.PersonalcenterActivity;
import com.example.administrator.R;
import com.example.administrator.RegisterActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FaBu extends AppCompatActivity {
    private EditText name;
    private EditText introduce;
    private EditText price;
    private Button fabu;
    private Button image_fabu;
    private ImageView image;
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private Bitmap bitmap;
    private Blob blob;

    Intent intent2 = new Intent(Intent.ACTION_VIEW);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu);
        name=(EditText)findViewById(R.id.m1_title);
        introduce=(EditText)findViewById(R.id.m1_nr);
        price=(EditText)findViewById(R.id.m1_price);
        fabu=(Button) findViewById(R.id.fabu);
        image=(ImageView)findViewById(R.id.m1_image);
        image_fabu=(Button)findViewById(R.id.fabu_image);
        image_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists())
                        outputImage.delete();
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24)
                {

                    imageUri= FileProvider.getUriForFile(FaBu.this,"com.example.administrator.my.FaBu.fileprovider",outputImage);

                }
                else {  intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent2.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        image.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    void Insert(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                LinktoMysql linktoMysql = new LinktoMysql();
                linktoMysql.linkMysql();
                linktoMysql.Creat_Table();
                try {
                    Connection connection = linktoMysql.getConn();
                    Statement statement;
                    PreparedStatement preparedStatement;
                    statement = connection.createStatement();
                    Looper.prepare();
                    String sql2="INSERT INTO things(name,image,introduce,price) VALUES(?,?,?,?)";
                    try{
                        preparedStatement=connection.prepareStatement(sql2);
                        preparedStatement.setString(1,name.getText().toString());
                        preparedStatement.setBinaryStream(2,Bitmap2IS(bitmap));
                        preparedStatement.setString(3,introduce.getText().toString());
                        preparedStatement.setInt(4,Integer.parseInt(price.getText().toString()));
                        preparedStatement.executeUpdate();
                        Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                        connection.close();
                        Intent intent=new Intent(FaBu.this,PersonalcenterActivity.class);
                        startActivity(intent);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    Looper.loop();


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }).start();



    }
    private InputStream  Bitmap2IS(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        return sbs;
    }
}

