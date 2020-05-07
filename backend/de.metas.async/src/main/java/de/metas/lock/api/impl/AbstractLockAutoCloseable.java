package de.metas.lock.api.impl;

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


import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;

/**
 * {@link ILockAutoCloseable} base implementation which addresses the common concerns.
 * 
 * Developers are adviced to extend this class instead of implementing {@link ILockAutoCloseable}.
 * 
 * @author tsa
 *
 */
/* package */abstract class AbstractLockAutoCloseable implements ILockAutoCloseable
{
	private final ILock lock;

	public AbstractLockAutoCloseable(final ILock lock)
	{
		super();
		Check.assumeNotNull(lock, "lock not null");
		this.lock = lock;
	}

	@Override
	public final String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final ILock getLock()
	{
		return lock;
	}

	@Override
	public final void close()
	{
		// If lock was already closed, there is nothing to do
		if (lock.isClosed())
		{
			return;
		}

		closeImpl();

	}

	/** Actual lock closing logic */
	protected abstract void closeImpl();

	protected final void closeNow()
	{
		lock.close();
	}
}
