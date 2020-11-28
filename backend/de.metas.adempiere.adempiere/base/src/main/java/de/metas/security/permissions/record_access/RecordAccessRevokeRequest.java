package de.metas.security.permissions.record_access;

import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class RecordAccessRevokeRequest
{
	TableRecordReference recordRef;
	Principal principal;
	PermissionIssuer issuer;

	boolean revokeAllPermissions;
	ImmutableSet<Access> permissions;
	final UserId requestedBy;

	@Builder
	private RecordAccessRevokeRequest(
			@NonNull final TableRecordReference recordRef,
			@NonNull Principal principal,
			@NonNull PermissionIssuer issuer,
			//
			final boolean revokeAllPermissions,
			@Singular final Set<Access> permissions,
			//
			@NonNull final UserId requestedBy)
	{
		this.recordRef = recordRef;
		this.principal = principal;
		this.issuer = issuer;

		if (revokeAllPermissions)
		{
			this.revokeAllPermissions = true;
			this.permissions = ImmutableSet.of();
		}
		else
		{
			Check.assumeNotEmpty(permissions, "permissions is not empty");
			this.revokeAllPermissions = false;
			this.permissions = ImmutableSet.copyOf(permissions);
		}

		this.requestedBy = requestedBy;
	}
}
