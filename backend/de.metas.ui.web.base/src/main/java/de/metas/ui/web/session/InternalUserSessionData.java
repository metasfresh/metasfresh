package de.metas.ui.web.session;

import com.google.common.base.MoreObjects;
import de.metas.contracts.ConditionsId;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlateId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui.web.session.json.WebuiSessionId;
import de.metas.user.UserId;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Internal {@link UserSession} data.
 * <p>
 * NOTE: it's here and not inside UserSession class because it seems spring could not discover it
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
@Primary
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@lombok.Data
		/* package */ class InternalUserSessionData implements Serializable
{
	private static final long serialVersionUID = 4046535476486036184L;

	// ---------------------------------------------------------------------------------------------
	// NOTE: make sure none of those fields are "final" because this will prevent deserialization
	// ---------------------------------------------------------------------------------------------

	//
	// Actual session data
	private volatile boolean initialized = false;
	private WebuiSessionId sessionId;
	private UserPreference userPreference;
	private boolean loggedIn;
	private Locale locale = null;
	private Properties ctx;

	//
	// User info
	private String userFullname;
	private String userEmail;
	private String avatarId;
	private BoilerPlateId defaultBoilerPlateId;
	private ConditionsId defaultFlatrateConditionsId;

	//
	// Defaults
	@Value("${metasfresh.webui.debug.showColumnNamesForCaption:false}")
	private boolean defaultShowColumnNamesForCaption;
	private boolean showColumnNamesForCaption;
	//
	@Value("${metasfresh.webui.debug.allowDeprecatedRestAPI:false}")
	private boolean defaultAllowDeprecatedRestAPI;
	private boolean allowDeprecatedRestAPI;

	@Value("${metasfresh.webui.http.cache.maxAge:60}")
	private int defaultHttpCacheMaxAge;
	private int httpCacheMaxAge;

	// TODO: set default to "true" after https://github.com/metasfresh/metasfresh-webui-frontend/issues/819
	@Value("${metasfresh.webui.http.use.AcceptLanguage:false}")
	private boolean defaultUseHttpAcceptLanguage;
	private boolean useHttpAcceptLanguage;

	//
	public InternalUserSessionData()
	{
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		sessionId = WebuiSessionId.ofNullableString(requestAttributes.getSessionId());

		userPreference = new UserPreference();
		loggedIn = false;

		// Context
		ctx = new Properties();
		Env.setContext(ctx, WebRestApiContextProvider.CTXNAME_IsServerContext, false);
		Env.setContext(ctx, WebRestApiContextProvider.CTXNAME_IsWebUI, true);

		UserSession.logger.trace("User session created: {}", this);
	}

	void initializeIfNeeded()
	{
		if (!initialized)
		{
			synchronized (this)
			{
				if (!initialized)
				{
					initializeNow();
					initialized = true;
				}
			}
		}
	}

	private void initializeNow()
	{
		//
		// Set initial properties
		setShowColumnNamesForCaption(defaultShowColumnNamesForCaption);
		setAllowDeprecatedRestAPI(defaultAllowDeprecatedRestAPI);
		setHttpCacheMaxAge(defaultHttpCacheMaxAge);
		setUseHttpAcceptLanguage(defaultUseHttpAcceptLanguage);

		//
		// Set initial language
		try
		{
			final Language language = findInitialLanguage();
			verifyLanguageAndSet(language);
		}
		catch (final Throwable ex)
		{
			UserSession.logger.warn("Failed setting the language, but moving on", ex);
		}
	}

	private static Language findInitialLanguage()
	{
		final Locale locale = LocaleContextHolder.getLocale();
		if (locale != null)
		{
			final Language language = Language.findLanguageByLocale(locale);
			if (language != null)
			{
				return language;
			}
		}

		return Language.getBaseLanguage();
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
				.add("defaultUseHttpAcceptLanguage", defaultUseHttpAcceptLanguage)
				.toString();
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();

		UserSession.logger.trace("User session serialized: {}", this);
	}

	private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		UserSession.logger.trace("User session deserialized: {}", this);
	}

	Properties getCtx()
	{
		return ctx;
	}

	public ClientId getClientId()
	{
		return Env.getClientId(getCtx());
	}

	public OrgId getOrgId()
	{
		return Env.getOrgId(getCtx());
	}

	public String getOrgName()
	{
		return Env.getContext(getCtx(), Env.CTXNAME_AD_Org_Name);
	}

	public UserId getLoggedUserId()
	{
		return Env.getLoggedUserId(getCtx());
	}

	public Optional<UserId> getLoggedUserIdIfExists()
	{
		return Env.getLoggedUserIdIfExists(getCtx());
	}

	public RoleId getLoggedRoleId()
	{
		return Env.getLoggedRoleId(getCtx());
	}

	public String getUserName()
	{
		return Env.getContext(getCtx(), Env.CTXNAME_AD_User_Name);
	}

	public String getRoleName()
	{
		return Env.getContext(getCtx(), Env.CTXNAME_AD_Role_Name);
	}

	String getAdLanguage()
	{
		return Env.getContext(getCtx(), Env.CTXNAME_AD_Language);
	}

	Language getLanguage()
	{
		return Env.getLanguage(getCtx());
	}

	/**
	 * @return previous language
	 */
	String verifyLanguageAndSet(final Language lang)
	{
		final Properties ctx = getCtx();
		final String adLanguageOld = Env.getContext(ctx, Env.CTXNAME_AD_Language);

		//
		// Check the language (and update it if needed)
		final Language validLang = Env.verifyLanguageFallbackToBase(lang);

		//
		// Actual update
		final String adLanguageNew = validLang.getAD_Language();
		Env.setContext(ctx, Env.CTXNAME_AD_Language, adLanguageNew);
		this.locale = validLang.getLocale();
		UserSession.logger.debug("Changed AD_Language: {} -> {}, {}", adLanguageOld, adLanguageNew, validLang);

		return adLanguageOld;
	}
}
