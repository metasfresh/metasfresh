package de.metas.inoutcandidate.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;

import de.metas.storage.IStorageSegment;
import de.metas.util.Services;
import lombok.NonNull;
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
final class ShipmentScheduleSegmentChangedProcessor
{
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
			processor = new ShipmentScheduleSegmentChangedProcessor(shipmentScheduleInvalidator);
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

	private final List<IStorageSegment> segments = new ArrayList<>();
	private final ShipmentScheduleInvalidateBL shipmentScheduleInvalidator;

	private ShipmentScheduleSegmentChangedProcessor(@NonNull final ShipmentScheduleInvalidateBL shipmentScheduleInvalidator)
	{
		this.shipmentScheduleInvalidator = shipmentScheduleInvalidator;
	}

	private void process()
	{
		if (segments.isEmpty())
		{
			return;
		}

		final List<IStorageSegment> segmentsCopy = new ArrayList<>(segments);
		segments.clear();

		shipmentScheduleInvalidator.invalidateStorageSegments(segmentsCopy);
	}

	public void addSegment(final IStorageSegment segment)
	{
		if (segment == null)
		{
			return;
		}

		this.segments.add(segment);
	}

	public void addSegments(final Collection<IStorageSegment> segments)
	{
		if (segments == null || segments.isEmpty())
		{
			return;
		}

		this.segments.addAll(segments);
	}
}
