package de.metas.procurement.base.event.impl;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IAutoCloseable;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;


/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class EventSource<ET> implements Iterator<ET>, IAutoCloseable
{
	private static final String COLUMNNAME_Processed = "Processed";

	// services
	protected final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	protected final transient ILockManager lockManager = Services.get(ILockManager.class);

	// Params
	private final Properties ctx;
	private final Class<ET> eventTypeClass;

	// State
	private final AtomicBoolean _closed = new AtomicBoolean(false);
	private ILock _lock;
	private Iterator<ET> _iterator;

	public EventSource(
			@NonNull final Properties ctx,
			@NonNull final Class<ET> eventTypeClass)
	{
		this.ctx = ctx;
		this.eventTypeClass = eventTypeClass;
	}

	@Override
	public ET next()
	{
		return getCreateIterator().next();
	}

	@Override
	public boolean hasNext()
	{
		return getCreateIterator().hasNext();
	}

	@Override
	public void remove()
	{
		getCreateIterator().remove();
	}

	private void assertNotClosed()
	{
		Check.assume(!_closed.get(), "Source is not already closed");
	}

	private Iterator<ET> getCreateIterator()
	{
		assertNotClosed();

		if (_iterator == null)
		{
			final ILock lock = getOrAcquireLock();
			final Object contextProvider = PlainContextAware.newWithThreadInheritedTrx(ctx);
			_iterator = queryBL.createQueryBuilder(eventTypeClass, contextProvider)
					.filter(lockManager.getLockedByFilter(eventTypeClass, lock))
					.addOnlyActiveRecordsFilter()
					//
					.orderBy()
					.addColumn(InterfaceWrapperHelper.getKeyColumnName(eventTypeClass))
					.endOrderBy()
					//
					.create()
					.iterate(eventTypeClass);
		}
		return _iterator;
	}

	private ILock getOrAcquireLock()
	{
		assertNotClosed();
		if (_lock == null)
		{
			final IQueryFilter<ET> filter = createFilter();

			final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName());
			_lock = lockManager.lock()
					.setOwner(lockOwner)
					.setAutoCleanup(true)
					.setFailIfNothingLocked(false)
					.setRecordsByFilter(eventTypeClass, filter)
					.acquire();
		}

		return _lock;
	}

	protected IQueryFilter<ET> createFilter()
	{
		return queryBL.createCompositeQueryFilter(eventTypeClass)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.addEqualsFilter(COLUMNNAME_Processed, false)
				.addFilter(lockManager.getNotLockedFilter(eventTypeClass));
	}

	@Override
	public void close()
	{
		// Mark this source as closed
		if (_closed.getAndSet(true))
		{
			// already closed
			return;
		}

		//
		// Unlock all
		if (_lock != null)
		{
			_lock.close();
			_lock = null;
		}

		//
		// Close iterator
		if (_iterator != null)
		{
			IteratorUtils.closeQuietly(_iterator);
			_iterator = null;
		}
	}
}
