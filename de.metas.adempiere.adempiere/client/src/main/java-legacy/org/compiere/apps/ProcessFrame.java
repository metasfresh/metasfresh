package org.compiere.apps;

import java.awt.Window;

import org.compiere.swing.CFrame;
import org.compiere.util.Env;

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
 * Process starter - frame (i.e. not modal dialog).
 * 
 * To create a new instance, please use {@link ProcessDialog#builder()}
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
class ProcessFrame extends CFrame implements ProcessDialog, ProcessPanelWindow
{
	private final ProcessPanel panel;
	private final Integer windowNoCreatedHere;

	public ProcessFrame(final ProcessDialogBuilder builder)
	{
		super();

		if (builder.getWindowNo() <= 0 || builder.getWindowNo() == Env.WINDOW_None)
		{
			windowNoCreatedHere = Env.createWindowNo(this);
			builder.setWindowAndTabNo(windowNoCreatedHere);
		}
		else
		{
			windowNoCreatedHere = null;
		}

		panel = builder
				.setWindow(this)
				.buildPanel();
	}

	@Override
	public void dispose()
	{
		panel.dispose();
		super.dispose();

		if (windowNoCreatedHere != null)
		{
			Env.clearWinContext(windowNoCreatedHere);
		}
	}

	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		panel.setVisible(visible);
	}

	@Override
	public void enableWindowEvents(final long eventsToEnable)
	{
		enableEvents(eventsToEnable);
	}

	@Override
	public Window asAWTWindow()
	{
		return this;
	}

	public boolean isDisposed()
	{
		return panel.isDisposed();
	}

	@Override
	public void showCenterScreen()
	{
		validate();
		pack();
		AEnv.showCenterScreen(this);
	}
}
