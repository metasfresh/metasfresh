package de.metas.printing.api.util;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.util.Check;

public class PdfCollator
{
	private final OutputStream out;
	private PdfCopy pdfCopy = null;
	private Document pdfDocument = null;
	private boolean closed = false;

	public PdfCollator()
	{
		this(new ByteArrayOutputStream());
	}

	public PdfCollator(@NonNull final OutputStream out)
	{
		this.out = out;
	}

	private PdfCopy getPdfCopy()
	{
		if (pdfCopy != null)
		{
			return pdfCopy;
		}

		pdfDocument = new Document();

		try
		{
			pdfCopy = new PdfCopy(pdfDocument, out);
		}
		catch (final DocumentException e)
		{
			throw new AdempiereException(e);
		}

		pdfDocument.open();

		return pdfCopy;
	}

	public PdfCollator addPages(final InputStream pdfIn, final int pageFrom, final int pageTo)
	{
		Check.assume(pdfIn != null, "pdfIn not null");

		final PdfReader reader;
		try
		{
			reader = new PdfReader(pdfIn);
		}
		catch (final IOException e)
		{
			throw new AdempiereException(e);
		}

		return addPages(reader, pageFrom, pageTo);
	}

	public PdfCollator addPages(final byte[] pdfData, final int pageFrom, final int pageTo)
	{
		final PdfReader reader;
		try
		{
			reader = new PdfReader(pdfData);
		}
		catch (final IOException e)
		{
			throw new AdempiereException(e);
		}

		return addPages(reader, pageFrom, pageTo);
	}

	private PdfCollator addPages(final PdfReader reader, final int pageFrom, final int pageTo)
	{
		Check.assume(!closed, "collator not closed");

		//
		// Add pages
		final PdfCopy copy = getPdfCopy();
		for (int page = pageFrom; page <= pageTo; page++)
		{
			try
			{
				copy.addPage(copy.getImportedPage(reader, page));
			}
			catch (final BadPdfFormatException e)
			{
				throw new AdempiereException("Error adding page " + page, e);
			}
			catch (final IOException e)
			{
				throw new AdempiereException(e);
			}
		}

		//
		// Free reader
		try
		{
			copy.freeReader(reader);
		}
		catch (final IOException e)
		{
			throw new AdempiereException(e);
		}
		reader.close();

		return this;
	}

	public PdfCollator close()
	{
		if (closed)
		{
			return this;
		}
		closed = true;

		if (pdfCopy == null)
		{
			return this;
		}

		pdfDocument.close();

		pdfCopy = null;
		pdfDocument = null;

		return this;
	}

	public byte[] toByteArray()
	{
		if (out instanceof ByteArrayOutputStream)
		{
			close();
			final ByteArrayOutputStream baos = (ByteArrayOutputStream)out;
			return baos.toByteArray();
		}
		else
		{
			throw new RuntimeException("Output stream not supported: " + out); // NOPMD by tsa on 2/28/13 2:15 AM
		}
	}
}
