package de.metas.document.archive.spi;

import de.metas.document.archive.api.IDocOutboundProducerService;
import org.compiere.model.I_C_Doc_Outbound_Config;

/**
 * Implementation of this interface is responsible for producing document outbound from given models.
 *
 * @author tsa
 */
public interface IDocOutboundProducer
{

	I_C_Doc_Outbound_Config getC_Doc_Outbound_Config();

	/**
	 * Called by API when this producer is registered to an service.
	 * <p>
	 * NOTE: never call it directly
	 */
	void init(IDocOutboundProducerService producerService);

	/**
	 * Called by API when this producer is unregistered from service.
	 * <p>
	 * NOTE: never call it directly
	 */
	void destroy(IDocOutboundProducerService producerService);

	/**
	 * Checks if given model is handled by this producer
	 *
	 * @return true if the model is handled by this producer
	 */
	boolean accept(Object model);

	/**
	 * Creates the document outbound for given model.
	 * <p>
	 * NOTE: it is assumed that the API already asked this producer if the model is accepted (see {@link #accept(Object)}).
	 */
	void createDocOutbound(Object model);

	/**
	 * Process the document outbound for given model - the printing queue more specific
	 * <p>
	 * NOTE: it is assumed that the API already asked this producer if the model is accepted (see {@link #accept(Object)}).
	 */
	void voidDocOutbound(Object model);
}
