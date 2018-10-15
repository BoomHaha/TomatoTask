package com.android.tomatotask;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {
	private SeekBar sbTick;
	private SeekBar sbRest;
	private SeekBar sbLongRest;
	private TextView tvTick;		//�����µ��ı���
	private TextView tvRest;
	private TextView tvLongRest;
	private CheckBox chkShake;	//�����Ƿ���
	private CheckBox chkTick;		//������ģʽ��CheckBox����Ƿ���
	private View mMainView;		//ҳ�����岼��
	private SharedPreferences mySharedPreferences;
	private int tick, rest, longrest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.setting_layout,(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		Log.i("MAIN", "********SettingFragment********onCreate********");
		chkShake = (CheckBox) mMainView.findViewById(R.id.chkShake);
		chkTick = (CheckBox) mMainView.findViewById(R.id.chkTick);

		// ����ʱ������
		sbTick = (SeekBar) mMainView.findViewById(R.id.sbTick);
		sbRest = (SeekBar) mMainView.findViewById(R.id.sbRest);
		sbLongRest = (SeekBar) mMainView.findViewById(R.id.sbLongRest);
		tvTick = (TextView) mMainView.findViewById(R.id.tvTick);
		tvRest = (TextView) mMainView.findViewById(R.id.tvRest);
		tvLongRest = (TextView) mMainView.findViewById(R.id.tvLongRest);
		sbTick.setOnSeekBarChangeListener(new SeekBarListener(tvTick, 20, 5));
		sbRest.setOnSeekBarChangeListener(new SeekBarListener(tvRest, 1, 1));
		sbLongRest.setOnSeekBarChangeListener(new SeekBarListener(tvLongRest,5, 5));

		// ���ö�ȡ
		mySharedPreferences = getActivity().getSharedPreferences("test",Activity.MODE_PRIVATE);
		//process=��ʱ��-��ʼֵ��/����
		tick = (mySharedPreferences.getInt("tick", 20) - 20);
		rest = mySharedPreferences.getInt("rest", 1) - 1;
		longrest = (mySharedPreferences.getInt("longrest", 5) - 5);
		Log.i("MAIN", "********tick="+tick+"****rest="+rest+"****longrest="+longrest+"********");

		// �����д���ֻ��Ϊ�˴��������¼�
		sbTick.setProgress(tick + 1);
		sbRest.setProgress(rest + 1);
		sbLongRest.setProgress(longrest + 1);
		sbTick.setProgress(tick);
		sbRest.setProgress(rest);
		sbLongRest.setProgress(longrest);

		// �𶯺���������
		boolean showShake = mySharedPreferences.getBoolean("showshake", true);
		boolean showTick = mySharedPreferences.getBoolean("showtick", true);
		chkShake.setChecked(showShake);
		chkTick.setChecked(showTick);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.i("MAIN", "********SettingFragment********onCreateView********");
		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
			Log.i("MAIN","********SettingFragment********removeAllViewsInLayout!");
		}
		return mMainView;
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i("MAIN", "********SettingFragment********onStop********");
		// ���㹫ʽ��ʱ��=process*����+��ʼֵ
		int tick_Change = sbTick.getProgress() + 20;
		int rest_Change = sbRest.getProgress() + 1;
		int longrest_Change = sbLongRest.getProgress() + 5;
		boolean showShake = chkShake.isChecked();
		boolean showTick = chkTick.isChecked();
		if (tick+20 == tick_Change && rest+1 == rest_Change && longrest+5 == longrest_Change) return;

		// ��������иı䣬�������²���
		// ʵ����SharedPreferences���󣨵�һ����
		mySharedPreferences = getActivity().getSharedPreferences("test",Activity.MODE_PRIVATE);
		// ʵ����SharedPreferences.Editor���󣨵ڶ�����
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// ��putString�ķ�����������
		editor.putInt("tick", tick_Change);
		editor.putInt("rest", rest_Change);
		editor.putInt("longrest", longrest_Change);
		editor.putBoolean("showshake", showShake);
		editor.putBoolean("showtick", showTick);

		// �ύ��ǰ����
		editor.commit();

		// ��ʾ�ѱ���
		Toast.makeText(getActivity(), "�����ѱ��棡", Toast.LENGTH_SHORT).show();
		Log.i("MAIN", "********SettingFragment********�����ѱ��棡********");
	}

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
		private TextView textView;
		private int TickStep;
		private int StartTick;

		public SeekBarListener(TextView tv, int startTick, int tickStep) {
			textView = tv;
			TickStep = tickStep;
			StartTick = startTick;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
			// ʱ��=process*����+��ʼֵ
			int curTick = progress + StartTick;
			int remainder = curTick % TickStep;
			int halfStep = TickStep % 2 == 0 ? TickStep - TickStep % 2 : TickStep - TickStep % 2 + 1;
			if (remainder < halfStep) {
				curTick -= remainder;
			} else {
				curTick += (TickStep - remainder);
			}
			textView.setText(curTick + "min");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// ʱ��=process*����+��ʼֵ
			int progress = seekBar.getProgress();
			int curTick = progress + StartTick;
			int remainder = curTick % TickStep;
			int halfStep = TickStep % 2 == 0 ? TickStep - TickStep % 2 : TickStep - TickStep % 2 + 1;
			if (remainder < halfStep) {
				curTick -= remainder;
			} else {
				curTick += (TickStep - remainder);
			}
			seekBar.setProgress(curTick - StartTick);
			textView.setText(curTick + "min");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("MAIN", "********SettingFragment********onDestroy********");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i("MAIN", "********SettingFragment********onPause********");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("MAIN", "********SettingFragment********onResume********");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i("MAIN", "********SettingFragment********onStart********");
	}
}
