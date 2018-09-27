package io.geewit.boot.aliyun.dm.configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.DmClient;
import com.aliyuncs.dm.HangZhouDmClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.geewit.boot.aliyun.AliyunProperties;
import io.geewit.boot.aliyun.dm.service.MailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * aliyun dm auto configuration
 *
 * @author geewit
 */
@Configuration
@ConditionalOnClass({ IClientProfile.class, HangZhouDmClient.class })
@EnableConfigurationProperties({AliyunProperties.class, AliyunDmProperties.class})
public class AliyunDmAutoConfiguration {
    private final AliyunProperties properties;
    private final AliyunDmProperties dmProperties;

    public AliyunDmAutoConfiguration(AliyunProperties properties, AliyunDmProperties dmProperties) {
        this.properties = properties;
        this.dmProperties = dmProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public IAcsClient iAcsClient() {
        DmClient dmClient = new HangZhouDmClient(properties.getKey(), properties.getSecret());
        IClientProfile profile = DefaultProfile.getProfile(dmClient.getRegion(), properties.getKey(), properties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @Bean
    @ConditionalOnMissingBean
    public MailService mailService() {
        MailService mailService = new MailService(iAcsClient(), dmProperties);
        return mailService;
    }
}
