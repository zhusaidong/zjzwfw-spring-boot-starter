package cn.zhusaidong.zjzwfw.starter.domain.personal;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 浙里办个人单点登录票据
 *
 * @author zhusaidong
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZjzwfwTicket extends ZjzwfwResponse {
    /**
     * 令牌
     */
    private String token;
    /**
     * 用户在SSO分配的身份唯一号
     */
    private String userid;
    /**
     * 登录的用户名
     */
    @JsonAlias("loginname")
    private String loginName;
    /**
     * 用户姓名
     */
    private String username;
    @JsonAlias("orgcoding")
    private String orgCoding;
}
