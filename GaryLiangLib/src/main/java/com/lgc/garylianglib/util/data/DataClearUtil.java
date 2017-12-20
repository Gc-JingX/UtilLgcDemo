package com.lgc.garylianglib.util.data;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.lgc.garylianglib.util.L;

import java.io.File;
import java.math.BigDecimal;


/**
 * Created by asus on 2016/9/15.
 */
public class DataClearUtil {
    /**
     * 清除内部缓存
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFileByDirectory(context.getCacheDir());
    }

    /**
     * 清除外部缓存
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFileByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除数据库缓存
     *
     * @param context
     */
    public static void cleanDatabase(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除SharedPreference缓存
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 清除自定义路径下缓存
     *
     * @param filepath
     */
    public static void cleanCustomCache(String filepath) {
        deleteFileByDirectory(new File(filepath));
    }

    /**
     * 清除files文件夹下缓存
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFileByDirectory(context.getFilesDir());
    }

    /**
     * 删除指定名称数据库
     *
     * @param context
     * @param databaseName
     */
    public static void cleanDatabaseByName(Context context, String databaseName) {
        context.deleteDatabase(databaseName);
    }

    /**
     * 清除App内所有数据
     *
     * @param context
     * @param filePaths
     */
    public static void cleanApplicationData(Context context, String... filePaths) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabase(context);
        cleanSharedPreference(context);
        cleanFiles(context);

        if (filePaths != null) {
            for (String filePath : filePaths) {
                cleanCustomCache(filePath);
            }
        }
    }

    /**
     * 删除指定目录下所有文件,不处理文件夹
     *
     * @param cacheDir
     */
    private static void deleteFileByDirectory(File cacheDir) {
        /*if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                file.delete();
            }
        }*/
        if (cacheDir == null || !cacheDir.exists()) {
            return;
        }
        if (cacheDir.isDirectory()) {
            File[] files = cacheDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFileByDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        cacheDir.delete();
    }

    public static long folderSize = 0l;

    /**
     * 获取Cache总大小
     *
     * @param context
     * @return
     */
    public static String getTotalCacheSize(Context context) {
        folderSize = getFolderSize(context.getCacheDir())+ getCacheSize(context);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            folderSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(folderSize);
    }

    /**
     * 清除所有缓存
     */
    public static boolean cleanAllCache(Context context) {
        L.e("xx2","删除...1."+context.getExternalCacheDir());
        boolean isdelete=false;
        isdelete= deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            L.e("xx","删除..2.."+context.getExternalCacheDir());
            isdelete=deleteDir(context.getExternalCacheDir());
        }
        clearImageAllCache(context);
        return isdelete;
    }

    /**
     * 删除指定目录下所有文件及文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir == null && !dir.exists()) {
            return false;
        }
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                boolean isSuccess = deleteDir(file);
                if (!isSuccess) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     * @return
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size += getFolderSize(fileList[i]);
                } else {
                    size += fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取格式化后文件大小
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraByte = gigaByte / 1024;
        if (teraByte < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(Double.toString(teraByte));
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
// BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
//        clearImageDiskCache(context);
//        clearImageMemoryCache(context);
//        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        String path=Glide.getPhotoCacheDir(context).getAbsolutePath();
        FileCacheUtils.deleteFolderFile(path, false);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static Long getCacheSize(Context context) {
        try {
            return getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
