package com.android.clientsocket.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class DataBackUp {
	private void copyDBToSDcrad()
	{
	    String DATABASE_NAME = "数据库文件名称";
	     
	    String oldPath = "data/data/com.packagename/databases/" + DATABASE_NAME;
	    String newPath = Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME;
	     
	    copyFile(oldPath, newPath);
	}
	 
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径
	 * @param newPath
	 *            String 复制后路径
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath)
	{
	    try
	    {
	        int bytesum = 0;
	        int byteread = 0;
	        File oldfile = new File(oldPath);
	        File newfile = new File(newPath);
	        if (!newfile.exists())
	        {
	            newfile.createNewFile();
	        }
	        if (oldfile.exists())
	        { // 文件存在时
	            InputStream inStream = new FileInputStream(oldPath); // 读入原文件
	            FileOutputStream fs = new FileOutputStream(newPath);
	            byte[] buffer = new byte[1444];
	            while ((byteread = inStream.read(buffer)) != -1)
	            {
	                bytesum += byteread; // 字节数 文件大小
	                fs.write(buffer, 0, byteread);
	            }
	            inStream.close();
	        }
	    }
	    catch (Exception e)
	    {
	        System.out.println("复制单个文件操作出错");
	        e.printStackTrace();
	 
	    }
	 
	}
	
	

    private String SHARED_PREFS;  
    private String DATABASES;  
    private String APP_PATH;  
    private Context mContext;  
    private String BACKUP_PATH;  
    private String BACKUP_DATABASES;  
    private String BACKUP_SHARED_PREFS;  
  
    public  DataBackUp(Context context) {  
        mContext = context;  
//        ApkInfo apkInfo = new ResourceUtil(context).getApkInfo();  
        APP_PATH = new StringBuilder("/data/data/").append("com.android.clientsocket")  
                .toString();  
        SHARED_PREFS = APP_PATH + "/shared_prefs";  
        DATABASES = APP_PATH + "/databases";  
        if (Environment.MEDIA_MOUNTED.equals(Environment  
                .getExternalStorageState())) {
        	BACKUP_PATH = "/sdcard/lurencun/backup/";  
//            BACKUP_PATH = "/sdcard/backup/";
            Utils.showToast(mContext, "检测到SD卡");
        } else {
            BACKUP_PATH = "/com.lurencun/backup/";  
            Toast.makeText(mContext, "没有检测到SD卡，可能无法备份成功", Toast.LENGTH_SHORT)  
                    .show();  
        }  
        BACKUP_PATH += "com.android.clientmp";  
        BACKUP_DATABASES = BACKUP_PATH + "/databases";  
        BACKUP_SHARED_PREFS = BACKUP_PATH + "/shared_prefs";  
    }  
  
    /** 
     * 备份文件 
     *  
     * @return 当且仅当数据库及配置文件都备份成功时返回true。 
     */  
    public boolean doBackup() {
        return backupDB() && backupSharePrefs();  
    }  
  
    private boolean backupDB() {  
        return copyDir(DATABASES, BACKUP_DATABASES, "备份数据库文件成功:"  
                + BACKUP_DATABASES, "备份数据库文件失败");  
    }  
  
    private boolean backupSharePrefs() {  
        return copyDir(SHARED_PREFS, BACKUP_SHARED_PREFS, "备份配置文件成功:"  
                + BACKUP_SHARED_PREFS, "备份配置文件失败");  
    }  
  
    /** 
     * 恢复 
     *  
     * @return 当且仅当数据库及配置文件都恢复成功时返回true。 
     */  
    public boolean doRestore() {  
        return restoreDB() && restoreSharePrefs();  
    }  
  
    private boolean restoreDB() {  
        return copyDir(BACKUP_DATABASES, DATABASES, "恢复数据库文件成功", "恢复数据库文件失败");  
    }  
  
    private boolean restoreSharePrefs() {  
        return copyDir(BACKUP_SHARED_PREFS, SHARED_PREFS, "恢复配置文件成功",  
                "恢复配置文件失败");  
    }  
  
    private final void showToast(String msg) {  
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();  
    }  
  
    /** 
     * 复制目录 
     *  
     * @param srcDir 
     *            源目录 
     * @param destDir 
     *            目标目录 
     * @param successMsg 
     *            复制成功的提示语 
     * @param failedMsg 
     *            复制失败的提示语 
     * @return 当复制成功时返回true, 否则返回false。 
     */  
    private final boolean copyDir(String srcDir, String destDir,  
            String successMsg, String failedMsg) {  
        try {  
            FileUtils.copyDirectory(new File(srcDir), new File(destDir));  
        } catch (IOException e) {  
            e.printStackTrace();  
            showToast(failedMsg);  
            return false;  
        }  
        showToast(successMsg);  
        return true;  
    }  
}
