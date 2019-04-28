package cn.dezhi.welfare.partner.entity.viewObjcet;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xjk
 * @date 2019/4/18 -  13:07
 * 视图模型
 **/
@Data
public class PartnerVo {
    private Integer partnerId;

    private String partnerName;

    private String partnerPwd;

    private String reisterRole;

    private String reigsterType;

    private String status;

    private Integer createTime;

    private Integer lastLoginTime;

    private String enterpriseName;

    private String businessLicenseNum;

    private String legalName;

    private String legalIdentityNo;

    private String enterpriseResidence;

    private String contact;

    private String contactPhone;

    private String contactEmail;

    private String depositBank;

    private String depositBankAccount;

    private String businessLicensePic;

    private String depositBankPermitPic;

    private String qualificationCertificatePic;
}
