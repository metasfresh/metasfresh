/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.cache.ThreadLocalCacheController;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static de.metas.common.rest_api.v2.APIConstants.CACHE_CONTROL_NO_CACHE;

public class CacheControlFilter implements Filter
{
	private final static Logger logger = LogManager.getLogger(CacheControlFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException
	{
		logger.info(CacheControlFilter.class.getSimpleName() + " initialized!");
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		try (final IAutoCloseable ignored = temporarySetCacheModeIfSpecified((HttpServletRequest)request))
		{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy()
	{
	}

	@NonNull
	private IAutoCloseable temporarySetCacheModeIfSpecified(@NonNull final HttpServletRequest request)
	{
		final String cacheControl = request.getHeader(HttpHeaders.CACHE_CONTROL);
		if (CACHE_CONTROL_NO_CACHE.equals(cacheControl))
		{
			logger.debug("Temporary disabling cache");
			return ThreadLocalCacheController.instance.temporaryDisableCache();
		}
		else
		{
			//do nothing
			return () -> {};
		}
	}
}
