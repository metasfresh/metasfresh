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
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor2;
import de.metas.async.spi.WorkpackageProcessorAdapter;

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
