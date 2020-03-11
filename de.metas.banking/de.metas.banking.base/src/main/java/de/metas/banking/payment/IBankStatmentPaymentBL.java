package de.metas.banking.payment;

import javax.annotation.Nullable;

import org.compiere.model.I_C_BankStatementLine;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.model.BankStatementPaymentInfo;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatmentPaymentBL extends ISingletonService
{
	void setPayment(@NonNull IBankStatementLineOrRef lineOrRef, @Nullable PaymentId paymentId);

	void setPayment(@NonNull IBankStatementLineOrRef lineOrRef, @Nullable BankStatementPaymentInfo payment);

	ImmutableSet<PaymentId> getAllNotReconciledPaymentsMatching(@NonNull I_C_BankStatementLine line, BPartnerId bpartnerId);

	void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(
			org.compiere.model.I_C_BankStatement bankStatement,
			de.metas.banking.model.I_C_BankStatementLine line);

	/**
	 * If no payment exists and a bpartner is set, create a new Payment or use an existing Payment if paymentIdToSet param is not null,
	 * and link the payment to the BankStatementLine.
	 *
	 * @param paymentIdToSet - if not null, don't create a new payment but link this one to the BankStatementLine
	 */
	void setOrCreateAndLinkPaymentToBankStatementLine(
			@NonNull org.compiere.model.I_C_BankStatement bankStatement,
			@NonNull de.metas.banking.model.I_C_BankStatementLine line,
			@Nullable PaymentId paymentIdToSet);
}
