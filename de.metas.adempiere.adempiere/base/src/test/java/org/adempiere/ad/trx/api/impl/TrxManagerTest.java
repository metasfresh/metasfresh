package org.adempiere.ad.trx.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.exceptions.IllegalTrxRunStateException;
import org.adempiere.ad.trx.exceptions.TrxNotFoundException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrxManagerTest
{
	/** Service under test */
	private MockedTrxManager trxManager;

	/** A custom {@link AdempiereException} extension which we are using to test exceptions propagation through transactions management module */
	public static final class MyCustomAdempiereException extends AdempiereException
	{
		private static final long serialVersionUID = 1L;

		public MyCustomAdempiereException(final String message)
		{
			super(message);
		}
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = new MockedTrxManager();
		Services.registerService(ITrxManager.class, trxManager);
	}

	private void assertInActiveTransactionList(final ITrx trx, final boolean expectedActive)
	{
		boolean actualActive = false;
		for (final ITrx t : trxManager.getActiveTransactions())
		{
			if (t == trx)
			{
				actualActive = true;
				break;
			}
		}

		Assert.assertEquals("Transaction " + trx
				+ (expectedActive ? " shall be " : " shall not be ")
				+ " in active transactions list", expectedActive, actualActive);
	}

	@Test
	public void test_Start_Commit() throws Exception
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		Assert.assertEquals("Trx not active", false, trx.isActive());

		trx.start();
		Assert.assertEquals("Trx active/started", true, trx.isActive());

		trx.commit(true);
		Assert.assertEquals("Trx not active", false, trx.isActive());

		assertInActiveTransactionList(trx, true); // still in active trx list; only close() will take it out
	}

	@Test
	public void test_Start_Rollback() throws Exception
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		Assert.assertEquals("Trx not active", false, trx.isActive());
		assertInActiveTransactionList(trx, true);

		trx.start();
		Assert.assertEquals("Trx active/started", true, trx.isActive());

		trx.rollback();
		Assert.assertEquals("Trx not active", false, trx.isActive());

		assertInActiveTransactionList(trx, true); // still in active trx list; only close() will take it out
	}

	@Test
	public void test_Start_Close()
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		Assert.assertEquals("Trx not active", false, trx.isActive());
		assertInActiveTransactionList(trx, true);

		trx.start();

		trx.close();
		assertInActiveTransactionList(trx, false);
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName=null, manageTrx=N/A
	 *
	 * Expectation: create a new trxName (and thus a new local trx) with prefix <code>"TrxRun"</code>
	 */
	@Test
	public void test_run_managedTrx_001()
	{
		final String trxName = null;
		final boolean manageTrx = false;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		Assert.assertTrue("Runnable was executed", runnable.isExecuted());

		final String localTrxNameEffective = runnable.getLastTrxNameEffective();
		final MockedTrx trx = (MockedTrx)trxManager.getRemovedTransactionByName(localTrxNameEffective);
		Assert.assertNotNull("Transaction was created and removed for trxName=" + localTrxNameEffective, trx);

		Assert.assertEquals("Commit was called for " + trx, true, trx.isCommitCalled());
		Assert.assertEquals("Close was called for " + trx, true, trx.isCloseCalled());

		Assert.assertTrue("No active savepoints", trx.getActiveSavepoints().isEmpty());
		Assert.assertTrue("No released savepoints", trx.getReleasedSavepoints().isEmpty());
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName!=null, manageTrx=true
	 *
	 * Expectation: create a new trxName (and thus a new local trx) with prefix being the given <code>trxName</code>
	 */
	@Test
	public void test_run_managedTrx_002()
	{
		final String trxName = "TestTrx002";
		final boolean manageTrx = true;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		Assert.assertTrue("Runnable was executed", runnable.isExecuted());

		final String localTrxNameEffective = runnable.getLastTrxNameEffective();
		Assert.assertTrue("Created trx '" + localTrxNameEffective + "' shall start with '" + trxName + "'", localTrxNameEffective.startsWith(trxName));

		final MockedTrx trx = (MockedTrx)trxManager.getRemovedTransactionByName(localTrxNameEffective);
		Assert.assertNotNull("Transaction was created and removed for trxName=" + localTrxNameEffective, trx);

		Assert.assertEquals("Commit was called for " + trx, true, trx.isCommitCalled());
		Assert.assertEquals("Close was called for " + trx, true, trx.isCloseCalled());

		Assert.assertTrue("No active savepoints", trx.getActiveSavepoints().isEmpty());
		Assert.assertTrue("No released savepoints", trx.getReleasedSavepoints().isEmpty());
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName!=null, manageTrx=false
	 *
	 * Expectation: use the trx with the the given trxName; create a savepoint and to roll back to in case of problems. don't commit in case of success.
	 */
	@Test
	public void test_run_managedTrx_003()
	{
		final String trxName = trxManager.createTrxName("TestTrx003", true); // createNew=true
		final boolean manageTrx = false;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		Assert.assertTrue("Runnable was executed", runnable.isExecuted());

		final String localTrxName = runnable.getLastTrxName();
		// NOTE: until we get rid of "TrxCallableWithTrxName" our Runnables will get the "effective" localTrxName instead of ThreadInherited.
		// Assert.assertEquals("Invalid trxName used", ITrx.TRXNAME_ThreadInherited, localTrxName);
		Assert.assertNotNull(TrxCallableWithTrxName.class); // non-sense, but we just want to have a reference here for future refactoring
		Assert.assertEquals("Invalid trxName used", trxName, localTrxName);

		final MockedTrx trx = (MockedTrx)trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		Assert.assertNotNull("Transaction was created but not removed for trxName=" + localTrxName, trx);

		Assert.assertEquals("Commit was NOT called for " + trx, false, trx.isCommitCalled());
		Assert.assertEquals("Close was NOT called for " + trx, false, trx.isCloseCalled());

		Assert.assertTrue("No active savepoints", trx.getActiveSavepoints().isEmpty());
		Assert.assertEquals("One released savepoint shall be available", 1, trx.getReleasedSavepoints().size());

		final ITrxSavepoint releasedSavepoint = trx.getReleasedSavepoints().get(0);
		Assert.assertNotNull("Released savepoint exists", releasedSavepoint);
	}

	@Test
	public void test_run_inheritedTrx()
	{
		final MockedTrxRunnable runnableInner = new MockedTrxRunnable(trxManager);
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);

		runnable.setInnerRunnable(new Runnable()
		{
			@Override
			public void run()
			{
				trxManager.run(ITrx.TRXNAME_ThreadInherited, runnableInner);
			}
		});

		trxManager.run(runnable);

		Assert.assertTrue("Runnable was executed", runnable.isExecuted());
		Assert.assertTrue("Inner Runnable was executed", runnableInner.isExecuted());

		final String localTrxName = runnable.getLastTrxName();
		Assert.assertFalse("Runnable trxName shall not be null: " + localTrxName, trxManager.isNull(localTrxName));

		Assert.assertEquals("Inner runnable's trxName shall be the same as runnable's trxName", localTrxName, runnableInner.getLastTrxName());

		Assert.assertNull("Transaction shall be removed: trxName=" + localTrxName,
				trxManager.get(localTrxName, OnTrxMissingPolicy.ReturnTrxNone));

		final MockedTrx trx = (MockedTrx)runnableInner.getLastTrx();
		Assert.assertNotNull("Last transaction shall not be null", trx);
		Assert.assertEquals("Commit was called for " + trx, true, trx.isCommitCalled());
		Assert.assertEquals("Close was called for " + trx, true, trx.isCloseCalled());

		Assert.assertEquals("No active savepoints",
				Collections.emptyList(),
				trx.getActiveSavepoints());

		Assert.assertEquals("One released savepoint shall be available (for inner runnable)",
				1, trx.getReleasedSavepoints().size());

		final ITrxSavepoint releasedSavepoint = trx.getReleasedSavepoints().get(0);
		Assert.assertNotNull("Released savepoint exists", releasedSavepoint);

		Assert.assertSame("Release point transaction shall be the same as inner transaction",
				trx, releasedSavepoint.getTrx());
	}

	@Test
	public void test_get_InheritedTrx_DonNotCreateNew()
	{
		final boolean createNew = false;

		final ITrx trx = trxManager.get(ITrx.TRXNAME_ThreadInherited, createNew);
		Assert.assertNull("When no actual transaction name was found then null shall be returned", trx);
	}

	@Test
	public void test_get_InheritedTrx_CreateNew()
	{
		final boolean createNew = true;
		final ITrx trx = trxManager.get(ITrx.TRXNAME_ThreadInherited, createNew);
		Assert.assertTrue("Not null transaction shall be returned: " + trx, !trxManager.isNull(trx));

		final String trxName = trx.getTrxName();
		Assert.assertTrue("Transaction name shall be valid: " + trxName, trxManager.isValidTrxName(trxName));
	}

	/**
	 * Makes sure that {@value ITrx#TRXNAME_ThreadInherited} is not considered null/no transaction.
	 */
	@Test
	public void test_isNull_IneritedTrx()
	{
		Assert.assertEquals("Inherited trxName shall not be considered null",
				false,   // expected
				trxManager.isNull(ITrx.TRXNAME_ThreadInherited) // actual
		);
	}

	private final void assertSameTrxName(final boolean expected, final String trxName1, final String trxName2)
	{
		Assert.assertEquals("Invalid isSameTrxName() result for trxName1=" + trxName1 + ", trxName2=" + trxName2,
				expected,
				trxManager.isSameTrxName(trxName1, trxName2));

		// Also test the other way around
		Assert.assertEquals("Invalid isSameTrxName() result for trxName2=" + trxName2 + ", trxName1=" + trxName1,
				expected,
				trxManager.isSameTrxName(trxName2, trxName1));
	}

	/**
	 * Tests {@link AbstractTrxManager#isSameTrxName(String, String)}.
	 */
	@Test
	public void test_isSameTrxName()
	{
		// Straightforward tests
		assertSameTrxName(true, "trxName1", "trxName1");
		assertSameTrxName(false, "trxName1", "trxName2");

		// Nulls test
		assertSameTrxName(true, null, null);
		assertSameTrxName(true, null, ITrx.TRXNAME_NoneNotNull);
		assertSameTrxName(true, ITrx.TRXNAME_NoneNotNull, ITrx.TRXNAME_NoneNotNull);

		// Thread Inherited: when not set
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_ThreadInherited);
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_None);
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_NoneNotNull);

		// Thread Inherited: set
		trxManager.setThreadInheritedTrxName("trxName1");
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, "trxName1");
		assertSameTrxName(false, ITrx.TRXNAME_ThreadInherited, "trxName2");
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_ThreadInherited);

		// Thread Inherited: set to NoneNotNull
		trxManager.setThreadInheritedTrxName(ITrx.TRXNAME_NoneNotNull);
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_ThreadInherited);
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_None);
		assertSameTrxName(true, ITrx.TRXNAME_ThreadInherited, ITrx.TRXNAME_NoneNotNull);
	}

	/**
	 * Case:
	 * <ul>
	 * <li>running an {@link TrxRunnable} on following configuration: NESTED transaction, OnSuccess=DONT_COMMIT, OnFail=DONT_ROLLBACK
	 * <li>the runnable throws a custom exception
	 * </ul>
	 *
	 * Expectations:
	 * <ul>
	 * <li>our custom exception is propagated
	 * <li>transaction was not rollback
	 * </ul>
	 */
	@Test
	public void test_run_OnFailDontRollback_ExceptionShallBePropagated()
	{
		//
		// Transaction configuration:
		final String trxName = trxManager.createTrxName("TestTrx", true);
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,   // we expect to run into a transaction at this point
				OnRunnableSuccess.DONT_COMMIT,
				// OnRunnableFail: don't rollback => no savepoint shall be created (avoid HUGE performance issues)
				OnRunnableFail.DONT_ROLLBACK);

		//
		// TrxRunnable which will throw our custom exception when executed
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		runnable.setInnerRunnable(new Runnable()
		{
			@Override
			public void run()
			{
				throw new MyCustomAdempiereException("FAIL");
			}
		});

		//
		// Execute and expect our exception to be propagated
		MyCustomAdempiereException exceptionActual = null;
		try
		{
			trxManager.run(trxName, trxRunConfig, runnable);
		}
		catch (MyCustomAdempiereException e)
		{
			exceptionActual = e;
		}

		Assert.assertNotNull("Exception shall be propagated", exceptionActual);

		final MockedTrx trx = (MockedTrx)trxManager.getTrx(trxName);
		Assert.assertNotNull("Transaction shall exist", trx);
		Assert.assertFalse("Transaction shall not be rollback: " + trx, trx.isRollbackCalled());
		Assert.assertFalse("Transaction shall not be commited: " + trx, trx.isCommitCalled());
		Assert.assertTrue("Transaction shall be active: " + trx, trx.isActive());
	}

	/**
	 * Case: we are running with {@link TrxPropagation#NESTED} but we are not providing any transaction.
	 *
	 * Expection: shall fail
	 */
	@Test(expected = IllegalTrxRunStateException.class)
	public void test_run_NestedTransaction_ButNoTransactionProvided()
	{
		// Transaction configuration:
		final String trxName = ITrx.TRXNAME_None; // NO transaction
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.DONT_COMMIT,   // we don't care for this test
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, trxRunConfig, runnable);
	}

	@Test(expected = IllegalTrxRunStateException.class)
	public void test_run_NestedTransaction_ThreadIneritedTrxProvided_ThreadInheritedTrxIsMissing()
	{
		// Transaction configuration:
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.DONT_COMMIT,   // we don't care for this test
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, trxRunConfig, runnable);
	}

	@Test
	public void test_run_NestedTransaction_ThreadIneritedTrxProvided_ThreadInheritedTrxExists()
	{
		// Transaction configuration:
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.DONT_COMMIT,   // we don't care for this test
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		// Make sure the thread inherited transaction exists
		final String existingThreadInheritedTrxName = trxManager.createTrxName("TestTrx", true);
		trxManager.setThreadInheritedTrxName(existingThreadInheritedTrxName);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, trxRunConfig, runnable);

		runnable.assertExecutedUsingTrxName(existingThreadInheritedTrxName);
		Assert.assertEquals("ThreadInheritedTrxName shall not be reset after runnable was executed", existingThreadInheritedTrxName, trxManager.getThreadInheritedTrxName());
	}

	/**
	 * Case:
	 * <ul>
	 * <li>Propagation=NESTED, OnSuccess=DONT_COMMIT, OnFail=DONT_ROLLBACK, trxName= {@link ITrx#TRXNAME_ThreadInherited}
	 * <li>the thread inerited transaction is set but it does not actually exists
	 * </ul>
	 *
	 * Expectations: an exception will be thrown
	 *
	 * Production code expectations (NOT tested here):
	 * <ul>
	 * <li>system will create the transaction using that provided trxName and will execute the runnable
	 * <li>system shall NOT close the created transaction because even if it created it the calling code was expecting to be there and running
	 * </ul>
	 */
	@Test(expected = IllegalTrxRunStateException.class)
	public void test_run_NestedTransaction_DontCommit_DontRollback_ThreadIneritedTrxProvided_ThreadInheritedTrxSetButDoesNotActuallyExist()
	{
		// Transaction configuration:
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.DONT_COMMIT,   // we don't care for this test
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		// Make sure the thread inherited transaction exists
		final String existingThreadInheritedTrxName = "TestTrxWhichDoesNotActuallyExist";
		trxManager.setThreadInheritedTrxName(existingThreadInheritedTrxName);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, trxRunConfig, runnable);
	}

	/**
	 * Corner (possible invalid) case: propagation=NESTED, onSuccess=COMMIT.
	 *
	 * Expectation: at least, in JUnit/Developer Mode we expect to fail.
	 */
	@Test(expected = IllegalTrxRunStateException.class)
	public void test_run_NestedTransaction_OnSuccessCommit()
	{
		final String trxName = trxManager.createTrxName("TestTrx", true);
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.COMMIT,
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, trxRunConfig, runnable);
	}

	@Test
	public void test_run_NewTransaction_FailOnRollback()
	{
		final String trxName = null;
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.REQUIRES_NEW,
				OnRunnableSuccess.COMMIT,
				OnRunnableFail.ROLLBACK);

		final MyCustomAdempiereException expectedException = new MyCustomAdempiereException("test - fail on run()");
		final MyCustomAdempiereException doFinallyException = new MyCustomAdempiereException("test - fail on doFinally()");

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		runnable.setInnerRunnable(new Runnable()
		{

			@Override
			public void run()
			{
				throw expectedException;
			}
		});
		runnable.setDoFinallyRunnable(new Runnable()
		{

			@Override
			public void run()
			{
				throw doFinallyException;
			}
		});

		Throwable actualException = null;
		try
		{
			trxManager.run(trxName, trxRunConfig, runnable);
		}
		catch (Throwable e)
		{
			actualException = e;
		}

		Assert.assertEquals("Proper shall be thrown", expectedException, actualException);
		Assert.assertArrayEquals("Exception thrown on doFinally() shall be suppressed", new Throwable[] { doFinallyException } // expected
		, actualException.getSuppressed()// actual
		);
	}

	@Test
	public void test_runOutOfTransaction()
	{
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);

		trxManager.runOutOfTransaction(runnable);

		runnable.assertExecutedUsingTrxName(ITrx.TRXNAME_None);
	}

	@Test
	public void test_runOutOfTransaction_Fail()
	{
		final MyCustomAdempiereException expectedException = new MyCustomAdempiereException("test - fail on run()");
		final MyCustomAdempiereException doFinallyException = new MyCustomAdempiereException("test - fail on doFinally()");

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		runnable.setInnerRunnable(new Runnable()
		{

			@Override
			public void run()
			{
				throw expectedException;
			}
		});
		runnable.setDoFinallyRunnable(new Runnable()
		{
			@Override
			public void run()
			{
				throw doFinallyException;
			}
		});

		Throwable actualException = null;
		try
		{
			trxManager.runOutOfTransaction(runnable);
		}
		catch (Throwable e)
		{
			actualException = e;
		}

		Assert.assertEquals("Proper shall be thrown", expectedException, actualException);
		Assert.assertArrayEquals("Exception thrown on doFinally() shall be suppressed", new Throwable[] { doFinallyException } // expected
		, actualException.getSuppressed()// actual
		);
	}

	@Test
	public void test_getTrx_NULL()
	{
		Assert.assertEquals(ITrx.TRXNAME_None, trxManager.getTrx(ITrx.TRXNAME_None));
		Assert.assertEquals(ITrx.TRXNAME_None, trxManager.getTrx(ITrx.TRXNAME_NoneNotNull));

		// Test for inherited transaction
		trxManager.assertThreadInheritedTrxNotExists();
		Assert.assertEquals(ITrx.TRXNAME_None, trxManager.getTrx(ITrx.TRXNAME_ThreadInherited));
	}

	@Test
	public void test_getTrx_ExistingTrxName()
	{
		// Create and register the transaction
		final String trxName = "TestTrx";
		final ITrx expectedTrx = trxManager.createTrxAndRegister(trxName, false);

		Assert.assertEquals(expectedTrx, trxManager.getTrx(trxName));

		// Test for inherited transaction
		trxManager.setThreadInheritedTrxName(trxName);
		Assert.assertEquals(expectedTrx, trxManager.getTrx(ITrx.TRXNAME_ThreadInherited));
	}

	@Test(expected = TrxNotFoundException.class)
	public void test_getTrx_NotExistingTrxName()
	{
		final String trxName = "MissingTrx";
		trxManager.getTrx(trxName); // expect exception
	}

	@Test(expected = TrxNotFoundException.class)
	public void test_getTrx_NotExistingTrxName_ViaThreadInherited()
	{
		final String trxName = "MissingTrx";
		trxManager.setThreadInheritedTrxName(trxName);

		trxManager.getTrx(ITrx.TRXNAME_ThreadInherited); // expect exception
	}

	@Test(expected = AdempiereException.class)
	public void test_assertThreadInheritedTrxExists_NoTrx()
	{
		trxManager.assertThreadInheritedTrxExists();
	}

	@Test
	public void test_assertThreadInheritedTrxExists_WithTrx()
	{
		final String threadInheritedTrxName = trxManager.createTrxName("TestTrx", true);
		trxManager.setThreadInheritedTrxName(threadInheritedTrxName);
		
		trxManager.assertThreadInheritedTrxExists();
	}

	@Test
	public void test_assertThreadInheritedTrxNotExists_NoTrx()
	{
		trxManager.assertThreadInheritedTrxNotExists();
	}

	@Test(expected = AdempiereException.class)
	public void test_assertThreadInheritedTrxNotExists_WithTrx()
	{
		final String threadInheritedTrxName = trxManager.createTrxName("TestTrx", true);
		trxManager.setThreadInheritedTrxName(threadInheritedTrxName);
		
		trxManager.assertThreadInheritedTrxNotExists();
	}

}
