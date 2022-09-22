package cn.zhusaidong.zjzwfw.starter.domain.personal;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 浙里办个人单点登录用户信息
 *
 * @author zhusaidong
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZjzwfwUserInfo extends ZjzwfwResponse {
    /**
     * 用户在SSO分配的身份唯一号
     */
    private String userid;
    /**
     * 认证级别
     *   1.非实名 2.初级实名 3.高级实名
     *  （当认证级别为2或者3的时候实名信息才有效）
     */
    @JsonAlias("authlevel")
    private String authLevel;
    /**
     * 注册证件类型：
     *  1.身份证 2.护照 3.军官证 4.港澳居民来往内地通行证 5.台湾居民来往大陆通行证 6.外国人永久居留身份证
     */
    @JsonAlias("idtype")
    private Integer idType;
    /**
     * 实名信息
     */
    private String username;
    /**
     * 实名信息 – 注册证件号码：与注册证件类型对应（如注册证件类型是护照，证件号码这里对应的就是护照号码）
     */
    @JsonAlias("idnum")
    private String idNum;
    /**
     * 护照
     */
    private String passport;
    /**
     * 港澳居民来往大陆通行证
     */
    @JsonAlias("permitlicense")
    private String permitLicense;
    /**
     * 台湾居民来往内地通行证
     */
    @JsonAlias("taiwanlicense")
    private String taiwanLicense;
    /**
     * 军官证
     */
    @JsonAlias("officerlicense")
    private String officerLicense;
    /**
     * 外国人永久居留身份证（中国绿卡）
     */
    @JsonAlias("greencard")
    private String greenCard;
    /**
     * 	实名信息 -- 性别
     * 	    1.男 2.女
     */
    private String sex;
    /**
     * 	实名信息 -- 民族(见国标GB3304-91)
     */
    private String nation;
    /**
     * 登录名
     */
    @JsonAlias("loginname")
    private String loginName;
    /**
     * 邮件R
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * CA证书KEY
     */
    private String cakey;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 国籍
     */
    private String country;
    /**
     * 省籍
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 办公地址
     */
    @JsonAlias("officeaddress")
    private String officeAddress;
    /**
     * 办公电话
     */
    @JsonAlias("officephone")
    private String officePhone;
    /**
     * 办公传真
     */
    @JsonAlias("officefax")
    private String officeFax;
    /**
     * 家庭电话
     */
    @JsonAlias("homephone")
    private String homePhone;
    /**
     * 家庭地址
     */
    @JsonAlias("homeaddress")
    private String homeAddress;
    /**
     * 用户激活状态
     *  1.激活，2.未激活
     */
    @JsonAlias("useable")
    private String useAble;
    /**
     * 排序
     */
    @JsonAlias("orderby")
    private String orderBy;
    /**
     * 头像地址
     */
    @JsonAlias("headpicture")
    private String headPicture;
}
