package cn.zhusaidong.zjzwfw.starter.domain.personal;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * 浙里办个人单点登录接口响应基类
 *
 * @author zhusaidong
 */
@Data
public class ZjzwfwResponse {
    /**
     * 结果
     0： 成功
     6001：st已经超时失效
     6002：st不是票据类型
     6003：st不属于该接入资源
     6004：st格式不对
     6099：其他错误
     */
    private Integer result;
    /**
     * 错误说明
     */
    @JsonAlias("errmsg")
    private String errMsg;
}