package com.xiaowenshuma.code;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    Button scanner_qr, creater_qr;
    ImageView iv;
    EditText et_qr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {

        scanner_qr = (Button) findViewById(R.id.scanner_qr);
        et_qr = (EditText) findViewById(R.id.et_qr);
        creater_qr = (Button) findViewById(R.id.creater_qr);
        iv = (ImageView) findViewById(R.id.iv);

        scanner_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inten = new Intent(MainActivity.this, ScannerActivity.class);
                //startActivity(inten);
                startActivityForResult(inten,1);
            }
        });

        creater_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_qr.getText().toString().trim();
                if (str.isEmpty()) {
                    Toast.makeText(MainActivity.this, "没有输入字符串", Toast.LENGTH_SHORT).show();
                } else {
                   Bitmap qrCodeBitmap = createQRCodeBitmap(str);
                    iv.setImageBitmap(qrCodeBitmap);



                    }}});


    }
            @Override
            //重写活动获得结果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){//分别处理不同的请求码
            case 1:
                if(resultCode==RESULT_OK){  //根据其他活动发送的结果码来获取数据
                    String returnData=data.getStringExtra("fasong");
                    et_qr.setText(returnData);

                }
        }
    }


    /**
     * 创建QR二维码图片方法
     */

    private Bitmap createQRCodeBitmap(String url) {
        // 用于设置QR二维码参数
        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别——这里选择最高H级别
        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 设定二维码里面的内容，这里我采用我博客的地址
        //   String cont = "http://blog.csdn.net/fengltxx";

        // 生成QR二维码数据——这里只是得到一个由true和false组成的数组
        // 参数顺序分别为：编码内容url地址，编码类型，生成图片宽度，生成图片高度，设置参数
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
                    BarcodeFormat.QR_CODE, 180, 180, qrParam);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;// 黑色
                    else
                        data[y * w + x] = -1;// -1 相当于0xffffffff 白色
                }
            }

            // 创建一张bitmap图片，采用最高的效果显示
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            // 将上面的二维码颜色数组传入，生成图片颜色
            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    }
