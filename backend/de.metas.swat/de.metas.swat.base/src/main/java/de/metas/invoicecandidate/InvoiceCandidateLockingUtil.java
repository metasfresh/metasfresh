package de.metas.invoicecandidate;

import static de.metas.util.Check.assumeNotEmpty;

import java.util.Collection;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InvoiceCandidateLockingUtil
{

	private static final String LOCK_OWNER_PREFIX = "ICEnqueuer";

	/** Lock all invoice candidates for selection and return an auto-closable lock. */
	public static final ILock lockInvoiceCandidatesForSelection(@NonNull final PInstanceId pinstanceId)
	{
		final ILockManager lockManager = Services.get(ILockManager.class);

		final LockOwner lockOwner = LockOwner.newOwner(LOCK_OWNER_PREFIX, "AD_PInstance_ID=" + pinstanceId.getRepoId());

		return lockManager.lock()
				.setOwner(lockOwner)
				// allow these locks to be cleaned-up on server starts.
				// NOTE: when we will add the ICs to workpackages we will move the ICs to another owner and we will also set AutoCleanup=false
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_C_Invoice_Candidate.class, pinstanceId)
				.acquire();
	}

	public static final ILock lockInvoiceCandidates(
			@NonNull final Collection<I_C_Invoice_Candidate> invoiceCandidateRecords,
			@NonNull final String uniqueLockOwnerSuffix)
	{
		final ILockManager lockManager = Services.get(ILockManager.class);

		final LockOwner lockOwner = LockOwner.newOwner(LOCK_OWNER_PREFIX, assumeNotEmpty(uniqueLockOwnerSuffix, "uniqueLockOwnerSuffix"));

		return lockManager.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.addRecordsByModel(invoiceCandidateRecords)
				.acquire();
	}
}
