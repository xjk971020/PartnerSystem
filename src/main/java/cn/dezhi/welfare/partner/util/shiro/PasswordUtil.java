package cn.dezhi.welfare.partner.util.shiro;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @author xjk
 * @date 2019/4/14 -  15:54
 **/
public class PasswordUtil {

    /**
     * 散列算法名称
     */
    private static final  String aloggerorithName = "MD5";

    /**
     * 散列次数
     */
    private static final int hashIterations = 1024;

    public static void encryptPartnerDo(PartnerDo partnerDo) {
        String password = new SimpleHash(aloggerorithName,partnerDo.getPartnerPwd(), ByteSourceUtils.bytes(WebConst.MD5_SALT),hashIterations).toHex();
        partnerDo.setPartnerPwd(password);
    }

    public static String encryptPassword(String content) {
        return new SimpleHash(aloggerorithName,content,ByteSourceUtils.bytes(WebConst.MD5_SALT),hashIterations).toHex();
    }

    /**
     * AES加密，将登录信息存入cookie
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String enAes(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encryptedBytes);
    }

}
