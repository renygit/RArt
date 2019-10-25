package com.reny.git.rart.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 类名：GZIPUtils
 * 作者：xxx
 * 功能：
 * 创建日期：2015/5/5 11:44
 * 修改记录：
 */
public class GZIPUtils {
    public static final int BUFFER = 1024;

    /**
     * 压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 压缩
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        gos.write(data, 0, data.length);
        gos.finish();
        byte[] output = baos.toByteArray();
        baos.flush();
        baos.close();
        return output;
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) {
        if(null == data)return null;
        GZIPInputStream gis = null;
        ByteArrayOutputStream baos = null;
        try {
            gis = new GZIPInputStream(new ByteArrayInputStream(data));
            baos = new ByteArrayOutputStream();
            int count;
            byte[] buf = new byte[BUFFER];
            while ((count = gis.read(buf, 0, BUFFER)) != -1) {
                baos.write(buf, 0, count);
            }
            baos.flush();
            data = baos.toByteArray();
            baos.close();
            gis.close();
            return data;
        } catch (Exception e) {
        } finally {
            try {
                if(null != baos) {
                    baos.close();
                }
                if(null != gis) {
                    gis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
