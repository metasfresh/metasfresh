package de.metas.async.processor.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackageProcessor;

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
