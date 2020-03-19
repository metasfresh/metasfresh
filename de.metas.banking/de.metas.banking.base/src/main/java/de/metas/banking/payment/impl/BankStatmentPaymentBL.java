package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.Nullable;

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
import de.metas.banking.payment.BankStatementLineReconcileRequest;
import de.metas.banking.payment.BankStatementLineReconcileResult;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
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
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public void setC_Payment(@NonNull final I_C_BankStatementLine line, @Nullable final I_C_Payment payment)
	{
		if (payment == null)
		{
			line.setC_Payment_ID(0);
			line.setDiscountAmt(BigDecimal.ZERO);
			line.setWriteOffAmt(BigDecimal.ZERO);
			line.setIsOverUnderPayment(false);
			line.setOverUnderAmt(BigDecimal.ZERO);
			setPayAmt(line, BigDecimal.ZERO);
			return;
		}

		line.setC_Payment_ID(payment.getC_Payment_ID());
		line.setC_Currency_ID(payment.getC_Currency_ID());
		line.setC_BPartner_ID(payment.getC_BPartner_ID());
		line.setC_Invoice_ID(payment.getC_Invoice_ID());
		//
		BigDecimal multiplier = BigDecimal.ONE;
		if (!payment.isReceipt())
		{
			multiplier = multiplier.negate();
		}

		final BigDecimal payAmt = payment.getPayAmt().multiply(multiplier);

		setPayAmt(line, payAmt);
		line.setDiscountAmt(payment.getDiscountAmt().multiply(multiplier));
		line.setWriteOffAmt(payment.getWriteOffAmt().multiply(multiplier));
		line.setOverUnderAmt(payment.getOverUnderAmt().multiply(multiplier));
		line.setIsOverUnderPayment(payment.isOverUnderPayment());
	}

	@Override
	public void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(@NonNull final I_C_BankStatement bankStatement, @NonNull final I_C_BankStatementLine line)
	{
		final boolean manualActionRequired = findAndLinkPaymentToBankStatementLineIfPossible(line);

		if (!manualActionRequired)
		{
			final PaymentId paymentIdToSet = null;
			setOrCreateAndLinkPaymentToBankStatementLine(bankStatement, line, paymentIdToSet);
		}
	}

	/**
	 * @return true if the automatic flow should STOP as manual action is required; false if the automatic flow should continue
	 */
	private boolean findAndLinkPaymentToBankStatementLineIfPossible(final I_C_BankStatementLine line)
	{
		// a payment is already linked
		if (line.getC_Payment_ID() > 0)
		{
			return true;
		}
		if (line.getC_BPartner_ID() <= 0)
		{
			return true;
		}

		final boolean isReceipt = line.getStmtAmt().signum() >= 0;
		final BigDecimal expectedPaymentAmount = isReceipt ? line.getStmtAmt() : line.getStmtAmt().negate();

		final Money money = Money.of(expectedPaymentAmount, CurrencyId.ofRepoId(line.getC_Currency_ID()));
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(line.getC_BPartner_ID());
		final ImmutableSet<PaymentId> possiblePayments = Services.get(IPaymentDAO.class).retrieveAllMatchingPayments(isReceipt, bPartnerId, money);

		// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
		// The user must fix this case manually by choosing the correct Payment
		if (possiblePayments.size() > 1)
		{
			return true;
		}

		if (possiblePayments.size() == 1)
		{
			line.setC_Payment_ID(possiblePayments.iterator().next().getRepoId());
			bankStatementDAO.save(line);
		}
		return false;
	}

	@Override
	public Optional<PaymentId> setOrCreateAndLinkPaymentToBankStatementLine(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine line,
			@Nullable final PaymentId paymentIdToSet)
	{
		// a payment is already linked
		if (line.getC_Payment_ID() > 0)
		{
			return Optional.of(PaymentId.ofRepoId(line.getC_Payment_ID()));
		}

		if (paymentIdToSet != null)
		{
			final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

			final I_C_Payment payment = paymentDAO.getById(paymentIdToSet);
			setC_Payment(line, payment);

			bankStatementDAO.save(line);
			paymentDAO.save(payment);
			return Optional.of(paymentIdToSet);
		}

		if (line.getC_BPartner_ID() <= 0)
		{
			return Optional.empty();
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(line.getC_BPartner_ID());
		final OrgId orgId = OrgId.ofRepoId(line.getAD_Org_ID());
		final LocalDate statementLineDate = TimeUtil.asLocalDate(line.getStatementLineDate());
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());

		final boolean isReceipt = line.getStmtAmt().signum() >= 0;
		final BigDecimal payAmount = isReceipt ? line.getStmtAmt() : line.getStmtAmt().negate();

		final I_C_Payment createdPayment = createAndCompletePayment(orgBankAccountId, statementLineDate, payAmount, isReceipt, orgId, bpartnerId, currencyId);
		setC_Payment(line, createdPayment);

		bankStatementDAO.save(line);
		return Optional.of(PaymentId.ofRepoId(createdPayment.getC_Payment_ID()));
	}

	private I_C_Payment createAndCompletePayment(
			@NonNull final BankAccountId orgBankAccountId,
			@NonNull final LocalDate dateAcct,
			@NonNull final BigDecimal payAmt,
			final boolean isReceipt,
			@NonNull final OrgId adOrgId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CurrencyId currencyId)
	{
		final DefaultPaymentBuilder paymentBuilder;

		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
		if (isReceipt)
		{
			paymentBuilder = paymentBL.newInboundReceiptBuilder();
		}
		else
		{
			paymentBuilder = paymentBL.newOutboundPaymentBuilder();
		}

		return paymentBuilder
				.adOrgId(adOrgId)
				.bpartnerId(bpartnerId)
				.bpBankAccountId(orgBankAccountId)
				.currencyId(currencyId)
				.payAmt(payAmt)
				.dateAcct(dateAcct)
				.dateTrx(dateAcct)
				.description("Automatically created when BankStatement was completed.")
				.tenderType(TenderType.DirectDeposit)
				.createAndProcess();
	}

	private void setPayAmt(@NonNull final I_C_BankStatementLine line, final BigDecimal payAmt)
	{
		line.setTrxAmt(payAmt);
		if (line.getStmtAmt().signum() == 0)
		{
			line.setStmtAmt(payAmt);
		}
	}

	@Override
	public BankStatementLineReconcileResult reconcile(@NonNull final BankStatementLineReconcileRequest request)
	{
		return BankStatementLineReconcileCommand.builder()
				.moneyService(SpringContextHolder.instance.getBean(MoneyService.class))
				.request(request)
				.build()
				.execute();
	}
}
