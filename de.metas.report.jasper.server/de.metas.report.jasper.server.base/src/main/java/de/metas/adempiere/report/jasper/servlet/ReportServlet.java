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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.server.LocalJasperServer;

public class ReportServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -331002076463543429L;

//	private final CLogger logger = CLogger.getCLogger(getClass());

	private LocalJasperServer server = null;

	@Override
	public void init(ServletConfig config) throws ServletException
	{
//		// 03301 explicitly registering the font
//		// note that the TTF file is currently located in adempiereJAsper-commons
//		try
//		{
//			final Font ocrb = Font.createFont(Font.TRUETYPE_FONT, ReportServlet.class.getResourceAsStream("/OCRB1_Demo.TTF"));
//			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(ocrb);
//			logger.info("Registered font " + ocrb);
//		}
//		catch (FontFormatException e1)
//		{
//			throw new AdempiereException(e1);
//		}
//		catch (IOException e1)
//		{
//			throw new AdempiereException(e1);
//		}
//		// 03301 end
		
		//
		// we don't restrict set of entity types because this webapp is running in the same server as the rest of adempiere.
		// and there are a lot of static variables we can't get easily rid of
		// with this war happends to be started first, and it starts up with only this set of entity types, we are in trouble.
//		//
//		// 03023: Only following entity types are required for this module:
//		Env.getCtx().put(ModelValidationEngine.CTX_InitEntityTypes, Arrays.asList(
//				"D", // Dictionary
//				"C", // Adempiere
//				"U", // User maintained
//				"A" // Applications
//		));

		// System.setProperty("PropertyFile", "d:/sources/workspace/ad_it/Adempiere_zk.properties");
		Env.getSingleAdempiereInstance().startup(RunMode.BACKEND);
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
