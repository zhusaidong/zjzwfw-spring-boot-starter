package cn.zhusaidong.zjzwfw.starter.test;

import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import cn.zhusaidong.zjzwfw.starter.service.ZjzwfwPersonalSsoService;
import cn.zhusaidong.zjzwfw.starter.service.ZjzwfwWechatSsoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author zhusaidong
 * @date 2022/8/25
 */
@SpringBootTest
@Slf4j
public class ZjzwfwNoPropertiesTests {
    @Autowired
    private ZjzwfwProperties zjzwfwProperties;
    @Autowired(required = false)
    private ZjzwfwPersonalSsoService zjzwfwPersonalSsoService;
    @Autowired(required = false)
    private ZjzwfwWechatSsoService zjzwfwWechatSsoService;

    @Test
    public void testDefaultConfig() {
        ZjzwfwProperties.Base base = zjzwfwProperties.getBase();
        Assert.isTrue(!base.isGovernmentExtraNet(), "默认配置错误");
        Assert.isTrue(base.getConnectTimeout() == 1000, "默认配置错误");
        Assert.isTrue(base.getReadTimeout() == 1000, "默认配置错误");
    }

    @Test
    public void testNoConfigNoService() {
        Assert.isNull(zjzwfwPersonalSsoService, "个人单点登录未配置");
        Assert.isNull(zjzwfwWechatSsoService, "微信单点登录未配置");
    }

}
