package de.metas.async.processor.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor2;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.util.Check;

public final class WorkpackageProcessor2Wrapper extends WorkpackageProcessorAdapter implements IWorkpackageProcessor2
{
	public static final IWorkpackageProcessor2 wrapIfNeeded(final IWorkpackageProcessor workpackageProcessor)
	{
		if (workpackageProcessor instanceof IWorkpackageProcessor2)
		{
			return (IWorkpackageProcessor2)workpackageProcessor;
		}
		else
		{
			return new WorkpackageProcessor2Wrapper(workpackageProcessor);
		}
	}
	private final IWorkpackageProcessor delegate;

	private WorkpackageProcessor2Wrapper(final IWorkpackageProcessor delegate)
	{
		super();
		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
	}

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		return delegate.processWorkPackage(workpackage, localTrxName);
	}
}
