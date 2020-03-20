package de.metas.banking.payment;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatmentPaymentBL extends ISingletonService
{
	void findOrCreateSinglePaymentAndLink(I_C_BankStatement bankStatement, I_C_BankStatementLine line);

	void createSinglePaymentAndLink(I_C_BankStatement bankStatement, I_C_BankStatementLine bankStatementLine);

	void linkSinglePayment(@NonNull I_C_BankStatement bankStatement, @NonNull I_C_BankStatementLine bankStatementLine, @NonNull PaymentId paymentId);

	BankStatementLineMultiPaymentLinkResult linkMultiPayments(@NonNull BankStatementLineMultiPaymentLinkRequest request);
}
