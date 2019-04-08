package com.android.tomatotask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/*
 * ��������Բ�ι���������
 * ʵ������ʱ��ı���������ӵ�����
 * */
public class CircleProgressBar extends View {
	private int maxProgress = 10;		//������
	private int progress = 0;			//��ǰ����
	private int progressStrokeWidth = 10;	//�߿�
	RectF oval;							//Բ�����ڵľ�������RectF���Ѷ���õ��࣬����һ����������
	Paint paint;						//�������Ի�����ͼ�Σ��ı���bitmap
	
	/*
	 * ��Ĺ��췽��1
	 * Context��һ�����������������ϵͳ�Ľ�����һ�ֹ���
	 * �ӳ���ĽǶ�������⣺Context�Ǹ������࣬��Activity��Service��Application�ȶ��Ǹ����һ��ʵ��
	 * */
	
	/*
	 * ���ø���View�Ĺ��췽��  public View(Context context)
	 * ��context������һ��View
	 * */
	public CircleProgressBar(Context context) {
		super(context);
		oval = new RectF();
		paint = new Paint();
	}

	/*
	 * ���ø���View�ĵڶ��ֹ��췽��  public View(Context context, AttributeSet attrs)
	 * */
	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		oval = new RectF();
		paint = new Paint();
	}

	public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		oval = new RectF();
		paint = new Paint();
	}

	/*
	 * ��дView�����onDraw���������������canvas
	 * view�е�Canvas����ᱻ�����������ݹ��������ǲ������Canvas��Ч����ֱ�ӷ�Ӧ��View��
	 * */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * �������Ĵ�Բ������(��Ļ���-Բ�����)Ϊ�뾶
		 * Ҳ���ǻ���Ļ���ұ��ϵ�����Բ
		 */
		int centre = getWidth()/2;	//��ȡԲ�ĵ�x����=��Ļ���/2
		int radius = (int) (centre - progressStrokeWidth/2);	//Բ���İ뾶=(Բ��x����-�߿��)/2
		paint.setColor(Color.DKGRAY);		//����Բ������ɫ
		/*
		 * Paint.Style.FILL������ڲ�
		 * Paint.Style.FILL_AND_STROKE������ڲ������
		 * Paint.Style.STROKE�����
		 */
		paint.setStyle(Paint.Style.STROKE);
		/*
		 * ������ߵĿ�ȣ�Ҳ���ǿ��ƻ��ʵĴ�ϸ��
		 * �������������⣬�ο����ͣ�https://blog.csdn.net/wning1/article/details/60147898
		 * */
		paint.setStrokeWidth(progressStrokeWidth);
		paint.setAntiAlias(true);		//�����
		/*
		 * Android Canvas��ͼ����Android Canvas �����ܽ�
		 * https://blog.csdn.net/qq_35427437/article/details/80045074
		 * */
		canvas.drawCircle(centre, centre, radius, paint);		//����Բ��
		/*
		 * Բ�� ��Բ���Ľ��ȣ�ÿ�����һ��
		 */
		paint.setStrokeWidth(progressStrokeWidth);		//����Բ���Ŀ��
		paint.setColor(Color.rgb(0x57, 0x87, 0xb6));		//���ý���������ɫ 
		/*
		 * ���ڶ����Բ������״�ʹ�С�Ľ���
		 * ���￪ʼ��һ�����⣬radius����ֱ����centre/2�����򻭲�������Բ
		 * 
		 * */
		RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
		paint.setStyle(Paint.Style.STROKE);
		/*
		 * ���ݽ��Ȼ�Բ�� �����ư�ɫԲȦ��������������
		 * drawArcΪ��Բ���ĺ�����oval��ʾԲ���ľ��η�Χ��-90��ʾ��ʼ���ĽǶȣ���x������ת��90�ȣ���360 * progress / maxProgress
		 * ��ʾԲ�������Ļ��ȣ�ÿ��һ���£�ͨ������ǰ����/�����ȣ�����ɰٷֱȵõ�
		 * false ����Բ���ڻ滭��ʱ���Ƿ񾭹�Բ�ģ������ǲ�������
		 * https://blog.csdn.net/qq_18432309/article/details/51811546
		 * */
		canvas.drawArc(oval, -90, 360 * progress / maxProgress, false, paint);
	}

	public int getMaxProgress() {
		return maxProgress;
	}
	
	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
		//invalidate()�ػ�����View����UI�߳���ִ��
		this.invalidate();
	}
	
	public void setProgressNotInUiThread(int progress) {
		this.progress = progress;
		//postInvalidate()�ػ�����View���ڷ�UI�߳���ִ��
		this.postInvalidate();
	}
}
