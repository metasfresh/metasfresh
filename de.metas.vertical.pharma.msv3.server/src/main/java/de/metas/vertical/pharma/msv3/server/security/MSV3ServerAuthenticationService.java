package de.metas.vertical.pharma.msv3.server.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
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

@Component
public class MSV3ServerAuthenticationService implements UserDetailsService
{
	private final JpaUserRepository usersRepo;

	public MSV3ServerAuthenticationService(@NonNull final JpaUserRepository usersRepo)
	{
		this.usersRepo = usersRepo;
	}

	@Override
	public MSV3User loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		final JpaUser jpaUser = usersRepo.findByUsername(username);
		if (jpaUser == null || jpaUser.isDeleted())
		{
			throw new UsernameNotFoundException("User '" + username + "' does not exist");
		}

		return MSV3User.builder()
				.username(jpaUser.getUsername())
				.password(jpaUser.getPassword())
				.bpartnerId(BPartnerId.of(jpaUser.getBpartnerId()))
				.build();
	}

	public void assertValidClientSoftwareId(final String clientSoftwareId)
	{
		// TODO implement
	}

	public MSV3User getCurrentUser()
	{
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
		{
			throw new IllegalStateException("No authentication found");
		}

		return loadUserByUsername(authentication.getName());
	}
}
