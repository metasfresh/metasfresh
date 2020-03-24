package de.metas.banking.payment.impl;

import java.time.LocalDate;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.api.PaymentQuery;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatementPaymentBL implements IBankStatementPaymentBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public void findOrCreateSinglePaymentAndLinkIfPossible(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		// Bank Statement Line is already reconciled => do nothing
		if (bankStatementLine.isReconciled())
		{
			return;
		}

		// if BPartner is not set, we cannot match it or generate a new payment
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		final Set<PaymentId> eligiblePaymentIds = findEligiblePaymentIds(bankStatementLine, bpartnerId, 2);
		if (eligiblePaymentIds.size() > 1)
		{
			// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
			// The user must fix this case manually by choosing the correct Payment
		}
		else if (eligiblePaymentIds.size() == 1)
		{
			final PaymentId paymentId = eligiblePaymentIds.iterator().next();
			linkSinglePayment(bankStatement, bankStatementLine, paymentId);
		}
		else
		{
			createSinglePaymentAndLink(bankStatement, bankStatementLine);
		}
	}

	@Override
	public Set<PaymentId> findEligiblePaymentIds(
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final BPartnerId bpartnerId,
			final int limit)
	{
		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

		final Money statementAmt = extractStatementAmt(bankStatementLine);
		final PaymentDirection expectedPaymentDirection = PaymentDirection.ofBankStatementAmount(statementAmt);
		final Money expectedPaymentAmount = expectedPaymentDirection.convertStatementAmtToPayAmt(statementAmt);

		return paymentDAO.retrievePaymentIds(PaymentQuery.builder()
				.limit(limit)
				.docStatus(DocStatus.Completed)
				.reconciled(false)
				.direction(expectedPaymentDirection)
				.bpartnerId(bpartnerId)
				.payAmt(expectedPaymentAmount)
				.build());
	}

	private static Money extractStatementAmt(final I_C_BankStatementLine line)
	{
		return Money.of(line.getStmtAmt(), CurrencyId.ofRepoId(line.getC_Currency_ID()));
	}

	@Override
	public void createSinglePaymentAndLink(final I_C_BankStatement bankStatement, final I_C_BankStatementLine bankStatementLine)
	{
		final I_C_Payment payment = createPayment(bankStatement, bankStatementLine);
		linkSinglePayment(bankStatement, bankStatementLine, payment);
	}

	private I_C_Payment createPayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			throw new AdempiereException("Bank statement line's BPartner is not set");
		}

		// final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final OrgId orgId = OrgId.ofRepoId(bankStatementLine.getAD_Org_ID());
		final LocalDate statementLineDate = TimeUtil.asLocalDate(bankStatementLine.getStatementLineDate());
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());

		final Money statementAmt = extractStatementAmt(bankStatementLine);
		final boolean inboundPayment = statementAmt.signum() >= 0;
		final Money payAmount = statementAmt.negateIf(!inboundPayment);

		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		final DefaultPaymentBuilder paymentBuilder = inboundPayment
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		return paymentBuilder
				.adOrgId(orgId)
				.bpartnerId(bpartnerId)
				.orgBankAccountId(orgBankAccountId)
				.currencyId(payAmount.getCurrencyId())
				.payAmt(payAmount.toBigDecimal())
				.dateAcct(statementLineDate)
				.dateTrx(statementLineDate)
				.tenderType(TenderType.Check)
				.createAndProcess();
	}

	@Override
	public void linkSinglePayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final PaymentId paymentId)
	{
		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		linkSinglePayment(bankStatement, bankStatementLine, payment);
	}

	@Override
	public void linkSinglePayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final I_C_Payment payment)
	{
		// a payment is already linked
		if (bankStatementLine.isReconciled())
		{
			throw new AdempiereException("Linking payment to an already reconciled bank statement line is not allowed");
		}

		bankStatementLine.setIsReconciled(true);
		bankStatementLine.setIsMultiplePaymentOrInvoice(false);
		bankStatementLine.setIsMultiplePayment(false);

		bankStatementLine.setC_Payment_ID(payment.getC_Payment_ID());
		bankStatementLine.setC_BPartner_ID(payment.getC_BPartner_ID());
		bankStatementLine.setC_Invoice_ID(payment.getC_Invoice_ID());

		//
		final PaymentDirection paymentDirection = extractPaymentDirection(payment);
		final Money payAmt = extractPayAmt(payment);
		final Money trxAmt = paymentDirection.convertPayAmtToStatementAmt(payAmt);
		// NOTE: don't touch the StmtAmt!
		bankStatementLine.setC_Currency_ID(trxAmt.getCurrencyId().getRepoId());
		bankStatementLine.setTrxAmt(trxAmt.toBigDecimal());

		bankStatementDAO.save(bankStatementLine);

		//
		// ReConcile payment if bank statement is processed
		final DocStatus bankStatementDocStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (bankStatementDocStatus.isCompleted())
		{
			final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
			paymentBL.markReconciledAndSave(payment);
		}
	}

	private static PaymentDirection extractPaymentDirection(final I_C_Payment payment)
	{
		return PaymentDirection.ofReceiptFlag(payment.isReceipt());
	}

	private Money extractPayAmt(@NonNull final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		return Money.of(payment.getPayAmt(), currencyId);
	}

	@Override
	public BankStatementLineMultiPaymentLinkResult linkMultiPayments(@NonNull final BankStatementLineMultiPaymentLinkRequest request)
	{
		return BankStatementLineMultiPaymentLinkCommand.builder()
				.moneyService(SpringContextHolder.instance.getBean(MoneyService.class))
				.request(request)
				.build()
				.execute();
	}
}
