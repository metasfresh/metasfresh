/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.util.web.filter;

import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.web.audit.ApiAuditService;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ApiAuditFilter implements Filter
{
	private final static Logger logger = LogManager.getLogger(ApiAuditFilter.class);

	private final ApiAuditService apiAuditService;

	public ApiAuditFilter(final ApiAuditService apiAuditService)
	{
		this.apiAuditService = apiAuditService;
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException
	{
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		try
		{
			if (apiAuditService.bypassFilter(httpServletRequest))
			{
				chain.doFilter(request, response);
				return;
			}

			final Optional<ApiRequestAuditId> requestAuditIdOpt = apiAuditService.extractApiRequestAuditId(httpServletRequest);

			// dev-note: this means the request was already filtered once
			if (requestAuditIdOpt.isPresent())
			{
				final ApiAuditLoggable apiAuditLoggable = apiAuditService.createLogger(requestAuditIdOpt.get(), Env.getLoggedUserId());

				try (final IAutoCloseable loggableRestorer = Loggables.temporarySetLoggable(apiAuditLoggable))
				{
					chain.doFilter(request, response);
					return;
				}
			}

			final Optional<ApiAuditConfig> matchingAuditConfig = apiAuditService.getMatchingAuditConfig(httpServletRequest);

			if (!matchingAuditConfig.isPresent())
			{
				chain.doFilter(request, response);
				return;
			}

			apiAuditService.processHttpCall(httpServletRequest, httpServletResponse, matchingAuditConfig.get());
		}
		catch (final Throwable t)
		{
			logger.error(t.getLocalizedMessage(), t);

			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
		}
	}

	@Override
	public void destroy()
	{

	}
}
