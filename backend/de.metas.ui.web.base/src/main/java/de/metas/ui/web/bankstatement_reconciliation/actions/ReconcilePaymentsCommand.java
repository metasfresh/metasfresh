package de.metas.ui.web.bankstatement_reconciliation.actions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest.PaymentToLink;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.bankstatement_reconciliation.BankStatementLineRow;
import de.metas.ui.web.bankstatement_reconciliation.PaymentToReconcileRow;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

public class ReconcilePaymentsCommand
{
	@VisibleForTesting
	static final AdMessageKey MSG_StatementLineAmtToReconcileIs = AdMessageKey.of("StatementLineAmtToReconcileIs");

	private final IMsgBL msgBL;
	private final IBankStatementPaymentBL bankStatmentPaymentBL;

	private final ReconcilePaymentsRequest request;

	@Builder
	private ReconcilePaymentsCommand(
			@NonNull final IMsgBL msgBL,
			@NonNull final IBankStatementPaymentBL bankStatmentPaymentBL,
			//
			@NonNull final ReconcilePaymentsRequest request)
	{
		this.msgBL = msgBL;
		this.bankStatmentPaymentBL = bankStatmentPaymentBL;

		this.request = request;
	}

	public ProcessPreconditionsResolution checkCanExecute()
	{
		//
		final BankStatementLineRow bankStatementLine = request.getSelectedBankStatementLine();
		if (bankStatementLine == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no bank statement line selected");
		}
		if (bankStatementLine.isReconciled())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("bank statement line was already reconciled");
		}

		//
		final List<PaymentToReconcileRow> payments = request.getSelectedPaymentsToReconcile();
		if (payments.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no payment rows selected");
		}

		//
		final ExplainedOptional<BankStatementLineMultiPaymentLinkRequest> optionalRequest = computeBankStatementLineReconcileRequest();
		if (!optionalRequest.isPresent())
		{
			return ProcessPreconditionsResolution.reject(optionalRequest.getExplanation());
		}

		return ProcessPreconditionsResolution.accept();
	}

	public void execute()
	{
		final BankStatementLineMultiPaymentLinkRequest request = computeBankStatementLineReconcileRequest().get();
		bankStatmentPaymentBL.linkMultiPayments(request);
	}

	private ExplainedOptional<BankStatementLineMultiPaymentLinkRequest> computeBankStatementLineReconcileRequest()
	{
		final BankStatementLineRow bankStatementLineRow = request.getSelectedBankStatementLine();
		if (bankStatementLineRow == null)
		{
			return ExplainedOptional.emptyBecause("no bank statement line selected");
		}
		if (bankStatementLineRow.isReconciled())
		{
			return ExplainedOptional.emptyBecause("bank statement line was already reconciled");
		}

		final List<PaymentToReconcileRow> paymentRows = request.getSelectedPaymentsToReconcile();
		if (paymentRows.isEmpty())
		{
			return ExplainedOptional.emptyBecause("no payment rows selected");
		}

		final Amount statementLineAmt = bankStatementLineRow.getStatementLineAmt();
		final CurrencyCode currencyCode = statementLineAmt.getCurrencyCode();

		Amount statementLineAmtReconciled = Amount.zero(currencyCode);
		final ArrayList<PaymentToLink> paymentsToReconcile = new ArrayList<>();
		for (final PaymentToReconcileRow paymentRow : paymentRows)
		{
			if (paymentRow.isReconciled())
			{
				return ExplainedOptional.emptyBecause("Payment `" + paymentRow.getDocumentNo() + "` was already reconciled");
			}
			final Amount payAmt = paymentRow.getPayAmtNegateIfOutbound();
			if (!payAmt.getCurrencyCode().equals(currencyCode))
			{
				return ExplainedOptional.emptyBecause("Payment `" + paymentRow.getDocumentNo() + "` shall be in `" + currencyCode + "` instead of `" + payAmt.getCurrencyCode() + "`");
			}

			statementLineAmtReconciled = statementLineAmtReconciled.add(payAmt);

			paymentsToReconcile.add(PaymentToLink.builder()
					.paymentId(paymentRow.getPaymentId())
					.statementLineAmt(payAmt)
					.build());
		}

		final Amount statementLineAmtToReconcile = statementLineAmt.subtract(statementLineAmtReconciled);
		if (!statementLineAmtToReconcile.isZero())
		{
			return ExplainedOptional.emptyBecause(msgBL.getTranslatableMsgText(MSG_StatementLineAmtToReconcileIs, statementLineAmtToReconcile));
		}

		return ExplainedOptional.of(BankStatementLineMultiPaymentLinkRequest.builder()
				.bankStatementLineId(bankStatementLineRow.getBankStatementLineId())
				.paymentsToLink(paymentsToReconcile)
				.build());
	}
}
