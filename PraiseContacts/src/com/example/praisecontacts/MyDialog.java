package com.example.praisecontacts;


import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyDialog extends Dialog {
        public interface OnCustomDialogListener{
                public void back(int haveset);
        }
        
        private String name;
        
        private EditText phoneNumEdit;
        private OnCustomDialogListener customDialogListener;
       private EditText smsEditText;
        private TextView cancle,bound;
      private  Context context;
      private String phoneNum;
      
    
      
        public MyDialog(Context context,int theme,String name,OnCustomDialogListener customDialogListener) {
                super(context,theme);
                this.name = name;
                this.context=context;
                this.customDialogListener = customDialogListener;
               
        }
        
        @Override
        protected void onCreate(Bundle savedInstanceState) { 
                super.onCreate(savedInstanceState);
                setContentView(R.layout.forget_gesture_dialog);
                
               
                SharedPreferences phoneNumShare = context.getSharedPreferences("phoneNumShare",0);
        		 phoneNum=phoneNumShare.getString("PHONENUM", null);
                smsEditText=(EditText) findViewById(R.id.content_send);
                phoneNumEdit=(EditText) findViewById(R.id.to_phonenum);
                phoneNumEdit.setText(phoneNum);
                 cancle=(TextView) findViewById(R.id.cancle);
                 bound=(TextView) findViewById(R.id.bound);
                 bound.setOnClickListener(clickListener);
                 cancle.setOnClickListener(clickListener);
                 bound.setTextColor(0x7FFFFFFF);
                 bound.setEnabled(false);
                 cancle.setTextColor(0xff000000);
                 smsEditText.addTextChangedListener(new TextWatcher() {
                	
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						if(smsEditText.getText().toString().length() > 0){
							bound.setTextColor(0xFFFFFFFF);
							bound.setEnabled(true);
						} else {
							bound.setTextColor(0x7fffffff);
							bound.setEnabled(false);
						}
					}
				});
               // setTitle(name); 
             
                     }
        
        
      
        
        
        private View.OnClickListener clickListener = new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                	if(v.getId()==R.id.bound)
                	{	   SmsManager smsManager = SmsManager.getDefault();
                	
                		if(smsEditText.getText().toString().length()>70)
                		{	
                			
                			   List<String> contents = smsManager.divideMessage(smsEditText.getText().toString());
                               for(String sms : contents) {
                                   smsManager.sendTextMessage(phoneNum, null, sms, null, null);
                               }
                           }
                		else
                		{
                            smsManager.sendTextMessage(phoneNum, null, phoneNum, null, null);
                        }
                		MyDialog.this.dismiss();
                	}
                	else if(v.getId()==R.id.cancle)
                	{
                		smsEditText.setText("");
                		MyDialog.this.dismiss();
                	}
                }
        };
}
