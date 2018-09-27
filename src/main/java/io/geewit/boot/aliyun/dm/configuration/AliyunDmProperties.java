package io.geewit.boot.aliyun.dm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * aliyun dm properties
 *
 * @author geewit
 */
@ConfigurationProperties(prefix = "aliyun.dm")
public class AliyunDmProperties {
    private boolean enable = true;
    /**
     * 控制台创建的发信地址
     */
    private String accountName;
    /**
     * 发信人昵称
     */
    private String fromAlias;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFromAlias() {
        return fromAlias;
    }

    public void setFromAlias(String fromAlias) {
        this.fromAlias = fromAlias;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
