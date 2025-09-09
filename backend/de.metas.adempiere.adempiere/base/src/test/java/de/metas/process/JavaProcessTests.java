package de.metas.process;

import de.metas.process.JavaProcess.ProcessCanceledException;
import de.metas.process.impl.ADPInstanceDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Tests {@link JavaProcess} life-cycle management.
 *
 * @author tsa
 */
public class JavaProcessTests
{
	public static class MockedJavaProcess extends JavaProcess
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

		protected MockedJavaProcess()
		{
			this(false, false); // expectPrepareOutOfTrx=false, expectDoItOutOfTrx=false
		}

		protected MockedJavaProcess(final boolean expectPrepareOutOfTrx, final boolean expectDoItOutOfTrx)
		{
			super();
			this.expectPrepareOutOfTrx = expectPrepareOutOfTrx;
			this.expectDoItOutOfTrx = expectDoItOutOfTrx;
		}

		@Override
		protected void prepare()
		{
			Assertions.assertFalse(prepareExecuted, "prepare method not already executed");
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
			Assertions.assertFalse(doItExecuted, "doIt method not already executed");
			doItExecuted = true;

			assertOutOfTrx(expectDoItOutOfTrx);

			//
			// Make sure the AD_PInstance was updated correctly, so far
			final I_AD_PInstance pinstance = retrieveAD_PInstance();
			if (validateAD_PInstance_Values)
			{
				Assertions.assertEquals( true,  pinstance.isProcessing(), "Invalid AD_PInstance.Processing");
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
			Assertions.assertFalse(postProcess_Executed, "postProcess method not already executed");
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
			Assertions.assertTrue(postProcess_Executed, "postProcess method executed");
			Assertions.assertEquals( expectedSuccess,  postProcess_Param_Success, "postProcess - success parameter");
		}

		private final I_AD_PInstance retrieveAD_PInstance()
		{
			final ProcessInfo pi = getProcessInfo();
			Assertions.assertNotNull(pi, "ProcessInfo not null");

			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).getById(pi.getPinstanceId());

			// Make sure AD_PInstance record was created in database
			Assertions.assertNotNull(pinstance, "AD_PInstance record exists in database");
			Assertions.assertTrue(pinstance.getAD_PInstance_ID() > 0, "AD_PInstance record exists in database (ID exists)"); // shall not happen

			return pinstance;
		}

		public void assertEverythingConsistentAfterRun()
		{
			// Make sure ProcesInfo exists
			final ProcessInfo pi = getProcessInfo();
			Assertions.assertNotNull( pi, "ProcessInfo not null");

			final ProcessExecutionResult result = pi.getResult();
			Assertions.assertEquals( pi.getPinstanceId(),  result.getPinstanceId(), "Result AD_PInstance_ID shall match ProcessInfo's AD_PInstance_ID");

			//
			// Validate AD_PInstance values
			if (validateAD_PInstance_Values)
			{
				final I_AD_PInstance pinstance = retrieveAD_PInstance();
				final int resultExpected = result.isError() ? ADPInstanceDAO.RESULT_ERROR : ADPInstanceDAO.RESULT_OK;
				Assertions.assertEquals( loggedUserId.getRepoId(),  pinstance.getAD_User_ID(), "Invalid AD_PInstance.AD_User_ID");
				Assertions.assertEquals( resultExpected,  pinstance.getResult(), "Invalid AD_PInstance.Result");
				Assertions.assertEquals( result.getSummary(),  pinstance.getErrorMsg(), "Invalid AD_PInstance.ErrorMsg/Summary");
				Assertions.assertEquals( false,  pinstance.isProcessing(), "Invalid AD_PInstance.Processing");
				Assertions.assertEquals( pi.getWhereClause(),  pinstance.getWhereClause(), "Invalid AD_PInstance.ErrorMsg/Summary");
			}

			// Make sure postProcess was executed and it's success parameter was consistent with ProcessInfo.isError
			final boolean expected_postProcess_param_Success = !result.isError();
			assertPostProcessExecuted(expected_postProcess_param_Success);
		}

	}

	public static class MockedJavaProcess_PrepareOutOfTrx extends MockedJavaProcess
	{
		public MockedJavaProcess_PrepareOutOfTrx()
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

	public static class MockedJavaProcess_DoItOutOfTrx extends MockedJavaProcess
	{
		public MockedJavaProcess_DoItOutOfTrx()
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

	private static UserId loggedUserId = UserId.ofRepoId(1234567);
	private PlainTrxManager trxManager;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		//
		// Don't fail on COMMIT/ROLLBACK if transaction was not started,
		// because when running processes the transaction is actually started when it's first used.
		trxManager = (PlainTrxManager)Services.get(ITrxManager.class);
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);

		Env.setLoggedUserId(Env.getCtx(), loggedUserId);
	}

	@AfterEach
	public void afterTest()
	{
		trxManager.assertNoActiveTransactions();
		trxManager.assertTrxNameNull(trxManager.getThreadInheritedTrxName());
	}

	private <T extends JavaProcess> ProcessInfo createProcessInfo(final Class<T> processClass)
	{
		final IADPInstanceDAO pinstancesRepo = Services.get(IADPInstanceDAO.class);

		// Create the AD_PInstance record
		final AdProcessId adProcessId = createProcess(processClass);
		final I_AD_PInstance pinstance = pinstancesRepo.createAD_PInstance(adProcessId);

		//
		// Create ProcessInfo descriptor
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_PInstance(pinstance)
				.setTitle("Test")
				// .setClassname(processClass == null ? null : processClass.getName())
				.build();
		return pi;
	}

	private AdProcessId createProcess(final Class<?> processClass)
	{
		final I_AD_Process adProcess = newInstanceOutOfTrx(I_AD_Process.class);
		adProcess.setValue(processClass.getSimpleName());
		adProcess.setName(processClass.getSimpleName());
		adProcess.setType(X_AD_Process.TYPE_Java);
		adProcess.setClassname(processClass.getName());
		saveRecord(adProcess);

		return AdProcessId.ofRepoId(adProcess.getAD_Process_ID());
	}

	private ProcessExecutionResult startProcess(final JavaProcess process, final ProcessInfo pi, final ITrx trx)
	{
		try (IAutoCloseable c = JavaProcess.temporaryChangeCurrentInstance(process))
		{
			process.startProcess(pi, trx);
			return pi.getResult();
		}
	}

	private Exception startProcessAndExpectToFail(final JavaProcess process, final ProcessInfo pi, final ITrx trx)
	{
		try (IAutoCloseable c = JavaProcess.temporaryChangeCurrentInstance(process))
		{
			process.startProcess(pi, trx);
			Assertions.fail("Process is expected to fail");
			return null;
		}
		catch (final Exception e)
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
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess.class);
		final MockedJavaProcess process = (MockedJavaProcess)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onDoItReturnMsg = "Process executed";
		final ProcessExecutionResult result = startProcess(process, pi, trx);

		Assertions.assertEquals( false,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( "Process executed",  result.getSummary(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_RunAndFailOnDoIt()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess.class);
		final MockedJavaProcess process = (MockedJavaProcess)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		final String failErrorMsg = "FailOnDoIt";
		process.onDoItThrowException = new AdempiereException(failErrorMsg);
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assertions.assertEquals( process.onDoItThrowException,  exceptionActual, "Thrown exception");

		final ProcessExecutionResult result = pi.getResult();

		Assertions.assertEquals( true,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( failErrorMsg,  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_RunAndFailOnPrepare()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess.class);
		final MockedJavaProcess process = (MockedJavaProcess)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		final String failErrorMsg = "FailOnPrepare";
		process.onPrepareThrowException = new AdempiereException(failErrorMsg);
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assertions.assertEquals( process.onPrepareThrowException,  exceptionActual, "Thrown exception");

		final ProcessExecutionResult result = pi.getResult();

		Assertions.assertEquals( true,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( failErrorMsg,  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( false,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrx_doItReturnMessage()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_PrepareOutOfTrx.class);
		final MockedJavaProcess_PrepareOutOfTrx process = (MockedJavaProcess_PrepareOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onDoItReturnMsg = "Process executed";
		final ProcessExecutionResult result = startProcess(process, pi, trx);

		Assertions.assertEquals( false,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( "Process executed",  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrxButFail()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_PrepareOutOfTrx.class);
		final MockedJavaProcess_PrepareOutOfTrx process = (MockedJavaProcess_PrepareOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		final String failErrorMsg = "FailOnPrepare";
		process.onPrepareThrowException = new AdempiereException(failErrorMsg);
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assertions.assertEquals( process.onPrepareThrowException,  exceptionActual, "Thrown exception");

		final ProcessExecutionResult result = pi.getResult();

		Assertions.assertEquals( true,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( failErrorMsg,  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( false,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_PrepareOutOfTrxButCancel()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_PrepareOutOfTrx.class);
		final MockedJavaProcess_PrepareOutOfTrx process = (MockedJavaProcess_PrepareOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onPrepareThrowException = new ProcessCanceledException();
		final ProcessExecutionResult result = startProcess(process, pi, trx);

		Assertions.assertEquals( false,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( ProcessCanceledException.MSG_Canceled.toAD_Message(),  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( false,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_doItOutOfTrx_doItReturnMessage()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_DoItOutOfTrx.class);
		final MockedJavaProcess_DoItOutOfTrx process = (MockedJavaProcess_DoItOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onDoItReturnMsg = "Process executed";
		final ProcessExecutionResult result = startProcess(process, pi, trx);

		Assertions.assertEquals( false,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( "Process executed",  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_doItOutOfTrxButFail()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_DoItOutOfTrx.class);
		final MockedJavaProcess_DoItOutOfTrx process = (MockedJavaProcess_DoItOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		final String failErrorMsg = "FailOnDoIt";
		process.onDoItThrowException = new AdempiereException(failErrorMsg);
		final Exception exceptionActual = startProcessAndExpectToFail(process, pi, trx);
		Assertions.assertEquals( process.onDoItThrowException,  exceptionActual, "Thrown exception");

		final ProcessExecutionResult result = pi.getResult();

		Assertions.assertEquals( true,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( failErrorMsg,  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	@Test
	public void test_DoItOutOfTrxButCancel()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess_DoItOutOfTrx.class);
		final MockedJavaProcess_DoItOutOfTrx process = (MockedJavaProcess_DoItOutOfTrx)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onDoItThrowException = new ProcessCanceledException();
		final ProcessExecutionResult result = startProcess(process, pi, trx);

		Assertions.assertEquals( false,  result.isError(), "Invalid ProcessInfo.IsError");
		Assertions.assertEquals( ProcessCanceledException.MSG_Canceled.toAD_Message(),  result.getSummary(), "Invalid ProcessInfo.Summary");
		Assertions.assertEquals( true,  process.prepareExecuted, "prepare() executed");
		Assertions.assertEquals( true,  process.doItExecuted, "doIt() executed");
		process.assertEverythingConsistentAfterRun();
	}

	/**
	 * Test the behavior when {@link JavaProcess#postProcess(boolean)} fails.
	 * <p>
	 * NOTE: this is subject to change, but at the moment, if postProcess fails, an exception is thrown right away and it's assumed that it's the job of caller to handle it and update the ProcessInfo.
	 * I know it's not OK, but it is as it is for now.
	 */
	@Test
	public void test_RunAndFailOnPostProcess()
	{
		final ProcessInfo pi = createProcessInfo(MockedJavaProcess.class);
		final MockedJavaProcess process = (MockedJavaProcess)pi.newProcessClassInstanceOrNull();
		final ITrx trx = ITrx.TRX_None;

		process.onPostProcessThrowException = new RuntimeException("fail on postProcess");
		final Exception startProcess_Exception = startProcessAndExpectToFail(process, pi, trx);
		Assertions.assertSame( process.onPostProcessThrowException, startProcess_Exception, "startProcess exception");
	}
}
