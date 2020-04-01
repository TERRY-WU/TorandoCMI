package com.torando.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    /*
        传入来的Map参数记得要用LinkedHashMap保证顺序唯一性!!!
     */
    public static String doGet(String url, Map<String, String> param) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                    System.out.println(key + "-----" + param.get(key));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        return doPostJson(url, null, json);
    }

    public static String doPostJson(String url, String cookies, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if (cookies != null) {
                httpPost.setHeader("Cookie", cookies);
            }
            // 创建请求内容
            // httpPost.setHeader("content-type", "application/json");
            // StringEntity entity = new StringEntity(json);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doSendCookieByGet(String url, String cookies) {
        return doSendCookieByGet(url, cookies, null);
/*
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            if (Objects.isNull(url) || Objects.isNull(cookies)) {
                return "URL or cookies can't be null...!";
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Cookie", cookies);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
*/
    }

    public static String doSendCookieByGet(String url, String cookies, Map<String, String> param) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            if (Objects.isNull(url) || Objects.isNull(cookies)) {
                return "URL or cookies can't be null...!";
            }
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Cookie", cookies);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doSendCookieByPost(String url, String cookies) {
        return doSendCookieByPost(url, cookies, null);
/*
        String resultString = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            if (Objects.isNull(url) || Objects.isNull(cookies)) {
                return "URL or cookies can't be null...!";
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Cookie", cookies);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
*/
    }

    public static String doSendCookieByPost(String url, String cookies, Map<String, String> param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            if (Objects.isNull(url) || Objects.isNull(cookies)) {
                return "URL or cookies can't be null...!";
            }
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Cookie", cookies);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity1);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static List<Cookie> doGetCookie(String url, Map<String, String> param) {
        // 返回一个Cookie列表，用System.out.println(cookie.toString())去查看有什么get方法
        List<Cookie> cookieList = null;
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = null;
        if (Objects.isNull(param)) {
            try {
                HttpGet httpGet = new HttpGet(url);
                response = httpClient.execute(httpGet);
                cookieList = cookieStore.getCookies();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                URIBuilder builder = new URIBuilder(url);
                if (param != null) {
                    for (String key : param.keySet()) {
                        builder.addParameter(key, param.get(key));
                    }
                }
                URI uri = builder.build();
                HttpGet httpGet = new HttpGet(uri);
                response = httpClient.execute(httpGet);
                cookieList = cookieStore.getCookies();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cookieList;
    }

    /*
    public static String doPost(String url) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 构造list集合，往里面丢数据
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(null, null);
        BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair(null, null);
        list.add(basicNameValuePair);
        list.add(basicNameValuePair2);
        // 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
        // 第一步：通过setEntity 将我们的entity对象传递过去
        httpPost.setEntity(formEntity);
        // HttpEntity
        // 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
        // 通过client来执行请求，获取一个响应结果
        CloseableHttpResponse response = client.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(str);
        // 关闭
        response.close();
        client.close();
        return null;
    }
    */

    public static String uploadFile(String url, File file) {
        return uploadFile(url, file, null, null);
    }

    public static String uploadFile(String url, File file, Map<String, String> headerParams) {
        return uploadFile(url, file, headerParams, null);
    }

    public static String uploadFile(String url, File file, Map<String, String> headerParams, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        //每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
        String boundary = "-----WebKitFormBoundary-----";
        try {
            //文件名
            String fileName = file.getName();
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            httpPost.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
            //HttpEntity builder
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //字符编码
            builder.setCharset(Charset.forName("UTF-8"));
            //模拟浏览器
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//          builder.setMode(HttpMultipartMode.RFC6532);
            //boundary
            builder.setBoundary(boundary);
            //multipart/form-data
            builder.addPart("multipartFile", new FileBody(file)); //相当于<input name='multipartFile' type='file'/>
            builder.addTextBody("filename", fileName, ContentType.create("text/plain", Consts.UTF_8));
            // binary 只能传单个文件
//          builder.addBinaryBody("name=\"multipartFile\"; filename=\"test.docx\"", new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//          builder.addBinaryBody("file", new File("test.txt"), ContentType.MULTIPART_FORM_DATA, fileName);
            //其他参数
            if (params != null) {
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    builder.addTextBody(entry.getKey(), entry.getValue()); //, ContentType.create("text/plain", Consts.UTF_8)
//                }
                for (String key : params.keySet()) {
                    builder.addTextBody(key, params.get(key));
                }
            }
            //添加header
            if (headerParams != null) {
//                for (Map.Entry<String, String> e : headerParams.entrySet()) {
//                    httpPost.addHeader(e.getKey(), e.getValue());
//                }
                for (String key : headerParams.keySet()) {
                    httpPost.addHeader(key, headerParams.get(key));
                }
            }
            //HttpEntity
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);
            //响应
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("result" + result);
        return result;
    }
    /*
    public static void main(String[] args) {
        httpClientUploadFile("http://127.0.0.1:8080/upload",new File("d:/test/test.docx"));
    }
    */

    /*
    public static void proxy() throws IOException {
        HttpHost proxy = new HttpHost("192.168.88.12", 8888, "http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(15000).build();
        CloseableHttpClient httpClient= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        httpPost.setHeader("content-type","application/json");
        JSONObject param = new JSONObject();
        param.put("name","gaga");
        param.put("age","18");
        System.out.println(param.toString());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(str);

        HttpClientUtil.uploadFile("http://www.163.com", new File("/Users/terry/Desktop/hello你好.png"));
    }
     */

}