package cn.dezhi.welfare.partner.util;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Random;

/**
 * @author xjk
 * @date 2019/4/14 -  18:36
 * 处理上传的文件的工具类
 **/
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String getUploadPic(String imgStr,String filePath) throws IOException {
        if (StringUtils.isEmpty(imgStr)) {
            throw new BussinessException(EmBusinessError.FILE_ERROR, "文件不能为空");
        }
        //为防止文件重名被覆盖，文件名取名为：当前日期 + 1-1000内随机数
        Random random = new Random();
        Integer randomFileName = random.nextInt(1000);
        String fileName = DateKit.formatetime(System.currentTimeMillis() /100,"yyyyMMddHHmmss") + randomFileName +".jpg";
        BASE64Decoder decoder = new BASE64Decoder();
        String base64Data =  imgStr.split(",")[1];
        byte[] fileByte = decoder.decodeBuffer(base64Data);
        OutputStream fileStream = new FileOutputStream(WebConst.ABSSOLUTE_PICTURE_PATH + WebConst.PICTURE_PATH + filePath + fileName);
        fileStream.write(fileByte);
        fileStream.flush();
        fileStream.close();
        return WebConst.PICTURE_PATH  + filePath + fileName;
    }

    public static String getUploadFilePath(MultipartFile file,String filePath) {
        //返回上传的文件是否为空，即没有选择任何文件，或者所选文件没有内容。
        //防止上传空文件导致奔溃
        if (file.isEmpty()) {
            throw new BussinessException(EmBusinessError.FILE_ERROR,"文件不能为空");
        }
        if (!PatternKit.isImage(file.getOriginalFilename())) {
            throw new BussinessException(EmBusinessError.FILE_ERROR,"请上传正确的图片格式的文件");
        }
        String suffix = file.getOriginalFilename();
        String prefix = suffix.substring(suffix.lastIndexOf(".") + 1);
        //为防止文件重名被覆盖，文件名取名为：当前日期 + 1-1000内随机数
        Random random = new Random();
        Integer randomFileName = random.nextInt(1000);
        String fileName = DateKit.formatetime(System.currentTimeMillis() /100,"yyyyMMddHHmmss") + randomFileName +"." +  prefix;
        //创建文件路径
        File dest = new File(WebConst.ABSSOLUTE_PICTURE_PATH + WebConst.PICTURE_PATH + filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return WebConst.PICTURE_PATH  + filePath + fileName;
        } catch (Exception e) {
            return dest.toString();
        }
    }
}
