package com.example.googletutorial2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class PhotoActivity extends Activity {

	private ImageView mImageView;

	private String mCurrentPhotoPath;
	private String uploadUrl;
	private String imageKey;

	private Intent returnIntent = new Intent();
	private int takeCode = 110;
	
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//setContentView(R.layout.activity_photo);
	
		
	
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
		
		
		takePhoto(takeCode);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}
	
	
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}
	
	
	private File setUpPhotoFile() throws IOException {

		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	/** Called when the user clicks the Camera button */
	public void takePhoto(int Code) {
		// Do something in response to button
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File f = null;

		try {
			f = setUpPhotoFile();
			mCurrentPhotoPath = f.getAbsolutePath();
			takePictureIntent
					.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			e.printStackTrace();
			f = null;
			mCurrentPhotoPath = null;
		}

		// questo da il via ad un'attività da cui si aspetta un risultato
		startActivityForResult(takePictureIntent, Code);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			
				try {
					handleBigCameraPhoto();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	@SuppressWarnings("unchecked")
	private void setPic() throws Exception {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		
		ImageView image;
		
		mImageView.setImageBitmap(bitmap);

		mImageView.setVisibility(View.VISIBLE);

		// Carichiamo l'immagine sull'endpoint

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		byte[] bArray = bos.toByteArray();

		System.out.println("--------Send Get--------");
		// getInputStreamFromUrl("http://10.0.2.2:8888/get");
		new ConnectionGet().execute();
		
		new ConnectionPost().execute();
		System.out.println("--------Send Post--------");
		
	}
	


// Recupera l'indirizzo in cui caricare l'immagine con un GET
	@SuppressWarnings("rawtypes")
	private class ConnectionGet extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
				connect();
				return arg0;
	}
		
	private void connect() {	
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://10.0.2.2:8888/get");
			HttpResponse response = client.execute(request);
			
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			
			uploadUrl = content;
	       
		} catch (ClientProtocolException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		} catch (IOException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
	}
	}
	
	@SuppressWarnings("rawtypes")
	private class ConnectionPost extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
			post(uploadUrl, mCurrentPhotoPath);
				return arg0;
	}
		
	@SuppressWarnings("deprecation")
	public void post(String url, String filename) {
		
		HttpClient httpclient = new DefaultHttpClient();
		url = url.replaceAll("http://localhost:8888", "");
		url = url.replaceAll("\r\n|\r|\n", "");

		url = "http://10.0.2.2:8888" + url;
		HttpPost httppost = new HttpPost(url);

		try {
			MultipartEntityBuilder entity = MultipartEntityBuilder.create();
			final File file = new File(filename);
			FileBody fb = new FileBody(file);
			entity.addPart("myFile", fb);

			httppost.setEntity(entity.build());
			HttpResponse response = httpclient.execute(httppost);
			
			 HttpEntity imageKeyEntity = response.getEntity();
			 imageKey = EntityUtils.toString(imageKeyEntity);
			 
			 System.out.println(filename);			
			 System.out.println(imageKey);			
			 returnIntent.putExtra("localImagePath", filename);
			 returnIntent.putExtra("imageKey", imageKey);
			 
			 setResult(RESULT_OK, returnIntent);
			 
			 mCurrentPhotoPath = null;
			
			 finish();
			 
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}
	}
	
	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}
	
	@SuppressWarnings("unchecked")
	private void handleBigCameraPhoto() throws Exception {

		if (mCurrentPhotoPath != null) {
			//setPic();
			
			galleryAddPic();
			
			new ConnectionGet().execute();
			new ConnectionPost().execute();
			
			//new ConnectionServe().execute();
			
			//Thread.currentThread().sleep(5000);
			//mImageView2.setImageBitmap(mImageBitmap);
			//System.out.println("-------------contenuto3");
			//mImageView2.setVisibility(View.VISIBLE);
			//System.out.println("-------------contenuto4");
		}

	}
	
}
