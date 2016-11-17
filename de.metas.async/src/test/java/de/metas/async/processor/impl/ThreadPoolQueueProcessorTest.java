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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Test;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackageProcessor.Result;

public class ThreadPoolQueueProcessorTest extends QueueProcessorTestBase
{
	private I_C_Queue_Processor processorDef;
	private IQueueProcessorsExecutor processorsExecutor;

	@Override
	protected void beforeTestCustomized()
	{
		// CLogMgt.setLevel(Level.FINE);

		processorDef = helper.createQueueProcessor("test",
				5, // poolSize
				10, // maxPoolSize
				1000 // keepAliveTimeMillis
				);
		helper.assignPackageProcessor(processorDef, StaticMockedWorkpackageProcessor.class);

		processorsExecutor = new QueueProcessorsExecutor();
		processorsExecutor.addQueueProcessor(processorDef);
	}

	@Override
	protected void afterTestCustomized()
	{
		logger.info(StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor().getStatisticsInfo());

		processorsExecutor.shutdown();
		processorsExecutor = null;
		processorDef = null;
	}

	@Test
	public void test_Simple_100workpackages() throws Exception
	{
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 100, false);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor
				.setDefaultResult(Result.SUCCESS)
				.setException(workpackages.get(5), "test error")
		// .setResult(workpackages.get(7), Result.SKIPPED)
		;
		workpackages.get(7).setPriority(X_C_Queue_WorkPackage.PRIORITY_Urgent);

		helper.markReadyForProcessing(workpackages);

		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();

		helper.waitUntilSize(processedWorkpackages, workpackages.size(), 0 * 1000); // wait max 5sec

		Assert.assertEquals("Processed workpackages list shall have same size as initial workpackages list",
				workpackages.size(), processedWorkpackages.size());

		// because we process in parallel it's impossible to make sure first package will be prio1
		// Assert.assertEquals("Priority 1 packages shall be processed first", workpackages.get(7), processedWorkpackages.get(0));

		for (final I_C_Queue_WorkPackage wp : processedWorkpackages)
		{
			final String errorExpected = workpackageProcessor.getException(wp);
			if (errorExpected != null)
			{
				// Exception
				Assert.assertEquals("Workpackage - Invalid ErrorMsg: " + wp, errorExpected, wp.getErrorMsg());
				Assert.assertEquals("Workpackage - Invalid Processed: " + wp, false, wp.isProcessed());
				Assert.assertEquals("Workpackage - Invalid IsError: " + wp, true, wp.isError());
			}
			else
			{
				Assert.assertEquals("Workpackage - Invalid Processed: " + wp, true, wp.isProcessed());
				Assert.assertEquals("Workpackage - Invalid IsError: " + wp, false, wp.isError());
			}
		}

		helper.assertNothingLocked();
	}

	@Test
	public void test_Callback() throws Exception
	{
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
			Assert.assertEquals("Processed package shall be the same as the one enqueued", workpackage0, processedWorkpackage0);
			Assert.assertEquals("Callback shall be called for our workpackage", processedWorkpackage0, result.getC_Queue_WorkPackage());
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
			Assert.assertEquals("Callback shall be called for our workpackage", workpackage0, result.getC_Queue_WorkPackage());
			Assert.assertEquals("Workpackage shall be processed again", true, workpackage0.isProcessed());
		}
	}

	/**
	 * Aim of this test is to make sure that everything works ok even if we issue {@link ITrx#commit(boolean)} multiple times.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_markReadyForProcessingAfterTrxCommit_MultipleCommits() throws Exception
	{
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 1, false);
		final I_C_Queue_WorkPackage workpackage0 = workpackages.get(0);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setException(workpackage0, "Fail test");

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
		Assert.assertNotNull("Result shall be available after second commit", result);

		//
		// Make sure second commit will fail and execute it
		trx.getTrxListenerManager().registerListener(new TrxListenerAdapter()
		{
			@Override
			public void afterCommit(final ITrx trx)
			{
				throw new AdempiereException("Test fail on after commit: " + trx);
			}
		});
		trx.commit(true);

		// Get result
		Assert.assertTrue("Future result shall be marked as done", futureResult.isDone());
		final IWorkpackageProcessorExecutionResult result2 = futureResult.get(1, TimeUnit.MINUTES);
		Assert.assertNotNull("Result shall be available after second commit", result2);
	}

	@Test
	public void test_Callback_AfterCommit_Commit() throws Exception
	{
		//
		// Setup
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
		Assert.assertFalse("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet", workpackage0.isReadyForProcessing());
		Assert.assertFalse("Workpackage " + workpackage0 + " shall NOT be processed", workpackage0.isProcessed());
		Assert.assertEquals("Result " + futureResult + " shall not be available yet", false, futureResult.isDone());

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
			Assert.assertEquals("Result " + futureResult + " shall be available", true, futureResult.isDone());
			Assert.assertEquals("Result " + futureResult + " shall not be canceled", false, futureResult.isCancelled());

			final IWorkpackageProcessorExecutionResult result = futureResult.get();
			Assert.assertNotNull("Inner result of " + futureResult + " shall be available", result);

			InterfaceWrapperHelper.refresh(workpackage0);
			Assert.assertTrue("Workpackage " + workpackage0 + " shall be processed", workpackage0.isProcessed());
		}
	}

	@Test(expected = CancellationException.class)
	public void test_Callback_AfterCommit_Rollback() throws Exception
	{
		//
		// Setup
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
		Assert.assertFalse("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet", workpackage0.isReadyForProcessing());
		Assert.assertFalse("Workpackage " + workpackage0 + " shall NOT be processed", workpackage0.isProcessed());
		Assert.assertEquals("Result " + futureResult + " shall not be available yet", false, futureResult.isDone());

		//
		// Rollback the transaction
		trx.rollback(true); // throwException=true

		//
		// Validate the result
		{
			Assert.assertEquals("Result " + futureResult + " shall be available", true, futureResult.isDone());
			// Assert.assertEquals("Result " + futureResult + " shall be canceled", true, futureResult.isCancelled());

			InterfaceWrapperHelper.refresh(workpackage0);
			Assert.assertFalse("Workpackage " + workpackage0 + " shall not be marked ready for processing because transaction is not commited yet", workpackage0.isReadyForProcessing());
			Assert.assertFalse("Workpackage " + workpackage0 + " shall NOT be processed", workpackage0.isProcessed());

			// Expect following to throw CancellationException
			futureResult.get();
		}
	}
}
