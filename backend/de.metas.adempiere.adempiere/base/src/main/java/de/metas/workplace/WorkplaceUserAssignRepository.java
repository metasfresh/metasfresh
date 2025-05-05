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

import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Workplace_User_Assign;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WorkplaceUserAssignRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserId, Optional<WorkplaceId>> byUserId = CCache.<UserId, Optional<WorkplaceId>>builder()
			.tableName(I_C_Workplace_User_Assign.Table_Name)
			.build();

	@NonNull
	public Optional<WorkplaceId> getWorkplaceIdByUserId(@NonNull final UserId userId)
	{
		return byUserId.getOrLoad(userId, this::retrieveWorkplaceIdByUserId);
	}

	@NonNull
	private Optional<WorkplaceId> retrieveWorkplaceIdByUserId(@NonNull final UserId userId)
	{
		return retrieveActiveRecordByUserId(userId).map(WorkplaceUserAssignRepository::extractWorkplaceId);
	}

	private static WorkplaceId extractWorkplaceId(final I_C_Workplace_User_Assign record) {return WorkplaceId.ofRepoId(record.getC_Workplace_ID());}

	@NonNull
	private Optional<I_C_Workplace_User_Assign> retrieveActiveRecordByUserId(final @NonNull UserId userId)
	{
		return queryBL.createQueryBuilder(I_C_Workplace_User_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Workplace_User_Assign.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_C_Workplace_User_Assign.class);
	}

	public void create(@NonNull final WorkplaceAssignmentCreateRequest request)
	{
		final UserId userId = request.getUserId();
		final WorkplaceId workplaceId = request.getWorkplaceId();

		final I_C_Workplace_User_Assign record = retrieveActiveRecordByUserId(userId)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_C_Workplace_User_Assign.class));

		record.setIsActive(true);
		record.setAD_User_ID(userId.getRepoId());
		record.setC_Workplace_ID(workplaceId.getRepoId());

		InterfaceWrapperHelper.save(record);
	}
}
