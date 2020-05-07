package de.metas.handlingunits.impl;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Ini;

import com.google.common.base.Preconditions;

import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockCommand.AllowAdditionalLocks;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HULockBL implements IHULockBL
{
	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1861
	 */
	private static final String SYS_CONFIG_LOCK_VIA_VIRTUAL_COLUMN = "de.metas.handlingunits.HULockBL.UseVirtualColumn";

	@Override
	public boolean isLocked(final I_M_HU hu)
	{
		if (isUseVirtualColumn())
		{
			return hu.isLocked(); // gh #1861 return the value of this virtual column
		}
		return Services.get(ILockManager.class).isLocked(hu);
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1861
	 */
	private boolean isUseVirtualColumn()
	{
		final String value = Services.get(ISysConfigBL.class).getValue(SYS_CONFIG_LOCK_VIA_VIRTUAL_COLUMN, "N");
		if ("N".equalsIgnoreCase(value))
		{
			return false;
		}
		else if ("A".equalsIgnoreCase(value))
		{
			return true;
		}
		else if ("C".equalsIgnoreCase(value))
		{
			return Ini.isClient();
		}
		return false; // we don't know what to do with the value; play it safe and return false.
	}

	@Override
	public boolean isLockedBy(final I_M_HU hu, final LockOwner lockOwner)
	{
		return Services.get(ILockManager.class).isLocked(I_M_HU.class, hu.getM_HU_ID(), lockOwner);
	}

	@Override
	public void lock(final I_M_HU hu, final LockOwner lockOwner)
	{
		lockPrepare(hu, lockOwner)
				.acquire();

		if (isUseVirtualColumn())
		{
			InterfaceWrapperHelper.refresh(hu);
		}
	}

	@Override
	public void lockAfterCommit(final I_M_HU hu, final LockOwner lockOwner)
	{
		lockPrepare(hu, lockOwner)
				.acquireAfterTrxCommit(ITrx.TRXNAME_ThreadInherited);
	}

	private ILockCommand lockPrepare(final I_M_HU hu, final LockOwner lockOwner)
	{
		Preconditions.checkNotNull(hu, "hu is null");
		Preconditions.checkNotNull(lockOwner, "lockOwner is null");
		Preconditions.checkArgument(!lockOwner.isAnyOwner(), "{} not allowed", lockOwner);

		return Services.get(ILockManager.class)
				.lock()
				.setOwner(lockOwner)
				.setFailIfAlreadyLocked(false)
				.setAllowAdditionalLocks(AllowAdditionalLocks.FOR_DIFFERENT_OWNERS)
				.setAutoCleanup(false)
				.setRecordByModel(hu);
	}

	@Override
	public void lockAll(final Collection<I_M_HU> hus, final LockOwner lockOwner)
	{
		if (hus.isEmpty())
		{
			return;
		}

		Preconditions.checkNotNull(lockOwner, "lockOwner is null");
		Preconditions.checkArgument(!lockOwner.isAnyOwner(), "{} not allowed", lockOwner);

		Services.get(ILockManager.class)
				.lock()
				.setOwner(lockOwner)
				.setFailIfAlreadyLocked(false)
				.setAllowAdditionalLocks(AllowAdditionalLocks.FOR_DIFFERENT_OWNERS)
				.setAutoCleanup(false)
				.addRecordsByModel(hus)
				.acquire();

		if (isUseVirtualColumn())
		{
			InterfaceWrapperHelper.refreshAll(hus);
		}
	}

	@Override
	public void unlock(final I_M_HU hu, final LockOwner lockOwner)
	{
		Preconditions.checkNotNull(hu, "hu is null");
		Preconditions.checkNotNull(lockOwner, "lockOwner is null");
		Preconditions.checkArgument(!lockOwner.isAnyOwner(), "{} not allowed", lockOwner);

		final int huId = hu.getM_HU_ID();
		unlock0(huId, lockOwner);

		if (isUseVirtualColumn())
		{
			InterfaceWrapperHelper.refresh(hu);
		}
	}

	@Override
	public void unlockOnAfterCommit(
			final int huId,
			@NonNull final LockOwner lockOwner)
	{
		Preconditions.checkNotNull(huId > 0, "huId shall be > 0");
		Preconditions.checkArgument(!lockOwner.isAnyOwner(), "{} not allowed", lockOwner);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerWeakly(false) // register "hard", because that's how it was before
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(transaction -> unlock0(huId, lockOwner));

	}

	private final void unlock0(final int huId, final LockOwner lockOwner)
	{
		Services.get(ILockManager.class)
				.unlock()
				.setOwner(lockOwner)
				.setRecordByTableRecordId(I_M_HU.Table_Name, huId)
				.release();
	}

	@Override
	public void unlockAll(final Collection<I_M_HU> hus, final LockOwner lockOwner)
	{
		if (hus.isEmpty())
		{
			return;
		}

		Preconditions.checkNotNull(lockOwner, "lockOwner is null");
		Preconditions.checkArgument(!lockOwner.isAnyOwner(), "{} not allowed", lockOwner);

		Services.get(ILockManager.class)
				.unlock()
				.setOwner(lockOwner)
				.setRecordsByModels(hus)
				.release();

		if (isUseVirtualColumn())
		{
			InterfaceWrapperHelper.refresh(hus);
		}
	}

	@Override
	public IQueryFilter<I_M_HU> isLockedFilter()
	{
		return Services.get(ILockManager.class).getLockedByFilter(I_M_HU.class, LockOwner.ANY);
	}

	@Override
	public IQueryFilter<I_M_HU> isNotLockedFilter()
	{
		return Services.get(ILockManager.class).getNotLockedFilter(I_M_HU.class);
	}

}
