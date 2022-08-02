package de.metas.banking.payment.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.X_C_BP_BankAccount;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;

public class PaySelectionBL implements IPaySelectionBL
{
	private static final AdMessageKey MSG_CannotReactivate_PaySelectionLineInBankStatement_2P = AdMessageKey.of("CannotReactivate_PaySelectionLineInBankStatement");
	private static final AdMessageKey MSG_PaySelectionLines_No_BankAccount = AdMessageKey.of("C_PaySelection_PaySelectionLines_No_BankAccount");

	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

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
		if (Check.isBlank(psl.getReference()) && InterfaceWrapperHelper.isNew(psl))
		{
			psl.setReference(invoice.getPOReference());
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

	/**
	 * Creates a payment for given pay selection line, links the payment to line and saves the line.
	 *
	 * A payment will be created only if
	 * <ul>
	 * <li>was not created before
	 * <li>line is not linked to a bank statement line ref
	 * </ul>
	 *
	 * @return newly created payment or <code>null</code> if no payment was generated.
	 */
	private org.compiere.model.I_C_Payment createPaymentIfNeeded(final I_C_PaySelectionLine line)
	{
		// Skip if a payment was already created
		if (line.getC_Payment_ID() > 0)
		{
			return null;
		}

		// Skip if this pay selection line is already in a bank statement
		// because in that case, the payment shall be generated there
		if (isReconciled(line))
		{
			return null;
		}

		try
		{
			final org.compiere.model.I_C_Payment payment = createPayment(line);
			line.setC_Payment(payment);
			paySelectionDAO.save(line);

			return payment;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed creating payment from " + line, e);
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
	private org.compiere.model.I_C_Payment createPayment(final I_C_PaySelectionLine line)
	{
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		final I_C_PaySelection paySelection = line.getC_PaySelection();
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(paySelection.getC_BP_BankAccount_ID());
		final LocalDate payDate = TimeUtil.asLocalDate(paySelection.getPayDate());

		return paymentBL.newBuilderOfInvoice(line.getC_Invoice())
				.adOrgId(OrgId.ofRepoId(line.getAD_Org_ID()))
				.orgBankAccountId(orgBankAccountId)
				.dateAcct(payDate)
				.dateTrx(payDate)
				.bpartnerId(BPartnerId.ofRepoId(line.getC_BPartner_ID()))
				.tenderType(TenderType.DirectDeposit)
				.payAmt(line.getPayAmt())
				.discountAmt(line.getDiscountAmt())
				//
				.createAndProcess();
	}

	@Override
	public void linkBankStatementLinesByPaymentIds(@NonNull final Map<PaymentId, BankStatementAndLineAndRefId> bankStatementAndLineAndRefIds)
	{
		if (bankStatementAndLineAndRefIds.isEmpty())
		{
			return;
		}

		final Set<PaymentId> paymentIds = bankStatementAndLineAndRefIds.keySet();

		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionDAO.retrievePaySelectionLines(paymentIds);
		if (paySelectionLines.isEmpty())
		{
			return;
		}

		final ImmutableMap<PaymentId, I_C_PaySelectionLine> paySelectionLinesByPaymentId = Maps.uniqueIndex(
				paySelectionLines,
				paySelectionLine -> PaymentId.ofRepoId(paySelectionLine.getC_Payment_ID()));

		for (final Map.Entry<PaymentId, I_C_PaySelectionLine> e : paySelectionLinesByPaymentId.entrySet())
		{
			final PaymentId paymentId = e.getKey();
			final I_C_PaySelectionLine paySelectionLine = e.getValue();
			final BankStatementAndLineAndRefId bankStatementLineRefId = bankStatementAndLineAndRefIds.get(paymentId);

			linkBankStatementLine(paySelectionLine, bankStatementLineRefId);
		}

		final ImmutableSet<PaySelectionId> paySelectionIds = extractPaySelectionIds(paySelectionLines);
		updatePaySelectionReconciledStatus(paySelectionIds);
	}

	@Override
	public void unlinkPaySelectionLineFromBankStatement(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
	{
		final List<I_C_PaySelectionLine> paySelectionLines = paySelectionDAO.retrievePaySelectionLinesByBankStatementLineIds(bankStatementLineIds);
		if (paySelectionLines.isEmpty())
		{
			return;
		}

		unlinkBankStatementFromLine(paySelectionLines);

		final ImmutableSet<PaySelectionId> paySelectionIds = extractPaySelectionIds(paySelectionLines);
		updatePaySelectionReconciledStatus(paySelectionIds);
	}

	private static ImmutableSet<PaySelectionId> extractPaySelectionIds(@NonNull final List<I_C_PaySelectionLine> paySelectionLines)
	{
		return paySelectionLines.stream()
				.map(paySelectionLine -> PaySelectionId.ofRepoId(paySelectionLine.getC_PaySelection_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@VisibleForTesting
	void updatePaySelectionReconciledStatus(@NonNull final Set<PaySelectionId> paySelectionIds)
	{
		if (paySelectionIds.isEmpty())
		{
			// shall NOT happen
			return;
		}

		final ImmutableSet<de.metas.banking.PaySelectionId> notReconciledPaySelectionIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelectionIds)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_BankStatement_ID, null) // not reconciled
				.create()
				.listDistinct(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, Integer.class)
				.stream()
				.map(PaySelectionId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		for (final I_C_PaySelection paySelection : paySelectionDAO.getByIds(paySelectionIds))
		{
			final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(paySelection.getC_PaySelection_ID());
			final boolean isReconciled = !notReconciledPaySelectionIds.contains(paySelectionId);

			paySelection.setIsReconciled(isReconciled);
			paySelectionDAO.save(paySelection);
		}
	}

	@VisibleForTesting
	static boolean isReconciled(@NonNull final I_C_PaySelectionLine psl)
	{
		return BankStatementId.ofRepoIdOrNull(psl.getC_BankStatement_ID()) != null;
	}

	private void linkBankStatementLine(
			@NonNull final I_C_PaySelectionLine psl,
			@NonNull final BankStatementAndLineAndRefId bankStatementAndLineAndRefId)
	{
		psl.setC_BankStatement_ID(bankStatementAndLineAndRefId.getBankStatementId().getRepoId());
		psl.setC_BankStatementLine_ID(bankStatementAndLineAndRefId.getBankStatementLineId().getRepoId());
		psl.setC_BankStatementLine_Ref_ID(bankStatementAndLineAndRefId.getBankStatementLineRefId().getRepoId());
		paySelectionDAO.save(psl);
	}

	private void unlinkBankStatementFromLine(@NonNull final Collection<I_C_PaySelectionLine> paySelectionLines)
	{
		for (final I_C_PaySelectionLine psl : paySelectionLines)
		{
			psl.setC_BankStatement_ID(-1);
			psl.setC_BankStatementLine_ID(-1);
			psl.setC_BankStatementLine_Ref_ID(-1);
			paySelectionDAO.save(psl);
		}
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
			throw new AdempiereException(MSG_PaySelectionLines_No_BankAccount, linesWithoutBankAccount.toString());
		}
	}

	@Override
	public void reactivatePaySelection(final I_C_PaySelection paySelection)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionDAO.retrievePaySelectionLines(paySelection))
		{
			if (isReconciled(paySelectionLine))
			{

				final BankStatementId bankStatementId = BankStatementId.ofRepoId(paySelectionLine.getC_BankStatement_ID());
				final String bankStatementDocumentNo = bankStatementBL.getDocumentNo(bankStatementId);
				throw new AdempiereException(
						MSG_CannotReactivate_PaySelectionLineInBankStatement_2P,
						paySelectionLine.getLine(),
						bankStatementDocumentNo);
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
