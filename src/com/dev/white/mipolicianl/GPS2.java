package com.dev.white.mipolicianl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GPS2 extends Activity {
	
	private TextView tvTest = null;
	private TextView tvUsuario = null;
	private TextView tvTelefono = null;
	private ProgressDialog pd = null;
	private Button btnConfirm = null;

public static final String PREFS = "fuckPrefs";
	  
@Override
protected void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_gps2);
		
	tvTest = (TextView)	this.findViewById(R.id.tvTest);
	tvUsuario = (TextView) findViewById(R.id.tvUsuario); 
	tvTelefono = (TextView) findViewById(R.id.tvTelefono);
	btnConfirm = (Button) findViewById(R.id.confirmbutton);
	pd = ProgressDialog.show(this, "Recopilando Información", "Espere Unos Momentos");
		
	btnConfirm.setOnClickListener(new View.OnClickListener() {
	         	
public void onClick(View v) {
  	          
	android.os.Process.killProcess(android.os.Process.myPid());
				}
          });	       
          
	configGPS(); 
				}

private void updateScreen(Location location) {
		
		 
	tvTest.setText("Latitude=" + String.valueOf(location.getLatitude()) + "\n" + "Longitude=" + String.valueOf(location.getLongitude()));						
		
	SharedPreferences fuckPrefs = getSharedPreferences(PREFS, 0);
    String userString = fuckPrefs.getString("keyUsuario", null);
    String userString2 = fuckPrefs.getString("keyPass", null);
    
    tvUsuario.setText(userString);
    tvTelefono.setText(userString2);
        
    String latitude = String.valueOf(location.getLongitude());
        
    String longitude = String.valueOf(location.getLatitude());
        
    HttpClient httpclient = new DefaultHttpClient();
	    
	HttpPost httppost = new HttpPost("http://mipolicianl.site88.net/receiver3.php");
	    
		try {
	    
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
	    
			nameValuePairs.add(new BasicNameValuePair("myHttpData", userString));
			nameValuePairs.add(new BasicNameValuePair("myHttpData2", userString2));
			nameValuePairs.add(new BasicNameValuePair("myHttpData3", latitude));
			nameValuePairs.add(new BasicNameValuePair("myHttpData4", longitude));
			nameValuePairs.add(new BasicNameValuePair("myHttpData5", "emergencia"));
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			@SuppressWarnings("unused")
			HttpResponse response = httpclient.execute(httppost);
					} 
			catch (ClientProtocolException e) {
					} 
			catch (IOException e) {
    				}
    		pd.dismiss();
    				}
     			{
     		}
			
private void configGPS() {
	
	LocationManager mLocationManager;
	LocationListener mLocationListener;
		
	mLocationManager = (LocationManager)
	getSystemService(Context.LOCATION_SERVICE);
		
	mLocationListener = new MyLocationListener();
		
	mLocationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener); 
					}

private class MyLocationListener implements LocationListener {

@Override
public void onLocationChanged(Location location) { 
					
	Log.d("GPS2", "Latitude=" + String.valueOf(location.getLatitude()));	
	Log.d("GPS2", "Longitude=" + String.valueOf(location.getLongitude())); 
			
	updateScreen(location);
					}

@Override
public void onProviderDisabled(String arg0) { 	
					}

@Override
public void onProviderEnabled(String arg0) { 
					}

@Override
public void onStatusChanged(String arg0, int arg1, Bundle arg2) {	
					}
			}
	
@Override
public boolean onKeyDown(int keyCode, KeyEvent event)  {
		     
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		return true;
		     		}
		return super.onKeyDown(keyCode, event);
		 			}	
			}