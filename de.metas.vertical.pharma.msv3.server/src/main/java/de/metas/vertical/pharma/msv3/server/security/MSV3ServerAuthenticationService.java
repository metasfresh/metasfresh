package de.metas.vertical.pharma.msv3.server.security;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent.ChangeType;
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
	private static final Logger logger = LoggerFactory.getLogger(MSV3ServerAuthenticationService.class);

	private final JpaUserRepository usersRepo;

	private final MSV3User adminUser;

	public MSV3ServerAuthenticationService(
			@NonNull final JpaUserRepository usersRepo,
			@Value("${msv3server.admin.username:}") final String serverAdminUsername,
			@Value("${msv3server.admin.password:}") final String serverAdminPassword)
	{
		this.usersRepo = usersRepo;

		if (serverAdminUsername != null && serverAdminPassword != null)
		{
			adminUser = MSV3User.builder()
					.username(serverAdminUsername)
					.password(serverAdminPassword)
					.serverAdmin(true)
					.build();
		}
		else
		{
			adminUser = null;
		}
		logger.info("Server admin user: {}", adminUser);
	}

	@Override
	public MSV3User loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		if (adminUser != null && Objects.equals(username, adminUser.getUsername()))
		{
			return adminUser;
		}

		final JpaUser jpaUser = usersRepo.findByUsername(username);
		if (jpaUser == null || jpaUser.isDeleted())
		{
			throw new UsernameNotFoundException("User '" + username + "' does not exist");
		}

		return toMSV3User(jpaUser);
	}

	private static MSV3User toMSV3User(final JpaUser jpaUser)
	{
		return MSV3User.builder()
				.username(jpaUser.getUsername())
				.password(jpaUser.getPassword())
				.bpartnerId(BPartnerId.of(jpaUser.getBpartnerId(), jpaUser.getBpartnerLocationId()))
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

	public List<MSV3User> getAllUsers()
	{
		return usersRepo.findAll()
				.stream()
				.map(jpaUser -> toMSV3User(jpaUser))
				.collect(ImmutableList.toImmutableList());
	}

	public void handleEvent(@NonNull final MSV3UserChangedEvent event)
	{
		if (event.getChangeType() == ChangeType.CREATED_OR_UPDATED)
		{
			final String username = event.getUsername();

			JpaUser user = usersRepo.findByUsername(username);
			if (user == null)
			{
				user = new JpaUser();
				user.setUsername(username);
			}

			user.setPassword(event.getPassword());
			user.setBpartnerId(event.getBpartnerId());
			user.setBpartnerLocationId(event.getBpartnerLocationId());

			usersRepo.save(user);
		}
		else if (event.getChangeType() == ChangeType.DELETED)
		{
			final JpaUser user = usersRepo.findByUsername(event.getUsername());
			if (user != null)
			{
				usersRepo.delete(user);
			}
		}
		else
		{
			throw new IllegalArgumentException("Unknown change type: " + event);
		}
	}
}
