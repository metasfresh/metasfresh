package de.metas.async.api.impl;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IWorkpackageProcessorContextFactory;

public class WorkpackageProcessorContextFactory implements IWorkpackageProcessorContextFactory
{

	private final InheritableThreadLocal<AsyncBatchId> threadLocalAsyncBatch = new InheritableThreadLocal<>();

	private final InheritableThreadLocal<AsyncBatchId> threadLocalWorkpackageAsyncBatch = new InheritableThreadLocal<>();

	private final InheritableThreadLocal<String> threadLocalPriority = new InheritableThreadLocal<>();

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


	@Override
	public AsyncBatchId setThreadInheritedWorkpackageAsyncBatch(final AsyncBatchId asyncBatchId)
	{
		final AsyncBatchId asyncBatchIdOld = threadLocalWorkpackageAsyncBatch.get();
		threadLocalWorkpackageAsyncBatch.set(asyncBatchId);
		return asyncBatchIdOld;
	}

	@Override
	public AsyncBatchId getThreadInheritedWorkpackageAsyncBatch()
	{
		return threadLocalWorkpackageAsyncBatch.get();
	}

}
