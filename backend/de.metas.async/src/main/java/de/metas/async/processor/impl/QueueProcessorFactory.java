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
	// gh Spring bootstrap: this class is instantiated eagerly (via Services.get(IQueueProcessorFactory.class))
	// from other beans' field initializers, i.e. potentially *during* Spring's own container refresh, before
	// SpringContextHolder is set (see org.compiere.SpringContextHolder / de.metas.StartupListener). Resolving
	// QueueProcessorDescriptorIndex.getInstance() eagerly here throws "SpringApplicationContext not configured
	// yet" whenever this happens to sit on the bean-graph traversal that constructs it. Deferred with the
	// lazy-init pattern (docs/coding-rules/service-injection.md) -- see WorkPackageQueueFactory for the same fix
	// and why plain field-init deferral is used instead of SpringContextHolder.lazyBean(...).
	@javax.annotation.Nullable
	private QueueProcessorDescriptorIndex _queueProcessorDescriptorIndex;

	@NonNull
	private QueueProcessorDescriptorIndex queueProcessorDescriptorIndex()
	{
		QueueProcessorDescriptorIndex result = _queueProcessorDescriptorIndex;
		if (result == null)
		{
			result = _queueProcessorDescriptorIndex = QueueProcessorDescriptorIndex.getInstance();
		}
		return result;
	}

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
		final I_C_Queue_Processor queueProcessorConfig = queueProcessorDescriptorIndex().getQueueProcessor(packageProcessorId);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForPackageProcessing(queueProcessorConfig);

		return createAsynchronousQueueProcessor(queueProcessorConfig, queue);
	}
}
