package de.metas.async.testWorkPackageProcessors;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * This processor's {@link #processWorkPackage(de.metas.async.model.I_C_Queue_WorkPackage, String)} method will return without delay. Used in combination with {@link LongRunningWorkPackageProcessor}.
 * 
 * @author ts
 *
 */
public class ShortRunningWorkPackageProcessor implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		return Result.SUCCESS;
	}
}