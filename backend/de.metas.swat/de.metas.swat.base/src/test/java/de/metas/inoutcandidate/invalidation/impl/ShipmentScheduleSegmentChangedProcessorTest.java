package de.metas.inoutcandidate.invalidation.impl;

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

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regression guard for {@link ShipmentScheduleSegmentChangedProcessor}'s segment accumulation.
 * <p>
 * The processor accumulates segments into a dedup {@link java.util.LinkedHashSet} and, on trx commit (AFTER_COMMIT
 * listener), flushes them to {@link ShipmentScheduleInvalidateBL#flagSegmentForRecompute(Collection)}. These tests
 * assert that (a) value-equal segments collapse to one, (b) distinct segments are all retained (dedupe, not
 * truncation), and (c) identity-equals implementations (e.g. the HU-derived segments) are also deduped because the
 * accumulator normalizes every segment to the value-based {@link ImmutableShipmentScheduleSegment} before adding.
 */
class ShipmentScheduleSegmentChangedProcessorTest
{
	private static final int REPEATED_ADD_COUNT = 1000;
	private static final int DISTINCT_SEGMENT_COUNT = 1000;

	/** Low, deterministic threshold so the mid-batch-flush test is fast and does not depend on the 1000 default. */
	private static final int FLUSH_THRESHOLD = 10;
	/** Distinct segments to add: > FLUSH_THRESHOLD so at least one mid-batch flush must fire before the trx commit. */
	private static final int OVER_THRESHOLD_DISTINCT_COUNT = 25;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Adding {@value #REPEATED_ADD_COUNT} separately-constructed but value-equal segments must be deduped to a single
	 * distinct segment.
	 * <p>
	 * Each iteration builds a FRESH {@link ImmutableShipmentScheduleSegment} instance (not the same reference), so this
	 * exercises the Lombok {@code @Value} equals/hashCode contract the Set-based accumulator relies on — mirroring the
	 * real caller ({@code ShipmentScheduleInvalidateBL.notifySegmentsChanged}), which builds fresh equal instances per
	 * invalidation event.
	 * <p>
	 * FAILS on the pre-fix ArrayList-based accumulator (retains all {@value #REPEATED_ADD_COUNT}).
	 */
	@Test
	void sameSegmentAddedManyTimes_isDedupedToOne()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			for (int i = 0; i < REPEATED_ADD_COUNT; i++)
			{
				// fresh, independently-built instance each time — value-equal, NOT the same reference
				processor.addSegment(ImmutableShipmentScheduleSegment.builder()
						.productId(1)
						.bpartnerId(2)
						.locatorId(3)
						.build());
			}
		});

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentForRecompute(captor.capture());

		assertThat(captor.getValue())
				.as("adding %d value-equal segments must be deduped to a single distinct segment", REPEATED_ADD_COUNT)
				.hasSize(1);
	}

	/**
	 * {@value #DISTINCT_SEGMENT_COUNT} DISTINCT segment values must all be retained (proves dedupe, not truncation).
	 * <p>
	 * PASSES on current code.
	 */
	@Test
	void distinctSegments_areAllRetained()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		final List<IShipmentScheduleSegment> distinctSegments = new ArrayList<>();
		for (int i = 0; i < DISTINCT_SEGMENT_COUNT; i++)
		{
			distinctSegments.add(ImmutableShipmentScheduleSegment.builder()
					.productId(i)
					.bpartnerId(2)
					.locatorId(3)
					.build());
		}

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			processor.addSegments(distinctSegments);
		});

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentForRecompute(captor.capture());

		assertThat(captor.getValue())
				.as("all %d distinct segments must be retained", DISTINCT_SEGMENT_COUNT)
				.hasSize(DISTINCT_SEGMENT_COUNT);
	}

	/**
	 * Identity-equals segment implementations must ALSO be deduped.
	 * <p>
	 * The HU-driven invalidation path ({@code M_HU_Storage}/{@code M_HU_Attribute}/{@code M_HU} changes) pushes
	 * segment classes that use Object identity equals/hashCode straight into the accumulator (they do NOT go through
	 * the value-based builder). The accumulator normalizes each segment to {@link ImmutableShipmentScheduleSegment}
	 * before adding, so {@value #REPEATED_ADD_COUNT} freshly-constructed but value-equal identity segments must
	 * collapse to one — otherwise this high-churn path would grow unbounded exactly like the pre-fix accumulator.
	 */
	@Test
	void identityEqualsSegments_valueEqual_areDedupedToOne()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			for (int i = 0; i < REPEATED_ADD_COUNT; i++)
			{
				// fresh identity-equals instance each iteration, all value-equal
				processor.addSegment(new IdentityEqualsSegment(1, 2, 3));
			}
		});

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentForRecompute(captor.capture());

		assertThat(captor.getValue())
				.as("value-equal identity-based segments must be normalized and deduped to a single segment")
				.hasSize(1);
	}

	/**
	 * Threshold flush. Adding {@value #OVER_THRESHOLD_DISTINCT_COUNT} DISTINCT segments while the flush
	 * threshold is {@value #FLUSH_THRESHOLD} must bound the accumulator: it flushes mid-batch when it reaches the
	 * threshold, not only at AFTER_COMMIT. Asserts (a) MORE THAN ONE flush fired (≥1 mid-batch flush before commit),
	 * (b) the UNION of all flushed segments equals the full distinct set (none lost, none duplicated), and (c) no
	 * single flush exceeded the threshold size (memory stayed bounded).
	 * <p>
	 * FAILS on the pre-fix code: only the AFTER_COMMIT listener flushes, so there is exactly ONE invocation carrying
	 * all {@value #OVER_THRESHOLD_DISTINCT_COUNT} segments — assertion (a) "more than one flush" fails.
	 */
	@Test
	void distinctSegmentsExceedingThreshold_areFlushedMidBatch_bounded()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);
		// The processor gets its flush threshold from the owning BL — stub a low, deterministic value directly on the mock.
		when(invalidator.getSegmentFlushThreshold()).thenReturn(FLUSH_THRESHOLD);

		final List<IShipmentScheduleSegment> distinctSegments = new ArrayList<>();
		for (int i = 0; i < OVER_THRESHOLD_DISTINCT_COUNT; i++)
		{
			distinctSegments.add(ImmutableShipmentScheduleSegment.builder()
					.productId(i)
					.bpartnerId(2)
					.locatorId(3)
					.build());
		}

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor)
					.as("processor must be created inside a thread-inherited trx")
					.isNotNull();

			// add one at a time so the accumulator can cross the threshold mid-batch
			for (final IShipmentScheduleSegment segment : distinctSegments)
			{
				processor.addSegment(segment);
			}
		});

		// capture EVERY flush: the mid-batch threshold flushes AND the final AFTER_COMMIT flush of the remainder
		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator, atLeastOnce()).flagSegmentForRecompute(captor.capture());
		final List<Collection<IShipmentScheduleSegment>> flushes = captor.getAllValues();

		// (a) more than one flush → at least one mid-batch flush fired before the trx commit
		assertThat(flushes.size())
				.as("adding %d distinct segments with threshold %d must flush mid-batch (>1 flush), not only at commit",
						OVER_THRESHOLD_DISTINCT_COUNT, FLUSH_THRESHOLD)
				.isGreaterThan(1);

		// (c) no single flush's batch exceeded the threshold size → memory stayed bounded
		for (final Collection<IShipmentScheduleSegment> flush : flushes)
		{
			assertThat(flush.size())
					.as("no single flush may exceed the threshold size %d", FLUSH_THRESHOLD)
					.isLessThanOrEqualTo(FLUSH_THRESHOLD);
		}

		// (b) union of all flushed segments == the full distinct set (none lost, none duplicated)
		final List<IShipmentScheduleSegment> union = new ArrayList<>();
		flushes.forEach(union::addAll);
		assertThat(union)
				.as("union of all flushed segments must equal the full distinct set — none lost, none duplicated")
				.containsExactlyInAnyOrderElementsOf(distinctSegments);
	}

	/**
	 * Minimal {@link IShipmentScheduleSegment} using Object (identity) equals/hashCode — models the HU-derived
	 * segments ({@code ShipmentScheduleSegmentFromHU}/{@code -Storage}/{@code -Attribute}), none of which override
	 * equals/hashCode.
	 */
	private static final class IdentityEqualsSegment implements IShipmentScheduleSegment
	{
		private final Set<Integer> productIds;
		private final Set<Integer> bpartnerIds;
		private final Set<Integer> locatorIds;

		IdentityEqualsSegment(final int productId, final int bpartnerId, final int locatorId)
		{
			this.productIds = ImmutableSet.of(productId);
			this.bpartnerIds = ImmutableSet.of(bpartnerId);
			this.locatorIds = ImmutableSet.of(locatorId);
		}

		@Override
		public Set<Integer> getProductIds() { return productIds; }

		@Override
		public Set<Integer> getBpartnerIds() { return bpartnerIds; }

		@Override
		public Set<Integer> getBillBPartnerIds() { return ImmutableSet.of(); }

		@Override
		public Set<Integer> getLocatorIds() { return locatorIds; }

		@Override
		public Set<ShipmentScheduleAttributeSegment> getAttributes() { return ImmutableSet.of(); }
	}
}
