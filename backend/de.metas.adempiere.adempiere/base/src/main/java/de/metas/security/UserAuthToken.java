package de.metas.security;

import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

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
	@NonNull UserId userId;
	@NonNull String authToken;
	@Nullable String description;

	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull RoleId roleId;

	@Builder
	private UserAuthToken(
			@NonNull final UserId userId,
			@NonNull final String authToken,
			@Nullable final String description,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final RoleId roleId)
	{
		Check.assume(userId.isRegularUser(), "userId shall be regular user: {}", userId);
		Check.assumeNotEmpty(authToken, "authToken is not empty");
		// Check.assume(clientId.isRegular(), "clientId shall be regular"); allow SYSTEM client, just as we allow SYSTEM users to log into WEBUI
		// Check.assume(orgId.isRegular(), "orgId shall be regular"); allow Org=* as well, just as we allow users to log into the UI with Org=*
		Check.assume(roleId.isRegular(), "roleId shall be regular");

		this.userId = userId;
		this.authToken = authToken;
		this.description = description;

		this.clientId = clientId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
}
