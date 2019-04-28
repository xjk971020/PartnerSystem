package cn.dezhi.welfare.partner.entity.dataObject;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PartnerDo {
    private Integer partnerId;

    @NotBlank(message = "用户名称不能为空")
    private String partnerName;

    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6,max = 20, message = "密码长度必须大于6小于20")
    private String partnerPwd;

    private Integer reisterRole;

    private Integer reigsterType;

    private Integer status;

    private Integer createTime;

    private Integer lastLoginTime;

    @NotBlank(message = "企业全称不能为空")
    private String enterpriseName;

    @NotBlank(message = "营业执照号不能为空")
    private String businessLicenseNum;

    @NotBlank(message = "法人代表姓名不能为空")
    private String legalName;

    @Pattern(regexp = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])",message = "身份证填写信息有误")
    @NotBlank(message = "法人代表身份证号不能为空")
    private String legalIdentityNo;

    @NotBlank(message = "企业住所不能为空")
    private String enterpriseResidence;

    @NotBlank(message = "联系人不能为空")
    private String contact;

    /**
     *说明：移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     * 验证号码 手机号 固话均可
     * 正则表达式:验证手机号
     */
    @Pattern(regexp = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))",message = "联系电话信息有误")
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @Email(message = "邮箱格式错误")
    @NotBlank(message = "联系邮箱不能为空")
    private String contactEmail;

    @NotBlank(message = "开户银行不能为空")
    private String depositBank;

    @NotBlank(message = "开户银行账号不能为空")
    private String depositBankAccount;

    private String businessLicensePic;

    private String depositBankPermitPic;

    private String qualificationCertificatePic;

}