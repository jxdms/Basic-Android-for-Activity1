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
	/* scdard��·�� */
	private static final String Music_PATH = new String("/sdcard/music/");
	
	/* �����б� */
	private List<String> musicList = new ArrayList<String>();

	MediaPlayer mp=null;
	
	private int currentPosition;//��ǰ���ֲ��ŵĽ���
	private int currentItem = 0; // ��ǰ���Ÿ���������
	
	private Boolean isClickPlay=false;//�Ƿ����ˡ����š���ť
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		//setContentView(R.layout.test);
		
		audioList();
		
		mp= new MediaPlayer(); // ʵ����һ��MediaPlayer����
		
		Button play = (Button) findViewById(R.id.play); // ��ȡ�����š���ť
		Button pause = (Button) findViewById(R.id.pause); // ��ȡ����ͣ/��������ť
		
		//���룺���Ž���
		final ImageButton buttonPause=(ImageButton) findViewById(R.id.buttonPause);
		final ImageButton buttonPlay=(ImageButton) findViewById(R.id.buttonPlay);
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isClickPlay){
					/* ��ʼ���� */
					mp.start();
					
				}else{
					playMusic(Music_PATH + musicList.get(currentItem));//���ñ���SDCARD�ϵĶ�ý����Դ
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
					/* ��ʼ���� */
					mp.start();
					
				}else{
					playMusic(Music_PATH + musicList.get(currentItem));//���ñ���SDCARD�ϵĶ�ý����Դ
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
					/* ��ͣ */
					mp.pause();
					
					buttonPlay.setVisibility(View.VISIBLE);
					buttonPause.setVisibility(View.GONE);
				} else {
					/* ��ʼ���� */
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
					/* ��ͣ */
					mp.pause();
					
					buttonPlay.setVisibility(View.VISIBLE);
					buttonPause.setVisibility(View.GONE);
				} else {
					/* ��ʼ���� */
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
		
		// ȡ��ָ��λ�õ���Ƶ�ļ�������ʾ�������б�
		File home = new File(Music_PATH);
		if (home.listFiles(new Mp3Filter()).length > 0) {
			for (File file : home.listFiles(new Mp3Filter())) {
				musicList.add(file.getName());
			}
					
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MusicActivity.this,
				android.R.layout.simple_list_item_1, musicList); // ����һ��������
			setListAdapter(adapter); // ����������ListView����
		}
	}
	
	private void playMusic(String path) {
		try {
			/* ����MediaPlayer */
			mp.reset();
			/* ����Ҫ���ŵ��ļ���·�� */
			mp.setDataSource(path);
			/* ׼������ */
			mp.prepare();
			/* ��ʼ���� */
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
					// �������һ��֮�������һ��
					nextMusic();
					
				}
			});
		} catch (IOException e) {
		}
	}
	
	/* ��һ�� */
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
		//�洢�������� ҲҪ��Intent
		
		//���÷�������
        //������ResultCode,�����ô洢���ݵ���ͼ
        Intent in = new Intent();

        setResult(RESULT_OK,in.putExtra("backstr","back"));

        finish();
		
	}
}
/* �ⲿ��
 * ������Ƶ�ļ����� */
class Mp3Filter implements FilenameFilter {
	//implements�̳У����಻���Ը��Ǹ���ķ����ͱ�������ʹ���ඨ���븸����ͬ�ı������ߺ�����Ҳ�ᱻ����ȡ������
	public boolean accept(File dir, String name) {
		// ���ﻹ��������������ʽ���ļ�
		return (name.endsWith(".mp3"));
	}
}