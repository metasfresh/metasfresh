package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.compiere.util.Ini;

import de.metas.adempiere.service.IGlobalLockSystem;
import de.metas.adempiere.util.GlobalLock;

/**
 * Global Locking System.
 * Used to synchronize ADempiere processes across multiple instances.
 * @author tsa
 *
 */
public class GlobalLockSystem implements IGlobalLockSystem
{
	private static Map<GlobalLock, Semaphore> s_localLocks = Collections.synchronizedMap(new HashMap<GlobalLock, Semaphore>());

	@Override
	public GlobalLock createLock(String type, int id)
	{
		GlobalLock lock = new GlobalLock(type, id);
		GlobalLock l = findExitingLock(lock);
		return l == null ? lock : l;
	}

	private GlobalLock findExitingLock(GlobalLock lock)
	{
		for (Map.Entry<GlobalLock, Semaphore> e : s_localLocks.entrySet())
		{
			if (lock.equals(e.getKey()))
				return e.getKey();
		}
		return null;
	}

	@Override
	public boolean acquire(GlobalLock lock, long timeoutMillis) throws InterruptedException
	{
		// TODO: this functionality is supported only in server mode
		if (Ini.isSwingClient())
		{
			throw new IllegalStateException("GlobalLockSystem is supported only in server mode");
		}

		Semaphore s = s_localLocks.get(lock);
		if (s == null)
		{
			s = new Semaphore(1, true);
			s_localLocks.put(lock, s);
		}
		if (timeoutMillis == TIMEOUT_NEVER)
		{
			s.acquire();
			return true;
		}
		else
		{
			return s.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public void release(GlobalLock lock)
	{
		if (lock == null)
			return ;
		Semaphore s = s_localLocks.get(lock);
		if (s == null)
			return ;
		s.release();
		if (!s.hasQueuedThreads())
		{
			s_localLocks.remove(lock);
		}
	}

	@Override
	public boolean isLocked(GlobalLock lock)
	{
		Semaphore s = s_localLocks.get(lock);
		return s != null && s.availablePermits() == 0;
	}

	public Set<GlobalLock> getLocalLocks()
	{
		return s_localLocks.keySet();
	}
}
