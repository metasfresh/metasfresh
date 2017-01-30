package de.metas.ui.web.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Language;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.exceptions.DeprecatedRestAPINotAllowedException;
import de.metas.ui.web.login.exceptions.AlreadyLoggedInException;
import de.metas.ui.web.login.exceptions.NotLoggedInException;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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
public class UserSession implements InitializingBean, Serializable
{
	private static final transient Logger logger = LogManager.getLogger(UserSession.class);

	// NOTE: make sure none of those fields are "final" because this will prevent deserialization
	private String sessionId = null;
	private UserPreference userPreference;
	private boolean loggedIn;
	private Locale locale;

	private final Map<String, Object> properties = new ConcurrentHashMap<>();

	@Value("${metasfresh.webui.debug.showColumnNamesForCaption:false}")
	private boolean default_showColumnNamesForCaption;

	@Value("${metasfresh.webui.userSession.dashboardUrl:}")
	private String dashboardUrl;

	public static final String PARAM_DisableDeprecatedRestAPI = "metasfresh.webui.debug.DisableDeprecatedRestAPI";
	@Value("${" + PARAM_DisableDeprecatedRestAPI + ":false}")
	private boolean default_disableDeprecatedRestAPI;

	public UserSession()
	{
		super();
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		sessionId = requestAttributes.getSessionId();

		userPreference = new UserPreference();
		loggedIn = false;

		//
		// Set initial language
		try
		{
			final Locale locale = LocaleContextHolder.getLocale();
			final Language language = Language.getLanguage(locale);
			setLanguage(language);
		}
		catch (final Exception e)
		{
			logger.warn("Failed setting the language, but moving on", e);
		}

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

	@Override
	public void afterPropertiesSet() throws Exception
	{
		//
		// Set initial properties
		properties.put(JSONOptions.SESSION_ATTR_ShowColumnNamesForCaption, default_showColumnNamesForCaption);
		properties.put(PARAM_DisableDeprecatedRestAPI, default_disableDeprecatedRestAPI);
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
	
	public Evaluatee toEvaluatee()
	{
		return Evaluatees.ofCtx(getCtx());
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
		if (this.loggedIn == loggedIn)
		{
			return;
		}

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

	public void assertLoggedIn()
	{
		if (!isLoggedIn())
		{
			throw new NotLoggedInException();
		}
	}

	public void assertNotLoggedIn()
	{
		if (isLoggedIn())
		{
			throw new AlreadyLoggedInException();
		}
	}

	/**
	 * @param adLanguage
	 * @return old AD_Language
	 */
	public String setAD_Language(final String adLanguage)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		final Language lang = Language.getLanguage(adLanguage);
		return setLanguage(lang);
	}

	private String setLanguage(final Language lang)
	{
		final Properties ctx = getCtx();
		Check.assumeNotNull(ctx, "Parameter ctx is not null");

		final String adLanguageOld = Env.getContext(ctx, Env.CTXNAME_AD_Language);

		Env.verifyLanguage(lang);
		final String adLanguageNew = lang.getAD_Language();

		Env.setContext(ctx, Env.CTXNAME_AD_Language, adLanguageNew);
		locale = lang.getLocale();
		logger.info("Changed AD_Language: {} -> {}, {}", adLanguageOld, adLanguageNew, lang);

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

	public int getAD_User_ID()
	{
		return Env.getAD_User_ID(getCtx());
	}

	public UserRolePermissionsKey getUserRolePermissionsKey()
	{
		// TODO: cache the permissions key
		return UserRolePermissionsKey.of(getCtx());
	}

	public IUserRolePermissions getUserRolePermissions()
	{
		return Env.getUserRolePermissions(getCtx());
	}

	@Deprecated
	public String getDashboardUrl()
	{
		return dashboardUrl;
	}

	public <T extends Serializable> Object setProperty(final String name, final T value)
	{
		return properties.put(name, value);
	}

	public <T> T getProperty(final String name)
	{
		final Object valueObj = properties.get(name);

		@SuppressWarnings("unchecked")
		final T valueConv = (T)valueObj;

		return valueConv;
	}

	public boolean getPropertyAsBoolean(final String name, final boolean defaultValue)
	{
		final Boolean value = getProperty(name);
		return value != null ? value : defaultValue;
	}
	
	public void assertDeprecatedRestAPIAllowed()
	{
		final boolean disableDeprecatedRestAI = getPropertyAsBoolean(PARAM_DisableDeprecatedRestAPI, false);
		if(disableDeprecatedRestAI)
		{
			throw new DeprecatedRestAPINotAllowedException();
		}
	}
}
