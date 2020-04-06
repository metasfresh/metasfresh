package de.metas.security.permissions.record_access;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.handlers.RecordAccessChangeEvent;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class RecordAccessCopyCommand
{
	private final RecordAccessService service;

	private final TableRecordReference target;
	private final TableRecordReference grantFrom;
	private final TableRecordReference revokeFrom;
	private @NonNull PermissionIssuer issuer;
	private @NonNull UserId requestedBy;

	public RecordAccessCopyCommand(
			@NonNull final RecordAccessService service,
			@NonNull final RecordAccessCopyRequest request)
	{
		this.service = service;

		target = request.getTarget();
		grantFrom = request.getGrantFrom();
		revokeFrom = request.getRevokeFrom();
		issuer = request.getIssuer();
		requestedBy = request.getRequestedBy();
	}

	public void run()
	{
		if (grantFrom == null && revokeFrom == null)
		{
			return;
		}
		if (Objects.equals(grantFrom, revokeFrom))
		{
			return;
		}

		//
		final HashMap<RecordAccessKey, RecordAccess> accessGrants = new HashMap<>();
		final HashMap<RecordAccessKey, RecordAccess> accessRevokes = new HashMap<>();

		//
		// Fetch existing accesses of our target record
		final ImmutableMap<RecordAccessKey, RecordAccess> existingTargetAccessRecords = Maps.uniqueIndex(
				getAccessRecords(target),
				RecordAccessKey::of);

		//
		// Revoke accesses
		if (revokeFrom != null)
		{
			final ImmutableList<RecordAccess> fromAccesses = getAccessRecords(revokeFrom);
			for (final RecordAccess fromAccess : fromAccesses)
			{
				final RecordAccessKey key = RecordAccessKey.of(fromAccess);
				final RecordAccess targetAccess = existingTargetAccessRecords.get(key);
				if (targetAccess != null)
				{
					accessRevokes.put(key, targetAccess);
				}
			}
		}

		//
		// Grant accesses
		if (grantFrom != null)
		{
			final List<RecordAccess> fromAccesses = getAccessRecords(grantFrom);
			for (final RecordAccess fromAccess : fromAccesses)
			{
				final RecordAccess targetAccess = RecordAccess.builder()
						.recordRef(target)
						.principal(fromAccess.getPrincipal())
						.permission(fromAccess.getPermission())
						.issuer(issuer)
						.createdBy(requestedBy)
						//
						.parentId(fromAccess.getId())
						.rootId(fromAccess.getRootId())
						//
						.build();

				final RecordAccessKey key = RecordAccessKey.of(targetAccess);
				final boolean wasJustRevoked = accessRevokes.remove(key) != null;

				if (!existingTargetAccessRecords.containsKey(key)
						&& !wasJustRevoked)
				{
					accessGrants.put(key, targetAccess);
				}
			}
		}

		//
		// Stop here if nothing changed
		if (accessGrants.isEmpty() && accessRevokes.isEmpty())
		{
			return;
		}

		//
		// Persist to database
		{
			service.saveNew(accessGrants.values());

			final ImmutableSet<RecordAccessId> accessIdsToRevoke = extractIds(accessRevokes.values());
			service.deleteByIds(accessIdsToRevoke, requestedBy);
		}

		//
		// Fire event
		service.fireEvent(RecordAccessChangeEvent.builder()
				.accessGrants(accessGrants.values())
				.accessRevokes(accessRevokes.values())
				.build());
	}

	private static ImmutableSet<RecordAccessId> extractIds(final Collection<RecordAccess> accesses)
	{
		return accesses.stream().map(RecordAccess::getId).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableList<RecordAccess> getAccessRecords(@NonNull final TableRecordReference recordRef)
	{
		return service.getAccessesByRecordAndIssuer(recordRef, issuer);
	}

	@Value
	@Builder
	private static class RecordAccessKey
	{
		@NonNull
		Access permission;

		@NonNull
		Principal principal;

		public static RecordAccessKey of(final RecordAccess access)
		{
			return builder()
					.permission(access.getPermission())
					.principal(access.getPrincipal())
					.build();
		}
	}

}
