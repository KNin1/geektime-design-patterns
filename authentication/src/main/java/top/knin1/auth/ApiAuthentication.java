package top.knin1.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.knin1.auth.storage.impl.DefaultCredentialStorage;

/**
 * 接口请求认证
 * @author KNin1
 */
public class ApiAuthentication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthentication.class);

    public static boolean auth(String url) {
        ApiRequest request = ApiRequest.parseFullUrl(url);
        if (request == null) {
            LOGGER.error("url error!");
            return false;
        }
        AuthToken authToken = new AuthToken(request.getToken(), request.getTimestamp());
        String secret = new DefaultCredentialStorage().getSecretByAppId(request.getAppId());

        if (authToken.isExpired()) {
            LOGGER.error("Token 已过期！");
            return false;
        }
        if (!authToken.isMatched(
                AuthToken.genToken(request.getBaseUrl(), request.getAppId(), secret, request.getTimestamp()))) {
            LOGGER.error("Token 错误！");
            return false;
        }
        return true;
    }
}
