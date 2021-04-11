package top.knin1.auth.test;

import org.junit.Assert;
import org.junit.Test;
import top.knin1.auth.ApiAuthentication;
import top.knin1.auth.ApiRequest;
import top.knin1.auth.AuthToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KNin1
 */
public class AuthTest {
    private String url;

    public void genRequestUrl(String secret) {
        Map<String, String> params = new HashMap<>();
        params.put("username", "KNin1");
        params.put("email", "wangcpps@gmail.com");

        String baseUrl = "http://localhost:8088/test";
        String appId = "A-client";
        long createTime = System.currentTimeMillis();

        String url = ApiRequest.genRequestUrl(baseUrl, params, null);
        String token = AuthToken.genToken(url, appId, secret, createTime);

        params.put("app_id", appId);
        params.put("timestamp", String.valueOf(createTime));
        params.put("token", token);
        this.url = ApiRequest.genRequestUrl(baseUrl, params, null);
    }

    @Test
    public void testAuthCorrect() {
        String secret = "authentication";
        genRequestUrl(secret);
        Assert.assertTrue(ApiAuthentication.auth(url));
    }

    @Test
    public void testAuthSecretError() {
        String secret = "authentication-1";
        genRequestUrl(secret);

        Assert.assertFalse(ApiAuthentication.auth(url));
    }
}
