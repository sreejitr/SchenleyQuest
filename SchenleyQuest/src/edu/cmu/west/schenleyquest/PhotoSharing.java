package edu.cmu.west.schenleyquest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import com.facebook.Session.StatusCallback;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class PhotoSharing extends Activity {
    
    private UiLifecycleHelper uiHelper;
    boolean called_once = false;

    String mCurrentPhotoPath;
    String imageFileName;
    File photoFile;

    static final int REQUEST_TAKE_PHOTO = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_photo_sharing);
        
        StatusCallback callback = null;
        
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        
        dispatchTakePictureIntent();
                  
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo_sharing, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
        if (requestCode == REQUEST_TAKE_PHOTO)
        {
            //If either photo was not clicked or not saved,
            //or if Facebook app is not present or photo sharing is not allowed
            //then kill this activity and return to Questions activity
            boolean photoSharingAllowed =
FacebookDialog.canPresentShareDialog(this,FacebookDialog.ShareDialogFeature.PHOTOS);
            
            if(photoFile==null || photoFile.length()<10000 ||
photoSharingAllowed==false)
            {
                //Kill this activity and return to questions view
                this.finish();
            }
            
            if(photoFile!=null && photoFile.length()>10000 &&
called_once==false)
            {
                Bitmap myBitmap =
BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ImageView img = (ImageView) findViewById(R.id.imageCapture);
                img.setImageBitmap(myBitmap);
                called_once=true;                   
            }

        }
        
        uiHelper.onActivityResult(requestCode, resultCode, data, new
FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall,
Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s",
error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall
pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    private FacebookDialog.PhotoShareDialogBuilder
createShareDialogBuilderForPhoto(Bitmap[] photos) {
        return new FacebookDialog.PhotoShareDialogBuilder(this)
                .addPhotos(Arrays.asList(photos));
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();          
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new
SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }        

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new
Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) !=
null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent,
REQUEST_TAKE_PHOTO);                                                          
 

            }
        }
    }    

    public void shareButtonClick(View view) {      
        

        Bitmap[] myBitmap = new Bitmap[1];
        myBitmap[0]  =
BitmapFactory.decodeFile(photoFile.getAbsolutePath());  
        FacebookDialog shareDialog =
createShareDialogBuilderForPhoto(myBitmap)
                .build();        
        uiHelper.trackPendingDialogCall(shareDialog.present());            
                
        
    }

}