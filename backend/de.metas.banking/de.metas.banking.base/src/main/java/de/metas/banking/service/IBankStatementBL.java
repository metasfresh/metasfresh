package de.metas.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBankStatementBL extends ISingletonService
{
	I_C_BankStatement getById(BankStatementId bankStatementId);

	I_C_BankStatementLine getLineById(BankStatementLineId bankStatementLineId);

	List<I_C_BankStatementLine> getLinesByBankStatementId(BankStatementId bankStatementId);

	List<I_C_BankStatementLine> getLinesByIds(Set<BankStatementLineId> ids);

	boolean isPaymentOnBankStatement(PaymentId paymentId);

	/**
	 * Updates bank statement ending balance as "beginning balance" + "statement difference".
	 */
	void updateEndingBalance(I_C_BankStatement bankStatement);

	void unpost(I_C_BankStatement bankStatement);

	boolean isReconciled(I_C_BankStatementLine line);

	BigDecimal computeStmtAmtExcludingChargeAmt(I_C_BankStatementLine line);

	String getDocumentNo(BankStatementId bankStatementId);

	void deleteReferences(@NonNull BankStatementLineId bankStatementLineId);

	void unlinkPaymentsAndDeleteReferences(@NonNull List<I_C_BankStatementLine> bankStatementLines);

	int computeNextLineNo(@NonNull BankStatementId bankStatementId);

	@NonNull
	ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId);
}
