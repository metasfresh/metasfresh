/**
 * 
 */
package de.metas.payment.esr.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRLineMatchHandler;

/**
 * @author cg
 *
 */
public interface IESRLineMatcherHandlersService extends ISingletonService
{
	/**
	 * register listeners for matching fields in ESR lines
	 * 
	 * @param l
	 */
	void registerESRLineListener(IESRLineMatchHandler l);

	/**
	 * apply the esr matching listeners for Bpartner of the invoice
	 */
	boolean applyESRMatchingBPartnerOfTheInvoice(I_C_Invoice invoice, I_ESR_ImportLine esrLine);

	/**
	 * apply the esr matching listeners
	 */
	boolean applyESRMatchingBPartner(I_C_BPartner bpartner, I_ESR_ImportLine esrLine);
}
