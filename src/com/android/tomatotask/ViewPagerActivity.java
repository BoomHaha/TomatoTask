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
import android.util.Log;
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
		Log.i("MAIN", "--------ViewPagerActivity--------onCreate--------");
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
	    titleList.add("����");
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
	
	
//	/**
//	 * ��ʼ������
//	 */
//	private void initTextView() {
////		t1 = (TextView) findViewById(R.id.text1);
////		t2 = (TextView) findViewById(R.id.text2);
////		t3 = (TextView) findViewById(R.id.text3);
////
////		t1.setOnClickListener(new MyOnClickListener(0));
////		t2.setOnClickListener(new MyOnClickListener(1));
////		t3.setOnClickListener(new MyOnClickListener(2));
//		
//	}
//	/**
//	 * ��ʼ��PageViewer
//	 */
//	private void initPagerViewer() {
//		pager = (ViewPager) findViewById(R.id.viewpager);
//		final ArrayList<View> list = new ArrayList<View>();
//		Intent intent = new Intent(context, TaskActivity.class);
//		list.add(getView("TaskActivity", intent));
//		Intent intent2 = new Intent(context, TomatoActivity.class);
//		list.add(getView("TomatoActivity", intent2));
//		Intent intent3 = new Intent(context, SettingActivity.class);
//		list.add(getView("SettingActivity", intent3));
//
//		pager.setAdapter(new MyPagerAdapter(list));
//		pager.setCurrentItem(0);
//		pager.setOnPageChangeListener(new MyOnPageChangeListener());
////		pager.setOffscreenPageLimit(0);
//	}
//	/**
//	 * ��ʼ������
//	 */
//	private void InitImageView() {
//		cursor = (ImageView) findViewById(R.id.cursor);
//		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
//		.getWidth();// ��ȡͼƬ���
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
//		offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(offset, 0);
//		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
//	}
//	/**
//	 * ͨ��activity��ȡ��ͼ
//	 * @param id
//	 * @param intent
//	 * @return
//	 */
//	private View getView(String id, Intent intent) {
//		return manager.startActivity(id, intent).getDecorView();
//	}

//	/**
//	 * Pager������
//	 */
//	public class MyPagerAdapter extends PagerAdapter{
//		List<View> list =  new ArrayList<View>();
//		public MyPagerAdapter(ArrayList<View> list) {
//			this.list = list;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position,
//				Object object) {
//			ViewPager pViewPager = ((ViewPager) container);
//			pViewPager.removeView(list.get(position));
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//		@Override
//		public Object instantiateItem(View arg0, int arg1) {
//			ViewPager pViewPager = ((ViewPager) arg0);
//			pViewPager.addView(list.get(arg1));
//			return list.get(arg1);
//		}
//
//		@Override
//		public void restoreState(Parcelable arg0, ClassLoader arg1) {
//
//		}
//
//		@Override
//		public Parcelable saveState() {
//			return null;
//		}
//
//		@Override
//		public void startUpdate(View arg0) {
//		}
//	}
//	/**
//	 * ҳ���л�����
//	 */
//	public class MyOnPageChangeListener implements OnPageChangeListener {
//
//		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
//		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
//
//		@Override
//		public void onPageSelected(int arg0) {
////			Animation animation = null;
////			switch (arg0) {
////			case 0:
////				if (currIndex == 1) {
////					animation = new TranslateAnimation(one, 0, 0, 0);
////				} else if (currIndex == 2) {
////					animation = new TranslateAnimation(two, 0, 0, 0);
////				}
////				break;
////			case 1:
////				if (currIndex == 0) {
////					animation = new TranslateAnimation(offset, one, 0, 0);
////				} else if (currIndex == 2) {
////					animation = new TranslateAnimation(two, one, 0, 0);	
////				}
////				break;
////			case 2:
////				if (currIndex == 0) {
////					animation = new TranslateAnimation(offset, two, 0, 0);
////				} else if (currIndex == 1) {
////					animation = new TranslateAnimation(one, two, 0, 0);
////				}
////				break;
////			}
//			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//�ð취����ʡ��25�д��롣ŭ��
//			currIndex = arg0;
//			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
//			animation.setDuration(300);
//			cursor.startAnimation(animation);
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			
//		}
//	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("MAIN", "--------ViewPagerActivity--------onRestart--------");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("MAIN", "--------ViewPagerActivity--------onResume--------");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("MAIN", "--------ViewPagerActivity--------onStop--------");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("MAIN", "--------ViewPagerActivity--------onPause--------");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("MAIN", "--------ViewPagerActivity--------onDestroy Method is executed");
	}
}
