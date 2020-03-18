package de.metas.banking.payment;

import java.util.Optional;

import javax.annotation.Nullable;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.X_I_BankStatement;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatmentPaymentBL extends ISingletonService
{
	void setC_Payment(@NonNull IBankStatementLineOrRef lineOrRef, @Nullable I_C_Payment payment);

	String createPayment(I_C_BankStatementLine_Ref ref) throws Exception;

	String createPayment(MBankStatementLine bankStatementLine) throws Exception;

	String createPayment(X_I_BankStatement ibs) throws Exception;

	void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(
			org.compiere.model.I_C_BankStatement bankStatement,
			de.metas.banking.model.I_C_BankStatementLine line);

	/**
	 * If no payment exists and a bpartner is set, create a new Payment or use an existing Payment if paymentIdToSet param is not null,
	 * and link the payment to the BankStatementLine.
	 *
	 * @param paymentIdToSet - if not null, don't create a new payment but link this one to the BankStatementLine
	 * @return the current paymentId of the line if it has a payment, null if the line does not have a payment
	 */
	Optional<PaymentId> setOrCreateAndLinkPaymentToBankStatementLine(
			@NonNull org.compiere.model.I_C_BankStatement bankStatement,
			@NonNull de.metas.banking.model.I_C_BankStatementLine line,
			@Nullable PaymentId paymentIdToSet);

	void reconcile(@NonNull BankStatementLineReconcileRequest request);

}
