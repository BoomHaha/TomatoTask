package com.android.tomatotask;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	ListPreference lstPre_TomatoTime_value, lstPre_BreakTime_value;
	public static final String RINGTONE = "pref_ringtone";
	public static final String RINGTONE_TITLE_NAME = "pref_ringtone_name";
	private Preference mSoundsPref;
	public SettingPreferenceFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.preferences);
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
		prefs.registerOnSharedPreferenceChangeListener(this);
		mSoundsPref = findPreference("pref_ringtone");
		lstPre_TomatoTime_value=(ListPreference)findPreference("TomatoTime_value");		//����ʱ��ı�ǩ��ֵ���˱���
		lstPre_BreakTime_value=(ListPreference)findPreference("BreakTime_value");		//��Ϣʱ��ı�ǩ��ֵ���˱���
		lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());
		lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
		mSoundsPref.setSummary(prefs.getString(RINGTONE_TITLE_NAME, null));
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		if (key.equals("TomatoTime_value")) {
			lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());	//����ñ�ǩ��ִ����Ӧ�Ķ���������ѡ��ʱ��Ĳ˵�
		}
		if (key.equals("BreakTime_value")) {
			lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
		}
		if (key.equals("pref_ringtone")) {
			mSoundsPref.setSummary(sharedPreferences.getString(RINGTONE_TITLE_NAME, null));
		}
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,Preference preference) {
		if (preference.getKey().equals("clearCount")) {
			alertDialogShow();
		}
		//�����Ӧ��ǩ�������¼�
		if (preference.getKey().equals("aboutTomatoTask")) {
			Uri uri = Uri.parse("http://baike.baidu.com/link?url=b7rlhS6YssFup2xqAjnw9__6VsQnyhtVT8Gx_-qwckUE4IZ-ns6i_jw9w_aKH-C_sjWheb9NFR_GZcfUII0bV_");
			startActivity(new Intent(Intent.ACTION_VIEW,uri));				//����һ���������׿ϵͳ��ϵͳ����������򿪴���ַ
		}
		if (preference.getKey().equals("supportAuthor")) {
			Uri uri = Uri.parse("https://m.alipay.com/personal/");  
			startActivity(new Intent(Intent.ACTION_VIEW,uri));
		}
		if (mSoundsPref == preference) {
			doPickRingtone();
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	private void doPickRingtone() {
		SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
		String Str = sharedPreferences.getString(RINGTONE, null); 
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        Uri notificationUri;
        if (Str != null) {
        	notificationUri = Uri.parse(Str);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUri);		//�򿪶Ի����ʱ���Ĭ��ѡ���ϴα�ѡ�е�����
        } else {
        	notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUri);
        }
        startActivityForResult(intent, 1);
	}
	/**
	 * ��ʾȷ����������ĶԻ���
	 */
	private void alertDialogShow() {
		new AlertDialog.Builder(getActivity()).setTitle("�����").setMessage("�Ƿ����������\nע���ò��������棡").setPositiveButton("���", 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//��������Ϊ������з�������
				SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("TomatoCount",Activity.MODE_PRIVATE);		//��ȡͳ��ģ����ص���
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putInt("todayTomatoCount", 0);		//��������Ϊ0
				editor.putInt("allTomatoCount", 0);
				editor.commit();			//ȷ�ϸò���
				Toast.makeText(getActivity(), "����ɹ���", Toast.LENGTH_SHORT).show();		//����Ļ�·���ʾ����ɹ�
			}
		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).create().show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());  
        SharedPreferences.Editor editor = sharedPreferences.edit();
		getActivity();
		if (resultCode != Activity.RESULT_OK) {
            return;
        }
		switch (requestCode) {
	      case 1:{
	    	  Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);		//�򿪶Ի���ѡ��ϵͳĬ�ϵ�����
	    	  if (null == pickedUri) {
	    		  editor.putString(RINGTONE_TITLE_NAME, "����");		//���ؽ��Ϊ�գ���û����������
	    		  editor.putString(RINGTONE, null);
	    		  editor.commit();
	    	  } else {
	    		  Ringtone ringtone =  RingtoneManager.getRingtone(getActivity(), pickedUri);		//RingtoneManager����ѡ��ϵͳ�е�����
	    		  String strRingtone = ringtone.getTitle(getActivity());		//��ȡ�����ı��⣬��ʾ��������
	    		  editor.putString(RINGTONE_TITLE_NAME, strRingtone);
	    		  editor.putString(RINGTONE, pickedUri.toString());
	    		  editor.commit();
	    	  }
	    	  break;
	      }
	      default:break;
		}
		mSoundsPref.setSummary(sharedPreferences.getString(RINGTONE_TITLE_NAME, null));
		super.onActivityResult(requestCode, resultCode, data);
	}
}
