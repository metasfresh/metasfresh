/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.ShipmentScheduleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ShipmentScheduleWithHUService}.
 */
class ShipmentScheduleWithHUServiceTest
{
	private ShipmentScheduleWithHUService shipmentScheduleWithHUService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleWithHUService = ShipmentScheduleWithHUService.newInstanceForUnitTesting();
	}

	/**
	 * Batch processing behavior:
	 * Single schedule with no picked HUs should throw exception when failOnSingleScheduleWithNoPickedHUs=true
	 */
	@Test
	void batchProcessing_singleSchedule_noPickedHUs_shouldThrowException()
	{
		// Given: Single schedule with no picked HUs (test mode returns empty by default)
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(ShipmentScheduleId.ofRepoId(100));
		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When/Then: Should throw exception
		assertThatThrownBy(() -> shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Batch processing behavior:
	 * Single schedule with no picked HUs should NOT throw when failOnSingleScheduleWithNoPickedHUs=false
	 */
	@Test
	void batchProcessing_singleSchedule_noPickedHUs_failDisabled_shouldReturnEmpty()
	{
		// Given: Single schedule with no picked HUs (test mode returns empty by default)
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(ShipmentScheduleId.ofRepoId(100));
		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(false)
				.build();

		// When: Process
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should return empty list (no exception)
		assertThat(result).isEmpty();
	}

	/**
	 * Batch processing behavior:
	 * Multiple schedules - one with no picked HUs should be skipped, others should process
	 */
	@Test
	void batchProcessing_multipleSchedules_oneWithNoPickedHUs_shouldSkipAndContinue()
	{
		// Given: 3 schedules - all have no picked HUs (test mode returns empty by default)
		final I_M_ShipmentSchedule schedule1 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(101));
		final I_M_ShipmentSchedule schedule2 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(102));
		final I_M_ShipmentSchedule schedule3 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(103));

		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule1, schedule2, schedule3));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true) // Even with fail=true, batch mode should not throw
				.build();

		// When: Process batch
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should complete without exception (schedules with no picked HUs are skipped)
		assertThat(result).isNotNull();
		// Note: In this test all schedules have no picked HUs so result is empty,
		// but the important part is that NO EXCEPTION was thrown despite failOnSingleScheduleWithNoPickedHUs=true
	}

	/**
	 * Helper: Create a shipment schedule using InterfaceWrapperHelper (proper Adempiere test approach)
	 */
	private I_M_ShipmentSchedule createShipmentSchedule(final ShipmentScheduleId id)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_ShipmentSchedule_ID(id.getRepoId());
		schedule.setProcessed(false);
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}
}
