/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.banking.BankStatementTestHelper;
import de.metas.banking.api.BankAccountId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.payment.impl.BankStatmentPaymentBL;
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

class BankStatementPaymentBLTest
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private final String metasfreshIban = "123456";
	private final LocalDate statementDate = SystemTime.asLocalDate();
	private final LocalDate valutaDate = SystemTime.asLocalDate();

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(new C_BankStatementLine_MockedInterceptor());
	}

	private void paymentChecks(
			final String expectedPayAmt,
			final int expectedC_payment_id,
			final boolean expectedIsReceipt,
			final BankAccountId expectedBankAccountId)
	{
		final I_C_Payment payment = InterfaceWrapperHelper.load(expectedC_payment_id, I_C_Payment.class);
		assertThat(payment).isNotNull();
		assertThat(payment.getPayAmt()).isEqualTo(expectedPayAmt);
		assertThat(payment.isReconciled()).isTrue();
		assertThat(payment.isReceipt()).isEqualTo(expectedIsReceipt);
		assertThat(DocStatus.ofCode(payment.getDocStatus())).isEqualTo(DocStatus.Completed);
		assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(expectedBankAccountId.getRepoId());

		// can't test `payment.getC_DocType_ID()` as it is set by `PaymentsForInvoicesCreator`, and during test there's no DocTypes
	}

	@Nested
	class ExistingPayments
	{
		@Test
		@Disabled("not implemented")
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
			//
			// create test data
			final CurrencyId eurCurrencyId = BusinessTestHelper.getEURCurrencyId();

			final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
			final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()), eurCurrencyId, metasfreshIban);
			final BankAccountId metasfreshBankAccountId = BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());

			final I_C_BankStatement bankStatement = BankStatementTestHelper.createBankStatement(metasfreshBankAccountId, "Bank Statement 1", statementDate);

			final I_C_BPartner customerBPartner = BusinessTestHelper.createBPartner("le customer");

			final I_C_BankStatementLine bsl = BankStatementTestHelper.createBankStatementLine(
					BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()),
					BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()),
					10,
					statementDate,
					valutaDate,
					Money.of(-123, eurCurrencyId));

			final org.compiere.model.I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()))
					.bpBankAccountId(metasfreshBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			final BankStatmentPaymentBL testBankStatementPaymentBL = new BankStatmentPaymentBL();
			testBankStatementPaymentBL.findOrCreateSinglePaymentAndLink(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			final boolean isReceipt = false;
			assertEquals(payment.getC_Payment_ID(), bsl.getC_Payment_ID());
			paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, metasfreshBankAccountId);
			assertFalse(bsl.isMultiplePayment());
			assertFalse(bsl.isMultiplePaymentOrInvoice());
		}

		@Test
		void TwoIdenticalPaymentsExist_SoLineHasNoPaymentLinked()
		{
			//
			// create test data
			final CurrencyId eurCurrencyId = BusinessTestHelper.getEURCurrencyId();

			final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
			final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()), eurCurrencyId, metasfreshIban);
			final BankAccountId metasfreshBankAccountId = BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());

			final I_C_BankStatement bankStatement = BankStatementTestHelper.createBankStatement(metasfreshBankAccountId, "Bank Statement 1", statementDate);

			final I_C_BPartner customerBPartner = BusinessTestHelper.createBPartner("le customer");

			final I_C_BankStatementLine bsl = BankStatementTestHelper.createBankStatementLine(
					BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()),
					BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()),
					10,
					statementDate,
					valutaDate,
					Money.of(-123, eurCurrencyId));

			//
			// create 2 identical payments
			final org.compiere.model.I_C_Payment payment1 = paymentBL.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()))
					.bpBankAccountId(metasfreshBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			final org.compiere.model.I_C_Payment payment2 = paymentBL.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()))
					.bpBankAccountId(metasfreshBankAccountId)
					.currencyId(eurCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			final BankStatmentPaymentBL testBankStatementBL = new BankStatmentPaymentBL();
			testBankStatementBL.findOrCreateSinglePaymentAndLink(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			assertEquals(0, bsl.getC_Payment_ID());
			assertFalse(bsl.isMultiplePayment());
			assertFalse(bsl.isMultiplePaymentOrInvoice());

			InterfaceWrapperHelper.refresh(payment1);
			InterfaceWrapperHelper.refresh(payment2);
			assertFalse(payment1.isReconciled());
			assertFalse(payment2.isReconciled());
		}
	}

	@Nested
	class NoExistingPayment
	{

		@Test
		void OneInboundBankStatementLine_PaymentIsCreated()
		{
			//
			// create test data
			final CurrencyId eurCurrencyId = BusinessTestHelper.getEURCurrencyId();

			final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
			final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()), eurCurrencyId, metasfreshIban);
			final BankAccountId metasfreshBankAccountId = BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());

			final I_C_BankStatement bankStatement = BankStatementTestHelper.createBankStatement(metasfreshBankAccountId, "Bank Statement 1", statementDate);

			final I_C_BPartner customerBPartner = BusinessTestHelper.createBPartner("le customer");

			final I_C_BankStatementLine bsl = BankStatementTestHelper.createBankStatementLine(
					BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()),
					BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()),
					10,
					statementDate,
					valutaDate,
					Money.of(123, eurCurrencyId));

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			final BankStatmentPaymentBL testBankStatementBL = new BankStatmentPaymentBL();
			testBankStatementBL.findOrCreateSinglePaymentAndLink(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			final boolean isReceipt = true;
			paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, metasfreshBankAccountId);
			assertFalse(bsl.isMultiplePayment());
			assertFalse(bsl.isMultiplePaymentOrInvoice());
		}

		@Test
		void OneOutboundBankStatementLine_PaymentIsCreated()
		{
			//
			// create test data
			final CurrencyId eurCurrencyId = BusinessTestHelper.getEURCurrencyId();

			final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
			final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()), eurCurrencyId, metasfreshIban);
			final BankAccountId metasfreshBankAccountId = BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());

			final I_C_BankStatement bankStatement = BankStatementTestHelper.createBankStatement(metasfreshBankAccountId, "Bank Statement 1", statementDate);

			final I_C_BPartner customerBPartner = BusinessTestHelper.createBPartner("le customer");

			final I_C_BankStatementLine bsl = BankStatementTestHelper.createBankStatementLine(
					BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()),
					BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()),
					10,
					statementDate,
					valutaDate,
					Money.of(-123, eurCurrencyId));

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			final BankStatmentPaymentBL testBankStatementBL = new BankStatmentPaymentBL();
			testBankStatementBL.findOrCreateSinglePaymentAndLink(bankStatement, bsl);

			//
			// Checks
			InterfaceWrapperHelper.refresh(bsl);
			final boolean isReceipt = false;
			paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, metasfreshBankAccountId);
			assertFalse(bsl.isMultiplePayment());
			assertFalse(bsl.isMultiplePaymentOrInvoice());
		}
	}
}
