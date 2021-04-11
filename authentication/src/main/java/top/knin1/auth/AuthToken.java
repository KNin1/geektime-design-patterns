package top.knin1.auth;

import com.google.common.base.Joiner;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @author KNin1
 * token 认证类
 */
public class AuthToken {
    private static final String SEPARATE_CHAR = "#";

    private final String token;
    private final long createTime;
    // 默认过期时间 30s
    private long expiredTimeInternal = 30 * 1000L;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInternal) {
        this(token, createTime);
        this.expiredTimeInternal = expiredTimeInternal;
    }

    public static String genToken(String url, String appId, String secret, long createTime) {
        // 使用 commons-codec 获取 md5 值
        return DigestUtils.md5Hex(
                Joiner.on(SEPARATE_CHAR).join(Arrays.asList(url, appId, secret, createTime)));
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > createTime + expiredTimeInternal;
    }

    public boolean isMatched(String token) {
        return this.token.equals(token);
    }
}
