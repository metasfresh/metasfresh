/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.async.api;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.lock.api.ILockManager;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.slf4j.Logger;

@UtilityClass
public class WorkPackageLockHelper
{
	private static final Logger logger = LogManager.getLogger(WorkPackageLockHelper.class);

	/**
	 * Releases a workpackage that was claimed for processing but will not be processed now.
	 * <p>
	 * Clears {@code C_Queue_WorkPackage.LockedAt}, because the poller only selects workpackages with {@code LockedAt IS NULL}
	 * and on this path no {@code WorkpackageProcessorTask} runs that would clear it. Then releases the T_Lock.
	 */
	public static boolean unlockNoFail(final I_C_Queue_WorkPackage workPackage)
	{
		final boolean lockedAtCleared = clearLockedAtNoFail(workPackage);
		try
		{
			unlock(workPackage);
			return lockedAtCleared;
		}
		catch (final Exception e)
		{
			logger.warn("Got exception while unlocking " + workPackage, e);
			return false;
		}
	}

	private static boolean clearLockedAtNoFail(final I_C_Queue_WorkPackage workPackage)
	{
		try
		{
			// dev-note: update only this column directly, out of trx (like the planner's claim), so we never write a stale model over newer values
			Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_C_Queue_WorkPackage.class)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, workPackage.getC_Queue_WorkPackage_ID())
					.create()
					.updateDirectly()
					.addSetColumnValue(I_C_Queue_WorkPackage.COLUMNNAME_LockedAt, null)
					.execute();
			return true;
		}
		catch (final Exception e)
		{
			logger.warn("Got exception while clearing LockedAt of " + workPackage, e);
			return false;
		}
	}

	private static void unlock(final I_C_Queue_WorkPackage workPackage)
	{
		try
		{
			final boolean success = Services.get(ILockManager.class).unlock(workPackage);
			if (!success)
			{
				throw new UnlockFailedException("Cannot unlock");
			}
		}
		catch (final Exception e)
		{
			throw UnlockFailedException.wrapIfNeeded(e)
					.setParameter("Workpackage", workPackage);
		}
	}
}
