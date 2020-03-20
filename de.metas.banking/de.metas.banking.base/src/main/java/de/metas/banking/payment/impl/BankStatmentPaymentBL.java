package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

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

import com.google.common.collect.ImmutableSet;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatmentPaymentBL implements IBankStatmentPaymentBL
{
	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public void findOrCreateSinglePaymentAndLink(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		// Bank Statement Line is already reconciled => do nothing
		if (bankStatementBL.isReconciled(bankStatementLine))
		{
			return;
		}

		// if BPartner is not set, we cannot match it or generate a new payment
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		final Money statementAmt = extractStatementAmt(bankStatementLine);
		final boolean expectInboundPayment = statementAmt.signum() >= 0;
		final Money expectedPaymentAmount = statementAmt.negateIf(!expectInboundPayment);

		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final ImmutableSet<PaymentId> matchingPayments = paymentDAO.retrieveAllMatchingPayments(expectInboundPayment, bpartnerId, expectedPaymentAmount);

		if (matchingPayments.size() > 1)
		{
			// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
			// The user must fix this case manually by choosing the correct Payment
		}
		else if (matchingPayments.size() == 1)
		{
			final PaymentId paymentId = matchingPayments.iterator().next();
			linkSinglePayment(bankStatement, bankStatementLine, paymentId);
		}
		else
		{
			createSinglePaymentAndLink(bankStatement, bankStatementLine);
		}
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
		if (bankStatementBL.isReconciled(bankStatementLine))
		{
			throw new AdempiereException("Linking payment to an already reconciled bank statement line is not allowed");
		}

		bankStatementLine.setIsMultiplePaymentOrInvoice(false);
		bankStatementLine.setIsMultiplePayment(false);
		bankStatementLine.setC_Payment_ID(payment.getC_Payment_ID());
		bankStatementLine.setC_Currency_ID(payment.getC_Currency_ID());
		bankStatementLine.setC_BPartner_ID(payment.getC_BPartner_ID());
		bankStatementLine.setC_Invoice_ID(payment.getC_Invoice_ID());

		//
		final BigDecimal negateIfOutboundPayment = payment.isReceipt()
				? BigDecimal.ONE
				: BigDecimal.ONE.negate();

		// NOTE: don't touch the StmtAmt!
		bankStatementLine.setTrxAmt(payment.getPayAmt().multiply(negateIfOutboundPayment));

		bankStatementDAO.save(bankStatementLine);

		//
		// ReConcile payment if bank statement is processed
		final DocStatus bankStatementDocStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (bankStatementDocStatus.isCompleted())
		{
			final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
			paymentBL.markReconciled(payment);
		}
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
