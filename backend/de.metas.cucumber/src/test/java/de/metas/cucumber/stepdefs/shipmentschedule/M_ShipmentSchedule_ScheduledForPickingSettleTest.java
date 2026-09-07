/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shipmentschedule;

import de.metas.cucumber.stepdefs.StepDefUtil;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards the {@code validate shipment schedules} step's picking-reconcile assertion against a transient
 * stale read.
 * <p>
 * After {@code create or update picking job schedules}, the shipment schedule already exists (with its
 * delivery quantities settled), but the async picking-job-schedule reconcile writes
 * {@code IsScheduledForPicking=Y} / {@code QtyScheduledForPicking=<qty>} a moment later. A readiness
 * poll that only checks the schedule's existence (or its delivery quantities) therefore returns before
 * the picking columns are written, and the hard assertion then reads the stale {@code N} / {@code 0}.
 * The fix gates the poll on the target picking-column values; these tests model that predicate over
 * {@link #pickingReadSource(int)} and exercise the real
 * {@link StepDefUtil#tryAndWait(long, long, java.util.function.Supplier)} used by the step.
 */
class M_ShipmentSchedule_ScheduledForPickingSettleTest
{
	private static final BigDecimal EXPECTED_QTY = new BigDecimal("3");

	/**
	 * Models the observed picking-reconcile state. The first {@code staleReads} reads return the
	 * pre-reconcile state ({@code IsScheduledForPicking=false} / {@code QtyScheduledForPicking=0}); every
	 * read after that returns the settled state ({@code true} / {@code 3}).
	 * {@code staleReads = Integer.MAX_VALUE} models a reconcile that never happens (a genuine failure).
	 */
	private static Supplier<PickingState> pickingReadSource(final int staleReads)
	{
		final AtomicInteger reads = new AtomicInteger();
		return () -> reads.getAndIncrement() < staleReads
				? new PickingState(false, BigDecimal.ZERO)
				: new PickingState(true, EXPECTED_QTY);
	}

	private static boolean isSettled(final PickingState state)
	{
		return state.isScheduledForPicking && state.qtyScheduledForPicking.compareTo(EXPECTED_QTY) == 0;
	}

	@Test
	void singleImmediateRead_throwsOnTransientStalePickingRead()
	{
		// A single immediate read observes the pre-reconcile N/0 state and asserts Y/3 => AssertionError,
		// the spurious failure this de-flake removes.
		final Supplier<PickingState> source = pickingReadSource(1); // one transient stale read, then settled
		final PickingState observed = source.get();
		assertThatThrownBy(() -> {
			assertThat(observed.isScheduledForPicking).as("IsScheduledForPicking").isEqualTo(true);
			assertThat(observed.qtyScheduledForPicking).as("QtyScheduledForPicking").isEqualByComparingTo(EXPECTED_QTY);
		}).isInstanceOf(AssertionError.class);
	}

	@Test
	void boundedPoll_settlesOnEventualScheduledForPicking() throws InterruptedException
	{
		// The bounded readiness poll tolerates the transient N/0 reads and settles on Y/3.
		final Supplier<PickingState> source = pickingReadSource(3); // a few stale reads, then settled
		StepDefUtil.tryAndWait(10, 20, () -> isSettled(source.get()));
		// no exception => the poll observed the settled picking columns
	}

	@Test
	void boundedPoll_failsLoudWhenReconcileNeverHappens()
	{
		// Non-masking guarantee: a schedule whose picking reconcile never runs (a real failure) still
		// fails on timeout rather than being silently swallowed.
		final Supplier<PickingState> source = pickingReadSource(Integer.MAX_VALUE); // always N/0
		assertThatThrownBy(() -> StepDefUtil.tryAndWait(1, 100, () -> isSettled(source.get())))
				.isInstanceOf(AssertionError.class);
	}

	@Test
	void prePickingExpectation_isNotGated_soUnsettledValidateDoesNotTimeOut()
	{
		// A pre-picking validate step expects the initial unsettled state (IsScheduledForPicking=N /
		// QtyScheduledForPicking=0). QtyScheduledForPicking is nullable with no default, so pre-picking it is
		// NULL in the DB; gating the poll on it would emit SQL `QtyScheduledForPicking = 0`, which never matches
		// NULL, so the poll would time out even though nothing async is pending. The gate MUST NOT apply to the
		// unsettled expectation. This locks the regression the first cut of the fix introduced.
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnScheduledForPicking(false))
				.as("gate on expected IsScheduledForPicking=N").isFalse();
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnScheduledForPicking(null))
				.as("gate on absent IsScheduledForPicking").isFalse();
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnQtyScheduledForPicking(BigDecimal.ZERO))
				.as("gate on expected QtyScheduledForPicking=0").isFalse();
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnQtyScheduledForPicking(null))
				.as("gate on absent QtyScheduledForPicking").isFalse();
	}

	@Test
	void postPickingExpectation_isGatedOnSettledColumns()
	{
		// A post-picking validate step expects the settled Y/3 state, so the poll must wait for the
		// async reconcile to write it (this is the de-flake).
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnScheduledForPicking(true))
				.as("gate on expected IsScheduledForPicking=Y").isTrue();
		assertThat(M_ShipmentSchedule_StepDef.shouldGateOnQtyScheduledForPicking(EXPECTED_QTY))
				.as("gate on expected QtyScheduledForPicking=3").isTrue();
	}

	@Value
	private static class PickingState
	{
		boolean isScheduledForPicking;
		BigDecimal qtyScheduledForPicking;
	}
}
