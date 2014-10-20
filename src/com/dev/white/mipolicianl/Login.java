package com.dev.white.mipolicianl;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.dev.white.mipolicianl.library.Httppostaux;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

    EditText user;
    EditText pass;
    Button btnlogin;
    TextView registrar;
    Httppostaux post;
    
    String IP_Server="mipolicianl.site88.net";
    String URL_connect="http://"+IP_Server+"/acces.php";
  
    boolean result_back;
    private ProgressDialog pDialog;
    
public static final String PREFS = "fuckPrefs";
    
@Override
public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
        
    post=new Httppostaux();
        
    user= (EditText) findViewById(R.id.username);
    pass= (EditText) findViewById(R.id.celular);
    btnlogin= (Button) findViewById(R.id.acceptbutton);
    registrar=(TextView) findViewById(R.id.registerbutton);
        
    btnlogin.setOnClickListener(new View.OnClickListener() {

public void onClick(View view) {
				
    String usuario=user.getText().toString();
    String passw=pass.getText().toString();
    SharedPreferences fuckPrefs = getSharedPreferences(PREFS, 0);
    Editor editor = fuckPrefs.edit();
    editor.putString("keyUsuario", usuario);
    editor.putString("keyPass", passw);
    editor.commit();
        				
      if( checklogindata( usuario , passw )==true) {
        
    	  new asynclogin().execute(usuario,passw);        		                 		
        	}
      			else {
        			
      				err_login();
        	}
        }        								
    {  
    	
    registrar.setOnClickListener(new View.OnClickListener() {
            
public void onClick(View view) {
        		
    String url = "http://"+IP_Server+"/adduser.html";
    Intent i = new Intent(Intent.ACTION_VIEW);
  	i.setData(Uri.parse(url));
    startActivity(i);        		
        	}        	
        });        
    }
  
public void err_login() {
	
	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	vibrator.vibrate(200);
	Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Nombre o Celular Incorrectos", Toast.LENGTH_SHORT);
 	toast1.show();    	
    }
       
 public boolean loginstatus(String username ,String password ) {
    
	int logstatus=-1;
    	
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    postparameters2send.add(new BasicNameValuePair("usuario",username));
		    postparameters2send.add(new BasicNameValuePair("password",password));

        JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);
      		
		SystemClock.sleep(950);
		    		
		   if (jdata!=null && jdata.length() > 0) {

		    JSONObject json_data; 
				
		    	try {
		    		
					json_data = jdata.getJSONObject(0); 
					logstatus=json_data.getInt("logstatus"); 
					Log.e("loginstatus","logstatus= "+logstatus);
					
					} 
		    		
		    		catch (JSONException e) {
		    			
						e.printStackTrace();
					}		            	   
					
		    		 if (logstatus==0) {
		    			 
		    			 Log.e("loginstatus ", "invalido");
		    			 return false;
		    		}
		    		 
		    		 else {
		    			 
		    			 Log.e("loginstatus ", "valido");
		    			 return true;
		    		}
		    	}		 
		     		 else {	
		    			 Log.e("JSON  ", "ERROR");
			    		 return false;
			  }	
    	}
    
 public boolean checklogindata(String username ,String password ) {
    	
	 			if 	(username.equals("") || password.equals("")) {
	 			Log.e("Login ui", "checklogindata user or pass error");
	 			return false;
	 				}
	 			else {
	 				
	 			return true;
	 		  }
 		}           
   
 class asynclogin extends AsyncTask< String, String, String > {
    	 
    String user,pass;
    
    protected void onPreExecute() {
        	
        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Autenticando....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        
        }
 
    protected String doInBackground(String... params) {
			
    	user=params[0];
		pass=params[1];
            
    			if (loginstatus(user,pass)==true) {    		    		
    			return "ok"; 
    		 }
    			else {    		
    			return "err";      	          	  
    		 }	
		}
       
    protected void onPostExecute(String result) {

        pDialog.dismiss();
        Log.e("onPostExecute=",""+result);
           
           		if (result.equals("ok")) {
        	    Intent i=new Intent(Login.this, Home.class);
				i.putExtra("user",user);
				startActivity(i); 
             } 
           		else {
            	err_login(); 
           	}
          }
        }
     });
   }
}