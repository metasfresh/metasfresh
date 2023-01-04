package de.metas.async.processor.impl;

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
import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class ThreadPoolQueueProcessorTest extends QueueProcessorTestBase
{
	private I_C_Queue_Processor processorDef;
	private IQueueProcessorsExecutor processorsExecutor;

	@Override
	protected void beforeTestCustomized()
	{
		// LogManager.setLevel(Level.DEBUG);
	}

	private void setupQueueProcessor(final int poolSize)
	{
		processorDef = helper.createQueueProcessor("test",
				poolSize,
				1000 // keepAliveTimeMillis
		);
		helper.assignPackageProcessor(processorDef, StaticMockedWorkpackageProcessor.class);

		processorsExecutor = new QueueProcessorsExecutor();
		processorsExecutor.addQueueProcessor(QueueProcessorDescriptorRepository.mapToQueueProcessor(processorDef));
	}

	@Override
	protected void afterTestCustomized()
	{
		logger.info(StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor().getStatisticsInfo());

		if (processorsExecutor != null)
		{
			processorsExecutor.shutdown();
			processorsExecutor = null;
		}
		processorDef = null;
	}

	/**
	 * Enqueue 100 WPs, the 5th shall throw an exception.
	 */
	@Test
	public void test_Simple_100workpackages() throws Exception
	{
		setupQueueProcessor(5);
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 100, false);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor
				.setDefaultResult(Result.SUCCESS)
				.setRuntimeException(workpackages.get(5), "test error")
		// .setResult(workpackages.get(7), Result.SKIPPED)
		;
		workpackages.get(7).setPriority(X_C_Queue_WorkPackage.PRIORITY_Urgent);

		helper.markReadyForProcessing(workpackages);

		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();

		helper.waitUntilSize(processedWorkpackages, workpackages.size(), 0 * 1000); // wait max 5secs for all WPs to be processed

		assertThat(processedWorkpackages)
				.as("Processed workpackages list shall have same size as initial workpackages list")
				.hasSize(processedWorkpackages.size());

		// because we process in parallel it's impossible to make sure first package will be prio1
		// Assert.assertEquals("Priority 1 packages shall be processed first", workpackages.get(7), processedWorkpackages.get(0));

		for (final I_C_Queue_WorkPackage wp : processedWorkpackages)
		{
			final RuntimeException rteExpected = workpackageProcessor.getRuntimeExceptionFor(wp);
			if (rteExpected != null)
			{
				// Exception
				assertThat(wp.getErrorMsg())
						.as("Workpackage - Invalid ErrorMsg: %s", wp).startsWith(rteExpected.getMessage());
				assertThat(wp.isProcessed())
						.as("Workpackage - Invalid Processed: " + wp).isFalse();
				assertThat(wp.isError()).as("Workpackage - Invalid IsError: " + wp).isTrue();
			}
			else
			{
				assertThat(wp.isProcessed())
						.as("Workpackage - Invalid Processed: " + wp).isTrue();
				assertThat(wp.isError()).
						as("Workpackage - Invalid IsError: " + wp).isFalse();
			}
		}

		helper.assertNothingLocked();
	}

	@Test
	public void test_workpackages_OOME() throws Exception
	{
		LogManager.setLevel(Level.DEBUG);
		LogManager.setLoggerLevel("org.adempiere.ad.trx.api.impl.AbstractTrx", Level.INFO);
		LogManager.setLoggerLevel("de.metas.util.Services", Level.INFO);
		LogManager.setLoggerLevel("de.metas.async.spi.impl.SysconfigBackedSizeBasedWorkpackagePrioConfig", Level.INFO);
		setupQueueProcessor(1);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 5, false);
		assertThat(workpackages).hasSize(5); // guard

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor
				.setDefaultResult(Result.SUCCESS)
				.setOutOfMemoryError(workpackages.get(3), "test OOME");
		helper.markReadyForProcessing(workpackages);

		final List<I_C_Queue_WorkPackage> processedWPs = workpackageProcessor.getProcessedWorkpackages();

		helper.waitUntilSize(processedWPs, workpackages.size(), 0);

		assertThat(processedWPs)
				.as("Processed workpackages list shall have same size as initial workpackages list")
				.hasSize(processedWPs.size());

		assertThat(processedWPs.get(0).isProcessed()).as("Workpackage - Invalid Processed: %s", processedWPs.get(0)).isTrue();
		assertThat(processedWPs.get(0).isError()).as("Workpackage - Invalid IsError: %s", processedWPs.get(0)).isFalse();

		assertThat(processedWPs.get(1).isProcessed()).as("Workpackage - Invalid Processed: %s", processedWPs.get(1)).isTrue();
		assertThat(processedWPs.get(1).isError()).as("Workpackage - Invalid IsError: %s", processedWPs.get(1)).isFalse();

		assertThat(processedWPs.get(2).isProcessed()).as("Workpackage - Invalid Processed: %s", processedWPs.get(2)).isTrue();
		assertThat(processedWPs.get(2).isError()).as("Workpackage - Invalid IsError: %s", processedWPs.get(2)).isFalse();

		assertThat(processedWPs.get(3).getErrorMsg()).as("Workpackage - Invalid ErrorMsg: %s", processedWPs.get(3)).startsWith("OutOfMemoryError: test OOME");
		assertThat(processedWPs.get(3).isProcessed()).as("Workpackage - Invalid Processed: %s", processedWPs.get(3)).isFalse();
		assertThat(processedWPs.get(3).isError()).as("Workpackage - Invalid IsError: " + processedWPs.get(3)).isTrue();

		assertThat(processedWPs.get(4).isProcessed()).as("Workpackage - Invalid Processed: %s", processedWPs.get(0)).isTrue();
		assertThat(processedWPs.get(4).isError()).as("Workpackage - Invalid IsError: %s", processedWPs.get(0)).isFalse();

		helper.assertNothingLocked();
	}

	@Test
	public void test_Callback() throws Exception
	{
		setupQueueProcessor(5);
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final int count = 1;
		final boolean markReadyForProcessing = false;
		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, count, markReadyForProcessing);
		final I_C_Queue_WorkPackage workpackage0 = workpackages.get(0);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);

		//
		// Enqueue the workpackage using a callback
		{
			final Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAndReturn(workpackage0);
			final IWorkpackageProcessorExecutionResult result = futureResult.get(1, TimeUnit.MINUTES);

			final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();
			helper.waitUntilSize(processedWorkpackages, workpackages.size(), 0 * 1000);

			final I_C_Queue_WorkPackage processedWorkpackage0 = processedWorkpackages.get(0);
			InterfaceWrapperHelper.refresh(workpackage0);
			assertThat(processedWorkpackage0).as("Processed package shall be the same as the one enqueued").isEqualTo(workpackage0);
			assertThat(result.getC_Queue_WorkPackage()).as("Callback shall be called for our workpackage").isEqualTo(processedWorkpackage0);
		}

		//
		// Enqueue the same workpackage again, but without a callback.
		// Make sure previous callback is not called twice.
		{
			// remove from processed list. If not doing this, the workpackage processor will thrown an error because workpackage was already processed by it
			workpackageProcessor.removeProcessedWorkpackageId(workpackage0.getC_Queue_WorkPackage_ID());

			helper.markUnprocessed(workpackage0);

			final Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAndReturn(workpackage0);
			final IWorkpackageProcessorExecutionResult result = futureResult.get(1, TimeUnit.MINUTES);

			InterfaceWrapperHelper.refresh(workpackage0);
			assertThat(result.getC_Queue_WorkPackage()).as("Callback shall be called for our workpackage").isEqualTo(workpackage0);
			assertThat(workpackage0.isProcessed()).as("Workpackage shall be processed again").isTrue();
		}
	}

	/**
	 * Aim of this test is to make sure that everything works ok even if we issue {@link ITrx#commit(boolean)} multiple times.
	 */
	@Test
	public void test_markReadyForProcessingAfterTrxCommit_MultipleCommits() throws Exception
	{
		setupQueueProcessor(5);
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 1, false);
		final I_C_Queue_WorkPackage workpackage0 = workpackages.get(0);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setRuntimeException(workpackage0, "Fail test");

		final String trxName = Services.get(ITrxManager.class).createTrxName("TestTrx", true);
		final ITrx trx = Services.get(ITrxManager.class).get(trxName, false);
		trx.start(); // make sure it's started, if not it will fail on commit/rollback

		//
		// Enqueue the workpackage using a callback
		final Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAfterTrxCommit(workpackage0, trxName);

		// Execute first commit
		trx.commit(true);
		trx.start(); // make sure it's started, if not it will fail on commit/rollback

		// Get result
		final IWorkpackageProcessorExecutionResult result = futureResult.get(1, TimeUnit.MINUTES);
		assertThat(result).as("Result shall be available after second commit").isNotNull();

		//
		// Make sure second commit will fail and execute it
		trx.getTrxListenerManager()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> {
					throw new AdempiereException("Test fail on after commit: " + innerTrx);
				});

		trx.commit(true);

		// Get result
		assertThat(futureResult.isDone()).as("Future result shall be marked as done").isTrue();
		final IWorkpackageProcessorExecutionResult result2 = futureResult.get(1, TimeUnit.MINUTES);
		assertThat(result2).as("Result shall be available after second commit").isNotNull();
	}

	@Test
	public void test_Callback_AfterCommit_Commit() throws Exception
	{
		// Setup
		setupQueueProcessor(5);
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);

		//
		// Transaction
		final ITrx trx = Services.get(ITrxManager.class).get("TestCallback", true);
		trx.start(); // make sure it's started, if not it will fail on commit/rollback

		//
		// Create and enqueue the workpackage
		final I_C_Queue_WorkPackage workpackage0 = helper.createAndEnqueueWorkpackages(workpackageQueue, 1, false).get(0); // count=1, readyForProcessing=false

		//
		// Mark ready for processing after transaction is committed
		final Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAfterTrxCommit(workpackage0, trx.getTrxName());
		assertThat(workpackage0.isReadyForProcessing()).as("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet").isFalse();
		assertThat(workpackage0.isProcessed()).as("Workpackage " + workpackage0 + " shall NOT be processed").isFalse();
		assertThat(futureResult.isDone()).as("Result " + futureResult + " shall not be available yet").isFalse();

		//
		// Commit the transaction
		trx.commit(true);

		//
		// Wait for workpackage to be processed
		{
			final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();
			helper.waitUntilSize(processedWorkpackages, 1, 1 * 60 * 1000);
		}

		//
		// Validate the result
		{
			assertThat(futureResult.isDone()).as("Result " + futureResult + " shall be available").isTrue();
			assertThat(futureResult.isCancelled()).as("Result " + futureResult + " shall not be canceled").isFalse();

			final IWorkpackageProcessorExecutionResult result = futureResult.get();
			assertThat(result).as("Inner result of " + futureResult + " shall be available").isNotNull();

			InterfaceWrapperHelper.refresh(workpackage0);
			assertThat(workpackage0.isProcessed()).as("Workpackage " + workpackage0 + " shall be processed").isTrue();
		}
	}

	@Test
	public void test_Callback_AfterCommit_Rollback() throws Exception
	{
		// Setup
		setupQueueProcessor(5);
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);

		//
		// Transaction
		final ITrx trx = Services.get(ITrxManager.class).get("TestCallback", true);
		trx.start(); // make sure it's started, if not it will fail on commit/rollback

		//
		// Create and enque the workpackage
		final I_C_Queue_WorkPackage workpackage0 = helper.createAndEnqueueWorkpackages(workpackageQueue, 1, false).get(0); // readyForProcessing=false

		//
		// Mark ready for processing after transaction is commited
		final Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAfterTrxCommit(workpackage0, trx.getTrxName());
		assertThat(workpackage0.isReadyForProcessing()).as("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet").isFalse();

		assertThat(workpackage0.isProcessed()).as("Workpackage " + workpackage0 + " shall NOT be processed").isFalse();
		assertThat(futureResult.isDone()).as("Result " + futureResult + " shall not be available yet").isFalse();

		//
		// Rollback the transaction
		trx.rollback(true); // throwException=true

		//
		// Validate the result
		{
			assertThat(futureResult.isDone()).as("Result " + futureResult + " shall be available").isTrue();
			// Assert.assertEquals("Result " + futureResult + " shall be canceled", true, futureResult.isCancelled());

			InterfaceWrapperHelper.refresh(workpackage0);
			assertThat(workpackage0.isReadyForProcessing()).as("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet").isFalse();
			assertThat(workpackage0.isProcessed()).as("Workpackage " + workpackage0 + " shall NOT be processed").isFalse();

			// Expect following to throw CancellationException
			assertThatThrownBy(() -> futureResult.get()).isInstanceOf(CancellationException.class);
		}
	}
}
