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
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ShipmentScheduleWithHUService}.
 */
class ShipmentScheduleWithHUServiceTest
{
	private ShipmentScheduleWithHUService shipmentScheduleWithHUService;
	private ProductId productId;
	private ListAppender<ILoggingEvent> logAppender;
	private Logger logger;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleWithHUService = ShipmentScheduleWithHUService.newInstanceForUnitTesting();
		// not stocked: a quantity-to-deliver run then ships without picking HUs on the fly, so these tests need no stock / partner fixture
		final I_M_Product product = BusinessTestHelper.createProduct("product", BusinessTestHelper.createUomEach());
		product.setIsStocked(false);
		InterfaceWrapperHelper.save(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

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
	 * Edge case: 2 schedules submitted, but 1 already processed
	 * Should throw exception (single unprocessed schedule mode, not batch mode)
	 */
	@Test
	void batchProcessing_edgeCase_twoSchedules_oneAlreadyProcessed_shouldThrowException()
	{
		// Given: 2 schedules submitted, but first one is already processed
		final I_M_ShipmentSchedule schedule1 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(601));
		schedule1.setProcessed(true); // Already processed
		InterfaceWrapperHelper.save(schedule1);

		final I_M_ShipmentSchedule schedule2 = createShipmentSchedule(ShipmentScheduleId.ofRepoId(602));
		// schedule2 is NOT processed, has no picked HUs

		final ShipmentScheduleAndJobSchedulesCollection schedules = ShipmentScheduleAndJobSchedulesCollection
				.ofShipmentSchedules(ImmutableList.of(schedule1, schedule2));

		final ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest request = ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(schedules)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
				.failOnSingleScheduleWithNoPickedHUs(true)
				.build();

		// When/Then: Should throw exception (only 1 unprocessed schedule, NOT batch mode)
		assertThatThrownBy(() -> shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Batch processing behavior:
	 * Verify that schedules with no picked HUs are skipped without throwing exception in batch mode.
	 * Also verifies WARN log is emitted (if Loggables routes to SLF4J in test environment).
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
		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(request);

		// Then: Primary assertion - no exception thrown and empty result
		assertThat(result)
				.as("Batch mode should skip schedules with no picked HUs without throwing exception")
				.isEmpty();

		// Secondary assertion - verify logs (may not work if Loggables doesn't route to SLF4J in tests)
		final long warnCount = logAppender.list.stream()
				.filter(event -> event.getLevel() == Level.WARN)
				.filter(event -> event.getFormattedMessage().contains("Skipping shipment schedule"))
				.filter(event -> event.getFormattedMessage().contains("batch processing"))
				.count();

		// Note: This assertion may be fragile if Loggables routes to work-package log instead of SLF4J
		assertThat(warnCount)
				.as("Should log WARN for each skipped schedule (if Loggables routes to SLF4J)")
				.isEqualTo(2);
	}

	// ---------------------------------------------------------------------------------------------------------------
	// A shipment run still ships a schedule that an earlier shipment's completion closed and a recompute then
	// processed, when the run carries a quantity for it (unshipped picks in its scope, or an entered QtyToDeliver_Override).
	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * (1) flag set, schedule closed + processed, unshipped pick in this run's scope: the schedule is shipped with its pick.
	 */
	@Test
	void closedProcessedSchedule_flagSet_unshippedPickInScope_isShipped()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(701), true);
		createPick(schedule, new BigDecimal("5"), null);

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY, true, null));

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getShipmentScheduleId()).isEqualTo(ShipmentScheduleId.ofRepoId(701));
		assertThat(result.get(0).getQtyPicked().toBigDecimal()).isEqualByComparingTo("5");
	}

	/**
	 * (2) flag set, schedule closed + processed, quantity type D, entered QtyToDeliver_Override > 0: the schedule is shipped with that quantity.
	 */
	@Test
	void closedProcessedSchedule_flagSet_qtyToDeliverOverride_isShippedWithThatQty()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(702), true);
		schedule.setQtyToDeliver(BigDecimal.ZERO); // what the recompute leaves on a closed schedule
		schedule.setQtyToDeliver_Override(new BigDecimal("7"));
		InterfaceWrapperHelper.save(schedule);

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER, true, null));

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getShipmentScheduleId()).isEqualTo(ShipmentScheduleId.ofRepoId(702));
		assertThat(result.get(0).getQtyPicked().toBigDecimal()).isEqualByComparingTo("7");
	}

	/**
	 * (3) single schedule, type P, failOnSingleScheduleWithNoPickedHUs = true, flag set, closed + processed, no picks:
	 * the run carries no quantity for it, so it is skipped as before - no "no qty picked" exception.
	 */
	@Test
	void closedProcessedSchedule_flagSet_noPicks_isSkippedWithoutException()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(703), true);

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY, true, null));

		assertThat(result).isEmpty();
	}

	/**
	 * (4) flag NOT set (any other caller), closed + processed with picks: skipped as before.
	 */
	@Test
	void closedProcessedSchedule_flagNotSet_withPicks_isSkipped()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(704), true);
		createPick(schedule, new BigDecimal("5"), null);

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY, false, null));

		assertThat(result).isEmpty();
	}

	/**
	 * (5) flag set, processed but NOT closed, with an unshipped pick in scope: skipped (only the "closed" condition decides).
	 */
	@Test
	void processedNotClosedSchedule_flagSet_withPicks_isSkipped()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(705), false);
		createPick(schedule, new BigDecimal("5"), null);

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY, true, null));

		assertThat(result).isEmpty();
	}

	/**
	 * (6) single schedule, type P, flag set, closed + processed, unshipped picks only on an LU outside the run's OnlyLUIds:
	 * the run carries no quantity for it, so it is skipped - no "no qty picked" exception.
	 */
	@Test
	void closedProcessedSchedule_flagSet_picksOnlyOutsideOnlyLUIds_isSkippedWithoutException()
	{
		final I_M_ShipmentSchedule schedule = createClosedSchedule(ShipmentScheduleId.ofRepoId(706), true);
		createPick(schedule, new BigDecimal("5"), HuId.ofRepoId(9001));

		final ImmutableList<ShipmentScheduleWithHU> result = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				requestFor(schedule, M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY, true, ImmutableSet.of(HuId.ofRepoId(9002))));

		assertThat(result).isEmpty();
	}

	private ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest requestFor(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityType,
			final boolean includeProcessedClosedSchedulesWithQty,
			@Nullable final Set<HuId> onlyLUIds)
	{
		return ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest.builder()
				.schedules(ShipmentScheduleAndJobSchedulesCollection.ofShipmentSchedules(ImmutableList.of(schedule)))
				.quantityTypeToUse(quantityType)
				.onlyLUIds(onlyLUIds)
				.includeProcessedClosedSchedulesWithQty(includeProcessedClosedSchedulesWithQty)
				.build(); // failOnSingleScheduleWithNoPickedHUs keeps its default (true)
	}

	private I_M_ShipmentSchedule createClosedSchedule(final ShipmentScheduleId id, final boolean closed)
	{
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(id);
		schedule.setM_Product_ID(productId.getRepoId());
		schedule.setIsClosed(closed);
		schedule.setProcessed(true);
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}

	private void createPick(@NonNull final I_M_ShipmentSchedule schedule, @NonNull final BigDecimal qtyPicked, @Nullable final HuId luId)
	{
		final I_M_ShipmentSchedule_QtyPicked pick = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		pick.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		pick.setQtyPicked(qtyPicked);
		pick.setM_LU_HU_ID(HuId.toRepoId(luId));
		InterfaceWrapperHelper.save(pick);
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
