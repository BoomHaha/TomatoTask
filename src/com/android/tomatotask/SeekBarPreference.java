package com.android.tomatotask;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class SeekBarPreference extends DialogPreference {
	private Context context;
	private SeekBar sensitivityLevel=null;
	private LinearLayout layout=null;

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		persistInt(10);
	}
	public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		//��Ӳ���
		layout = new LinearLayout(context);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));		//��������
		layout.setMinimumWidth(400);		//���ֵ���С���
		layout.setPadding(20, 20, 20, 20);		//�������ҵ�padding
		
		//���seekbar
		sensitivityLevel = new SeekBar(context);
		sensitivityLevel.setMax(100);		//���ֵ
		sensitivityLevel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));		//SeekBar�Ĳ�������
		sensitivityLevel.setProgress(getPersistedInt(10));		//����Ĭ��ֵ
		layout.addView(sensitivityLevel);		//��seekbar��ӵ�layout������
		builder.setView(layout);
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			persistInt(sensitivityLevel.getProgress());		//����SeekBar��ֵ
		}
		super.onDialogClosed(positiveResult);
	}
}
