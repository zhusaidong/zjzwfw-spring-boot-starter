package cn.zhusaidong.zjzwfw.starter.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * 浙里办脱敏类库
 *
 * @author zhusaidong
 *
 * @date 2022/6/21
 */
public final class ZjzwfwDesensitizationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZjzwfwDesensitizationUtil.class);

    /**
     * 姓名：隐藏姓名中的第一个字，如为英文等其他语种，也是隐藏第一个字母。
     * <p>
     * 示例：*大友、*安、*ahn
     */
    public static String name(String name) {
        return format(name, str -> "*".concat(str.substring(1)));
    }

    /**
     * 身份证号-强隐藏规则：
     * 显示前 1 位和后 1 位，其它用*号代替。
     * <p>
     * （凡是页面显示后无需用户检查确认的，使用这个规则。）
     * <p>
     * 示例：3****************X
     */
    public static String idCardNumberForStrongRule(String idCardNumber) {
        return format(idCardNumber, str -> starInMiddleOfString(str, 1, 1));
    }

    /**
     * 身份证号-普通隐藏规则：
     * 显示前 1、5、6、7、8、9、10、11、12、18 位，其余用*号代替。
     * <p>
     * （凡是系统显示后还需用户检查确认的，可使用这个规则。）
     * <p>
     * 示例：3***23197402*****X
     */
    public static String idCardNumberForOrdinaryRule(String idCardNumber) {
        return format(idCardNumber, str ->
                str.substring(0, 1)
                        .concat("***")
                        .concat(str.substring(4, 12))
                        .concat("*****")
                        .concat(str.substring(str.length() - 1))
        );
    }

    /**
     * 其他各类敏感信息(包括军官证号、护照号等其他身份证件)：
     * 显示前 1/3 和后 1/3 段字节，其他用*号代替
     */
    public static String otherIdNumber(String idNumber) {
        return format(idNumber, str -> {
            int length = str.length();
            //前 1/3 和后 1/3
            int before, after;
            before = after = length / 3;

            return starInMiddleOfString(str, before, after);
        });
    }

    /**
     * 手机号：显示前 3 位+****+后 4 位。
     * <p>
     * 示例：137****9050
     */
    public static String mobile(String mobile) {
        return format(mobile, str -> starInMiddleOfString(str, 3, 4));
    }

    /**
     * 固定电话号码：显示区号和后 4 位，其余用*号代替，
     * <p>
     * 示例：0571****8709
     */
    public static String fixedPhone(String phone) {
        //删除区号分隔符：0571-12345678变成057112345678
        return format(phone.replace("-", ""), str -> starInMiddleOfString(str, 4, 4));
    }

    /**
     * 邮箱：@前面的字符显示 3 位，3 位后显示 3 个*，@后面完整显示
     * <p>
     * 示例：con***@163.com
     * <p>
     * 如果少于三位，则全部显示，@前加***，
     * <p>
     * 例如： tt@163.com 则显示为 tt***@163.com
     */
    public static String email(String email) {
        return format(email, str -> {
            String[] split = str.split("@", 2);
            String name = split[0];
            String host = split.length == 1 ? "" : split[1];
            return name.substring(0, Math.min(3, name.length()))
                    .concat("***@")
                    .concat(host);
        });
    }

    /**
     * 银行卡卡号：显示前 6 位+ *（实际位数）+后 4 位。
     * <p>
     * 示例：622575******1496
     */
    public static String bankCardNumber(String bankCardNumber) {
        return format(bankCardNumber, str -> starInMiddleOfString(str, 6, 4));
    }

    /**
     * 字符串中间为*
     * @param str 字符串
     * @param before 前n位
     * @param after 后n位
     * @return 脱敏字符串
     */
    private static String starInMiddleOfString(String str, int before, int after) {
        int length = str.length();

        return str.substring(0, before)
                .concat(StringUtils.leftPad("", length - before - after, "*"))
                .concat(str.substring(length - after));
    }

    private static String format(String str, Function<String, String> function) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        String newStr = function.apply(str);
        if (logger.isDebugEnabled()) {
            logger.debug("originStr={}, newStr={}", str, newStr);
        }

        return newStr;
    }

    public static void main(String[] args) {
        logger.info(name("尼古拉斯·赵四"));
        logger.info(idCardNumberForStrongRule("123456789012345678"));
        logger.info(idCardNumberForOrdinaryRule("123456789012345678"));
        logger.info(otherIdNumber("1234567890"));
        logger.info(mobile("15012345678"));
        logger.info(fixedPhone("057112345678"));
        logger.info(email("tt@qq.com"));
        logger.info(email("1234567890@qq.com"));
        logger.info(bankCardNumber("6225751234561496"));
    }
}
