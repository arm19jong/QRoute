package com.teamsmokeweed.qroute.readqr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.database.AddDatabaseQr;
import com.teamsmokeweed.qroute.genqr.DateQr;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by jongzazaal on 15/10/2559.
 */

public class ReadActivity  extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    private DateQr dateQr;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.read_qr);

//        Dexter.initialize(ReadActivity.this);
//        Dexter.checkPermission(new PermissionListener() {
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse response) {finish();}
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//
//                // ask permission once time
//                //token.cancelPermissionRequest();
//
//                // request permission when call method again
//                //token.continuePermissionRequest();
//
//
//
//
//            }
//        }, Manifest.permission.CAMERA);

        //setupToolbar();
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this){@Override
        protected IViewFinder createViewFinderView(Context context) {
            return new CustomViewFinderView(context);
        }};
        contentFrame.addView(mScannerView);



    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
//        Toast.makeText(this, "Contents = " + rawResult.getText() +
//                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();


        String[] sQr = rawResult.getText().split("#420#");
        //String part1 = parts[0];

        setDateQr(sQr);
        AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
        addDatabaseQr.addDb();


        Intent i = new Intent(getApplicationContext(), ResultReadQrActivity.class);
        i.putExtra("sQr", sQr);
        startActivity(i);

        //String[] parts = rawResult.getText().split(":");
        //String part1 = parts[1]; // 004



        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScannerView.resumeCameraPreview(ReadActivity.this);
//            }
//        }, 2000);
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "ZXing";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }
            //canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }

    private void setDateQr(String[] sQr){
        dateQr = new DateQr();

        dateQr.setTitles(sQr[2]);
        dateQr.setPlaceName(sQr[3]);
        dateQr.setPlaceType(sQr[4]);
        dateQr.setDes(sQr[5]);
        dateQr.setLat(Float.valueOf(sQr[0]));
        dateQr.setLng(Float.valueOf(sQr[1]));
        dateQr.setWebPage(sQr[6]);
    }
}
