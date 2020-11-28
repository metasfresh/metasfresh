package de.metas.handlingunits.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.lock.api.LockOwner;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.util.Services;

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
 * Tests HU locking/unlocking mechanism.
 *
 * Following BLs are tested:
 * <ul>
 * <li>{@link IHULockBL}
 * <Li>{@link IHUQueryBuilder#onlyLocked()}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, HUReservationRepository.class })
public class HULockBL_IntegrationTest
{
	private IHULockBL huLockBL;

	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher()
	{
		@Override
		protected void onTestFailed(final String testName, final Throwable exception)
		{
			super.onTestFailed(testName, exception);

			PlainLockManager.get().dump();
		};
	};

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		huLockBL = Services.get(IHULockBL.class);
	}

	@Test
	public void test_lock_unlock()
	{
		final LockOwner lockOwner = LockOwner.forOwnerName("test");

		final I_M_HU hu = createHU();
		assertNotLocked(hu);

		huLockBL.lock(hu, lockOwner);
		assertLocked(hu);

		huLockBL.unlock(hu, lockOwner);
		assertNotLocked(hu);
	}

	@Test
	public void test_lock10_unlock10()
	{
		//
		// Create 10 lock owners
		final List<LockOwner> lockOwners = new ArrayList<>();
		for (int i = 1; i <= 10; i++)
		{
			lockOwners.add(LockOwner.forOwnerName("owner-" + i));
		}
		final LockOwner lastLockOwner = lockOwners.get(lockOwners.size() - 1);

		//
		// Create the HU to test with
		final I_M_HU hu = createHU();
		Assert.assertEquals("Locked", false, huLockBL.isLocked(hu));

		//
		// Lock and test
		lockOwners.forEach(lockOwner -> {
			huLockBL.lock(hu, lockOwner);
			assertLockedBy(hu, lockOwner);
		});

		// Test again all locks
		lockOwners.forEach(lockOwner -> assertLockedBy(hu, lockOwner));

		// Unlock and test
		lockOwners.forEach(lockOwner -> {
			huLockBL.unlock(hu, lockOwner);
			assertNotLockedBy(hu, lockOwner);

			if (lockOwner != lastLockOwner)
			{
				assertLocked(hu);
			}
			else
			{
				assertNotLocked(hu);
			}
		});

		// Test again all locks
		lockOwners.forEach(lockOwner -> assertNotLockedBy(hu, lockOwner));
		assertNotLocked(hu);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_unlock_any()
	{
		final LockOwner lockOwner = LockOwner.forOwnerName("test");
		final I_M_HU hu = createHU();
		assertNotLocked(hu);

		huLockBL.lock(hu, lockOwner);
		assertLocked(hu);

		huLockBL.unlock(hu, LockOwner.ANY);
	}

	private void assertLocked(final I_M_HU hu)
	{
		Assert.assertEquals("Locked", true, huLockBL.isLocked(hu));
		Assert.assertEquals("Locked", true, huLockBL.isLockedBy(hu, LockOwner.ANY)); // shall work the same as previous one

		final List<I_M_HU> result = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.onlyLocked()
				.addOnlyHUIds(ImmutableSet.of(hu.getM_HU_ID()))
				.list();
		Assert.assertEquals("Given HU expected", ImmutableList.of(hu), result);

	}

	private void assertLockedBy(final I_M_HU hu, final LockOwner lockOwner)
	{
		assertLocked(hu);

		Assert.assertEquals("Locked by " + lockOwner, true, huLockBL.isLockedBy(hu, lockOwner));
	}

	private void assertNotLocked(final I_M_HU hu)
	{
		Assert.assertEquals("Locked", false, huLockBL.isLocked(hu));
		Assert.assertEquals("Locked", false, huLockBL.isLockedBy(hu, LockOwner.ANY)); // shall work the same as previous one

		final List<I_M_HU> result = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.onlyLocked()
				.addOnlyHUIds(ImmutableSet.of(hu.getM_HU_ID()))
				.list();
		Assert.assertEquals("No HUs expected", ImmutableList.of(), result);
	}

	private void assertNotLockedBy(final I_M_HU hu, final LockOwner lockOwner)
	{
		Assert.assertEquals("Locked", false, huLockBL.isLockedBy(hu, lockOwner));
	}

	private I_M_HU createHU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		hu.setIsActive(true);
		InterfaceWrapperHelper.save(hu);
		// Services.get(IHandlingUnitsDAO.class).saveHU(hu);
		return hu;
	}
}
