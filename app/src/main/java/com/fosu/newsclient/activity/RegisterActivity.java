package com.fosu.newsclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.User;
import com.fosu.newsclient.db.dao.UserDao;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/11.
 * 用户注册页面，主要用来处理用户注册请求，发送手机验证码
 */

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = "RegisterActivity";
    private static final String APPKEY = "17ccfa22b2003";
    private static final String APPSECRET = "4233a3ce5a09d48a703c057a49a3880e";

//    private static String APPKEY = "f3fc6baa9ac4";
//    private static String APPSECRET = "7f3dedcb36d92deebcb373af921d635a";


    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_username)
    @NotEmpty(message = "用户名不能为空")
    EditText etUsername;
    @BindView(R.id.et_password)
    @Password(message = "密码不能为空，且不小于6位")
    EditText etPassword;

    @BindView(R.id.btn_eye)
    Button btnEye;

    private boolean isVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }





    @OnClick({R.id.img_back, R.id.btn_next, R.id.btn_eye})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_next:
               // validator.validate();
               //验证成功
                String userName = etUsername.getText().toString().trim();
                String userPassword = etPassword.getText().toString().trim();

                User user = new User();
                user.setUserName(userName);
                user.setUserPassword(userPassword);

                UserDao userDao = new UserDao(RegisterActivity.this);
               // boolean result = userDao.addUser(user);
                boolean result=true;
                if (result) {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("user", user);
                   setResult(0x002, intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eye:
                // 设置密码框的密码是否可见
                if (isVisible) {
                    isVisible = false;
                    btnEye.setBackgroundResource(R.drawable.eye_unenable);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isVisible = true;
                    btnEye.setBackgroundResource(R.drawable.eye_enable);
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;

            default:
                break;
        }
    }

    private void validatePhone(String phone) {
        // 获取短信目前支持的国家列表，在监听中返回
//        SMSSDK.getSupportedCountries();
        // 请求获取短信验证码，在监听中返回
        SMSSDK.getVerificationCode("86", phone);

        // OnSendMessageHandler的定义如下，这个Handler的用途是在发送短信之前，
        // 开发者自己执行一个操作，来根据电话号码判断是否需要发送短信
//        SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
//            @Override
//            public boolean onSendMessage(String country, String phone) {
//                // 此方法在发送验证短信前被调用，传入参数为接收者号码
//                // 返回true表示此号码无须实际接收短信
//                LogUtil.i(TAG, "country:" + country + ", phone:" + phone);
//                return false;
//            }
//        });
    }




}
