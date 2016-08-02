package com.demo.demoactivity;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MusicActivity extends ListActivity {
	/* scdard的路径 */
	private static final String Music_PATH = new String("/sdcard/music/");
	
	/* 音乐列表 */
	private List<String> musicList = new ArrayList<String>();

	MediaPlayer mp=null;
	
	private int currentPosition;//当前音乐播放的进度
	private int currentItem = 0; // 当前播放歌曲的索引
	
	private Boolean isClickPlay=false;//是否点击了“播放”按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		//setContentView(R.layout.test);
		
		audioList();
		
		mp= new MediaPlayer(); // 实例化一个MediaPlayer对象
		
		Button play = (Button) findViewById(R.id.play); // 获取“播放”按钮
		Button pause = (Button) findViewById(R.id.pause); // 获取“暂停/继续”按钮
		
		//抽屉：播放界面
		final ImageButton buttonPause=(ImageButton) findViewById(R.id.buttonPause);
		final ImageButton buttonPlay=(ImageButton) findViewById(R.id.buttonPlay);
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isClickPlay){
					/* 开始播放 */
					mp.start();
					
				}else{
					playMusic(Music_PATH + musicList.get(currentItem));//调用本地SDCARD上的多媒体资源
					isClickPlay=true;
					
				}
				buttonPlay.setVisibility(View.GONE);
				buttonPause.setVisibility(View.VISIBLE);
			}
		});
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isClickPlay){
					/* 开始播放 */
					mp.start();
					
				}else{
					playMusic(Music_PATH + musicList.get(currentItem));//调用本地SDCARD上的多媒体资源
					isClickPlay=true;
					
				}
				buttonPlay.setVisibility(View.GONE);
				buttonPause.setVisibility(View.VISIBLE);
			}
		});
		
		pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp.isPlaying()) {
					/* 暂停 */
					mp.pause();
					
					buttonPlay.setVisibility(View.VISIBLE);
					buttonPause.setVisibility(View.GONE);
				} else {
					/* 开始播放 */
					mp.start();
					
					buttonPlay.setVisibility(View.GONE);
					buttonPause.setVisibility(View.VISIBLE);
				}
				
			}
		});
		
		buttonPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp.isPlaying()) {
					/* 暂停 */
					mp.pause();
					
					buttonPlay.setVisibility(View.VISIBLE);
					buttonPause.setVisibility(View.GONE);
				} else {
					/* 开始播放 */
					mp.start();
					
					buttonPlay.setVisibility(View.GONE);
					buttonPause.setVisibility(View.VISIBLE);
				}
				
			}
		});
		
		ImageButton btnBack=(ImageButton) this.findViewById(R.id.yybtn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				backResultForActivity();
			}
		});
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		currentItem=position;
		
		//super.onListItemClick(l, v, position, id);
		
	}

	private void audioList() {
		
		// 取得指定位置的音频文件设置显示到播放列表
		File home = new File(Music_PATH);
		if (home.listFiles(new Mp3Filter()).length > 0) {
			for (File file : home.listFiles(new Mp3Filter())) {
				musicList.add(file.getName());
			}
					
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MusicActivity.this,
				android.R.layout.simple_list_item_1, musicList); // 创建一个适配器
			setListAdapter(adapter); // 将适配器与ListView关联
		}
	}
	
	private void playMusic(String path) {
		try {
			/* 重置MediaPlayer */
			mp.reset();
			/* 设置要播放的文件的路径 */
			mp.setDataSource(path);
			/* 准备播放 */
			mp.prepare();
			/* 开始播放 */
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
					// 播放完成一首之后进行下一首
					nextMusic();
					
				}
			});
		} catch (IOException e) {
		}
	}
	
	/* 下一首 */
	private void nextMusic() {
		if (++currentItem >= musicList.size()) {
			currentItem = 0;
		} else {
			
			playMusic(Music_PATH + musicList.get(currentItem));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music, menu);
		return true;
	}

	private void backResultForActivity(){
		//存储返回数据 也要用Intent
		
		//设置返回数据
        //先设置ResultCode,再设置存储数据的意图
        Intent in = new Intent();

        setResult(RESULT_OK,in.putExtra("backstr","back"));

        finish();
		
	}
}
/* 外部类
 * 过滤音频文件类型 */
class Mp3Filter implements FilenameFilter {
	//implements继承，子类不可以覆盖父类的方法和变量。即使子类定义与父类相同的变量或者函数，也会被父类取代掉。
	public boolean accept(File dir, String name) {
		// 这里还可以设置其他格式的文件
		return (name.endsWith(".mp3"));
	}
}