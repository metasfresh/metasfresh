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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.service.ITaskExecutorService;

import de.metas.storage.IStorageListener;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.util.Check;
import de.metas.util.Services;

public class StorageListeners implements IStorageListeners
{
	private final CopyOnWriteArrayList<IStorageListener> listeners = new CopyOnWriteArrayList<>();

	@Override
	public void addStorageListener(final IStorageListener storageListener)
	{
		Check.assumeNotNull(storageListener, "storageListener not null");
		listeners.addIfAbsent(storageListener);
	}

	@Override
	public void notifyStorageSegmentChanged(final IStorageSegment storageSegment)
	{
		Check.assumeNotNull(storageSegment, "storageSegment not null");

		notifyStorageSegmentsChanged(Collections.singleton(storageSegment));
	}

	@Override
	public void notifyStorageSegmentsChanged(final Collection<IStorageSegment> storageSegments)
	{
		Check.assumeNotNull(storageSegments, "storageSegments not null");

		final StorageSegmentChangedExecutor collector = StorageSegmentChangedExecutor.getCreateThreadInheritedOrNull();
		if (collector == null)
		{
			fireStorageSegmentsChanged(storageSegments);
			return;
		}

		collector.setListeners(this);
		collector.addSegments(storageSegments);
	}

	protected final void fireStorageSegmentsChanged(final Collection<IStorageSegment> storageSegments)
	{
		if (storageSegments == null || storageSegments.isEmpty())
		{
			return;
		}

		if (listeners.isEmpty())
		{
			return;
		}

		Services.get(ITaskExecutorService.class).submit(new Runnable()
		{
			@Override
			public void run()
			{
				for (final IStorageListener listener : listeners)
				{
					listener.onStorageSegmentChanged(storageSegments);
				}
			}
		},
		this.getClass().getSimpleName());
	}
}
