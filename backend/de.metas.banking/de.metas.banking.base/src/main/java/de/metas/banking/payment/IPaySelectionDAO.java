package de.metas.banking.payment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.compiere.model.IQuery;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.PaySelectionId;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

/**
 * @author al
 */
public interface IPaySelectionDAO extends ISingletonService
{
	Optional<I_C_PaySelection> getById(PaySelectionId paySelectionId);

	List<I_C_PaySelection> getByIds(Set<PaySelectionId> paySelectionIds);

	void save(@NonNull I_C_PaySelection paySelection);

	void save(@NonNull I_C_PaySelectionLine psl);

	List<I_C_PaySelectionLine> retrievePaySelectionLines(I_C_PaySelection paySelection);

	List<I_C_PaySelectionLine> retrievePaySelectionLines(PaySelectionId paySelectionId);

	int retrievePaySelectionLinesCount(I_C_PaySelection paySelection);

	int retrieveLastPaySelectionLineNo(PaySelectionId paySelectionId);

	List<I_C_PaySelectionLine> retrievePaySelectionLinesByBankStatementLineId(BankStatementLineId bankStatementLineId);

	List<I_C_PaySelectionLine> retrievePaySelectionLinesByBankStatementLineIds(Collection<BankStatementLineId> bankStatementLineIds);

	Optional<I_C_PaySelectionLine> retrievePaySelectionLine(BankStatementAndLineAndRefId bankStatementLineAndRefId);

	List<I_C_PaySelectionLine> retrievePaySelectionLines(InvoiceId invoiceId);

	Optional<I_C_PaySelection> retrieveProcessedPaySelectionContainingPayment(PaymentId paymentId);

	Optional<I_C_PaySelectionLine> retrievePaySelectionLineForPayment(I_C_PaySelection paySelection, PaymentId paymentId);

	List<I_C_PaySelectionLine> retrievePaySelectionLines(Collection<PaymentId> paymentIds);

	IQuery<I_C_PaySelectionLine> queryActivePaySelectionLinesByInvoiceId(Set<InvoiceId> invoiceIds);

	/**
	 * Make a direct SQL-update to the given {@code C_PaySelection.TotalAmount} and send a cache invalidation event.
	 */
	void updatePaySelectionTotalAmt(@NonNull PaySelectionId paySelectionId);
}
