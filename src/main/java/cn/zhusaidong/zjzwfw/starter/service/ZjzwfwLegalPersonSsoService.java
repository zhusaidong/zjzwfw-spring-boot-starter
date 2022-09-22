package cn.zhusaidong.zjzwfw.starter.service;

import cn.zhusaidong.zjzwfw.starter.exception.ZjzwfwException;
import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * todo 浙里办单点登录服务-法人单点登录
 * @author zhusaidong
 * @date 2021/12/16
 */
@Service
public class ZjzwfwLegalPersonSsoService extends ZjzwfwBaseSsoService {
    public ZjzwfwLegalPersonSsoService(ZjzwfwProperties zjzwfwProperties) {
        super(zjzwfwProperties.getLegalPersonSso(), zjzwfwProperties.getBase());
    }

    @Override
    protected String get(String url, Map<String, String> params) {
        return null;
    }

    @Override
    protected <T> T getResponse(String response, Class<T> tClass) throws ZjzwfwException {
        return null;
    }

}
