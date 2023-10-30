/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Workplace_User_Assign;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WorkplaceUserAssignRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<Integer, WorkplaceIdsMap> cache = CCache.<Integer, WorkplaceIdsMap>builder()
			.tableName(I_C_Workplace_User_Assign.Table_Name)
			.build();

	@NonNull
	public Optional<WorkplaceId> getWorkplaceIdByUserId(@NonNull final UserId userId)
	{
		return getMap().getWorkplaceIdByUserId(userId);
	}

	@NonNull
	private WorkplaceIdsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private WorkplaceIdsMap retrieveMap()
	{
		final ImmutableMap<UserId, WorkplaceId> byUserId = queryBL.createQueryBuilder(I_C_Workplace_User_Assign.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap((record) -> UserId.ofRepoId(record.getAD_User_ID()),
													 (record) -> WorkplaceId.ofRepoId(record.getC_Workplace_ID())));

		return WorkplaceIdsMap.of(byUserId);
	}

	@Value(staticConstructor = "of")
	private static class WorkplaceIdsMap
	{
		@NonNull
		ImmutableMap<UserId, WorkplaceId> byUserId;

		@NonNull
		public Optional<WorkplaceId> getWorkplaceIdByUserId(@NonNull final UserId userId)
		{
			return Optional.ofNullable(byUserId.get(userId));
		}
	}
}
