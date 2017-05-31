package de.metas.async.testWorkPackageProcessors;

import de.metas.async.AsyncTestTools;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Processor's {@link #processWorkPackage(I_C_Queue_WorkPackage, String)} will sleep half a second. Used in combination with {@link ShortRunningWorkPackageProcessor}.
 * 
 * @author ts
 *
 */
public class LongRunningWorkPackageProcessor implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		AsyncTestTools.sleep(500);
		return Result.SUCCESS;
	}
}