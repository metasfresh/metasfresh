/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.user.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_User_Assigned_Role;

import javax.annotation.Nullable;

@Value
public class UserAssignedRoleId implements RepoIdAware
{
	int repoId;

	@NonNull
	UserRoleId userRoleId;

	@JsonCreator
	public static UserAssignedRoleId ofRepoId(@NonNull final UserRoleId userRoleId, final int repoId)
	{
		return new UserAssignedRoleId(userRoleId, repoId);
	}

	@Nullable
	public static UserAssignedRoleId ofRepoIdOrNull(@Nullable final UserRoleId userRoleId, final int repoId)
	{
		return userRoleId != null && repoId > 0 ? new UserAssignedRoleId(userRoleId, repoId) : null;
	}

	public static int toRepoId(@Nullable final UserAssignedRoleId repoId)
	{
		return repoId != null ? repoId.getRepoId() : -1;
	}

	private UserAssignedRoleId(@NonNull final UserRoleId UserRoleId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, (I_C_User_Assigned_Role.COLUMNNAME_C_User_Assigned_Role_ID));
		this.userRoleId = UserRoleId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
