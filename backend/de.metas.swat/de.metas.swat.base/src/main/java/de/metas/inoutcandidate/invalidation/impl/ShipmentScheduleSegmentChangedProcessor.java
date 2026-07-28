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
		if (trxManager.isNull(trx))
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

						innerProcessor.process();
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

	private void process()
	{
		if (segments.isEmpty())
		{
			return;
		}

		final List<IShipmentScheduleSegment> segmentsCopy = new ArrayList<>(segments);

		// Flag FIRST, clear AFTER: if flagging throws (it runs on a separate TRXNAME_None connection), the segments
		// stay accumulated and are retried by the next flush (the AFTER_COMMIT listener) instead of being lost.
		// flagSegmentForRecompute is idempotent (it only marks schedules as "needs recompute"), so a retry that
		// re-flags an already-flagged schedule is a harmless no-op.
		shipmentScheduleInvalidator.flagSegmentForRecompute(segmentsCopy);
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

		// Bound the accumulator during a long-running batch: flush mid-batch once it reaches the configured threshold,
		// so memory stays bounded regardless of batch size / dedupe effectiveness (not only at AFTER_COMMIT). A
		// threshold <= 0 disables the mid-batch flush. Safe mid-batch because the flush's matching SQL runs on its own
		// connection (TRXNAME_None) and matches only COMMITTED schedules; the batch's own new (uncommitted) schedules
		// are flagged by id directly on the batch trx via ShipmentScheduleInvalidateBL.notifySegmentChangedForShipmentScheduleInclSched
		// -> flagForRecompute, so these already-committed-targeting segments lose no invalidations.
		//
		// Best-effort: a mid-batch flush failure must NOT abort the enclosing business transaction (the AFTER_COMMIT
		// flush historically ran post-commit and could never do so). process() flags-then-clears, so on failure the
		// segments remain accumulated and are retried at commit.
		if (flushThreshold > 0 && segments.size() >= flushThreshold)
		{
			try
			{
				process();
			}
			catch (final RuntimeException ex)
			{
				logger.warn("Mid-batch invalidation-segment flush failed ({} segments retained for the AFTER_COMMIT retry)", segments.size(), ex);
			}
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
