package de.metas.ui.web.session.json;

import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.session.UserSession;

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

	@JsonProperty("username")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String username;

	@JsonProperty("rolename")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String rolename;

	@JsonProperty("language")
	private final String language;

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
		}
		else
		{
			username = null;
			rolename = null;
		}

		language = userSession.getAD_Language();

		final TimeZone timeZone = TimeZone.getDefault();
		this.timeZone = timeZone.getID();
	}
}
