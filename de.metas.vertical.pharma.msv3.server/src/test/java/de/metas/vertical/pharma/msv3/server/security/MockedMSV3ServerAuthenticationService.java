package de.metas.vertical.pharma.msv3.server.security;

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

public class MockedMSV3ServerAuthenticationService extends MSV3ServerAuthenticationService
{
	private MSV3User currentUser;

	public MockedMSV3ServerAuthenticationService(final JpaUserRepository usersRepo)
	{
		super(usersRepo, /* serverAdminUsername */null, /* serverAdminPassword */null);
	}

	@Override
	public MSV3User getCurrentUser()
	{
		if (currentUser != null)
		{
			return currentUser;
		}

		return super.getCurrentUser();
	}

	public void setCurrentUser(final MSV3User currentUser)
	{
		this.currentUser = currentUser;
	}
}
