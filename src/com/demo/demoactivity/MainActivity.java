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
	/* scdard��·�� */
	private static final String FILE_PATH = new String("/sdcard/");
	
	/* �ļ��б� */
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
		Log.i("","MainActivity���������ˡ�");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//����ֹͣ
		//mp.stop();
		
		Log.i("","MainActivity����Resume״̬��");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		//����ֹͣ
		//mp.stop();
		Log.i("","MainActivity����Pause״̬��");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		//����ֹͣ
		
		mp.pause();
		Log.i("","MainActivity����Stop״̬��");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		mp=new MediaPlayer(); // ʵ����һ��MediaPlayer����
		
		Log.i("","MainActivity�����ˡ�");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ȡ��һ��Activity������������
		Intent intent=getIntent();
		System.out.println(intent.getStringExtra("act"));
		Bundle bundle=intent.getBundleExtra("bundle");
		System.out.println(bundle.getString("upwd"));
		Toast.makeText(getApplication(), "��ӭ "+bundle.getString("uname"), Toast.LENGTH_SHORT).show();
		
		btnPopup=(Button) this.findViewById(R.id.btnPopup);
		
		logo=(ImageView) this.findViewById(R.id.iv_Logo);
		
		Animation anim = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.all);
		this.logo.startAnimation(anim); // ��������,����ImageView�ؼ�
			 
		
		lvScdardfile=(ListView) this.findViewById(R.id.lv_scdardfile);
		/* �����Զ���ĺ�������ʾScdard�е��ļ��б�  */
		scdardfileList();
		
		btnPlay=(Button) this.findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//��ý���ļ�����
				//��̬��Դ��ȡ
				mp=MediaPlayer.create(MainActivity.this, R.raw.xslxsl);
				//�����ļ�����
				mp.start();
				
			}
		});
		
		btnMusic=(Button) this.findViewById(R.id.btnMusic);
		//�����ť�����ڲ���ķ�ʽ�����¼�����
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

	/* ��ȡscdard���ļ��б� */
	public void scdardfileList() {
		// ȡ��ָ��λ�õ��ļ�������ʾ�������б�
		File home = new File(FILE_PATH);
		if (home.listFiles(new FileFilter()).length > 0) {
			for (File file : home.listFiles(new FileFilter())) {
				fileList.add(file.getName());
			}
			
			//Adapter��Ϊ��SimpleAdapter��SimpleCursorAdapter
			//һ���������Ҫ�����Զ����Adapter������
			//ͨ��Adapter�����ݵ��ؼ���,��ʹListView�ؼ������Զ���Ĳ���filelist
			ArrayAdapter<String> fileListAdapter = new ArrayAdapter<String>(
					MainActivity.this, R.layout.filelist, fileList);
			//ʵ���б�������ʾ
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
		 textView.setText("����"); 
		 
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
   
		 //����PopupWindow��������ɵ�� 
		 popupWindow.setFocusable(true);
		 popupWindow.setTouchable(true);
		 popupWindow.setOutsideTouchable(true);
		 
		 // ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		 /*popupWindow.setBackgroundDrawable(getResources().getDrawable(
	                R.drawable.default_album));*/
		 popupWindow.setBackgroundDrawable(new BitmapDrawable());
		 
		 // ˢ��״̬ 
		 popupWindow.update();
		 //����PopupWindow�������嶯��Ч�� 
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

	 
	/* �ڲ���
	 * ��ť�¼����� */
	class buttonMusic implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent();
			intent.setClass(getApplicationContext(),MusicActivity.class);

			//��ʼ��ת �з���ֵ��д��
	        //������ʽ,�з���ֵ
	        //��һ������ intent
	        //�ڶ������� requestCode ������
	        startActivityForResult(intent,1);
	        
			//MainActivity����Stop״̬
		}
		
	}

	//��������Ϣ�ļ������ص�������
    //�������з�����Ϣ��
    //����Ҫ��requestCode�������ĸ����󷵻ص�
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode ==1){
            if(resultCode == RESULT_OK)
            {
            	//�Ȼ�ȡ���ص���Ϣ
                String str = data.getExtras().getString("backstr");
            	
            }else{
            	
            }
		}
	}
	
	
}

/* �ⲿ��
 * �����ļ����� */
class FileFilter implements FilenameFilter {
	//implements�̳У����಻���Ը��Ǹ���ķ����ͱ�������ʹ���ඨ���븸����ͬ�ı������ߺ�����Ҳ�ᱻ����ȡ������
	public boolean accept(File dir, String name) {
		// ���ﻹ��������������ʽ���ļ�
		return (name.endsWith(".mp3"))||(name.endsWith(".txt"))||(name.endsWith(".png"));
	}
}