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

import de.metas.audit.config.ApiAuditConfig;
import de.metas.util.web.audit.ApiAuditService;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class ApiAuditFilter implements Filter
{
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
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		final ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);

		final Optional<ApiAuditConfig> matchingAuditConfig = apiAuditService.getMatchingAuditConfig(contentCachingRequestWrapper);

		if (!matchingAuditConfig.isPresent()
				|| apiAuditService.wasAlreadyFiltered(contentCachingRequestWrapper))
		{
			chain.doFilter(request, response);
			return;
		}

		apiAuditService.handleRequest(request, response, matchingAuditConfig.get());
	}

	@Override
	public void destroy()
	{

	}
}
