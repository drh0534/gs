package com.gs.pp.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 文件服务器客户端
 */
public class FileClient {

    //@Value("${upload_domain}")
    //private static String uploadUrl;

    private static String DOMIN_UPLOAD="http://174.139.85.242/upload/uploadimages";
    /**
     * @param file     spring MultipartFile
     * @return
     * @throws IOException
     */
    public static String post(MultipartFile file) throws IOException {
        String url = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(DOMIN_UPLOAD);
            byte[] data = file.getBytes();
            String fname = file.getOriginalFilename();
            String encodeFname = URLEncoder.encode(fname, "UTF-8");
            InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), encodeFname);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("media", inputStreamBody)
                    .build();
            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                //打印响应状态
                //System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                url = resEntity != null ? EntityUtils.toString(resEntity, "utf-8") : null;
                //销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return url;
    }

    public static String postList(List<FileBean> fileBeans) throws IOException {
        String url = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(DOMIN_UPLOAD);
            int size = fileBeans.size();
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (int i = 0; i < size; i++) {
                FileBean fileBean = fileBeans.get(i);
                String fname = fileBean.getFileName();
//                int fileType = fileBean.getFileType();
                byte[] data = fileBean.getFile().getBytes();
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), fname);
                multipartEntityBuilder.addPart("media" + i, inputStreamBody);
            }

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);
            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                //打印响应状态
                System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                url = resEntity != null ? EntityUtils.toString(resEntity, "utf-8") : null;
                //销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return url;
    }

    /**
     * @param file
     * @param fileType 1 文件 2 图片3 static 静态资源文件
     * @return
     * @throws IOException
     */
    public static String post2(File file, int fileType) throws IOException {
        String url = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // HttpPost httpPost = new HttpPost(Constants.DOMIN_UPLOAD);
            HttpPost httpPost = new HttpPost("http://10.10.38.134:8080/uploadimage");
            //把文件转换成流对象FileBody
            FileBody bin = new FileBody(file);
            //filetype
            StringBody comment = new StringBody(fileType + "", ContentType.create("text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("media", bin)//相当于<input type="file" name="media"/>
                    .addPart("filetype", comment)
                    .build();

            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                //打印响应状态
                System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                String responseBody = resEntity != null ? EntityUtils.toString(resEntity, "utf-8") : null;
                url = (JSON.parseObject(responseBody, Map.class)).get("path").toString();
                //销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return url;
    }


    public static File multipartFileToCommonFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            System.out.println(file.getAbsolutePath());
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}