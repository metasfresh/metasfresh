package de.metas.ui.web.base.util.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import de.metas.ui.web.base.util.IHttpSessionProvider;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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
		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeRequest = execution.getNativeRequest();
		Check.assume(nativeRequest != null, "nativeRequest not null");
		if (nativeRequest instanceof HttpServletRequest)
		{
			return (HttpServletRequest)nativeRequest;
		}
		else
		{
			throw new AdempiereException("Native request is not supported: " + nativeRequest);
		}
	}

	@Override
	public HttpServletResponse getCurrentResponse()
	{
		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeResponse = execution.getNativeResponse();
		Check.assume(nativeResponse != null, "nativeResponse not null");
		if (nativeResponse instanceof HttpServletResponse)
		{
			return (HttpServletResponse)nativeResponse;
		}
		else
		{
			throw new AdempiereException("Native response is not supported: " + nativeResponse);
		}
	}

}
