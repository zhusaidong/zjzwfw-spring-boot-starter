package cn.zhusaidong.zjzwfw.starter.domain.wechat;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 浙里办微信小程序用户信息
 *
 * @author zhusaidong
 */
@Data
public class ZjzwfwWechatUserInfo {
    /**
     * 用户类型: PERSON 个人, LEGAL_PERSON 法人
     */
    private String userType;
    /**
     * 个人用户信息，当前登陆自然人的信息
     */
    private ZjzwfwWechatPersonInfo personInfo;
    /**
     * 法人用户信息，比如公司相关的信息
     */
    private ZjzwfwWechatLegalPersonInfo legalPersonInfo;
    /**
     * 所属组织信息
     */
    private List<ZjzwfwWechatOrganizationInfo> organizationInfoList;

    /**
     * 个人用户信息，当前登陆自然人的信息
     * @see ZjzwfwWechatUserInfo#getPersonInfo()
     * @author lixiaolong
     * @date 2022/8/16
     */
    @Data
    public static class ZjzwfwWechatPersonInfo {
        /**
         * 主键
         */
        private String userId;
        /**
         * 个人姓名
         */
        private String userName;
        /**
         * ID_CARD:身份证,
         * PASSPORT:护照,
         * OFFICER_CARD:军官证,
         * MAINLAND_TRAVEL_PERMIT_FOR_HONGKONG_AND_MACAO_RESIDENTS:港澳居民来往内地通行证,
         * MAINLAND_TRAVEL_PERMIT_FOR_TAIWAN_RESIDENTS:台湾居民来往大陆通行证,
         * FOREIGN_PERMANENT_RESIDENT_ID_CARD:外国人永久居留身份证,
         * FOREIGN_PASSPORT:外籍人士护照,
         * DIPLOMACY_PASSPORT:外交护照,
         * OFFICIAL_PASSPORT:公务护照,
         * SOLDIER_CARD:士兵证,
         * OFFICER_RETIRE_CARD:军官离退休证,
         * GANG_AO_TAI_RESIDENCE_CART:港澳台居民居住证,
         * GANG_AO_ID_CART:港澳居民身份证,
         * UNIFIED_SOCIAL_ID:统一社会信用代码,
         * OTHER:其他
         */
        private String idType;
        /**
         * 外部证件类型
         */
        private String outerIdType;
        /**
         * 证件编号
         */
        private String idNo;
        /**
         * 法人经办人时用户类型，评级
         */
        private String attnUserType;
        /**
         * 手机号
         */
        private String phone;
        /**
         * 邮箱
         */
        private String email;
        /**
         * 民族
         */
        private String nation;
        /**
         * 性别
         */
        private String gender;
        /**
         * 生日
         */
        private String birthday;
        /**
         * 身份散列值
         */
        private String certKey;
        /**
         * 额外属性
         */
        private Map<String, Object> attributes;
    }

    /**
     * 法人用户信息，比如公司相关的信息
     * @see ZjzwfwWechatUserInfo#getLegalPersonInfo()
     * @author lixiaolong
     * @date 2022/8/16
     */
    @Data
    public static class ZjzwfwWechatLegalPersonInfo {
        /**
         * 法人名称
         */
        private String name;
        /**
         * 社会统一信用代码
         */
        private String unifiedSocialId;
        /**
         * 法人类型
         */
        private String orgType;
        /**
         * 经办人姓名
         */
        private String attnName;
        /**
         * 经办人手机号
         */
        private String attnPhone;
        /**
         * 经办人证件类型
         */
        private String attnIdType;
        /**
         * 经办人证件号码
         */
        private String attnIdNo;
        /**
         * 经办人用户等级
         */
        private String attnUserType;
        /**
         * 法人代表人姓名
         */
        private String principal;
        /**
         * 法人代表人性别
         */
        private Integer gender;
        /**
         * 法人代表人民族
         */
        private Integer nation;
        /**
         * 法人代表人证件类型
         */
        private Integer idType;
        /**
         * 法人代表人外部证件类型
         */
        private String outerIdType;
        /**
         * 法人代表人证件号码
         */
        private String idNo;
        /**
         * 法人代表唯一键
         */
        private String principalUserId;
        /**
         * 法人唯一键
         */
        private String corpId;
        /**
         * 额外属性
         */
        private Map<String, Object> attributes;
    }

    /**
     * 所属组织信息
     * @see ZjzwfwWechatUserInfo#getOrganizationInfoList()
     * @author lixiaolong
     * @date 2022/8/16
     */
    @Data
    public static class ZjzwfwWechatOrganizationInfo {
        /**
         * 组织主键
         */
        private String orgId;
        /**
         * Alias for orgId
         */
        private String oid;
        /**
         * 父组织主键
         */
        private String parentId;
        /**
         * Alias for parentId
         */
        private String pid;
        /**
         * 组织机构简称
         */
        private String name;
        /**
         * 组织机构全称
         */
        private String fullName;
        /**
         * 组织后缀
         */
        private String devCoding;
        /**
         * 是否叶子标志
         */
        private Boolean leafFlag;
        /**
         * 排序号，从小到大
         */
        private Integer orderBy;
    }
}
