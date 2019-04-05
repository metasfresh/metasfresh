package de.metas.security.permissions.record_access;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.security.permissions.Access;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class UserGroupRecordAccess
{
	TableRecordReference recordRef;
	Access access;

	UserId userId;
	UserGroupId userGroupId;

	@Builder(toBuilder = true)
	private UserGroupRecordAccess(
			@NonNull final TableRecordReference recordRef,
			@NonNull final Access access,
			final UserId userId,
			final UserGroupId userGroupId)
	{
		this.recordRef = recordRef;
		this.access = access;

		if (userId != null)
		{
			Check.assume(userGroupId == null, "Setting both {} and {} not allowed", userId, userGroupId);
			this.userId = userId;
			this.userGroupId = null;
		}
		else if (userGroupId != null)
		{
			Check.assume(userId == null, "Setting both {} and {} not allowed", userId, userGroupId);
			this.userId = null;
			this.userGroupId = userGroupId;
		}
		else
		{
			throw new AdempiereException("UserId or UserGroupId shall be set");
		}
	}

	public UserGroupRecordAccess withRecordRef(@NonNull final TableRecordReference recordRef)
	{
		if (this.recordRef.equals(recordRef))
		{
			return this;
		}

		return toBuilder().recordRef(recordRef).build();
	}
}
