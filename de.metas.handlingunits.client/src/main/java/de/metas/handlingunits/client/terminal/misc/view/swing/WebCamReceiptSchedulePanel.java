/**
 *
 */
package de.metas.handlingunits.client.terminal.misc.view.swing;

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


import org.adempiere.exceptions.AdempiereException;

import com.github.sarxos.webcam.WebcamPanel;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalDialogListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.misc.model.WebCamReceiptScheduleModel;

/**
 * @author lc
 *
 */
public class WebCamReceiptSchedulePanel
		extends TerminalDialogListenerAdapter
		implements IComponent
{
	private final WebCamReceiptScheduleModel model;
	private final IContainer panel;
	private final WebcamPanel webCamPanel;
	private final ITerminalFactory factory;

	private boolean disposed = false;

	public static final String MSG_ErrorSavingPicture = "de.metas.handlingunits.client.WebcamReceiptSchedule.ErrorSavingPicture";

	public WebCamReceiptSchedulePanel(final WebCamReceiptScheduleModel model)
	{
		super();

		this.model = model;

		final ITerminalContext terminalContext = model.getTerminalContext();
		factory = terminalContext.getTerminalFactory();

		// init components
		{
			panel = factory.createContainer();

			webCamPanel = new WebcamPanel(model.getWebcam());
			webCamPanel.setFillArea(true);
			webCamPanel.setFPS(model.getFPS());
		}

		initLayout();

		terminalContext.addToDisposableComponents(this);
	}

	private void initLayout()
	{
		panel.add(factory.wrapComponent(webCamPanel), "dock north, grow, wrap");
	}

	@Override
	public Object getComponent()
	{
		return panel.getComponent();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return model.getTerminalContext();
	}

	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		try
		{
			model.savePicture();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("@" + MSG_ErrorSavingPicture + "@", e);
		}
		model.dispose();
	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		return true; // allow canceling
	}

	@Override
	public void dispose()
	{
		if (webCamPanel != null)
		{
			// TODO: remove the WebCam listener
			webCamPanel.stop();
			webCamPanel.removeAll();
		}
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}
}
