package org.adempiere.ad.trx.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
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
import org.compiere.util.TrxRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TrxManagerTest
{
	/**
	 * Service under test
	 */
	private MockedTrxManager trxManager;

	/**
	 * A custom {@link AdempiereException} extension which we are using to test exceptions propagation through transactions management module
	 */
	public static final class MyCustomAdempiereException extends AdempiereException
	{
		private static final long serialVersionUID = 1L;

		public MyCustomAdempiereException(final String message)
		{
			super(message);
		}
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = new MockedTrxManager();
		Services.registerService(ITrxManager.class, trxManager);
	}

	private void assertInActiveTransactionList(final ITrx trx, final boolean expectedActive)
	{
		boolean actualActive = false;
		for (final ITrx t : trxManager.getActiveTransactionsList())
		{
			if (t == trx)
			{
				actualActive = true;
				break;
			}
		}

		assertThat(actualActive).as("Transaction " + trx
				+ (expectedActive ? " shall be " : " shall not be ")
				+ " in active transactions list").isEqualTo(expectedActive);
	}

	@Test
	public void test_Start_Commit() throws Exception
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		assertThat(trx.isActive()).as("Trx not active").isFalse();

		trx.start();
		assertThat(trx.isActive()).as("Trx active/started").isTrue();

		trx.commit(true);
		assertThat(trx.isActive()).as("Trx not active").isFalse();

		assertInActiveTransactionList(trx, true); // still in active trx list; only close() will take it out
	}

	@Test
	public void test_Start_Rollback()
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		assertThat(trx.isActive()).as("Trx not active").isFalse();
		assertInActiveTransactionList(trx, true);

		trx.start();
		assertThat(trx.isActive()).as("Trx active/started").isTrue();

		trx.rollback();
		assertThat(trx.isActive()).as("Trx not active").isFalse();

		assertInActiveTransactionList(trx, true); // still in active trx list; only close() will take it out
	}

	@Test
	public void test_Start_Close()
	{
		final ITrx trx = trxManager.get("Test", OnTrxMissingPolicy.CreateNew);
		assertThat(trx.isActive()).as("Trx not active").isFalse();
		assertInActiveTransactionList(trx, true);

		trx.start();

		trx.close();
		assertInActiveTransactionList(trx, false);
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName=null, manageTrx=N/A
	 * <p>
	 * Expectation: create a new trxName (and thus a new local trx) with prefix <code>"TrxRun"</code>
	 */
	@Test
	public void test_run_managedTrx_001()
	{
		final String trxName = null;
		final boolean manageTrx = false;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		assertThat(runnable.isExecuted()).as("Runnable was executed").isTrue();

		final String localTrxNameEffective = runnable.getLastTrxNameEffective();
		final MockedTrx trx = (MockedTrx)trxManager.getRemovedTransactionByName(localTrxNameEffective);
		assertThat(trx).as("Transaction was created and removed for trxName=" + localTrxNameEffective).isNotNull();

		assertThat(trx.isCommitCalled()).as("Commit was called for " + trx).isTrue();
		assertThat(trx.isCloseCalled()).as("Close was called for " + trx).isTrue();

		assertThat(trx.getActiveSavepoints()).as("No active savepoints").isEmpty();
		assertThat(trx.getReleasedSavepoints()).as("No released savepoints").isEmpty();
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName!=null, manageTrx=true
	 * <p>
	 * Expectation: create a new trxName (and thus a new local trx) with prefix being the given <code>trxName</code>
	 */
	@Test
	public void test_run_managedTrx_002()
	{
		final String trxName = "TestTrx002";
		final boolean manageTrx = true;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		assertThat(runnable.isExecuted()).as("Runnable was executed").isTrue();

		final String localTrxNameEffective = runnable.getLastTrxNameEffective();
		assertThat(localTrxNameEffective).as("Created trx '" + localTrxNameEffective + "' shall start with '" + trxName + "'").startsWith(trxName);

		final MockedTrx trx = (MockedTrx)trxManager.getRemovedTransactionByName(localTrxNameEffective);
		assertThat(trx).as("Transaction was created and removed for trxName=" + localTrxNameEffective).isNotNull();

		assertThat(trx.isCommitCalled()).as("Commit was called for " + trx).isTrue();
		assertThat(trx.isCloseCalled()).as("Close was called for " + trx).isTrue();

		assertThat(trx.getActiveSavepoints()).as("No active savepoints").isEmpty();
		assertThat(trx.getReleasedSavepoints()).as("No released savepoints").isEmpty();
	}

	/**
	 * Test {@link ITrxManager#run(String, boolean, org.compiere.util.TrxRunnable)} for trxName!=null, manageTrx=false
	 * <p>
	 * Expectation: use the trx with the the given trxName; create a savepoint and to roll back to in case of problems. don't commit in case of success.
	 */
	@Test
	public void test_run_managedTrx_003()
	{
		final String trxName = trxManager.createTrxName("TestTrx003", true); // createNew=true
		final boolean manageTrx = false;
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		trxManager.run(trxName, manageTrx, runnable);

		assertThat(runnable.isExecuted()).as("Runnable was executed").isTrue();

		final String localTrxName = runnable.getLastTrxName();
		// NOTE: until we get rid of "TrxCallableWithTrxName" our Runnables will get the "effective" localTrxName instead of ThreadInherited.
		// Assert.assertEquals("Invalid trxName used", ITrx.TRXNAME_ThreadInherited, localTrxName);
		assertThat(TrxCallableWithTrxName.class).isNotNull(); // non-sense, but we just want to have a reference here for future refactoring
		assertThat(localTrxName).as("Invalid trxName used").isEqualTo(trxName);

		final MockedTrx trx = (MockedTrx)trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		assertThat(trx).as("Transaction was created but not removed for trxName=" + localTrxName).isNotNull();

		assertThat(trx.isCommitCalled()).as("Commit was NOT called for " + trx).isFalse();
		assertThat(trx.isCloseCalled()).as("Close was NOT called for " + trx).isFalse();

		assertThat(trx.getActiveSavepoints()).as("No active savepoints").isEmpty();
		assertThat(trx.getReleasedSavepoints()).as("One released savepoint shall be available").hasSize(1);

		final ITrxSavepoint releasedSavepoint = trx.getReleasedSavepoints().get(0);
		assertThat(releasedSavepoint).as("Released savepoint exists").isNotNull();
	}

	@Test
	public void test_run_inheritedTrx()
	{
		final MockedTrxRunnable runnableInner = new MockedTrxRunnable(trxManager);
		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);

		runnable.setInnerRunnable(() -> trxManager.run(ITrx.TRXNAME_ThreadInherited, runnableInner));

		trxManager.runInNewTrx(runnable);

		assertThat(runnable.isExecuted()).as("Runnable was executed").isTrue();
		assertThat(runnableInner.isExecuted()).as("Inner Runnable was executed").isTrue();

		final String localTrxName = runnable.getLastTrxName();
		assertThat(trxManager.isNull(localTrxName)).as("Runnable trxName shall not be null: " + localTrxName).isFalse();

		assertThat(runnableInner.getLastTrxName()).as("Inner runnable's trxName shall be the same as runnable's trxName").isEqualTo(localTrxName);

		assertThat(trxManager.get(localTrxName, OnTrxMissingPolicy.ReturnTrxNone)).as("Transaction shall be removed: trxName=" + localTrxName).isNull();

		final MockedTrx trx = (MockedTrx)runnableInner.getLastTrx();
		assertThat(trx).as("Last transaction shall not be null").isNotNull();
		assertThat(trx.isCommitCalled()).as("Commit was called for " + trx).isTrue();
		assertThat(trx.isCloseCalled()).as("Close was called for " + trx).isTrue();

		assertThat(trx.getActiveSavepoints()).as("No active savepoints").isEqualTo(Collections.emptyList());

		assertThat(trx.getReleasedSavepoints()).as("One released savepoint shall be available (for inner runnable)").hasSize(1);

		final ITrxSavepoint releasedSavepoint = trx.getReleasedSavepoints().get(0);
		assertThat(releasedSavepoint).as("Released savepoint exists").isNotNull();

		assertThat(releasedSavepoint.getTrx()).as("Release point transaction shall be the same as inner transaction").isSameAs(trx);
	}

	@Test
	public void test_get_InheritedTrx_DonNotCreateNew()
	{
		final boolean createNew = false;

		final ITrx trx = trxManager.get(ITrx.TRXNAME_ThreadInherited, createNew);
		assertThat(trx).as("When no actual transaction name was found then null shall be returned").isNull();
	}

	@Test
	public void test_get_InheritedTrx_CreateNew()
	{
		final boolean createNew = true;
		final ITrx trx = trxManager.get(ITrx.TRXNAME_ThreadInherited, createNew);
		assertThat(trxManager.isNull(trx)).as("Not null transaction shall be returned: " + trx).isFalse();

		final String trxName = trx.getTrxName();
		assertThat(trxManager.isValidTrxName(trxName)).as("Transaction name shall be valid: " + trxName).isTrue();
	}

	/**
	 * Makes sure that {@value ITrx#TRXNAME_ThreadInherited} is not considered null/no transaction.
	 */
	@Test
	public void test_isNull_IneritedTrx()
	{
		assertThat(trxManager.isNull(ITrx.TRXNAME_ThreadInherited)).as("Inherited trxName shall not be considered null").isFalse();
	}

	private void assertSameTrxName(final boolean expected, final String trxName1, final String trxName2)
	{
		assertThat(trxManager.isSameTrxName(trxName1, trxName2)).as("Invalid isSameTrxName() result for trxName1=" + trxName1 + ", trxName2=" + trxName2).isEqualTo(expected);

		// Also test the other way around
		assertThat(trxManager.isSameTrxName(trxName2, trxName1)).as("Invalid isSameTrxName() result for trxName2=" + trxName2 + ", trxName1=" + trxName1).isEqualTo(expected);
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
	 * <p>
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
		runnable.setInnerRunnable(() -> {
			throw new MyCustomAdempiereException("FAIL");
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

		assertThat(exceptionActual).as("Exception shall be propagated").isNotNull();

		final MockedTrx trx = (MockedTrx)trxManager.getTrx(trxName);
		assertThat(trx).as("Transaction shall exist").isNotNull();
		assertThat(trx.isRollbackCalled()).as("Transaction shall not be rollback: " + trx).isFalse();
		assertThat(trx.isCommitCalled()).as("Transaction shall not be commited: " + trx).isFalse();
		assertThat(trx.isActive()).as("Transaction shall be active: " + trx).isTrue();
	}

	/**
	 * Case: we are running with {@link TrxPropagation#NESTED} but we are not providing any transaction.
	 * <p>
	 * Expection: shall fail
	 */
	@Test
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

		assertThatThrownBy(() -> trxManager.run(trxName, trxRunConfig, runnable))
				.isInstanceOf(IllegalTrxRunStateException.class);
	}

	@Test
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

		assertThatThrownBy(() -> trxManager.run(trxName, trxRunConfig, runnable))
				.isInstanceOf(IllegalTrxRunStateException.class);
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
		assertThat(trxManager.getThreadInheritedTrxName()).as("ThreadInheritedTrxName shall not be reset after runnable was executed").isEqualTo(existingThreadInheritedTrxName);
	}

	/**
	 * Case:
	 * <ul>
	 * <li>Propagation=NESTED, OnSuccess=DONT_COMMIT, OnFail=DONT_ROLLBACK, trxName= {@link ITrx#TRXNAME_ThreadInherited}
	 * <li>the thread inerited transaction is set but it does not actually exists
	 * </ul>
	 * <p>
	 * Expectations: an exception will be thrown
	 * <p>
	 * Production code expectations (NOT tested here):
	 * <ul>
	 * <li>system will create the transaction using that provided trxName and will execute the runnable
	 * <li>system shall NOT close the created transaction because even if it created it the calling code was expecting to be there and running
	 * </ul>
	 */
	@Test
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

		assertThatThrownBy(() -> trxManager.run(trxName, trxRunConfig, runnable))
				.isInstanceOf(IllegalTrxRunStateException.class);
	}

	/**
	 * Corner (possible invalid) case: propagation=NESTED, onSuccess=COMMIT.
	 * <p>
	 * Expectation: at least, in JUnit/Developer Mode we expect to fail.
	 */
	@Test
	public void test_run_NestedTransaction_OnSuccessCommit()
	{
		final String trxName = trxManager.createTrxName("TestTrx", true);
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(
				TrxPropagation.NESTED,
				OnRunnableSuccess.COMMIT,
				OnRunnableFail.DONT_ROLLBACK // we don't care for this test
		);

		final MockedTrxRunnable runnable = new MockedTrxRunnable(trxManager);
		assertThatThrownBy(() -> trxManager.run(trxName, trxRunConfig, runnable))
				.isInstanceOf(IllegalTrxRunStateException.class);
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
		runnable.setInnerRunnable(() -> {
			throw expectedException;
		});
		runnable.setDoFinallyRunnable(() -> {
			throw doFinallyException;
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

		assertThat(actualException)
				.isNotNull()
				.as("Proper shall be thrown").isEqualTo(expectedException);
		assertThat(actualException.getSuppressed()).as("Exception thrown on doFinally() shall be suppressed").containsExactly(doFinallyException);
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
		runnable.setInnerRunnable(() -> {
			throw expectedException;
		});
		runnable.setDoFinallyRunnable(() -> {
			throw doFinallyException;
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

		assertThat(actualException)
				.isNotNull()
				.as("Proper shall be thrown").isEqualTo(expectedException);
		assertThat(actualException.getSuppressed()).as("Exception thrown on doFinally() shall be suppressed").containsExactly(doFinallyException);
	}

	@Test
	public void test_getTrx_NULL()
	{
		assertThat(trxManager.getTrx(ITrx.TRXNAME_None)).isEqualTo(ITrx.TRX_None);
		assertThat(trxManager.getTrx(ITrx.TRXNAME_NoneNotNull)).isEqualTo(ITrx.TRX_None);

		// Test for inherited transaction
		trxManager.assertThreadInheritedTrxNotExists();
		assertThat(trxManager.getTrx(ITrx.TRXNAME_ThreadInherited)).isEqualTo(ITrx.TRX_None);
	}

	@Test
	public void test_getTrx_ExistingTrxName()
	{
		// Create and register the transaction
		final String trxName = "TestTrx";
		final ITrx expectedTrx = trxManager.createTrxAndRegister(trxName, false);

		assertThat(trxManager.getTrx(trxName)).isEqualTo(expectedTrx);

		// Test for inherited transaction
		trxManager.setThreadInheritedTrxName(trxName);
		assertThat(trxManager.getTrx(ITrx.TRXNAME_ThreadInherited)).isEqualTo(expectedTrx);
	}

	@Test
	public void test_getTrx_NotExistingTrxName()
	{
		assertThatThrownBy(() -> trxManager.getTrx("MissingTrx"))
				.isInstanceOf(TrxNotFoundException.class);
	}

	@Test
	public void test_getTrx_NotExistingTrxName_ViaThreadInherited()
	{
		trxManager.setThreadInheritedTrxName("MissingTrx");

		assertThatThrownBy(() -> trxManager.getTrx(ITrx.TRXNAME_ThreadInherited))
				.isInstanceOf(TrxNotFoundException.class);
	}

	@Test
	public void test_assertThreadInheritedTrxExists_NoTrx()
	{
		assertThatThrownBy(() -> trxManager.assertThreadInheritedTrxExists())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("ThreadInherited transaction shall be set at this point");
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

	@Test
	public void test_assertThreadInheritedTrxNotExists_WithTrx()
	{
		final String threadInheritedTrxName = trxManager.createTrxName("TestTrx", true);
		trxManager.setThreadInheritedTrxName(threadInheritedTrxName);

		assertThatThrownBy(() -> trxManager.assertThreadInheritedTrxNotExists())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("ThreadInherited transaction shall NOT be set at this point");
	}

	@Nested
	public class runInThreadInheritedTrx
	{
		private void assertRealTrxName(final String lastTrxName)
		{
			assertThat(lastTrxName)
					.isNotNull()
					.isNotEqualTo(ITrx.TRXNAME_ThreadInherited);
		}

		@Test
		public void notRunningInTrxYet()
		{
			final MockedTrxRunnable trxRunnable = new MockedTrxRunnable(trxManager);
			assertThat(trxRunnable.getLastTrxNameEffective()).isNull();

			trxManager.assertThreadInheritedTrxNotExists();

			trxManager.runInThreadInheritedTrx(trxRunnable);

			assertRealTrxName(trxRunnable.getLastTrxNameEffective());
		}

		@Test
		public void alreadyRunningInTrx()
		{
			final MockedTrxRunnable trxRunnable = new MockedTrxRunnable(trxManager);
			assertThat(trxRunnable.getLastTrxNameEffective()).isNull();

			// Simulate already running transaction
			trxManager.get("AlreadyRunningTrx", OnTrxMissingPolicy.CreateNew);
			trxManager.setThreadInheritedTrxName("AlreadyRunningTrx");
			trxManager.assertThreadInheritedTrxExists();

			trxManager.runInThreadInheritedTrx(trxRunnable);
			System.out.println(trxRunnable);

			assertThat(trxRunnable.getLastTrxNameEffective()).isEqualTo("AlreadyRunningTrx");
		}

	}

	@Test
	void trx_accumulateAndProcessAfterCommit()
	{
		final ArrayList<String> flushedItems = new ArrayList<>();

		trxManager.runInThreadInheritedTrx(() -> {
			final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail);
			trx.accumulateAndProcessAfterCommit(
					"propName",
					ImmutableList.of("1", "2"),
					flushedItems::addAll);

			trx.accumulateAndProcessAfterCommit(
					"propName",
					ImmutableList.of("3", "4"),
					flushedItems::addAll);

			assertThat(flushedItems).isEmpty();
		});

		assertThat(flushedItems).containsExactly("1", "2", "3", "4");
	}
}
