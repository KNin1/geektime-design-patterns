package top.knin1.auth;

import com.google.common.base.Joiner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author KNin1
 */
public class ApiRequest {
    private static final Set<String> EXCLUDE_PARAMS = new TreeSet<>(Arrays.asList("app_id", "token", "timestamp"));

    private String baseUrl;
    private String appId;
    private String token;
    private long timestamp;

    public ApiRequest() {}

    public ApiRequest(String baseUrl, String appId, String token, long timestamp) {
        this.baseUrl = baseUrl;
        this.appId = appId;
        this.token = token;
        this.timestamp = timestamp;
    }

    public static ApiRequest parseFullUrl(String url) {
        try {
            URL fullUrl = new URL(url);
            String baseUrl = url.substring(0, url.indexOf("?", 0));
            String queryStr = fullUrl.getQuery();
            String[] queryParams = queryStr.split("&");
            Map<String, String> params = new HashMap<>();
            for (String query : queryParams) {
                String[] split = query.split("=");
                params.put(split[0], split[1]);
            }
            baseUrl = genRequestUrl(baseUrl, params, EXCLUDE_PARAMS);
            return new ApiRequest(
                    baseUrl, params.get("app_id"), params.get("token"), Long.parseLong(params.get("timestamp")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String genRequestUrl(String baseUrl, Map<String, String> params, Set<String> excludeParams) {
        if (params != null && !params.isEmpty()) {
            List<String> paramsList = new ArrayList<>(params.size());
            for (Map.Entry<String, String> entry: params.entrySet()) {
                if (excludeParams != null && excludeParams.contains(entry.getKey())) {
                    continue;
                }
                paramsList.add(entry.getKey() + "=" + entry.getValue());
            }
            paramsList.sort(String::compareTo);
            baseUrl = String.format("%s?%s", baseUrl, Joiner.on('&').join(paramsList));
        }
        return baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
