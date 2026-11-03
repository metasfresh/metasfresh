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

package de.metas.inoutcandidate.modelvalidator;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.PriorityRule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Verifies that {@link M_Shipper_ShipmentSchedule} flags a shipper's unprocessed {@link I_M_ShipmentSchedule}s
 * for recompute when the shipper's {@code PriorityRule} changes -- and only then: a processed schedule on the
 * same shipper, and a change to an unrelated shipper column, must both leave {@link IShipmentScheduleInvalidateBL}
 * untouched. {@code IShipmentScheduleInvalidateBL} is mocked because its real implementation flags via raw SQL
 * against a live DB, which this plain unit test does not have.
 */
class M_Shipper_ShipmentScheduleTest
{
	private IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		shipmentScheduleInvalidateBL = mock(IShipmentScheduleInvalidateBL.class);
	}

	@Test
	void changingShipperPriority_flagsUnprocessedSchedule()
	{
		// given: a shipper with an unprocessed schedule, the interceptor registered only AFTER setup so
		// the priority change below is the only thing that fires it
		final I_M_Shipper shipper = createShipper();
		final ShipmentScheduleId scheduleId = createShipmentSchedule(shipper, false);

		registerInterceptorUnderTest(shipmentScheduleInvalidateBL);

		// when: the shipper's PriorityRule changes
		shipper.setPriorityRule(PriorityRule.High.getCode());
		saveRecord(shipper);

		// then: the unprocessed schedule is flagged for recompute
		verify(shipmentScheduleInvalidateBL).flagForRecompute(eq(ImmutableSet.of(scheduleId)));
	}

	@Test
	void changingShipperPriority_doesNotFlagProcessedSchedule()
	{
		// given: a shipper with a processed schedule
		final I_M_Shipper shipper = createShipper();
		createShipmentSchedule(shipper, true);

		registerInterceptorUnderTest(shipmentScheduleInvalidateBL);

		// when: the shipper's PriorityRule changes
		shipper.setPriorityRule(PriorityRule.High.getCode());
		saveRecord(shipper);

		// then: nothing is flagged -- the processed schedule was never a candidate
		verify(shipmentScheduleInvalidateBL, never()).flagForRecompute(org.mockito.ArgumentMatchers.<Set<ShipmentScheduleId>>any());
	}

	@Test
	void changingUnrelatedShipperColumn_flagsNothing()
	{
		// given: a shipper with an unprocessed schedule
		final I_M_Shipper shipper = createShipper();
		createShipmentSchedule(shipper, false);

		registerInterceptorUnderTest(shipmentScheduleInvalidateBL);

		// when: an unrelated column (Name) changes -- PriorityRule stays untouched
		shipper.setName("Some other name");
		saveRecord(shipper);

		// then: the interceptor's @ModelChange never fires, so nothing is flagged
		verify(shipmentScheduleInvalidateBL, never()).flagForRecompute(org.mockito.ArgumentMatchers.<Set<ShipmentScheduleId>>any());
	}

	private I_M_Shipper createShipper()
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		saveRecord(shipper);
		return shipper;
	}

	private ShipmentScheduleId createShipmentSchedule(final I_M_Shipper shipper, final boolean isProcessed)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Shipper_ID(shipper.getM_Shipper_ID());
		schedule.setProcessed(isProcessed);
		schedule.setAD_Table_ID(0);
		schedule.setRecord_ID(0);
		saveRecord(schedule);
		return ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
	}

	private static void registerInterceptorUnderTest(@NonNull final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL)
	{
		POJOLookupMap.get().addModelValidator(new M_Shipper_ShipmentSchedule(shipmentScheduleInvalidateBL));
	}
}
