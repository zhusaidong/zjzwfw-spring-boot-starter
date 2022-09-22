package cn.zhusaidong.zjzwfw.starter.service;

import cn.zhusaidong.zjzwfw.starter.domain.Constant;
import cn.zhusaidong.zjzwfw.starter.exception.ZjzwfwException;
import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import cn.zhusaidong.zjzwfw.starter.util.ZjzwfwAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 浙里办基础服务
 * @author zhusaidong
 * @date 2021/12/16
 */
@Slf4j
public abstract class ZjzwfwBaseSsoService {
    public static final DateTimeFormatter DATETIME_NO_SEPARATION = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    protected static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplate();
    }

    protected final ZjzwfwProperties.Base base;
    protected final ZjzwfwProperties.AccessSecret accessSecret;

    public ZjzwfwBaseSsoService(ZjzwfwProperties.AccessSecret accessSecret, ZjzwfwProperties.Base base) {
        this.accessSecret = accessSecret;
        this.base = base;

        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) REST_TEMPLATE.getRequestFactory();
        requestFactory.setConnectTimeout(base.getConnectTimeout());
        requestFactory.setReadTimeout(base.getReadTimeout());
    }

    protected abstract String get(String url, Map<String, String> params);

    protected abstract <T> T getResponse(String response, Class<T> tClass) throws ZjzwfwException;

    protected String getHost() {
        return base.isGovernmentExtraNet() ? Constant.GOVERNMENT_EXTRANET_HOST : Constant.INTERNET_HOST;
    }

    protected HttpHeaders getHeaders(String url) {
        String fullUrl = getHost() + url;

        //生成header鉴权
        String accessKey = accessSecret.getAccessKey();
        String secretKey = accessSecret.getSecretKey();
        Map<String, String> header = ZjzwfwAuthUtil.generateHeader(fullUrl, "POST", accessKey, secretKey);
        log.info("header:{}", header);
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        header.forEach(headerMap::add);
        header.clear();

        //设置header
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(headerMap);
        headerMap.clear();

        return headers;
    }
}
