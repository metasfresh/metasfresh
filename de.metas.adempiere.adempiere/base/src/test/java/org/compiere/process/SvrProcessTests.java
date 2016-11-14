package org.compiere.process;

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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.MPInstance;
import org.compiere.process.SvrProcess.ProcessCanceledException;
import org.compiere.util.Env;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.process.RunOutOfTrx;

/**
 * Tests {@link SvrProcess} life-cycle management.
 *
 * @author tsa
 *
 */
public class SvrProcessTests
{
	public static class MockedSvrProcess extends SvrProcess
	{
		public final boolean expectPrepareOutOfTrx;
		public RuntimeException onPrepareThrowException = null;
		public boolean prepareExecuted = false;

		public final boolean expectDoItOutOfTrx;
		public Exception onDoItThrowException = null;
		public String onDoItReturnMsg = null;
		public boolean doItExecuted = false;

		private boolean postProcess_Executed = false;
		public RuntimeException onPostProcessThrowException = null;
		private Boolean postProcess_Param_Success = null;

		protected MockedSvrProcess()
		{
			this(false, false); // expectPrepareOutOfTrx=false, expectDoItOutOfTrx=false
		}

		protected MockedSvrProcess(final boolean expectPrepareOutOfTrx, final boolean expectDoItOutOfTrx)
		{
			super();
			this.expectPrepareOutOfTrx = expectPrepareOutOfTrx;
			this.expectDoItOutOfTrx = expectDoItOutOfTrx;
		}

		@Override
		protected void prepare()
		{
			Assert.assertFalse("prepare method not already executed", prepareExecuted);
			prepareExecuted = true;

			assertOutOfTrx(expectPrepareOutOfTrx);

			if (onPrepareThrowException != null)
			{
				throw onPrepareThrowException;
			}
		}

		@Override
		protected String doIt() throws Exception
		{
			Assert.assertFalse("doIt method not already executed", doItExecuted);
			doItExecuted = true;

			assertOutOfTrx(expectDoItOutOfTrx);

			//
			// Make sure the AD_PInstance was updated correctly, so far
			assertPInstanceConsistentWhileRunning();

			if (onDoItThrowException != null)
			{
				throw onDoItThrowException;
			}

			return onDoItReturnMsg;
		}

		@Override
		protected void postProcess(final boolean success)
		{
			Assert.assertFalse("postProcess method not already executed", postProcess_Executed);
			postProcess_Executed = true;
			postProcess_Param_Success = success;

			// post process shall ALWAYS run out of transaction
			assertOutOfTrx(true);

			if (onPostProcessThrowException != null)
			{
				throw onPostProcessThrowException;
			}
		}

		protected final void assertOutOfTrx(final boolean expectedOutOfTrx)
		{
			final ITrxManager trxManager = Services.get(ITrxManager.class);

			// Out of transaction
			if (expectedOutOfTrx)
			{
				trxManager.assertTrxNameNull(trxManager.getThreadInheritedTrxName());
				trxManager.assertTrxNameNull(getTrxName());
			}
			// In transaction
			else
			{
				trxManager.assertThreadInheritedTrxExists();
				trxManager.assertTrxNameNotNull(getTrxName());

			}
		}

		public void assertPostProcessExecuted(final boolean expectedSuccess)
		{
			Assert.assertTrue("postProcess method executed", this.postProcess_Executed);
			Assert.assertEquals("postProcess - success parameter", expectedSuccess, this.postProcess_Param_Success);
		}

		private final void assertPInstanceConsistentWhileRunning()
		{
			final ProcessInfo pi = getProcessInfo();
			Assert.assertNotNull("ProcessInfo not null", pi);

			// Make sure AD_PInstance record was created in database
			final I_AD_PInstance pinstance = InterfaceWrapperHelper.create(Env.getCtx(), pi.getAD_PInstance_ID(), I_AD_PInstance.class, ITrx.TRXNAME_None);
			Assert.assertNotNull("AD_PInstance record exists in database", pinstance);
			Assert.assertTrue("AD_PInstance record exists in database (ID exists)", pinstance.getAD_PInstance_ID() > 0); // shall not happen

			Assert.assertEquals("Invalid AD_PInstance.Processing", true, pinstance.isProcessing());
		}

		public void assertEverythingConsistentAfterRun()
		{
			// Make sure ProcesInfo exists
			final ProcessInfo pi = getProcessInfo();
			Assert.assertNotNull("ProcessInfo not null", pi);

			// Make sure AD_PInstance record was created in database
			final I_AD_PInstance pinstance = InterfaceWrapperHelper.create(Env.getCtx(), pi.getAD_PInstance_ID(), I_AD_PInstance.class, ITrx.TRXNAME_None);
			Assert.assertNotNull("AD_PInstance record exists in database", pinstance);
			Assert.assertTrue("AD_PInstance record exists in database (ID exists)", pinstance.getAD_PInstance_ID() > 0); // shall not happen
			
			final ProcessExecutionResult result = pi.getResult();
			Assert.assertEquals("Result AD_PInstance_ID shall match ProcessInfo's AD_PInstance_ID", pi.getAD_PInstance_ID(), result.getAD_PInstance_ID());

			// Validate AD_PInstance flags
			final int resultExpected = result.isError() ? MPInstance.RESULT_ERROR : MPInstance.RESULT_OK;
			Assert.assertEquals("Invalid AD_PInstance.Result", resultExpected, pinstance.getResult());
			Assert.assertEquals("Invalid AD_PInstance.ErrorMsg/Summary", result.getSummary(), pinstance.getErrorMsg());
			Assert.assertEquals("Invalid AD_PInstance.Processing", false, pinstance.isProcessing());
			Assert.assertEquals("Invalid AD_PInstance.ErrorMsg/Summary", pi.getWhereClause(), pinstance.getWhereClause());
			

			// Make sure postProcess was executed and it's success parameter was consistent with ProcessInfo.isError
			final boolean expected_postProcess_param_Success = !result.isError();
			assertPostProcessExecuted(expected_postProcess_param_Success);
		}

	}

	public static class MockedSvrProcess_PrepareOutOfTrx extends MockedSvrProcess
	{
		public MockedSvrProcess_PrepareOutOfTrx()
		{
			super(true, false); // expectPrepareOutOfTrx=true, expectDoItOutOfTrx=false
		}

		@Override
		@RunOutOfTrx
		protected void prepare()
		{
			super.prepare();
		}
	}

	public static class MockedSvrProcess_DoItOutOfTrx extends MockedSvrProcess
	{
		public MockedSvrProcess_DoItOutOfTrx()
		{
			super(true, true); // expectPrepareOutOfTrx=true, expectDoItOutOfTrx=true
			// NOTE: in case doIt is out of transaction then prepare is automatically set to be out of transaction
		}

		@Override
		@RunOutOfTrx
		protected String doIt() throws Exception
		{
			return super.doIt();
		}
	}

	private Properties ctx;
	private PlainTrxManager trxManager;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		//
		// Don't fail on COMMIT/ROLLBACK if transaction was not started,
		// because when running processes the transaction is actually started when it's first used.
		trxManager = (PlainTrxManager)Services.get(ITrxManager.class);
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);
	}

	@After
	public void afterTest()
	{
		trxManager.assertNoActiveTransactions();
		trxManager.assertTrxNameNull(trxManager.getThreadInheritedTrxName());
	}

	private ProcessInfo createProcessInfo(final SvrProcess processInstance)
	{
		// Create the AD_PInstance record
		final I_AD_PInstance pinstance = InterfaceWrapperHelper.create(ctx, I_AD_PInstance.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(pinstance);

		//
		// Create ProcessInfo descriptor
		final ProcessInfo pi = ProcessInfo.builder()
				.setAD_PInstance(pinstance)
				.setAD_Process_ID(0) // N/A
				.setTitle("Test")
				.setClassname(processInstance == null ? null : processInstance.getClass().getName())
				.build();
		return pi;
	}

	//
	//
	// Actual tests ------------------------------------------------------------------------------------------------------------------------------------------------------
	//
	//
	@Test
	public void test_RunAndReturnMessage()
	{
		final MockedSvrProcess process = new MockedSvrProcess();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onDoItReturnMsg = "Process executed";
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", true, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", false, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.IsError", "Process executed", result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_RunAndFailOnDoIt()
	{
		final MockedSvrProcess process = new MockedSvrProcess();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		final String failErrorMsg = "FailOnDoIt";
		process.onDoItThrowException = new AdempiereException(failErrorMsg);
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", false, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", true, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", failErrorMsg, result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_RunAndFailOnPrepare()
	{
		final MockedSvrProcess process = new MockedSvrProcess();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		final String failErrorMsg = "FailOnPrepare";
		process.onPrepareThrowException = new AdempiereException(failErrorMsg);
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", false, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", true, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", failErrorMsg, result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", false, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrx_doItReturnMessage()
	{
		final MockedSvrProcess_PrepareOutOfTrx process = new MockedSvrProcess_PrepareOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onDoItReturnMsg = "Process executed";
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", true, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", false, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", "Process executed", result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrxButFail()
	{
		final MockedSvrProcess_PrepareOutOfTrx process = new MockedSvrProcess_PrepareOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		final String failErrorMsg = "FailOnPrepare";
		process.onPrepareThrowException = new AdempiereException(failErrorMsg);
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", false, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", true, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", failErrorMsg, result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", false, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrxButCancel()
	{
		final MockedSvrProcess_PrepareOutOfTrx process = new MockedSvrProcess_PrepareOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onPrepareThrowException = new ProcessCanceledException();
		final boolean ok = process.startProcess(ctx, pi, trx);
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", true, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", false, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", "@" + ProcessCanceledException.MSG_Canceled + "@", result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", false, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_doItOutOfTrx_doItReturnMessage()
	{
		final MockedSvrProcess_DoItOutOfTrx process = new MockedSvrProcess_DoItOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onDoItReturnMsg = "Process executed";
		final boolean ok = process.startProcess(ctx, pi, trx);

		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", true, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", false, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", "Process executed", result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_doItOutOfTrxButFail()
	{
		final MockedSvrProcess_DoItOutOfTrx process = new MockedSvrProcess_DoItOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		final String failErrorMsg = "FailOnDoIt";
		process.onDoItThrowException = new AdempiereException(failErrorMsg);
		final boolean ok = process.startProcess(ctx, pi, trx);
		
		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", false, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", true, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", failErrorMsg, result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_DoItOutOfTrxButCancel()
	{
		final MockedSvrProcess_DoItOutOfTrx process = new MockedSvrProcess_DoItOutOfTrx();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onDoItThrowException = new ProcessCanceledException();
		final boolean ok = process.startProcess(ctx, pi, trx);

		final ProcessExecutionResult result = pi.getResult();

		Assert.assertEquals("Invalid result", true, ok);
		Assert.assertEquals("Invalid ProcessInfo.IsError", false, result.isError());
		Assert.assertEquals("Invalid ProcessInfo.Summary", "@" + ProcessCanceledException.MSG_Canceled + "@", result.getSummary());
		Assert.assertEquals("prepare() executed", true, process.prepareExecuted);
		Assert.assertEquals("doIt() executed", true, process.doItExecuted);
		process.assertEverythingConsistentAfterRun();
	}

	/**
	 * Test the behavior when {@link SvrProcess#postProcess(boolean)} fails.
	 *
	 * NOTE: this is subject to change, but at the moment, if postProcess fails, an exception is thrown right away and it's assumed that it's the job of caller to handle it and update the ProcessInfo.
	 * I know it's not OK, but it is as it is for now.
	 */
	@Test
	public void test_RunAndFailOnPostProcess()
	{
		final MockedSvrProcess process = new MockedSvrProcess();
		final ITrx trx = ITrx.TRX_None;
		final ProcessInfo pi = createProcessInfo(process);

		process.onPostProcessThrowException = new RuntimeException("fail on postProcess");
		Boolean startProcess_ReturnValue = null;
		Exception startProcess_Exception = null;
		try
		{
			startProcess_ReturnValue = process.startProcess(ctx, pi, trx);
		}
		catch (Exception e)
		{
			startProcess_Exception = e;
		}

		Assert.assertEquals("startProcess return value", null, startProcess_ReturnValue);
		Assert.assertSame("startProcess exception", process.onPostProcessThrowException, startProcess_Exception);
	}
}
