package de.metas.ui.web.login.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

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
public class JSONLoginAuthResponse implements Serializable
{
	public static final JSONLoginAuthResponse of(final Collection<JSONLoginRole> roles)
	{
		Check.assumeNotEmpty(roles, "roles is not empty");
		final boolean loginComplete = false;
		return new JSONLoginAuthResponse(roles, loginComplete);
	}

	public static final JSONLoginAuthResponse loginComplete(final JSONLoginRole role)
	{
		Check.assumeNotNull(role, "Parameter role is not null");
		final Set<JSONLoginRole> roles = ImmutableSet.of(role);
		final boolean loginComplete = true;
		return new JSONLoginAuthResponse(roles, loginComplete);
	}

	@JsonProperty("roles")
	private final Set<JSONLoginRole> roles;

	@JsonProperty("loginComplete")
	private final boolean loginComplete;

	private JSONLoginAuthResponse(final Collection<JSONLoginRole> roles, final boolean loginComplete)
	{
		super();
		this.roles = ImmutableSet.copyOf(roles);
		this.loginComplete = loginComplete;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("roles", roles)
				.toString();
	}

	public Set<JSONLoginRole> getRoles()
	{
		return roles;
	}
}
