/**
 * ģ�����ƣ�����ͳ��ģ��
<<<<<<< HEAD
 * Author��������
=======
>>>>>>> second commit
 * ģ�����ã�ͳ�ƽ����Ѿ���ɵ�������������ʷ��������
 * 
 */
package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Locale;
<<<<<<< HEAD
=======

>>>>>>> second commit
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> second commit
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TomatoFragment extends Fragment {
	private TextView tomatoTxtView,todayTomatoCountTextView,allTomatoCountTextView;
	private ImageView imageView;
	private View mMainView;
	private int todayTomatoCount;
	private int allTomatoCount;
<<<<<<< HEAD
	SharedPreferences myshPreferences;
=======
	private SharedPreferences myshPreferences;
>>>>>>> second commit
	int tick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.tomato_layout, (ViewGroup)getActivity().findViewById(R.id.viewpager),false);
		tomatoTxtView = (TextView)mMainView.findViewById(R.id.tomatoTxtView);
		todayTomatoCountTextView = (TextView)mMainView.findViewById(R.id.todayTomatoCount);
		allTomatoCountTextView = (TextView)mMainView.findViewById(R.id.allTomatoCount);
		imageView =  (ImageView)mMainView.findViewById(R.id.imageTomato);
		//�޸�����
		Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		tomatoTxtView.setTypeface(fontFace);
		todayTomatoCountTextView.setTypeface(fontFace);
		allTomatoCountTextView.setTypeface(fontFace);
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onCreate++++++++");
=======
>>>>>>> second commit
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onCreateView++++++++");
		ViewGroup p = (ViewGroup)mMainView.getParent();
		if (p!=null) {
			p.removeAllViewsInLayout();
			Log.i("MAIN", "++++++++TomatoFragment++++++++removeAllViewsInLayout!!!!!!!!");
		}
=======
		ViewGroup p = (ViewGroup)mMainView.getParent();
		if (p!=null) p.removeAllViewsInLayout();
>>>>>>> second commit
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onResume++++++++");
		//ʱ�����ڵĶ�ȡ�����ʱ�䲻�ǽ����ʱ�䣬���ý��շ�����Ϊ 0 
		// ���ö�ȡ
=======
		//ʱ�����ڵĶ�ȡ�����ʱ�䲻�ǽ����ʱ�䣬���ý��շ�����Ϊ 0
		//���ö�ȡ
>>>>>>> second commit
		SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("TomatoCount",Activity.MODE_PRIVATE);
		String dateStr = mySharedPreferences.getString("date", "2001-01-01");
		todayTomatoCount = mySharedPreferences.getInt("todayTomatoCount", 0);		//��ȡ�洢�Ľ��շ���ʱ��
		allTomatoCount = mySharedPreferences.getInt("allTomatoCount", 0);				//��ȡ�洢�ĺϼƷ���ʱ��
		String dateNowString = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).format(new java.util.Date());
		if (!dateStr.equals(dateNowString)) {			//�жϴ洢ʱ���Ƿ�͵�ǰʱ����ͬһ��
			todayTomatoCount=0;
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putInt("todayTomatoCount", todayTomatoCount);
			editor.commit();
		}
		todayTomatoCountTextView.setText("����:"+todayTomatoCount);
		allTomatoCountTextView.setText("�ܼ�:"+allTomatoCount);
<<<<<<< HEAD
		Log.v("MAIN", "----TomatoFragment---todayTomatoCount---------"+todayTomatoCount+"-------");
		Log.v("MAIN", "----TomatoFragment---allTomatoCount---------"+allTomatoCount+"-------");
		Log.v("MAIN", "----TomatoFragment---dateStr---------"+dateStr+"-------");
		//�޸������淬��ͼƬ
		myshPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		tick = Integer.parseInt(myshPreferences.getString("TomatoTime_value","25"));
=======
		//�޸������淬��ͼƬ
		myshPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		tick = Integer.parseInt(myshPreferences.getString("TomatoTime_value","25"));
		/**
		 * ����������Ϣʱ��Ĳ�ͬ�޸Ĳ�ͬ�ķ���ͼƬ
		 * */
>>>>>>> second commit
		switch (tick) {
		case 25:
			imageView.setImageResource(R.drawable.tomato_25);
			break;
		case 35:
			imageView.setImageResource(R.drawable.tomato_35);
			break;
		case 45:
			imageView.setImageResource(R.drawable.tomato_45);
			break;
		case 60:
			imageView.setImageResource(R.drawable.tomato_60);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onDestroy++++++++");
=======
>>>>>>> second commit
	}
	
	@Override
	public void onPause() {
		super.onPause();
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onPause++++++++");
=======
>>>>>>> second commit
	}
	
	@Override
	public void onStart() {
		super.onStart();
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onStart++++++++");
=======
>>>>>>> second commit
	}
	
	@Override
	public void onStop() {
		super.onStop();
<<<<<<< HEAD
		Log.i("MAIN", "++++++++TomatoFragment++++++++onStop++++++++");
=======
>>>>>>> second commit
	}
}
