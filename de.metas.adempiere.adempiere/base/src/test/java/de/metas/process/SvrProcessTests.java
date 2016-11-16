package de.metas.process;

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
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.process.SvrProcess.ProcessCanceledException;
import de.metas.process.impl.ADPInstanceDAO;

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

		// TODO: we need to validate AD_PInstance values only when running from ProcessCtl
		public boolean validateAD_PInstance_Values = false;

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
			final I_AD_PInstance pinstance = retrieveAD_PInstance();
			if (validateAD_PInstance_Values)
			{
				Assert.assertEquals("Invalid AD_PInstance.Processing", true, pinstance.isProcessing());
			}

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

		private final I_AD_PInstance retrieveAD_PInstance()
		{
			final ProcessInfo pi = getProcessInfo();
			Assert.assertNotNull("ProcessInfo not null", pi);

			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).retrieveAD_PInstance(Env.getCtx(), pi.getAD_PInstance_ID());

			// Make sure AD_PInstance record was created in database
			Assert.assertNotNull("AD_PInstance record exists in database", pinstance);
			Assert.assertTrue("AD_PInstance record exists in database (ID exists)", pinstance.getAD_PInstance_ID() > 0); // shall not happen

			return pinstance;
		}

		public void assertEverythingConsistentAfterRun()
		{
			// Make sure ProcesInfo exists
			final ProcessInfo pi = getProcessInfo();
			Assert.assertNotNull("ProcessInfo not null", pi);

			final ProcessExecutionResult result = pi.getResult();
			Assert.assertEquals("Result AD_PInstance_ID shall match ProcessInfo's AD_PInstance_ID", pi.getAD_PInstance_ID(), result.getAD_PInstance_ID());

			//
			// Validate AD_PInstance values
			if (validateAD_PInstance_Values)
			{
				final I_AD_PInstance pinstance = retrieveAD_PInstance();
				final int resultExpected = result.isError() ? ADPInstanceDAO.RESULT_ERROR : ADPInstanceDAO.RESULT_OK;
				Assert.assertEquals("Invalid AD_PInstance.Result", resultExpected, pinstance.getResult());
				Assert.assertEquals("Invalid AD_PInstance.ErrorMsg/Summary", result.getSummary(), pinstance.getErrorMsg());
				Assert.assertEquals("Invalid AD_PInstance.Processing", false, pinstance.isProcessing());
				Assert.assertEquals("Invalid AD_PInstance.ErrorMsg/Summary", pi.getWhereClause(), pinstance.getWhereClause());
			}

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

	private PlainTrxManager trxManager;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

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
		final Properties ctx = Env.getCtx();
		
		// Create the AD_PInstance record
		int AD_Process_ID = 0; // N/A
		int AD_Table_ID = 0;
		int recordId = 0;
		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, AD_Process_ID, AD_Table_ID, recordId);

		//
		// Create ProcessInfo descriptor
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_PInstance(pinstance)
				.setTitle("Test")
				.setClassname(processInstance == null ? null : processInstance.getClass().getName())
				.build();
		return pi;
	}
	
	private Exception startProcessAndExpectToFail(final SvrProcess process, final ProcessInfo pi, final ITrx trx)
	{
		try
		{
			process.startProcess(pi, trx);
			Assert.fail("Process is expected to fail");
			return null;
		}
		catch (Exception e)
		{
			return e;
		}
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
		process.startProcess(pi, trx);
		final ProcessExecutionResult result = pi.getResult();

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
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assert.assertEquals("Thrown exception", process.onDoItThrowException, exceptionActual);
		
		final ProcessExecutionResult result = pi.getResult();

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
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assert.assertEquals("Thrown exception", process.onPrepareThrowException, exceptionActual);
		
		final ProcessExecutionResult result = pi.getResult();

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
		process.startProcess(pi, trx);
		final ProcessExecutionResult result = pi.getResult();

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
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assert.assertEquals("Thrown exception", process.onPrepareThrowException, exceptionActual);
		
		final ProcessExecutionResult result = pi.getResult();

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
		process.startProcess(pi, trx);
		final ProcessExecutionResult result = pi.getResult();

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
		process.startProcess(pi, trx);

		final ProcessExecutionResult result = pi.getResult();

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
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assert.assertEquals("Thrown exception", process.onDoItThrowException, exceptionActual);

		final ProcessExecutionResult result = pi.getResult();

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
		process.startProcess(pi, trx);

		final ProcessExecutionResult result = pi.getResult();

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
		Exception startProcess_Exception = startProcessAndExpectToFail(process, pi, trx);
		Assert.assertSame("startProcess exception", process.onPostProcessThrowException, startProcess_Exception);
	}
}
