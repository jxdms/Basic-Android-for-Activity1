package com.demo.demoactivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/* scdard的路径 */
	private static final String FILE_PATH = new String("/sdcard/");
	
	/* 文件列表 */
	private List<String> fileList = new ArrayList<String>();
	
	ImageView logo;
	Button btnPlay,btnMusic,btnPopup;
	ListView lvScdardfile;
	
	MediaPlayer mp=null;
	
	private String[] filename;
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		mp.start();
		Log.i("","MainActivity重新启动了。");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//音乐停止
		//mp.stop();
		
		Log.i("","MainActivity进入Resume状态。");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		//音乐停止
		//mp.stop();
		Log.i("","MainActivity进入Pause状态。");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		//音乐停止
		
		mp.pause();
		Log.i("","MainActivity进入Stop状态。");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		mp=new MediaPlayer(); // 实例化一个MediaPlayer对象
		
		Log.i("","MainActivity启动了。");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//获取上一个Activity传递来的数据
		Intent intent=getIntent();
		System.out.println(intent.getStringExtra("act"));
		Bundle bundle=intent.getBundleExtra("bundle");
		System.out.println(bundle.getString("upwd"));
		Toast.makeText(getApplication(), "欢迎 "+bundle.getString("uname"), Toast.LENGTH_SHORT).show();
		
		btnPopup=(Button) this.findViewById(R.id.btnPopup);
		
		logo=(ImageView) this.findViewById(R.id.iv_Logo);
		
		Animation anim = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.all);
		this.logo.startAnimation(anim); // 启动动画,给予ImageView控件
			 
		
		lvScdardfile=(ListView) this.findViewById(R.id.lv_scdardfile);
		/* 调用自定义的函数：显示Scdard中的文件列表  */
		scdardfileList();
		
		btnPlay=(Button) this.findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//多媒体文件创建
				//静态资源读取
				mp=MediaPlayer.create(MainActivity.this, R.raw.xslxsl);
				//音乐文件播放
				mp.start();
				
			}
		});
		
		btnMusic=(Button) this.findViewById(R.id.btnMusic);
		//这个按钮采用内部类的方式进行事件监听
		buttonMusic btn=new buttonMusic();
		btnMusic.setOnClickListener(btn);
		
		btnPopup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				initPopWindow();
			}
		});
	}

	/* 读取scdard的文件列表 */
	public void scdardfileList() {
		// 取得指定位置的文件设置显示到播放列表
		File home = new File(FILE_PATH);
		if (home.listFiles(new FileFilter()).length > 0) {
			for (File file : home.listFiles(new FileFilter())) {
				fileList.add(file.getName());
			}
			
			//Adapter分为：SimpleAdapter和SimpleCursorAdapter
			//一般情况都需要创建自定义的Adapter适配器
			//通过Adapter绑定数据到控件上,并使ListView控件关联自定义的布局filelist
			ArrayAdapter<String> fileListAdapter = new ArrayAdapter<String>(
					MainActivity.this, R.layout.filelist, fileList);
			//实现列表数据显示
			lvScdardfile.setAdapter(fileListAdapter);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 private void initPopWindow(){  
		 View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupwindow, null);  
		 contentView.setBackgroundColor(Color.BLUE);  
 
		 final PopupWindow popupWindow = new PopupWindow(findViewById(R.id.mainLayout), 320, 500);  
		 popupWindow.setContentView(contentView);  
 
		 TextView textView = (TextView) contentView.findViewById(R.id.tvText);  
		 textView.setText("测试"); 
		 
		 Button btnHide=(Button) contentView.findViewById(R.id.buttonHide);
		 btnHide.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(MainActivity.this, "button is pressed",
                        Toast.LENGTH_SHORT).show();
				
				popupWindow.dismiss();
			}
			 
		 });
		 
		 openDir();  
		 ListView listView = (ListView) contentView.findViewById(R.id.lvList);  
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filename);  
		 listView.setAdapter(adapter);  
   
		 //设置PopupWindow弹出窗体可点击 
		 popupWindow.setFocusable(true);
		 popupWindow.setTouchable(true);
		 popupWindow.setOutsideTouchable(true);
		 
		 // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		 /*popupWindow.setBackgroundDrawable(getResources().getDrawable(
	                R.drawable.default_album));*/
		 popupWindow.setBackgroundDrawable(new BitmapDrawable());
		 
		 // 刷新状态 
		 popupWindow.update();
		 //设置PopupWindow弹出窗体动画效果 
		 popupWindow.setAnimationStyle(R.anim.push_up_in);
		 
		 //popupWindow.showAsDropDown(btnPopup);
		 popupWindow.showAtLocation(btnPopup, Gravity.CENTER, 0, 0);
		 
		 /*
		 int[] location = new int[2];
		 btnPopup.getLocationOnScreen(location); 
		 popupWindow.showAtLocation(btnPopup, Gravity.NO_GRAVITY,location[0],location[1]-popupWindow.getHeight());
		 */
	 }
	
	 private void openDir() {  
		 String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();  
		 File file  = new File(rootPath);  
		 File[] files = file.listFiles();  
		 filename = new String[files.length];  
		 for(int i=0;i<files.length;i++){  
			 filename[i]=files[i].getName();  
			 System.out.println(filename[i]);  
		 }  
	}  

	 
	/* 内部类
	 * 按钮事件监听 */
	class buttonMusic implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent();
			intent.setClass(getApplicationContext(),MusicActivity.class);

			//开始跳转 有返回值的写法
	        //启动方式,有返回值
	        //第一个参数 intent
	        //第二个参数 requestCode 请求码
	        startActivityForResult(intent,1);
	        
			//MainActivity进入Stop状态
		}
		
	}

	//处理返回信息的监听（回调方法）
    //监听所有返回信息的
    //必须要有requestCode区分由哪个请求返回的
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode ==1){
            if(resultCode == RESULT_OK)
            {
            	//先获取返回的信息
                String str = data.getExtras().getString("backstr");
            	
            }else{
            	
            }
		}
	}
	
	
}

/* 外部类
 * 过滤文件类型 */
class FileFilter implements FilenameFilter {
	//implements继承，子类不可以覆盖父类的方法和变量。即使子类定义与父类相同的变量或者函数，也会被父类取代掉。
	public boolean accept(File dir, String name) {
		// 这里还可以设置其他格式的文件
		return (name.endsWith(".mp3"))||(name.endsWith(".txt"))||(name.endsWith(".png"));
	}
}