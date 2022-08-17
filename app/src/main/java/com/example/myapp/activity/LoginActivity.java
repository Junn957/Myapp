package com.example.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.api.Api;
import com.example.myapp.api.ApiConfig;
import com.example.myapp.api.JunCallback;
import com.example.myapp.util.AppConfig;
import com.example.myapp.util.StringUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {


    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
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
        params.put("mobile",account);
        params.put("password",pwd);
        Api.config(ApiConfig.LOGIN,params).postRequest(new JunCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}