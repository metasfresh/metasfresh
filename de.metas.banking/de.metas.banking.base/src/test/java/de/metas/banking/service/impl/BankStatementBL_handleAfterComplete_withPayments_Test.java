package de.metas.banking.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.validator.C_BankStatement;
import de.metas.banking.model.validator.C_BankStatementLine;
import de.metas.banking.model.validator.C_BankStatementLine_Ref;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@ExtendWith(AdempiereTestWatcher.class)
public class BankStatementBL_handleAfterComplete_withPayments_Test
{
	private IPaymentBL paymentService = Services.get(IPaymentBL.class);

	private CurrencyId eurCurrencyId;

	private final String orgIBAN = "123456";
	private BankAccountId orgBankAccountId;

	private final Timestamp statementDate = SystemTime.asTimestamp();
	private final Timestamp valutaDate = SystemTime.asTimestamp();

	private BPartnerId customerId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(C_BankStatementLine_Ref.instance);
		modelInterceptorRegistry.addModelInterceptor(C_BankStatement.instance);
		modelInterceptorRegistry.addModelInterceptor(C_BankStatementLine.instance);

		createMasterData();
	}

	private void createMasterData()
	{
		eurCurrencyId = BusinessTestHelper.getEURCurrencyId();

		//
		// Org Bank Account
		final I_C_BPartner orgBPartner = BusinessTestHelper.createBPartner("metasfresh");
		final BPartnerId orgBPartnerId = BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID());
		final I_C_BP_BankAccount orgBankAccountEUR = BusinessTestHelper.createBpBankAccount(orgBPartnerId, eurCurrencyId, orgIBAN);
		orgBankAccountId = BankAccountId.ofRepoId(orgBankAccountEUR.getC_BP_BankAccount_ID());

		//
		// Customer
		final I_C_BPartner customer = BusinessTestHelper.createBPartner("le customer");
		customerId = BPartnerId.ofRepoId(customer.getC_BPartner_ID());
	}

	private Money eur(final String amt)
	{
		return Money.of(amt, eurCurrencyId);
	}

	public static I_C_BankStatement createBankStatement(final BankAccountId bankAccountId, final String name, final Timestamp statementDate)
	{
		final I_C_BankStatement bankStatement = newInstanceOutOfTrx(I_C_BankStatement.class);
		bankStatement.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		bankStatement.setName(name);
		bankStatement.setStatementDate(statementDate);
		save(bankStatement);

		return bankStatement;
	}

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "BankStatementLineBuilder")
	private static I_C_BankStatementLine createBankStatementLine(
			@NonNull final BankStatementId bankStatementId,
			final BPartnerId bpartnerId,
			final int lineNo,
			final Timestamp statementLineDate,
			final Timestamp valutaDate,
			@NonNull final Money stmtAmt)
	{
		final I_C_BankStatementLine bsl = newInstanceOutOfTrx(I_C_BankStatementLine.class);
		bsl.setC_BankStatement_ID(bankStatementId.getRepoId());
		bsl.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		bsl.setLine(lineNo);
		bsl.setStatementLineDate(statementLineDate);
		bsl.setValutaDate(valutaDate);
		bsl.setC_Currency_ID(stmtAmt.getCurrencyId().getRepoId());
		bsl.setStmtAmt(stmtAmt.toBigDecimal());
		save(bsl);

		return bsl;
	}

	@Value
	@Builder
	private static class PaymentExpectation
	{
		int paymentId;
		String payAmt;
		boolean receipt;
		BankAccountId bankAccountId;
	}

	private void assertPayment(@NonNull final PaymentExpectation expectation)
	{
		final I_C_Payment payment = InterfaceWrapperHelper.load(expectation.getPaymentId(), I_C_Payment.class);
		assertThat(payment).isNotNull();
		assertThat(payment.getPayAmt()).isEqualByComparingTo(expectation.getPayAmt());
		assertThat(payment.isReconciled()).isTrue();
		assertThat(payment.isReceipt()).isEqualTo(expectation.isReceipt());
		assertThat(payment.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
		assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(expectation.getBankAccountId().getRepoId());

		// can't test `payment.getC_DocType_ID()` as it is set by `PaymentsForInvoicesCreator`, and during test there's no DocTypes
	}

	@Nested
	class ExistingPayments
	{
		@Test
		void vendorOneMatchingPaymentExists_DifferentInvoiceOnBSL()
		{
			// TODO tbp: check with mark in a followup task about this usecase.
			// the followup task is: https://github.com/metasfresh/metasfresh/issues/6128
			// here is a draft of the data required to test
			// outgoing payment:
			// there is invoice for vendor: no. 1111
			// payment is allocated completely against invoice
			// amount 169.09 (same as BSL)
			// curency ok
			// bpartner ok
			// NOT reconciled!
			// ====================
			// BankStatementLine:
			// same bpartner as Invoice 1111
			// amount = 169.09 (same as payment)
			// reference = 2222 (this invoice number is different from the one on the payment!!!!!!!!)
			// ====================
			// => outcome: should not auto-link the payment, since the invoices are wrong
		}

		@Test
		void OneMatchingPaymentExists_SoItIsLinked()
		{
			final I_C_BankStatement bankStatement = createBankStatement(orgBankAccountId, "Bank Statement 1", statementDate);
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.lineNo(10)
					.statementLineDate(statementDate)
					.valutaDate(valutaDate)
					.stmtAmt(eur("-123"))
					.build();
			bankStatement.setDocStatus(DocStatus.Completed.getCode());

			final I_C_Payment existingPayment = paymentService.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.bpBankAccountId(orgBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate.toLocalDateTime().toLocalDate())
					.dateTrx(statementDate.toLocalDateTime().toLocalDate())
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			new BankStatementBL().handleAfterComplete(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			assertThat(bsl.getC_Payment_ID()).isEqualTo(existingPayment.getC_Payment_ID());
			assertThat(bsl.isMultiplePayment()).isFalse();
			assertThat(bsl.isMultiplePaymentOrInvoice()).isFalse();
			assertPayment(PaymentExpectation.builder()
					.paymentId(bsl.getC_Payment_ID())
					.payAmt("123")
					.receipt(false)
					.bankAccountId(orgBankAccountId)
					.build());
		}

		@Test
		void TwoIdenticalPaymentsExist_SoLineHasNoPaymentLinked()
		{
			final I_C_BankStatement bankStatement = createBankStatement(orgBankAccountId, "Bank Statement 1", statementDate);
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.lineNo(10)
					.statementLineDate(statementDate)
					.valutaDate(valutaDate)
					.stmtAmt(eur("-123"))
					.build();
			bankStatement.setDocStatus(DocStatus.Completed.getCode());

			//
			// create 2 identical payments
			final I_C_Payment payment1 = paymentService.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.bpBankAccountId(orgBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate.toLocalDateTime().toLocalDate())
					.dateTrx(statementDate.toLocalDateTime().toLocalDate())
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			final I_C_Payment payment2 = paymentService.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.bpBankAccountId(orgBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate.toLocalDateTime().toLocalDate())
					.dateTrx(statementDate.toLocalDateTime().toLocalDate())
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			//
			new BankStatementBL().handleAfterComplete(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			assertThat(bsl.getC_Payment_ID()).isZero();
			assertThat(bsl.isMultiplePayment()).isFalse();
			assertThat(bsl.isMultiplePaymentOrInvoice()).isFalse();

			InterfaceWrapperHelper.refresh(payment1);
			assertThat(payment1.isReconciled()).isFalse();

			InterfaceWrapperHelper.refresh(payment2);
			assertThat(payment2.isReconciled()).isFalse();
		}
	}

	@Nested
	class NoExistingPayment
	{
		@Test
		void OneInboundBankStatementLine_PaymentIsCreated()
		{
			final I_C_BankStatement bankStatement = createBankStatement(orgBankAccountId, "Bank Statement 1", statementDate);
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.lineNo(10)
					.statementLineDate(statementDate)
					.valutaDate(valutaDate)
					.stmtAmt(eur("123"))
					.build();
			bankStatement.setDocStatus(DocStatus.Completed.getCode());

			//
			// call tested method
			//
			new BankStatementBL().handleAfterComplete(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			assertThat(bsl.isMultiplePayment()).isFalse();
			assertThat(bsl.isMultiplePaymentOrInvoice()).isFalse();
			assertPayment(PaymentExpectation.builder()
					.paymentId(bsl.getC_Payment_ID())
					.payAmt("123")
					.receipt(true)
					.bankAccountId(orgBankAccountId)
					.build());
		}

		@Test
		void OneOutboundBankStatementLine_PaymentIsCreated()
		{
			final I_C_BankStatement bankStatement = createBankStatement(orgBankAccountId, "Bank Statement 1", statementDate);
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.lineNo(10)
					.statementLineDate(statementDate)
					.valutaDate(valutaDate)
					.stmtAmt(eur("-123"))
					.build();
			bankStatement.setDocStatus(DocStatus.Completed.getCode());

			//
			// call tested method
			//
			new BankStatementBL().handleAfterComplete(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			assertThat(bsl.isMultiplePayment()).isFalse();
			assertThat(bsl.isMultiplePaymentOrInvoice()).isFalse();
			assertPayment(PaymentExpectation.builder()
					.paymentId(bsl.getC_Payment_ID())
					.payAmt("123")
					.receipt(false)
					.bankAccountId(orgBankAccountId)
					.build());
		}
	}
}
