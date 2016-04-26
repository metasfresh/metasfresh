package de.metas.ui.web.base.util.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.exceptions.AdempiereException;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;

import de.metas.ui.web.base.util.IHttpSessionProvider;

/*
 * #%L
 * test_vaadin
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

public class HttpSessionProvider implements IHttpSessionProvider
{

	@Override
	public HttpServletRequest getCurrentRequest()
	{
		final VaadinRequest request = VaadinService.getCurrentRequest();
		if (request instanceof VaadinServletRequest)
		{
			return (VaadinServletRequest)request;
		}
		else
		{
			throw new AdempiereException("Invalid request: " + request);
		}
	}

	@Override
	public HttpServletResponse getCurrentResponse()
	{
		final VaadinResponse response = VaadinService.getCurrentResponse();
		if (response instanceof VaadinServletResponse)
		{
			return (VaadinServletResponse)response;
		}
		else
		{
			throw new AdempiereException("Invalid response: " + response);
		}
	}

}
