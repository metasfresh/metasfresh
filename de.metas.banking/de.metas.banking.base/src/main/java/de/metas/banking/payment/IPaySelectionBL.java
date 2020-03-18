package de.metas.banking.payment;

import java.util.Set;

import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.BankStatementId;
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
	 *
	 * @return updater
	 */
	IPaySelectionUpdater newPaySelectionUpdater();

	/**
	 * Create bank statement lines for the given <code>bankStatement</code>, using the pay selection lines of the given <code>paySelection</code>.
	 *
	 * @param bankStatement
	 * @param paySelection
	 */
	void createBankStatementLines(I_C_BankStatement bankStatement, I_C_PaySelection paySelection);

	/**
	 * Create payments for each line of given pay selection. The payments are created only if they where not created before.<br>
	 * For more informations, see {@link #createPaymentIfNeeded(I_C_PaySelectionLine)}.
	 *
	 * @param paySelection
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
	 * @param line
	 * @return newly created payment or <code>null</code> if no payment was generated.
	 */
	I_C_Payment createPaymentIfNeeded(I_C_PaySelectionLine line);

	/**
	 * Link given {@link I_C_BankStatementLine_Ref}/{@link I_C_BankStatementLine} to pay selection line
	 */
	void linkBankStatementLine(@NonNull I_C_PaySelectionLine psl, @NonNull BankStatementId bankStatementId, @NonNull BankStatementLineId bankStatementLineId, int bankStatementLineRefRepoId);

	/**
	 * Unlink any pay selection line which points to given bank statement line or to one of it's references.
	 *
	 * @param bankStatementLine
	 */
	void unlinkPaySelectionLineForBankStatement(I_C_BankStatementLine bankStatementLine);

	/**
	 * Unlink any pay selection line which points to given bank statement line reference.
	 *
	 * @param bankStatementLineRef
	 */
	void unlinkPaySelectionLineForBankStatement(de.metas.banking.model.I_C_BankStatementLine_Ref bankStatementLineRef);

	/**
	 * Update the given <code>psl</code>'s <code>C_BPartner_ID</code>, <code>C_BP_BankAccount_ID</code> and <code>Reference</code> from the <code>C_Invoice</code> which it references.
	 * <p>
	 * If the psl doesn't reference an invoice or if {@link IPaymentRequestBL#isUpdatedFromPaymentRequest(I_C_PaySelectionLine)} returns <code>true</code>,
	 * then the method does nothing.
	 *
	 * @param psl
	 */
	void updateFromInvoice(I_C_PaySelectionLine psl);

	/**
	 * Make sure its lines are not involved in Bank Statements and set the paySelection as not processed
	 * 
	 * @param paySelection
	 */
	void reactivatePaySelection(I_C_PaySelection paySelection);

	/**
	 * Make sure all its lines have a C_BP_BankAccount and set the paySelection as processed
	 * 
	 * @param paySelection
	 */
	void completePaySelection(I_C_PaySelection paySelection);

	/**
	 * Verify if the pay selection lines of the given pay selection have C_BP_BankAccount values set.
	 * Throw an exception if they don't.
	 * 
	 * @param paySelection
	 */
	void validateBankAccounts(I_C_PaySelection paySelection);

	Set<PaymentId> getPaymentIds(PaySelectionId paySelectionId);
}
