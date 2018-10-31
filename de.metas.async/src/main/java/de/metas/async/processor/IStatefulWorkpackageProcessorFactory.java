package de.metas.async.processor;

import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.IMultitonService;

public interface IStatefulWorkpackageProcessorFactory extends IWorkpackageProcessorFactory, IMultitonService
{

	void registerWorkpackageProcessor(IWorkpackageProcessor packageProcessor);

}
