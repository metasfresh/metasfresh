package org.adempiere.webui.session;

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


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implement this interface if you want to control the lifecycle of WebUIServlet.
 * 
 * Also please check {@link WebUIServletListeners} documentation for using the proper package name and classname conventions.
 * 
 * @author tsa
 * 
 * @see WebUIServletListeners
 * @see WebUIServletListeners#CLASSNAME
 */
public interface IWebUIServletListener
{
	/**
	 * Method called when WebUIServlet is initialized
	 * 
	 * @param servletConfig
	 */
	void init(ServletConfig servletConfig);

	/**
	 * Method called when WebUIServlet is destroyed
	 */
	void destroy();
	
    void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
