package com.example.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testdemo.encryption.AESCode;
import com.example.testdemo.encryption.DES;



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

        if (v.getId() == R.id.btn_aes) {
            // aes 字符串加密解密测试
            AESTest(raw);
        } else if (v.getId() == R.id.btn_des) {
            // des 字符串加密解密测试
            DESTest(raw);
        }
    }

    private void DESTest(String raw) {
        String keyType="DES";
        String key="msdmsd0123456789";
        String aesStr = onEncodeData(keyType, key, raw);
        Log.d("asd", "AES加密后 = " + aesStr);
        String result = onDecodeData(keyType, key, aesStr);
        Log.d("asd", "AES解密后 = " + result);
        StringBuilder builder = new StringBuilder();
        builder.append("AES加密后 = " + aesStr + "\n")
                .append("AES解密后 = " + result + "\n");
        tv_des.setText(builder.toString());
    }

    private void AESTest(String raw) {
        String keyType="AES";
        String key="msdmsd0123456789";
        String aesStr = onEncodeData(keyType, key, raw);
        Log.d("asd", "AES加密后 = " + aesStr);
        String result = onDecodeData(keyType, key, aesStr);
        Log.d("asd", "AES解密后 = " + result);
        StringBuilder builder = new StringBuilder();
        builder.append("DES加密后 = " + aesStr + "\n")
                .append("DES解密后 = " + result + "\n");
        tv_des.setText(builder.toString());
    }

    public String onEncodeData(String encodeType, String encodeKey, String data) {
        String encodeResult = null;
        try {
            if(encodeType != null && encodeKey != null){
                if("AES".equalsIgnoreCase(encodeType)){				//AES加密算法
                    encodeResult = AESCode.encode(data, encodeKey);

                } else if("DES".equalsIgnoreCase(encodeType)){		//DES加密算法
                    DES des = new DES(encodeKey);
                    encodeResult = des.Encode(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("encode", "加密错误: " + e.getMessage());
        }
        return encodeResult;
    }

    public String onDecodeData(String encodeType, String encodeKey, String data) {
        String encodeResult = null;
        try {
            if(encodeType != null && encodeKey != null){
                if("AES".equalsIgnoreCase(encodeType)){				//AES加密算法
                    encodeResult = AESCode.decode(data, encodeKey);

                } else if("DES".equalsIgnoreCase(encodeType)){		//DES加密算法
                    DES des = new DES(encodeKey);
                    encodeResult = des.Decode(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("encode", "解密错误: " + e.getMessage());
        }
        return encodeResult;
    }

}
