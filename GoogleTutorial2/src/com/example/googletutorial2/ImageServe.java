package com.example.googletutorial2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.api.client.util.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class ImageServe extends Activity {

	private String imageKey;
	private Bitmap mImageBitmap;
	
	private Intent returnIntent = new Intent();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_image_serve);
	
		Intent intent = getIntent(); // recupero l'intent che ha generato
		// l'attività

		// dall'intent che ha generato l'attività estraggi l'elemento che si
		// chiama NOME_INTENT
		imageKey = intent.getStringExtra("imageKey");

		new ConnectionServe().execute();
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_serve, menu);
		return true;
	}

	@SuppressWarnings("rawtypes")
	private class ConnectionServe extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
				connect();
				return arg0;
	}
		
	private void connect() {	
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			
			imageKey = imageKey.replaceAll(" ", "");
			imageKey = imageKey.replaceAll("\r\n|\r|\n", "");
			imageKey = imageKey.replaceAll("<BlobKey:", "");
			imageKey = imageKey.replaceAll(">", "");
			
			HttpGet request = new HttpGet("http://10.0.2.2:8888/serve?blob-key=" + imageKey);
			HttpResponse response = client.execute(request);
			
			mImageBitmap = BitmapFactory.decodeStream((InputStream) response.getEntity().getContent());
			
			//Convert to byte array
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			 
			returnIntent.putExtra("byteArrayImage", byteArray);

			 setResult(RESULT_OK, returnIntent);
			
			 finish();
			
		} catch (ClientProtocolException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		} catch (IOException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
	}
	}
	
	
}
