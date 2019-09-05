package com.example.entrydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.encrylibrary.encode.Base64Util;
import com.example.encrylibrary.oneway.MD5Util;
import com.example.encrylibrary.oneway.SHAUtil;
import com.example.encrylibrary.symmetric.AESUtil;
import com.example.encrylibrary.symmetric.DESUtil;
import com.example.encrylibrary.unsymmetric.RSAUtil;


import java.util.Map;

import javax.crypto.Cipher;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private final static String TAG = "MainActivity";
    private EditText et_raw;
    private TextView tv_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_raw = (EditText) findViewById(R.id.et_raw);
        tv_des = (TextView) findViewById(R.id.tv_des);
        findViewById(R.id.btn_md5).setOnClickListener(this);
        findViewById(R.id.btn_shal).setOnClickListener(this);
        findViewById(R.id.btn_base64).setOnClickListener(this);
        findViewById(R.id.btn_rsa).setOnClickListener(this);
        findViewById(R.id.btn_aes).setOnClickListener(this);
        findViewById(R.id.btn_des).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String raw = et_raw.getText().toString();
        if (raw == null || raw.length() <= 0) {
            Toast.makeText(this, "请输入待加密字符串", Toast.LENGTH_LONG).show();
            return;
        }

        if (v.getId() == R.id.btn_md5) {
            String enStr = MD5Util.md5(raw);
            tv_des.setText("MD5的加密结果是:" + enStr);
        }else if(v.getId() == R.id.btn_shal){
            String enStr = SHAUtil.sha(raw, null);
            tv_des.setText("shal的加密结果是:" + enStr);
        } else if (v.getId() == R.id.btn_base64){
            String enStr= Base64Util.base64EncodeStr(raw);
            String deStr=Base64Util.base64DecodedStr(enStr);
            String desc = String.format("base64加密结果是:%s\nbase64解密结果是:%s", enStr
                    ,deStr);
            tv_des.setText(desc);

        } else if (v.getId() == R.id.btn_rsa) {
            // rsa 字符串加密解密测试
            try {
                RSATest(raw);
            } catch (Exception e) {
                e.printStackTrace();
                tv_des.setText("RSA加密/解密失败");
            }
        } else if (v.getId() == R.id.btn_aes) {
            // aes 字符串加密解密测试
            AESTest(raw);
        } else if (v.getId() == R.id.btn_des) {
            // des 字符串加密解密测试
            DESTest(raw);
        }
    }

    private void DESTest(String raw) {
        String key = "1234567890123456";
        String aesStr = DESUtil.des(raw, key, Cipher.ENCRYPT_MODE);
        Log.d("asd","DES加密后 = " + aesStr);
        String result = DESUtil.des(aesStr, key, Cipher.DECRYPT_MODE);
        Log.d("asd","DES解密后 = " + result);
        StringBuilder builder=new StringBuilder();
        builder.append("DES加密后 = " + aesStr+"\n")
                .append("DES解密后 = " + result+"\n");
        tv_des.setText(builder.toString());
    }

    private void AESTest(String raw) {
        String key = "1234567890123456";
        String aesStr = AESUtil.aes(raw, key, Cipher.ENCRYPT_MODE);
        Log.d("asd","AES加密后 = " + aesStr);
        String result = AESUtil.aes(aesStr, key, Cipher.DECRYPT_MODE);
        Log.d("asd","AES解密后 = " + result);
        StringBuilder builder=new StringBuilder();
        builder.append("AES加密后 = " + aesStr+"\n")
                .append("AES解密后 = " + result+"\n");
        tv_des.setText(builder.toString());
    }


    private void RSATest(String s) throws Exception {
        StringBuilder builder=new StringBuilder();
        // des 字符串加密解密测试
        byte[] data = s.getBytes();
        // 密钥与数字签名获取
        Map<String, Object> keyMap = RSAUtil.getKeyPair();
        String publicKey = RSAUtil.getKey(keyMap, true);
        Log.d("asd","rsa获取公钥： " + publicKey);
        String privateKey = RSAUtil.getKey(keyMap, false);
        Log.d("asd","rsa获取私钥： " + privateKey);

        // 公钥加密私钥解密
        byte[] rsaPublic =
                RSAUtil.rsa(data, publicKey, RSAUtil.RSA_PUBLIC_ENCRYPT);
        Log.d("asd","rsa公钥加密： " + new String(rsaPublic));
        Log.d("asd","rsa私钥解密： " + new String(
                RSAUtil.rsa(rsaPublic, privateKey, RSAUtil.RSA_PRIVATE_DECRYPT)));

        // 私钥加密公钥解密
        byte[] rsaPrivate =
                RSAUtil.rsa(data, privateKey, RSAUtil.RSA_PRIVATE_ENCRYPT);
        Log.d("asd","rsa私钥加密： " + new String(rsaPrivate));
        Log.d("asd","rsa公钥解密： " + new String(
                RSAUtil.rsa(rsaPrivate, publicKey, RSAUtil.RSA_PUBLIC_DECRYPT)));

        // 私钥签名及公钥签名校验
        String signStr = RSAUtil.sign(rsaPrivate, privateKey);
        Log.d("asd","rsa数字签名生成： " + signStr);
        Log.d("asd","rsa数字签名校验： " + RSAUtil.verify(rsaPrivate, publicKey, signStr));


        builder.append("rsa获取公钥： " + publicKey+"\n")
                .append("rsa获取私钥： " + privateKey+"\n")
                .append("rsa公钥加密： " + new String(rsaPublic)+"\n")
                .append("rsa私钥解密： " + new String(
                        RSAUtil.rsa(rsaPublic, privateKey, RSAUtil.RSA_PRIVATE_DECRYPT))+"\n")
                .append("rsa私钥加密： " + new String(rsaPrivate)+"\n")
                .append("rsa公钥解密： " + new String(
                        RSAUtil.rsa(rsaPrivate, publicKey, RSAUtil.RSA_PUBLIC_DECRYPT))+"\n")
                .append("rsa数字签名生成： " + signStr+"\n")
                .append("rsa数字签名校验： " + RSAUtil.verify(rsaPrivate, publicKey, signStr));
        tv_des.setText(builder.toString());
    }

}
