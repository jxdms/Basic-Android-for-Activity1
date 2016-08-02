package com.demo.demoactivity;

import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	Button btnLogin;
	EditText etUserName,etPassWord;
	InputMethodManager imm;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.i("destroy","LoginActivity����");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Ĭ�ϵĲ����ļ�
		//setContentView(R.layout.activity_login);
		
		//�����µĲ����ļ�
		setContentView(R.layout.login);
		
		//ǿ��android����������̣���InputMethodManager����
		//�õ�InputMethodManager��ʵ�� 
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		//boolean isOpen=imm.isActive();//isOpen������true�����ʾ���뷨��
		
		//�û����ؼ�ע��
		etUserName=(EditText) this.findViewById(R.id.et_UserName);
		//����ؼ�ע��
		etPassWord=(EditText) this.findViewById(R.id.et_PassWord);
		
		//EditTextʼ�ղ������������
		//etUserName.setInputType(InputType.TYPE_NULL);
		
		/*
		etUserName.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_ENTER){
					
					//Javaԭ�����
					System.out.println("����etUserName����");
					
					etPassWord.setFocusable(true);
					etPassWord.requestFocus();
					
					return true;
				}
				return false;
			}
		});
		*/
		
		/*
		etUserName.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable edit) {
				// TODO Auto-generated method stub
				//edit  �������������������е���Ϣ 
			}

			@Override
			public void beforeTextChanged(CharSequence text, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				//text  ������иı�ǰ���ַ�����Ϣ  
				//start ������иı�ǰ���ַ�������ʼλ��  
				//count ������иı�ǰ����ַ����ı�����һ��Ϊ0  
				//after ������иı����ַ�������ʼλ�õ�ƫ����
			}

			@Override
			public void onTextChanged(CharSequence text, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				//text  ������иı����ַ�����Ϣ  
			    //start ������иı����ַ�������ʼλ��  
			    //before ������иı�ǰ���ַ�����λ�� Ĭ��Ϊ0  
				//count ������иı���һ�������ַ���������  
			}
			
		});
		*/
		
		//�ؼ���ע��
		btnLogin=(Button) this.findViewById(R.id.btnLogin);
		//��ť�ؼ��ļ���
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�����¼������¼�����
				
				//���������
				imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etPassWord.getWindowToken(), 0);
				
				String inputUserName=etUserName.getText().toString();
				String inputPassWord=etPassWord.getText().toString();
				
				//���������û���Ϊ��user������Ϊ��12345������ת��MainActivity����棻���򵯳���ʾ�������
				if((inputUserName.trim().equals("user"))&&(inputPassWord.trim().equals("12345")))
						{
					
					Toast.makeText(LoginActivity.this, "��¼�ɹ�����ӭ����", Toast.LENGTH_LONG).show();
					
					//�л�����һ��Activity����ת����һ��Activity
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					//Я������
					intent.putExtra("act", "Login");
					
					//ͨ��Bundle�������ݵ���һ��Activity
					Bundle bundle=new Bundle();
					bundle.putString("uname", inputUserName);
					bundle.putString("upwd", inputPassWord);
					intent.putExtra("bundle", bundle);
					//��ʼ��ת �޷���ֵ��д��
					startActivity(intent);
				
					//���ٵ�ǰ����Activity
					finish();
					
					//Activity֮���л���������������
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right); 
					
					//ǿ��ɱ����ǰ����
					killProcessActivity();
					}else{
					
					Toast.makeText(LoginActivity.this, "��¼ʧ�ܣ����������룡", Toast.LENGTH_SHORT).show();
					etUserName.setText("");//���������û�����������
					etPassWord.setText("");//�������������������
					etUserName.setFocusable(true);//ʹ�û����ؼ���ý���
					etUserName.requestFocus();//ʹ�û����ؼ����̻�ȡ����
					
					//��ʾ�����               
					//imm.showSoftInputFromInputMethod(etUserName.getWindowToken(), 0);
					
					//etUserName.setFocusableInTouchMode(true);
					//etUserName.requestFocusFromTouch();
				}
				
			}
			
		});
		
		
	}

	private void killProcessActivity(){
		//ɱ������PIDһ���Ľ��̣�������Щӵ����ͬUID��Ӧ�ã�ͳͳ���ᱻɱ����
		Process.killProcess(Process.myPid());
		System.exit(0);//ֻ��Ӱ�쵱ǰ�ĳ���
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Test--KeyEvent");
		
		if((event.getAction()==KeyEvent.ACTION_DOWN)&&(event.getRepeatCount() == 0)){
			switch (event.getKeyCode()) 
            {
			case KeyEvent.KEYCODE_ENTER:
				Log.i("KeyDown","----Enter Key");
			case KeyEvent.KEYCODE_BACK:
				if(event.isLongPress()) {
					Log.i("KeyBack","----LongPress");
					
					this.stopService(getIntent());    
					System.exit(0);    
					return true;    
				}else{
					Log.i("KeyBack","----is not LongPress");
					
					return false;    
				}    
			default:
				break;
            }
		}
		return super.dispatchKeyEvent(event);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	        System.out.println("���·��ؼ�");
	        return true;
	    } else if(keyCode == KeyEvent.KEYCODE_MENU) {
	        //���/���ز˵���
	    	return true;
	    } else if(keyCode == KeyEvent.KEYCODE_HOME) {
	        //����Home��Ϊϵͳ�����˴����ܲ�����Ҫ��дonAttachedToWindow()
	    }
		
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD); 
		
		super.onAttachedToWindow();
	}

}
