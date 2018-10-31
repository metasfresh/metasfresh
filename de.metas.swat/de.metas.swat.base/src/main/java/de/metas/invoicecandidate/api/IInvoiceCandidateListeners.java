package de.metas.invoicecandidate.api;

import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.util.ISingletonService;

/**
 * Invoice Candidates listeners.
 * 
 * Those listeners will be called along the "document" to "invoice candidate" to "invoice" data flow.
 * 
 * @author tsa
 *
 */
public interface IInvoiceCandidateListeners extends ISingletonService, IInvoiceCandidateListener
{
	/**
	 * Register a new listener.
	 * 
	 * @param listener listener to be registered
	 */
	void addListener(final IInvoiceCandidateListener listener);
}
