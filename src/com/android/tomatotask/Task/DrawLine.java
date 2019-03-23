package com.android.tomatotask.Task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class DrawLine extends EditText {
	private Paint ePaint;
	
	public DrawLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		ePaint = new Paint();
		ePaint.setColor(Color.BLACK);
		ePaint.setStyle(Paint.Style.STROKE);
	}

	// ���3���˴����ֽ����������м������,ע��ʹ��getLineHeight()�õ��иߡ�
	public void onDraw(Canvas canvas) {
		int count = getLineCount();
		for (int i = 0; i < count + 11; i++) {
			float[] pts = { 15.0F, (i + 1) * getLineHeight(),this.getWidth() - 20.0F, (i + 1) * getLineHeight()};
			// i*50-280,50�������м���285�������߿��ϡ�
			canvas.drawLine(15, i*42, this.getWidth()-20,i*42, ePaint);
			canvas.drawLines(pts, ePaint);
		}
		super.onDraw(canvas);
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> second commit
