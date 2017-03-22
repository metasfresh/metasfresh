package de.metas.process.ui;

import java.awt.Window;

import javax.swing.JFrame;

import org.compiere.apps.AEnv;
import org.compiere.swing.CDialog;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Process starter - modal dialog.
 * 
 * To create a new instance, please use {@link ProcessDialog#builder()}
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
class ProcessModalDialog extends CDialog implements ProcessDialog, ProcessPanelWindow
{
	private final JFrame parentFrame;
	private ProcessPanel panel;

	ProcessModalDialog(final JFrame parentFrame, final ProcessDialogBuilder builder)
	{
		super(
				parentFrame,       // owner
				"",       // title
				true // modal
		);
		this.parentFrame = parentFrame;

		panel = builder
				.skipResultsPanel()
				.buildPanel();

		if (panel.isDisposed())
		{
			dispose();
			return;
		}

		panel.installTo(this);
	}

	@Override
	public void enableWindowEvents(final long windowEventMask)
	{
		enableEvents(windowEventMask);
	}

	@Override
	public Window asAWTWindow()
	{
		return this;
	}

	@Override
	public void showCenterScreen()
	{
		validate();
		pack();
		AEnv.showCenterWindow(parentFrame, this);
	}

	@Override
	public void dispose()
	{
		final ProcessPanel panel = this.panel;
		if (panel != null)
		{
			panel.dispose();
			this.panel = null;
		}

		super.dispose();
	}

	public boolean isDisposed()
	{
		final ProcessPanel panel = this.panel;
		return panel == null || panel.isDisposed();
	}

	@Override
	public void setVisible(final boolean visible)
	{
		super.setVisible(visible);
		
		final ProcessPanel panel = this.panel;
		if (panel != null)
		{
			panel.setVisible(visible);
		}
	}
}
