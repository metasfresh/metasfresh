package de.metas.lock.process;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessSingleInstanceLockService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Process;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * {@link IProcessSingleInstanceLockService} implemented as an exclusive {@link ILock} on the process's
 * own {@code AD_Process} record. Row-based (pool-agnostic), shared across app + webapi (same DB), and
 * crash-safe ({@code autoCleanup} releases the lock if a run dies).
 */
@Component
public class ProcessSingleInstanceLockService implements IProcessSingleInstanceLockService
{
	private final ILockManager lockManager = Services.get(ILockManager.class);

	@Override
	public Optional<IAutoCloseable> acquireFor(@NonNull final AdProcessId adProcessId)
	{
		final I_AD_Process processRecord = Services.get(IADProcessDAO.class).getById(adProcessId);
		try
		{
			final ILock lock = lockManager.lock()
					.setOwner(LockOwner.newOwner("ProcessSingleInstance-" + adProcessId.getRepoId()))
					.setFailIfAlreadyLocked(true) // a concurrent run must fail to acquire, not queue
					.setAutoCleanup(true)         // release the lock on crash so it can't block forever
					.setRecordByModel(processRecord)
					.acquire();
			final IAutoCloseable release = lock::close;
			return Optional.of(release);
		}
		catch (final LockFailedException ex)
		{
			// LockFailedException covers BOTH a genuine concurrent-run collision AND unexpected DB errors.
			// Treat only an actual existing lock as "another instance running"; re-throw real failures so
			// they surface instead of being silently swallowed as a skip.
			if (lockManager.isLocked(I_AD_Process.class, adProcessId.getRepoId(), LockOwner.ANY))
			{
				return Optional.empty();
			}
			throw ex;
		}
	}
}
