package de.metas.printing.client.engine;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import de.metas.printing.client.util.Util;

/**
 *
 * A {@link Printable} implementation that allows us to print PDF data with specified calibration parameters.
 *
 */
public class PrintablePDF implements Printable
{
	private final PDFFile pdfFile;

	private int calX = 0;

	private int calY = 0;

	public PrintablePDF(final InputStream in)
	{
		pdfFile = mkPDFFile(in);
	}

	private static PDFFile mkPDFFile(final InputStream pdfIn)
	{
		final PDFFile pdfFile;
		try
		{
			final ByteBuffer bb = ByteBuffer.wrap(Util.toByteArray(pdfIn));
			pdfFile = new PDFFile(bb);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		return pdfFile;
	}

	/**
	 * Calibrating the paper on the X-axis, with out stretching or compressing. Less than zero => move left; more that zero => move right
	 *
	 * @param exchange
	 */
	public void setCalX(final int calX)
	{
		this.calX = calX;
	}

	/**
	 * Calibrating the paper on the Y-axis, with out stretching or compressing. Less than zero => move up; more that zero => move down
	 *
	 * @param exchange
	 */
	public void setCalY(final int calY)
	{
		this.calY = calY;
	}

	/**
	 * Prints the PDF for the given printing context, moving it according to what was set with {@link #setCalX(int)} and {@link #setCalY(int)}.
	 * <p>
	 * Generally don't resize the PDF to fit to the page format. The jasper was made to have a particular size.<br>
	 * However, as we always scaled to the page format in the past and some jaspers might rely on it, we do scale *down* if the PDF is bigger than the page format. Note that we only scale if both the
	 * page format's height *and* width are smaller.
	 * <p>
	 * Originally taken from http://www.javaworld.com/javaworld/jw-06-2008/jw-06-opensourcejava-pdf-renderer.html?page=5
	 */
	@Override
	public int print(final Graphics g, final PageFormat format, final int index) throws PrinterException
	{
		final int pagenum = index + 1;
		if (pagenum < 1 || pagenum > pdfFile.getNumPages())
		{
			return NO_SUCH_PAGE;
		}

		final PDFPage pdfPage = pdfFile.getPage(pagenum);

		// task 08618: generally don't resize the PDF to fit to the page format. The jasper was made to have a particular size.
		//
		// However, as we always scaled to the page format and some jaspers might rely on it,
		// we do scale *down* if the PDF is bigger than the page format.
		// note that we only scale if both the page format's height *and* width are smaller
		final Dimension scaledToPageFormat = pdfPage.getUnstretchedSize(
				(int)format.getImageableWidth(),
				(int)format.getImageableHeight(),
				pdfPage.getPageBox());

		final int widthToUse;
		final int heightToUse;
		if (scaledToPageFormat.getWidth() <= pdfPage.getWidth()
				&& scaledToPageFormat.getHeight() <= pdfPage.getHeight())
		{
			// scale down to fit the page format
			widthToUse = (int)scaledToPageFormat.getWidth();
			heightToUse = (int)scaledToPageFormat.getHeight();
		}
		else
		{
			// *don't* scale up to fit the page format
			widthToUse = (int)pdfPage.getWidth();
			heightToUse = (int)pdfPage.getHeight();
		}

		final Rectangle bounds = new Rectangle(
				(int)format.getImageableX() + calX,
				(int)format.getImageableY() + calY,
				widthToUse,
				heightToUse);

		final Graphics2D g2d = (Graphics2D)g;

		final PDFRenderer pdfRenderer = new PDFRenderer(
				pdfPage,
				g2d,
				bounds,
				null, // Rectangle2D clip
				null // Color bgColor
		);

		try
		{
			pdfPage.waitForFinish();
			pdfRenderer.run();
		}
		catch (final InterruptedException ie)
		{
			throw new RuntimeException(ie);
		}

		// debugging: print a rectangle around the whole thing
		// g2d.draw(new Rectangle2D.Double(
		// format.getImageableX(),
		// format.getImageableY(),
		// format.getImageableWidth(),
		// format.getImageableHeight()));

		return PAGE_EXISTS;
	}

	public int getNumPages()
	{
		return pdfFile.getNumPages();
	}

}
