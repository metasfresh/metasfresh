package de.metas.async.processor.impl;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository;
import de.metas.async.processor.descriptor.model.QueueProcessorDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class QueueProcessorFactory implements IQueueProcessorFactory
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	private IWorkpackageLogsRepository getLogsRepository()
	{
		return SpringContextHolder.instance.getBean(IWorkpackageLogsRepository.class);
	}

	@Override
	public IQueueProcessor createAsynchronousQueueProcessor(@NonNull final QueueProcessorDescriptor config, final IWorkPackageQueue queue)
	{
		final IWorkpackageLogsRepository logsRepository = getLogsRepository();
		return new ThreadPoolQueueProcessor(config, queue, logsRepository);
	}

	private IQueueProcessorEventDispatcher queueProcessorEventDispatcher = new DefaultQueueProcessorEventDispatcher();

	@Override
	public IQueueProcessorEventDispatcher getQueueProcessorEventDispatcher()
	{
		return queueProcessorEventDispatcher;
	}

	@Override
	public IQueueProcessor createAsynchronousQueueProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final QueueProcessorDescriptor queueProcessorConfig = QueueProcessorDescriptorRepository.getInstance().getQueueProcessor(packageProcessorId);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForPackageProcessing(queueProcessorConfig);

		return createAsynchronousQueueProcessor(queueProcessorConfig, queue);
	}
}
