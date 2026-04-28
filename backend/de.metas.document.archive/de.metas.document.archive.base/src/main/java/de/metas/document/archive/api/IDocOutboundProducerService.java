package de.metas.document.archive.api;

import de.metas.document.archive.spi.IDocOutboundProducer;
import de.metas.util.ISingletonService;
import org.adempiere.ad.table.api.AdTableId;

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
	 * <p>
	 * NOTE: if a different {@link IDocOutboundProducer} is already registered with the same tableId nothing will be done
	 */
	void registerProducer(IDocOutboundProducer producer);

	/**
	 * Unregisters the {@link IDocOutboundProducer} which was previously registered for given <code>config</code>.
	 * <p>
	 * If no producer was registered, this method does nothing.
	 */
	void unregisterProducerByTableId(AdTableId tableId);

	/**
	 * Creates the document outbound for given <code>model</code> by picking the right {@link IDocOutboundProducer} and delegating the work to it.
	 */
	void createDocOutbound(Object model);
}
