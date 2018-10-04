package de.metas.async.processor.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Check;

/**
 * Default immutable implementation of {@link IWorkpackageProcessorExecutionResult}
 * 
 * @author tsa
 * 
 */
public class WorkpackageProcessorExecutionResult implements IWorkpackageProcessorExecutionResult
{
	private final I_C_Queue_WorkPackage workpackage;
	private final IWorkpackageProcessor workpackageProcessor;

	public WorkpackageProcessorExecutionResult(I_C_Queue_WorkPackage workpackage, IWorkpackageProcessor workpackageProcessor)
	{
		super();

		Check.assumeNotNull(workpackage, "workpackage not null");
		this.workpackage = workpackage;

		Check.assumeNotNull(workpackageProcessor, "workpackageProcessor not null");
		this.workpackageProcessor = workpackageProcessor;
	}

	@Override
	public I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		return workpackage;
	}

	@Override
	public IWorkpackageProcessor getWorkpackageProcessor()
	{
		return workpackageProcessor;
	}

}
