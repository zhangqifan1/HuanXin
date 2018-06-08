package com.as.huanxindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_info;
    private EditText user_name;
    private ImageView img_clear_phone;
    private EditText user_pwd;
    private TextView mRegister;
    private TextView mForget;
    private Button btn_login;
    private Button btn_clear;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_info = (TextView) findViewById(R.id.tv_info);
        user_name = (EditText) findViewById(R.id.user_name);
        img_clear_phone = (ImageView) findViewById(R.id.img_clear_phone);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        mRegister = (TextView) findViewById(R.id.mRegister);
        mForget = (TextView) findViewById(R.id.mForget);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        img_clear_phone.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if(s1.equals("") || s1==null){
                    img_clear_phone.setVisibility(View.GONE);
                }else{
                    img_clear_phone.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear_phone:
                user_name.setText("");
                break;
            case R.id.btn_login://登录
                submit();
                EaseUtil.login(MainActivity.this,ChatActivity.class,user_name.getText().toString(),user_pwd.getText().toString());
                break;
            case R.id.mRegister://注册
                submit();
                EaseUtil.registUser(MainActivity.this,user_name.getText().toString(),user_pwd.getText().toString());
                break;
            case R.id.btn_clear:
                EaseUtil.loginOut(MainActivity.this);
                break;
        }
    }

    private void submit() {
        // validate
        String name = user_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = user_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

    }


}
