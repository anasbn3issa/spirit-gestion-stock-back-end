package tn.esprit.spring.services.produit;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import tn.esprit.spring.entities.Produit;

public class ExportToPDF {
	 private List<Produit> listP;
     
	    public ExportToPDF(List<Produit> listP) {
	        this.listP = listP;
	    }
	 
	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        
	       cell.setPadding(7);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        
	        cell.setPhrase(new Phrase("ID Produit", font));
	         
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("code", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("libelle", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("prix unitaire", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Date Creation", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Date Dernière Modification", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Catégorie", font));
	        table.addCell(cell);
	        
	        
	        
	         
	               
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (Produit produit : listP) {
	        	
	        	table.addCell(String.valueOf(produit.getIdProduit()));
	        	table.addCell(produit.getCode());
	            table.addCell(produit.getLibelle());
	            table.addCell(String.valueOf(produit.getPrixUnitaire()));
	            table.addCell(String.valueOf(produit.getDetailProduit().getDateCreation()));
	            table.addCell(String.valueOf(produit.getDetailProduit().getDateDernièreModification()));
	            table.addCell(String.valueOf(produit.getDetailProduit().getCategorieProduit()));
	            
	            
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        
	         
	        Paragraph p = new Paragraph("List produits", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {2f, 2f, 2f, 2f, 2f, 3f, 3f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }

}
