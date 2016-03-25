package de.metas.adempiere.report.jasper.servlet;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.util.Ini.IsNotSwingClient;
import org.springframework.context.annotation.Conditional;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.JasperServerConstants;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.server.LocalJasperServer;
import net.sf.jasperreports.engine.JRException;

@Conditional(IsNotSwingClient.class)
@WebServlet(value = JasperServerConstants.SERVLET_ROOT + "/ReportServlet", loadOnStartup = 1)
public class ReportServlet extends HttpServlet
{

	private static final long serialVersionUID = -331002076463543429L;

	private LocalJasperServer server = null;

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		server = new LocalJasperServer();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	// Test URL:
	// http://localhost:8080/adempiereJasper/ReportServlet?AD_Process_ID=500007&Record_ID=1000000&output=pdf

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int processId = getParameterAsInt(req, "AD_Process_ID");
		int pinstanceId = getParameterAsInt(req, "AD_PInstance_ID");
		// final int tableId = getParameterAsInt(req, "AD_Table_ID");
		// int recordId = getParameterAsInt(req, "Record_ID");

		final String language = req.getParameter("AD_Language");
		final String output = req.getParameter("output");

		final OutputType outputType = output == null ? IJasperServer.DEFAULT_OutputType : OutputType.valueOf(output);
		try
		{
			byte[] data = server.report(processId, pinstanceId, language, outputType);
			createOutput(resp, data, outputType);
		}
		catch (Exception e)
		{
			throw new ServletException(e);
		}
	}

	private void createOutput(final HttpServletResponse response, final byte[] data, final OutputType outputType) throws JRException, IOException
	{
		final OutputStream out = response.getOutputStream();

		final String contentType = outputType.getContentType();
		response.setHeader("Content-Type", contentType);
		out.write(data);
		out.flush();
		out.close();
	}

	private int getParameterAsInt(HttpServletRequest req, String name)
	{
		String value = req.getParameter(name);
		if (value == null)
			return -1;
		try
		{
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
