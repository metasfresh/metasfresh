package de.metas.banking.payment;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.PaySelectionId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IPaySelectionBL extends ISingletonService
{
	/**
	 * Creates a new pay selection updater which will create/updated/delete the {@link I_C_PaySelectionLine}s.
	 */
	IPaySelectionUpdater newPaySelectionUpdater();

	/**
	 * Create payments for each line of given pay selection. The payments are created only if they where not created before.<br>
	 * For more informations, see {@link #createPaymentIfNeeded(I_C_PaySelectionLine)}.
	 */
	void createPayments(I_C_PaySelection paySelection);

	void linkBankStatementLinesByPaymentIds(@NonNull Map<PaymentId, BankStatementAndLineAndRefId> bankStatementAndLineAndRefIds);

	/**
	 * Unlink any pay selection line which points to given bank statement line or to one of it's references.
	 */
	void unlinkPaySelectionLineFromBankStatement(Collection<BankStatementLineId> bankStatementLineIds);

	/**
	 * Update the given <code>psl</code>'s <code>C_BPartner_ID</code>, <code>C_BP_BankAccount_ID</code> and <code>Reference</code> from the <code>C_Invoice</code> which it references.
	 * <p>
	 * If the psl doesn't reference an invoice or if {@link IPaymentRequestBL#isUpdatedFromPaymentRequest(I_C_PaySelectionLine)} returns <code>true</code>,
	 * then the method does nothing.
	 */
	void updateFromInvoice(I_C_PaySelectionLine psl);

	/**
	 * Make sure its lines are not involved in Bank Statements and set the paySelection as not processed
	 */
	void reactivatePaySelection(I_C_PaySelection paySelection);

	/**
	 * Make sure all its lines have a C_BP_BankAccount and set the paySelection as processed
	 */
	void completePaySelection(I_C_PaySelection paySelection);

	/**
	 * Verify if the pay selection lines of the given pay selection have C_BP_BankAccount values set.
	 * Throw an exception if they don't.
	 */
	void validateBankAccounts(I_C_PaySelection paySelection);

	Set<PaymentId> getPaymentIds(PaySelectionId paySelectionId);
}
