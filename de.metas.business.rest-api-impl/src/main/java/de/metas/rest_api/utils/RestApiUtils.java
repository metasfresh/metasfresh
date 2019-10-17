package de.metas.rest_api.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.metas.i18n.ILanguageDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public class RestApiUtils
{
	public static String getAdLanguage()
	{
		final ILanguageDAO languagesRepo = Services.get(ILanguageDAO.class);

		final HttpServletRequest httpServletRequest = getHttpServletRequest();
		if (httpServletRequest != null)
		{
			final String httpAcceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
			if (!Check.isEmpty(httpAcceptLanguage, true))
			{
				final String requestLanguage = languagesRepo.retrieveAvailableLanguages()
						.getAD_LanguageFromHttpAcceptLanguage(httpAcceptLanguage, null);
				if (requestLanguage != null)
				{
					return requestLanguage;
				}
			}
		}

		// Fallback to base language
		return languagesRepo.retrieveBaseLanguage();
	}

	public static final HttpServletRequest getHttpServletRequest()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null)
		{
			return null;
		}
		if (!(requestAttributes instanceof ServletRequestAttributes))
		{
			return null;
		}

		final HttpServletRequest servletRequest = ((ServletRequestAttributes)requestAttributes).getRequest();
		return servletRequest;
	}
}
