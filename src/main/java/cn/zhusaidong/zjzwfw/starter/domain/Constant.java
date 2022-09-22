package cn.zhusaidong.zjzwfw.starter.domain;

/**
 * @author zhusaidong
 * @date 2022/8/18
 */
public class Constant {
    /**
     * 政务外网
     */
    public static final String GOVERNMENT_EXTRANET_HOST = "https://bcdsg.zj.gov.cn:8443/restapi/prod";
    /**
     * 互联网
     */
    public static final String INTERNET_HOST = "https://ibcdsg.zj.gov.cn:8443/restapi/prod";

    /**
     * 个人单点登录
     */
    public static final String PERSONAL_TICKET_VALIDATION_URL = "/IC33000020220228000002/sso/servlet/simpleauth";
    public static final String PERSONAL_GET_USERINFO_URL = "/IC33000020220228000004/sso/servlet/simpleauth";

    /**
     * 微信单点登录
     */
    public static final String WECHAT_ACCESS_TOKEN_URL = "/IC33000020220329000007/uc/sso/access_token";
    public static final String WECHAT_GET_USERINFO_URL = "/IC33000020220329000008/uc/sso/getUserInfo";

    /**
     * 法人单点登录
     */
    public static final String LEGAL_PERSON_USER_QUERY_URL = "/IC33000020220309000001/rest/user/query";
    public static final String LEGAL_PERSON_CALLBACK_URL = "/IC33000020220309000002/rest/callbackUrl";
}
