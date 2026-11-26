package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
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

/**
 * The wait of {@code Backend.createMasterdata({shipments: ...})} before it ships an order.
 * <p>
 * The recompute of a shipment schedule deletes its {@code M_ShipmentSchedule_Recompute} marker in its own, already
 * committed transaction, while the recomputed QtyToDeliver is committed a moment later with the recompute's batch.
 * In between, the schedule looks valid but still has the QtyToDeliver it was created with (0), and shipping it then
 * finds nothing to ship ("No candidates found for shipment schedules of order ...").
 */
public class ShipmentSchedulesAwaiterTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(1);
	private static final Duration TIMEOUT = Duration.ofMinutes(1);

	private final List<Duration> sleeps = new ArrayList<>();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void waitsUntilTheRecomputedQtyToDeliverIsCommitted()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		// the recompute's marker is already gone, its QtyToDeliver not yet there
		final I_M_ShipmentSchedule schedule = createSchedule(orderLineId, BigDecimal.ZERO);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId), true, TIMEOUT, sleep -> {
			schedule.setQtyToDeliver(new BigDecimal("10")); // now the recompute's batch is committed
			saveRecord(schedule);
		}).await();

		assertThat(schedules).hasSize(1);
		assertThat(schedules.get(0).getQtyToDeliver()).isEqualByComparingTo("10");
		assertThat(sleeps).hasSize(1);
	}

	@Test
	public void waitsWhileAScheduleIsFlaggedForRecompute()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		final I_M_ShipmentSchedule schedule = createSchedule(orderLineId, new BigDecimal("10"));
		final I_M_ShipmentSchedule_Recompute marker = newInstance(I_M_ShipmentSchedule_Recompute.class);
		marker.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		saveRecord(marker);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId), true, TIMEOUT, sleep -> InterfaceWrapperHelper.delete(marker)).await();

		assertThat(schedules).hasSize(1);
		assertThat(sleeps).hasSize(1);
	}

	@Test
	public void waitsUntilTheSchedulesAreCreated()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId), true, TIMEOUT, sleep -> createSchedule(orderLineId, new BigDecimal("5"))).await();

		assertThat(schedules).hasSize(1);
		assertThat(sleeps).hasSize(1);
	}

	@Test
	public void ignoresOrderLinesWithoutSchedule()
	{
		final OrderLineId itemLineId = OrderLineId.ofRepoId(10);
		final OrderLineId serviceLineId = OrderLineId.ofRepoId(20); // e.g. a service product: it never gets a schedule
		createSchedule(itemLineId, new BigDecimal("10"));

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(itemLineId, serviceLineId), true, TIMEOUT, sleep -> {}).await();

		assertThat(schedules).hasSize(1);
		assertThat(sleeps).isEmpty();
	}

	@Test
	public void aLineWithNothingToDeliverDoesNotBlockTheOthers()
	{
		final OrderLineId orderLineId1 = OrderLineId.ofRepoId(10);
		final OrderLineId orderLineId2 = OrderLineId.ofRepoId(20);
		createSchedule(orderLineId1, new BigDecimal("10"));
		createSchedule(orderLineId2, BigDecimal.ZERO);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId1, orderLineId2), true, TIMEOUT, sleep -> {}).await();

		assertThat(schedules).hasSize(2);
		assertThat(sleeps).isEmpty();
	}

	@Test
	public void aProcessedScheduleDoesNotCountAsSomethingToDeliver()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		final I_M_ShipmentSchedule schedule = createSchedule(orderLineId, new BigDecimal("10"));
		schedule.setProcessed(true);
		saveRecord(schedule);

		assertThatThrownBy(() -> awaiter(ImmutableSet.of(orderLineId), true, Duration.ZERO, sleep -> {}).await())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyToDeliver");
	}

	@Test
	public void theQtyToDeliverOverrideCounts()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		final I_M_ShipmentSchedule schedule = createSchedule(orderLineId, BigDecimal.ZERO);
		schedule.setQtyToDeliver_Override(new BigDecimal("3"));
		saveRecord(schedule);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId), true, TIMEOUT, sleep -> {}).await();

		assertThat(schedules).hasSize(1);
		assertThat(sleeps).isEmpty();
	}

	@Test
	public void aZeroQtyToDeliverOverrideIsNothingToDeliver()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		final I_M_ShipmentSchedule schedule = createSchedule(orderLineId, new BigDecimal("10"));
		schedule.setQtyToDeliver_Override(BigDecimal.ZERO);
		saveRecord(schedule);

		assertThatThrownBy(() -> awaiter(ImmutableSet.of(orderLineId), true, Duration.ZERO, sleep -> {}).await())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyToDeliver");
	}

	@Test
	public void aZeroQtyToDeliverIsFineWhenItIsNotWhatIsShipped()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		createSchedule(orderLineId, BigDecimal.ZERO);

		final List<I_M_ShipmentSchedule> schedules = awaiter(ImmutableSet.of(orderLineId), false, TIMEOUT, sleep -> {}).await();

		assertThat(schedules).hasSize(1);
		assertThat(sleeps).isEmpty();
	}

	@Test
	public void failsAfterTheTimeoutAndSaysWhatIsMissing()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		createSchedule(orderLineId, BigDecimal.ZERO);

		assertThatThrownBy(() -> awaiter(ImmutableSet.of(orderLineId), true, Duration.ZERO, sleep -> {}).await())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Timeout waiting for the shipment schedules of order " + ORDER_ID)
				.hasMessageContaining("QtyToDeliver");
	}

	private ShipmentSchedulesAwaiter awaiter(
			final ImmutableSet<OrderLineId> orderLineIds,
			final boolean requirePositiveQtyToDeliver,
			final Duration timeout,
			final Consumer<Duration> onSleep)
	{
		return ShipmentSchedulesAwaiter.builder()
				.orderId(ORDER_ID)
				.orderLineIds(orderLineIds)
				.requirePositiveQtyToDeliver(requirePositiveQtyToDeliver)
				.timeout(timeout)
				.pollInterval(Duration.ofSeconds(1))
				.sleeper(sleep -> {
					sleeps.add(sleep);
					onSleep.accept(sleep);
				})
				.build();
	}

	private static I_M_ShipmentSchedule createSchedule(final OrderLineId orderLineId, final BigDecimal qtyToDeliver)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		schedule.setRecord_ID(orderLineId.getRepoId());
		schedule.setC_OrderLine_ID(orderLineId.getRepoId());
		schedule.setC_Order_ID(ORDER_ID.getRepoId());
		schedule.setQtyToDeliver(qtyToDeliver);
		saveRecord(schedule);
		return schedule;
	}
}
