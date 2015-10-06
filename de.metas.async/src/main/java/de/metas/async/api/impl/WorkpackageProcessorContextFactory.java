package de.metas.async.api.impl;

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


import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.model.I_C_Async_Batch;

public class WorkpackageProcessorContextFactory implements IWorkpackageProcessorContextFactory
{

	private final InheritableThreadLocal<I_C_Async_Batch> threadLocalAsyncBatch = new InheritableThreadLocal<I_C_Async_Batch>();

	private final InheritableThreadLocal<String> threadLocalPriority = new InheritableThreadLocal<String>();

	@Override
	public I_C_Async_Batch setThreadInheritedAsyncBatch(final I_C_Async_Batch asyncBatch)
	{
		final I_C_Async_Batch asyncBatchOld = threadLocalAsyncBatch.get();
		threadLocalAsyncBatch.set(asyncBatch);
		return asyncBatchOld;
	}

	@Override
	public int getThreadInheritedAsyncBatchId()
	{
		final I_C_Async_Batch asyncBatch = threadLocalAsyncBatch.get();
		return asyncBatch == null ? -1 : asyncBatch.getC_Async_Batch_ID();
	}

	@Override
	public I_C_Async_Batch getThreadInheritedAsyncBatch()
	{
		return threadLocalAsyncBatch.get();
	}

	@Override
	public String getThreadInheritedPriority()
	{
		return threadLocalPriority.get();
	}

	@Override
	public void setThreadInheritedPriority(final String priority)
	{
		threadLocalPriority.set(priority);
	}

}
