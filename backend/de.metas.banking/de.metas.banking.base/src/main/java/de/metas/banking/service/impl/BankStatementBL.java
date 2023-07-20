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
import de.metas.acct.api.IPostingRequestBuilder;
import de.metas.acct.api.IPostingService;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.banking.service.ReconcileAsBankTransferRequest;
import de.metas.currency.Amount;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MPeriod;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
	private final IPostingService postingService = Services.get(IPostingService.class);
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
		if (bankStatement.isPosted())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(bankStatement);
			MPeriod.testPeriodOpen(ctx, bankStatement.getStatementDate(), DocBaseType.BankStatement, bankStatement.getAD_Org_ID());

			factAcctDAO.deleteForDocumentModel(bankStatement);

			bankStatement.setPosted(false);
			InterfaceWrapperHelper.save(bankStatement);
		}

		postIt(bankStatement);
	}

	private void postIt(final I_C_BankStatement bankStatement)
	{
		postingService.newPostingRequest()
				.setClientId(ClientId.ofRepoId(bankStatement.getAD_Client_ID()))
				.setDocumentRef(TableRecordReference.of(bankStatement))
				.setFailOnError(false)
				.onErrorNotifyUser(UserId.ofRepoId(bankStatement.getUpdatedBy()))
				.setPostImmediate(IPostingRequestBuilder.PostImmediate.No)
				.postIt();

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
	public void reconcileAsBankTransfer(@NonNull final ReconcileAsBankTransferRequest request)
	{
		if (BankStatementId.equals(request.getBankStatementId(), request.getCounterpartBankStatementId()))
		{
			throw new AdempiereException("Matching same bank statement is not allowed");
		}
		if (BankStatementLineId.equals(request.getBankStatementLineId(), request.getCounterpartBankStatementLineId()))
		{
			throw new AdempiereException("Matching same bank statement line is not allowed");
		}

		final I_C_BankStatement bankStatement = getById(request.getBankStatementId());
		assertBankStatementIsDraftOrInProcessOrCompleted(bankStatement);
		final I_C_BankStatementLine line = getLineById(request.getBankStatementLineId());
		if (line.isReconciled())
		{
			throw new AdempiereException(MSG_LineIsAlreadyReconciled);
		}

		final I_C_BankStatement counterpartBankStatement = getById(request.getCounterpartBankStatementId());
		assertBankStatementIsDraftOrInProcessOrCompleted(counterpartBankStatement);
		final I_C_BankStatementLine counterpartLine = getLineById(request.getCounterpartBankStatementLineId());
		if (counterpartLine.isReconciled())
		{
			throw new AdempiereException(MSG_LineIsAlreadyReconciled);
		}

		final boolean sameCurrency = line.getC_Currency_ID() == counterpartLine.getC_Currency_ID();
		if (sameCurrency && line.getTrxAmt().negate().compareTo(counterpartLine.getTrxAmt()) != 0)
		{
			throw new AdempiereException("Transfer amount differs"); // TODO: translate
		}

		line.setC_BP_BankAccountTo_ID(counterpartBankStatement.getC_BP_BankAccount_ID());
		line.setLink_BankStatementLine_ID(counterpartLine.getC_BankStatementLine_ID());
		line.setIsReconciled(true);
		InterfaceWrapperHelper.save(line);

		counterpartLine.setC_BP_BankAccountTo_ID(bankStatement.getC_BP_BankAccount_ID());
		counterpartLine.setLink_BankStatementLine_ID(line.getC_BankStatementLine_ID());
		counterpartLine.setIsReconciled(true);
		InterfaceWrapperHelper.save(counterpartLine);

		unpost(bankStatement);
		unpost(counterpartBankStatement);
	}

	@Override
	public void assertBankStatementIsDraftOrInProcessOrCompleted(final I_C_BankStatement bankStatement)
	{
		if (!DocStatus.ofCode(bankStatement.getDocStatus()).isDraftedInProgressOrCompleted())
		{
			throw new AdempiereException(MSG_BankStatement_MustBe_Draft_InProgress_Or_Completed);
		}
	}

	@Override
	public void unreconcile(@NonNull final List<I_C_BankStatementLine> bankStatementLines)
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

			final BankStatementLineId linkBankStatementLineId = BankStatementLineId.ofRepoIdOrNull(bankStatementLine.getLink_BankStatementLine_ID());
			if (linkBankStatementLineId != null)
			{
				final I_C_BankStatementLine linkBankStatementLine = getLineById(linkBankStatementLineId);
				markAsNotReconciledAndSave(linkBankStatementLine);
			}

			markAsNotReconciledAndSave(bankStatementLine);
		}

		//
		// Check references
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.getLineReferences(bankStatementLineIds);
		paymentIdsToUnReconcile.addAll(lineRefs.getPaymentIds());

		deleteReferencesAndNotifyListeners(lineRefs);

		paymentBL.markNotReconciled(paymentIdsToUnReconcile);
	}

	private void markAsNotReconciledAndSave(final I_C_BankStatementLine bankStatementLine)
	{
		bankStatementLine.setIsReconciled(false);

		bankStatementLine.setLink_BankStatementLine_ID(-1);

		bankStatementLine.setIsMultiplePaymentOrInvoice(false);
		bankStatementLine.setIsMultiplePayment(false);
		bankStatementLine.setC_Payment_ID(-1);

		bankStatementDAO.save(bankStatementLine);
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
	public BankStatementLineReferenceList getLineReferences(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
	{
		return bankStatementDAO.getLineReferences(bankStatementLineIds);
	}

	@Override
	public void updateLineFromInvoice(final @NonNull I_C_BankStatementLine bankStatementLine, @NonNull final InvoiceId invoiceId)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final Amount openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoiceId);

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		bankStatementLine.setC_BPartner_ID(invoice.getC_BPartner_ID());

		if (bankStatementLine.isUpdateAmountsFromInvoice())
		{
			bankStatementLine.setStmtAmt(openAmt.getAsBigDecimal());
			bankStatementLine.setTrxAmt(openAmt.getAsBigDecimal());
			bankStatementLine.setC_Currency_ID(invoice.getC_Currency_ID());
		}
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
	public PaymentCurrencyContext getPaymentCurrencyContext(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final PaymentCurrencyContext.PaymentCurrencyContextBuilder result = PaymentCurrencyContext.builder()
				.currencyConversionTypeId(getPaymentCurrencyConversionTypeId(bankStatementLine));

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

	@Override
	public void changeCurrencyRate(@NonNull final BankStatementLineId bankStatementLineId, @NonNull final BigDecimal currencyRate)
	{
		if (currencyRate.signum() == 0)
		{
			throw new AdempiereException("Invalid currency rate: " + currencyRate);
		}

		final I_C_BankStatementLine line = getLineById(bankStatementLineId);
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(line.getC_BankStatement_ID());

		final I_C_BankStatement bankStatement = getById(bankStatementId);
		assertBankStatementIsDraftOrInProcessOrCompleted(bankStatement);

		final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final CurrencyId baseCurrencyId = getBaseCurrencyId(line);
		if (CurrencyId.equals(currencyId, baseCurrencyId))
		{
			throw new AdempiereException("line is not in foreign currency");
		}

		line.setCurrencyRate(currencyRate);
		InterfaceWrapperHelper.save(line);

		unpost(bankStatement);
	}

	@Override
	public CurrencyId getBaseCurrencyId(final I_C_BankStatementLine line)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(line.getAD_Client_ID(), line.getAD_Org_ID());
		return moneyService.getBaseCurrencyId(clientAndOrgId);
	}

	@Nullable
	private CurrencyConversionTypeId getPaymentCurrencyConversionTypeId(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());

		if (paymentId == null)
		{
			return null;
		}

		return paymentBL.getCurrencyConversionTypeId(paymentId).orElse(null);
	}
}
