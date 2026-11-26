/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Workplace_User_Assign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkplaceUserAssignRepositoryTest
{
	private static final UserId userId = UserId.ofRepoId(1234567);
	private static final WorkplaceId workplaceA = WorkplaceId.ofRepoId(100);
	private static final WorkplaceId workplaceB = WorkplaceId.ofRepoId(200);

	private WorkplaceUserAssignRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new WorkplaceUserAssignRepository();
	}

	private List<I_C_Workplace_User_Assign> retrieveAllForUser()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Workplace_User_Assign.class)
				.addEqualsFilter(I_C_Workplace_User_Assign.COLUMNNAME_AD_User_ID, userId)
				.create()
				.list(I_C_Workplace_User_Assign.class);
	}

	private void createRow(final WorkplaceId workplaceId, final boolean active)
	{
		final I_C_Workplace_User_Assign record = InterfaceWrapperHelper.newInstance(I_C_Workplace_User_Assign.class);
		record.setAD_User_ID(userId.getRepoId());
		record.setC_Workplace_ID(workplaceId.getRepoId());
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
	}

	@Test
	void reassign_reuses_existing_inactive_row_instead_of_inserting_a_duplicate()
	{
		// the user has a leftover INACTIVE assignment on another workplace (same user+org)
		createRow(workplaceA, false);

		// scanning the workstation re-asserts the workplace -> now workplaceB
		repository.create(WorkplaceAssignmentCreateRequest.builder()
				.userId(userId)
				.workplaceId(workplaceB)
				.build());

		// the unique index One_User_Per_Org (AD_User_ID, AD_Org_ID) spans inactive rows,
		// so the row must be reused, not re-inserted
		final List<I_C_Workplace_User_Assign> rows = retrieveAllForUser();
		assertThat(rows).hasSize(1);
		assertThat(rows.get(0).isActive()).isTrue();
		assertThat(rows.get(0).getC_Workplace_ID()).isEqualTo(workplaceB.getRepoId());

		assertThat(repository.getWorkplaceIdByUserId(userId)).contains(workplaceB);
	}

	@Test
	void scanning_a_different_workplace_repoints_the_single_active_row()
	{
		// the user is actively assigned to workplaceA (e.g. from the workstation)
		createRow(workplaceA, true);

		// the user deliberately scans a different workplace -> must switch to workplaceB
		repository.create(WorkplaceAssignmentCreateRequest.builder()
				.userId(userId)
				.workplaceId(workplaceB)
				.build());

		final List<I_C_Workplace_User_Assign> rows = retrieveAllForUser();
		assertThat(rows).hasSize(1);
		assertThat(rows.get(0).getC_Workplace_ID()).isEqualTo(workplaceB.getRepoId());
		assertThat(repository.getWorkplaceIdByUserId(userId)).contains(workplaceB);
	}

	@Test
	void reassign_invalidates_the_cached_workplace_lookup()
	{
		createRow(workplaceA, true);
		// warm the byUserId CCache with the old value
		assertThat(repository.getWorkplaceIdByUserId(userId)).contains(workplaceA);

		repository.create(WorkplaceAssignmentCreateRequest.builder()
				.userId(userId)
				.workplaceId(workplaceB)
				.build());

		// the save must invalidate the cache so the next read returns the new workplace
		assertThat(repository.getWorkplaceIdByUserId(userId)).contains(workplaceB);
	}
}
