package de.metas.procurement.webui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.spring.servlet.Vaadin4SpringServlet;

import com.vaadin.addon.touchkit.settings.TouchKitSettings;

/*
 * #%L
 * de.metas.procurement.webui
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
 * License: http://www.wtfpl.net
 *
 * @author Matti Tahvonen
 */
@SuppressWarnings("serial")
public class SpringAwareTouchKitServlet extends Vaadin4SpringServlet
{

	TouchKitSettings touchKitSettings;

	@Override
	protected void servletInitialized() throws ServletException
	{
		super.servletInitialized();
		touchKitSettings = new TouchKitSettings(getService());
	}

	@Override
	protected void service(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException
	{
		final String pathInfo = request.getPathInfo();
		if (pathInfo != null)
		{
			if (pathInfo.endsWith("themes/touchkit/styles.css"))
			{
				serveDummyFile(response, "max-age=1000000");
				return;
			}
			else if (pathInfo.endsWith("PING/"))
			{
				serveDummyFile(response, "no-store, no-cache, max-age=0, must-revalidate");
				return;
			}
		}
		super.service(request, response);
	}

	private void serveDummyFile(final HttpServletResponse response, final String cacheControl) throws IOException
	{
		response.setContentType("text/css");
		response.setHeader("Cache-Control", cacheControl);
		response.getOutputStream().write("\n".getBytes());
	}

}
