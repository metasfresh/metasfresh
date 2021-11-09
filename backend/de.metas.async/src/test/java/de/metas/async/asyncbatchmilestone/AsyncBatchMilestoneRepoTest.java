/*
 * #%L
 * de.metas.async
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

package de.metas.async.asyncbatchmilestone;

import de.metas.async.AsyncBatchId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AsyncBatchMilestoneRepoTest
{
	private static final AsyncBatchId MOCK_ASYNC_BATCH_ID = AsyncBatchId.ofRepoId(1);
	AsyncBatchMilestoneRepo asyncBatchMilestoneRepo;

	@Before
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(AsyncBatchMilestoneRepo.class, new AsyncBatchMilestoneRepo());
		asyncBatchMilestoneRepo = SpringContextHolder.instance.getBean(AsyncBatchMilestoneRepo.class);
	}

	@Test
	public void saveAsyncBatchMilestoneTest()
	{
		final AsyncBatchMilestone milestone = getAsyncBatchMilestone()
				.milestoneName(MilestoneName.INVOICE_CREATION)
				.asyncBatchId(MOCK_ASYNC_BATCH_ID)
				.orgId(OrgId.ofRepoId(1))
				.processed(true)
				.build();

		final AsyncBatchMilestone savedMilestone = asyncBatchMilestoneRepo.save(milestone);

		assertThat(savedMilestone).isNotNull();
		assertThat(savedMilestone.getIdNotNull()).isNotNull();
		assertThat(savedMilestone.getAsyncBatchId()).isEqualTo(milestone.getAsyncBatchId());
		assertThat(savedMilestone.getOrgId()).isEqualTo(milestone.getOrgId());
		assertThat(savedMilestone.getMilestoneName()).isEqualTo(milestone.getMilestoneName());
		assertThat(savedMilestone.isProcessed()).isEqualTo(milestone.isProcessed());
	}

	@Test
	public void getByIdTest()
	{
		final AsyncBatchMilestone mockMilestoneRecord = asyncBatchMilestoneRepo.save(getAsyncBatchMilestone()
																							 .orgId(OrgId.ofRepoId(1))
																							 .asyncBatchId(MOCK_ASYNC_BATCH_ID)
																							 .milestoneName(MilestoneName.INVOICE_CREATION)
																							 .processed(true)
																							 .build());

		final AsyncBatchMilestone milestone = asyncBatchMilestoneRepo.getById(mockMilestoneRecord.getIdNotNull());

		assertThat(milestone).isNotNull();

		assertThat(milestone.getIdNotNull()).isEqualTo(mockMilestoneRecord.getIdNotNull());
		assertThat(milestone.getAsyncBatchId()).isEqualTo(mockMilestoneRecord.getAsyncBatchId());
		assertThat(milestone.getOrgId()).isEqualTo(mockMilestoneRecord.getOrgId());
		assertThat(milestone.getMilestoneName()).isEqualTo(mockMilestoneRecord.getMilestoneName());
		assertThat(milestone.isProcessed()).isEqualTo(mockMilestoneRecord.isProcessed());
	}

	@Test
	public void getByQueryTest()
	{
		final AsyncBatchMilestone invoiceMilestone = getAsyncBatchMilestone()
				.milestoneName(MilestoneName.INVOICE_CREATION)
				.asyncBatchId(MOCK_ASYNC_BATCH_ID)
				.orgId(OrgId.ofRepoId(1))
				.processed(true)
				.build();

		asyncBatchMilestoneRepo.save(invoiceMilestone);

		final AsyncBatchMilestone shipmentMilestone = getAsyncBatchMilestone()
				.milestoneName(MilestoneName.SHIPMENT_CREATION)
				.asyncBatchId(MOCK_ASYNC_BATCH_ID)
				.orgId(OrgId.ofRepoId(1))
				.processed(false)
				.build();

		final AsyncBatchMilestone savedShipmentMilestone = asyncBatchMilestoneRepo.save(shipmentMilestone);

		final AsyncBatchMilestoneQuery query = AsyncBatchMilestoneQuery.builder()
				.asyncBatchId(MOCK_ASYNC_BATCH_ID)
				.processed(false)
				.build();

		final List<AsyncBatchMilestone> milestones = asyncBatchMilestoneRepo.getByQuery(query);

		assertThat(milestones).isNotNull();
		assertThat(milestones.size()).isEqualTo(1);

		final AsyncBatchMilestone milestone = milestones.get(0);

		assertThat(milestone.getIdNotNull()).isEqualTo(savedShipmentMilestone.getIdNotNull());
		assertThat(milestone.getAsyncBatchId()).isEqualTo(savedShipmentMilestone.getAsyncBatchId());
		assertThat(milestone.getOrgId()).isEqualTo(savedShipmentMilestone.getOrgId());
		assertThat(milestone.getMilestoneName()).isEqualTo(savedShipmentMilestone.getMilestoneName());
		assertThat(milestone.isProcessed()).isEqualTo(savedShipmentMilestone.isProcessed());
	}

	@Builder(builderMethodName = "getAsyncBatchMilestone")
	public static AsyncBatchMilestone asyncBatchMilestone(
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final MilestoneName milestoneName,
			@NonNull final OrgId orgId,
			final boolean processed)
	{
		return AsyncBatchMilestone.builder()
				.asyncBatchId(asyncBatchId)
				.milestoneName(milestoneName)
				.orgId(orgId)
				.processed(processed)
				.build();
	}
}
