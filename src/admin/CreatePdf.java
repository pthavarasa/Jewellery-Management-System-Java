package admin;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CreatePdf{
    public String file;
    Document document = new Document();
    PdfWriter writer;
    PdfPTable table;

    public CreatePdf(String file) throws FileNotFoundException, DocumentException {
        this.file = file;
        this.writer = PdfWriter.getInstance(this.document, new FileOutputStream(file));
        this.document.open();
        this.document.add(new Paragraph("Demo Invoice."));
        table = new PdfPTable(4);
        float[] columnWidths = {1f, 1f, 1f, 1f};
        table.setWidths(columnWidths);
    }

    public String getFile() {
        return this.file;
    }

    public void addCell(String ID, String description, String quantity, String amount){
        PdfPCell cell1 = new PdfPCell(new Paragraph(ID));
        cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell2 = new PdfPCell(new Paragraph(description));
        cell2.setPaddingLeft(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell3 = new PdfPCell(new Paragraph(quantity));
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell4 = new PdfPCell(new Paragraph(amount));
        cell4.setPaddingLeft(10);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

        this.table.addCell(cell1);
        this.table.addCell(cell2);
        this.table.addCell(cell3);
        this.table.addCell(cell4);
    }

    public void finishCreating() throws DocumentException {
        document.add(table);
        document.close();
        writer.close();
    }
}
