package com.example.myapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.R;
import com.example.myapp.api.Api;
import com.example.myapp.api.ApiConfig;
import com.example.myapp.api.JunCallback;
import com.example.myapp.entity.LoginResponse;
import com.example.myapp.util.StringUtils;
import com.google.gson.Gson;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.HashMap;

public class LoginActivity extends BaseActivity {


    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString().trim();   //调用输入框中的文本, trim可以去除空格   trim -> 修剪, 整修
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    private void login(String account, String pwd) {

        if(StringUtils.isEmpty(account)){
            showToast("请输入账号");
            return;
        } if(StringUtils.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("mobile" ,account);
        params.put("password" ,pwd);
        Api.config(ApiConfig.LOGIN,params).postRequest(new JunCallback() {
            @Override
            public void onSuccess(final String res) {
                 Gson gson =new Gson();
                LoginResponse loginResponse =gson.fromJson(res,LoginResponse.class);

                if(loginResponse.getCode() == 0){
                    String token =loginResponse.getToken();
                    saveStringToSp("token",token);    //本地保存token
                    navigateTo(HomeActivity.class);
                    showToastSync("登录成功");

                } else
                    showToastSync("登录失败");
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}