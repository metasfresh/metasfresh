package de.metas.payment.sepa.api;

import org.compiere.model.I_C_PaySelection;

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.util.ISingletonService;

/**
 * Used for custom payment operations (like SEPA export).
 * 
 * @author ad
 * 
 */
public interface IPaymentDAO extends ISingletonService
{
	/**
	 * Retrieves the SEPA_Export that corresponds to the pay selection. Throws an error if there are more than one unprocessed.
	 * 
	 * @param paySelection
	 * @return
	 */
	I_SEPA_Export retrieveForPaySelection(I_C_PaySelection paySelection);

}
