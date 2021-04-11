package top.knin1.auth.storage.impl;

import top.knin1.auth.storage.CredentialStorage;

/**
 * @author KNin1
 */
public class DefaultCredentialStorage implements CredentialStorage {
    @Override
    public String getSecretByAppId(String appId) {
        return "authentication";
    }
}
