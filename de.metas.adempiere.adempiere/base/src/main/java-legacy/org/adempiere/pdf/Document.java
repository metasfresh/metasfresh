/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.pdf;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.adempiere.pdf.viewer.PDFViewerBean;
import org.compiere.model.MSysConfig;

import com.lowagie.text.FontFactory;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Generate PDF document using iText
 * @author Low Heng Sin
 *
 */
public class Document {

	static {
		FontFactory.registerDirectories();
	}
	
	private final static String PDF_FONT_DIR = "PDF_FONT_DIR";
	
	private static void writePDF(Pageable pageable, OutputStream output)
	{
		try {
            final PageFormat pf = pageable.getPageFormat(0);
            
            final com.lowagie.text.Document document =
            	new com.lowagie.text.Document(new Rectangle(
            			(int) pf.getWidth(), (int) pf.getHeight()));
            final PdfWriter writer = PdfWriter.getInstance(
                    document, output);
            writer.setPdfVersion(PdfWriter.VERSION_1_2);
            document.open();
            final DefaultFontMapper mapper = new DefaultFontMapper();     
            
            //Elaine 2009/02/17 - load additional font from directory set in PDF_FONT_DIR of System Configurator 
            String pdfFontDir = MSysConfig.getValue(PDF_FONT_DIR, ""); 
            if(pdfFontDir != null && pdfFontDir.trim().length() > 0)
            {
            	pdfFontDir = pdfFontDir.trim();
	            File dir = new File(pdfFontDir);
	            if(dir.exists() && dir.isDirectory())
	            	mapper.insertDirectory(pdfFontDir);
            }
            //
            
            final float w = (float) pf.getWidth();
            final float h = (float) pf.getHeight();
            final PdfContentByte cb = writer.getDirectContent();
            for (int page = 0; page < pageable.getNumberOfPages(); page++) {
            	if (page != 0) {
            		document.newPage();
            	}
            	
	            final PdfTemplate tp = cb.createTemplate(w, h);
	            final Graphics2D g2 = tp.createGraphics(w, h, mapper);
	            tp.setWidth(w);
	            tp.setHeight(h);
	            pageable.getPrintable(page).print(g2, pf, page);
	            g2.dispose();
	            cb.addTemplate(tp, 0, 0);
            }
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static File getPDFAsFile(String filename, Pageable pageable) {
        final File result = new File(filename);
        
        try {
        	writePDF(pageable, new FileOutputStream(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static byte[] getPDFAsArray(Pageable pageable) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream(10240);
            writePDF(pageable, output);
            return output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return null;
    }
    
    public static PDFViewerBean getViewer() {
    	return new PDFViewerBean();
    }
    
    public static boolean isValid(Pageable layout) {
    	return true;
    }
    
    public static boolean isLicensed() {
    	return true;
    }    
}
