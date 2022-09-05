package work.twgj.itext5_velocity_pdf;

import com.itextpdf.text.pdf.PdfPageEventHelper;
import org.junit.Test;
import work.twgj.itext5_velocity_pdf.bean.PdfFileBean;
import work.twgj.itext5_velocity_pdf.event.PdfReportM1HeaderFooterEvent;
import work.twgj.itext5_velocity_pdf.utils.PdfUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weijie.zhu
 * @date 2022/9/5
 */
public class PdfUtilsTest {

    @Test
    public void createPdf2FileTest(){
        File file = new File("E:\\demo\\itext5_velocity_pdf\\src\\main\\resources\\out\\hello.pdf");
        file.getParentFile().mkdirs();

        Map<String,Object> data = new HashMap<>(1);
        List<String> equations = new ArrayList<>();
        for(int i = 0;i<1000;i++){
            equations.add("1+1=");
        }
        data.put("equations",equations);

        List<PdfPageEventHelper> eventHelperList = new ArrayList<>();
        eventHelperList.add(new PdfReportM1HeaderFooterEvent());

        PdfUtils.createPdf2File("src/main/resources/template","hello.vm","E:\\demo\\itext5_velocity_pdf\\src\\main\\resources\\out\\hello.pdf",eventHelperList,data);
    }

    @Test
    public void createPdf2ByteFileTest(){
        Map<String,Object> data = new HashMap<>(1);
        List<String> equations = new ArrayList<>();
        for(int i = 0;i<1000;i++){
            equations.add("1+1=");
        }
        data.put("equations",equations);

        List<PdfPageEventHelper> eventHelperList = new ArrayList<>();
        eventHelperList.add(new PdfReportM1HeaderFooterEvent());

        PdfFileBean pdfFileDTO = PdfUtils.createPdf2ByteFile("src/main/resources/template","hello.vm","四则运算.pdf",eventHelperList,data);
        System.out.println(pdfFileDTO);
    }
}
