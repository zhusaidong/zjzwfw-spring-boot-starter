package cn.zhusaidong.zjzwfw.starter.test;

import cn.zhusaidong.zjzwfw.starter.properties.ZjzwfwProperties;
import cn.zhusaidong.zjzwfw.starter.service.ZjzwfwPersonalSsoService;
import cn.zhusaidong.zjzwfw.starter.service.ZjzwfwWechatSsoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

/**
 * @author zhusaidong
 * @date 2022/8/25
 */
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class ZjzwfwPropertiesTests {
    @Autowired
    private ZjzwfwProperties zjzwfwProperties;
    @Autowired(required = false)
    private ZjzwfwPersonalSsoService zjzwfwPersonalSsoService;
    @Autowired(required = false)
    private ZjzwfwWechatSsoService zjzwfwWechatSsoService;

    @Test
    public void testProperties() {
        Assert.notNull(zjzwfwProperties, "配置错误");
        Assert.notNull(zjzwfwProperties.getBase(), "`base`配置错误");
        Assert.notNull(zjzwfwProperties.getPersonalSso(), "`personal-sso`配置错误");
        Assert.notNull(zjzwfwProperties.getWechatSso(), "`wechat-sso`配置错误");
    }

    @Test
    public void testHaveService() {
        Assert.notNull(zjzwfwPersonalSsoService, "个人单点登录已配置");
        Assert.notNull(zjzwfwWechatSsoService, "微信单点登录已配置");
    }
}
