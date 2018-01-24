/*
 * Copyright (c) 2003 US Hayden, Inc. All rights reserved.
 */
package com.silence.util;

import com.silence.exception.CoreException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理和文件等IO操作相关的处理
 * @author songy
 */
public class FileUtils {
    /** the logger */
    //private static Logger logger = Logger.getLogger(FileUtils.class);
    
    /**
     * 读取文本文件信息，得到存放文本信息的字符串
     * @param file  文件全路径名
     * @return  文本字符串
     * @throws CoreException 当文件操作错误
     */
    public static String readFile(String file) throws CoreException {
        StringBuffer result = new StringBuffer("");
        String thisLine;
        try {
            File templateFile = new File(file);
            if (!templateFile.exists()) {
                throw new CoreException("");
            }
            BufferedReader br = new BufferedReader(new FileReader(templateFile));
    
            while (null != (thisLine = br.readLine())) {
                result.append(thisLine);
            }
            br.close();
        } catch (IOException e) {
            throw new CoreException("");
        }
        
        return result.toString(); 
    }
    /**
     * 读取文本文件信息，得到一个按行存放文本信息的字符串数组
     * @param file  文件全路径名
     * @return  文本字符串数组
     * @throws CoreException 当文件操作错误
     */
    public static String[] readFileArray(String file) throws CoreException {
        StringBuffer result = new StringBuffer("");
        String thisLine;
        try {
            File templateFile = new File(file);
            if (!templateFile.exists()) {
                throw new CoreException("");
            }
            BufferedReader br = new BufferedReader(new FileReader(templateFile));
    
            while (null != (thisLine = br.readLine())) {
                result.append(thisLine.trim()).append(":");
            }
            br.close();
        } catch (IOException e) {
            throw new CoreException("");
        }
        String rs = result.toString();
        if (rs.length() > 0) {
            rs = rs.substring(0, rs.length() - 1);
            
            return rs.split(":"); 
        } else { 
            return new String[0];
        }
    }
    /**
     * 删除指定的文件
     * @param file  文件全路径名
     * @return  是否成功删除
     */
    public static boolean deleteFile(String file) {
        boolean result = false;
        File templateFile = new File(file);
        if (templateFile.exists()) {
            result = templateFile.delete();
        }

        return result;
    }
    /**
     * 删除指定的文件支持通配符
     * @param file 文件全路径名
     * @param exp 含通配符的表达式
     * @return 是否成功删除
     */
    public static boolean deleteFileList(String file, String exp) {
        File[] templateFile = getFiles(file, exp);
        boolean result = false;
        for (int i = 0; i < templateFile.length; i++) {
            result = templateFile[i].delete();
        }
        return result;
    }
    
    /**
     * 将指定内容以指定的编码写入指定的文件，如果文件存在则更新文件，如果文件不存在则创建一个新文件
     * @param file  文件全路径名
     * @param content   要更新的内容
     * @throws CoreException 当错误发生时
     */
    public static void writeFile(String file, String content, String enc) throws CoreException {
        try {
            File tempFile = new File(file);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                if (path != null) {
                    File tempPath = new File(path);
                    if (!tempPath.exists()) {
                        tempPath.mkdirs();
                    }
                }
            }
            
            BufferedWriter fileHandle = new BufferedWriter(
                                        new OutputStreamWriter(
                                        new FileOutputStream(file), enc));
            fileHandle.write(content);
            fileHandle.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将指定内容写入指定的文件，如果文件存在则更新文件，如果文件不存在则创建一个新文件
     * @param file  文件全路径名
     * @param content   要更新的内容
     * @throws CoreException 当错误发生时
     */
    public static void writeFile(String file, String content) throws CoreException {
        try {
            File tempFile = new File(file);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                if (path != null) {
                    File tempPath = new File(path);
                    if (!tempPath.exists()) {
                        tempPath.mkdirs();
                    }
                }
            }
            BufferedWriter fileHandle = new BufferedWriter(
                                        new OutputStreamWriter(
                                        new FileOutputStream(file)));
            fileHandle.write(content);
            fileHandle.close();
        } catch (IOException e) {
            throw new CoreException("");
        }
    }
    /**
     * 将指定内容写入指定的文件，如果文件存在则把新内容追加到文件中去. Added by huzhiqiang ---begin
     * @param file  文件全路径名
     * @param content   要更新的内容
     * @throws CoreException 当错误发生时
     */
    public static void writeFileappend(String file, String content) throws CoreException {
        try {
            File tempFile = new File(file);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                File tempPath = new File(path);
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
            }
         // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
            FileWriter writer = new FileWriter(file, true);   
            writer.write(content);   
            writer.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Added by huzhiqiang ---end
    /**
     * 将指定内容写入指定的文件，如果文件存在则更新文件，如果文件不存在则创建一个新文件。<br>
     * 使用了JDK1.4的新nio包来操作文件。
     * @param file  文件全路径名
     * @param content   要更新的内容
     * @throws CoreException 当错误发生时
     */
    public static void writeFile(String file, byte[] content) throws CoreException {
        try {
            File tempFile = new File(file);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                File tempPath = new File(path);
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
            }
            FileChannel fc = new FileOutputStream(file).getChannel();
            fc.write(ByteBuffer.wrap(content));
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将指定内容写入指定的文件，如果文件存在则更新文件，如果文件不存在则创建一个新文件
     * @param file  文件全路径名
     * @param in   要更新的内容
     * @throws CoreException 当错误发生时
     */
    public static void writeFile(String file, InputStream in) throws CoreException {
        try {
            File tempFile = new File(file);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                File tempPath = new File(path);
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 1;
            while (true) {
                len = in.read(buffer);
                if (len > 0) {
                    fileOutputStream.write(buffer, 0, len);
                } else {
                    break;
                }
            }
            in.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 复制一个文件到另外一个指定的文件中，如果文件不存在就创建一个。
     * @param fromFile  源文件全路径名
     * @param toFile    目标文件全路径名
     * @throws CoreException 当文件操作错误时
     */
    public static void copyFile(String fromFile, String toFile) throws CoreException {
        try {
            File outFile = new File(toFile);
            FileChannel in = new FileInputStream(fromFile).getChannel();
            FileChannel out = new FileOutputStream(toFile).getChannel();
            if (!outFile.exists()) {
                //检查路径
                String path = outFile.getParent();
                File tempPath = new File(path);
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
            }
            in.transferTo(0, in.size(), out);
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 执行文件的下载操作.
     * @param response  response对象
     * @param fullName  文件全路径名称
     * @param disName   下载的显示文件
     * @throws CoreException 当发生文件操作错误时
     */
    public static void downloadFile(HttpServletResponse response, String fullName, String disName)
        throws CoreException {
        downloadFile(response, fullName, disName, "attachment");
    }

    /**
     * 执行文件的下载操作.
     * @param response  response对象
     * @param fullName  文件全路径名称
     * @param disName   下载的显示文件
     * @param downType  下载类型 attachment(打开需要两次), file(类似inline) 
     * @throws CoreException 当发生文件操作错误时
     */
    public static void downloadFile(HttpServletResponse response, String fullName, String disName, String downType)
        throws CoreException {
        try {
            File dbFile = new File(fullName);
            FileInputStream fileIn = new FileInputStream(dbFile);
        
            String contentType;
            contentType = "application/x-msdownload";
            response.setContentType(contentType);
            StringBuffer header = null;
            if (downType != null && !"".equals(downType)) {
                header = new StringBuffer(downType + "; filename=");                
            } else {
                header = new StringBuffer("attachment; filename=");
            }            
            //如果系统是运行在中文平台下，则将文件名转码为ISO8859_1的编码
            disName = new String(disName.getBytes("GBK"), "ISO8859_1");
            header.append(disName);
            response.setHeader("Content-Disposition", header.toString());
            byte[] buffer = new byte[1024 * 512];
            while (true) {
                int bytes = fileIn.read(buffer);
                if (bytes == -1) {
                    break;
                }
                response.getOutputStream().write(buffer, 0, bytes);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获得文件中对应的内容
     * 
     * @param file 文件信息
     * @return 文件内容
     * @throws CoreException 当文件读取错误时发生
     */
    public static byte[] readFileBytes(File file) throws CoreException {
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            int size = (int) fc.size();
            ByteBuffer buff = ByteBuffer.allocateDirect(size);
            fc.read(buff);
            buff.flip();
            byte[] content = new byte[size];
            buff.get(content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获得文件中对应的内容
     * 
     * @param file 文件信息
     * @return 文件内容
     * @throws CoreException 当文件读取错误时发生
     */
    public static InputStream readFileStream(File file) throws CoreException {
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将指定内容写入指定的文件，如果文件存在则更新文件，如果文件不存在则创建一个新文件。<br>
     * 使用了JDK1.4的新nio包来操作文件。
     * @param  filePath  文件所在目录的全路径名
     * @return 本目录下所有文件的数组
     * @throws CoreException 当错误发生时
     */
    public static File[] listFiles(String filePath) throws CoreException {
        try {
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                //检查路径
                String path = tempFile.getParent();
                File tempPath = new File(path);
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
            }
            return tempFile.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 取得文件名的扩展名
     *
     * @param fileName 文件名称，如test.txt
     *
     * @return DOCUMENT ME!
     */
    public static String getFileExt(String fileName) {
        String ext = new String();
        int start = 0;
        int end = 0;

        if (fileName == null) {
            return null;
        }

        start = fileName.lastIndexOf(46) + 1;
        end = fileName.length();
        ext = fileName.substring(start, end);

        if (fileName.lastIndexOf(46) > 0) {
            return ext;
        } else {
            return "";
        }
    }
    
    /**
     * 取得文件的不带路径的文件名
     *
     * @param filePathName 带路径的文件名称，如f:\test.txt
     *
     * @return DOCUMENT ME!
     */
    public static String getFileName(String filePathName) {
        int pos = filePathName.lastIndexOf(47);

        if (pos != -1) {
            return filePathName.substring(pos + 1, filePathName.length());
        }

        pos = filePathName.lastIndexOf(92);

        if (pos != -1) {
            return filePathName.substring(pos + 1, filePathName.length());
        } else {
            return filePathName;
        }
    }
    /**
     * 建立临时文件
     * @param preFix 临时文件名的前缀
     * @return 临时文件
     * @throws CoreException 错误发生时
     */
    public static File createTmpFile(String preFix)  throws CoreException {
        //如果没有设置临时目录，使用系统的临时目录
        String tmpDirStr = System.getProperty("java.io.tmpdir");
        File tmpFile = null;
        try {
            //建立临时目录
            File tmpDir = new File(tmpDirStr);
            //产生一个正的长整数截取8位做为临时文件名称的一部分
            Random rand = new Random();
            String randLong = Long.toString(Math.abs(rand.nextLong())).substring(14);
            
            tmpFile = File.createTempFile(preFix + randLong, ".tmp", tmpDir);
            tmpFile.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpFile;
    }
    
    /**
     * 将字符串转换为文件存储需要的输入流
     * @param inputStr  需要被转换的字符串
     * @return  文件存储需要输入流
     * @throws CoreException  当转换错误时
     */
    public static InputStream stringToInputStream(String inputStr) throws CoreException {
        try {
            return new ByteArrayInputStream(inputStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 读取输入流中的文本信息，得到存放文本信息的字符串
     * @param in  文本信息输入流
     * @return  文本字符串
     * @throws CoreException 当文件操作错误
     */
    public static String inputStreamToString(InputStream in) throws CoreException {
        StringBuffer result = new StringBuffer("");
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while (null != (thisLine = br.readLine())) {
                result.append(thisLine);
            }
            br.close();
        } catch (IOException e) {
            throw new CoreException("");
        }
        return result.toString(); 
    }
    
    /**
     * 根据条件得到特定前缀的文件名。用于列表，卡片页面显示。目前只支持取得单个文件
     * @param path  文件名称。
     * @param prefix  文件前缀。
     * @param split  分隔符。
     * @return  符合条件的所有文件名
     * @throws CoreException 当文件操作错误
     */
    public static String getFileNameCon(String path, String prefix, String split) throws CoreException {
        try {
            File tempFile = new File(path); 
            String[] str = tempFile.list();
            if (str == null) {
                return "";
            }
            ArrayList list = new ArrayList();
            String rtn = "";
            for (int i = 0; i < str.length; i++) { 
                if(str[i].indexOf(split) == -1) {continue;}
                if (str[i].substring(0, str[i].indexOf(split)).equals(prefix)) {
                    list.add(str[i].substring(str[i].indexOf(split) + 2));                    
                }                
            }
            return list.size() == 0 ? "" : (String) list.get(0);
        } catch (Exception e) {
            throw new CoreException("");
        }
    }
    /**
     * 根据条件得到特定前缀的文件名。用于列表，卡片页面显示。目前只支持取得多个文件
     * @param path  文件名称。
     * @param prefix  文件前缀。
     * @param split  分隔符。
     * @return  符合条件的所有文件名
     * @throws CoreException 当文件操作错误
     */
    public static ArrayList getMultiFileNameCon(String path, String prefix, String split) throws CoreException {
        try {
            File tempFile = new File(path); 
            String[] str = tempFile.list();
            if (str == null) {
                return null;
            }
            ArrayList list = new ArrayList();
            String rtn = "";
            for (int i = 0; i < str.length; i++) { 
                if(str[i].indexOf(split) == -1) {continue;}
                if (str[i].substring(0, str[i].indexOf(split)).equals(prefix)) {
                    list.add(str[i].substring(str[i].indexOf(split) + 2));                    
                }                
            }
            return list.size() == 0 ? null : list;
        } catch (Exception e) {
            throw new CoreException("");
        }
    }
    /**
     * @param file 路径
     * @param p 正则表达式
     * @return ArrayList
     */
    private static ArrayList filePattern(File file, Pattern p) {
        if (file == null) {
            return null;
        } else if (file.isFile()) {
            Matcher fMatcher = p.matcher(file.getName());
            if (fMatcher.matches()) {
                ArrayList list = new ArrayList();
                list.add(file);
                return list;
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < files.length; i++) {
                    ArrayList rlist = filePattern(files[i], p);
                    if (rlist != null) {
                        list.addAll(rlist);
                    }
                }
                return list;
            }
        }
        return null;
    }
    /**
     * 根据通配符取得文件列表
     * @param dir 路径
     * @param s 含通配符的文件名
     * @return File[]
     */
    public static File[] getFiles(String dir, String s) {
        //开始的文件夹
        File file = new File(dir);

        s = s.replace('.', '#');
        s = s.replaceAll("#", "\\\\.");
        s = s.replace('*', '#');
        s = s.replaceAll("#", ".*");
        s = s.replace('?', '#');
        s = s.replaceAll("#", ".?");
        s = "^" + s + "$";

        Pattern p = Pattern.compile(s);
        ArrayList list = filePattern(file, p);
        if (list == null) {
            list = new ArrayList();
        }
        File[] rtn = new File[list.size()];
        list.toArray(rtn);
        return rtn;
      }
}
