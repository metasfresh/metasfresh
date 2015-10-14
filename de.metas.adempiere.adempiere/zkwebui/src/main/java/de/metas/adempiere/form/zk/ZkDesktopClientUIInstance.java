package de.metas.adempiere.form.zk;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.session.SessionManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;

public class ZkDesktopClientUIInstance extends AbstractZkClientUIInstance
{
	private final Desktop desktop;
	private final IDesktop appDesktop;

	public ZkDesktopClientUIInstance()
	{
		super();
		this.desktop = retrieveCurrentExecutionDesktop();
		this.appDesktop = SessionManager.getAppDesktop();

	}

	@Override
	protected Desktop getDesktop()
	{
		return desktop;
	}

	@Override
	protected Component getEventComponent()
	{
		return appDesktop.getComponent();
	}

	@Override
	protected Execution getExecution()
	{
		return desktop.getExecution();
	}

	@Override
	public void showURL(final String url)
	{
		final boolean closeable = true;
		appDesktop.showURL(url, closeable);
	}
}
