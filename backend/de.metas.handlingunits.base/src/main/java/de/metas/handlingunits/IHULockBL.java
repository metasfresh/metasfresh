package de.metas.handlingunits;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.lock.api.LockOwner;

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

/**
 * Handling units locking service
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHULockBL extends ISingletonService
{
	/** @return true if given HU is locked by any {@link LockOwner} */
	boolean isLocked(I_M_HU hu);

	/**
	 * @param hu
	 * @param lockOwner lock owner; {@link LockOwner#ANY} is accepted
	 * @return true if given HU is locked by given owner
	 */
	boolean isLockedBy(I_M_HU hu, LockOwner lockOwner);

	/**
	 * Locks the HU using given lock owner.
	 * 
	 * If the HU was already locked by same owner, this method does nothing.
	 * 
	 * @param hu
	 * @param lockOwner
	 */
	void lock(I_M_HU hu, LockOwner lockOwner);

	void lockAfterCommit(I_M_HU hu, LockOwner lockOwner);

	void lockAll(Collection<I_M_HU> hus, LockOwner lockOwner);

	/**
	 * Unlock the HU from given lock owner.
	 * 
	 * If the HU is not locked by that owner, this method does nothing.
	 * 
	 * @param hu
	 * @param lockOwner
	 */
	void unlock(I_M_HU hu, LockOwner lockOwner);

	void unlockOnAfterCommit(final int huId, LockOwner lockOwner);

	void unlockAll(Collection<I_M_HU> hus, LockOwner lockOwner);

	/**
	 * @return filter which accepts only those HUs which are not locked for any owner
	 */
	IQueryFilter<I_M_HU> isLockedFilter();

	IQueryFilter<I_M_HU> isNotLockedFilter();
}
