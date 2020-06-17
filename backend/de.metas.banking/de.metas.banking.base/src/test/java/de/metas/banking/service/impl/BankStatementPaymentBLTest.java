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

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Trace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.BankId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest.PaymentToLink;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.payment.impl.BankStatementPaymentBL;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListener;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;

class BankStatementPaymentBLTest
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private IBankStatementListenerService bankStatementListenerService;
	private BankStatementPaymentBL bankStatementPaymentBL;
	private BankRepository bankRepo;

	private final String metasfreshIban = "123456";
	private final LocalDate statementDate = SystemTime.asLocalDate();
	private final LocalDate valutaDate = SystemTime.asLocalDate();

	private CurrencyId euroCurrencyId;
	private BankAccountId euroOrgBankAccountId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BankAccountService bankAccountService = BankAccountService.newInstanceForUnitTesting();
		final BankStatementBL bankStatementBL = new BankStatementBL(bankAccountService)
		{
			public void unpost(I_C_BankStatement bankStatement)
			{
				System.out.println("In JUnit test BankStatementBL.unpost() does nothing"
						+ "\n\t bank statement: " + bankStatement
						+ "\n\t called via " + Trace.toOneLineStackTraceString());
			}
		};

		bankStatementListenerService = Services.get(IBankStatementListenerService.class);
		bankStatementPaymentBL = new BankStatementPaymentBL(
				bankStatementBL,
				new MoneyService(new CurrencyRepository()));

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(new C_BankStatementLine_MockedInterceptor(bankStatementBL));

		bankRepo = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepo);

		final BankAccountAcctRepository bankAccountAcctRepo = new BankAccountAcctRepository();
		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepo, bankAccountAcctRepo, currencyRepo));

		createMasterData();
	}

	private void createMasterData()
	{
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		euroOrgBankAccountId = createOrgBankAccount(euroCurrencyId);
	}

	private BPartnerId createCustomer()
	{
		I_C_BPartner customer = BusinessTestHelper.createBPartner("le customer");
		return BPartnerId.ofRepoId(customer.getC_BPartner_ID());
	}

	private BankAccountId createOrgBankAccount(final CurrencyId euroCurrencyId)
	{
		final I_C_BPartner orgBPartner = BusinessTestHelper.createBPartner("metasfresh");
		final BankId bankId = createBank();

		final I_C_BP_BankAccount orgBankAccount = BusinessTestHelper.createBpBankAccount(
				BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()),
				euroCurrencyId,
				metasfreshIban);
		orgBankAccount.setC_Bank_ID(bankId.getRepoId());
		saveRecord(orgBankAccount);

		return BankAccountId.ofRepoId(orgBankAccount.getC_BP_BankAccount_ID());
	}

	private BankId createBank()
	{
		return bankRepo.createBank(BankCreateRequest.builder()
				.bankName("MyBank")
				.routingNo("routingNo")
				.build())
				.getBankId();
	}

	private I_C_BankStatement createBankStatement(final BankAccountId orgBankAccountId)
	{
		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
				.orgId(OrgId.ANY)
				.orgBankAccountId(orgBankAccountId)
				.name("Bank Statement 1")
				.statementDate(statementDate)
				.build());

		return bankStatementDAO.getById(bankStatementId);
	}

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "BankStatementLineBuilder")
	private I_C_BankStatementLine createBankStatementLine(
			final BankStatementId bankStatementId,
			final BPartnerId bpartnerId,
			final Money stmtAmt,
			final Money trxAmt,
			final Money bankFeeAmt,
			final boolean processed)
	{
		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ANY)
				.bpartnerId(bpartnerId)
				.lineNo(10)
				.statementLineDate(statementDate)
				.valutaDate(valutaDate)
				.statementAmt(stmtAmt)
				.trxAmt(trxAmt != null ? trxAmt : stmtAmt)
				.bankFeeAmt(bankFeeAmt)
				.build());

		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);

		if (processed)
		{
			bankStatementLine.setProcessed(true);
			bankStatementDAO.save(bankStatementLine);
		}

		return bankStatementLine;
	}

	@Nested
	public class linkSinglePayment
	{
		@Test
		public void checkListenerFired()
		{
			final BPartnerId customerId = createCustomer();

			final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
			final I_C_BankStatementLine bankStatementLine = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.stmtAmt(Money.of(-123, euroCurrencyId))
					.build();

			final I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.orgBankAccountId(euroOrgBankAccountId)
					.currencyId(euroCurrencyId)
					.payAmt(new BigDecimal("123"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			final ArrayList<PaymentLinkResult> paymentsLinked = new ArrayList<>();
			bankStatementListenerService.addListener(new IBankStatementListener()
			{
				@Override
				public void onPaymentsLinked(List<PaymentLinkResult> payments)
				{
					paymentsLinked.addAll(payments);
				}

				@Override
				public void onPaymentsUnlinkedFromBankStatementLineReferences(@NonNull BankStatementLineReferenceList lineRefs)
				{
					throw new UnsupportedOperationException();
				}
			});

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			bankStatementPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, payment);

			assertThat(paymentsLinked).hasSize(1);
			assertThat(paymentsLinked.get(0)).isEqualTo(PaymentLinkResult.builder()
					.bankStatementId(BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID()))
					.bankStatementLineId(BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID()))
					.bankStatementLineRefId(null)
					.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
					.statementTrxAmt(Money.of(-123, euroCurrencyId))
					.paymentMarkedAsReconciled(payment.isReconciled())
					.build());
		}

		@Test
		public void inboundPayment_Expect_BankFeeAmt()
		{
			final BPartnerId customerId = createCustomer();

			final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
			final I_C_BankStatementLine bankStatementLine = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.stmtAmt(Money.of(100, euroCurrencyId))
					.build();
			assertThat(bankStatementLine.getStmtAmt()).isEqualByComparingTo("100");
			assertThat(bankStatementLine.getTrxAmt()).isEqualByComparingTo("100");
			assertThat(bankStatementLine.getBankFeeAmt()).isEqualByComparingTo("0");

			final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.orgBankAccountId(euroOrgBankAccountId)
					.currencyId(euroCurrencyId)
					.payAmt(new BigDecimal("120"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			bankStatementPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, payment);

			assertThat(bankStatementLine.getStmtAmt()).isEqualByComparingTo("100");
			assertThat(bankStatementLine.getTrxAmt()).isEqualByComparingTo("120");
			assertThat(bankStatementLine.getBankFeeAmt()).isEqualByComparingTo("20");
		}

		@Test
		public void inboundPayment_presetBankFeeAmt()
		{
			final BPartnerId customerId = createCustomer();

			final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
			final I_C_BankStatementLine bankStatementLine = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.stmtAmt(Money.of(100, euroCurrencyId))
					.trxAmt(Money.of(120, euroCurrencyId))
					.bankFeeAmt(Money.of(20, euroCurrencyId))
					.build();
			assertThat(bankStatementLine.getStmtAmt()).isEqualByComparingTo("100");
			assertThat(bankStatementLine.getTrxAmt()).isEqualByComparingTo("120");
			assertThat(bankStatementLine.getBankFeeAmt()).isEqualByComparingTo("20");

			final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.orgBankAccountId(euroOrgBankAccountId)
					.currencyId(euroCurrencyId)
					.payAmt(new BigDecimal("120"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();

			//
			// call tested method
			//
			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			bankStatementPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, payment);

			assertThat(bankStatementLine.getStmtAmt()).isEqualByComparingTo("100");
			assertThat(bankStatementLine.getTrxAmt()).isEqualByComparingTo("120");
			assertThat(bankStatementLine.getBankFeeAmt()).isEqualByComparingTo("20");
		}
	}

	@Nested
	public class findOrCreateSinglePaymentAndLinkIfPossible
	{
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
			void OneMatchingOutboundPaymentExists_SoItIsLinked()
			{
				//
				// create test data
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);

				final BPartnerId customerId = createCustomer();

				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(-123, euroCurrencyId))
						.build();

				final I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
						.adOrgId(OrgId.ANY)
						.bpartnerId(customerId)
						.orgBankAccountId(euroOrgBankAccountId)
						.currencyId(euroCurrencyId)
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
				bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(
						bankStatement,
						bsl,
						ImmutableSet.of() // excludePaymentIds
				);

				//
				// Checks
				final boolean isReceipt = false;
				assertEquals(payment.getC_Payment_ID(), bsl.getC_Payment_ID());
				paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, euroOrgBankAccountId);
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());
			}

			@Test
			void OneMatchingInboundPaymentExists_SoItIsLinked()
			{
				//
				// create test data
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);

				final BPartnerId customerId = createCustomer();

				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(100, euroCurrencyId))
						.trxAmt(Money.of(123, euroCurrencyId))
						.bankFeeAmt(Money.of(23, euroCurrencyId))
						.build();

				final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
						.adOrgId(OrgId.ANY)
						.bpartnerId(customerId)
						.orgBankAccountId(euroOrgBankAccountId)
						.currencyId(euroCurrencyId)
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
				bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(
						bankStatement,
						bsl,
						ImmutableSet.of() // excludePaymentIds
				);

				//
				// Checks
				final boolean isReceipt = true;
				assertEquals(payment.getC_Payment_ID(), bsl.getC_Payment_ID());
				paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, euroOrgBankAccountId);
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());

				//
				InterfaceWrapperHelper.refresh(bsl);
				assertThat(BankStatementLineAmounts.of(bsl))
						.isEqualToComparingFieldByField(BankStatementLineAmounts.builder()
								.stmtAmt(new BigDecimal("100"))
								.trxAmt(new BigDecimal("123"))
								.bankFeeAmt(new BigDecimal("23"))
								.build());
			}

			@Test
			void TwoIdenticalPaymentsExist_SoLineHasNoPaymentLinked()
			{
				//
				// create test data
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);

				final BPartnerId customerId = createCustomer();

				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(-123, euroCurrencyId))
						.build();

				//
				// create 2 identical payments
				final I_C_Payment payment1 = paymentBL.newOutboundPaymentBuilder()
						.adOrgId(OrgId.ANY)
						.bpartnerId(customerId)
						.orgBankAccountId(euroOrgBankAccountId)
						.currencyId(euroCurrencyId)
						.payAmt(new BigDecimal("123"))
						.dateAcct(statementDate)
						.dateTrx(statementDate)
						.description("test")
						.tenderType(TenderType.DirectDeposit)
						.createAndProcess();

				final I_C_Payment payment2 = paymentBL.newOutboundPaymentBuilder()
						.adOrgId(OrgId.ANY)
						.bpartnerId(customerId)
						.orgBankAccountId(euroOrgBankAccountId)
						.currencyId(euroCurrencyId)
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
				bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(
						bankStatement,
						bsl,
						ImmutableSet.of() // excludePaymentIds
				);

				//
				// Checks
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
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);

				final BPartnerId customerId = createCustomer();

				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(100, euroCurrencyId))
						.trxAmt(Money.of(123, euroCurrencyId))
						.bankFeeAmt(Money.of(23, euroCurrencyId))
						.build();

				//
				// call tested method
				//
				bankStatement.setDocStatus(DocStatus.Completed.getCode());
				bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(
						bankStatement,
						bsl,
						ImmutableSet.of() // excludePaymentIds
				);

				//
				// Checks
				final boolean isReceipt = true;
				paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, euroOrgBankAccountId);
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());

				//
				InterfaceWrapperHelper.refresh(bsl);
				assertThat(BankStatementLineAmounts.of(bsl))
						.isEqualToComparingFieldByField(BankStatementLineAmounts.builder()
								.stmtAmt(new BigDecimal("100"))
								.trxAmt(new BigDecimal("123"))
								.bankFeeAmt(new BigDecimal("23"))
								.build());
			}

			@Test
			void OneOutboundBankStatementLine_PaymentIsCreated()
			{
				//
				// create test data
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);

				final BPartnerId customerId = createCustomer();

				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(-123, euroCurrencyId))
						.build();

				//
				// call tested method
				//
				bankStatement.setDocStatus(DocStatus.Completed.getCode());
				bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(
						bankStatement,
						bsl,
						ImmutableSet.of() // excludePaymentIds
				);

				//
				// Checks
				final boolean isReceipt = false;
				paymentChecks("123", bsl.getC_Payment_ID(), isReceipt, euroOrgBankAccountId);
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());
			}
		}
	}

	@Nested
	public class linkMultiPayments
	{
		@Nested
		public class onePayment
		{
			@Test
			public void bankStatementLine_NotProcessed()
			{
				final boolean bankStatementLineProcessed = false;
				test(bankStatementLineProcessed);
			}

			@Test
			public void bankStatementLine_Processed()
			{
				final boolean bankStatementLineProcessed = true;
				test(bankStatementLineProcessed);
			}

			private void test(final boolean bankStatementLineProcessed)
			{
				final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
				final BPartnerId customerId = createCustomer();
				final I_C_BankStatementLine bsl = bankStatementLine()
						.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
						.bpartnerId(customerId)
						.stmtAmt(Money.of(-123, euroCurrencyId))
						.processed(bankStatementLineProcessed)
						.build();

				final I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
						.adOrgId(OrgId.ANY)
						.bpartnerId(customerId)
						.orgBankAccountId(euroOrgBankAccountId)
						.currencyId(euroCurrencyId)
						.payAmt(new BigDecimal("123"))
						.dateAcct(statementDate)
						.dateTrx(statementDate)
						.description("test")
						.tenderType(TenderType.DirectDeposit)
						.createAndProcess();
				final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());

				final BankStatementLineMultiPaymentLinkResult result = bankStatementPaymentBL.linkMultiPayments(BankStatementLineMultiPaymentLinkRequest.builder()
						.bankStatementLineId(BankStatementLineId.ofRepoId(bsl.getC_BankStatementLine_ID()))
						.paymentToLink(PaymentToLink.builder()
								.paymentId(paymentId)
								.statementLineAmt(Amount.of(-123, CurrencyCode.EUR))
								.build())
						.build());

				assertThat(result.getPayments()).hasSize(1);
				final PaymentLinkResult paymentLinkResult = result.getPayments().get(0);
				assertThat(paymentLinkResult.getPaymentId()).isEqualTo(paymentId);
				assertThat(paymentLinkResult.getStatementTrxAmt()).isEqualTo(Money.of(-123, euroCurrencyId));

				final I_C_BankStatementLine_Ref lineRef = InterfaceWrapperHelper.load(paymentLinkResult.getBankStatementLineRefId(), I_C_BankStatementLine_Ref.class);
				assertThat(lineRef.isProcessed()).isEqualTo(bankStatementLineProcessed);
			}
		}

		@Test
		public void twoPayments_trxAmtSum_moreThan_stmtAmt()
		{
			final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
			final BPartnerId customerId = createCustomer();
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.stmtAmt(Money.of(100, euroCurrencyId))
					.processed(true)
					.build();
			assertThat(bsl.getStmtAmt()).isEqualByComparingTo("100");
			assertThat(bsl.getTrxAmt()).isEqualByComparingTo("100");
			assertThat(bsl.getBankFeeAmt()).isEqualByComparingTo("0");

			final I_C_Payment payment1 = paymentBL.newInboundReceiptBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.orgBankAccountId(euroOrgBankAccountId)
					.currencyId(euroCurrencyId)
					.payAmt(new BigDecimal("80"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();
			final PaymentId paymentId1 = PaymentId.ofRepoId(payment1.getC_Payment_ID());

			final I_C_Payment payment2 = paymentBL.newInboundReceiptBuilder()
					.adOrgId(OrgId.ANY)
					.bpartnerId(customerId)
					.orgBankAccountId(euroOrgBankAccountId)
					.currencyId(euroCurrencyId)
					.payAmt(new BigDecimal("40"))
					.dateAcct(statementDate)
					.dateTrx(statementDate)
					.description("test")
					.tenderType(TenderType.DirectDeposit)
					.createAndProcess();
			final PaymentId paymentId2 = PaymentId.ofRepoId(payment2.getC_Payment_ID());

			BankStatementLineMultiPaymentLinkRequest request = BankStatementLineMultiPaymentLinkRequest.builder()
					.bankStatementLineId(BankStatementLineId.ofRepoId(bsl.getC_BankStatementLine_ID()))
					.paymentToLink(PaymentToLink.builder()
							.paymentId(paymentId1)
							.statementLineAmt(Amount.of(80, CurrencyCode.EUR))
							.build())
					.paymentToLink(PaymentToLink.builder()
							.paymentId(paymentId2)
							.statementLineAmt(Amount.of(40, CurrencyCode.EUR))
							.build())
					.build();

			assertThatThrownBy(() -> bankStatementPaymentBL.linkMultiPayments(request))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Partial bank statement line reconciliation is not allowed");

			// assertThat(result.getPayments()).hasSize(2);
			// {
			// final PaymentLinkResult paymentLinkResult = result.getPayments().get(0);
			// assertThat(paymentLinkResult.getPaymentId()).isEqualTo(paymentId1);
			// assertThat(paymentLinkResult.getStatementTrxAmt()).isEqualTo(Money.of(80, euroCurrencyId));
			// }
			// {
			// final PaymentLinkResult paymentLinkResult = result.getPayments().get(1);
			// assertThat(paymentLinkResult.getPaymentId()).isEqualTo(paymentId1);
			// assertThat(paymentLinkResult.getStatementTrxAmt()).isEqualTo(Money.of(40, euroCurrencyId));
			// }
			//
			// InterfaceWrapperHelper.refresh(bsl);
			// assertThat(bsl.getStmtAmt()).isEqualByComparingTo("100");
			// assertThat(bsl.getTrxAmt()).isEqualByComparingTo("120");
			// assertThat(bsl.getBankFeeAmt()).isEqualByComparingTo("20");
		}

	}
}
