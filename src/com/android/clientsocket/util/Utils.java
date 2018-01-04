package com.android.clientsocket.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class Utils {
    public static int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++)
            if (need <= (1 << i) - 12)
                return (1 << i) - 12;
        return need;
    }
    
    public static int idealLongArraySize(int need) {
        return idealByteArraySize(need * 8) / 8;
    }
    
    public static String toString(int mInt) {
    	return Integer.toString(mInt);
    }
    
    public static String toTypeID(int mInt) {
    	return "T" + Integer.toString(mInt);
    }
    
    public static String toContID(int mInt) {
    	return "C" + Integer.toString(mInt);
    }
    
    public static String toTypeID(String mInt) {
    	return "T" + mInt;
    }
    
    public static String toContID(String mInt) {
    	return "C" + mInt;
    }
    
    //////////////////////////////////////////////////
    public static String toWarTypeID(int mInt) {
    	return "WT" + Integer.toString(mInt);
    }
    
    public static String toWarContID(int mInt) {
    	return "WC" + Integer.toString(mInt);
    }
    
    public static String toWarTypeID(String mInt) {
    	return "WT" + mInt;
    }
    
    public static String toWarContID(String mInt) {
    	return "WC" + mInt;
    }
    public static String toRekTypeID(int mInt) {
    	return "RE" + Integer.toString(mInt);
    }
    
    public static String toRekTypeID(String mInt) {
    	return "RE" + mInt;
    }
    
    public static String toConGrupID(int mInt) {
    	return "GL" + Integer.toString(mInt);
    }
    
    public static String toConGrupID(String mInt) {
    	return "GL" + mInt;
    }
    public static String toConGrupconID(String mInt) {
    	return "GM" + mInt;
    }
    
    public static String toConGrupconID(int mInt) {
    	return "GM" + Integer.toString(mInt);
    }
    public static String systemFrmtTime(String format) {
    	long times = System.currentTimeMillis();
		Date curdate = new Date(times);
		String strtime = new SimpleDateFormat(format).format(curdate);
		return strtime;
    }
    
    public static String Today() {
    	return  Utils.systemFrmtTime("yyyy/MM/dd HH:mm:ss").substring(0, 10);
    }
	public static String systemDate() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);

		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		String month = String.valueOf(monthOfYear + 1);
		String day = String.valueOf(dayOfMonth);
		if (monthOfYear < 10) {
			month = "0" + month;
		}
		if (dayOfMonth < 10) {
			day = "0" + day;
		}
		String date = year + "/" + month + "/" + day;
		return date;
	}
	
	public static void showToastDebug(Context context,String toastStr) {
		if (true)
            Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
    }
	
	public static void showToast(Context context,String toastStr) {
        Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
    }
	
	public static void showToastLong(Context context,String toastStr) {
        Toast.makeText(context, toastStr, Toast.LENGTH_LONG).show();
    }
	
	public static void showToast(Context context,boolean toastStr) {
        Toast.makeText(context, String.valueOf(toastStr), Toast.LENGTH_SHORT).show();
    }
	
	public static void showToast(Context context,int toastStr) {
        Toast.makeText(context, String.valueOf(toastStr), Toast.LENGTH_SHORT).show();
    }
	
	public static Boolean compare(String com,String pare) {
		if (com.equals(pare) )
			return true;
		else 
			return false;
    }
	
    /**
     * 返回每个按钮应该出现的角度(弧度单位)
     * @param index
     * @return double 角度(弧度单位)
     */
    public static double getAngle(int total,int index){

        return Math.toRadians(90/(total-1)*index+90);
    }
    
    /**
     * 按钮移动动画
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public static void buttonAnimation(final List<ImageButton> buttonList,int radius ,int flag){

        for(int i=0;i<buttonList.size();i++){

            ObjectAnimator objAnimatorX;
            ObjectAnimator objAnimatorY;
            ObjectAnimator objAnimatorRotate;

            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);

            // 按钮在X、Y方向的移动距离
            float distanceX = (float) (flag*radius*(Math.cos(Utils.getAngle(buttonList.size(),i))));
            float distanceY = -(float) (flag*radius*(Math.sin(Utils.getAngle(buttonList.size(),i))));

            // X方向移动
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x", buttonList.get(i).getX(),buttonList.get(i).getX()+distanceX);
            objAnimatorX.setDuration(200);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();

            // Y方向移动
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y", buttonList.get(i).getY(),buttonList.get(i).getY()+distanceY);
            objAnimatorY.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();

            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i), "rotation", 0, 360);
            objAnimatorRotate.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();

            if(flag==-1){
                objAnimatorX.addListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // TODO Auto-generated method stub
                        // 将按钮设为可见
                        for (int i = 0; i < buttonList.size(); i++) {
                        	buttonList.get(i).setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }

        }
    }
    
    public static Bitmap textAsBitmap(String text, float textSize) {  
    	  
        TextPaint textPaint = new TextPaint();  
  
        // textPaint.setARGB(0x31, 0x31, 0x31, 0);  
        textPaint.setColor(Color.BLACK);  
  
        textPaint.setTextSize(textSize);  
  
        StaticLayout layout = new StaticLayout(text, textPaint, 450,  
                Alignment.ALIGN_NORMAL, 1.3f, 0.0f, true);  
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,  
                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);  
        Canvas canvas = new Canvas(bitmap);  
        canvas.translate(10, 10);  
        canvas.drawColor(Color.WHITE);  
  
        layout.draw(canvas);  
        Log.d("textAsBitmap",  
                String.format("1:%d %d", layout.getWidth(), layout.getHeight()));  
        return bitmap;  
    }
    
    public static String getMac() {
        String macSerial = null;
        String str = "";
 
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
 
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }
}