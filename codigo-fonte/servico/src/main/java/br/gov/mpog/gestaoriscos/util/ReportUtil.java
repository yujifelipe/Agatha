package br.gov.mpog.gestaoriscos.util;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReportUtil {
    private static ReportUtil instance;

    private FontProgram fontAwesomeStream = null;

    public static final String iconCircle = "\uf111";

    public static final DeviceRgb colorYellow = WebColors.getRGBColor("#ffff00");
    public static final DeviceRgb colorRed = WebColors.getRGBColor("#FF0000");
    public static final DeviceRgb colorGreen = WebColors.getRGBColor("#008000");
    public static final DeviceRgb colorGray = WebColors.getRGBColor("#E3DFDE");
    public static final DeviceRgb colorGreenLight1 = WebColors.getRGBColor("#E3F0D9");
    public static final DeviceRgb colorGreenLight2 = WebColors.getRGBColor("#F4F9F2");

    public static ReportUtil getInstance() throws IOException {
        if (instance == null) {
            instance = new ReportUtil();
        }
        return instance;
    }

    public ReportUtil() throws IOException {
        this.carregaFontes();
    }

    private void carregaFontes() throws IOException {
        fontAwesomeStream = FontProgramFactory.createFont(readByteFromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("fonts/fontawesome/fontawesome-webfont.ttf")));
    }

    public Document criarDocumento(PdfDocument pdf, ReportFooterEventHandler footerHandler, PageSize pageSize) throws IOException {
        Document document = new Document(pdf, pageSize);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);

        document.setFont(getFontHelveticaRegular());

        return document;
    }

    public void criarReportHeader(Document document, String orgao, String secretaria, Boolean portrait) throws IOException {

        Image imgLogo = new Image(ImageDataFactory.create(readByteFromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("img/logo-relatorio.png"))));
        imgLogo.scaleToFit(90, 55);
        imgLogo.setHorizontalAlignment(HorizontalAlignment.LEFT);

        Paragraph titleOrgao = new Paragraph().setFontSize(9).add(new Text(orgao)).setTextAlignment(TextAlignment.LEFT);
        Paragraph titleSecretaria = new Paragraph().setFontSize(9).add(new Text(secretaria).setFontColor(ReportUtil.getInstance().colorRed)).setTextAlignment(TextAlignment.LEFT);
        Paragraph titleNomeSistema = new Paragraph().setFontSize(9).add(new Text("Ágatha - Sistema de Gestão de Riscos")).setTextAlignment(TextAlignment.LEFT).setPaddingTop(2f);

        Table tableHeader = new Table(new UnitValue[]{
                                    new UnitValue(UnitValue.PERCENT, portrait ? 12 : 8),
                                    new UnitValue(UnitValue.PERCENT, portrait ? 88 : 92)})
                            .setWidthPercent(100);

        tableHeader.addCell(new Cell(1, 1).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).add(imgLogo));

        tableHeader.addCell(new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).add(titleOrgao).add(titleSecretaria).add(titleNomeSistema).setPadding(0f).setPaddingLeft(2f));

        document.add(tableHeader);

        document.add(new Paragraph());
    }

    public byte[] readByteFromInputStream(InputStream stream) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read;

        while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
            arrayOutputStream.write(buffer, 0, read);
        }

        arrayOutputStream.flush();

        return arrayOutputStream.toByteArray();
    }

    public PdfFont getFontHelveticaBold() throws IOException {
        return PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
    }

    public PdfFont getFontHelveticaRegular() throws IOException {
        return PdfFontFactory.createFont(FontConstants.HELVETICA);
    }

    public PdfFont getFontAwesome() throws IOException {
        return PdfFontFactory.createFont(fontAwesomeStream, "Identity-H");
    }
}
