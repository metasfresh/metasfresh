package de.metas.async.processor.impl;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.impl.planner.SynchronousProcessorPlanner;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.lock.api.ILockManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.reflect.TestingClassInstanceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SynchronousQueueProcessorTest extends QueueProcessorTestBase
{
	@Test
	public void test01()
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);

		final I_C_Queue_Processor queueProcessor = helper.createQueueProcessor(StaticMockedWorkpackageProcessor.class.getName(), 1, 1000);

		helper.assignPackageProcessor(queueProcessor, packageProcessorDef);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 20, true); // markReadyForProcessing=true

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor
				.setDefaultResult(Result.SUCCESS)
				.setRuntimeException(workpackages.get(5), "test error");

		workpackages.get(7).setPriority(X_C_Queue_WorkPackage.PRIORITY_Urgent);
		InterfaceWrapperHelper.save(workpackages.get(7));

		processWorkpackages(StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();
		Assertions.assertEquals(
				workpackages.size(), processedWorkpackages.size(), "Processed workpackages list shall have same size as initial workpackages list");

		InterfaceWrapperHelper.refresh(workpackages.get(7));
		Assertions.assertEquals(workpackages.get(7), processedWorkpackages.get(0), "Priority 1 packages shall be processed first");

		for (final I_C_Queue_WorkPackage wp : processedWorkpackages)
		{
			final RuntimeException rteExpected = workpackageProcessor.getRuntimeExceptionFor(wp);
			if (rteExpected != null)
			{
				// Exception
				assertThat(wp.getErrorMsg()).as("Workpackage - Invalid ErrorMsg: %s", wp).startsWith(rteExpected.getMessage());
				Assertions.assertFalse(wp.isProcessed(), "Workpackage - Invalid Processed: " + wp);
				Assertions.assertTrue(wp.isError(), "Workpackage - Invalid IsError: " + wp);
			}
			else
			{
				Assertions.assertTrue(wp.isProcessed(), "Workpackage - Invalid Processed: " + wp);
				Assertions.assertFalse(wp.isError(), "Workpackage - Invalid IsError: " + wp);
			}
		}

		helper.assertNothingLocked();
	}

	protected void processWorkpackages(final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);
		final IQueueProcessor processor = newSynchronousQueueProcessor(workpackageQueue);

		SynchronousProcessorPlanner.executeNow(processor);
	}

	/**
	 * Class for test_WorkpackageProcessorClassNotFound().
	 */
	public static class MissingWorkPackageProcessor implements IWorkpackageProcessor
	{
		@Override
		public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
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
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor("Test_test_WorkpackageProcessorClassNotFound", 10, 1000);

		// create a package processor with a wrong class name.
		final I_C_Queue_PackageProcessor packageProcessor1 = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);
		helper.assignPackageProcessor(queueProcessorDef, packageProcessor1);

		//
		// Make sure our bad workpackage processor wasn't blacklisted yet
		final IWorkpackageProcessorFactory workpackageProcessorFactory = Services.get(IWorkpackageProcessorFactory.class);
		Assertions.assertFalse(workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()), "Package processor " + packageProcessor1 + " shall not be blacklisted yet");

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
			final IQueueProcessor processor = newSynchronousQueueProcessor(workpackageQueueForProcessing);
			processor.setWorkpackageProcessorFactory(workpackageProcessorFactory);

			SynchronousProcessorPlanner.executeNow(processor);
		}

		//
		// Now our processor shall be black listed
		Assertions.assertTrue(
				workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()),
				"Package processor " + packageProcessor1 + " shall be blacklisted");

		//
		// Make sure all packages are not processed, not flagged as Error
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{
			InterfaceWrapperHelper.refresh(workpackage);
			Assertions.assertFalse(workpackage.isError(), "Workpackage " + workpackage + " - Invalid IsError");
			Assertions.assertFalse(workpackage.isProcessed(), "Workpackage " + workpackage + " - Invalid Processed");
			Assertions.assertFalse(Services.get(ILockManager.class).isLocked(workpackage), "Workpackage " + workpackage + " - Shall not be locked");
		}

		//
		// Fixing our bad configuration and try to reprocess again... shall work
		TestingClassInstanceProvider.instance.clearExceptionsForClassNames();

		// Validate the processor. If valid (i.e. no exceptions will be thrown), the processor will be removed from blacklist
		workpackageProcessorFactory.validateWorkpackageProcessor(packageProcessor1);
		Assertions.assertFalse(workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessor1.getC_Queue_PackageProcessor_ID()), "Package processor " + packageProcessor1 + " shall not be blacklisted anymore");

		//
		// Create processor and run
		{
			final IQueueProcessor processor = newSynchronousQueueProcessor(workpackageQueueForProcessing);
			processor.setWorkpackageProcessorFactory(workpackageProcessorFactory);

			SynchronousProcessorPlanner.executeNow(processor);
		}

		//
		// Make sure all packages are processed
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{
			InterfaceWrapperHelper.refresh(workpackage);
			Assertions.assertFalse(workpackage.isError(), "Workpackage " + workpackage + " - Invalid IsError");
			Assertions.assertTrue(workpackage.isProcessed(), "Workpackage " + workpackage + " - Invalid Processed");
			Assertions.assertFalse(Services.get(ILockManager.class).isLocked(workpackage), "Workpackage " + workpackage + " - Shall not be locked");
		}

	}

	private SynchronousQueueProcessor newSynchronousQueueProcessor(final IWorkPackageQueue workpackageQueueForProcessing)
	{
		return new SynchronousQueueProcessor(workpackageQueueForProcessing, NOPWorkpackageLogsRepository.instance);
	}
}
