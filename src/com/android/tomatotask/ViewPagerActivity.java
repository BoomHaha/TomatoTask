/**
 * ģ�����ƣ�Ӧ�ó������������
 * Author��HZ
 * ģ�鹦�ܣ�Ӧ�ó���������Ĵ��������
 */
package com.android.tomatotask;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

public class ViewPagerActivity extends FragmentActivity {
	private TomatoFragment tomatoFragment;
	private TaskFragment taskFragment;
	private SettingPreferenceFragment settingPreferenceFragment;
	private ViewPager m_vp;
	//ҳ���б�
	private ArrayList<Fragment> fragmentList;
	//�����б�
	ArrayList<String> titleList  = new ArrayList<String>();
	//ͨ��pagerTabStrip�������ñ��������
	private PagerTabStrip pagerTabStrip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		m_vp = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagertab);
		//�����»��ߵ���ɫ
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_light));
		//���ñ�������ɫ
		pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
		pagerTabStrip.setTextAlignment(4);
		pagerTabStrip.setTextSpacing(40);
		tomatoFragment = new TomatoFragment();
		taskFragment = new TaskFragment();
		settingPreferenceFragment = new SettingPreferenceFragment();
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(tomatoFragment);
		fragmentList.add(taskFragment);
		fragmentList.add(settingPreferenceFragment);
	    titleList.add("����");
	    titleList.add("�ʼ�");
	    titleList.add("����");
	    m_vp.setOffscreenPageLimit(1);		//������Ĭ��ֵΪ1   ��Ĭ�ϼ�����һ��ҳ�棬 ���Ϊ2�������2��ҳ�棬Ϊ0ûЧ��
	    m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
	}
	
	public class MyViewPagerAdapter extends FragmentPagerAdapter{
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return titleList.get(position);
		}
		
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
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
