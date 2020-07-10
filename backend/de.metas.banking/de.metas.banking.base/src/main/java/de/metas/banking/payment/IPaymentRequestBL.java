package de.metas.banking.payment;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.model.I_C_Payment_Request;
import de.metas.util.ISingletonService;

/**
 * Service for {@link I_C_Payment_Request} operations
 *
 * @author al
 */
public interface IPaymentRequestBL extends ISingletonService
{
	/**
	 * Use given request to fill a new payment request
	 *
	 * @param request
	 * @return newly created payment request
	 */
	I_C_Payment_Request createNewFromTemplate(I_C_Payment_Request request);

	/**
	 * Checks if methods like {@link #updatePaySelectionLineFromPaymentRequestIfExists(I_C_PaySelectionLine)} were already executed on given pay selection line.
	 *
	 * @param paySelectionLine
	 * @return
	 */
	boolean isUpdatedFromPaymentRequest(I_C_PaySelectionLine paySelectionLine);

	/**
	 * Updates {@link I_C_PaySelectionLine} from {@link I_C_Payment_Request} linked to pay selection line's invoice.<br>
	 * Updates
	 * <ul><li><code>PayAmt</code> (but not more than the invoice's open amount, task 09698)
	 * <li><code>DifferenceAmt</code>
	 * <li><code>C_BP_BankAccount_ID</code>
	 * </li><code>Reference</code>
	 * </ul>
	 *
	 * @param paySelectionLine
	 * @return true if payment request was found and pay selection line was updated
	 */
	boolean updatePaySelectionLineFromPaymentRequestIfExists(I_C_PaySelectionLine paySelectionLine);

	/**
	 * Creates a new {@link I_C_Payment_Request} for given <code>invoice</code>, optionally using given payment request template
	 *
	 * @param invoice
	 * @param paymentRequestTemplate payment request template (optional)
	 * @return newly created payment request (saved); never returns <code>null</code>
	 */
	I_C_Payment_Request createPaymentRequest(I_C_Invoice invoice, I_C_Payment_Request paymentRequestTemplate);

}
