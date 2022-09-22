package cn.zhusaidong.zjzwfw.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zhusaidong
 * @date 2022/8/17
 */
@ConfigurationProperties("zjzwfw")
@Component
@Data
public class ZjzwfwProperties {
    private final static Base DEFAULT_BASE;

    static {
        DEFAULT_BASE = new Base();
        DEFAULT_BASE.governmentExtraNet = false;
        DEFAULT_BASE.connectTimeout = 1000;
        DEFAULT_BASE.readTimeout = 1000;
    }

    /**
     * 个人单点登录：支持浙里办app，支付宝浙里办小程序
     */
    private AccessSecret personalSso;
    /**
     * 法人单点登录：支持浙里办app，支付宝浙里办小程序
     */
    private AccessSecret legalPersonSso;
    /**
     * 微信单点登录：仅支持微信浙里办小程序
     */
    private AccessSecret wechatSso;
    private Base base;

    public Base getBase() {
        return Optional.ofNullable(base).orElse(DEFAULT_BASE);
    }

    /**
     * 单点登录ak&sk
     */
    @Data
    public static class AccessSecret {
        /**
         * access key
         */
        private String accessKey;
        /**
         * secret key
         */
        private String secretKey;
    }

    @Data
    public static class Base {
        /**
         * appid
         */
        private String appId;
        /**
         * 是否是政务外网
         */
        private boolean governmentExtraNet = false;
        /**
         * 连接超时时长/ms
         */
        private int connectTimeout = -1;
        /**
         * 读取超时时长/ms
         */
        private int readTimeout = -1;
    }
}
