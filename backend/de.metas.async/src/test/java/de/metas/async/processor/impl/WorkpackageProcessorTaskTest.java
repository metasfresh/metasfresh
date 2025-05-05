/**
 *
 */
package de.metas.async.processor.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * @author tsa
 *
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
		Assert.assertTrue("afterWorkpackageProcessed() method should be invoked for " + task, task.isAfterWorkpackageProcessedInvoked());
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
		Assert.assertEquals("Invalid IsError", false, workpackage.isError());
		Assert.assertEquals("Invalid AD_Issue", null, workpackage.getAD_Issue());
		Assert.assertEquals("Invalid Processed", true, workpackage.isProcessed());
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
		Assert.assertEquals("Invalid Processed", false, workpackage.isProcessed());
		Assert.assertEquals("Invalid IsError", true, workpackage.isError());
		Assert.assertNotNull("Invalid AD_Issue", workpackage.getAD_Issue());

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
		Assert.assertEquals("Invalid Processed", false, workpackage.isProcessed());
		Assert.assertEquals("Invalid IsError", false, workpackage.isError());
		Assert.assertNotNull("Invalid SkippedAt", workpackage.getSkippedAt());
		assertThat(workpackage.getSkipped_Last_Reason()).as("Invalid Skipped_Last_Reason").startsWith(skipReason);
		Assert.assertEquals("Invalid SkipTimeoutMillis", skipTimeoutMillis, workpackage.getSkipTimeoutMillis());
		Assert.assertEquals("Invalid Skipped_Count", 1, workpackage.getSkipped_Count());
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
		assertEquals("Invalid Processed", false, workpackage.isProcessed());
		assertEquals("Invalid IsError", false, workpackage.isError());
		assertNotNull("Invalid SkippedAt", workpackage.getSkippedAt());
		assertThat("Invalid Skipped_Last_Reason", workpackage.getSkipped_Last_Reason(), startsWith("Deadlock detected"));
		assertEquals("Invalid SkipTimeoutMillis", skipTimeoutMillis, workpackage.getSkipTimeoutMillis());
		assertEquals("Invalid Skipped_Count", 1, workpackage.getSkipped_Count());
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
			Assert.assertEquals("Invalid Skipped_Count", i, workpackage.getSkipped_Count());
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
		Assert.assertEquals("Invalid Processed", false, workpackage.isProcessed());
		Assert.assertEquals("Invalid IsError", true, workpackage.isError());
		Assert.assertNotNull("Invalid AD_Issue", workpackage.getAD_Issue());
	}
}
