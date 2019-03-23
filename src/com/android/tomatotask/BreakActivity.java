package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD
import android.annotation.SuppressLint;
=======
import java.util.Locale;

>>>>>>> second commit
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
<<<<<<< HEAD
	private boolean showShake = true;		//��
	private String showRing;					//����
	private Vibrator vibrator;
	MediaPlayer player;

	public BreakActivity() {
		// TODO �Զ����ɵĹ��캯�����
	}
=======
	private boolean showShake = true;			//��
	private String showRing;					//����
	private Vibrator vibrator;
	MediaPlayer player;
>>>>>>> second commit
	
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
<<<<<<< HEAD
		Log.v("MAIN", showRing+"-showRing");
		timeSpan = rest * 60 * 1000;
		breakProgressBar.setMax(rest*60);
		returnbButton.setOnClickListener(this);
		time = new TimeCount(timeSpan, 1000);		//����CountDownTimer����
=======
		timeSpan = rest * 60 * 1000;
		breakProgressBar.setMax(rest*60);
		returnbButton.setOnClickListener(this);
		time = new TimeCount(timeSpan, 1000);
>>>>>>> second commit
		time.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
<<<<<<< HEAD
		if (player != null) {
			if (player.isPlaying()) {
				player.stop();
			}
		}
		Log.v("MAIN", "Doing BreakActivity-onPause");
=======
		if (player != null&&player.isPlaying()) player.stop();
>>>>>>> second commit
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);		//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}
		/**
		 * ��ʱ������ʾ
		 */
<<<<<<< HEAD
		@SuppressLint("SimpleDateFormat")
		@Override
		public void onTick(long millisUntilFinished) {
			int nowprogress;
			String string = new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished));
=======
		@Override
		public void onTick(long millisUntilFinished) {
			int nowprogress;
			String string = new SimpleDateFormat("mm:ss",Locale.getDefault()).format(new Date(millisUntilFinished));
>>>>>>> second commit
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
<<<<<<< HEAD
				Log.v("MAIN", "Doing show ring");
=======
>>>>>>> second commit
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
<<<<<<< HEAD
					Log.v("MAIN", AudioManager.STREAM_RING +"");
=======
>>>>>>> second commit
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
<<<<<<< HEAD
=======
	
>>>>>>> second commit
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
<<<<<<< HEAD
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v("MAIN", "BreakActivity-onRestart");
=======
	
	@Override
	protected void onRestart() {
		super.onRestart();
>>>>>>> second commit
	}

	@Override
	protected void onResume() {
		super.onResume();
<<<<<<< HEAD
		Log.v("MAIN", "BreakActivity-onResume");
=======
>>>>>>> second commit
	}

	@Override
	protected void onStop() {
		super.onStop();
<<<<<<< HEAD
		Log.v("MAIN", "BreakActivity-onStop");
=======
>>>>>>> second commit
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
<<<<<<< HEAD
		Log.v("MAIN", "BreakActivity-onDestroy");
	}
}
=======
	}
}
>>>>>>> second commit
