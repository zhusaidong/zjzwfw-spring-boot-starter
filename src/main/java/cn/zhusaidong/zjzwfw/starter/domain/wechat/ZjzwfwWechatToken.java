package cn.zhusaidong.zjzwfw.starter.domain.wechat;

import lombok.Data;

/**
 * 浙里办微信小程序token
 *
 * @author zhusaidong
 */
@Data
public class ZjzwfwWechatToken {
    /**
     * 获取用户信息token
     */
    private String accessToken;
}