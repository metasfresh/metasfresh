/**
 * 
 */
package de.metas.async.processor.impl;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Test;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.testWorkPackageProcessors.AsyncBatchTestWorkpackageProcessorChild;
import de.metas.async.testWorkPackageProcessors.AsyncBatchTestWorkpackageProcessorParent;

/**
 * @author cg
 *
 */
public class AsyncBatchTest extends QueueProcessorTestBase
{
	/**
	// services
	private IWorkPackageQueueFactory workPackageQueueFactory;

	private I_C_Queue_Processor processorDef;
	private IQueueProcessorsExecutor processorsExecutor;

	@Override
	protected void beforeTestCustomized()
	{
		workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		processorDef = createQueueProcessor("test",
				5 // poolSize
		);
		assignPackageProcessor(processorDef, AsyncBatchTestWorkpackageProcessorParent.class);
		assignPackageProcessor(processorDef, AsyncBatchTestWorkpackageProcessorChild.class);

		processorsExecutor = new QueueProcessorsExecutor();
		processorsExecutor.addQueueProcessor(processorDef);
	}

	@Override
	protected void afterTestCustomized()
	{
		processorsExecutor.shutdown();
		processorsExecutor = null;
		processorDef = null;
	}


	@Test
	public void test01() throws InterruptedException
	{
		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(asyncBatch);

		final IWorkPackageQueue workpackageQueue = workPackageQueueFactory.getQueueForEnqueuing(ctx, AsyncBatchTestWorkpackageProcessorParent.class);

		Services.get(IWorkpackageProcessorContextFactory.class).setThreadInheritedAsyncBatch(asyncBatch);

		final List<I_C_Queue_WorkPackage> workpackages = createAndEnqueueWorkpackages(workpackageQueue, 10, false);
		assertAsyncBatch(asyncBatch, workpackages);

		markReadyForProcessing(workpackages);

		//
		// Wait until all packages are processed
		final List<I_C_Queue_WorkPackage> workpackagesEnqueued = retriveProcessedWorkpackages(10 * 2);

		//
		// Make sure workpackage's C_AsyncBatch_ID was set
		assertAsyncBatch(asyncBatch, workpackagesEnqueued);
	}

	private void assertAsyncBatch(final I_C_Async_Batch asyncBatchExpected, final List<I_C_Queue_WorkPackage> workpackages)
	{
		for (final I_C_Queue_WorkPackage workpackage : workpackages)
		{

			final String message = "Invalid batch id for " + workpackage
					+ "\n Expected Async Batch: " + asyncBatchExpected
					+ "\n Workpackage processor: " + workpackage.getC_Queue_Block().getC_Queue_PackageProcessor().getClassname();

			Assert.assertEquals(message,
					asyncBatchExpected.getC_Async_Batch_ID(), // Expected
					workpackage.getC_Async_Batch_ID() // Actual
			);
		}
	}

	private final List<I_C_Queue_WorkPackage> retriveProcessedWorkpackages(final int waitUntilSize)
	{
		List<I_C_Queue_WorkPackage> workpackagesEnqueued = null;
		for (int trialNo = 1; trialNo <= 100; trialNo++)
		{
			workpackagesEnqueued = POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class, new IPOJOFilter<I_C_Queue_WorkPackage>()
			{

				@Override
				public boolean accept(I_C_Queue_WorkPackage pojo)
				{
					return pojo.isProcessed();
				}
			});
			if (workpackagesEnqueued.size() != 10 * 2)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException("Interrupted", e);
				}
				continue;
			}
			break;
		}
		if (workpackagesEnqueued == null || workpackagesEnqueued.size() != 10 * 2)
		{
			throw new IllegalStateException("Workpackages were not all processed: " + workpackagesEnqueued);
		}

		return workpackagesEnqueued;
	}
	
	*/
}
