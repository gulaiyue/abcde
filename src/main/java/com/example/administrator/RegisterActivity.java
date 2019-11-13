package com.example.administrator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    private EditText User;
    private EditText Password1;
    private EditText Password2;
    private String user=null;
    private String password=null;
    private String password2=null;
    private Button register;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        User=(EditText)findViewById(R.id.user);
        Password1=(EditText)findViewById(R.id.password1);
        Password2=(EditText)findViewById(R.id.password2);
        register=(Button)findViewById(R.id.register);
        cancel=(Button)findViewById(R.id.toReturn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=User.getText().toString();
                password=Password1.getText().toString();
                password2=Password2.getText().toString();
                if(user==null||user.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"请输入您注册的用户名",Toast.LENGTH_SHORT).show();
                }
                if(password==null||password.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }
                if(!password2.equals(password))
                {
                    Toast.makeText(getApplicationContext(),"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
                check_user();



            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void check_user() {
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

                    String sql = "SELECT * FROM users WHERE userId="+user;

                    ResultSet rs = statement.executeQuery(sql);
                    Log.d("FUCK","FUCK1");
                    Looper.prepare();
                    if(rs.next())
                    {
                        Toast.makeText(getApplicationContext(),"该账户已经注册过了！",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        String sql2="INSERT INTO users(userId,passWord) VALUES(?,?)";
                        try{
                            Log.d("FUCK","FUCK3");
                            preparedStatement=connection.prepareStatement(sql2);
                            preparedStatement.setString(1,user);
                            preparedStatement.setString(2,password);
                            preparedStatement.executeUpdate();
                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                            connection.close();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    Looper.loop();
                    /*if (rs.next())
                    {
                        if(rs.getString(1)==null)
                        {
                            Log.d("FUCK","FUCK2");
                            String sql2="INSERT INTO users(?,?)";
                            try{
                                Log.d("FUCK","FUCK3");
                                preparedStatement=connection.prepareStatement(sql2);
                                preparedStatement.setString(1,user);
                                preparedStatement.setString(2,password);
                                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"该账户已经注册过了！",Toast.LENGTH_SHORT).show();
                        }
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }).start();


    }
}

