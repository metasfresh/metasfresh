package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
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
import de.metas.banking.model.BankStatementPaymentInfo;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
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
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	public void setPayment(@NonNull final IBankStatementLineOrRef lineOrRef, @Nullable final PaymentId paymentId)
	{
		final BankStatementPaymentInfo paymentInfo = paymentId != null
				? toBankStatementPaymentInfo(paymentBL.getById(paymentId))
				: null;
		setPayment(lineOrRef, paymentInfo);
	}

	public static BankStatementPaymentInfo toBankStatementPaymentInfo(@NonNull final I_C_Payment payment)
	{
		return BankStatementPaymentInfo.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.invoiceId(InvoiceId.optionalOfRepoId(payment.getC_Invoice_ID()))
				.build();
	}

	@Override
	public void setPayment(@NonNull final IBankStatementLineOrRef lineOrRef, @Nullable final BankStatementPaymentInfo payment)
	{
		if (payment == null)
		{
			lineOrRef.setC_Payment_ID(-1);
			setPayAmt(lineOrRef, BigDecimal.ZERO);
		}
		else
		{
			lineOrRef.setC_Payment_ID(payment.getPaymentId().getRepoId());
			lineOrRef.setC_Currency_ID(payment.getPayAmt().getCurrencyId().getRepoId());
			lineOrRef.setC_BPartner_ID(payment.getBpartnerId().getRepoId());
			lineOrRef.setC_Invoice_ID(payment.getInvoiceId().map(InvoiceId::getRepoId).orElse(-1));
			//
			final BigDecimal negateIfOutbound = payment.isInboundPayment() ? BigDecimal.ONE : BigDecimal.ONE.negate();
			final BigDecimal payAmt = payment.getPayAmt().toBigDecimal().multiply(negateIfOutbound);
			setPayAmt(lineOrRef, payAmt);
		}

		lineOrRef.setDiscountAmt(BigDecimal.ZERO);
		lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
		lineOrRef.setIsOverUnderPayment(false);
		lineOrRef.setOverUnderAmt(BigDecimal.ZERO);

	}

	@Override
	public void findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(
			@NonNull final org.compiere.model.I_C_BankStatement bankStatement,
			@NonNull final de.metas.banking.model.I_C_BankStatementLine line)
	{
		//
		// a payment is already linked
		if (line.getC_Payment_ID() > 0)
		{
			return;
		}

		//
		// Find matching payments
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}
		final ImmutableSet<PaymentId> possiblePayments = getAllNotReconciledPaymentsMatching(line, bpartnerId);
		if (possiblePayments.isEmpty())
		{
			final PaymentId paymentId = null;
			setOrCreateAndLinkPaymentToBankStatementLine(bankStatement, line, paymentId);
		}
		else if (possiblePayments.size() == 1)
		{
			final PaymentId paymentId = possiblePayments.iterator().next();
			setOrCreateAndLinkPaymentToBankStatementLine(bankStatement, line, paymentId);
		}
		else // if (possiblePayments.size() > 1)
		{
			// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
			// The user must fix this case manually by choosing the correct Payment
		}
	}

	@Override
	public ImmutableSet<PaymentId> getAllNotReconciledPaymentsMatching(@NonNull final I_C_BankStatementLine line, final BPartnerId bpartnerId)
	{
		final IPaymentDAO paymentsRepo = Services.get(IPaymentDAO.class);

		final Money stmtAmt = extractStmtAmtAsMoney(line);
		final boolean isInboundPayment = isInboundPayment(line);
		final Money expectedPaymentAmount = isInboundPayment ? stmtAmt : stmtAmt.negate();
		return paymentsRepo.retrieveAllNotReconciledPaymentsMatching(isInboundPayment, bpartnerId, expectedPaymentAmount);
	}

	private static Money extractStmtAmtAsMoney(@NonNull final I_C_BankStatementLine line)
	{
		return Money.of(line.getStmtAmt(), CurrencyId.ofRepoId(line.getC_Currency_ID()));
	}

	private static boolean isInboundPayment(@NonNull final I_C_BankStatementLine line)
	{
		return line.getStmtAmt().signum() >= 0;
	}

	@Override
	public void setOrCreateAndLinkPaymentToBankStatementLine(
			@NonNull final org.compiere.model.I_C_BankStatement bankStatement,
			@NonNull final de.metas.banking.model.I_C_BankStatementLine line,
			@Nullable final PaymentId paymentIdToSet)
	{
		//
		// if a payment is already linked
		final PaymentId currentPaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		if (currentPaymentId != null)
		{
			if (PaymentId.equals(currentPaymentId, paymentIdToSet))
			{
				return;
			}
			else if (paymentIdToSet == null)
			{
				throw new AdempiereException("Specify a payment if you want to change current payment");
			}
			else
			{
				setPayment(line, (BankStatementPaymentInfo)null);
			}
		}

		if (paymentIdToSet != null)
		{
			setPayment(line, paymentIdToSet);
		}
		else
		{
			final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());
			final BankStatementPaymentInfo createdPayment = createAndCompletePayment(orgBankAccountId, line);
			setPayment(line, createdPayment);
		}

		bankStatementDAO.save(line);
	}

	private BankStatementPaymentInfo createAndCompletePayment(
			@NonNull final BankAccountId orgBankAccountId,
			@NonNull final I_C_BankStatementLine line)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(line.getC_BPartner_ID());
		final OrgId orgId = OrgId.ofRepoId(line.getAD_Org_ID());
		final LocalDate statementLineDate = TimeUtil.asLocalDate(line.getStatementLineDate());
		final Money statementAmt = extractStmtAmtAsMoney(line);
		final boolean isInboundPayment = isInboundPayment(line);
		final Money payAmount = isInboundPayment ? statementAmt : statementAmt.negate();

		final DefaultPaymentBuilder paymentBuilder = isInboundPayment
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		final I_C_Payment payment = paymentBuilder
				.adOrgId(orgId)
				.bpartnerId(bpartnerId)
				.bpBankAccountId(orgBankAccountId)
				.currencyId(payAmount.getCurrencyId())
				.payAmt(payAmount.toBigDecimal())
				.dateAcct(statementLineDate)
				.dateTrx(statementLineDate)
				.tenderType(TenderType.Check)
				.createAndProcess();

		return toBankStatementPaymentInfo(payment);
	}

	public void setPayAmt(final IBankStatementLineOrRef lineOrRef, final BigDecimal payAmt)
	{
		setPayAmt(lineOrRef, payAmt, false);
	}

	public void setPayAmt(@NonNull final IBankStatementLineOrRef lineOrRef, final BigDecimal payAmt, final boolean updateStatementAmt)
	{
		if (InterfaceWrapperHelper.isInstanceOf(lineOrRef, I_C_BankStatementLine.class))
		{
			final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(lineOrRef, I_C_BankStatementLine.class);
			bsl.setTrxAmt(payAmt);
			if (updateStatementAmt || bsl.getStmtAmt().signum() == 0)
			{
				bsl.setStmtAmt(payAmt);
			}
		}
		else if (InterfaceWrapperHelper.isInstanceOf(lineOrRef, I_C_BankStatementLine_Ref.class))
		{
			final I_C_BankStatementLine_Ref lineRef = InterfaceWrapperHelper.create(lineOrRef, I_C_BankStatementLine_Ref.class);
			lineRef.setTrxAmt(payAmt);
		}
		else
		{
			throw new IllegalStateException("Object not supported: " + lineOrRef);
		}
	}
}
