/*
 * #%L
 * procurement-webui-backend
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

package de.metas.procurement.webui.service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import de.metas.procurement.webui.util.LanguageKey;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class I18N
{
	private static final String HTTP_SESSION_language = "language";

	private final ConcurrentHashMap<LanguageKey, LanguageData> cache = new ConcurrentHashMap<>();

	public String get(
			@NonNull final String messageKey,
			@Nullable final Object... args)
	{
		final LanguageKey language = getCurrentLang();
		return get(language, messageKey, args);
	}

	public String get(
			@Nullable final LanguageKey language,
			@NonNull final String messageKey,
			@Nullable final Object... args)
	{
		final ResourceBundle bundle = getLanguageData(language).getResourceBundle();
		final String message = bundle.containsKey(messageKey)
				? bundle.getString(messageKey)
				: messageKey;

		return MessageFormat.format(message, args);
	}

	public void setSessionLanguage(@NonNull final LanguageKey language)
	{
		final HttpSession httpSession = getCurrentHttpSessionOrNull();
		if (httpSession == null)
		{
			throw new IllegalStateException("No session");
		}
		httpSession.setAttribute(I18N.HTTP_SESSION_language, language);
	}

	public LanguageKey getCurrentLang()
	{
		final HttpSession httpSession = getCurrentHttpSessionOrNull();
		if (httpSession == null)
		{
			return LanguageKey.ofLocale(Locale.getDefault());
		}

		final LanguageKey languageKey = (LanguageKey)httpSession.getAttribute(HTTP_SESSION_language);

		return languageKey != null
				? languageKey
				: LanguageKey.ofLocale(Locale.getDefault());
	}

	public Locale getCurrentLocale()
	{
		final HttpSession httpSession = getCurrentHttpSessionOrNull();
		if (httpSession == null)
		{
			return Locale.getDefault();
		}

		final LanguageKey languageKey = (LanguageKey)httpSession.getAttribute(HTTP_SESSION_language);

		return languageKey != null
				? languageKey.toLocale()
				: Locale.getDefault();
	}

	@Nullable
	private HttpSession getCurrentHttpSessionOrNull()
	{
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (servletRequestAttributes == null)
		{
			return null;
		}

		return servletRequestAttributes.getRequest().getSession();
	}

	private LanguageData getLanguageData(@Nullable final LanguageKey language)
	{
		final LanguageKey languageEffective = language != null ? language : LanguageKey.getDefault();
		return cache.computeIfAbsent(languageEffective, LanguageData::new);
	}

	public ImmutableMap<String, String> getMessagesMap(@NonNull final LanguageKey language)
	{
		return getLanguageData(language).getFrontendMessagesMap();
	}

	private static class LanguageData
	{
		@Getter
		private final ResourceBundle resourceBundle;
		@Getter
		private final ImmutableMap<String, String> frontendMessagesMap;

		private LanguageData(final @NonNull LanguageKey language)
		{
			resourceBundle = ResourceBundle.getBundle("messages", language.toLocale());
			frontendMessagesMap = computeMessagesMap(resourceBundle);
		}

		private static ImmutableMap<String, String> computeMessagesMap(@NonNull final ResourceBundle bundle)
		{
			final Set<String> keys = bundle.keySet();

			final HashMap<String, String> map = new HashMap<>(keys.size());
			for (final String key : keys)
			{
				// shall not happen
				if (key == null || key.isBlank())
				{
					continue;
				}

				final String value = Strings.nullToEmpty(bundle.getString(key));
				map.put(key, value);
			}

			return ImmutableMap.copyOf(map);
		}

	}
}
