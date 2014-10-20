package com.dev.white.mipolicianl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {
	
	Button logoff;
	TextView txthome;
	Button emergency;
	Button emergency2;

public static final String PREFS = "fuckPrefs";
    
public void onCreate(Bundle savedInstanceState) {
		 
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_home);    	
	  
    logoff= (Button) findViewById(R.id.logoffbutton);          
    txthome= (TextView) findViewById(R.id.hometxt);  
    emergency= (Button) findViewById(R.id.emergencybutton);
    emergency2= (Button) findViewById(R.id.howtobutton);
                  
    SharedPreferences fuckPrefs = getSharedPreferences(PREFS, 0);
    String userString = fuckPrefs.getString("keyUsuario", null);
    txthome.setText(userString);
            
    emergency.setOnClickListener(new View.OnClickListener() {
    	
public void onClick(View v) {
    	              
	startActivity(new Intent(Home.this, GPS.class));
			}
       });	       
            
    emergency2.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
    	       
	startActivity(new Intent(Home.this, GPS2.class));
			}
       });	   
	         		
	logoff.setOnClickListener(new View.OnClickListener() {
	         	
public void onClick(View view) {
    	        
	finish(); 
	      	}
	   });	               
    }
		
@Override
public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    
	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	return true;
	     	}
	return super.onKeyDown(keyCode, event);
	 		}	
	}