package com.angelo.androidprova.applet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

 
	
	public MyTextView(Context context) {
		super(context);
 
		// TODO Auto-generated constructor stub
	}
	public MyTextView(Context context, AttributeSet attrs)
	{
		  super(context, attrs);
		
	}
	
	@Override
	public void postInvalidate() {
		// TODO Auto-generated method stub
		super.postInvalidate();
		
	}
	
 
	
}
