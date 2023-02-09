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

import de.metas.Profiles;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.audit.ApiAuditService;
import de.metas.util.web.github.GithubIssueFilter;
import de.metas.util.web.github.IAuthenticateGithubService;
import de.metas.util.web.security.UserAuthTokenFilter;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER;
import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT;

@Profile(Profiles.PROFILE_App)
@Configuration
public class FilterRegistrationConfig
{
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
	public FilterRegistrationBean<CacheControlFilter> cacheControlFilterFilter()
	{
		final FilterRegistrationBean<CacheControlFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CacheControlFilter());
		registrationBean.setOrder(0);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<GithubIssueFilter> githubIssueFilter(@NonNull final IAuthenticateGithubService authenticateGithubRequest)
	{
		final String syncIssueUrlPattern = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + GITHUB_ISSUE_CONTROLLER + GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT;

		final FilterRegistrationBean<GithubIssueFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new GithubIssueFilter(authenticateGithubRequest));
		registrationBean.addUrlPatterns(syncIssueUrlPattern);
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<UserAuthTokenFilter> authFilter(
			@NonNull final UserAuthTokenService userAuthTokenService,
			@NonNull final UserAuthTokenFilterConfiguration configuration)
	{
		final FilterRegistrationBean<UserAuthTokenFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new UserAuthTokenFilter(userAuthTokenService, configuration));
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
