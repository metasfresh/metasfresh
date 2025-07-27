/**
 *
 */
package de.metas.async.processor.impl;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author tsa
 */
public class WorkpackageProcessorTaskTest extends QueueProcessorTestBase
{
	private static class TestableWorkpackageProcessorTask extends WorkpackageProcessorTask
	{
		private boolean afterWorkpackageProcessedInvoked = false;

		public TestableWorkpackageProcessorTask(
				final IQueueProcessor queueProcessor,
				final IWorkpackageProcessor workPackageProcessor,
				final I_C_Queue_WorkPackage workPackage,
				final IWorkpackageLogsRepository logsRepository,
				PerformanceMonitoringService perfMonService)
		{
			super(queueProcessor, workPackageProcessor, workPackage, logsRepository, perfMonService);
		}

		@Override
		protected void afterWorkpackageProcessed()
		{
			afterWorkpackageProcessedInvoked = true;
		}

		public boolean isAfterWorkpackageProcessedInvoked()
		{
			return afterWorkpackageProcessedInvoked;
		}
	}

	private MockedQueueProcessor queueProcessor;
	private IWorkpackageLogsRepository logsRepository;

	private I_C_Queue_WorkPackage workpackage;

	private PerformanceMonitoringService perfMonService;

	@Override
	protected void beforeTestCustomized()
	{
		queueProcessor = new MockedQueueProcessor();
		logsRepository = NOPWorkpackageLogsRepository.instance;
		perfMonService = NoopPerformanceMonitoringService.INSTANCE;

		workpackage = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Queue_WorkPackage.class, ITrx.TRXNAME_None);
		workpackage.setProcessed(false);
		// workpackage.setIsReadyForProcessing(true); // not checked, not relevant
		workpackage.setIsError(false);
		InterfaceWrapperHelper.save(workpackage);
	}

	@Override
	protected void afterTestCustomized()
	{
	}

	private void assertAfterWorkpackageProcessedInvoked(final TestableWorkpackageProcessorTask task)
	{
		Assertions.assertTrue(task.isAfterWorkpackageProcessedInvoked(), "afterWorkpackageProcessed() method should be invoked for " + task);
	}

	/**
	 * Test case: {@link IWorkpackageProcessor} returns Result.SUCCESS
	 */
	@Test
	public void testProcessSuccess()
	{
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> IWorkpackageProcessor.Result.SUCCESS;
		final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
		task.run();

		assertAfterWorkpackageProcessedInvoked(task);
		Assertions.assertFalse(workpackage.isError(), "Invalid IsError");
		Assertions.assertNull(workpackage.getAD_Issue(), "Invalid AD_Issue");
		Assertions.assertTrue(workpackage.isProcessed(), "Invalid Processed");
	}

	/**
	 * Test case: {@link IWorkpackageProcessor} throws an exception
	 */
	@Test
	public void testProcessError()
	{
		final String processingErrorMsg = "test-error";
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> {
			throw new RuntimeException(processingErrorMsg);
		};
		final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
		task.run();

		assertAfterWorkpackageProcessedInvoked(task);
		Assertions.assertFalse(workpackage.isProcessed(), "Invalid Processed");
		Assertions.assertTrue(workpackage.isError(), "Invalid IsError");
		Assertions.assertNotNull(workpackage.getAD_Issue(), "Invalid AD_Issue");

		final String expectedErrorMessage = RuntimeException.class.getSimpleName() + ": " + processingErrorMsg;
		assertThat(workpackage.getErrorMsg()).as("Invalid ErrorMsg").startsWith(expectedErrorMessage);
	}

	/**
	 * Test case: {@link IWorkpackageProcessor} throws {@link WorkpackageSkipRequestException}
	 */
	@Test
	public void testProcessSkip()
	{
		final String skipReason = "test-skip";
		final int skipTimeoutMillis = 12345;
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> {
			throw WorkpackageSkipRequestException.createWithTimeout(skipReason, skipTimeoutMillis);
		};
		final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
		task.run();

		assertAfterWorkpackageProcessedInvoked(task);
		Assertions.assertFalse(workpackage.isProcessed(), "Invalid Processed");
		Assertions.assertFalse(workpackage.isError(), "Invalid IsError");
		Assertions.assertNotNull(workpackage.getSkippedAt(), "Invalid SkippedAt");
		assertThat(workpackage.getSkipped_Last_Reason()).as("Invalid Skipped_Last_Reason").startsWith(skipReason);
		Assertions.assertEquals(skipTimeoutMillis, workpackage.getSkipTimeoutMillis(), "Invalid SkipTimeoutMillis");
		Assertions.assertEquals(1, workpackage.getSkipped_Count(), "Invalid Skipped_Count");
	}

	@Test
	public void testProcessSkipOnDeadLock()
	{
		final int skipTimeoutMillis = 5000; // for now this needs to be kept in sync with the code under test
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> {
			throw new DBDeadLockDetectedException(null, null);
		};
		final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
		task.run();

		assertAfterWorkpackageProcessedInvoked(task);
		Assertions.assertFalse(workpackage.isProcessed(), "Invalid Processed");
		Assertions.assertFalse(workpackage.isError(), "Invalid IsError");
		Assertions.assertNotNull(workpackage.getSkippedAt(), "Invalid SkippedAt");

		Assertions.assertTrue(workpackage.getSkipped_Last_Reason().startsWith("Deadlock detected"), "Invalid Skipped_Last_Reason");
		Assertions.assertEquals(skipTimeoutMillis, workpackage.getSkipTimeoutMillis(), "Invalid SkipTimeoutMillis");
		Assertions.assertEquals(1, workpackage.getSkipped_Count(), "Invalid Skipped_Count");
	}

	/**
	 * Test case: {@link IWorkpackageProcessor} throws {@link WorkpackageSkipRequestException} multiple times. The counter shall be updated correctly
	 */
	@Test
	public void testProcessSkipSuccesive()
	{
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> {
			throw WorkpackageSkipRequestException.create("test-skip");
		};

		for (int i = 1; i <= 10; i++)
		{
			final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
			task.run();
			Assertions.assertEquals(i, workpackage.getSkipped_Count(), "Invalid Skipped_Count");
		}
	}

	/**
	 * Test case: {@link IWorkpackageProcessor} returns an invalid value
	 */
	@Test
	public void testProcessInvalidReturnValue()
	{
		final IWorkpackageProcessor workPackageProcessor = (workpackage, localTrxName) -> null;
		final TestableWorkpackageProcessorTask task = new TestableWorkpackageProcessorTask(queueProcessor, workPackageProcessor, workpackage, logsRepository, perfMonService);
		task.run();

		assertAfterWorkpackageProcessedInvoked(task);
		Assertions.assertFalse(workpackage.isProcessed(), "Invalid Processed");
		Assertions.assertTrue(workpackage.isError(), "Invalid IsError");
		Assertions.assertNotNull(workpackage.getAD_Issue(), "Invalid AD_Issue");
	}
}
