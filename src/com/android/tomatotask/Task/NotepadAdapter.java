/**
 * ʹ�ʼ���ʾ�ڽ�����
 * */
package com.android.tomatotask.Task;

import java.util.ArrayList;
import java.util.Map;

import com.android.tomatotask.R;
import com.android.tomatotask.TaskFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NotepadAdapter extends BaseAdapter {
	public Context context;
	public Activity activity;
	public LayoutInflater inflater;
	public TaskFragment taskFragment;
	public ArrayList<Map<String, Object>> list;

	public NotepadAdapter(Activity activity, TaskFragment taskFragment,ArrayList<Map<String, Object>> list) {
		this.context = activity;
		this.activity = activity;
		this.taskFragment = taskFragment;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		SetShow setShow = new SetShow();
		//ȡ��map�е�չ�����
		Map<String, Object> map = list.get(arg0);
		boolean boo = (Boolean) map.get("EXPANDED");
		if (!boo) {
			arg1 = inflater.inflate(R.layout.showtypes, arg2, false);
			setShow.contentView = (TextView) arg1.findViewById(R.id.contentTextView);
			setShow.dateView = (TextView) arg1.findViewById(R.id.dateTextView);
			String str = (String) list.get(arg0).get("titleItem");
			String dateStr = (String) list.get(arg0).get("dateItem");
			setShow.contentView.setText("   " + str);
			setShow.dateView.setText(dateStr);
			setShow.showButtonWrite = (Button) arg1.findViewById(R.id.smallbutton1);
			setShow.showButtonDelete = (Button) arg1.findViewById(R.id.smallbutton2);
			//�޸�����
			Typeface fontFace = Typeface.createFromAsset(taskFragment.getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
			setShow.contentView.setTypeface(fontFace);
			setShow.dateView.setTypeface(fontFace);
			setShow.showButtonWrite.setTypeface(fontFace);
			setShow.showButtonDelete.setTypeface(fontFace);			
			setShow.showButtonWrite.setOnClickListener(new WriteButtonListener(arg0));
			setShow.showButtonDelete.setOnClickListener(new DeleteButtonListener(arg0));
		} else {
			arg1 = inflater.inflate(R.layout.style, arg2, false);
			setShow.cContentView = (TextViewLine) arg1.findViewById(R.id.changecontentview);
			setShow.cDateView = (TextView) arg1.findViewById(R.id.changedateview);
			String str = (String) list.get(arg0).get("contentItem");
			String dateStr = (String) list.get(arg0).get("dateItem");
			setShow.cContentView.setText("" + str);
			setShow.cDateView.setText(dateStr);
			setShow.styleButtonWrite = (Button) arg1.findViewById(R.id.stylebutton1);
			setShow.styleButtonDelete = (Button) arg1.findViewById(R.id.stylebutton2);
			//�޸�����
			Typeface fontFace = Typeface.createFromAsset(taskFragment.getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
			setShow.cContentView.setTypeface(fontFace);
			setShow.cDateView.setTypeface(fontFace);
			setShow.styleButtonWrite.setTypeface(fontFace);
			setShow.styleButtonDelete.setTypeface(fontFace);
			setShow.styleButtonWrite.setOnClickListener(new WriteButtonListener(arg0));
			setShow.styleButtonDelete.setOnClickListener(new DeleteButtonListener(arg0));
		}
		return arg1;
	}

	class WriteButtonListener implements OnClickListener {
		private int position;
		public WriteButtonListener(int position) {
			this.position = position;
		}
		SetEdit setEdit = new SetEdit();

		@Override
		public void onClick(View v) {
			setEdit.layout = (RelativeLayout) taskFragment.getActivity().getLayoutInflater().inflate(R.layout.showedit, null);
			setEdit.textView = ((TextView) setEdit.layout.findViewById(R.id.editdate));
			setEdit.editText = ((DrawLine) setEdit.layout.findViewById(R.id.edittexttwo));
			setEdit.cancelButton = ((Button) setEdit.layout.findViewById(R.id.editbutton));
			setEdit.sureButton = ((Button) setEdit.layout.findViewById(R.id.editbutton2));
			setEdit.date = (String) list.get(position).get("dateItem");
			setEdit.content = (String) list.get(position).get("contentItem");
			setEdit.id = (String) list.get(position).get("idItem");
			setEdit.editText.setSelection(setEdit.editText.length());
			setEdit.editText.setText(setEdit.content);
			setEdit.editText.setSelection(setEdit.content.length());
			setEdit.textView.setText(setEdit.date);
			setEdit.dateNow = new Date();
			setEdit.date = setEdit.dateNow.getDate();
			setEdit.textView.setText(setEdit.date);
			//�޸�����
			Typeface fontFace = Typeface.createFromAsset(taskFragment.getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
			setEdit.textView.setTypeface(fontFace);
			setEdit.editText.setTypeface(fontFace);
			new AlertDialog.Builder(activity)
					.setView(setEdit.layout)
					.setPositiveButton("����",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									SQLiteDatabase localSqLiteDatabase = new SqliteHelper(taskFragment.getActivity(), null,null, 0).getWritableDatabase();
									Notepad localNotepad = new Notepad();
									ChangeSqlite localChangeSqlite = new ChangeSqlite();
									String strContent = setEdit.editText.getText().toString();
									if (strContent.equals("")) {
										Toast.makeText(taskFragment.getActivity(),"����Ϊ��", Toast.LENGTH_SHORT).show();
										return;
									}
									String strTitle = strContent.length() > 11 ? " " + strContent.substring(0, 11) : strContent;
									localNotepad.setContent(strContent);
									localNotepad.setTitle(strTitle);
									localNotepad.setdata(setEdit.date);
									localNotepad.setid(setEdit.id);
									localChangeSqlite.update(localSqLiteDatabase, localNotepad);
									taskFragment.showUpdate();
								}
							})
					.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {

								}
							}).show();
		}
	}

	class DeleteButtonListener implements OnClickListener {
		private int position;

		public DeleteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			//���ɾ����ִ�еĲ���
			android.app.AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("ȷ��ɾ����");
			builder.setPositiveButton("ɾ��",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
							SqliteHelper sql = new SqliteHelper(context, null,null, 0);
							SQLiteDatabase dataBase = sql.getWritableDatabase();
							ChangeSqlite change = new ChangeSqlite();
							Notepad notepad = new Notepad();
							notepad.setid((String) list.get(position).get("idItem"));
							change.delete(dataBase, notepad);		//�������ݿⲢɾ����ز���
							taskFragment.showUpdate();				//���½���
						}
					})
					.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create();
			builder.show();
		}
	}

	class SetShow {
		public TextView contentView;
		public TextView dateView;
		public TextViewLine cContentView;
		public TextView cDateView;
		public Button styleButtonWrite;
		public Button styleButtonDelete;
		public Button showButtonWrite;
		public Button showButtonDelete;
	}

	class SetEdit {
		public RelativeLayout layout;
		public Button cancelButton;
		public String content;
		public String date;
		public Date dateNow;
		public EditText editText;
		public String id;
		public Button sureButton;
		public TextView textView;
	}
}
