package de.metas.vertical.pharma.msv3.server.security.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.security.MSV3User;
import de.metas.vertical.pharma.msv3.server.security.MSV3UserRepository;

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

@RestController
@RequestMapping(UserSyncRestEndpoint.ENDPOINT)
public class UserSyncRestEndpoint
{
	public static final String ENDPOINT = MSV3ServerConstants.BACKEND_SYNC_REST_ENDPOINT + "/users";

	@Autowired
	private MSV3UserRepository usersRepo;

	@PostMapping
	public void update(@RequestBody final JsonUsersUpdateRequest request)
	{
		request.getUsers().forEach(this::updateUser);
	}

	private void updateUser(final JsonUser jsonUser)
	{
		final String username = jsonUser.getUsername();

		MSV3User user = usersRepo.findByUsername(username);
		if (user == null)
		{
			user = new MSV3User();
			user.setUsername(username);
		}

		user.setPassword(jsonUser.getPassword());

		usersRepo.save(user);
	}
}
