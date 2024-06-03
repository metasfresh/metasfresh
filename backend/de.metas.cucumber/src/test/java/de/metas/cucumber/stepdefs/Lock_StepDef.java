/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableList;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockException;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import static org.assertj.core.api.Assertions.*;

public class Lock_StepDef
{
	private final ILockManager lockManager = Services.get(ILockManager.class);

	private LockAcquireResult lastLockAcquireResult;

	@And("a lock on the record with AD_Table {string} and Record_ID {int} is requested for a lock owner with prefix {string}")
	public void aLockOnTheRecordWithAD_TableAD_PInstanceAndRecord_IDIsRequested(
			@NonNull final String tableName,
			final int recordId,
			@NonNull final String lockOwnerPrefix)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(tableName, recordId);
		try
		{
			final ILock lock = lockManager.lock()
					.setOwner(LockOwner.newOwner(lockOwnerPrefix))
					.setFailIfAlreadyLocked(true)
					.addRecordByModel(tableRecordReference)
					.acquire();
			lastLockAcquireResult = LockAcquireResult.success(lock.getOwner());
		}
		catch (final Throwable t)
		{
			lastLockAcquireResult = LockAcquireResult.fail(t);
		}
	}

	@And("the lock request was successful and the lock owner has the prefix {string}")
	public void theLockRequestWasSuccessful(@NonNull final String lockOwnerPrefix)
	{
		assertThat(lastLockAcquireResult.isSuccess()).isTrue();
		assertThat(lastLockAcquireResult.getOwner().getOwnerName()).startsWith(lockOwnerPrefix);
	}

	@Then("the lock request failed")
	public void theLockRequestFailed()
	{
		assertThat(lastLockAcquireResult.isSuccess()).isFalse();
	}

	@Then("a {string} LockException was thrown that contains the previously acquired lock on the record with AD_Table {string} and Record_ID {int}" 
			+ " and a lock owner with prefix {string}")
	public void aLockFailedExceptionWasThrownWhichContainsThePreviouslyAcquiredLockOnTheRecordWithAD_TableAD_PInstanceAndRecord_ID(
			@NonNull final String exceptionSimpleClassName,
			@NonNull final String tableName,
			final int recordId,
			@NonNull final String lockOwnerPrefix)
	{
		final Throwable lastThrowable = lastLockAcquireResult.getThrowable();
		assertThat(lastThrowable).isNotNull();
		assertThat(lastThrowable.getClass().getSimpleName()).isEqualTo(exceptionSimpleClassName);

		final TableRecordReference expectedLockedRecord = TableRecordReference.of(tableName, recordId);
		final ImmutableList<ExistingLockInfo> existingLocks = ((LockException)lastThrowable).getExistingLocks();

		assertThat(existingLocks).anyMatch(l -> l.getLockedRecord().equals(expectedLockedRecord) && l.getOwnerName().startsWith(lockOwnerPrefix));
	}

	@Value
	private static class LockAcquireResult
	{
		boolean success;
		Throwable throwable;
		LockOwner owner;

		public static LockAcquireResult success(@NonNull final LockOwner owner)
		{
			return new LockAcquireResult(true, null, owner);
		}

		public static LockAcquireResult fail(@NonNull final Throwable throwable)
		{
			return new LockAcquireResult(false, throwable, null);
		}
	}
}
