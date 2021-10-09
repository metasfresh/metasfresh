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

package de.metas.banking.service.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.currency.Amount;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.FixedConversionRate;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
public class BankStatementBL implements IBankStatementBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBankStatementListenerService bankStatementListenersService = Services.get(IBankStatementListenerService.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final BankAccountService bankAccountService;
	private final MoneyService moneyService;

	public BankStatementBL(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final MoneyService moneyService)
	{
		this.bankAccountService = bankAccountService;
		this.moneyService = moneyService;
	}

	@Override
	public I_C_BankStatement getById(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getById(bankStatementId);
	}

	@Override
	public I_C_BankStatementLine getLineById(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return bankStatementDAO.getLineById(bankStatementLineId);
	}

	@Override
	public List<I_C_BankStatementLine> getLinesByBankStatementId(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getLinesByBankStatementId(bankStatementId);
	}

	@Override
	public List<I_C_BankStatementLine> getLinesByIds(@NonNull final Set<BankStatementLineId> lineIds)
	{
		return bankStatementDAO.getLinesByIds(lineIds);
	}

	@Override
	public boolean isPaymentOnBankStatement(@NonNull final PaymentId paymentId)
	{
		return bankStatementDAO.isPaymentOnBankStatement(paymentId);
	}

	@Override
	public void updateEndingBalance(final I_C_BankStatement bankStatement)
	{
		final BigDecimal endingBalance = bankStatement
				.getBeginningBalance()
				.add(bankStatement.getStatementDifference());

		bankStatement.setEndingBalance(endingBalance);
	}

	@Override
	public void unpost(final I_C_BankStatement bankStatement)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(bankStatement);
		MPeriod.testPeriodOpen(ctx, bankStatement.getStatementDate(), X_C_DocType.DOCBASETYPE_BankStatement, bankStatement.getAD_Org_ID());

		factAcctDAO.deleteForDocumentModel(bankStatement);

		bankStatement.setPosted(false);
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public boolean isReconciled(@NonNull final I_C_BankStatementLine line)
	{
		if (line.isMultiplePaymentOrInvoice())
		{
			if (line.isMultiplePayment())
			{
				// NOTE: for performance reasons we are not checking if we have C_BankStatementLine_Ref records which have payments.
				// If this flag is set we assume that we already have them
				return true;
			}
			else
			{
				final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
				return paymentId != null;
			}
		}
		else
		{
			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
			return paymentId != null;
		}
	}

	@Override
	public String getDocumentNo(@NonNull final BankStatementId bankStatementId)
	{
		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		return bankStatement.getDocumentNo();
	}

	@Override
	public void unlinkPaymentsAndDeleteReferences(@NonNull final List<I_C_BankStatementLine> bankStatementLines)
	{
		if (bankStatementLines.isEmpty())
		{
			return;
		}

		//
		// Unlink payment from line
		final ArrayList<BankStatementLineId> bankStatementLineIds = new ArrayList<>();
		final ArrayList<PaymentId> paymentIdsToUnReconcile = new ArrayList<>();
		for (final I_C_BankStatementLine bankStatementLine : bankStatementLines)
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
			bankStatementLineIds.add(bankStatementLineId);

			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				paymentIdsToUnReconcile.add(paymentId);
			}

			bankStatementLine.setIsReconciled(false);
			bankStatementLine.setIsMultiplePaymentOrInvoice(false);
			bankStatementLine.setIsMultiplePayment(false);

			bankStatementLine.setC_Payment_ID(-1);

			bankStatementDAO.save(bankStatementLine);
		}

		//
		// Check references
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.getLineReferences(bankStatementLineIds);
		paymentIdsToUnReconcile.addAll(lineRefs.getPaymentIds());

		deleteReferencesAndNotifyListeners(lineRefs);

		paymentBL.markNotReconciled(paymentIdsToUnReconcile);
	}

	@Override
	public void deleteReferences(@NonNull final BankStatementLineId bankStatementLineId)
	{
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.getLineReferences(bankStatementLineId);
		deleteReferencesAndNotifyListeners(lineRefs);
	}

	private void deleteReferencesAndNotifyListeners(@NonNull final BankStatementLineReferenceList lineRefs)
	{
		if (lineRefs.isEmpty())
		{
			return;
		}

		bankStatementListenersService.firePaymentsUnlinkedFromBankStatementLineReferences(lineRefs);
		bankStatementDAO.deleteReferencesByIds(lineRefs.getBankStatementLineRefIds());
	}

	@Override
	public int computeNextLineNo(@NonNull final BankStatementId bankStatementId)
	{
		final int lastLineNo = bankStatementDAO.getLastLineNo(bankStatementId);
		return lastLineNo / 10 * 10 + 10;
	}

	@NonNull
	@Override
	public ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getLinesPaymentIds(bankStatementId);
	}

	@Override
	public void updateLineFromInvoice(final @NonNull I_C_BankStatementLine bankStatementLine, @NonNull final InvoiceId invoiceId)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final Amount openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoiceId);

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		bankStatementLine.setC_BPartner_ID(invoice.getC_BPartner_ID());
		bankStatementLine.setStmtAmt(openAmt.getAsBigDecimal());
		bankStatementLine.setTrxAmt(openAmt.getAsBigDecimal());
		bankStatementLine.setC_Currency_ID(invoice.getC_Currency_ID());
	}

	@Override
	public boolean isCashJournal(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		final I_C_BankStatement bankStatement = getById(bankStatementId);
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());

		return bankAccountService.isCashBank(bankAccountId);
	}

	@Override
	public CurrencyConversionContext getCurrencyConversionCtx(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final PaymentCurrencyContext paymentCurrencyContext = getPaymentCurrencyContext(bankStatementLine);

		final OrgId orgId = OrgId.ofRepoId(bankStatementLine.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		CurrencyConversionContext conversionCtx = currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(bankStatementLine.getDateAcct(), timeZone),
				paymentCurrencyContext.getCurrencyConversionTypeId(),
				ClientId.ofRepoId(bankStatementLine.getAD_Client_ID()),
				orgId);

		final FixedConversionRate fixedCurrencyRate = paymentCurrencyContext.toFixedConversionRateOrNull();
		if (fixedCurrencyRate != null)
		{
			conversionCtx = conversionCtx.withFixedConversionRate(fixedCurrencyRate);
		}

		return conversionCtx;
	}

	@Override
	public PaymentCurrencyContext getPaymentCurrencyContext(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final PaymentCurrencyContext.PaymentCurrencyContextBuilder result = PaymentCurrencyContext.builder()
				.currencyConversionTypeId(currencyConversionBL.getCurrencyConversionTypeId(ConversionTypeMethod.Spot));

		final BigDecimal fixedCurrencyRate = bankStatementLine.getCurrencyRate();
		if (fixedCurrencyRate != null && fixedCurrencyRate.signum() != 0)
		{
			final CurrencyId paymentCurrencyId = CurrencyId.ofRepoId(bankStatementLine.getC_Currency_ID());

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(bankStatementLine.getAD_Client_ID(), bankStatementLine.getAD_Org_ID());
			final CurrencyId acctSchemaCurrencyId = moneyService.getBaseCurrencyId(clientAndOrgId);

			result.paymentCurrencyId(paymentCurrencyId)
					.sourceCurrencyId(acctSchemaCurrencyId)
					.currencyRate(fixedCurrencyRate);
		}

		return result.build();
	}

}
