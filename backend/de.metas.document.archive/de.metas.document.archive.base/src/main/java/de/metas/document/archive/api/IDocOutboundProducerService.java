package de.metas.document.archive.api;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.spi.IDocOutboundProducer;
import de.metas.util.ISingletonService;

/**
 * Service responsible with managing registered {@link IDocOutboundProducer}s and picking the right one in order to perform document outbound actions.
 * 
 * @author tsa
 * 
 */
public interface IDocOutboundProducerService extends ISingletonService
{
	/**
	 * Registers the given document outbound producer.
	 * 
	 * NOTE: if a different {@link IDocOutboundProducer} is already registered for {@link IDocOutboundProducer#getC_Doc_Outbound_Config()} then the old one will be unregistered first
	 */
	void registerProducer(IDocOutboundProducer producer);

	/**
	 * Unregisters the {@link IDocOutboundProducer} which was previously registered for given <code>config</code>.
	 * 
	 * If no producer was registered, this method does nothing.
	 */
	void unregisterProducerByConfig(I_C_Doc_Outbound_Config config);

	/**
	 * Creates the document outbound for given <code>model</code> by picking the right {@link IDocOutboundProducer} and delegating the work to it.
	 */
	void createDocOutbound(Object model);
}
