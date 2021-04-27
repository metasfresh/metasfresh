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

package de.metas.util.web;

import de.metas.Profiles;
// import de.metas.util.web.audit.APIAuditFilter;
import de.metas.util.web.audit.APIAuditFilter;
import de.metas.util.web.security.UserAuthTokenCallable;
import de.metas.util.web.security.UserAuthTokenFilter;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Profile(Profiles.PROFILE_App)
@Configuration
public class FilterRegristrationConfig
{
	@Bean
	public FilterRegistrationBean<APIAuditFilter> apiAuditFilter()
	{
		final FilterRegistrationBean<APIAuditFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new APIAuditFilter());
		registrationBean.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API);
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<UserAuthTokenFilter> loggingFilter(@NonNull final UserAuthTokenService userAuthTokenService)
	{
		final FilterRegistrationBean<UserAuthTokenFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new UserAuthTokenFilter(userAuthTokenService));
		registrationBean.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API);
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
