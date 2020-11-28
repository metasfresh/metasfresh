/**
 * 
 */
package de.metas.printing;

import java.io.ByteArrayOutputStream;

import org.adempiere.exceptions.AdempiereException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author cg
 *
 */
public class MergePdfByteArrays
{
	private ByteArrayOutputStream outStream = null;
	private Document document = null;
	private PdfWriter writer = null;
	private PdfContentByte cb = null;

	public byte[] getMergedPdfByteArray()
	{
		if (document == null)
		{
			return null;
		}

		try
		{
			this.document.close();
			this.document = null;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}

		if (this.outStream != null)
		{
			return this.outStream.toByteArray();
		}
		else
		{
			return null;
		}

	}

	public MergePdfByteArrays add(final byte[] pdfByteArray)
	{
		try
		{
			final PdfReader reader = new PdfReader(pdfByteArray);
			int numberOfPages = reader.getNumberOfPages();

			if (this.document == null)
			{
				this.document = new Document(reader.getPageSizeWithRotation(1));
				this.outStream = new ByteArrayOutputStream();
				this.writer = PdfWriter.getInstance(this.document, this.outStream);
				this.writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE); // needs to be specified explicitly; will not work with PdfWriter.PrintScalingNone 
				this.document.open();
				this.cb = this.writer.getDirectContent();
			}

			PdfImportedPage page;
			int rotation;
			{
				int i = 0;
				while (i < numberOfPages)
				{
					i++;
					document.setPageSize(reader.getPageSizeWithRotation(i));
					document.newPage();
					page = writer.getImportedPage(reader, i);
					rotation = reader.getPageRotation(i);
					if (rotation == 90 || rotation == 270)
					{
						cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(i).getHeight());
					}
					else
					{
						cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}

		return this;
	}
}
