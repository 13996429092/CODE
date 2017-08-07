package com.xiaowenshuma.code;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;

public class ScannerActivity extends AppCompatActivity implements OnDecodeCompletionListener {

    private ScannerView scannerView;
    private String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        init();
    }
    private void init() {

        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        //设置监听

        scannerView.setOnDecodeListener(this);

    }



    @Override
    public void onDecodeCompletion(String barcodeFormat, String barcode, Bitmap bitmap) {
        if (barcode == null || "".equals(barcode)) {

            AlertDialog builder = new AlertDialog.Builder(ScannerActivity.this).
                    setTitle("Error").setMessage("Not Found").show();
        } else {
            string = barcode.substring(barcode.indexOf("?") + 1, barcode.length());
            //mShowDialog(string);
            Intent intent=getIntent();//获取意向
            intent.putExtra("fasong",string);
            setResult(RESULT_OK,intent);//设置结果码
            finish();
        }

    }


    @Override
    protected void onResume() {

        super.onResume();
        scannerView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**/

    private void mShowDialog(final String url) {
        AlertDialog alertDialog = new AlertDialog.Builder(ScannerActivity.this).setTitle("The Result")
                .setMessage(url).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (url.substring(0, 4).equals("http")) {
                            OpenURL(url);

                        } else {

                        }
                    }

                }).show();
    }

    private void OpenURL (String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        finish();
    }
}
