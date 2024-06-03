package de.metas.async.processor;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.util.ISingletonService;

public interface IQueueProcessorFactory extends ISingletonService
{
	IQueueProcessor createAsynchronousQueueProcessor(I_C_Queue_Processor config, IWorkPackageQueue queue);

	IQueueProcessorEventDispatcher getQueueProcessorEventDispatcher();

	IQueueProcessor createAsynchronousQueueProcessor(QueuePackageProcessorId packageProcessorId);
}
