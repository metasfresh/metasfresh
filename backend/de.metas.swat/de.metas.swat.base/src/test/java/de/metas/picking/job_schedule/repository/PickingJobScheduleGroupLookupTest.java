package de.metas.picking.job_schedule.repository;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link PickingJobScheduleRepository#listContributorsOfGroup(ProductId, UomId, java.util.Set)}: the lookup that
 * collects every workstation assignment contributing to one picking-replenishment product group
 * {@code (product, target locator, UOM)} — the group being served by a single consolidated DD_Order.
 * <p>
 * The query is index-served by the partial index {@code m_picking_job_schedule (c_workplace_id) WHERE processed = 'N'}:
 * {@code C_Workplace_ID} is the leading (and only) indexed column and the {@code Processed = 'N'} predicate is present,
 * so the workplace set — not the open-assignment set — bounds the scan. An in-memory test cannot show a query plan;
 * the {@code EXPLAIN} evidence is produced against the target instance.
 */
class PickingJobScheduleGroupLookupTest
{
	private static final BigDecimal QTY_TO_PICK = new BigDecimal("10");

	private static final ProductId productId = ProductId.ofRepoId(1_000_001);
	private static final ProductId otherProductId = ProductId.ofRepoId(1_000_002);

	private UomId uomId;
	private UomId otherUomId;
	private WorkplaceId workplaceId;
	private WorkplaceId otherWorkplaceId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = BusinessTestHelper.createUOM("Ea");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		final I_C_UOM otherUom = BusinessTestHelper.createUOM("Kg");
		otherUomId = UomId.ofRepoId(otherUom.getC_UOM_ID());

		workplaceId = createWorkplace("Workplace-1");
		otherWorkplaceId = createWorkplace("Workplace-2");
	}

	private static WorkplaceId createWorkplace(@NonNull final String name)
	{
		final I_C_Workplace record = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		record.setName(name);
		InterfaceWrapperHelper.saveRecord(record);
		return WorkplaceId.ofRepoId(record.getC_Workplace_ID());
	}

	private static ShipmentScheduleId createShipmentSchedule(@NonNull final ProductId productId)
	{
		return createShipmentSchedule(productId, true);
	}

	private static ShipmentScheduleId createShipmentSchedule(@NonNull final ProductId productId, final boolean active)
	{
		final I_M_ShipmentSchedule record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		record.setM_Product_ID(productId.getRepoId());
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
		return ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());
	}

	private PickingJobScheduleId createAssignment(
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull final WorkplaceId workplaceId,
			final boolean processed,
			final boolean active)
	{
		return createAssignment(createShipmentSchedule(productId), uomId, workplaceId, processed, active);
	}

	private PickingJobScheduleId createAssignment(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final UomId uomId,
			@NonNull final WorkplaceId workplaceId,
			final boolean processed,
			final boolean active)
	{
		final I_M_Picking_Job_Schedule record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Schedule.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		record.setC_Workplace_ID(workplaceId.getRepoId());
		record.setC_UOM_ID(uomId.getRepoId());
		record.setQtyToPick(QTY_TO_PICK);
		record.setProcessed(processed);
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
		return PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID());
	}

	/** An open assignment of the group, on the requested workplace. */
	private PickingJobScheduleId createContributor()
	{
		return createAssignment(productId, uomId, workplaceId, false, true);
	}

	private ImmutableList<PickingJobScheduleId> listContributorIds()
	{
		return PickingJobScheduleRepository.newInstanceForUnitTesting()
				.listContributorsOfGroup(productId, uomId, ImmutableSet.of(workplaceId))
				.stream()
				.map(PickingJobSchedule::getId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Two assignments on the same workplace whose shipment schedules carry the same product, in the same UOM, are BOTH
	 * contributors of the group — that is the consolidation input. An assignment of the same group on a workplace
	 * outside the requested set belongs to another group's target locator and must not be pulled in.
	 */
	@Test
	void listContributorsOfGroup_returnsEverySameGroupAssignmentOfTheRequestedWorkplaces()
	{
		final PickingJobScheduleId contributor1 = createContributor();
		final PickingJobScheduleId contributor2 = createContributor();
		createAssignment(productId, uomId, otherWorkplaceId, false, true);

		assertThat(listContributorIds()).containsExactlyInAnyOrder(contributor1, contributor2);
	}

	/**
	 * The group key is {@code (product, target locator, UOM)}: an assignment whose shipment schedule carries a different
	 * product, and one carrying the group's product in a different UOM, are separate groups and get their own DD_Order.
	 */
	@Test
	void listContributorsOfGroup_excludesOtherGroups()
	{
		final PickingJobScheduleId contributor = createContributor();
		createAssignment(otherProductId, uomId, workplaceId, false, true);
		createAssignment(productId, otherUomId, workplaceId, false, true);

		assertThat(listContributorIds()).containsExactly(contributor);
	}

	/**
	 * A processed assignment has had its shipment closed out; it no longer demands replenishment, and it is also the
	 * assignment state the serving partial index excludes.
	 */
	@Test
	void listContributorsOfGroup_excludesProcessed()
	{
		final PickingJobScheduleId contributor = createContributor();
		createAssignment(productId, uomId, workplaceId, true, true);

		assertThat(listContributorIds()).containsExactly(contributor);
	}

	/** A deactivated assignment was un-assigned or cancelled and has left the group. */
	@Test
	void listContributorsOfGroup_excludesInactive()
	{
		final PickingJobScheduleId contributor = createContributor();
		createAssignment(productId, uomId, workplaceId, false, false);

		assertThat(listContributorIds()).containsExactly(contributor);
	}

	/**
	 * An assignment whose shipment schedule was deactivated (a user action; no production code does it) no longer
	 * carries demand, so it must not keep a share of the group's DD_Order — and the sub-query, like the shipment
	 * schedule's own repository, therefore has to filter on {@code IsActive}.
	 */
	@Test
	void listContributorsOfGroup_excludesAssignmentsOfAnInactiveShipmentSchedule()
	{
		final PickingJobScheduleId contributor = createContributor();
		createAssignment(createShipmentSchedule(productId, false), uomId, workplaceId, false, true);

		assertThat(listContributorIds()).containsExactly(contributor);
	}

	/**
	 * An empty {@code workplaceIds} set must return no contributors, even though a matching contributor exists for the
	 * requested product/UOM. This guards {@code PickingJobScheduleRepository}'s early return: an empty IN-list renders
	 * as TRUE, so without that guard this call would collapse the workplace restriction and wrongly return the
	 * contributor created below.
	 */
	@Test
	void listContributorsOfGroup_returnsEmptyForEmptyWorkplaceSet_evenWhenMatchingContributorExists()
	{
		createContributor();

		final ImmutableList<PickingJobSchedule> result = PickingJobScheduleRepository.newInstanceForUnitTesting()
				.listContributorsOfGroup(productId, uomId, ImmutableSet.of());

		assertThat(result).isEmpty();
	}
}
