package cn.zhusaidong.zjzwfw.starter.service;

import cn.zhusaidong.zjzwfw.starter.exception.ZjzwfwException;
import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import com.alibaba.fastjson.JSON;
import cn.zhusaidong.zjzwfw.starter.domain.Constant;
import cn.zhusaidong.zjzwfw.starter.domain.wechat.ZjzwfwWechatResponse;
import cn.zhusaidong.zjzwfw.starter.domain.wechat.ZjzwfwWechatToken;
import cn.zhusaidong.zjzwfw.starter.domain.wechat.ZjzwfwWechatUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 浙里办单点登录服务-微信浙里办小程序
 * @author zhusaidong
 * @date 2021/12/16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "zjzwfw.wechat-sso", name = "access-key")
public class ZjzwfwWechatSsoService extends ZjzwfwBaseSsoService {
    private final String appId;

    public ZjzwfwWechatSsoService(ZjzwfwProperties zjzwfwProperties) {
        super(zjzwfwProperties.getWechatSso(), zjzwfwProperties.getBase());
        ZjzwfwProperties.Base base = zjzwfwProperties.getBase();
        this.appId = base.getAppId();
    }

    /**
     * 根据ticketId获取用户信息
     * @param ticketId ticketId
     * @return 用户信息
     * @throws ZjzwfwException 浙里办异常
     * @throws RestClientException 请求异常
     */
    public ZjzwfwWechatUserInfo getUserByTicketId(String ticketId) throws ZjzwfwException, RestClientException {
        return getUserInfo(accessToken(ticketId));
    }

    @Override
    protected String get(String url, Map<String, String> params) throws RestClientException {
        String fullUrl = getHost() + url;

        log.info("fullUrl:{}", fullUrl);
        log.info("params:{}", params);

        return REST_TEMPLATE.postForEntity(fullUrl, new HttpEntity<>(params, getHeaders(url)), String.class)
                .getBody();
    }

    @Override
    protected <T> T getResponse(String response, Class<T> tClass) throws ZjzwfwException {
        ZjzwfwWechatResponse<T> zjzwfwWechatResponse = JSON.parseObject(response,
                TypeUtils.parameterize(ZjzwfwWechatResponse.class, tClass));
        if (zjzwfwWechatResponse == null) {
            return null;
        }
        if (!zjzwfwWechatResponse.getSuccess()) {
            throw new ZjzwfwException(zjzwfwWechatResponse.getErrorCode(), zjzwfwWechatResponse.getErrorMsg());
        }

        return zjzwfwWechatResponse.getData();
    }

    /**
     * 微信小程序ticketId换token
     * @param ticketId 浙里办ticketId
     * @return token
     */
    private String accessToken(String ticketId) throws ZjzwfwException, RestClientException {
        Map<String, String> params = new HashMap<>();
        params.put("ticketId", ticketId);
        //todo 疑问：好像不要该参数也行？
        params.put("appId", this.appId);
        String response = get(Constant.WECHAT_ACCESS_TOKEN_URL, params);
        ZjzwfwWechatToken dataToken = getResponse(response, ZjzwfwWechatToken.class);

        return Optional.ofNullable(dataToken).orElseGet(ZjzwfwWechatToken::new).getAccessToken();
    }

    /**
     * 微信小程序获取用户信息
     * @param token ticketId换取的token
     * @return 用户信息
     */
    private ZjzwfwWechatUserInfo getUserInfo(String token) throws ZjzwfwException, RestClientException {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        String response = get(Constant.WECHAT_GET_USERINFO_URL, params);
        return getResponse(response, ZjzwfwWechatUserInfo.class);
    }
}
