package de.metas.async.testWorkPackageProcessors;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

public class AsyncBatchTestWorkpackageProcessorChild implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		return Result.SUCCESS;
	}
}