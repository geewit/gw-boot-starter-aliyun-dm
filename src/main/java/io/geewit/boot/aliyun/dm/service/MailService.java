package io.geewit.boot.aliyun.dm.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import io.geewit.boot.aliyun.dm.configuration.AliyunDmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

/**
 * @author geewit
 */
public class MailService {

    private final static Logger logger = LoggerFactory.getLogger(MailService.class);

    private final IAcsClient iAcsClient;

    private final AliyunDmProperties dmProperties;

    public MailService(IAcsClient iAcsClient, AliyunDmProperties dmProperties) {
        this.iAcsClient = iAcsClient;
        this.dmProperties = dmProperties;
    }

    /**
     *
     * @param accountName 控制台创建的发信地址
     * @param fromAlias   发信人昵称
     * @param tagName     控制台创建的标签
     * @param toAddress   目标地址
     * @param subject     邮件主题
     * @param htmlBody    邮件正文
     * @return SingleSendMailResponse
     * @throws ClientException
     */
    @Async
    public SingleSendMailResponse sendMail(String accountName, String fromAlias, String tagName, String toAddress, String subject, String htmlBody) throws ClientException {
        if(!dmProperties.getEnable()) {
            return null;
        }
        logger.debug("send mail request, accountName = " + accountName + ", fromAlias = " + fromAlias + ", tagName = " + tagName + ", toAddress = " + toAddress + ", subject = " + subject);
        SingleSendMailRequest request = new SingleSendMailRequest();
        //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
        request.setAccountName(accountName);
        request.setFromAlias(fromAlias);
        request.setAddressType(1);
        request.setTagName(tagName);
        request.setReplyToAddress(true);
        request.setToAddress(toAddress);
        request.setSubject(subject);
        request.setHtmlBody(htmlBody);
        try {
            SingleSendMailResponse httpResponse = iAcsClient.getAcsResponse(request);
            logger.debug("send mail response : { envId = " + httpResponse.getEnvId() + ", requestId = " + httpResponse.getRequestId() + "}");
            return httpResponse;
        } catch (ClientException e) {
            logger.warn("send mail " + e.getErrorType().name() + "." + e.getErrMsg() + "(" + e.getErrCode() + "), requestId: " + e.getRequestId());
            throw e;
        }
    }


    /**
     *
     * @param tagName     控制台创建的标签
     * @param toAddress   目标地址
     * @param subject     邮件主题
     * @param htmlBody    邮件正文
     * @return SingleSendMailResponse
     * @throws ClientException
     */
    @SuppressWarnings({"unused"})
    public SingleSendMailResponse sendMail(String tagName, String toAddress, String subject, String htmlBody) throws ClientException {
        String accountName = dmProperties.getAccountName();
        String fromAlias = dmProperties.getFromAlias();
        return sendMail(accountName, fromAlias, tagName, toAddress, subject, htmlBody);
    }


    /**
     *
     * @param toAddress   目标地址
     * @param subject     邮件主题
     * @param htmlBody    邮件正文
     * @return SingleSendMailResponse
     * @throws ClientException
     */
    @SuppressWarnings({"unused"})
    public SingleSendMailResponse sendMail(String toAddress, String subject, String htmlBody) throws ClientException {
        String accountName = dmProperties.getAccountName();
        String fromAlias = dmProperties.getFromAlias();
        return sendMail(accountName, fromAlias, null, toAddress, subject, htmlBody);
    }
}
