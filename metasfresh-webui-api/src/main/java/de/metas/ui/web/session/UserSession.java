package de.metas.ui.web.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.session.UserPreference;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
public class UserSession implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(UserSession.class);

	// NOTE: make sure none of those fields are "final" because this will prevent deserialization
	private String sessionId = null;
	private UserPreference userPreference;
	private boolean loggedIn;
	private Locale locale;

	private final Map<String, Object> properties = new ConcurrentHashMap<>();

	public UserSession()
	{
		super();
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		sessionId = requestAttributes.getSessionId();

		userPreference = new UserPreference();
		loggedIn = false;

		// FIXME: get browser locale and only if not available get default!
		locale = Locale.getDefault();

		logger.trace("User session created: {}", this);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sessionId", sessionId)
				.add("loggedIn", loggedIn)
				.add("locale", locale)
				.add("userPreferences", userPreference)
				.toString();
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();

		logger.trace("User session serialized: {}", this);
	}

	private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		logger.trace("User session deserialized: {}", this);
	}

	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * @return session's context
	 * @see de.metas.ui.web.session.WebRestApiContextProvider
	 */
	public Properties getCtx()
	{
		return Env.getCtx();
	}

	public UserPreference getUserPreference()
	{
		return userPreference;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public void setLoggedIn(final boolean loggedIn)
	{
		if (loggedIn)
		{
			this.loggedIn = true;
			userPreference.loadPreference(getCtx());
			logger.trace("User session logged in: {}", this);
		}
		else
		{
			this.loggedIn = false;
			userPreference = new UserPreference();
			logger.trace("User session logged out: {}", this);
		}
	}

	/**
	 * @param adLanguage
	 * @return old AD_Language
	 */
	public String setAD_Language(final String adLanguage)
	{
		final Properties ctx = getCtx();
		final String adLanguageOld = Env.getContext(ctx, Env.CTXNAME_AD_Language);

		Check.assumeNotNull(ctx, "Parameter ctx is not null");
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		final Language lang = Language.getLanguage(adLanguage);
		Env.verifyLanguage(lang);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, lang.getAD_Language());

		locale = lang.getLocale();

		return adLanguageOld;
	}

	public String getAD_Language()
	{
		return Env.getAD_Language(getCtx());
	}

	public Locale getLocale()
	{
		return locale;
	}

	public UserRolePermissionsKey getUserRolePermissionsKey()
	{
		// TODO: cache the permissions key
		return UserRolePermissionsKey.of(getCtx());
	}
}
