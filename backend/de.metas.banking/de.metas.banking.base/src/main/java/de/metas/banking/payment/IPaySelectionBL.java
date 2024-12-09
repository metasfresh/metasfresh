package de.metas.banking.payment;

<<<<<<< HEAD
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.PaySelectionId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.PaySelectionLineId;
import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public interface IPaySelectionBL extends ISingletonService
{
	/**
	 * Creates a new pay selection updater which will create/updated/delete the {@link I_C_PaySelectionLine}s.
	 */
	IPaySelectionUpdater newPaySelectionUpdater();

	/**
<<<<<<< HEAD
	 * Create payments for each line of given pay selection. The payments are created only if they where not created before.<br>
	 * For more informations, see {@link #createPaymentIfNeeded(I_C_PaySelectionLine)}.
=======
	 * Create payments for each line of given pay selection.
	 * The payments are created only if they were not created before.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	void createPayments(I_C_PaySelection paySelection);

	void linkBankStatementLinesByPaymentIds(@NonNull Map<PaymentId, BankStatementAndLineAndRefId> bankStatementAndLineAndRefIds);

	/**
<<<<<<< HEAD
	 * Unlink any pay selection line which points to given bank statement line or to one of it's references.
	 */
	void unlinkPaySelectionLineFromBankStatement(Collection<BankStatementLineId> bankStatementLineIds);

=======
	 * Unlink any pay selection line which points to given bank statement line or to one of its references.
	 */
	void unlinkPaySelectionLineFromBankStatement(Collection<BankStatementLineId> bankStatementLineIds);

	Optional<I_C_PaySelection> getById(@NonNull PaySelectionId paySelectionId);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======

	ImmutableSet<BPartnerId> getBPartnerIdsFromPaySelectionLineIds(@NonNull Collection<PaySelectionLineId> paySelectionLineIds);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
