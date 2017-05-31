package de.metas.async.testWorkPackageProcessors;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.impl.ConstantWorkpackagePrio;

public class AsyncBatchTestWorkpackageProcessorParent implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, AsyncBatchTestWorkpackageProcessorChild.class);

		final I_C_Queue_Block block = queue.enqueueBlock(ctx);
		final I_C_Queue_WorkPackage workpackageChild = queue.enqueueWorkPackage(block, ConstantWorkpackagePrio.medium());
		queue.markReadyForProcessing(workpackageChild);

		return Result.SUCCESS;
	}
}