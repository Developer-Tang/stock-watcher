package cn.tangshh.stock_watcher.util;

import com.intellij.openapi.diagnostic.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP工具类</br>
 * 基于Apache HttpClient
 *
 * @author Tang
 * @version v1.0
 */
public final class HttpUtil {

    private static final Logger LOG = Logger.getInstance(HttpUtil.class);
    // 连接池管理器，全局唯一
    private static final PoolingHttpClientConnectionManager connectionManager;
    // HTTP客户端，全局唯一
    private static final CloseableHttpClient httpClient;
    // 默认超时时间设置(毫秒)
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 1000;
    // 连接池配置
    private static final int MAX_TOTAL_CONNECTIONS = 50;
    private static final int MAX_CONNECTIONS_PER_ROUTE = 20;

    static {
        // 初始化连接池
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);

        // 初始化请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                .build();

        // 初始化HTTP客户端
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .evictIdleConnections(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();

        // 注册JVM关闭钩子，在程序退出时关闭连接池
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.error("关闭HTTP连接池时发生错误", e);
            }
        }));
    }

    private HttpUtil() {}

    /**
     * 执行HTTP请求
     *
     * @param request HTTP请求对象
     * @return 响应内容
     * @throws IOException 如果发生I/O错误
     */
    public static String execute(HttpRequestBase request) throws IOException {
        if (request == null) {
            throw new IllegalArgumentException("HTTP请求对象不能为null");
        }

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();

            // 检查响应状态码，只处理2xx成功状态
            if (statusCode < 200 || statusCode >= 300) {
                throw new IOException("HTTP请求失败，状态码: " + statusCode);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 使用UTF-8编码解析响应内容
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
            return "";
        } finally {
            // 释放资源
            request.releaseConnection();
        }
    }

    /**
     * 发送GET请求
     *
     * @param url 请求URL
     * @return 响应内容
     * @throws IOException 如果发生I/O错误
     */
    public static String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }

    /**
     * 发送POST请求
     *
     * @param url  请求URL
     * @param body 请求体内容
     * @return 响应内容
     * @throws IOException 如果发生I/O错误
     */
    public static String post(String url, String body) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (body != null && !body.isEmpty()) {
            httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        }
        return execute(httpPost);
    }

    /**
     * 发送PUT请求
     *
     * @param url  请求URL
     * @param body 请求体内容
     * @return 响应内容
     * @throws IOException 如果发生I/O错误
     */
    public static String put(String url, String body) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        if (body != null && !body.isEmpty()) {
            httpPut.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
        }
        return execute(httpPut);
    }

    /**
     * 发送DELETE请求
     *
     * @param url 请求URL
     * @return 响应内容
     * @throws IOException 如果发生I/O错误
     */
    public static String delete(String url) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url);
        return execute(httpDelete);
    }

    /**
     * 获取连接池状态信息
     *
     * @return 连接池状态描述
     */
    public static String getConnectionPoolStatus() {
        return String.format("连接池状态 - 总连接数: %d, 空闲连接数: %d",
                connectionManager.getTotalStats().getLeased(),
                connectionManager.getTotalStats().getAvailable());
    }
}