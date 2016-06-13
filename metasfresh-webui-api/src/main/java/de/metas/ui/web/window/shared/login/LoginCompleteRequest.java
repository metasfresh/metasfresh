package de.metas.ui.web.window.shared.login;

import java.io.Serializable;
import java.util.Objects;

import org.compiere.util.KeyNamePair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public final class LoginCompleteRequest implements Serializable
{
	@JsonCreator
	public static final LoginCompleteRequest of(
			@JsonProperty("role") final KeyNamePair role //
			, @JsonProperty("client") final KeyNamePair client //
			, @JsonProperty("org") final KeyNamePair org //
			, @JsonProperty("warehouse") final KeyNamePair warehouse //
	)
	{
		return new LoginCompleteRequest(role, client, org, warehouse);
	}

	@JsonProperty("role")
	private final KeyNamePair role;
	@JsonProperty("client")
	private final KeyNamePair client;
	@JsonProperty("org")
	private final KeyNamePair org;
	@JsonProperty("warehouse")
	private final KeyNamePair warehouse;

	private LoginCompleteRequest(final KeyNamePair role, final KeyNamePair client, final KeyNamePair org, final KeyNamePair warehouse)
	{
		super();
		this.role = role;
		this.client = client;
		this.org = org;
		this.warehouse = warehouse;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("role", role)
				.add("client", client)
				.add("org", org)
				.add("warehouse", warehouse)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(role, client, org, warehouse);
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
		if (!(obj instanceof LoginCompleteRequest))
		{
			return false;
		}

		final LoginCompleteRequest other = (LoginCompleteRequest)obj;
		return Objects.equals(role, other.role)
				&& Objects.equals(client, other.client)
				&& Objects.equals(org, other.org)
				&& Objects.equals(warehouse, other.warehouse);
	}

	@JsonIgnore
	public KeyNamePair getRole()
	{
		return role;
	}
	
	@JsonIgnore
	public KeyNamePair getClient()
	{
		return client;
	}
	
	@JsonIgnore
	public KeyNamePair getOrg()
	{
		return org;
	}
	
	@JsonIgnore
	public KeyNamePair getWarehouse()
	{
		return warehouse;
	}
}
