package de.metas.process;

import org.adempiere.ad.trx.api.ITrx;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ProcessClassInfoTests
{
	public static class ProcessCallImpl implements ProcessCall
	{
		@Override
		public void startProcess(final ProcessInfo pi, final ITrx trx)
		{
		}
	}

	@Before
	@After
	public void resetCache()
	{
		ProcessClassInfo.resetCache();
	}

	@Test
	public void test_ProcessCallImpl()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessCallImpl.class);
		Assert.assertNotSame("A new instance shall be created, and not the default one", ProcessClassInfo.NULL, processClassInfo);
		Assert.assertEquals("RunPrepareOutOfTransaction: " + processClassInfo, false, processClassInfo.isRunPrepareOutOfTransaction());
		Assert.assertEquals("RunDoItOutOfTransaction: " + processClassInfo, false, processClassInfo.isRunDoItOutOfTransaction());
	}

	@Test
	public void test_SvrProcess()
	{
		// just to notify that we are not going to test the SvrProcess here...
		Assume.assumeTrue("Other SvrProcess tests are already tested by " + SvrProcessTests.class, false);
	}

}
