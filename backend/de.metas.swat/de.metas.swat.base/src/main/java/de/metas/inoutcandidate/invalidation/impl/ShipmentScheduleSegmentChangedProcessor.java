package de.metas.inoutcandidate.invalidation.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.slf4j.Logger;

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString(of = "segments")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class ShipmentScheduleSegmentChangedProcessor
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleSegmentChangedProcessor.class);

	private static final String TRX_PROPERTYNAME = ShipmentScheduleSegmentChangedProcessor.class.getName();

	public static ShipmentScheduleSegmentChangedProcessor getOrCreateIfThreadInheritedElseNull(
			@NonNull final ShipmentScheduleInvalidateBL shipmentScheduleInvalidator)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		// during its post-commit listener dispatch a committed trx stays thread-inherited but inactive; its collector would never be flushed again.
		// An unstarted trx (it starts lazily on its first DB access) does get a collector: it commits later.
		if (!ThreadInheritedTrxState.isThreadTrxOpenForWrites(trx))
		{
			return null;
		}

		return getOrCreate(trx, shipmentScheduleInvalidator);
	}

	private static ShipmentScheduleSegmentChangedProcessor getOrCreate(
			@NonNull final ITrx trx,
			@NonNull final ShipmentScheduleInvalidateBL shipmentScheduleInvalidator)
	{
		ShipmentScheduleSegmentChangedProcessor processor = trx.getProperty(TRX_PROPERTYNAME);
		if (processor == null)
		{
			// The mid-batch flush threshold is owned by the invalidation BL (which owns the sysconfig read); the
			// processor obtains it from there instead of reaching into the Services registry itself. Resolved once,
			// here, because the processor is created exactly once per transaction.
			final int flushThreshold = shipmentScheduleInvalidator.getSegmentFlushThreshold();

			processor = new ShipmentScheduleSegmentChangedProcessor(shipmentScheduleInvalidator, flushThreshold);
			trx.setProperty(TRX_PROPERTYNAME, processor);

			// register our listener: we will actually fire the storage segment changed when the transaction is commited
			// Listens the {@link ITrx} and on commit actually fires the segment changed event
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
					.registerHandlingMethod(innerTrx -> {
						final ShipmentScheduleSegmentChangedProcessor innerProcessor = innerTrx.getProperty(TRX_PROPERTYNAME);
						if (innerProcessor == null)
						{
							// nothing to do
							return;
						}

						innerProcessor.flushAfterCommit();
					});
		}
		return processor;
	}

	private final Set<IShipmentScheduleSegment> segments = new LinkedHashSet<>();
	@NonNull private final ShipmentScheduleInvalidateBL shipmentScheduleInvalidator;

	/**
	 * Mid-batch flush threshold, obtained from the owning {@link ShipmentScheduleInvalidateBL} by the factory when
	 * this per-trx processor is created (the value is stable for the lifetime of the batch, and reading it once
	 * bounds the per-{@link #addSegment} cost). A value {@code <= 0} disables the mid-batch flush — only the
	 * AFTER_COMMIT listener flushes then.
	 */
	private final int flushThreshold;

	/**
	 * AFTER_COMMIT flush: these segments describe an already-committed transaction, so the flags are always written out of
	 * transaction. The thread-inherited transaction may meanwhile have been restarted by an earlier after-commit listener,
	 * and flags written into it could be lost if that transaction rolls back.
	 */
	private void flushAfterCommit()
	{
		if (!segments.isEmpty())
		{
			final List<IShipmentScheduleSegment> segmentsCopy = new ArrayList<>(segments);

			// Flag FIRST, clear AFTER: if flagging throws, the segments stay accumulated instead of being lost.
			// Flagging is idempotent (it only marks schedules as "needs recompute"), so a retry is a harmless no-op.
			shipmentScheduleInvalidator.flagSegmentsForRecomputeOutOfTrx(segmentsCopy);
			segments.clear();
		}

		// the processor lives as long as the ITrx, which can commit several times: every successful commit-flush re-enables the mid-batch flush
		midBatchFlushDisabled = false;
	}

	/**
	 * Set after the first failed mid-batch flush: the remaining segments are left to {@link #flushAfterCommit()} instead of
	 * retrying (and warning about) the full accumulator on every further {@link #addSegment}. Re-enabled by the next successful {@link #flushAfterCommit()}.
	 */
	private boolean midBatchFlushDisabled = false;

	/**
	 * Mid-batch flush. With a thread-inherited transaction that is open for writes (see {@link ThreadInheritedTrxState}) the flags are
	 * written in it, behind a savepoint, so they commit or roll back with the change that made the segments stale, and a failure rolls
	 * back to the savepoint instead of aborting the business transaction.
	 * The out-of-trx branch is defensive only: a processor is only handed out for an open trx, but a caller may keep its reference and add
	 * segments from that trx's post-commit listener (running in the thread-inherited trx there would restart a transaction that is never committed).
	 * On failure the segments are kept, further mid-batch flushes are disabled, and {@link #flushAfterCommit()} flags them.
	 */
	private void flushMidBatch()
	{
		if (midBatchFlushDisabled || segments.isEmpty())
		{
			return;
		}

		final List<IShipmentScheduleSegment> segmentsCopy = new ArrayList<>(segments);

		try
		{
			final ITrxManager trxManager = Services.get(ITrxManager.class);
			if (ThreadInheritedTrxState.isThreadInheritedTrxOpenForWrites())
			{
				// runInThreadInheritedTrx is NESTED: it creates a savepoint and rolls back to it on failure
				trxManager.runInThreadInheritedTrx(
						() -> shipmentScheduleInvalidator.flagSegmentsForRecomputeInThreadInheritedTrx(segmentsCopy));
			}
			else
			{
				shipmentScheduleInvalidator.flagSegmentsForRecomputeOutOfTrx(segmentsCopy);
			}
		}
		catch (final RuntimeException ex)
		{
			midBatchFlushDisabled = true;
			logger.warn("Mid-batch invalidation-segment flush failed ({} segments retained for the AFTER_COMMIT flush; further mid-batch flushes disabled)", segments.size(), ex);
			return;
		}

		// Flag FIRST, clear AFTER (see flushAfterCommit)
		segments.clear();
	}

	public void addSegment(final IShipmentScheduleSegment segment)
	{
		if (segment == null)
		{
			return;
		}

		// Normalize to the value-based ImmutableShipmentScheduleSegment before adding, so the dedupe Set actually
		// collapses logically-equal segments. Some IShipmentScheduleSegment implementations (e.g. the HU-derived
		// ShipmentScheduleSegmentFromHU/-Storage/-Attribute) use identity equals/hashCode; without this copy each
		// fresh instance would be retained as distinct and the Set would degrade to list-like unbounded growth
		// (the same OOM this class guards against). copyOf is a no-op for already-immutable segments.
		this.segments.add(ImmutableShipmentScheduleSegment.copyOf(segment));

		// Bound the accumulator during a long-running batch: flush mid-batch once it reaches the configured threshold
		// (a threshold <= 0 disables this). In an open trx the flush runs in it behind a savepoint, otherwise out of trx; see flushMidBatch().
		if (flushThreshold > 0 && segments.size() >= flushThreshold)
		{
			flushMidBatch();
		}
	}

	public void addSegments(final Collection<IShipmentScheduleSegment> segments)
	{
		if (segments == null || segments.isEmpty())
		{
			return;
		}

		for (final IShipmentScheduleSegment segment : segments)
		{
			addSegment(segment);
		}
	}
}
