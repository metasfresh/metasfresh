package de.metas.ui.web.login.json;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.Value;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONLoginAuthResponse
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
	Set<JSONLoginRole> roles;

	@JsonProperty("loginComplete")
	boolean loginComplete;

	private JSONLoginAuthResponse(final Collection<JSONLoginRole> roles, final boolean loginComplete)
	{
		this.roles = ImmutableSet.copyOf(roles);
		this.loginComplete = loginComplete;
	}
}
