package de.metas.async;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.processor.impl.StaticMockedWorkpackageProcessor;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

@ExtendWith(AdempiereTestWatcher.class)
public class QueueProcessorTestBase
{
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		LogManager.setLevel(Level.INFO);
	}

	protected final Logger logger = LogManager.getLogger(getClass());
	protected Helper helper;
	protected Properties ctx;

	protected PlainLockManager lockManager;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();
		NOPWorkpackageLogsRepository.registerToSpringContext();

		StaticMockedWorkpackageProcessor.reset();

		helper = new Helper();
		ctx = helper.getCtx();

		lockManager = (PlainLockManager)Services.get(ILockManager.class);
		// queueDAO = Services.get(IQueueDAO.class);
		// queueBL = Services.get(IQueueBL.class);

		beforeTestCustomized();
	}

	protected void beforeTestCustomized()
	{
		// nothing at this level
	}

	@AfterEach
	public void afterTest()
	{
		afterTestCustomized();

		// POJOLookupMap.get().dumpStatus();

		// Dump Locked objects
		final PlainLockDatabase lockDatabase = lockManager.getLockDatabase();
		final List<Object> lockedObjects = lockDatabase.getLockedObjects();
		if (!lockedObjects.isEmpty())
		{
			System.out.println("\nLocked objects:");
			for (final Object o : lockedObjects)
			{
				System.out.println(o);
			}
		}

		StaticMockedWorkpackageProcessor.reset();
	}

	protected void afterTestCustomized()
	{
		// nothing at this level
	}

	/**
	 * Adding this dummy method to avoid test failure when our ivy-ant build tries to execute this test
	 */
	@Test
	public void testDummy()
	{
		// nothing to do
	}
}
