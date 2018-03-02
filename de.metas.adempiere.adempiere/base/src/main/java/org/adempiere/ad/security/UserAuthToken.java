package org.adempiere.ad.security;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
@ToString(exclude = "authToken")
public class UserAuthToken
{
	int userId;
	String authToken;
	String description;

	int clientId;
	int orgId;
	int roleId;

	@Builder
	private UserAuthToken(
			final int userId,
			final String authToken,
			final String description,
			final int clientId,
			final int orgId,
			final int roleId)
	{
		Check.assume(userId > 0, "userId > 0");
		Check.assumeNotEmpty(authToken, "authToken is not empty");

		Check.assume(clientId > 0, "clientId > 0");
		Check.assume(orgId > 0, "orgId > 0");
		Check.assume(roleId > 0, "roleId > 0");

		this.userId = userId;
		this.authToken = authToken;
		this.description = description;

		this.clientId = clientId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
}
