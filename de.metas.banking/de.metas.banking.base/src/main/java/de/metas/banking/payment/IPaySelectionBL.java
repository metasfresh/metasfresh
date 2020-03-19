package de.metas.banking.payment;

import java.util.Set;

import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.model.PaySelectionId;
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
	 * Create bank statement lines for the given <code>bankStatement</code>, using the pay selection lines of the given <code>paySelection</code>.
	 */
	void createBankStatementLines(I_C_BankStatement bankStatement, I_C_PaySelection paySelection);

	/**
	 * Create payments for each line of given pay selection. The payments are created only if they where not created before.<br>
	 * For more informations, see {@link #createPaymentIfNeeded(I_C_PaySelectionLine)}.
	 */
	void createPayments(I_C_PaySelection paySelection);

	/**
	 * Creates a payment for given pay selection line, links the payment to line and saves the line.
	 *
	 * A payment will be created only if
	 * <ul>
	 * <li>was not created before
	 * <li>line is not linked to a {@link I_C_BankStatementLine} or {@link I_C_BankStatementLine_Ref}.
	 * </ul>
	 *
	 * @return newly created payment or <code>null</code> if no payment was generated.
	 */
	I_C_Payment createPaymentIfNeeded(I_C_PaySelectionLine line);

	/**
	 * Link given bank statement line reference to pay selection line
	 */
	void linkBankStatementLine(@NonNull I_C_PaySelectionLine psl, @NonNull BankStatementAndLineAndRefId bankStatementAndLineAndRefId);

	/**
	 * Unlink any pay selection line which points to given bank statement line or to one of it's references.
	 */
	void unlinkPaySelectionLineForBankStatement(BankStatementLineId bankStatementLineId);

	/**
	 * Unlink any pay selection line which points to given bank statement line reference.
	 */
	void unlinkPaySelectionLineForBankStatement(BankStatementAndLineAndRefId bankStatementLineAndRefId);

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
