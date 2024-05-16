package de.metas.async.processor.impl;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class QueueProcessorFactory implements IQueueProcessorFactory
{
	private final QueueProcessorDescriptorIndex queueProcessorDescriptorIndex = QueueProcessorDescriptorIndex.getInstance();
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	private IWorkpackageLogsRepository getLogsRepository()
	{
		return SpringContextHolder.instance.getBean(IWorkpackageLogsRepository.class);
	}

	@Override
	public IQueueProcessor createAsynchronousQueueProcessor(final I_C_Queue_Processor config, final IWorkPackageQueue queue)
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
		final I_C_Queue_Processor queueProcessorConfig = queueProcessorDescriptorIndex.getQueueProcessor(packageProcessorId);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForPackageProcessing(queueProcessorConfig);

		return createAsynchronousQueueProcessor(queueProcessorConfig, queue);
	}
}
