package de.metas.banking.payment;

import java.util.Set;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatementPaymentBL extends ISingletonService
{
	Set<PaymentId> findEligiblePaymentIds(@NonNull I_C_BankStatementLine bankStatementLine, @NonNull BPartnerId bpartnerId, int limit);

	void findOrCreateSinglePaymentAndLinkIfPossible(I_C_BankStatement bankStatement, I_C_BankStatementLine line);

	void createSinglePaymentAndLink(I_C_BankStatement bankStatement, I_C_BankStatementLine bankStatementLine);

	void linkSinglePayment(@NonNull I_C_BankStatement bankStatement, @NonNull I_C_BankStatementLine bankStatementLine, @NonNull PaymentId paymentId);

	void linkSinglePayment(@NonNull I_C_BankStatement bankStatement, @NonNull I_C_BankStatementLine bankStatementLine, @NonNull I_C_Payment payment);

	BankStatementLineMultiPaymentLinkResult linkMultiPayments(@NonNull BankStatementLineMultiPaymentLinkRequest request);

}
