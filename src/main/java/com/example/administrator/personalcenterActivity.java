package com.example.administrator;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PersonalcenterActivity extends AppCompatActivity {
    private EditText person_name;
    private EditText person_subject;
    private EditText person_phone;
    private EditText person_qq;
    private EditText person_address;
    private Button button;
    private String name;
    private String subject;
    private String phone;
    private String qq;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalcenter);
        button=(Button)findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person_name=(EditText)findViewById(R.id.person_name);
                person_address=(EditText)findViewById(R.id.person_address);
                person_phone=(EditText)findViewById(R.id.person_phone);
                person_qq=(EditText)findViewById(R.id.person_qq);
                person_subject=(EditText)findViewById(R.id.person_subject);
                name=person_name.getText().toString();
                subject=person_subject.getText().toString();
                phone=person_phone.getText().toString();
                qq=person_qq.getText().toString();
                address=person_address.getText().toString();
                wsxx();

            }
        });

    }
    private void wsxx()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinktoMysql linktoMysql = new LinktoMysql();
                linktoMysql.linkMysql();Looper.prepare();
                try{
                    Connection connection = linktoMysql.getConn();

                    PreparedStatement preparedStatement;
                    String s="UPDATE users set name=?,subject=?,phone=?,qq=?,address=? WHERE userId=12345";

                    preparedStatement=connection.prepareStatement(s);
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2,subject);
                    preparedStatement.setString(3,phone);
                    preparedStatement.setString(4,qq);
                    preparedStatement.setString(5,address);
                    preparedStatement.executeUpdate();
                    Toast.makeText(getApplicationContext(),"信息完善成功",Toast.LENGTH_SHORT).show();
                    connection.close();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }Looper.loop();
            }

        }).start();
    }
}
