package cn.zhusaidong.zjzwfw.starter.domain.wechat;

import lombok.Data;

/**
 * 浙里办微信单点登录响应基类
 *
 * @author zhusaidong
 */
@Data
public class ZjzwfwWechatResponse<T> {
    /**
     * 错误码
     * C-USER-SSO-TICKET-INVALID=ticket非法
     * C-USER-SSO-TOKEN-INVALID=token非法
     * C-USER-SSO-USER-EMPTY=用户信息为空
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 请求是否成功
     */
    private Boolean success;
    /**
     * 响应体
     */
    private T data;
}
