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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.ShipmentScheduleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ShipmentScheduleWithHUService}.
 */
class ShipmentScheduleWithHUServiceTest
{
	private ShipmentScheduleWithHUService shipmentScheduleWithHUService;
	private ListAppender<ILoggingEvent> logAppender;
	private Logger logger;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleWithHUService = ShipmentScheduleWithHUService.newInstanceForUnitTesting();

		// Setup log capturing
		logger = (Logger) LoggerFactory.getLogger(ShipmentScheduleWithHUService.class);
		logAppender = new ListAppender<>();
		logAppender.start();
		logger.addAppender(logAppender);
	}

	@AfterEach
	void afterEach()
	{
		// Cleanup log appender
		if (logger != null && logAppender != null)
		{
			logger.detachAppender(logAppender);
		}
	}

	/**
	 * Single-schedule mode:
	 * Single schedule with no picked HUs should throw exception when failOnSingleScheduleWithNoPickedHUs=true
	 */
	@Test
	void singleSchedule_noPickedHUs_failEnabled_shouldThrowException()
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
	 * Single-schedule mode:
	 * Single schedule with no picked HUs should NOT throw when failOnSingleScheduleWithNoPickedHUs=false
	 */
	@Test
	void singleSchedule_noPickedHUs_failDisabled_shouldReturnEmpty()
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

		// Then: Should return an empty list (no exception)
		assertThat(result).isEmpty();
	}

	/**
	 * Batch processing behavior:
	 * Multiple schedules with no picked HUs should NOT throw even when failOnSingleScheduleWithNoPickedHUs=true
	 */
	@Test
	void batchProcessing_multipleSchedules_allWithNoPickedHUs_failEnabled_shouldNotThrow()
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
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When: Process batch - should not throw despite failOnSingleScheduleWithNoPickedHUs=true
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should complete without exception, all schedules skipped
		assertThat(result).isNotNull().isEmpty();
	}

	/**
	 * Batch processing behavior:
	 * Multiple schedules with no picked HUs should NOT throw when failOnSingleScheduleWithNoPickedHUs=false
	 */
	@Test
	void batchProcessing_multipleSchedules_allWithNoPickedHUs_failDisabled_shouldNotThrow()
	{
		// Given: 3 schedules - all have no picked HUs (test mode returns empty by default)
		final I_M_ShipmentSchedule schedule1 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(201));
		final I_M_ShipmentSchedule schedule2 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(202));
		final I_M_ShipmentSchedule schedule3 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(203));

		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule1, schedule2, schedule3));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(false) // With fail=false, batch mode should not throw
				.build();

		// When: Process batch
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should complete without exception (schedules with no picked HUs are skipped)
		assertThat(result).isNotNull().isEmpty();
	}

	/**
	 * Batch processing boundary test:
	 * Exactly 1 schedule should throw exception when failOnSingleScheduleWithNoPickedHUs=true
	 */
	@Test
	void batchProcessing_boundary_exactlyOneSchedule_shouldThrowException()
	{
		// Given: Exactly 1 schedule (boundary: not batch mode)
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(ShipmentScheduleId.ofRepoId(301));
		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When/Then: Should throw exception (size == 1, not batch mode)
		assertThatThrownBy(() -> shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Batch processing boundary test:
	 * Exactly 2 schedules should NOT throw exception (batch mode threshold)
	 */
	@Test
	void batchProcessing_boundary_exactlyTwoSchedules_shouldSkipAndContinue()
	{
		// Given: Exactly 2 schedules (boundary: batch mode threshold)
		final I_M_ShipmentSchedule schedule1 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(401));
		final I_M_ShipmentSchedule schedule2 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(402));
		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule1, schedule2));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When: Process batch (size == 2, batch mode enabled)
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should complete without exception (size > 1 triggers batch mode)
		assertThat(result).isNotNull().isEmpty();
	}

	/**
	 * Batch processing behavior:
	 * Verify that WARN log is emitted when a schedule is skipped in batch mode
	 */
	@Test
	void batchProcessing_multipleSchedules_shouldLogWarningWhenSkippingSchedule()
	{
		// Given: 2 schedules in batch mode (both have no picked HUs)
		final I_M_ShipmentSchedule schedule1 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(501));
		final I_M_ShipmentSchedule schedule2 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(502));
		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule1, schedule2));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When: Process batch
		logAppender.list.clear(); // Clear any previous logs
		shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Should have logged WARN messages for skipped schedules
		final long warnCount = logAppender.list.stream()
				.filter(event -> event.getLevel() == Level.WARN)
				.filter(event -> event.getFormattedMessage().contains("Skipping shipment schedule"))
				.filter(event -> event.getFormattedMessage().contains("Continuing with remaining schedules"))
				.count();

		assertThat(warnCount)
				.as("Should log WARN for each skipped schedule in batch mode")
				.isEqualTo(2); // Both schedules should be logged as skipped
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
