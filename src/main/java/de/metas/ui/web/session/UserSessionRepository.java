package de.metas.ui.web.session;

import java.util.Objects;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import de.metas.adempiere.model.I_AD_User;
import de.metas.ui.web.session.json.JSONUserSessionChangesEvent;
import de.metas.ui.web.session.json.JSONUserSessionChangesEvent.JSONUserSessionChangesEventBuilder;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import lombok.AllArgsConstructor;

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

@Component
@DependsOn(Adempiere.BEAN_NAME) // NOTE: we need Adempiere as parameter to make sure it was initialized. Else the "addModelInterceptor" will fail.
public class UserSessionRepository
{
	@Autowired
	private WebsocketSender websocketSender;

	private UserSessionRepository()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new AD_User_UserSessionUpdater(this));
	}

	public void load(final UserSession userSession)
	{
		final I_AD_User fromUser = Services.get(IUserDAO.class).retrieveUser(userSession.getAD_User_ID());
		loadFromAD_User(userSession, fromUser);
	}

	private void loadFromAD_User(final UserSession userSession, final I_AD_User fromUser)
	{
		final JSONUserSessionChangesEventBuilder changesCollector = JSONUserSessionChangesEvent.builder();

		// Fullname
		final String userFullnameOld = userSession.setUserFullname(buildUserFullname(fromUser));
		final String userFullnameNew = userSession.getUserFullname();
		if (!Objects.equals(userFullnameNew, userFullnameOld))
		{
			changesCollector.fullname(userFullnameNew);
		}

		// EMail
		final String userEmailOld = userSession.setUserEmail(fromUser.getEMail());
		final String userEmailNew = userSession.getUserEmail();
		if (!Objects.equals(userEmailNew, userEmailOld))
		{
			changesCollector.email(userEmailNew);
		}

		// Avatar
		{
			final int avatarIdInt = fromUser.getAvatar_ID();
			final String avatarIdOld = userSession.setAvatarId(avatarIdInt > 0 ? String.valueOf(avatarIdInt) : null);
			final String avatarIdNew = userSession.getAvatarId();
			if (!Objects.equals(avatarIdNew, avatarIdOld))
			{
				// IMPORTANT: convert the null to empty string to make sure the "avatarId" is communicated to frontend.
				changesCollector.avatarId(Strings.nullToEmpty(avatarIdNew));
			}
		}

		// Language
		final String adLanguageToSet = fromUser.getAD_Language();
		if (!Check.isEmpty(adLanguageToSet))
		{
			String adLanguageOld = userSession.setAD_Language(adLanguageToSet);
			String adLanguageNew = userSession.getAD_Language();
			if (!Objects.equals(adLanguageNew, adLanguageOld))
			{
				changesCollector.language(userSession.getLanguageAsJson());
			}
		}

		//
		// Fire user session changed websocket event
		final JSONUserSessionChangesEvent changesEvent = changesCollector.build();
		if (!changesEvent.isEmpty())
		{
			final String websocketEndpoint = WebSocketConfig.buildUserSessionTopicName(userSession.getAD_User_ID());
			websocketSender.convertAndSend(websocketEndpoint, changesEvent);

		}
	}

	/**
	 * Builds user's full name.
	 *
	 * @param user
	 * @return user's full name (e.g. FirstName LastName)
	 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/468
	 */
	private final String buildUserFullname(final I_AD_User user)
	{
		final StringBuilder fullname = new StringBuilder();
		final String firstname = user.getFirstname();
		if (!Check.isEmpty(firstname, true))
		{
			fullname.append(firstname.trim());
		}

		final String lastname = user.getLastname();
		if (!Check.isEmpty(lastname, true))
		{
			if (fullname.length() > 0)
			{
				fullname.append(" ");
			}
			fullname.append(lastname.trim());
		}

		if (fullname.length() <= 0)
		{
			final String login = user.getLogin();
			if (!Check.isEmpty(login, true)) // shall not happen to be empty
			{
				fullname.append(login.trim());
			}
		}

		if (fullname.length() <= 0)
		{
			fullname.append(user.getAD_User_ID());
		}

		return fullname.toString();
	}

	public void setAD_Language(final int adUserId, final String adLanguage)
	{
		final I_AD_User user = Services.get(IUserDAO.class).retrieveUserInTrx(adUserId);
		user.setAD_Language(adLanguage);
		InterfaceWrapperHelper.save(user);
	}

	@Interceptor(I_AD_User.class)
	@AllArgsConstructor
	private static class AD_User_UserSessionUpdater
	{
		private final UserSessionRepository userSessionRepo;

		/**
		 * If system user and it's the user's of current session, then load the current session.
		 */
		@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, afterCommit = true)
		public void onUserChanged(final I_AD_User user)
		{
			if (!user.isSystemUser())
			{
				return;
			}

			final UserSession userSession = UserSession.getCurrentIfMatchingOrNull(user.getAD_User_ID());
			if (userSession == null)
			{
				return;
			}

			userSessionRepo.loadFromAD_User(userSession, user);
		}
	}

}
