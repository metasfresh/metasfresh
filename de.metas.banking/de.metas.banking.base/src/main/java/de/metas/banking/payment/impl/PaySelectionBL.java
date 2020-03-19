package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.X_C_BP_BankAccount;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.model.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocument;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class PaySelectionBL implements IPaySelectionBL
{
	private static final String MSG_CannotReactivate_PaySelectionLineInBankStatement_2P = "CannotReactivate_PaySelectionLineInBankStatement";
	private static final String MSG_PaySelectionLines_No_BankAccount = "C_PaySelection_PaySelectionLines_No_BankAccount";

	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	@Override
	public void createBankStatementLines(
			final I_C_BankStatement bankStatement,
			final I_C_PaySelection paySelection)
	{
		Check.errorIf(bankStatement.getC_BP_BankAccount_ID() != paySelection.getC_BP_BankAccount_ID(),
				"C_BankStatement {} with C_BP_BankAccount_ID={} and C_PaySelection {} with C_BP_BankAccount_ID={} need to have the same C_BP_BankAccount_ID",
				bankStatement, bankStatement.getC_BP_BankAccount_ID(), paySelection, paySelection.getC_BP_BankAccount_ID());

		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID());

		I_C_BankStatementLine bankStatementLine = null;
		int nextReferenceLineNo = 10;

		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionDAO.retrievePaySelectionLines(paySelection);
		for (final I_C_PaySelectionLine psl : paySelectionLines)
		{
			// Skip if already in a bank statement
			if (isInBankStatement(psl))
			{
				continue;
			}

			// Skip if no invoice
			if (psl.getC_Invoice_ID() <= 0)
			{
				continue;
			}

			//
			// Create the bank statement line (if not already created)
			if (bankStatementLine == null)
			{
				bankStatementLine = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine.class);
				bankStatementLine.setAD_Org_ID(paySelection.getAD_Org_ID());
				bankStatementLine.setC_BankStatement_ID(bankStatementId.getRepoId());
				bankStatementLine.setIsMultiplePaymentOrInvoice(true); // we have a reference line for each invoice
				bankStatementLine.setIsMultiplePayment(true); // each invoice shall have it's own payment
				bankStatementLine.setC_Currency_ID(bankStatement.getC_BP_BankAccount().getC_Currency_ID());
				bankStatementLine.setValutaDate(paySelection.getPayDate());
				bankStatementLine.setDateAcct(paySelection.getPayDate());
				bankStatementLine.setStatementLineDate(paySelection.getPayDate());
				bankStatementLine.setReferenceNo(null); // no ReferenceNo at this level
				bankStatementLine.setC_BPartner_ID(0); // no partner because we will have it on "line reference" level
				bankStatementLine.setStmtAmt(BigDecimal.ZERO); // will be updated at the end
				bankStatementLine.setTrxAmt(BigDecimal.ZERO); // will be updated at the end
				bankStatementLine.setChargeAmt(BigDecimal.ZERO);
				bankStatementLine.setInterestAmt(BigDecimal.ZERO);
				bankStatementDAO.save(bankStatementLine);
			}

			//
			// Create new bank statement line reference for our current pay selection line.
			final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class);
			bankStatementLineRef.setAD_Org_ID(bankStatementLine.getAD_Org_ID());
			bankStatementLineRef.setC_BankStatement_ID(bankStatementLine.getC_BankStatement_ID());
			bankStatementLineRef.setC_BankStatementLine_ID(bankStatementLine.getC_BankStatementLine_ID());
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(bankStatementLineRef, true); // disable recalculation. we will do it at the end

			//
			// Set Invoice from pay selection line
			bankStatementLineRef.setC_BPartner_ID(psl.getC_BPartner_ID());
			final I_C_Invoice invoice = psl.getC_Invoice();
			bankStatementLineRef.setC_Invoice(invoice);
			bankStatementLineRef.setC_Currency_ID(invoice.getC_Currency_ID());

			//
			// Get pay schedule line amounts:
			final boolean isReceipt;
			if (invoiceBL.isCreditMemo(invoice))
			{
				// SOTrx=Y, but credit memo => receipt=N
				isReceipt = !invoice.isSOTrx();
			}
			else
			{
				// SOTrx=Y => receipt=Y
				isReceipt = invoice.isSOTrx();
			}
			final BigDecimal factor = isReceipt ? BigDecimal.ONE : BigDecimal.ONE.negate();
			final BigDecimal linePayAmt = psl.getPayAmt().multiply(factor);
			final BigDecimal lineDiscountAmt = psl.getDiscountAmt().multiply(factor);

			// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
			bankStatementLineRef.setDiscountAmt(lineDiscountAmt);
			bankStatementLineRef.setTrxAmt(linePayAmt);
			bankStatementLineRef.setReferenceNo(psl.getReference());
			bankStatementLineRef.setLine(nextReferenceLineNo);

			//
			// Set Payment from pay selection line.
			// NOTE: In case the pay selection line does not already have a payment generated,
			// we are generating it now because it's the most convenient for the user.
			createPaymentIfNeeded(psl);
			bankStatementLineRef.setC_Payment_ID(psl.getC_Payment_ID());

			//
			// Save the bank statement line reference
			bankStatementDAO.save(bankStatementLineRef);
			nextReferenceLineNo += 10;

			//
			// Update pay selection line => mark it as reconciled
			linkBankStatementLine(psl, extractBankStatementAndLineAndRefId(bankStatementLineRef));
		}

		//
		// Update Bank Statement Line's totals:
		if (bankStatementLine != null)
		{
			bankStatementBL.recalculateStatementLineAmounts(bankStatementLine);
		}
	}

	private static BankStatementAndLineAndRefId extractBankStatementAndLineAndRefId(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		return BankStatementAndLineAndRefId.ofRepoIds(
				bankStatementLineRef.getC_BankStatement_ID(),
				bankStatementLineRef.getC_BankStatementLine_ID(),
				bankStatementLineRef.getC_BankStatementLine_Ref_ID());
	}

	private static boolean isInBankStatement(@NonNull final I_C_PaySelectionLine psl)
	{
		return BankStatementId.ofRepoIdOrNull(psl.getC_BankStatement_ID()) != null
				&& BankStatementLineId.ofRepoIdOrNull(psl.getC_BankStatementLine_ID()) != null;
	}

	@Override
	public void updateFromInvoice(final org.compiere.model.I_C_PaySelectionLine psl)
	{
		final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);

		if (paymentRequestBL.isUpdatedFromPaymentRequest(psl))
		{
			return;
		}

		final I_C_Invoice invoice = psl.getC_Invoice();

		if (invoice == null)
		{
			return; // nothing to do yet, but as C_PaySelectionLine.C_Invoice_ID is mandatory, we only need to make sure this method is eventually called from a model interceptor
		}

		final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(psl);

		final int partnerID = invoice.getC_BPartner_ID();
		psl.setC_BPartner_ID(partnerID);

		// task 09500 get the currency from the account of the selection header
		// this is safe because the columns are mandatory
		final int currencyID = psl.getC_PaySelection().getC_BP_BankAccount().getC_Currency_ID();

		final boolean isSalesInvoice = invoice.isSOTrx();

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final boolean isCreditMemo = invoiceBL.isCreditMemo(invoice);

		final String accteptedBankAccountUsage;

		if ((isSalesInvoice && !isCreditMemo) ||
				(!isSalesInvoice && isCreditMemo))
		{
			// allow a direct debit account if there is an invoice with SOTrx='Y', and not a credit memo
			// OR it is a Credit memo with isSoTrx = 'N'
			accteptedBankAccountUsage = X_C_BP_BankAccount.BPBANKACCTUSE_DirectDebit;
		}

		else
		{
			// allow a direct deposit account if there is an invoice with SOTrx='N', and not a credit memo
			// OR it is a Credit memo with isSoTrx = 'Y'
			accteptedBankAccountUsage = X_C_BP_BankAccount.BPBANKACCTUSE_DirectDeposit;
		}

		final List<I_C_BP_BankAccount> bankAccts = bpBankAccountDAO.retrieveBankAccountsForPartnerAndCurrency(ctx, partnerID, currencyID);

		if (!bankAccts.isEmpty())
		{
			int primaryAcct = 0;
			int secondaryAcct = 0;

			for (final I_C_BP_BankAccount account : bankAccts)
			{
				// FRESH-606: Only continue if the bank account has a use set
				if (account.getBPBankAcctUse() == null)
				{
					continue;
				}

				final int accountID = account.getC_BP_BankAccount_ID();
				if (accountID > 0)
				{
					if (account.getBPBankAcctUse().equals(X_C_BP_BankAccount.BPBANKACCTUSE_Both))
					{
						// in case a secondary act was already found, it should be not changed.
						// this is important because the default accounts come first from the query and they have higher priority than the non-defult ones.
						if (secondaryAcct == 0)
						{
							secondaryAcct = accountID;
						}
					}
					else if (accteptedBankAccountUsage == null)
					{
						continue;
					}

					else if (account.getBPBankAcctUse().equals(accteptedBankAccountUsage))
					{
						primaryAcct = accountID;
						break;
					}
				}
			}
			if (primaryAcct != 0)
			{
				psl.setC_BP_BankAccount_ID(primaryAcct);
			}
			else if (secondaryAcct != 0)
			{
				psl.setC_BP_BankAccount_ID(secondaryAcct);
			}
		}
	}

	@Override
	public IPaySelectionUpdater newPaySelectionUpdater()
	{
		return new PaySelectionUpdater();
	}

	@Override
	public void createPayments(final I_C_PaySelection paySelection)
	{
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(paySelection))
		{
			paySelectionLine.setC_PaySelection(paySelection); // for optimizations
			createPaymentIfNeeded(paySelectionLine);
		}
	}

	@Override
	public I_C_Payment createPaymentIfNeeded(final I_C_PaySelectionLine line)
	{
		// Skip if a payment was already created
		if (line.getC_Payment_ID() > 0)
		{
			return null;
		}

		// Skip if this pay selection line is already in a bank statement
		// because in that case, the payment shall be generated there
		if (isInBankStatement(line))
		{
			return null;
		}

		try
		{
			final I_C_Payment payment = createPayment(line);
			line.setC_Payment(payment);
			paySelectionDAO.save(line);

			return payment;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Caught " + e + " while trying to create a payment for C_PaySelectionLine " + line, e);
		}
	}

	/**
	 * Generates a payment for given pay selection line. The payment will be also processed.
	 * <p>
	 * NOTE: this method is NOT checking if the payment was already created or it's not needed.
	 *
	 * @param line
	 * @return generated payment.
	 */
	private I_C_Payment createPayment(final I_C_PaySelectionLine line)
	{
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		final I_C_PaySelection paySelection = line.getC_PaySelection();
		final int ownBankAccountId = paySelection.getC_BP_BankAccount_ID();
		final LocalDate payDate = TimeUtil.asLocalDate(paySelection.getPayDate());

		final org.compiere.model.I_C_Payment payment = paymentBL.newBuilderOfInvoice(line.getC_Invoice())
				.adOrgId(OrgId.ofRepoId(line.getAD_Org_ID()))
				.bpBankAccountId(BankAccountId.ofRepoId(ownBankAccountId))
				.dateAcct(payDate)
				.dateTrx(payDate)
				.bpartnerId(BPartnerId.ofRepoId(line.getC_BPartner_ID()))
				.tenderType(TenderType.DirectDeposit)
				.payAmt(line.getPayAmt())
				.discountAmt(line.getDiscountAmt())
				//
				.createAndProcess();

		return InterfaceWrapperHelper.create(payment, I_C_Payment.class);
	}

	@Override
	public void linkBankStatementLinesByPaymentIds(@NonNull final Map<PaymentId, BankStatementAndLineAndRefId> bankStatementAndLineAndRefIds)
	{
		if (bankStatementAndLineAndRefIds.isEmpty())
		{
			return;
		}

		final Set<PaymentId> paymentIds = bankStatementAndLineAndRefIds.keySet();

		final ImmutableMap<PaymentId, I_C_PaySelectionLine> paySelectionLinesByPaymentId = Maps.uniqueIndex(
				paySelectionDAO.retrievePaySelectionLines(paymentIds),
				paySelectionLine -> PaymentId.ofRepoId(paySelectionLine.getC_Payment_ID()));

		for (final Map.Entry<PaymentId, I_C_PaySelectionLine> e : paySelectionLinesByPaymentId.entrySet())
		{
			final PaymentId paymentId = e.getKey();
			final I_C_PaySelectionLine paySelectionLine = e.getValue();
			final BankStatementAndLineAndRefId bankStatementLineRefId = bankStatementAndLineAndRefIds.get(paymentId);

			linkBankStatementLine(paySelectionLine, bankStatementLineRefId);
		}
	}

	@Override
	public void unlinkPaySelectionLineForBankStatement(final BankStatementLineId bankStatementLineId)
	{
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(bankStatementLineId))
		{
			unlinkBankStatementFromLine(paySelectionLine);
		}
	}

	@Override
	public void unlinkPaySelectionLineForBankStatement(final BankStatementAndLineAndRefId bankStatementLineAndRefId)
	{
		final I_C_PaySelectionLine paySelectionLine = paySelectionDAO.retrievePaySelectionLine(bankStatementLineAndRefId).orElse(null);
		if (paySelectionLine != null)
		{
			unlinkBankStatementFromLine(paySelectionLine);
		}
	}

	@Override
	public void linkBankStatementLine(
			@NonNull final I_C_PaySelectionLine psl,
			@NonNull final BankStatementAndLineAndRefId bankStatementAndLineAndRefId)
	{
		psl.setC_BankStatement_ID(bankStatementAndLineAndRefId.getBankStatementId().getRepoId());
		psl.setC_BankStatementLine_ID(bankStatementAndLineAndRefId.getBankStatementLineId().getRepoId());
		psl.setC_BankStatementLine_Ref_ID(bankStatementAndLineAndRefId.getBankStatementLineRefId().getRepoId());
		paySelectionDAO.save(psl);
	}

	private void unlinkBankStatementFromLine(final I_C_PaySelectionLine psl)
	{
		psl.setC_BankStatement_ID(-1);
		psl.setC_BankStatementLine_ID(-1);
		psl.setC_BankStatementLine_Ref_ID(-1);
		paySelectionDAO.save(psl);
	}

	@Override
	public void completePaySelection(final I_C_PaySelection paySelection)
	{
		final List<I_C_PaySelectionLine> lines = getPaySelectionLines(paySelection);
		if (lines.isEmpty())
		{
			throw new AdempiereException("@NoLines@");
		}

		validateBankAccounts(paySelection);

		paySelection.setProcessed(true);
		paySelection.setDocAction(IDocument.ACTION_ReActivate);
	}

	private List<I_C_PaySelectionLine> getPaySelectionLines(final I_C_PaySelection paySelection)
	{
		return paySelectionDAO.retrievePaySelectionLines(paySelection);
	}

	@Override
	public void validateBankAccounts(final I_C_PaySelection paySelection)
	{
		final List<I_C_PaySelectionLine> paySelectionLines = getPaySelectionLines(paySelection);
		validateBankAccounts(paySelectionLines);
	}

	private void validateBankAccounts(final List<I_C_PaySelectionLine> paySelectionLines)
	{
		final StringJoiner linesWithoutBankAccount = new StringJoiner(",");

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			if (paySelectionLine.getC_BP_BankAccount_ID() <= 0)
			{
				linesWithoutBankAccount.add(String.valueOf(paySelectionLine.getLine()));
			}
		}

		if (linesWithoutBankAccount.length() != 0)
		{
			throw new AdempiereException(MSG_PaySelectionLines_No_BankAccount, new Object[] { linesWithoutBankAccount.toString() });
		}
	}

	@Override
	public void reactivatePaySelection(final I_C_PaySelection paySelection)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(paySelection))
		{
			if (isInBankStatement(paySelectionLine))
			{

				final BankStatementId bankStatementId = BankStatementId.ofRepoId(paySelectionLine.getC_BankStatement_ID());
				final String bankStatementDocumentNo = bankStatementBL.getDocumentNo(bankStatementId);
				throw new AdempiereException(
						MSG_CannotReactivate_PaySelectionLineInBankStatement_2P,
						new Object[] {
								paySelectionLine.getLine(),
								bankStatementDocumentNo
						});
			}
		}

		paySelection.setProcessed(false);
		paySelection.setDocAction(IDocument.ACTION_Complete);

	}

	@Override
	public Set<PaymentId> getPaymentIds(@NonNull final PaySelectionId paySelectionId)
	{
		return paySelectionDAO.retrievePaySelectionLines(paySelectionId)
				.stream()
				.map(line -> PaymentId.ofRepoIdOrNull(line.getC_Payment_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

}
