package de.metas.async.processor.impl;

/*
 * #%L
 * de.metas.async
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


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import de.metas.common.util.time.SystemTime;
import org.compiere.util.Util;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.collections.MultiValueMap;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorListener;
import de.metas.async.processor.NullQueueProcessorListener;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Default implementation for {@link IQueueProcessorListener}.
 * 
 * NOTE: this implementation is thread-safe
 * 
 * @author tsa
 * 
 */
public class DefaultQueueProcessorEventDispatcher implements IQueueProcessorEventDispatcher
{
	private static final transient Logger logger = LogManager.getLogger(DefaultQueueProcessorEventDispatcher.class);

	private static class ListenerEntry
	{
		private final IQueueProcessorListener listener;
		private final int workpackageId;
		private final long createdTS;

		public ListenerEntry(final IQueueProcessorListener listener, final int workpackageId)
		{
			this.listener = listener;
			this.workpackageId = workpackageId;
			this.createdTS = SystemTime.millis();
		}

		@Override
		public String toString()
		{
			return "ListenerEntry [listener=" + listener + ", workpackageId=" + workpackageId + ", createdTS=" + createdTS + "]";
		}

		public IQueueProcessorListener getListener()
		{
			return listener;
		}
	};

	private final MultiValueMap<Integer, ListenerEntry> _listeners = new MultiValueMap<Integer, ListenerEntry>();
	private final transient ReentrantLock listenersLock = new ReentrantLock();

	protected List<ListenerEntry> getAndRemoveListenerEntries(final int workpackageId)
	{
		listenersLock.lock();

		try
		{
			final List<ListenerEntry> entries = _listeners.remove(workpackageId);
			return entries;
		}
		finally
		{
			listenersLock.unlock();
		}
	}

	protected void addListenerEntry(final int workpackageId, final ListenerEntry entry)
	{
		listenersLock.lock();
		try
		{
			_listeners.add(workpackageId, entry);
		}
		finally
		{
			listenersLock.unlock();
		}
	}

	protected boolean removeListenerEntry(int workpackageId)
	{
		List<ListenerEntry> entries = null;

		listenersLock.lock();
		try
		{
			entries = _listeners.remove(workpackageId);
		}
		finally
		{
			listenersLock.unlock();
		}

		return entries != null && !entries.isEmpty();
	}

	protected boolean removeListenerEntry(int workpackageId, IQueueProcessorListener callback)
	{
		listenersLock.lock();
		try
		{
			final List<ListenerEntry> entries = _listeners.get(workpackageId);
			if (entries == null)
			{
				return false;
			}

			boolean removed = false;
			for (final Iterator<ListenerEntry> it = entries.iterator(); it.hasNext();)
			{
				final ListenerEntry entry = it.next();
				if (Util.same(entry.getListener(), callback))
				{
					it.remove();
					removed = true;
				}
			}

			if (entries.isEmpty())
			{
				_listeners.remove(workpackageId);
			}

			return removed;
		}
		finally
		{
			listenersLock.unlock();
		}
	}

	@Override
	public void fireWorkpackageProcessed(final I_C_Queue_WorkPackage workpackage, final IWorkpackageProcessor workpackageProcessor)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();

		final List<ListenerEntry> entries = getAndRemoveListenerEntries(workpackageId);
		if (entries == null)
		{
			return;
		}

		for (final ListenerEntry entry : entries)
		{
			final IQueueProcessorListener listener = entry.getListener();

			try
			{
				listener.onWorkpackageProcessed(workpackage, workpackageProcessor);
			}
			catch (Exception e)
			{
				logger.error("Error while executing " + listener + " for " + workpackage + " [SKIP]", e);
				// NOTE: we are not throwing the error because there is not much to handle it, everything was consumed before
			}
		}
	}

	@Override
	public void registerListener(final IQueueProcessorListener listener, final int workpackageId)
	{
		Check.assumeNotNull(listener, "listener not null");
		Check.assumeNotNull(workpackageId > 0, "workpackageId > 0");

		// If it's null then don't register it
		if (listener == NullQueueProcessorListener.instance)
		{
			return;
		}

		final ListenerEntry entry = new ListenerEntry(listener, workpackageId);
		addListenerEntry(workpackageId, entry);
	}

	@Override
	public boolean unregisterListener(final IQueueProcessorListener callback, final int workpackageId)
	{
		// If it's null then don't unregister it because it was never registered
		if (callback == NullQueueProcessorListener.instance)
		{
			return false;
		}

		return removeListenerEntry(workpackageId, callback);
	}

	@Override
	public boolean unregisterListeners(int workpackageId)
	{
		return removeListenerEntry(workpackageId);
	}
}
