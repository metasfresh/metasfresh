package de.metas.ui.web.session;

import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.exceptions.DeprecatedRestAPINotAllowedException;
import de.metas.ui.web.login.exceptions.AlreadyLoggedInException;
import de.metas.ui.web.login.exceptions.NotLoggedInException;
import lombok.NonNull;

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
 * User Session service
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class UserSession
{
	/**
	 * Gets current {@link UserSession} if any
	 * 
	 * NOTE: please use this method only if there is no other way to get the {@link UserSession}
	 *
	 * @return {@link UserSession} or null
	 */
	public static UserSession getCurrentOrNull()
	{
		//
		// Quickly check if the session scoped UserSession bean will be really available
		// NOTE: it's not about that the object will be null but if it's method calls will be really working
		if (RequestContextHolder.getRequestAttributes() == null)
		{
			return null;
		}

		//
		UserSession userSession = _staticUserSession;
		if (userSession == null)
		{
			synchronized (UserSession.class)
			{
				if (_staticUserSession == null)
				{
					userSession = _staticUserSession = Adempiere.getSpringApplicationContext().getBean(UserSession.class);
				}
			}
		}
		return userSession;
	}

	/**
	 * Gets current {@link UserSession}.
	 * 
	 * NOTE: please use this method only if there is no other way to get the {@link UserSession}
	 * 
	 * @return user session; never returns null
	 * @throws NotLoggedInException
	 */
	public static UserSession getCurrent() throws NotLoggedInException
	{
		final UserSession userSession = getCurrentOrNull();
		if (userSession == null)
		{
			throw new NotLoggedInException("no session found");
		}
		return userSession;
	}

	/**
	 * Gets current permissions.
	 * 
	 * @return permissions; never returns null
	 * @throws NotLoggedInException
	 */
	public static IUserRolePermissions getCurrentPermissions()
	{
		return getCurrent().getUserRolePermissions();
	}

	// services
	static final transient Logger logger = LogManager.getLogger(UserSession.class);
	private final transient ApplicationEventPublisher eventPublisher;

	private static UserSession _staticUserSession = null;

	@Autowired
	private InternalUserSessionData data; // session scoped

	@Autowired
	public UserSession(final ApplicationEventPublisher eventPublisher)
	{
		this.eventPublisher = eventPublisher;
	}

	public String getSessionId()
	{
		return data.getSessionId();
	}

	/**
	 * @return session's context
	 * @see de.metas.ui.web.session.WebRestApiContextProvider
	 */
	public Properties getCtx()
	{
		return data.getCtx();
	}

	public Evaluatee toEvaluatee()
	{
		return Evaluatees.ofCtx(data.getCtx());
	}

	public UserPreference getUserPreference()
	{
		return data.getUserPreference();
	}

	public boolean isLoggedIn()
	{
		return data.isLoggedIn();
	}

	public void setLoggedIn(final boolean loggedIn)
	{
		final boolean currentlyLoggedIn = data.isLoggedIn();
		if (currentlyLoggedIn == loggedIn)
		{
			return;
		}

		if (loggedIn)
		{
			data.setLoggedIn(true);
			data.getUserPreference().loadPreference(data.getCtx());
			logger.trace("User session logged in: {}", data);
		}
		else
		{
			data.setLoggedIn(false);
			data.setUserPreference(new UserPreference());
			logger.trace("User session logged out: {}", data);
		}
	}

	public void assertLoggedIn()
	{
		if (!data.isLoggedIn())
		{
			throw new NotLoggedInException();
		}
	}

	public void assertNotLoggedIn()
	{
		if (data.isLoggedIn())
		{
			throw new AlreadyLoggedInException();
		}
	}

	/**
	 * Sets user preferred language.
	 * 
	 * Fires {@link LanguagedChangedEvent}.
	 * 
	 * @param adLanguage
	 * @return old AD_Language
	 */
	public String setAD_Language(final String adLanguage)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		final Language lang = Language.getLanguage(adLanguage);
		final String adLanguageOld = data.verifyLanguageAndSet(lang);
		final String adLanguageNew = data.getAdLanguage();
		logger.info("Changed AD_Language: {} -> {}, {}", adLanguageOld, adLanguageNew, lang);

		// Fire event
		if (!Objects.equals(adLanguageOld, adLanguageNew))
		{
			eventPublisher.publishEvent(new LanguagedChangedEvent(adLanguageNew, getAD_User_ID()));
		}

		return adLanguageOld;
	}

	public int getAD_Client_ID()
	{
		return data.getAD_Client_ID();
	}

	public String getAD_Language()
	{
		return data.getAdLanguage();
	}

	public Language getLanguage()
	{
		return data.getLanguage();
	}

	public Locale getLocale()
	{
		return data.getLocale();
	}

	public int getAD_User_ID()
	{
		return data.getAD_User_ID();
	}

	public String getUserName()
	{
		return data.getUserName();
	}

	public String getRoleName()
	{
		return data.getRoleName();
	}

	public UserRolePermissionsKey getUserRolePermissionsKey()
	{
		// TODO: cache the permissions key
		return UserRolePermissionsKey.of(data.getCtx());
	}

	public IUserRolePermissions getUserRolePermissions()
	{
		return Env.getUserRolePermissions(data.getCtx());
	}

	public void assertDeprecatedRestAPIAllowed()
	{
		if (!data.isAllowDeprecatedRestAPI())
		{
			throw new DeprecatedRestAPINotAllowedException();
		}
	}

	public boolean isAllowDeprecatedRestAPI()
	{
		return data.isAllowDeprecatedRestAPI();
	}

	public void setAllowDeprecatedRestAPI(final boolean allowDeprecatedRestAPI)
	{
		data.setAllowDeprecatedRestAPI(allowDeprecatedRestAPI);
	}

	public boolean isShowColumnNamesForCaption()
	{
		return data.isShowColumnNamesForCaption();
	}

	public void setShowColumnNamesForCaption(final boolean showColumnNamesForCaption)
	{
		data.setShowColumnNamesForCaption(showColumnNamesForCaption);
	}

	/**
	 * Event fired when the user language was changed.
	 * Usually it is user triggered.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@lombok.Value
	public static class LanguagedChangedEvent
	{
		@NonNull
		private final String adLanguage;
		private final int adUserId;
	}
}
