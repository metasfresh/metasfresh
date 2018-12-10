package de.metas.ui.web.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class CORSFilter implements Filter
{
	private static final transient Logger logger = LogManager.getLogger(CORSFilter.class);

	private static final String SYSCONFIG_IsCORSEnabled = "webui.frontend.cors.enabled";

	private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	public CORSFilter()
	{
	}

	@Override
	public void init(final FilterConfig filterConfig)
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		if (response instanceof HttpServletResponse)
		{
			final HttpServletRequest httpRequest = (HttpServletRequest)request;
			final HttpServletResponse httpResponse = (HttpServletResponse)response;

			setCORSHeaders(httpRequest, httpResponse);
		}

		chain.doFilter(request, response);
	}

	private void setCORSHeaders(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse)
	{
		final String requestOrigin = httpRequest.getHeader("Origin");
		final String accessControlAllowOrigin = getAccessControlAllowOrigin(requestOrigin);
		httpResponse.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
		logger.trace("Set Access-Control-Allow-Origin={} (request's Origin={})", accessControlAllowOrigin, requestOrigin);

		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");

		// NOTE: Access-Control-Max-Age is browser dependent and each browser as a different max value.
		// e.g. chrome allows max 600sec=10min and it might be that everything above that is ignored.
		// see http://stackoverflow.com/questions/23543719/cors-access-control-max-age-is-ignored
		httpResponse.setHeader("Access-Control-Max-Age", "600");

		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Origin, Accept-Language");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true"); // allow cookies
	}

	private String getAccessControlAllowOrigin(final String requestOrigin)
	{
		if (isCORSEnabled())
		{
			final String frontendURL = webuiURLs.getFrontendURL();
			if (!Check.isEmpty(frontendURL, true))
			{
				return frontendURL;
			}
			else
			{
				logger.warn("Accepting any CORS Origin because even though CORS are enabled, the FrontendURL is not set.\n Please and set `{}` sysconfig or disable CORS (`{}` sysconfig).", WebuiURLs.SYSCONFIG_FRONTEND_URL, SYSCONFIG_IsCORSEnabled);
			}
		}

		// Fallback
		final String accessControlAllowOrigin = Check.isEmpty(requestOrigin, true) ? "*" : requestOrigin;
		return accessControlAllowOrigin;
	}

	private boolean isCORSEnabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getBooleanValue(SYSCONFIG_IsCORSEnabled, true);
	}
}
