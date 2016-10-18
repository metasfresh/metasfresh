/**
 *
 */
package de.metas.handlingunits.client.terminal.misc.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.model.I_AD_Archive;

import com.github.sarxos.webcam.Webcam;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WebCamReceiptScheduleModel implements IDisposable
{
	private static final String ERR_WEBCAM_NOT_FOUND = "WebcamNotFound";

	private final ITerminalContext terminalContext;

	private final WeakPropertyChangeSupport pcs;

	private Webcam webcam;

	private Object referencedModel;

	private boolean disposed = false;

	/**
	 *
	 * @param terminalContext
	 * @param referenceModel this model will be used to attach to it
	 */
	public WebCamReceiptScheduleModel(final ITerminalContext terminalContext, final Object referenceModel)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		referencedModel = referenceModel;

		terminalContext.addToDisposableComponents(this);
	}

	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public Webcam getWebcam()
	{
		if (webcam == null)
		{
			webcam = Webcam.getDefault();
			if (webcam == null)
			{
				throw new AdempiereException("@" + ERR_WEBCAM_NOT_FOUND + "@");
			}
			//
			// Set to maximum allowed size (if any)
			final Dimension[] viewSizes = webcam.getViewSizes();
			if (viewSizes != null && viewSizes.length > 0)
			{
				// they're ordered by size, so get the last one (maximum)
				final Dimension viewSize = viewSizes[viewSizes.length - 1];
				webcam.setViewSize(viewSize);
			}
		}

		return webcam;
	}

	public double getFPS()
	{
		return 25;
	}

	public void savePicture()
	{
		webcam.open();
		final BufferedImage image = webcam.getImage();
		final byte[] imagePDFBytes = toPDFBytes(image);

		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.create(getReferencedModel(), I_M_ReceiptSchedule.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);

		final IArchiveStorage archiveStorage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx);
		final I_AD_Archive archive = archiveStorage.newArchive(ctx, trxName);

		final int tableID = InterfaceWrapperHelper.getModelTableId(receiptSchedule);
		final int recordID = InterfaceWrapperHelper.getId(receiptSchedule);
		archive.setAD_Table_ID(tableID);
		archive.setRecord_ID(recordID);
		archive.setC_BPartner_ID(receiptSchedule.getC_BPartner_ID());
		archive.setAD_Org_ID(receiptSchedule.getAD_Org_ID());
		archive.setName("Photo_" + UUID.randomUUID() + ".pdf");
		archive.setIsReport(false);
		archiveStorage.setBinaryData(archive, imagePDFBytes);
		InterfaceWrapperHelper.save(archive);
	}

	/**
	 * Converts given image to PDF.
	 *
	 * @param image
	 * @return PDF file as bytes array.
	 */
	private static final byte[] toPDFBytes(final BufferedImage image)
	{
		try
		{
			//
			// PDF Image
			final ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
			ImageIO.write(image, "PNG", imageBytes);
			final Image pdfImage = Image.getInstance(imageBytes.toByteArray());

			//
			// PDF page size: image size + margins
			final Rectangle pageSize = new Rectangle(0, 0,
					pdfImage.getWidth() + 100,
					pdfImage.getHeight() + 100);

			// PDF document
			final Document document = new Document(pageSize, 50, 50, 50, 50);

			//
			// Add image to document
			final ByteArrayOutputStream pdfBytes = new ByteArrayOutputStream();
			final PdfWriter writer = PdfWriter.getInstance(document, pdfBytes);
			writer.open();
			document.open();
			document.add(pdfImage);
			document.close();
			writer.close();

			return pdfBytes.toByteArray();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed converting the image to PDF", e);
		}
	}

	@Override
	public void dispose()
	{
		if (webcam != null)
		{
			webcam.close();
			webcam = null;
		}

		referencedModel = null;
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	public Object getReferencedModel()
	{
		return referencedModel;
	}

}
