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

import ch.qos.logback.classic.Level;
import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.web.audit.ApiAuditService;
import de.metas.util.web.audit.ResponseHandler;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ApiAuditFilter implements Filter
{
	private final static Logger logger = LogManager.getLogger(ApiAuditFilter.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ApiAuditService apiAuditService;

	private static final String SYSCONFIG_BypassAll = "ApiAuditFilter.bypassAll";

	public ApiAuditFilter(final ApiAuditService apiAuditService)
	{
		this.apiAuditService = apiAuditService;
	}

	@Override
	public void init(final FilterConfig filterConfig)
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException
	{
		final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		try
		{
			if (isBypassAll() || apiAuditService.bypassFilter(httpServletRequest))
			{
				chain.doFilter(request, response);
				return;
			}

			final ApiRequestAuditId requestAuditId = apiAuditService.extractApiRequestAuditId(httpServletRequest).orElse(null);
			final Optional<UserId> loggedUserId = Env.getLoggedUserIdIfExists();

			// dev-note: this means the request was already filtered once
			if (requestAuditId != null)
			{
				final ApiAuditLoggable apiAuditLoggable;
				if (loggedUserId.isPresent())
				{
					apiAuditLoggable = apiAuditService.createLogger(requestAuditId, loggedUserId.get());
				}
				else
				{
					apiAuditLoggable = apiAuditService.createLogger(requestAuditId, UserId.METASFRESH);
					Loggables
							.withLogger(apiAuditLoggable, logger, Level.WARN)
							.addLog("Request contains ApiRequestAuditId={}, but there is no logged-in user - logging with AD_User_ID=", requestAuditId.getRepoId(), UserId.METASFRESH.getRepoId());
				}

				try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(apiAuditLoggable))
				{
					chain.doFilter(request, response);
					return;
				}
			}

			final ApiAuditConfig matchingAuditConfig = apiAuditService.getMatchingAuditConfig(httpServletRequest).orElse(null);
			if (matchingAuditConfig == null || matchingAuditConfig.isBypassAudit())
			{
				chain.doFilter(request, response);
				return;
			}

			apiAuditService.processRequest(chain, httpServletRequest, httpServletResponse, matchingAuditConfig);
		}
		catch (final Throwable t)
		{
			logger.error(t.getLocalizedMessage(), t);

			ResponseHandler.writeErrorResponse(t, httpServletResponse, null, null);
		}
	}

	@Override
	public void destroy()
	{

	}

	private boolean isBypassAll()
	{
		//if(true) return true;
		return sysConfigBL.getBooleanValue(SYSCONFIG_BypassAll, false);
	}
}
