/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
package de.metas.handlingunits.shipmentschedule.api.impl;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IHUShipmentScheduleDAO#retrieveMergeableListenerQtyPickedForVHU(ShipmentScheduleId, HuId)}.
 *
 * <p>Pinpoint the predicate filters that scope the merge to listener-shaped picks (no job schedule,
 * not anonymous-on-the-fly, un-shipped, active). See me03#29561.</p>
 */
class HUShipmentScheduleDAO_retrieveMergeableListenerQtyPickedForVHU_Test
{
	private static final int VHU_ID_1 = 1_111_111;
	private static final int VHU_ID_2 = 2_222_222;
	private static final int OTHER_INOUT_LINE_ID = 9_999;
	private static final int OTHER_PICKING_JOB_SCHEDULE_ID = 8_888;

	private IHUShipmentScheduleDAO dao;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		dao = Services.get(IHUShipmentScheduleDAO.class);
	}

	@Test
	void empty_when_no_rows_for_schedule_and_vhu()
	{
		final ShipmentScheduleId scheduleId = createSchedule();
		final HuId vhuId = HuId.ofRepoId(VHU_ID_1);

		assertThat(dao.retrieveMergeableListenerQtyPickedForVHU(scheduleId, vhuId)).isEmpty();
	}

	@Test
	void returns_only_active_unshipped_listener_rows_for_the_given_schedule_and_vhu()
	{
		final ShipmentScheduleId schedule = createSchedule();
		final ShipmentScheduleId otherSchedule = createSchedule();
		final HuId vhuId = HuId.ofRepoId(VHU_ID_1);
		final HuId otherVhuId = HuId.ofRepoId(VHU_ID_2);

		// Eligible listener row — should be returned.
		final I_M_ShipmentSchedule_QtyPicked eligibleRow = newRow(schedule, VHU_ID_1, b -> {});

		// Same VHU + schedule but bound to a shipment line → already shipped, not mergeable.
		newRow(schedule, VHU_ID_1, b -> b.setM_InOutLine_ID(OTHER_INOUT_LINE_ID));

		// Same VHU + schedule but inactive → not mergeable.
		newRow(schedule, VHU_ID_1, b -> b.setIsActive(false));

		// Same VHU + schedule but tied to a picking-job schedule → genuine mobile pick, not listener-shaped.
		newRow(schedule, VHU_ID_1, b -> b.setM_Picking_Job_Schedule_ID(OTHER_PICKING_JOB_SCHEDULE_ID));

		// Same VHU + schedule but flagged anonymous-on-the-fly → on-the-fly path, not listener-shaped.
		newRow(schedule, VHU_ID_1, b -> b.setIsAnonymousHuPickedOnTheFly(true));

		// Different schedule — not for our pair.
		newRow(otherSchedule, VHU_ID_1, b -> {});

		// Different VHU — not for our pair.
		newRow(schedule, VHU_ID_2, b -> {});

		final List<I_M_ShipmentSchedule_QtyPicked> result =
				dao.retrieveMergeableListenerQtyPickedForVHU(schedule, vhuId);

		assertThat(result).extracting(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID)
				.containsExactly(eligibleRow.getM_ShipmentSchedule_QtyPicked_ID());

		// Sanity-check: the second VHU truly has its own row in the DB (so the filter, not absence, excludes it).
		assertThat(dao.retrieveMergeableListenerQtyPickedForVHU(schedule, otherVhuId)).hasSize(1);
	}

	@Test
	void returns_multiple_when_multiple_listener_rows_exist_for_same_pair()
	{
		// This is the pre-existing-duplicates case: the BL method will then *not* auto-merge
		// (it logs a warning and falls through). The DAO honestly reports both.
		final ShipmentScheduleId schedule = createSchedule();
		final HuId vhuId = HuId.ofRepoId(VHU_ID_1);

		newRow(schedule, VHU_ID_1, b -> {});
		newRow(schedule, VHU_ID_1, b -> {});

		assertThat(dao.retrieveMergeableListenerQtyPickedForVHU(schedule, vhuId)).hasSize(2);
	}

	private ShipmentScheduleId createSchedule()
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		InterfaceWrapperHelper.saveRecord(sched);
		return ShipmentScheduleId.ofRepoId(sched.getM_ShipmentSchedule_ID());
	}

	@FunctionalInterface
	private interface RowCustomizer
	{
		void apply(I_M_ShipmentSchedule_QtyPicked row);
	}

	private I_M_ShipmentSchedule_QtyPicked newRow(
			final ShipmentScheduleId scheduleId,
			final int vhuId,
			final RowCustomizer customizer)
	{
		final I_M_ShipmentSchedule_QtyPicked row = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		row.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		row.setVHU_ID(vhuId);
		row.setIsActive(true);
		// All other relevant filter columns default to NULL/false in the in-memory DB,
		// which matches the "eligible listener row" baseline.
		customizer.apply(row);
		InterfaceWrapperHelper.saveRecord(row);
		return row;
	}
}
