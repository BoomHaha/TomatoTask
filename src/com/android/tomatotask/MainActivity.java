/*
 * MainActivity.java����Ҫ��android�ļ�
 * 1.��AndroidManifest.xml����������Ȼ�ᱨ��
 * 2.���ø��෽��
 * 3.������Ҫ�����ļ�
 * */

/*
 * ����Ŀ�����㣺1.�Զ����������ļ�
 * 			  2.ʵ����Բ�ι�����
 * */
package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

/*
 * MainActivity��Ϊ��Ҫ���࣬�̳�Activity����(extends)
 * ������ OnClickListener ��һ���ӿڣ�implements��ʾMainActivityʵ����OnClickListener�ӿڵ�ȫ������
 * OnClickListener�ӿ�ֻ��һ�����󷽷�   void onClick(View v);
 * */
public class MainActivity extends Activity implements OnClickListener {
	private CircleProgressBar progressBar;
	private TextView textView;
	private TimeCount time;
	protected Animation animation;
	private CharSequence[] arrCharSequences;
	private int timeSpan;
	private final String TAG = "MAIN";
	private int flag = 1;		//��־λ��1:����δ��ʼ  2:���������  3:������ɵ�δ��ʼ��Ϣ  4:��Ϣ��ʼ
	private int tick = 0;		//����ʱ�䣬��λ�Ƿ���
	private int rest = 0;		//��Ϣʱ�䣬��λ�Ƿ���
	private long exitTime = 0;
	private int[] ID;			//��Ŷ���
	private boolean showShake = true;	//��
	private String showRing;	//����
	private MediaPlayer player;
	private Vibrator vibrator;
	private SharedPreferences myshPreferences;

	/*
	 * @Override��ʾ��д���෽��onCreate
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		//���ȵ������̳и���ķ���onCreate
		setContentView(R.layout.activity_main);	//���ó�ʼ��Ļ����
		/*
		 * ��������assets�´���fonts�ļ��в����������ļ���ͬʱ�ṩ���·������Typeface����
		 * Typeface�еķ���createFromAsset������assets���ҵ��������Ͳ�������Ӧ�Ķ���
		 * ��ʹ���ⲿ����ȴ�ַ��������ޱ仯ʱ��ͨ������Ϊ������androidû��֧��
		 */
		Typeface fontFace = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
		/*
		 * txtView �� layout/activity_main.xml �������ϵġ���ʼ�����ֵ�����
		 * ���� R.java ����Ψһ��id
		 * this.findViewById ͨ��id�ҵ�����ֵ��textView����
		 * textView.setTypeface �����ı������壬����������assets
		 * */
		textView = (TextView) this.findViewById(R.id.txtView);
		textView.setTypeface(fontFace);
		
		progressBar = (CircleProgressBar) findViewById(R.id.circleProgressbar);
		//TomatoStart_String��һ���ַ������飬���汣����ǵ㡰��ʼ������ֵ�����
		arrCharSequences = getResources().getStringArray(R.array.TomatoStart_String);
		
		//���ö�ȡ
		ReadPreference();
		
		//����progressBar������
		progressBar.setMaxProgress(tick * 60);		//��λ����
		/*
		 * ��������ʵ��OnClickListener�ӿڲ���дonClick����
		 * �����صķ�����ʵ�ֵ������
		 * ע����������ʵ����OnClickListener�ӿ�
		 * */
		progressBar.setOnClickListener(this);

		//������Դ�ļ�
		ID = new int[] { R.anim.my_alpha_action, R.anim.my_scale_action, R.anim.my_rotate_action, R.anim.alpha_scale,R.anim.alpha_rotate, R.anim.scale_rotate, R.anim.alpha_scale_rotate, R.anim.myown_design };
		stateFlag();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (player != null&&player.isPlaying()) player.stop();
	}

	/**
	 * ��ȡ�����е����ã����ݱ����� /data/data
	 */
	private void ReadPreference() {
		/*
		 * ʹ��SharedPreferences��ȡ����
		 * https://www.cnblogs.com/penger/p/4143331.html
		 * ������  /data/data/<package name>/shared_prefs
		 * ��һ��������xml�ļ������֣��ڶ����������ļ�����ģʽ
		 * */
		myshPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		/**
		 * getString ��ȡ�ַ�������û�����½�һ��
		 * */
		tick = Integer.parseInt(myshPreferences.getString("TomatoTime_value","25"));
		rest = Integer.parseInt(myshPreferences.getString("BreakTime_value","25"));
		showShake = myshPreferences.getBoolean("startShake", false);
		showRing = myshPreferences.getString("pref_ringtone", "");
		//������ģʽ������ֵ��Ϊ����ִ��
		if (myshPreferences.getBoolean("developer_Mode", false)) {
			tick = 1;
			rest = 1;
		}
	}

	/**
	 * ÿ���1�룬CountDownTimer������onTick�ص�����ִ����Ӧ����
		������ʱ������CountDownTimer�����onFinish�ص�����ִ����Ӧ�Ĳ���
		https://blog.csdn.net/superxlcr/article/details/78996857
	 * */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);		//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ��������λ�Ǻ��룬���ø��෽������
		}
		/**
		 * ��ʱ���ʱ����
		 * ���񣺸ı����֣���ʾ����Ч�����𶯣������������޸����ݣ��ܷ�����+1�����շ�����+1��
		 */
		@Override
		public void onFinish() {
			switch (flag) {
				case 2:{		//2��ʾ������ʱ�䵽�ˣ�ת��3����Ϣ��
					flag = 3;
					//���Ӷ���Ч��
					textView.setText("�����Ϣ��");
					int randow = new Random().nextInt(8);
					animation = AnimationUtils.loadAnimation(MainActivity.this,ID[randow]);
					textView.startAnimation(animation);
					if (showShake) {
						/*
						 * https://blog.csdn.net/xiaojun111111/article/details/51220142
						 * */
						vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						long[] pattern = { 200, 500, 200, 500, 1200, 500, 200, 500 };
						vibrator.vibrate(pattern, -1);		//�ظ����������pattern�����ֻ����һ�Σ�index��Ϊ-1
					}
					//���������գ��򲥷�
					if (showRing != "") {
						Log.v(TAG, "-ShowRing-");
						//��������
						player = new MediaPlayer();
						/*
						 * URI https://blog.csdn.net/sunqiujing/article/details/75011871
						 * �����ǽ���������·��
						 * player.setDataSource ��������·��
						 * */
						Uri uri = Uri.parse(showRing);
						try {
							player.setDataSource(MainActivity.this, uri);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//ʵ����AudioManager����getSystemService(Context.AUDIO_SERVICE)
						AudioManager audioManager = (AudioManager) MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
						/*
						 * getStreamVolume ֻ�ܵõ�����stream)������
						 * AudioManager.STREAM_RING ��ʾ�绰��������
						 * ��������������Ϊ0
						 * */
						if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
							/**
							 * ���� MediaPlayer ��Ƶ��������Ϊ����
							 * ���� MediaPlayer ��ѭ������
							 * */
							player.setAudioStreamType(AudioManager.STREAM_RING);
							player.setLooping(false);
							try {
								player.prepare();
								player.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					/*
					 * ����һ����ΪTomatoCount�������ļ�
					 * �� shared_pref ��
					 * */
					SharedPreferences mySharedPreferences = getSharedPreferences("TomatoCount", Activity.MODE_PRIVATE);
					int todayTomatoCount = mySharedPreferences.getInt("todayTomatoCount", 0);
					int allTomatoCount = mySharedPreferences.getInt("allTomatoCount", 0);
					/**
					 * ��SimpleDateFormat�еĸ�ʽ����ʽ����ǰ��ʱ��
					 * ����Ϊ�ַ���
					 * */
					String dateNowString = (new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())).format(new Date());
					//ʵ����SharedPreferences.Editor����
					SharedPreferences.Editor editor = mySharedPreferences.edit();
					//��putString�ķ�����������
					todayTomatoCount++;
					allTomatoCount++;
					editor.putInt("todayTomatoCount", todayTomatoCount);
					editor.putInt("allTomatoCount", allTomatoCount);
					editor.putString("curDate", dateNowString);
					//�ύ��ǰ����
					editor.commit();
					Toast.makeText(MainActivity.this,getResources().getString(R.string.EndTask),Toast.LENGTH_LONG).show();
					break;
				}
			default:
				break;
			}
		}
		/*
		 * ÿ��ʱһ��ִ��
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			String string = new SimpleDateFormat("mm:ss",Locale.getDefault()).format(new Date(millisUntilFinished));
			textView.setText(string);
			//���ý��Ȳ��ػ�
			progressBar.setProgressNotInUiThread((int) (millisUntilFinished / 1000));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(MainActivity.this, SettingFragment.class);
			item.setIntent(intent);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (flag == 2) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
				alertBuilder
						.setTitle("������")
						.setMessage("�Ƿ����������Ѳ��˳���")
						.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										time.cancel();
										MainActivity.this.finish();
									}
								})
						.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										dialog.cancel();
									}
								}).create();
				alertBuilder.show();
			}
		}
		else {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "�ٰ�һ���˵�������",Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					time.cancel();
					finish();
					System.exit(0);
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		SharedPreferences mySharedPreferences = getSharedPreferences("test",Activity.MODE_PRIVATE);
		tick = mySharedPreferences.getInt("tick", 25);
		rest = mySharedPreferences.getInt("rest", 5);
		mySharedPreferences.getInt("longrest", 15);
		ReadPreference();
		progressBar.setMaxProgress(tick * 60);
		//�𶯺���������
		showShake = mySharedPreferences.getBoolean("showshake", true);
		if (flag == 4) {
			flag = 1;
			textView.setVisibility(View.VISIBLE);
			//���Ӷ���Ч��
			int randow = new Random().nextInt(8);
			animation = AnimationUtils.loadAnimation(MainActivity.this,ID[randow]);
			textView.startAnimation(animation);
			stateFlag();
		}
	}

	/*
	 * ���֮����¼������֮��Ķ���Ч����
	 * ����ͨ��id�ж��Ƿ�����Բ���������չʾ����Ч��
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.circleProgressbar:
			/**
			 * 2��4����Ч��1��3�������Ӧ��״̬
			 * */
			stateFlag();
			//���Ӷ���Ч��
			int randow = new Random().nextInt(8);
			animation = AnimationUtils.loadAnimation(this, ID[randow]);
			textView.startAnimation(animation);
			break;
		default:
			break;
		}
	}

	/**
	 * ����״̬��־�������1��ʼ���ѣ�ͬʱ��flagΪ2�������3����ʱ�䵽����ʼ��Ϣ��flag��Ϊ4
	 * ��ʵ��2��4ʱ�����Ч
	 */
	private void stateFlag() {
		switch (flag) {
		case 1:
			timeSpan = tick * 60 * 1000;
			time = new TimeCount(timeSpan, 1000);		//����CountDownTimer����
			//�����ȡarrays.xml�е����ݣ�����ʾ
			int randow = new Random().nextInt(arrCharSequences.length);
			Toast.makeText(MainActivity.this, arrCharSequences[randow],Toast.LENGTH_SHORT).show();
			flag = 2;	//����ʼ
			time.start();
			break;
		case 3:
			Intent intent = new Intent(getApplicationContext(),BreakActivity.class);
			intent.putExtra("rest", rest);
			intent.putExtra("showShake", showShake);
			intent.putExtra("showRing", showRing);
			flag = 4;		//��Ϣ��ʼ
			startActivity(intent);
			break;
		default:
			break;
		}
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
