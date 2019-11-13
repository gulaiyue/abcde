package com.example.administrator;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    private Button Reg;
    private Button Logg;
    private EditText User;
    private EditText Password;
    private String user;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Reg=(Button)findViewById(R.id.bt_fuck);
        Logg=(Button)findViewById(R.id.bt_log);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        Logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User=(EditText)findViewById(R.id.et_username);
                Password=(EditText)findViewById(R.id.et_password);
                user=User.getText().toString();
                password=Password.getText().toString();
                Check_Log();

            }
        });
    }
    private void Check_Log()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                LinktoMysql linktoMysql=new LinktoMysql();
                linktoMysql.linkMysql();
                Connection connection=linktoMysql.getConn();
                Statement statement=null;
                Log.d("FUCK","FUCK");
                String sql="SELECT passWord FROM users WHERE userId="+user;
                Log.d("FUCK",sql);
                try{


                    statement=connection.createStatement();
                    ResultSet resultSet=statement.executeQuery(sql);
                    Looper.prepare();
                    if(resultSet.next())
                    {
                        if(resultSet.getString("passWord").equals(password))
                        {
                            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Log.d("FUCK",resultSet.getString("passWord"));
                            Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"不存在该用户",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
