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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regression guard for {@link ShipmentScheduleSegmentChangedProcessor}'s segment accumulation.
 * <p>
 * The processor accumulates segments into a dedup {@link java.util.LinkedHashSet} and, on trx commit (AFTER_COMMIT
 * listener), flushes them out of transaction via {@link ShipmentScheduleInvalidateBL#flagSegmentsForRecomputeOutOfTrx(Collection)}
 * (a mid-batch flush uses {@link ShipmentScheduleInvalidateBL#flagSegmentsForRecomputeInThreadInheritedTrx(Collection)} in an open trx). These tests
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
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(captor.capture());

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
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(captor.capture());

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
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(captor.capture());

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
		verify(invalidator, atLeastOnce()).flagSegmentsForRecomputeInThreadInheritedTrx(captor.capture());
		final List<Collection<IShipmentScheduleSegment>> flushes = new ArrayList<>(captor.getAllValues());
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> afterCommitCaptor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(afterCommitCaptor.capture());
		flushes.addAll(afterCommitCaptor.getAllValues());

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
	 * A failing mid-batch flush (in the business transaction, behind a savepoint) must not break the transaction and must not lose
	 * the segments: they are retained and the AFTER_COMMIT flush passes them to the out-of-trx method.
	 */
	@Test
	void midBatchFlushFailureRetainsSegments()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);
		when(invalidator.getSegmentFlushThreshold()).thenReturn(1);
		doThrow(new RuntimeException("test: mid-batch flush failed"))
				.doNothing()
				.when(invalidator).flagSegmentsForRecomputeInThreadInheritedTrx(any());

		final ImmutableShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder()
				.productId(1)
				.bpartnerId(2)
				.locatorId(3)
				.build();

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor).isNotNull();

			// reaches the threshold -> mid-batch flush -> throws -> must be swallowed
			processor.addSegment(segment);
		});

		verify(invalidator, times(1)).flagSegmentsForRecomputeInThreadInheritedTrx(any());

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(captor.capture());
		assertThat(captor.getValue())
				.as("the segment of the failed mid-batch flush must be retained and flagged out of trx after commit")
				.containsExactly(segment);
	}

	/**
	 * After a failed mid-batch flush no further mid-batch flush is attempted (no O(n^2) retries / warn flood);
	 * all segments are flagged by the AFTER_COMMIT flush.
	 */
	@Test
	void afterMidBatchFlushFailure_noFurtherMidBatchFlushAttempts()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);
		when(invalidator.getSegmentFlushThreshold()).thenReturn(1);
		doThrow(new RuntimeException("test: mid-batch flush failed"))
				.when(invalidator).flagSegmentsForRecomputeInThreadInheritedTrx(any());

		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			final ShipmentScheduleSegmentChangedProcessor processor =
					ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor).isNotNull();
			for (int productId = 1; productId <= 3; productId++)
			{
				processor.addSegment(ImmutableShipmentScheduleSegment.builder().productId(productId).bpartnerId(2).locatorId(3).build());
			}
		});

		verify(invalidator, times(1)).flagSegmentsForRecomputeInThreadInheritedTrx(any());

		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator).flagSegmentsForRecomputeOutOfTrx(captor.capture());
		assertThat(captor.getValue()).hasSize(3);
	}

	/**
	 * A segment that reaches the threshold when the thread trx is already committed (here: added by an after-commit listener that
	 * runs after the processor's own commit flush) must be flagged out of trx; running in the thread-inherited trx would restart a
	 * transaction that is never committed.
	 */
	@Test
	void midBatchFlushAfterCommit_flagsOutOfTrx_neverInThreadInheritedTrx()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);
		when(invalidator.getSegmentFlushThreshold()).thenReturn(1);

		final ImmutableShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder().productId(1).bpartnerId(2).locatorId(3).build();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> {
			final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
			// obtained while the trx is active: an after-commit callback never gets a collector on the committed (inactive) trx
			final ShipmentScheduleSegmentChangedProcessor processor = ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor).isNotNull();

			// registered AFTER the processor's own listener (created by getOrCreate above), so it runs after the commit flush
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.registerHandlingMethod(committedTrx -> processor.addSegment(segment));
		});

		verify(invalidator, never()).flagSegmentsForRecomputeInThreadInheritedTrx(any());
		@SuppressWarnings("unchecked")
		final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
		verify(invalidator, atLeastOnce()).flagSegmentsForRecomputeOutOfTrx(captor.capture());
		assertThat(captor.getAllValues().stream().flatMap(Collection::stream)).contains(segment);
	}

	/**
	 * The processor lives as long as the ITrx, which can commit several times: a mid-batch failure disables the mid-batch flush only
	 * until the next successful commit flush.
	 */
	@Test
	void midBatchFlushDisabledByFailure_isReEnabledByTheNextCommit()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);
		when(invalidator.getSegmentFlushThreshold()).thenReturn(1);
		doThrow(new RuntimeException("test: mid-batch flush failed")).when(invalidator).flagSegmentsForRecomputeInThreadInheritedTrx(any());

		final ImmutableShipmentScheduleSegment s1 = ImmutableShipmentScheduleSegment.builder().productId(1).bpartnerId(2).locatorId(3).build();
		final ImmutableShipmentScheduleSegment s2 = ImmutableShipmentScheduleSegment.builder().productId(2).bpartnerId(2).locatorId(3).build();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.createTrxName("test", true);
		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			final ITrx trx = trxManager.getTrx(trxName);
			trx.start();
			final ShipmentScheduleSegmentChangedProcessor processor = ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator);
			assertThat(processor).isNotNull();

			processor.addSegment(s1); // active trx -> in-trx flush -> fails -> mid-batch flush disabled
			// registered after the processor's own listener: runs after its commit flush (succeeds -> re-enabled), while the trx is
			// committed and not restarted -> the mid-batch flush must run again, out of trx
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.registerHandlingMethod(committedTrx -> processor.addSegment(s2));
			trx.commit(true);

			@SuppressWarnings("unchecked")
			final ArgumentCaptor<Collection<IShipmentScheduleSegment>> captor = ArgumentCaptor.forClass(Collection.class);
			verify(invalidator, atLeastOnce()).flagSegmentsForRecomputeOutOfTrx(captor.capture());
			assertThat(captor.getAllValues().stream().flatMap(Collection::stream))
					.as("s2 must be flushed mid-batch after the commit re-enabled the mid-batch flush")
					.contains(s2);
		}
		catch (final java.sql.SQLException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}
	}

	/** A committed transaction keeps being thread-inherited in its after-commit callbacks, but a collector on it would never be flushed again. */
	@Test
	void getOrCreate_committedThreadTrxInAfterCommit_returnsNull()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final List<ShipmentScheduleSegmentChangedProcessor> processors = new ArrayList<>();
		final List<ITrx> trxs = new ArrayList<>();
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
			trxs.add(trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone));
			processors.add(ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator));
		}));

		assertThat(trxManager.isNull(trxs.get(0))).as("setup: the committed trx is still thread-inherited").isFalse();
		assertThat(trxs.get(0).isActive()).as("setup: committed, not restarted").isFalse();
		assertThat(processors).as("no collector on a committed trx during its post-commit dispatch").containsExactly((ShipmentScheduleSegmentChangedProcessor)null);
	}

	/** Transactions start lazily (e.g. a process trx before its first SQL statement): an unstarted trx gets a collector. */
	@Test
	void getOrCreate_unstartedThreadTrx_returnsProcessor()
	{
		final ShipmentScheduleInvalidateBL invalidator = mock(ShipmentScheduleInvalidateBL.class);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.createTrxName("test-unstarted", true);
		assertThat(trxManager.getTrx(trxName).isActive()).as("setup: created, never started").isFalse();

		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			assertThat(ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(invalidator))
					.as("an unstarted trx is not committed; its collector is flushed when it commits")
					.isNotNull();
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}
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
