package de.metas.procurement.base.event.impl;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.lang.IAutoCloseable;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;

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

class PMMQtyReportEventSource implements Iterator<I_PMM_QtyReport_Event>, IAutoCloseable
{
	public static final PMMQtyReportEventSource newInstance(final Properties ctx)
	{
		return new PMMQtyReportEventSource(ctx);
	}

	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	// Params
	private final Properties ctx;

	// State
	private final AtomicBoolean _closed = new AtomicBoolean(false);
	private ILock _lock;
	private Iterator<I_PMM_QtyReport_Event> _iterator;

	private PMMQtyReportEventSource(final Properties ctx)
	{
		super();
		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;
	}

	@Override
	public I_PMM_QtyReport_Event next()
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

	private final void assertNotClosed()
	{
		Check.assume(!_closed.get(), "Source is not already closed");
	}

	private Iterator<I_PMM_QtyReport_Event> getCreateIterator()
	{
		assertNotClosed();
		if (_iterator == null)
		{
			final ILock lock = getOrAcquireLock();
			final Object contextProvider = PlainContextAware.createUsingThreadInheritedTransaction(ctx);
			_iterator = queryBL.createQueryBuilder(I_PMM_QtyReport_Event.class, contextProvider)
					.filter(lockManager.getLockedByFilter(I_PMM_QtyReport_Event.class, lock))
					//
					.orderBy()
					.addColumn(I_PMM_QtyReport_Event.COLUMNNAME_PMM_QtyReport_Event_ID)
					.endOrderBy()
					//
					.create()
					.iterate(I_PMM_QtyReport_Event.class);
		}
		return _iterator;
	}

	private final ILock getOrAcquireLock()
	{
		assertNotClosed();
		if (_lock == null)
		{
			final IQueryFilter<I_PMM_QtyReport_Event> filters = queryBL.createCompositeQueryFilter(I_PMM_QtyReport_Event.class)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient(ctx)
					.addEqualsFilter(I_PMM_QtyReport_Event.COLUMNNAME_Processed, false)
					.addFilter(lockManager.getNotLockedFilter(I_PMM_QtyReport_Event.class));

			final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName());
			_lock = lockManager.lock()
					.setOwner(lockOwner)
					.setAutoCleanup(true)
					.setFailIfNothingLocked(false)
					.setSetRecordsByFilter(I_PMM_QtyReport_Event.class, filters)
					.acquire();
		}

		return _lock;
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
