package de.metas.async.processor;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.descriptor.model.QueueProcessorDescriptor;
import de.metas.util.ISingletonService;

public interface IQueueProcessorFactory extends ISingletonService
{
	IQueueProcessor createAsynchronousQueueProcessor(QueueProcessorDescriptor config, IWorkPackageQueue queue);

	IQueueProcessorEventDispatcher getQueueProcessorEventDispatcher();

	IQueueProcessor createAsynchronousQueueProcessor(QueuePackageProcessorId packageProcessorId);
}
