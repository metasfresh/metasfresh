package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.storage.IStorageSegment;

public class StorageSegmentChangedExecutor
{
	private static final String TRX_PROPERTYNAME = StorageSegmentChangedExecutor.class.getName();

	public static final StorageSegmentChangedExecutor getCreateThreadInheritedOrNull()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			return null;
		}

		final StorageSegmentChangedExecutor collector = getCreate(trx);
		return collector;

	}

	private static final StorageSegmentChangedExecutor getCreate(final ITrx trx)
	{
		Check.assumeNotNull(trx, "trx not null");
		StorageSegmentChangedExecutor collector = trx.getProperty(TRX_PROPERTYNAME);
		if (collector == null)
		{
			collector = new StorageSegmentChangedExecutor();
			trx.setProperty(TRX_PROPERTYNAME, collector);

			// register our listener: we will actually fire the storage segment changed when the transaction is commited
			// Listens the {@link ITrx} and on commit actually fires the segment changed event
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
					.registerHandlingMethod(innerTrx -> {
						final StorageSegmentChangedExecutor innerCollector = innerTrx.getProperty(TRX_PROPERTYNAME);
						if (innerCollector == null || innerCollector.isEmpty())
						{
							// nothing to do
							return;
						}

						final StorageListeners listeners = innerCollector.getListeners();
						if (listeners == null)
						{
							return;
						}

						final List<IStorageSegment> changedSegments = innerCollector.getSegmentsAndClear();
						listeners.fireStorageSegmentsChanged(changedSegments);
					});
		}
		return collector;
	}

	private final List<IStorageSegment> segments = new ArrayList<>();
	private StorageListeners listeners;

	private StorageSegmentChangedExecutor()
	{
		super();
	}

	public void setListeners(StorageListeners storageListeners)
	{
		this.listeners = storageListeners;
	}

	public StorageListeners getListeners()
	{
		return listeners;
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

	public List<IStorageSegment> getSegmentsAndClear()
	{
		final List<IStorageSegment> segmentsToReturn = new ArrayList<>(segments);
		segments.clear();
		return segmentsToReturn;
	}

	public boolean isEmpty()
	{
		return segments.isEmpty();
	}
}
