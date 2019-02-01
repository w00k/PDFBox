package cl.pdfbox.c4c;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType0;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Main {
	
	//org.apache.pdfbox.rendering.PDFRenderer suggestKCMS ---> -Dsun.java2d.cmm=sun.java2d.cmm.kcms.KcmsServiceProvider 

	public static void main(String[] args) {
		
		String path = "C:\\\\Users\\\\FArenas\\\\Documents\\\\eclipse-workspace\\\\PDFBox\\\\"; 
		String file = "spiderman-comic.pdf"; 
		Integer page = 0;
		float FACTOR = 0.5f;
		int width;
		int height;
		
		File  myPDF = new File(path+file);
		
		System.out.println("Inicio");
		
		 try {
			PDDocument document = PDDocument.load(myPDF);
			
//			Splitter splitter = new Splitter();
//			List<PDDocument> splittedDocuments = splitter.split(document);
//		    for(PDDocument pdf : splittedDocuments) {
//				pdf.save(page+file);
//				page++;
//			}

			System.out.println("Number of Page : " + document.getNumberOfPages());
			
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			
			while(document.getNumberOfPages() > page) {

				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB );
				
				width = (int)(bim.getWidth() * FACTOR);
	            height = (int)(bim.getHeight() * FACTOR);
				
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				
				AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
				Graphics2D g = image.createGraphics();
				
				g.drawRenderedImage(bim, at);
				
//			    ImageIOUtil.writeImage(bim, file + "-" + (page) + ".png", 300);
//				BufferedImage bim = pdfRenderer.renderImage(page);
				ImageIO.write(image, "JPEG", new File(path+file+"_"+page+".jpg"));
				
				System.out.println("Page " + page + " end");
			   
			    page++;
			}
			
			
		 } catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin");
	}

}
