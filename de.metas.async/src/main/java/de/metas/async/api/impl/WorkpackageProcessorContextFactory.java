package de.metas.async.api.impl;

import de.metas.async.AsyncBatchId;

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

public class WorkpackageProcessorContextFactory implements IWorkpackageProcessorContextFactory
{

	private final InheritableThreadLocal<AsyncBatchId> threadLocalAsyncBatch = new InheritableThreadLocal<AsyncBatchId>();

	private final InheritableThreadLocal<String> threadLocalPriority = new InheritableThreadLocal<String>();

	@Override
	public AsyncBatchId setThreadInheritedAsyncBatch(final AsyncBatchId asyncBatchId)
	{
		final AsyncBatchId asyncBatchIdOld = threadLocalAsyncBatch.get();
		threadLocalAsyncBatch.set(asyncBatchId);
		return asyncBatchIdOld;
	}

	@Override
	public AsyncBatchId getThreadInheritedAsyncBatchId()
	{
		final AsyncBatchId asyncBatchId = threadLocalAsyncBatch.get();
		return asyncBatchId;
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
