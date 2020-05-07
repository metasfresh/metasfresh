package org.adempiere.ad.trx.processor.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.api.impl.MockedTrx;
import org.adempiere.ad.trx.api.impl.MockedTrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Low level (mainly about transaction handling) tests for  
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TrxItemChunkProcessorExecutor_LowLevel_Test
{
	private ITrxItemProcessorExecutorService trxItemProcessorService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		trxItemProcessorService = Services.get(ITrxItemProcessorExecutorService.class);

		PlainTrxManager.get().setDebugTrxLog(true);
		LogManager.setLevel(Level.INFO); // we want to see our transaction logs
	}

	/**
	 * Case: the processor is doing nothing (i.e. not changing something in current transaction)
	 * Expectation: transaction shall not fail because it wasn't actually created (on database level).
	 */
	@Test
	public void test_doNothingProcessor()
	{
		trxItemProcessorService.<I_Test, Void> createExecutor()
				.setProcessor(candidate -> {
					// do nothing
				})
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(generateTestItems(10));
	}

	/**
	 * Case: transaction fails on commit. Also the rollback fails.
	 * Expectation: the commit exception shall be propagated.
	 */
	@Test
	public void test_failOnCommit_failOnRollback()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();
		Services.registerService(ITrxManager.class, trxManager);

		final Error commitException = new Error("test commit fail");

		try
		{
			trxItemProcessorService.<I_Test, Void> createExecutor()
					.setProcessor(candidate -> {
						final MockedTrx trx = (MockedTrx)trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail);
						trx.setOnCommitException(commitException);
						trx.setOnRollbackException(new Error("test rollback fail"));
					})
					.setExceptionHandler(FailTrxItemExceptionHandler.instance)
					.process(generateTestItems(1));

			Assert.fail("An exception was expected");
		}
		catch (Throwable ex)
		{
			Assert.assertSame(commitException, ex.getCause());
		}

	}

	private List<I_Test> generateTestItems(final int count)
	{
		final List<I_Test> records = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
			record.setT_Integer(i);
			InterfaceWrapperHelper.save(record);
			records.add(record);
		}
		return records;
	}
}
