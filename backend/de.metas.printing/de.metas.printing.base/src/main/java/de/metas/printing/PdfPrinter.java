package de.metas.printing;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

import org.compiere.model.MSysConfig;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author cg
 *
 *         <ul>
 *         Taken from http://www.zenbi.co.uk/2011/09/04/printable-to-pdf/
 *
 */
public class PdfPrinter
{
	private final static String PDF_FONT_DIR = "PDF_FONT_DIR";

	/**
	 * Prints the given {@link Printable} to a pdf file and returns the result as a byte-array. The size of the document is A4.
	 *
	 * @param printable The {@link Printable} that has to be printed.
	 * @param clone A clone of the first {@link Printable}. Needed because internally, it must be printed twice and the Printable may keep state and may not be re-usable.
	 * @return A byte-array with the pdf file.
	 */
	public static byte[] print(final Printable printable, final Printable clone)
	{
		// The bytes to be returned
		byte[] bytes = null;

		// We will count the number of pages that have to be printed
		int numberOfPages = 0;

		// We will print the document twice, once to count the number of pages and once for real
		for (int i = 0; i < 2; i++)
		{
			try
			{
				final Printable usedPrintable = i == 0 ? printable : clone; // First time, use the printable, second time, use the clone

				final ByteArrayOutputStream bos = new ByteArrayOutputStream();

				final Document document = new Document(PageSize.A4); // This determines the size of the pdf document
				final PdfWriter writer = PdfWriter.getInstance(document, bos);
				document.open();
				final PdfContentByte contentByte = writer.getDirectContent();

				final PrinterJob pjob = PrinterJob.getPrinterJob();

				final PageFormat pf = pjob.defaultPage();
				final Paper paper = pjob.defaultPage().getPaper();
				final MediaSize size = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4);

				paper.setSize(size.getSize(Size2DSyntax.INCH)[0] * 72, size.getSize(Size2DSyntax.INCH)[1] * 72);
				paper.setImageableArea(0, 0, size.getSize(Size2DSyntax.INCH)[0] * 72, size.getSize(Size2DSyntax.INCH)[1] * 72);
				pf.setPaper(paper);

				final float width = (float)pf.getWidth();
				final float height = (float)pf.getHeight();

				final DefaultFontMapper mapper = new DefaultFontMapper();

				// Elaine 2009/02/17 - load additional font from directory set in PDF_FONT_DIR of System Configurator
				String pdfFontDir = MSysConfig.getValue(PDF_FONT_DIR, "");
				if (pdfFontDir != null && pdfFontDir.trim().length() > 0)
				{
					pdfFontDir = pdfFontDir.trim();
					final File dir = new File(pdfFontDir);
					if (dir.exists() && dir.isDirectory())
					{
						mapper.insertDirectory(pdfFontDir);
					}
				}
				//

				// First time, don't use numberOfPages, since we don't know it yet
				for (int j = 0; j < numberOfPages || i == 0; j++)
				{
					final Graphics2D g2d = contentByte.createGraphics(width, height, mapper);
					final int pageReturn = usedPrintable.print(g2d, pf, j);
					g2d.dispose();

					// The page that we just printed, actually existed; we only know this afterwards
					if (pageReturn == Printable.PAGE_EXISTS)
					{
						document.newPage(); // We have to create a newPage for the next page, even if we don't yet know if it exists, hence the second run where we do know
						if (i == 0)
						{ // First run, count the pages
							numberOfPages++;
						}
					}
					else
					{
						break;
					}
				}
				document.close();
				writer.close();

				bytes = bos.toByteArray();
			}
			catch (final Exception e)
			{
				// We expect no Exceptions, so any Exception that occurs is a technical one and should be a RuntimeException
				throw new PrintException(e);
			}
		}
		return bytes;
	}

	public static class PrintException extends RuntimeException
	{
		private static final long serialVersionUID = -37592114843097156L;

		public PrintException(final Exception cause)
		{
			super(cause);
		}
	}
}
