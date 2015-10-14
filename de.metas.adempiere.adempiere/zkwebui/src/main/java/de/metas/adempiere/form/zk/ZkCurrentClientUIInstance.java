package de.metas.adempiere.form.zk;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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

public class ZkCurrentClientUIInstance extends AbstractZkClientUIInstance
{
	public ZkCurrentClientUIInstance()
	{
		super();
	}

	@Override
	protected Desktop getDesktop()
	{
		return retrieveCurrentExecutionDesktop();
	}

	@Override
	protected Component getEventComponent()
	{
		final IDesktop appDesktop = SessionManager.getAppDesktop();
		return appDesktop.getComponent();
	}

	@Override
	protected Execution getExecution()
	{
		return retrieveCurrentExecution();
	}
	

	@Override
	public void showURL(final String url)
	{
		final IDesktop appDesktop = SessionManager.getAppDesktop();
		final boolean closeable = true;
		appDesktop.showURL(url, closeable);
	}

}
