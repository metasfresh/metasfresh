package de.metas.vertical.pharma.msv3.server.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.NonNull;

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

class MSV3UserDetailsService implements UserDetailsService
{
	private final MSV3UserRepository usersRepo;

	public MSV3UserDetailsService(@NonNull final MSV3UserRepository usersRepo)
	{
		this.usersRepo = usersRepo;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		final MSV3User msv3User = usersRepo.findByUsername(username);
		if (msv3User == null || msv3User.isDeleted())
		{
			throw new UsernameNotFoundException("No user");
		}

		return toUserDetails(msv3User);
	}

	private UserDetails toUserDetails(final MSV3User msv3User)
	{
		return User.withUsername(msv3User.getUsername())
				.password(msv3User.getPassword())
				.roles(SecurityConfiguration.ROLE)
				.build();
	}
}
