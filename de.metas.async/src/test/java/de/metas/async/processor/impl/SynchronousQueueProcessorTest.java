package de.metas.async.processor.impl;

import static org.assertj.core.api.Assertions.assertThat;

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

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.reflect.TestingClassInstanceProvider;
import org.junit.Assert;
import org.junit.Test;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.lock.api.ILockManager;

public class SynchronousQueueProcessorTest extends QueueProcessorTestBase
{
	@Test
	public void test01() throws InterruptedException
	{
		helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 20, true); // markReadyForProcessing=true

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor
				.setDefaultResult(Result.SUCCESS)
				.setException(workpackages.get(5), "test error");

		workpackages.get(7).setPriority(X_C_Queue_WorkPackage.PRIORITY_Urgent);
		InterfaceWrapperHelper.save(workpackages.get(7));

		processWorkpackages(StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();
		Assert.assertEquals("Processed workpackages list shall have same size as initial workpackages list",
				workpackages.size(), processedWorkpackages.size());

		InterfaceWrapperHelper.refresh(workpackages.get(7));
		Assert.assertEquals("Priority 1 packages shall be processed first", workpackages.get(7), processedWorkpackages.get(0));

		for (I_C_Queue_WorkPackage wp : processedWorkpackages)
		{
			final String errorExpected = workpackageProcessor.getException(wp);
			if (errorExpected != null)
			{
				// Exception
				assertThat(wp.getErrorMsg()).as("Workpackage - Invalid ErrorMsg: %s", wp).startsWith(errorExpected);
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

	protected void processWorkpackages(final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);
		final IQueueProcessor processor = new SynchronousQueueProcessor(workpackageQueue);
		processor.run();
		processor.shutdown();
	}

	/**
	 * Class for test_WorkpackageProcessorClassNotFound().
	 * 
	 */
	public static class MissingWorkPackageProcessor implements IWorkpackageProcessor
	{
		@Override
		public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
		{
			return null; // no BL needed here.
		}
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/04298_Async_shall_handle_correctly_workpackage_processor_instantionation_failure_%282013052310000119%29
	 */
	@Test
	public void test_WorkpackageProcessorClassNotFound()
	{
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		//
		// Setup Queue Processor and Work Package processors
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor("Test_" + testName.getMethodName(), 10, 10, 1000);

		// create a package processor with a wrong class name.
		final I_C_Queue_PackageProcessor packageProcessor1 = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);
		helper.assignPackageProcessor(queueProcessorDef, packageProcessor1);

		//
		// Make sure our bad workpackage processor wasn't blacklisted yet
		final IWorkpackageProcessorFactory workpackageProcessorFactory = Services.get(IWorkpackageProcessorFactory.class);
		Assert.assertFalse("Package processor " + packageProcessor1 + " shall not be blacklisted yet",
				workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()));

		//
		// Create Queue, and fill it with some dummy items
		final IWorkPackageQueue workpackageQueueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);
		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueueForEnqueuing, 20, true); // markReadyForProcessing=true

		// emulate that the class can't be found
		TestingClassInstanceProvider.instance.throwExceptionForClassName(
				StaticMockedWorkpackageProcessor.class.getName(),
				new ClassNotFoundException("unit test method test_WorkpackageProcessorClassNotFound("));
		final IWorkPackageQueue workpackageQueueForProcessing = workPackageQueueFactory.getQueueForPackageProcessing(queueProcessorDef);

		//
		// Create processor and run
		{
			final IQueueProcessor processor = new SynchronousQueueProcessor(workpackageQueueForProcessing);
			processor.setWorkpackageProcessorFactory(workpackageProcessorFactory);
			processor.run();
			processor.shutdown();
		}

		//
		// Now our processor shall be black listed
		Assert.assertTrue("Package processor " + packageProcessor1 + " shall be blacklisted",
				workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()));

		//
		// Make sure all packages are not processed, not flagged as Error
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{
			InterfaceWrapperHelper.refresh(workpackage);
			Assert.assertEquals("Workpackage " + workpackage + " - Invalid IsError", false, workpackage.isError());
			Assert.assertEquals("Workpackage " + workpackage + " - Invalid Processed", false, workpackage.isProcessed());
			Assert.assertEquals("Workpackage " + workpackage + " - Shall not be locked", false, Services.get(ILockManager.class).isLocked(workpackage));
		}

		//
		// Fixing our bad configuration and try to reprocess again... shall work
		TestingClassInstanceProvider.instance.clearExceptionsForClassNames();

		// Validate the processor. If valid (i.e. no exceptions will be thrown), the processor will be removed from blacklist
		workpackageProcessorFactory.validateWorkpackageProcessor(packageProcessor1);
		Assert.assertFalse("Package processor " + packageProcessor1 + " shall not be blacklisted anymore",
				workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()));

		//
		// Create processor and run
		{
			final IQueueProcessor processor = new SynchronousQueueProcessor(workpackageQueueForProcessing);
			processor.setWorkpackageProcessorFactory(workpackageProcessorFactory);
			processor.run();
			processor.shutdown();
		}

		//
		// Make sure all packages are processed
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{
			InterfaceWrapperHelper.refresh(workpackage);
			Assert.assertEquals("Workpackage " + workpackage + " - Invalid IsError", false, workpackage.isError());
			Assert.assertEquals("Workpackage " + workpackage + " - Invalid Processed", true, workpackage.isProcessed());
			Assert.assertEquals("Workpackage " + workpackage + " - Shall not be locked", false, Services.get(ILockManager.class).isLocked(workpackage));
		}

	}
}
