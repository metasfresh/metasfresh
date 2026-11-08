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

import de.metas.Profiles;
import de.metas.i18n.ILanguageDAO;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.audit.ApiAuditService;
import de.metas.util.web.security.UserAuthTokenFilter;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.UrlHandlerFilter;

@Profile(Profiles.PROFILE_App)
@Configuration
public class FilterRegistrationConfig
{
	/** {@link org.springframework.web.util.pattern.PathPattern} that matches every path under {@code /api} (any depth). */
	private static final String URL_PATHPATTERN_API = "/api/**";

	private final ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);

	/**
	 * Restores trailing-slash tolerance for the REST API, e.g. {@code GET /api/v2/bpartner/byOrg/001/?since=…} must match
	 * {@code @GetMapping("/byOrg/{orgCode}")}. See {@link PathNormalizingFilterRegistrations#trailingSlashRegistration(String, String)}.
	 */
	@Bean
	public FilterRegistrationBean<UrlHandlerFilter> trailingSlashApiUrlHandlerFilter()
	{
		return PathNormalizingFilterRegistrations.trailingSlashRegistration(MetasfreshRestAPIConstants.URL_PATTERN_API, URL_PATHPATTERN_API);
	}

	/**
	 * Restores the Spring Boot 2 tolerance for duplicate slashes in the REST API path, e.g. {@code /api//v2/version}
	 * as sent by a reverse proxy that maps {@code /app/api/...} to {@code /api//...}. See {@link DuplicateSlashCollapsingFilter}.
	 */
	@Bean
	public FilterRegistrationBean<DuplicateSlashCollapsingFilter> duplicateSlashApiFilter()
	{
		return PathNormalizingFilterRegistrations.duplicateSlashRegistration(MetasfreshRestAPIConstants.URL_PATTERN_API);
	}

	// NOTE: we are using standard spring CORS filter
	// @Bean
	// public FilterRegistrationBean<CORSFilter> corsFilter()
	// {
	// 	final FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>();
	// 	registrationBean.setFilter(new CORSFilter());
	// 	registrationBean.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API);
	// 	registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	// 	return registrationBean;
	// }

	@Bean
	public FilterRegistrationBean<UserAuthTokenFilter> authFilter(
			@NonNull final UserAuthTokenService userAuthTokenService,
			@NonNull final UserAuthTokenFilterConfiguration configuration)
	{
		final FilterRegistrationBean<UserAuthTokenFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new UserAuthTokenFilter(userAuthTokenService, configuration, languageDAO));
		registrationBean.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API);
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<ApiAuditFilter> apiAuditFilter(@NonNull final ApiAuditService apiAuditService)
	{
		final FilterRegistrationBean<ApiAuditFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ApiAuditFilter(apiAuditService));
		registrationBean.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API_V2);
		registrationBean.setOrder(3);
		return registrationBean;
	}
}
