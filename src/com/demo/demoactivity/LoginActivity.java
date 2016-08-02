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
		
		Log.i("destroy","LoginActivity销毁");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//默认的布局文件
		//setContentView(R.layout.activity_login);
		
		//启用新的布局文件
		setContentView(R.layout.login);
		
		//强制android隐藏虚拟键盘，用InputMethodManager方法
		//得到InputMethodManager的实例 
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		//boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
		
		//用户名控件注册
		etUserName=(EditText) this.findViewById(R.id.et_UserName);
		//密码控件注册
		etPassWord=(EditText) this.findViewById(R.id.et_PassWord);
		
		//EditText始终不弹出软件键盘
		//etUserName.setInputType(InputType.TYPE_NULL);
		
		/*
		etUserName.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_ENTER){
					
					//Java原生输出
					System.out.println("测试etUserName按键");
					
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
				//edit  输入结束呈现在输入框中的信息 
			}

			@Override
			public void beforeTextChanged(CharSequence text, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				//text  输入框中改变前的字符串信息  
				//start 输入框中改变前的字符串的起始位置  
				//count 输入框中改变前后的字符串改变数量一般为0  
				//after 输入框中改变后的字符串与起始位置的偏移量
			}

			@Override
			public void onTextChanged(CharSequence text, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				//text  输入框中改变后的字符串信息  
			    //start 输入框中改变后的字符串的起始位置  
			    //before 输入框中改变前的字符串的位置 默认为0  
				//count 输入框中改变后的一共输入字符串的数量  
			}
			
		});
		*/
		
		//控件的注册
		btnLogin=(Button) this.findViewById(R.id.btnLogin);
		//按钮控件的监听
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//单击事件或点击事件操作
				
				//隐藏软键盘
				imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etPassWord.getWindowToken(), 0);
				
				String inputUserName=etUserName.getText().toString();
				String inputPassWord=etPassWord.getText().toString();
				
				//如果输入的用户名为：user；密码为：12345；正跳转到MainActivity活动界面；否则弹出提示输入错误
				if((inputUserName.trim().equals("user"))&&(inputPassWord.trim().equals("12345")))
						{
					
					Toast.makeText(LoginActivity.this, "登录成功，欢迎您！", Toast.LENGTH_LONG).show();
					
					//切换到另一个Activity或跳转到另一个Activity
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					//携带数据
					intent.putExtra("act", "Login");
					
					//通过Bundle传递数据到下一个Activity
					Bundle bundle=new Bundle();
					bundle.putString("uname", inputUserName);
					bundle.putString("upwd", inputPassWord);
					intent.putExtra("bundle", bundle);
					//开始跳转 无返回值的写法
					startActivity(intent);
				
					//销毁当前所在Activity
					finish();
					
					//Activity之间切换动画：往左拉推
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right); 
					
					//强制杀掉当前进程
					killProcessActivity();
					}else{
					
					Toast.makeText(LoginActivity.this, "登录失败！请重新输入！", Toast.LENGTH_SHORT).show();
					etUserName.setText("");//清空输入的用户名框中数据
					etPassWord.setText("");//清空输入的密码框中数据
					etUserName.setFocusable(true);//使用户名控件获得焦点
					etUserName.requestFocus();//使用户名控件立刻获取焦点
					
					//显示软键盘               
					//imm.showSoftInputFromInputMethod(etUserName.getWindowToken(), 0);
					
					//etUserName.setFocusableInTouchMode(true);
					//etUserName.requestFocusFromTouch();
				}
				
			}
			
		});
		
		
	}

	private void killProcessActivity(){
		//杀掉所有PID一样的进程，比如那些拥有相同UID的应用，统统都会被杀掉。
		Process.killProcess(Process.myPid());
		System.exit(0);//只会影响当前的程序
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
		
		if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	        System.out.println("按下返回键");
	        return true;
	    } else if(keyCode == KeyEvent.KEYCODE_MENU) {
	        //监控/拦截菜单键
	    	return true;
	    } else if(keyCode == KeyEvent.KEYCODE_HOME) {
	        //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
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
