package com.future.newt.p6;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

@SuppressLint("ClickableViewAccessibility")
class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��	   
	Ball ball;//��
	float lightOffset = -4;
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ�� 
            ball.yAngle += dx * TOUCH_SCALE_FACTOR;//���������Բ��y����ת�ĽǶ�
            ball.xAngle+= dy * TOUCH_SCALE_FACTOR;//���������Բ��x����ת�ĽǶ�
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
	@SuppressLint("NewApi")
	private class SceneRenderer implements Renderer
    {
        public void onDrawFrame(GL10 gl) 
        {
//        	//�����Ȼ�������ɫ����
//            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
//            //�����ֳ�
//            MatrixState.pushMatrix();
//            //������
//            MatrixState.pushMatrix();
//            ball.drawSelf();
//            MatrixState.popMatrix();
//            //�ָ��ֳ�
//            MatrixState.popMatrix();

            //�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
//            MatrixState.setLightLocation(lightOffset, 0, 1.5f);
            MatrixState.pushMatrix(); //�����ֳ�MatrixState.pushMatrix();//�����ֳ�
            MatrixState.translate(-1.2f, 0, 0);//��x�Ḻ����ƽ��
            ball.drawSelf();    //������
            MatrixState.popMatrix();       //�ָ��ֳ�

            MatrixState.pushMatrix();//�����ֳ�
            MatrixState.translate(1.2f, 0, 0);//��x��������ƽ��
            ball.drawSelf();    //������
            MatrixState.popMatrix();     //�ָ��ֳ�
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ��
        	GLES30.glViewport(0, 0, width, height);
        	//����GLSurfaceView�Ŀ�߱�
            Constant.ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
			// ���ô˷������������9����λ�þ���
			MatrixState.setCamera(0, 0, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            
            //��ʼ���任����
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0f,0f,0f, 1.0f);  
            //���������
            ball=new Ball(MySurfaceView.this);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
        }
    }
}
