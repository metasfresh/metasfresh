package de.metas.ui.web.session.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.adempiere.model.I_AD_User;
import de.metas.i18n.Language;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONUserSession
{
	public static final JSONUserSession of(final UserSession userSession)
	{
		return new JSONUserSession(userSession);
	}

	@JsonProperty("loggedIn")
	private final boolean loggedIn;

	/** login user */
	@JsonProperty("username")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String username;

	/** user's full name/display name */
	@JsonProperty("fullname")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String fullname;

	
	@JsonProperty("email")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String email;
	
	@JsonProperty("avatarId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String avatarId;

	@JsonProperty("rolename")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String rolename;

	@JsonProperty("language")
	private final JSONLookupValue language;

	@JsonProperty("timeZone")
	private final String timeZone;

	private JSONUserSession(final UserSession userSession)
	{
		super();

		loggedIn = userSession.isLoggedIn();
		if (loggedIn)
		{
			username = userSession.getUserName();
			rolename = userSession.getRoleName();
			
			final I_AD_User user = userSession.getAD_User();
			fullname = user.getName();
			email = user.getEMail();
			
			final int avatarIdInt = user.getAvatar_ID();
			avatarId = avatarIdInt > 0 ? String.valueOf(avatarIdInt) : null;
		}
		else
		{
			username = null;
			rolename = null;
			fullname = null;
			email = null;
			avatarId = null;
		}

		final Language language = userSession.getLanguage();
		this.language = JSONLookupValue.of(language.getAD_Language(), language.getName());

		timeZone = JSONDate.getCurrentTimeZoneAsJson();
	}
}
