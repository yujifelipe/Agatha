package br.gov.mpog.gestaoriscos.util;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportFooterEventHandler implements IEventHandler {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    protected PdfFormXObject placeholder;

    public ReportFooterEventHandler() {
        placeholder = new PdfFormXObject(new Rectangle(0, 0, 7, 5));
    }

    @Override
    public void handleEvent(Event event) {
        if (!(event instanceof PdfDocumentEvent)) {
            throw new AssertionError();
        }

        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);

        PdfCanvas canvasFooter = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        new Canvas(canvasFooter, pdfDoc, new Rectangle(36, -15, page.getPageSize().getWidth() - 72, 50)).add(createTableFooter(String.valueOf(pageNumber)));
    }

    private Table createTableFooter(String pageNumber) {
        Table table = new Table(new UnitValue[]{new UnitValue(UnitValue.PERCENT, 50), new UnitValue(UnitValue.PERCENT, 50)}).setWidthPercent(100);

        table.setFontSize(6);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        Cell cell = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Ágatha - Sistema de Gestão de Riscos").add(" - ").add(dateFormat.format(Calendar.getInstance().getTime())));
        table.addCell(cell);
        cell = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(String.valueOf(pageNumber)).add("/").add(new Image(placeholder)).setTextAlignment(TextAlignment.RIGHT));
        table.addCell(cell);

        return table;
    }

    public void writeTotalPages(PdfDocument pdf) {
        Canvas canvas = new Canvas(placeholder, pdf);
        canvas.showTextAligned(new Paragraph(String.valueOf(pdf.getNumberOfPages())).setFontSize(6), 0, -1.4f, TextAlignment.LEFT);
    }

}