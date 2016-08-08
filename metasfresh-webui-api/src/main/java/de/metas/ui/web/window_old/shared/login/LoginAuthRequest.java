package de.metas.ui.web.window_old.shared.login;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

@SuppressWarnings("serial")
public final class LoginAuthRequest implements Serializable
{
	@JsonCreator
	public static final LoginAuthRequest of(@JsonProperty("username") final String username, @JsonProperty("password") final String password)
	{
		return new LoginAuthRequest(username, password);
	}

	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private final String password;

	private LoginAuthRequest(final String username, final String password)
	{
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("username", username)
				.add("password", "********")
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(username, password);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof LoginAuthRequest))
		{
			return false;
		}

		final LoginAuthRequest other = (LoginAuthRequest)obj;
		return Objects.equals(username, other.username)
				&& Objects.equals(password, other.password);
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
