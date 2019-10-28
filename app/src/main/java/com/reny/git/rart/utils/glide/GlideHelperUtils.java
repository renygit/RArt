package com.reny.git.rart.utils.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by reny on 2017/8/10.
 */

public class GlideHelperUtils {

    public static void saveImgToSDCard(File file, String dir, String imgName, GlideHelper.DownLoadCallBack callBack) {
        try {
            if (null == file || !file.exists()) {
                throw new Exception("file is null");
            }
            String path = file.getPath();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
            File picDir = new File(dir);
            if (!picDir.exists()) {
                picDir.mkdir();
            }
            if(!imgName.contains(".jpg") && !imgName.contains(".png")){
                imgName += ".jpg";
            }
            final File file2 = new File(picDir, imgName);
            FileOutputStream fOut = new FileOutputStream(file2);
            fOut.write(baos.toByteArray());
            fOut.flush();
            fOut.close();
            baos.flush();
            baos.close();
            bitmap.recycle();
            bitmap = null;

            if(file2.length() == 0){
                throw new Exception("file is empty");
            }
            if(null != callBack) {
                callBack.onSuccess(file2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(null != callBack) {
                callBack.onFailed("图片保存失败");
            }
        }
    }

}
