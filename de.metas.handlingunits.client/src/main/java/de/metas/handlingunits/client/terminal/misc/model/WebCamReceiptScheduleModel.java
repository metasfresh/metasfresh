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
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import com.github.sarxos.webcam.Webcam;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;

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
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.create(getReferencedModel(), I_M_ReceiptSchedule.class);
		final String filename = "Photo_" + UUID.randomUUID() + ".pdf";
		final BufferedImage image = webcam.getImage();
		Services.get(IHUReceiptScheduleBL.class).attachPhoto(receiptSchedule, filename, image);
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
