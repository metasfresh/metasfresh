package de.metas.vertical.pharma.msv3.server.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@ToString(exclude = "password")
@SuppressWarnings("serial")
public class MSV3User implements UserDetails
{
	private final String username;
	private final String password;
	private final BPartnerId bpartnerId;
	private final List<GrantedAuthority> authorities;

	private static final List<GrantedAuthority> AUTHORITIES_CLIENT = ImmutableList.of(new SimpleGrantedAuthority(SecurityConfig.ROLE_CLIENT));
	private static final List<GrantedAuthority> AUTHORITIES_SERVER_ADMIN = ImmutableList.of(new SimpleGrantedAuthority(SecurityConfig.ROLE_SERVER_ADMIN));

	@Builder
	private MSV3User(
			@NonNull final String username,
			@NonNull final String password,
			final boolean serverAdmin,
			final BPartnerId bpartnerId)
	{
		this.username = username;
		this.password = password;
		this.authorities = serverAdmin ? AUTHORITIES_SERVER_ADMIN : AUTHORITIES_CLIENT;

		if (serverAdmin)
		{
			this.bpartnerId = null;
		}
		else
		{
			Preconditions.checkNotNull(bpartnerId, "bpartnerId");
			this.bpartnerId = bpartnerId;
		}
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
