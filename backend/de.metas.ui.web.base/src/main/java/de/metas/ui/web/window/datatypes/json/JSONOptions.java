package de.metas.ui.web.window.datatypes.json;

import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.Language;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.util.Services;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;

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

/**
 * JSON context: provide different options and filters to be used when the API responses are converted to/from JSON.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
@Getter
public class JSONOptions
{
	public static JSONOptionsBuilder prepareFrom(@NonNull final UserSession userSession)
	{
		final JSONDocumentPermissions documentPermissions = userSession.isLoggedIn()
				? new JSONDocumentPermissions(userSession.getUserRolePermissions())
				: null;

		return builder()
				.adLanguage(extractAdLanguageFromUserSession(userSession))
				.documentPermissions(documentPermissions)
				.zoneId(userSession.getTimeZone());
	}

	public static JSONOptions of(final UserSession userSession)
	{
		return prepareFrom(userSession).build();
	}

	static JSONOptions ofAdLanguage(final String adLanguage)
	{
		return builder().adLanguage(adLanguage).build();
	}

	public static JSONOptions newInstance()
	{
		final UserSession userSession = UserSession.getCurrentOrNull();
		return userSession != null
				? of(userSession)
				: ofAdLanguage(Language.getBaseAD_Language());
	}

	public static final String DEBUG_ATTRNAME = "json-options";

	private final String adLanguage;
	private final ZoneId zoneId;
	@Nullable private final JSONDocumentPermissions documentPermissions;

	@Builder(toBuilder = true)
	private JSONOptions(
			@NonNull final String adLanguage,
			@Nullable final ZoneId zoneId,
			@Nullable final JSONDocumentPermissions documentPermissions)
	{
		this.adLanguage = adLanguage;
		this.zoneId = zoneId != null ? zoneId : SystemTime.zoneId();
		this.documentPermissions = documentPermissions;
	}

	public JSONOptions withAdLanguage(@NonNull final String adLanguage)
	{
		if (this.adLanguage.equals(adLanguage))
		{
			return this;
		}
		else
		{
			return toBuilder().adLanguage(adLanguage).build();
		}
	}

	private static String extractAdLanguageFromUserSession(@NonNull final UserSession userSession)
	{
		final String sessionADLanguage = userSession.getAD_Language();
		if (sessionADLanguage != null)
		{
			return sessionADLanguage;
		}

		//
		// Try fetching the AD_Language from "Accept-Language" HTTP header
		if (userSession.isUseHttpAcceptLanguage())
		{
			HttpServletRequest httpServletRequest = getHttpServletRequest();
			if (httpServletRequest != null)
			{
				final String httpAcceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
				if (!Check.isEmpty(httpAcceptLanguage, true))
				{
					final String requestLanguage = Services.get(ILanguageDAO.class).retrieveAvailableLanguages()
							.getAD_LanguageFromHttpAcceptLanguage(httpAcceptLanguage, null);
					if (requestLanguage != null)
					{
						return requestLanguage;
					}
				}
			}
		}

		throw new IllegalStateException("Cannot extract the AD_Language from user session");
	}

	private static HttpServletRequest getHttpServletRequest()
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

		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}

	public DocumentFieldLogicExpressionResultRevaluator getLogicExpressionResultRevaluator()
	{
		return documentPermissions != null ? documentPermissions.getLogicExpressionResultRevaluator() : DocumentFieldLogicExpressionResultRevaluator.DEFAULT;
	}

}
