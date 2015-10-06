package de.metas.swat;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.util.Services;
import org.adempiere.webui.session.IWebUIServletListener;
import org.compiere.report.ReportStarter;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.zk.ZKClientUI;
import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.impl.ZkHostKeyStorage;
import de.metas.web.component.FilePreviewJRViewerProvider;

public class WebUIServletListener implements IWebUIServletListener
{

	@Override
	public void init(final ServletConfig servletConfig)
	{
		// ReportStarter.setReportViewerProvider(new ZkJRViewerProvider()); // old
		ReportStarter.setReportViewerProvider(new FilePreviewJRViewerProvider()); // 03874
		Services.registerService(IClientUI.class, new ZKClientUI());
	}

	@Override
	public void destroy()
	{
		// nothing
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		createUpdateHostKey(request, response);
	}

	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		createUpdateHostKey(request, response);
	}

	private void createUpdateHostKey(final HttpServletRequest request, final HttpServletResponse response)
	{
		final IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);

		//
		// Create HostKey if needed
		final ZkHostKeyStorage servletHostkeyStorage = new ZkHostKeyStorage(request, response);
		hostKeyBL.getCreateHostKey(servletHostkeyStorage);

		//
		// Configure default storage (which fetches hostkey from current Execution)
		hostKeyBL.setHostKeyStorage(new ZkHostKeyStorage());
	}
}
