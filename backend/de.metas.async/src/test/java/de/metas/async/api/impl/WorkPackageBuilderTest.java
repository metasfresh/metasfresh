/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.async.api.impl;

import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class WorkPackageBuilderTest
{
	private WorkPackageQueue queue;

	private ILockManager lockManager;
	private ITrxManager trxManager;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		lockManager = Services.get(ILockManager.class);
		trxManager = Services.get(ITrxManager.class);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, OrgId.MAIN);

		final I_C_Queue_PackageProcessor packageProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_PackageProcessor.class);
		packageProcessor.setInternalName("packageProcessor");
		packageProcessor.setClassname("DummyClassName");
		InterfaceWrapperHelper.save(packageProcessor);
		final QueuePackageProcessorId packageProcessorId = QueuePackageProcessorId.ofRepoId(packageProcessor.getC_Queue_PackageProcessor_ID());

		final I_C_Queue_Processor queueProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor.class);
		queueProcessor.setName("queueProcessor");
		InterfaceWrapperHelper.save(queueProcessor);
		final QueueProcessorId queueProcessorId = QueueProcessorId.ofRepoId(queueProcessor.getC_Queue_Processor_ID());

		final I_C_Queue_Processor_Assign queueProcessorAssign = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor_Assign.class);
		queueProcessorAssign.setC_Queue_Processor_ID(queueProcessorId.getRepoId());
		queueProcessorAssign.setC_Queue_PackageProcessor_ID(packageProcessorId.getRepoId());
		InterfaceWrapperHelper.save(queueProcessorAssign);

		queue = WorkPackageQueue.createForEnqueuing(
				ctx,
				packageProcessorId,
				queueProcessorId,
				"internalName"
		);
	}

	I_Test newRecord()
	{
		final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	@Test
	void transfer_lock_enqueue()
	{
		final I_Test record = newRecord();

		final LockOwner mainLockOwner = LockOwner.forOwnerName("main");
		final ILock mainLock = lockManager.lock()
				.setOwner(mainLockOwner)
				.setFailIfAlreadyLocked(true)
				.setRecordByModel(record)
				.acquire();
		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), mainLockOwner)).isTrue();

		final LockOwner workpackageLockOwner = LockOwner.forOwnerName("workpackage");
		final ILockCommand workpackageLocker = mainLock
				.split()
				.setOwner(workpackageLockOwner)
				.setAutoCleanup(false);

		queue.newWorkPackage()
				.setUserInChargeId(UserId.METASFRESH) // to avoid loading the queue processor descriptor
				.setElementsLocker(workpackageLocker)
				.addElement(record)
				.buildAndEnqueue();

		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), mainLockOwner)).isFalse();
		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), workpackageLockOwner)).isTrue();
	}

	@Test
	void release_lock_trx_fail()
	{
		final I_Test record = newRecord();

		final LockOwner mainLockOwner = LockOwner.forOwnerName("main");
		final ILock mainLock = lockManager.lock()
				.setOwner(mainLockOwner)
				.setFailIfAlreadyLocked(true)
				.setRecordByModel(record)
				.acquire();
		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), mainLockOwner)).isTrue();

		final LockOwner workpackageLockOwner = LockOwner.forOwnerName("workpackage");

		try
		{
			trxManager.runInNewTrx(() -> {
				final ILockCommand workpackageLocker = mainLock
						.split()
						.setOwner(workpackageLockOwner)
						.setAutoCleanup(false);

				queue.newWorkPackage()
						.setUserInChargeId(UserId.METASFRESH) // to avoid loading the queue processor descriptor
						.setElementsLocker(workpackageLocker)
						.addElement(record)
						.buildAndEnqueue();

				assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), mainLockOwner)).isFalse();
				assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), workpackageLockOwner)).isTrue();

				throw new AdempiereException("Fail");
			});

			fail("Shall not get to this point because and exception shall be thrown and propagated");
		}
		catch (AdempiereException ex)
		{
			assertThat(ex.getLocalizedMessage()).isEqualTo("Fail");
		}

		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), mainLockOwner)).isFalse();
		assertThat(lockManager.isLocked(I_Test.class, record.getTest_ID(), workpackageLockOwner)).isFalse();
	}

}