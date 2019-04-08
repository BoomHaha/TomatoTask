package com.android.tomatotask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.android.tomatotask.Task.ChangeSqlite;
import com.android.tomatotask.Task.Date;
import com.android.tomatotask.Task.DrawLine;
import com.android.tomatotask.Task.Notepad;
import com.android.tomatotask.Task.NotepadAdapter;
import com.android.tomatotask.Task.SqliteHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TaskFragment extends Fragment {
	private View mMainView;
	public String EXPANDED = "EXPANDED";
	public NotepadAdapter adapter;
	public ArrayList<Map<String, Object>> itemList;
	public ListView listView;
	public int number;
	public Button numberButton;
	public Button topButton;
	RelativeLayout layout;
    TextView textView;
    EditText editText;
    Date getDate;
    String date;

	public TaskFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.task_layout_main, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);		//���ý���Ĳ���
		this.numberButton = ((Button) mMainView.findViewById(R.id.number));		//���Ԫ�ظ�ֵ������
		this.topButton = ((Button) mMainView.findViewById(R.id.topButton));
		this.listView = ((ListView) mMainView.findViewById(R.id.listview));
		//�޸�����
		Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		numberButton.setTypeface(fontFace);
		topButton.setTypeface(fontFace);
		this.listView.setDivider(null);
		this.listView.setOnItemClickListener(new ItemClick());
		this.topButton.setOnClickListener(new View.OnClickListener() {
			//�����¼��ť����еĲ���
			@Override
			public void onClick(View v) {
			    textView = ((TextView)layout.findViewById(R.id.writedate));
			    editText = ((DrawLine)layout.findViewById(R.id.edittext));
			    getDate = new Date();
			    date = getDate.getDate();		//���浱ǰ������
			    textView.setText(date);
			    layout = (RelativeLayout)(getActivity().getLayoutInflater().inflate(R.layout.writedown, null));	//���õ����ť�󵯳�����Ĳ���
				new AlertDialog.Builder(getActivity()).setView(layout).setPositiveButton("����", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SQLiteDatabase localSqLiteDatabase = new SqliteHelper(getActivity(), null, null, 0).getWritableDatabase();	//��ʼ�����ݿ�����
						Notepad localNotepad = new Notepad();		//��ʼ��һ���ʼ���
						ChangeSqlite localChangeSqlite = new ChangeSqlite();
						String strContent = editText.getText().toString();		//��ȡ�ʼǵ�����
						if (strContent.equals("")) {		//�жϱʼ��Ƿ�Ϊ��
							Toast.makeText(getActivity(), "����Ϊ��", Toast.LENGTH_SHORT).show();		//��������
						}
						String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;	//ֻȡ�ʼ�ǰ11���ַ�
						localNotepad.setContent(strContent);		//Ϊ�ʼǵĶ���ֵ
						localNotepad.setTitle(strTitle);
						localNotepad.setdata(date);
						localChangeSqlite.add(localSqLiteDatabase, localNotepad);		//�������ݿ�
						showUpdate();		//������ʾ����
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
			}
		});
	}
	
	class ItemClick implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> paramAdapterView,View paramView, int paramInt, long paramLong) {
			Map<String, Object> localMap = itemList.get(paramInt);
			if (((Boolean) localMap.get("EXPANDED")).booleanValue()) {
				localMap.put("EXPANDED", Boolean.valueOf(false));
			} else {
				localMap.put("EXPANDED", Boolean.valueOf(true));
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	public void showUpdate() {
		this.itemList = new ArrayList<Map<String, Object>>();
		SQLiteDatabase localSqLiteDatabase = new SqliteHelper(getActivity(), null, null,0).getReadableDatabase();
		Iterator<Notepad> localIterator = new ChangeSqlite().query(localSqLiteDatabase).iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				Collections.reverse(this.itemList);
				this.adapter = new NotepadAdapter(getActivity(),this, this.itemList);
				this.listView.setAdapter(this.adapter);
				if (this.itemList.size()==0) {
					number=0;
					this.numberButton.setText("");
				}
				return;
			}
			Notepad localNotepad = (Notepad)localIterator.next();
			HashMap<String, Object> localHashMap = new HashMap<String, Object>();
			localHashMap.put("titleItem", localNotepad.getTitle());
			localHashMap.put("dateItem", localNotepad.getdata());
			localHashMap.put("contentItem", localNotepad.getContent());
			localHashMap.put("idItem", localNotepad.getid());
			// Ĭ�ϱʼ���̯�������۵���trueΪ̯��
			localHashMap.put("EXPANDED", Boolean.valueOf(true));
			this.itemList.add(localHashMap);
			this.number = this.itemList.size();
			System.out.println("number-number=" + number);
			this.numberButton.setText("(" + this.number + ")");
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		showUpdate();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) p.removeAllViewsInLayout();
		return mMainView;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
}
