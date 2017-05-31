package de.metas.async.testWorkPackageProcessors;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Wraps one instance of {@link MockedWorkpackageProcessor} and always delegate all method calls to that instance.
 * 
 * In this way we ensure that even if we instantiate this class more then one time, a common {@link MockedWorkpackageProcessor} will be shared.
 * 
 * @author tsa
 * 
 */
public class StaticMockedWorkpackageProcessor implements IWorkpackageProcessor
{
	private static MockedWorkpackageProcessor processor = new MockedWorkpackageProcessor();

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		return processor.processWorkPackage(workpackage, localTrxName);
	}

	/**
	 * 
	 * @return underlying {@link MockedWorkpackageProcessor}
	 */
	public static MockedWorkpackageProcessor getMockedWorkpackageProcessor()
	{
		return processor;
	}

	public static void reset()
	{
		processor = new MockedWorkpackageProcessor();
	}
}
