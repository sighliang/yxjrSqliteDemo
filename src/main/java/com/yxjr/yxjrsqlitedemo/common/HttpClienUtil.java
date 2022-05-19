package com.yxjr.yxjrsqlitedemo.common;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient工具类
 */
public class HttpClienUtil {

    /**请求编码*/
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 执行HTTP POST请求
     * @param url url
     * @param param 参数
     * @return
     */
    public static String httpPostWithJSON(String url, JSONObject param) {
        CloseableHttpClient client = null;
        try {
            if(url == null || url.trim().length() == 0){
                throw new Exception("URL is null");
            }
            HttpPost httpPost = new HttpPost(url);
            client = HttpClients.createDefault();
            if(param != null){
                StringEntity entity = new StringEntity(param.toString(),DEFAULT_CHARSET);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            HttpResponse resp = client.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }

//    /**
//     * 执行HTTP GET请求
//     * @param url url
//     * @param param 参数
//     * @return
//     */
//    public static String httpGetWithJSON(String url, JSONObject param) {
//        CloseableHttpClient client = null;
//        try {
//            if(url == null || url.trim().length() == 0){
//                throw new Exception("URL is null");
//            }
//            client = HttpClients.createDefault();
//            if(param != null){
//                StringBuffer sb = new StringBuffer("?");
//                for (String key : param.keySet()){
//                    sb.append(key).append("=").append(param.get(key)).append("&");
//                }
//                url = url.concat(sb.toString());
//                url = url.substring(0, url.length()-1);
//            }
//            HttpGet httpGet = new HttpGet(url);
//            HttpResponse resp = client.execute(httpGet);
//            if(resp.getStatusLine().getStatusCode() == 200) {
//                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close(client);
//        }
//        return null;
//    }

    /**
     * 关闭HTTP请求
     * @param client
     */
    private static void close(CloseableHttpClient client){
        if(client == null){
            return;
        }
        try {
            client.close();
        } catch (Exception e) {
        }
    }

//    public static void main(String[] args) throws Exception {
//        Map param = new HashMap();
//        param.put("userName", "admin");
//        param.put("password", "mao2080");
//        String result = httpGetWithJSON("https://www.baidu.com/", param);
//        System.out.println("result:"+result);
//    }


}