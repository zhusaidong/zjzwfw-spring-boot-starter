package cn.zhusaidong.zjzwfw.starter.service;

import cn.zhusaidong.zjzwfw.starter.exception.ZjzwfwException;
import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import com.alibaba.fastjson.JSON;
import cn.zhusaidong.zjzwfw.starter.domain.Constant;
import cn.zhusaidong.zjzwfw.starter.domain.personal.ZjzwfwResponse;
import cn.zhusaidong.zjzwfw.starter.domain.personal.ZjzwfwTicket;
import cn.zhusaidong.zjzwfw.starter.domain.personal.ZjzwfwUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 浙里办单点登录服务-个人单点登录
 * @author zhusaidong
 * @date 2021/12/16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "zjzwfw.personal-sso", name = "access-key")
public class ZjzwfwPersonalSsoService extends ZjzwfwBaseSsoService {
    public ZjzwfwPersonalSsoService(ZjzwfwProperties zjzwfwProperties) {
        super(zjzwfwProperties.getPersonalSso(), zjzwfwProperties.getBase());
    }

    /**
     * 根据ticket获取用户信息
     * @param ticket ticket
     * @return 用户信息
     * @throws ZjzwfwException 浙里办异常
     * @throws RestClientException 请求异常
     */
    public ZjzwfwUserInfo getUserByTicket(String ticket) throws ZjzwfwException, RestClientException {
        return getUserInfo(ticketValidation(ticket));
    }

    @Override
    protected String get(String url, Map<String, String> params) throws RestClientException {
        String fullUrl = getHost() + url;
        //生成header鉴权
        String accessKey = accessSecret.getAccessKey();
        String secretKey = accessSecret.getSecretKey();

        String time = LocalDateTime.now().format(DATETIME_NO_SEPARATION);
        String origin = accessKey + secretKey + time;
        String sign = DigestUtils.md5DigestAsHex(origin.getBytes(StandardCharsets.UTF_8)).toLowerCase();

        params.put("servicecode", accessKey);
        params.put("time", time);
        params.put("sign", sign);
        params.put("datatype", "json");

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        params.forEach(paramMap::add);
        params.clear();

        log.info("fullUrl:{}", fullUrl);
        log.info("params:{}", paramMap);

        return REST_TEMPLATE.postForEntity(fullUrl, new HttpEntity<>(paramMap, getHeaders(url)), String.class)
                .getBody();
    }

    @Override
    protected <T> T getResponse(String response, Class<T> tClass) throws ZjzwfwException {
        T body = JSON.parseObject(response, tClass);
        if (body == null) {
            return null;
        }
        if (!(body instanceof ZjzwfwResponse)) {
            return null;
        }

        ZjzwfwResponse zjzwfwResponse = (ZjzwfwResponse) body;
        if (zjzwfwResponse.getResult() != 0) {
            throw new ZjzwfwException(zjzwfwResponse.getResult() + "", zjzwfwResponse.getErrMsg());
        }

        return body;
    }

    private String ticketValidation(String ticket) throws ZjzwfwException, RestClientException {
        Map<String, String> params = new HashMap<>();
        params.put("method", "ticketValidation");
        params.put("st", ticket);

        ZjzwfwTicket zjzwfwTicket = getResponse(get(Constant.PERSONAL_TICKET_VALIDATION_URL, params), ZjzwfwTicket.class);
        return Optional.ofNullable(zjzwfwTicket).orElseGet(ZjzwfwTicket::new).getToken();
    }

    private ZjzwfwUserInfo getUserInfo(String token) throws ZjzwfwException, RestClientException {
        Map<String, String> params = new HashMap<>();
        params.put("method", "getUserInfo");
        params.put("token", token);

        return getResponse(get(Constant.PERSONAL_GET_USERINFO_URL, params), ZjzwfwUserInfo.class);
    }
}
