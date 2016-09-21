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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.X_C_BP_BankAccount;

import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.payment.api.DefaultPaymentBuilder.TenderType;
import de.metas.payment.api.IPaymentBL;

public class PaySelectionBL implements IPaySelectionBL
{
	private static final String MSG_CannotReactivate_PaySelectionLineInBankStatement_2P = "CannotReactivate_PaySelectionLineInBankStatement";

	/**
	 * @return true if given pay selection line is imported in a bank statement
	 */
	private boolean isInBankStatement(final I_C_PaySelectionLine psl)
	{
		return psl.getC_BankStatementLine_ID() > 0 || psl.getC_BankStatementLine_Ref_ID() > 0;
	}

	@Override
	public void createBankStatementLines(
			final I_C_BankStatement bankStatement,
			final I_C_PaySelection paySelection)
	{
		Check.errorIf(bankStatement.getC_BP_BankAccount_ID() != paySelection.getC_BP_BankAccount_ID(),
				"C_BankStatement {} with C_BP_BankAccount_ID={} and C_PaySelection {} with C_BP_BankAccount_ID={} need to have the same C_BP_BankAccount_ID",
				bankStatement, bankStatement.getC_BP_BankAccount_ID(), paySelection, paySelection.getC_BP_BankAccount_ID());

		// services
		final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		I_C_BankStatementLine bankStatementLine = null;
		int nextReferenceLineNo = 10;

		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionDAO.retrievePaySelectionLines(paySelection, I_C_PaySelectionLine.class);
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
				bankStatementLine = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine.class, paySelection);
				bankStatementLine.setAD_Org_ID(paySelection.getAD_Org_ID());
				bankStatementLine.setC_BankStatement(bankStatement);
				bankStatementLine.setIsMultiplePaymentOrInvoice(true); // we have a reference line for each invoice
				bankStatementLine.setIsMultiplePayment(true); // each invoice shall have it's own payment
				bankStatementLine.setC_Currency_ID(bankStatement.getC_BP_BankAccount().getC_Currency_ID());
				bankStatementLine.setValutaDate(paySelection.getPayDate());
				bankStatementLine.setDateAcct(paySelection.getPayDate());
				bankStatementLine.setStatementLineDate(paySelection.getPayDate());
				bankStatementLine.setReferenceNo(null); // no ReferenceNo at this level
				bankStatementLine.setC_BPartner(null); // no partner because we will have it on "line reference" level
				bankStatementLine.setStmtAmt(BigDecimal.ZERO); // will be updated at the end
				bankStatementLine.setTrxAmt(BigDecimal.ZERO); // will be updated at the end
				bankStatementLine.setChargeAmt(BigDecimal.ZERO);
				bankStatementLine.setInterestAmt(BigDecimal.ZERO);
				InterfaceWrapperHelper.save(bankStatementLine);
			}

			//
			// Create new bank statement line reference for our current pay selection line.
			final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class, bankStatementLine);
			bankStatementLineRef.setAD_Org_ID(bankStatementLine.getAD_Org_ID());
			bankStatementLineRef.setC_BankStatementLine(bankStatementLine);
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
			InterfaceWrapperHelper.save(bankStatementLineRef);
			nextReferenceLineNo += 10;

			//
			// Update pay selection line => mark it as reconciled
			linkBankStatementLine(psl, bankStatementLine, bankStatementLineRef);
		}

		//
		// Update Bank Statement Line's totals:
		if (bankStatementLine != null)
		{
			bankStatementBL.recalculateStatementLineAmounts(bankStatementLine);
		}

	}

	@Override
	public void updateFromInvoice(final org.compiere.model.I_C_PaySelectionLine psl)
	{
		final I_C_PaySelectionLine pslExt = InterfaceWrapperHelper.create(psl, I_C_PaySelectionLine.class);

		if (Services.get(IPaymentRequestBL.class).isUpdatedFromPaymentRequest(pslExt))
		{
			return;
		}

		final I_C_Invoice invoice = pslExt.getC_Invoice();

		if (invoice == null)
		{
			return; // nothing to do yet, but as C_PaySelectionLine.C_Invoice_ID is mandatory, we only need to make sure this method is eventually called from a model interceptor
		}

		final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(pslExt);

		final int partnerID = invoice.getC_BPartner_ID();
		pslExt.setC_BPartner_ID(partnerID);

		// task 09500 get the currency from the account of the selection header
		// this is safe because the columns are mandatory
		final int currencyID = pslExt.getC_PaySelection().getC_BP_BankAccount().getC_Currency_ID();

		final boolean isSalesInvoice = invoice.isSOTrx();

		final boolean isCreditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);

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
				pslExt.setC_BP_BankAccount_ID(primaryAcct);
			}
			else if (secondaryAcct != 0)
			{
				pslExt.setC_BP_BankAccount_ID(secondaryAcct);
			}
		}

		// 08297: After trying to set the Reference from the payment request, fallback (if still empty) to the Invoice's POReference
		final boolean trimWhitespaces = true;
		if (Check.isEmpty(pslExt.getReference(), trimWhitespaces))
		{
			final String invoicePOReference = invoice.getPOReference();
			if (Check.isEmpty(invoicePOReference, trimWhitespaces))
			{
				return;
			}
			pslExt.setReference(invoicePOReference);
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
		final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(paySelection, I_C_PaySelectionLine.class))
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
			InterfaceWrapperHelper.save(line);

			return payment;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Caught " + e + " while trying to create a payment for C_PaySelectionLine " + line, e);
		}
	}

	/**
	 * Generates a payment for given pay selection line. The payment will be also processed.
	 *
	 * NOTE: this method is NOT checking if the payment was already created or it's not needed.
	 *
	 * @param line
	 * @return generated payment.
	 */
	private I_C_Payment createPayment(final I_C_PaySelectionLine line)
	{
		final I_C_PaySelection paySelection = line.getC_PaySelection();
		final int ownBankAccountId = paySelection.getC_BP_BankAccount_ID();
		final Timestamp payDate = paySelection.getPayDate();

		final org.compiere.model.I_C_Payment payment = Services.get(IPaymentBL.class).newBuilder(line)
				.setC_Invoice(line.getC_Invoice())
				.setAD_Org_ID(line.getAD_Org_ID())
				.setC_BP_BankAccount_ID(ownBankAccountId)
				.setDateAcct(payDate)
				.setDateTrx(payDate)
				.setC_BPartner_ID(line.getC_BPartner_ID())
				.setTenderType(TenderType.ACH)
				.setPayAmt(line.getPayAmt())
				.setDiscountAmt(line.getDiscountAmt())
				//
				.createAndProcess();

		return InterfaceWrapperHelper.create(payment, I_C_Payment.class);
	}

	@Override
	public void linkBankStatementLine(
			final I_C_PaySelectionLine psl,
			final org.compiere.model.I_C_BankStatementLine bankStatementLine,
			final de.metas.banking.model.I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		Check.assumeNotNull(bankStatementLine, "bankStatementLine not null");
		Check.assume(bankStatementLine.getC_BankStatementLine_ID() > 0, "bankStatementLine is saved: {}", bankStatementLine);

		psl.setC_BankStatementLine(bankStatementLine);
		psl.setC_BankStatementLine_Ref(bankStatementLineRef);
		InterfaceWrapperHelper.save(psl);
	}

	/**
	 * Unlink any bank statement line from given pay selection line.
	 *
	 * @param psl
	 */
	private void unlinkBankStatementLine(final I_C_PaySelectionLine psl)
	{
		psl.setC_BankStatementLine(null);
		psl.setC_BankStatementLine_Ref(null);
		InterfaceWrapperHelper.save(psl);
	}

	@Override
	public void unlinkPaySelectionLineForBankStatement(final I_C_BankStatementLine bankStatementLine)
	{
		for (final I_C_PaySelectionLine paySelectionLine : Services.get(IPaySelectionDAO.class).retrievePaySelectionLines(bankStatementLine))
		{
			unlinkBankStatementLine(paySelectionLine);
		}
	}

	@Override
	public void unlinkPaySelectionLineForBankStatement(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final I_C_PaySelectionLine paySelectionLine = Services.get(IPaySelectionDAO.class).retrievePaySelectionLine(bankStatementLineRef);
		if (paySelectionLine != null)
		{
			unlinkBankStatementLine(paySelectionLine);
		}
	}

	@Override
	public void reActivate(final I_C_PaySelection paySelection)
	{
		if (!paySelection.isProcessed())
		{
			// already re-activated, nothing to do
			return;
		}

		final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(paySelection, I_C_PaySelectionLine.class))
		{
			paySelectionLine.setC_PaySelection(paySelection); // for optimizations
			reActivate(paySelectionLine);
		}

		paySelection.setProcessed(false);
		InterfaceWrapperHelper.save(paySelection);
	}

	private final void reActivate(final I_C_PaySelectionLine psl)
	{
		if (!psl.isProcessed())
		{
			return;
		}

		if (isInBankStatement(psl))
		{
			throw new AdempiereException(MSG_CannotReactivate_PaySelectionLineInBankStatement_2P, new Object[] { psl.getLine(), psl.getC_BankStatementLine().getC_BankStatement().getDocumentNo() });
		}

		psl.setProcessed(false);
		InterfaceWrapperHelper.save(psl);
	}
}
