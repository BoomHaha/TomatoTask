package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BreakActivity extends Activity implements OnClickListener {
	private TextView breakTextView;
	private ProgressBar breakProgressBar;
	private Button returnbButton;
	private int rest=0;
	private TimeCount time;
	private int timeSpan;
	private boolean showShake = true;			//��
	private String showRing;					//����
	private Vibrator vibrator;
	MediaPlayer player;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.break_layout);
		Log.v("MAIN", "BreakActivity-onCreate");
		breakProgressBar = (ProgressBar)findViewById(R.id.breakBar);
		breakTextView = (TextView)findViewById(R.id.breakTxtView);
		returnbButton = (Button)findViewById(R.id.ReturnButton);
		//�޸�����
		Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		breakTextView.setTypeface(fontFace);
		returnbButton.setTypeface(fontFace);
		Intent intent = getIntent();
		rest = intent.getIntExtra("rest", 5);
		showShake=intent.getBooleanExtra("showShake", true);
		showRing=intent.getStringExtra("showRing");
		timeSpan = rest * 60 * 1000;
		breakProgressBar.setMax(rest*60);
		returnbButton.setOnClickListener(this);
		time = new TimeCount(timeSpan, 1000);
		time.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (player != null&&player.isPlaying()) player.stop();
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);		//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}
		/**
		 * ��ʱ������ʾ
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			int nowprogress;
			String string = new SimpleDateFormat("mm:ss",Locale.getDefault()).format(new Date(millisUntilFinished));
			breakTextView.setText(string);
			nowprogress = (int)(rest*60-millisUntilFinished/1000);
			breakProgressBar.setProgress(nowprogress);
		}
		/**
		 * ��ʱ���ʱ����
		 */
		@Override
		public void onFinish() {
			if (showShake) {
				//������
				vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
				long [] pattern = {200,500,200,500,1200,500,200,500};		// ֹͣ ���� ֹͣ ����
				vibrator.vibrate(pattern,-1);           //�ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1   
			}
			if (showRing != "") {
				//��������
				player = new MediaPlayer();
				Uri uri = Uri.parse(showRing);
				try {
					player.setDataSource(BreakActivity.this, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				final AudioManager audioManager = (AudioManager) BreakActivity.this.getSystemService(Context.AUDIO_SERVICE);
				if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
					player.setAudioStreamType(AudioManager.STREAM_RING);
					player.setLooping(false);
					try {
						player.prepare();
					} catch (Exception e) {
						e.printStackTrace();
					}
					player.start();
				}
			}
			returnbButton.setVisibility(View.VISIBLE);
		}		
	}

	@Override
	public void onClick(View v) {
		finish();		//�رյ�ǰactivity ������һ��activity��activity��������activity
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BreakActivity.this);
			alertBuilder.setTitle("���ѣ�");
			alertBuilder.setMessage("������Ϣ������һ�����ѣ�");
			alertBuilder.setPositiveButton("����",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					time.cancel();
					BreakActivity.this.finish();
				}
			});
			alertBuilder.setNegativeButton("��Ϣ",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					dialog.cancel();
				}
			}).create();
			alertBuilder.show();
		}
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}