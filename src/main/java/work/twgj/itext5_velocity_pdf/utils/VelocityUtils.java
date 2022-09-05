package work.twgj.itext5_velocity_pdf.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * velocity工具类
 * @author weijie.zhu
 * @date 2022/9/1
 */
public class VelocityUtils {

    /**
     *
     * @param templatePath 模板路径
     * @param templateFileName 模板文件全称
     * @param paramMap 填入模板数据
     * @return
     */
    public static String generate(String templatePath,String templateFileName,Map paramMap){
        //初始化并取得velocity引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templatePath);
        ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        //指定编码格式，避免生成模板就造成乱码，影响到转pdf后的文件
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        ve.init();
        //取得velocity的模板
        Template template = ve.getTemplate(templateFileName,"UTF-8");
        //取得velocity的上下文
        VelocityContext context = new VelocityContext(paramMap);
        //往vm中写入信息
        StringWriter writer = new StringWriter();
        //把数据填入上下文
        template.merge(context,writer);
        return  writer.toString();
    }
}
