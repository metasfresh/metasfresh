package de.metas.ui.web.window_old.shared.login;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.compiere.util.KeyNamePair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
public final class LoginAuthResponse implements Serializable
{
	@JsonCreator
	public static final LoginAuthResponse of(@JsonProperty("roles") final List<KeyNamePair> roles)
	{
		if (roles == null || roles.isEmpty())
		{
			return NULL;
		}
		return new LoginAuthResponse(roles);
	}

	public static final LoginAuthResponse NULL = new LoginAuthResponse(ImmutableList.of());

	@JsonProperty("roles")
	private final List<KeyNamePair> roles;

	private LoginAuthResponse(final List<KeyNamePair> roles)
	{
		super();
		this.roles = ImmutableList.copyOf(roles);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("roles", roles)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(roles);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof LoginAuthResponse))
		{
			return false;
		}

		final LoginAuthResponse other = (LoginAuthResponse)obj;
		return Objects.equals(roles, other.roles);
	}

	public List<KeyNamePair> getRoles()
	{
		return roles;
	}

}
