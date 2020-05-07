/**
 * 
 */
package de.metas.payment.esr.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRLineHandler;

/**
 * @author cg
 *
 */
public interface IESRLineHandlersService extends ISingletonService
{
	/**
	 * register listeners for matching fields in ESR lines
	 * 
	 * @param l
	 */
	void registerESRLineListener(IESRLineHandler l);

	/**
	 * apply the esr matching listeners for Bpartner of the invoice
	 */
	boolean applyESRMatchingBPartnerOfTheInvoice(I_C_Invoice invoice, I_ESR_ImportLine esrLine);

	/**
	 * Invoke {@link IESRLineHandler#matchBPartner(I_C_BPartner, I_ESR_ImportLine)} on all registered handlers.
	 * 
	 * @return {@code false} if at least one of those handlers returns {@code false}.
	 */
	boolean applyESRMatchingBPartner(I_C_BPartner bpartner, I_ESR_ImportLine esrLine);
}
