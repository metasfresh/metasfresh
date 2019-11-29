package de.metas.banking.payment;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.compiere.model.IQuery;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.invoice.InvoiceId;
import de.metas.util.ISingletonService;

/**
 * @author al
 */
public interface IPaySelectionDAO extends ISingletonService
{
	<T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(I_C_PaySelection paySelection, Class<T> clazz);

	int retrievePaySelectionLinesCount(I_C_PaySelection paySelection);

	int retrieveLastPaySelectionLineNo(Properties ctx, int paySelectionId, String trxName);

	List<I_C_PaySelectionLine> retrievePaySelectionLinesMatchingInvoice(I_C_PaySelection paySelection, I_C_Invoice invoice);

	boolean isPaySelectionLineMatchInvoice(I_C_PaySelection paySelection, I_C_Invoice invoice);

	List<de.metas.banking.model.I_C_PaySelectionLine> retrievePaySelectionLines(I_C_BankStatementLine bankStatementLine);

	de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLine(I_C_BankStatementLine_Ref bankStatementLineRef);

	/**
	 * Returns the given invoice's <code>C_PaySelectionLine</code>s
	 * 
	 * @param invoice
	 * @return
	 */
	List<I_C_PaySelectionLine> retrievePaySelectionLines(org.compiere.model.I_C_Invoice invoice);

	/**
	 * @return processed pay selection which contains given payment
	 */
	I_C_PaySelection retrievePaySelection(I_C_Payment payment);

	de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLineForPayment(I_C_PaySelection paySelection, int paymentId);

	IQuery<I_C_PaySelectionLine> queryActivePaySelectionLinesByInvoiceId(Set<InvoiceId> invoiceIds);
}
