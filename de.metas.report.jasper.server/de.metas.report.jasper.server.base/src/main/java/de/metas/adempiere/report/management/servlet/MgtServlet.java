package de.metas.adempiere.report.management.servlet;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.util.CacheMgt;

import de.metas.adempiere.report.jasper.JasperConstants;

/**
 * This servlet was introduced for managing the actions that the other Jasper servlets can take.
 * 
 * @author RC
 *
 */
public class MgtServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1652716813425364152L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String action = req.getParameter(JasperConstants.MGTSERVLET_PARAM_Action);
		if (action == null)
		{
			throw new RuntimeException("Please use "+JasperConstants.MGTSERVLET_PARAM_Action+" parameter to specify which action to run");
		}
		
		if (JasperConstants.MGTSERVLET_ACTION_CacheReset.equalsIgnoreCase(action))
		{
			cacheReset();
			resp.getOutputStream().write("OK".getBytes());
		}
		else
		{
			throw new RuntimeException("Action not supported: "+action);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	private void cacheReset()
	{
		CacheMgt.get().reset();
	}
}
