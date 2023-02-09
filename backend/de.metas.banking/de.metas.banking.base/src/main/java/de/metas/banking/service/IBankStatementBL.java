/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.banking.service;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IBankStatementBL extends ISingletonService
{
	AdMessageKey MSG_BankStatement_MustBe_Draft_InProgress_Or_Completed = AdMessageKey.of("bankstatement.BankStatement_MustBe_Draft_InProgress_Or_Completed");
	AdMessageKey MSG_LineIsAlreadyReconciled = AdMessageKey.of("bankstatement.LineIsAlreadyReconciled");

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

	String getDocumentNo(BankStatementId bankStatementId);

	void deleteReferences(@NonNull BankStatementLineId bankStatementLineId);

	void assertBankStatementIsDraftOrInProcessOrCompleted(I_C_BankStatement bankStatement);

	void unreconcile(@NonNull List<I_C_BankStatementLine> bankStatementLines);

	void reconcileAsBankTransfer(@NonNull ReconcileAsBankTransferRequest request);

	int computeNextLineNo(@NonNull BankStatementId bankStatementId);

	@NonNull
	ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId);

	BankStatementLineReferenceList getLineReferences(@NonNull Collection<BankStatementLineId> bankStatementLineIds);

	void updateLineFromInvoice(final @NonNull I_C_BankStatementLine bsl, @NonNull InvoiceId invoiceId);

	boolean isCashJournal(final I_C_BankStatementLine bankStatementLine);

	PaymentCurrencyContext getPaymentCurrencyContext(@NonNull I_C_BankStatementLine bankStatementLine);

	void changeCurrencyRate(BankStatementLineId bankStatementLineId, BigDecimal currencyRate);

	CurrencyId getBaseCurrencyId(I_C_BankStatementLine line);
}
