/**
 * 
 */
package de.metas.payment.esr.spi;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * @author cg
 *
 */
public interface IESRLineHandler
{
	/**
	 * <ul>
	 * checks if the partner of the invoice was previously in the org of import line <bR>
	 * <il> if the partner was in the current org, match <br>
	 * <il> if was not, add error message
	 * </ul>
	 * 
	 * @param invoice
	 * @param esrLine
	 * @return
	 */
	boolean matchBPartnerOfInvoice(I_C_Invoice invoice, I_ESR_ImportLine esrLine);

	/**
	 * <ul>
	 * checks if the partner was previously in the org of import line <bR>
	 * <il> if the partner was in the current org, match <br>
	 * <il> if was not, add error message
	 * </ul>
	 * 
	 * @param invoice
	 * @param esrLine
	 * @return
	 */
	boolean matchBPartner(I_C_BPartner bpartner, I_ESR_ImportLine esrLine);
}
