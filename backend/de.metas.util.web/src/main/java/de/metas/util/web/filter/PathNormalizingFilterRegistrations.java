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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.UrlHandlerFilter;

/**
 * Registrations of the two filters that normalize a REST request path before any other filter and the handler mapping see it:
 * first {@link DuplicateSlashCollapsingFilter}, then the trailing-slash stripping {@link UrlHandlerFilter}.
 * <p>
 * The trailing-slash filter runs after the duplicate-slash one, because a path like {@code /api//v2/x/} only matches its
 * {@code PathPattern} once the slashes are collapsed.
 */
@UtilityClass
public class PathNormalizingFilterRegistrations
{
	public static final int ORDER_DUPLICATE_SLASH = Ordered.HIGHEST_PRECEDENCE;
	public static final int ORDER_TRAILING_SLASH = ORDER_DUPLICATE_SLASH + 1;

	/**
	 * @param servletUrlPattern servlet URL pattern the filter is mapped to, e.g. {@code /api/*}
	 */
	public static FilterRegistrationBean<DuplicateSlashCollapsingFilter> duplicateSlashRegistration(@NonNull final String servletUrlPattern)
	{
		final FilterRegistrationBean<DuplicateSlashCollapsingFilter> registrationBean = new FilterRegistrationBean<>(new DuplicateSlashCollapsingFilter());
		registrationBean.addUrlPatterns(servletUrlPattern);
		registrationBean.setOrder(ORDER_DUPLICATE_SLASH);
		return registrationBean;
	}

	/**
	 * Spring 6.0 switched trailing-slash matching off by default and deprecated {@code PathMatchConfigurer.setUseTrailingSlashMatch}
	 * (removed in Spring 7), so {@code GET /api/x/} no longer matches {@code @GetMapping("/x")} and fails with {@code NoResourceFoundException}.
	 * The returned filter strips the trailing slash in {@code wrapRequest} mode - not as a redirect, because API clients must not be bounced with a 308.
	 *
	 * @param servletUrlPattern servlet URL pattern the filter is mapped to, e.g. {@code /api/*}
	 * @param pathPattern       {@link org.springframework.web.util.pattern.PathPattern} of the paths whose trailing slash is stripped, e.g. {@code /api/**}
	 */
	public static FilterRegistrationBean<UrlHandlerFilter> trailingSlashRegistration(
			@NonNull final String servletUrlPattern,
			@NonNull final String pathPattern)
	{
		final UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter
				.trailingSlashHandler(pathPattern)
				.wrapRequest()
				.build();

		final FilterRegistrationBean<UrlHandlerFilter> registrationBean = new FilterRegistrationBean<>(urlHandlerFilter);
		registrationBean.addUrlPatterns(servletUrlPattern);
		registrationBean.setOrder(ORDER_TRAILING_SLASH);
		return registrationBean;
	}
}
