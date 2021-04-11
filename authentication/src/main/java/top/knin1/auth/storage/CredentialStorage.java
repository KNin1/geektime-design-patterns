package top.knin1.auth.storage;

/**
 * @author KNin1
 */
public interface CredentialStorage {
    /**
     * 通过 AppId 获取对应密码
     * @param appId 客户端 app id
     * @return 返回对应密码
     */
    String getSecretByAppId(String appId);
}
