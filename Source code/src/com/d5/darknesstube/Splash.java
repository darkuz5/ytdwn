package com.d5.darknesstube; 

import yt.sdk.access.YTSDK; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Splash extends Activity {

	 private YTSDK ytsdk;
	 String idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        

		 Intent intent = getIntent();
		 String action = intent.getAction();
		 String type = intent.getType();

			YTSDKUtils.initilizeYTSDK(this);
			ytsdk = YTSDKUtils.getYTSDK();
		
		if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	            handleSendText(intent); // Handle text being sent
	        }  
	    } 
		
		Log.i("Accion ", action);
    }
	
	void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    String[] data = sharedText.split("/");
	    Integer lenght = data.length - 1;
	    String id = data[lenght];
	    if (sharedText != null) {
	        TextView texto = (TextView) findViewById(R.id.texto);
	        texto.setText(sharedText+ " El ID ES " + id);
	        idx = id;
	        
	        String urlImg = "https://i.ytimg.com/vi/"+id+"/hqdefault.jpg";
	        
	        ImageView imagenx = (ImageView) findViewById(R.id.imgInfo);

			new DownloadImageTask((ImageView) 
     			findViewById(R.id.imgInfo)).execute(urlImg);

			Display display = getWindowManager().getDefaultDisplay(); 
			int width = display.getWidth(); // ((display.getWidth()*20)/100)
			int alto=(int)((width * 360 ) / 480);
			int height = alto;// ((display.getHeight()*30)/100)
			LinearLayout.LayoutParams parmsx = new LinearLayout.LayoutParams(width,height);
			imagenx.setLayoutParams(parmsx);
			
			LinearLayout descargaVideo = (LinearLayout) findViewById(R.id.toHiddenOne);
			 descargaVideo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) { 
					String idnotanotas = "";
					try {
					idnotanotas = idx;
					} catch (Exception e){
						Log.i("ERRORR",e.toString());
					}
					//Log.i("descarga el id",idnotanotas);
					//ytsdk.setCustomDialogLayoutId(-1, -1);
					ytsdk.setVideoPreview(false);
					ytsdk.download(Splash.this, idnotanotas);
				}
				 
			 });
			
	    }
	    
	    
	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
