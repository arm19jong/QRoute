package com.teamsmokeweed.qroute.genqr;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.squareup.otto.Subscribe;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.bar.App;
import com.teamsmokeweed.qroute.bar.BlackButtonClicked;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jongzazaal on 19/10/2559.
 */

public class GenQr2Activity extends AppCompatActivity {

    ImageView imageView;
    Button saveButton, shareButton;
    Bitmap bitmap;
    TextView home;
    private ShareButton shareButtonFB;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.gen_qr2);

        home = (TextView) findViewById(R.id.home);

        shareButtonFB = (ShareButton) findViewById(R.id.share_btn);
        shareButtonFB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                postPicture();
            }
        });

        imageView = (ImageView) findViewById(R.id.imgQrGen);
        saveButton = (Button) findViewById(R.id.save);
        shareButton = (Button) findViewById(R.id.shareB);

        Intent i = getIntent();
        final byte[] byteArray = getIntent().getByteArrayExtra("img");
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_SHORT).show();
                try {
                    // Save bitmap to storage

                    Date d = new Date();
                    String filename  = (String) DateFormat.format("hhmmss-MMddyyyy"
                            , d.getTime());
//                    File dir = new File(Environment.getExternalStorageDirectory()
//                            , "/Pictures/" + filename + ".png");
//                    File dir=new File(Environment.DIRECTORY_PICTURES, "/"
//                            + filename + ".png");


                    File pictureFolder = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                    );

                    //Toast.makeText(GenQr2Activity.this, "w55ww", Toast.LENGTH_SHORT).show();
                    File dirr = new File(pictureFolder, "/QRoute");
                    //dirr.mkdir();
                    if(!dirr.exists()){
                        //Toast.makeText(GenQr2Activity.this, "wwww", Toast.LENGTH_SHORT).show();
                        dirr.mkdir();
                    }


                    File dir = new File(pictureFolder,"/QRoute/"+ filename + ".png");


                    //if(!dir.exists()){
                    //    dir.mkdirs();
                    //}

                    FileOutputStream out = new FileOutputStream(dir);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    out.write(bos.toByteArray());

                    // Clear bitmap
                    bitmap.recycle();

                    // Update image to media system
                    Intent intent =
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(dir));
                    sendBroadcast(intent);
                    out.close();

                    Toast.makeText(getApplicationContext(), "Saved to: "+dir.toString()
                            , Toast.LENGTH_LONG).show();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(GenQr2Activity.this, "Shareee", Toast.LENGTH_SHORT).show();
                Bitmap b = bitmap;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/png");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                b.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        b, "Title", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Select"));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(0,returnIntent);
                finish();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        App.getBus().register(this); // Here we register this activity in bus.
    }


    @Override
    protected void onStop(){
        super.onStop();
        App.getBus().unregister(this); // Here we unregister this acitivity from the bus.
    }

    @Subscribe
    public void OnBackButtonClicked(BlackButtonClicked blackButtonClicked)
    {
        Intent returnIntent = new Intent();
        setResult(1,returnIntent);
        finish();
    }

    public void postPicture() {
        //check counter
        if(counter == 0) {
            //save the screenshot
            View rootView = findViewById(android.R.id.content).getRootView();
            rootView.setDrawingCacheEnabled(true);
            // creates immutable clone of image
            //image = Bitmap.createBitmap(rootView.getDrawingCache());
            final Bitmap image = bitmap;

//            image = BitmapFactory.decodeResource(GenQr2Activity.this.getResources(),
//                    R.mipmap.ic_launcher);
            // destroy
            rootView.destroyDrawingCache();

            //share dialog
            AlertDialog.Builder shareDialog = new AlertDialog.Builder(this);
            shareDialog.setTitle("Share QrCode");
            shareDialog.setMessage("Share image QrCode to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //share the image to Facebook
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                    shareButtonFB.setShareContent(content);
                    counter = 1;
                    shareButtonFB.performClick();
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            shareDialog.show();
        }
        else {
            counter = 0;
            shareButtonFB.setShareContent(null);
        }
    }

}
