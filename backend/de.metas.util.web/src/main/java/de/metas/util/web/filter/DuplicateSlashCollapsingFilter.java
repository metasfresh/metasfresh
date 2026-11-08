/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2026 metas GmbH
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

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.server.RequestPath;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ServletRequestPathUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Collapses runs of {@code /} in the request path ({@code /api//v2/version} becomes {@code /api/v2/version}).
 * <p>
 * Spring 5 / Spring Boot 2 matched handlers on the lookup path from {@code UrlPathHelper}, which replaces every
 * {@code //} by {@code /} ({@code UrlPathHelper.getSanitizedPath}). Spring Boot 3 matches with
 * {@code PathPatternParser} on the raw {@link HttpServletRequest#getRequestURI()}, which Tomcat does not normalize,
 * so a path with a double slash matches no controller and falls through to the static resource handler
 * ({@code NoResourceFoundException}). Reverse proxies produce such paths, e.g. nginx
 * {@code location /rest { proxy_pass http://webapi:8080/rest/; }} forwards {@code /rest/api/x} as {@code /rest//api/x}.
 * <p>
 * Like Spring's {@code UrlHandlerFilter} in {@code wrapRequest} mode, the request is wrapped (no redirect, API clients
 * must not be bounced), so the whole downstream filter chain and the {@code DispatcherServlet} see the normalized path.
 * Only literal slashes are collapsed; percent-encoded slashes ({@code %2F}) are left alone.
 */
public final class DuplicateSlashCollapsingFilter extends OncePerRequestFilter
{
	private static final Pattern DUPLICATE_SLASHES = Pattern.compile("/{2,}");

	@Override
	protected void doFilterInternal(
			@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response,
			@NonNull final FilterChain chain) throws ServletException, IOException
	{
		final String requestURI = request.getRequestURI();
		if (requestURI == null || !requestURI.contains("//"))
		{
			chain.doFilter(request, response);
			return;
		}

		final HttpServletRequest wrappedRequest = new DuplicateSlashCollapsingHttpServletRequest(request);

		// a RequestPath parsed from the raw URI might have been cached by an upstream filter; force a re-parse from the collapsed URI
		final RequestPath previousPath = (RequestPath)wrappedRequest.getAttribute(ServletRequestPathUtils.PATH_ATTRIBUTE);
		ServletRequestPathUtils.clearParsedRequestPath(wrappedRequest);
		try
		{
			chain.doFilter(wrappedRequest, response);
		}
		finally
		{
			if (previousPath != null)
			{
				ServletRequestPathUtils.setParsedRequestPath(previousPath, wrappedRequest);
			}
		}
	}

	@Nullable
	static String collapseDuplicateSlashes(@Nullable final String path)
	{
		return path != null && path.contains("//")
				? DUPLICATE_SLASHES.matcher(path).replaceAll("/")
				: path;
	}

	/**
	 * Collapses only the path part of the URL, so the {@code //} after the scheme ({@code https://}) is kept.
	 */
	static StringBuffer collapseDuplicateSlashesInURL(@NonNull final StringBuffer requestURL)
	{
		final String url = requestURL.toString();
		final int schemeSeparatorIdx = url.indexOf("://");
		final int pathStartIdx = schemeSeparatorIdx < 0 ? 0 : url.indexOf('/', schemeSeparatorIdx + 3);
		if (pathStartIdx < 0)
		{
			return new StringBuffer(url);
		}
		return new StringBuffer(url.substring(0, pathStartIdx)).append(collapseDuplicateSlashes(url.substring(pathStartIdx)));
	}

	private static final class DuplicateSlashCollapsingHttpServletRequest extends HttpServletRequestWrapper
	{
		private DuplicateSlashCollapsingHttpServletRequest(@NonNull final HttpServletRequest request)
		{
			super(request);
		}

		private HttpServletRequest getDelegate() {return (HttpServletRequest)getRequest();}

		/**
		 * Same as {@code UrlHandlerFilter}'s wrapper: on a FORWARD dispatch the container has set the forward target's paths; report them unchanged.
		 */
		private boolean isForward() {return getDispatcherType() == DispatcherType.FORWARD;}

		@Override
		public String getRequestURI()
		{
			return isForward() ? getDelegate().getRequestURI() : collapseDuplicateSlashes(getDelegate().getRequestURI());
		}

		@Override
		public StringBuffer getRequestURL()
		{
			return isForward() ? getDelegate().getRequestURL() : collapseDuplicateSlashesInURL(getDelegate().getRequestURL());
		}

		@Override
		public String getServletPath()
		{
			return isForward() ? getDelegate().getServletPath() : collapseDuplicateSlashes(getDelegate().getServletPath());
		}

		@Override
		public String getPathInfo()
		{
			return isForward() ? getDelegate().getPathInfo() : collapseDuplicateSlashes(getDelegate().getPathInfo());
		}
	}
}
