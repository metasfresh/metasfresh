package de.metas.banking.payment;

import java.util.Optional;

import javax.annotation.Nullable;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatmentPaymentBL extends ISingletonService
{
	void setC_Payment(@NonNull I_C_BankStatementLine line, @Nullable I_C_Payment payment);

	void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(I_C_BankStatement bankStatement, I_C_BankStatementLine line);

	/**
	 * If no payment exists and a bpartner is set, create a new Payment or use an existing Payment if paymentIdToSet param is not null,
	 * and link the payment to the BankStatementLine.
	 *
	 * @param paymentIdToSet - if not null, don't create a new payment but link this one to the BankStatementLine
	 * @return the current paymentId of the line if it has a payment, null if the line does not have a payment
	 */
	Optional<PaymentId> setOrCreateAndLinkPaymentToBankStatementLine(
			@NonNull I_C_BankStatement bankStatement,
			@NonNull I_C_BankStatementLine line,
			@Nullable PaymentId paymentIdToSet);

	BankStatementLineReconcileResult reconcile(@NonNull BankStatementLineReconcileRequest request);

}
