package work.twgj.itext5_velocity_pdf.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.twgj.itext5_velocity_pdf.bean.PdfFileBean;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author weijie.zhu
 * @date 2022/9/1
 */
public class PdfUtils {

    private static Logger logger = LoggerFactory.getLogger(PdfUtils.class);

    public static void createPdf2File(String templateFilePath, String templateFullName, String outputPath, List<PdfPageEventHelper> eventHelperList, Map<String,Object> data){
        Document document = null;
        PdfWriter writer = null;
        try{
            document = new Document(PageSize.A4,60.5f,60.5f,33f,33f);
            writer = PdfWriter.getInstance(document,new FileOutputStream(outputPath));

            // 添加页面事件
            if (CollectionUtils.isNotEmpty(eventHelperList)){
                for(PdfPageEventHelper eventHelper:eventHelperList){
                    writer.setPageEvent(eventHelper);
                }
            }
            parseXHtml(document,writer,templateFilePath,templateFullName,data);
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if (writer != null){ writer.close(); }
            if (document != null){ document.close(); }
        }
    }

    public static PdfFileBean createPdf2ByteFile(String templateFilePath, String templateFullName, String outputFileName, List<PdfPageEventHelper> eventHelperList , Map<String,Object> data){
        InputStream is = null;
        ByteArrayOutputStream out = null;
        Document document = null;
        PdfWriter writer = null;
        try {
            out = new ByteArrayOutputStream();
            document = new Document(PageSize.A4,60.5f,60.5f,33f,33f);
            writer = PdfWriter.getInstance(document,out);

            // 添加页面事件
            if (CollectionUtils.isNotEmpty(eventHelperList)){
                for(PdfPageEventHelper eventHelper:eventHelperList){
                    writer.setPageEvent(eventHelper);
                }
            }

            parseXHtml(document,writer,templateFilePath,templateFullName,data);

            is = new ByteArrayInputStream(out.toByteArray());
            byte[] bytes = new byte[is.available()];
            int len = is.read(bytes);
            if (len > 0) {
                PdfFileBean fileBean = new PdfFileBean();
                fileBean.setName(outputFileName);
                fileBean.setContent(Base64.getEncoder().encodeToString(bytes));
                return fileBean;
            } else {
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }finally {
            try{
                if (is != null){
                    is.close();
                }
                if (writer != null){
                    writer.close();
                }
                if (document != null){
                    document.close();
                }
                if (out != null){
                    out.close();
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private static void parseXHtml(Document document,PdfWriter pdfWriter,String templateFilePath, String templateFullName,Map<String,Object> data){
        Reader reader = null;
        try {
            document.open();
            String value = VelocityUtils.generate(templateFilePath,templateFullName,data);
            reader = new StringReader(value);
            //去找velocity并交给itext生成pdf
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter,document,reader);
            document.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
