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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoice.paymentbonus.PaymentBonusOnPaymentService;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.BankId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest.PaymentToLink;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.payment.impl.BankStatementPaymentBL;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationService;
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
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionProvider;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BankStatementPaymentBLTest
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private IBankStatementListenerService bankStatementListenerService;
	private BankStatementPaymentBL bankStatementPaymentBL;
	private PaymentBonusDeductionProvider paymentBonusDeductionProvider;
	@Nullable private List<InvoiceToAllocate> invoicesToAllocate;
	private BankRepository bankRepo;

	@SuppressWarnings("FieldCanBeLocal")
	private final String metasfreshIban = "123456";
	private final LocalDate statementDate = LocalDate.parse("2021-02-05");
	private final LocalDate defaultValutaDate = LocalDate.parse("2021-02-06");

	private CurrencyId euroCurrencyId;
	private BankAccountId euroOrgBankAccountId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BankAccountService bankAccountService = BankAccountService.newInstanceForUnitTesting();
		final BankStatementBL bankStatementBL = new BankStatementBL(
				bankAccountService,
				new MoneyService(new CurrencyRepository()))
		{
			@Override
			public void unpost(final I_C_BankStatement bankStatement)
			{
				System.out.println("In JUnit test BankStatementBL.unpost() does nothing"
						+ "\n\t bank statement: " + bankStatement
						+ "\n\t called via " + Trace.toOneLineStackTraceString());
			}
		};

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final MoneyService moneyService = new MoneyService(currencyRepository);
		SpringContextHolder.registerJUnitBean(moneyService);

		// the payment bonus and the open invoices of a test are set in paymentBonusDeductionProvider and invoicesToAllocate; none by default
		paymentBonusDeductionProvider = invoiceId -> Optional.empty();
		SpringContextHolder.registerJUnitBean(new PaymentBonusOnPaymentService(
				Optional.of(invoiceId -> paymentBonusDeductionProvider.computeForInvoice(invoiceId)),
				new PaymentBonusCreditMemoService()));
		invoicesToAllocate = null;
		SpringContextHolder.registerJUnitBean(PaymentAllocationRepository.class, new PaymentAllocationRepository()
		{
			@Override
			public List<InvoiceToAllocate> retrieveInvoicesToAllocate(@NonNull final InvoiceToAllocateQuery query)
			{
				// the open invoices are retrieved by a database function, so a test that needs them stubs them
				return invoicesToAllocate != null ? invoicesToAllocate : super.retrieveInvoicesToAllocate(query);
			}
		});

		bankStatementListenerService = Services.get(IBankStatementListenerService.class);
		bankStatementPaymentBL = new BankStatementPaymentBL(
				bankStatementBL,
				moneyService,
				PaymentAllocationService.newInstanceForUnitTesting());

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(new C_BankStatementLine_MockedInterceptor(bankStatementBL));

		bankRepo = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepo);

		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepo, currencyRepo));

		createMasterData();
	}

	private void createMasterData()
	{
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		euroOrgBankAccountId = createOrgBankAccount(euroCurrencyId);
		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		createClientInfo(acctSchemaId);
	}

	private void createClientInfo(@NonNull final AcctSchemaId acctSchemaId)
	{
		final I_AD_ClientInfo clientInfo = newInstance(I_AD_ClientInfo.class);
		clientInfo.setC_AcctSchema1_ID(acctSchemaId.getRepoId());
		save(clientInfo);
	}

	private BPartnerId createCustomer()
	{
		final I_C_BPartner customer = BusinessTestHelper.createBPartner("le customer");
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

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "$BankStatementLineBuilder")
	private I_C_BankStatementLine createBankStatementLine(
			final BankStatementId bankStatementId,
			final BPartnerId bpartnerId,
			final Money stmtAmt,
			final Money trxAmt,
			final Money bankFeeAmt,
			final boolean processed,
			@Nullable final String statementDate,
			@Nullable final String valutaDate,
			@Nullable final InvoiceId invoiceId)
	{
		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ANY)
				.bpartnerId(bpartnerId)
				.lineNo(10)
				.statementLineDate(statementDate != null ? LocalDate.parse(statementDate) : this.statementDate)
				.valutaDate(valutaDate != null ? LocalDate.parse(valutaDate) : defaultValutaDate)
				.statementAmt(stmtAmt)
				.trxAmt(trxAmt != null ? trxAmt : stmtAmt)
				.bankFeeAmt(bankFeeAmt)
				.invoiceId(invoiceId)
				.updateAmountsFromInvoice(invoiceId == null)
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
				public void onPaymentsLinked(final List<PaymentLinkResult> payments)
				{
					paymentsLinked.addAll(payments);
				}

				@Override
				public void onPaymentsUnlinkedFromBankStatementLineReferences(@NonNull final BankStatementLineReferenceList lineRefs)
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
		@SuppressWarnings("SameParameterValue")
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
				// TODO tbp: check with mark in a followup task about this use case.
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
						.statementDate("2021-02-11")
						.valutaDate("2021-02-14")
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
				// Check Bank Statement
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());
				InterfaceWrapperHelper.refresh(bsl);
				assertThat(BankStatementLineAmounts.of(bsl))
						.isEqualToComparingFieldByField(BankStatementLineAmounts.builder()
								.stmtAmt(new BigDecimal("100"))
								.trxAmt(new BigDecimal("123"))
								.bankFeeAmt(new BigDecimal("23"))
								.build());

				//
				// Check created Payment
				final I_C_Payment payment = InterfaceWrapperHelper.load(bsl.getC_Payment_ID(), I_C_Payment.class);
				assertThat(payment).isNotNull();
				assertThat(payment.getDateTrx()).isEqualTo(TimeUtil.asTimestamp(LocalDate.parse("2021-02-14")));
				assertThat(payment.getDateAcct()).isEqualTo(TimeUtil.asTimestamp(LocalDate.parse("2021-02-14")));
				assertThat(payment.getPayAmt()).isEqualTo("123");
				assertThat(payment.isReconciled()).isTrue();
				assertThat(payment.isReceipt()).isEqualTo(true);
				assertThat(DocStatus.ofCode(payment.getDocStatus())).isEqualTo(DocStatus.Completed);
				assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(euroOrgBankAccountId.getRepoId());
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
						.statementDate("2021-02-11")
						.valutaDate("2021-02-14")
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
				// Check bank statement line
				assertThat(bsl.getC_Payment_ID()).isGreaterThan(0);
				assertFalse(bsl.isMultiplePayment());
				assertFalse(bsl.isMultiplePaymentOrInvoice());

				//
				// Check created Payment
				final I_C_Payment payment = InterfaceWrapperHelper.load(bsl.getC_Payment_ID(), I_C_Payment.class);
				assertThat(payment).isNotNull();
				assertThat(payment.getDateTrx()).isEqualTo(TimeUtil.asTimestamp(LocalDate.parse("2021-02-14")));
				assertThat(payment.getDateAcct()).isEqualTo(TimeUtil.asTimestamp(LocalDate.parse("2021-02-14")));
				assertThat(payment.getPayAmt()).isEqualTo("123");
				assertThat(payment.isReconciled()).isTrue();
				assertThat(payment.isReceipt()).isEqualTo(false);
				assertThat(DocStatus.ofCode(payment.getDocStatus())).isEqualTo(DocStatus.Completed);
				assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(euroOrgBankAccountId.getRepoId());
			}
		}
	}

	/**
	 * A payment created for a bank statement line with a sales invoice gets the Skonto, the payment bonus or both, the one that the paid amount matches.
	 */
	@Nested
	class findOrCreateSinglePaymentAndLinkIfPossible_PaymentBonus
	{
		// open 166.50, Skonto 3.33, bonus 2.78 (2.60 net + 7 % VAT)
		private void setUpInvoiceWithBonus(@NonNull final InvoiceId invoiceId, @NonNull final BPartnerId customerId)
		{
			paymentBonusDeductionProvider = id -> Optional.of(deduction(id, customerId));
			invoicesToAllocate = ImmutableList.of(InvoiceToAllocate.builder()
					.invoiceId(invoiceId)
					.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY))
					.documentNo("1111")
					.bpartnerId(customerId)
					.bpartnerName("customer")
					.dateInvoiced(LocalDate.parse("2021-02-01"))
					.dateAcct(LocalDate.parse("2021-02-01"))
					.documentCurrencyCode(CurrencyCode.EUR)
					.evaluationDate(defaultValutaDate.atStartOfDay(ZoneId.of("UTC")))
					.multiplier(InvoiceAmtMultiplier.create(SOTrx.SALES, false, false))
					.grandTotal(Amount.of("166.50", CurrencyCode.EUR))
					.openAmountConverted(Amount.of("166.50", CurrencyCode.EUR))
					.discountAmountConverted(Amount.of("3.33", CurrencyCode.EUR))
					.docTypeId(DocTypeId.ofRepoId(1))
					.docBaseType(InvoiceDocBaseType.CustomerInvoice)
					.build());
		}

		private PaymentBonusDeduction deduction(@NonNull final InvoiceId invoiceId, @NonNull final BPartnerId customerId)
		{
			return PaymentBonusDeduction.builder()
					.orgId(OrgId.ANY)
					.invoiceId(invoiceId)
					.customerId(customerId)
					.currencyId(euroCurrencyId)
					.precision(CurrencyPrecision.TWO)
					.line(PaymentBonusDeductionLine.builder()
							.bonusProductId(ProductId.ofRepoId(1))
							.tax(Tax.builder()
									.taxId(TaxId.ofRepoId(1))
									.name("7 %")
									.orgId(OrgId.ANY)
									.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
									.taxCategoryId(TaxCategoryId.ofRepoId(1))
									.rate(new BigDecimal("7"))
									.isTaxExempt(false)
									.requiresTaxCertificate(false)
									.seqNo(10)
									.build())
							.netAmt(Money.of(new BigDecimal("2.60"), euroCurrencyId))
							.build())
					.build();
		}

		private I_C_Payment createPaymentForInvoice(@NonNull final String trxAmt)
		{
			final BPartnerId customerId = createCustomer();
			final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
			invoice.setC_BPartner_ID(customerId.getRepoId());
			invoice.setIsSOTrx(true);
			saveRecord(invoice);
			final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

			final I_C_BankStatement bankStatement = createBankStatement(euroOrgBankAccountId);
			final I_C_BankStatementLine bsl = bankStatementLine()
					.bankStatementId(BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID()))
					.bpartnerId(customerId)
					.stmtAmt(Money.of(new BigDecimal(trxAmt), euroCurrencyId))
					.invoiceId(invoiceId)
					.build();

			bankStatement.setDocStatus(DocStatus.Completed.getCode());
			setUpInvoiceWithBonus(invoiceId, customerId);
			bankStatementPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(bankStatement, bsl, ImmutableSet.of());

			assertThat(bsl.getC_Payment_ID()).as("C_Payment_ID").isGreaterThan(0);
			final I_C_Payment payment = InterfaceWrapperHelper.load(bsl.getC_Payment_ID(), I_C_Payment.class);
			assertThat(payment.getC_Invoice_ID()).as("C_Invoice_ID").isEqualTo(invoiceId.getRepoId());
			return payment;
		}

		@Test
		void inbound_openMinusBonus_bonusOnly()
		{
			final I_C_Payment payment = createPaymentForInvoice("163.72");
			assertThat(payment.isReceipt()).isTrue();
			assertThat(payment.getPayAmt()).isEqualByComparingTo("163.72");
			assertThat(payment.getDiscountAmt()).as("DiscountAmt").isEqualByComparingTo("0");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("2.78");
		}

		@Test
		void inbound_openMinusSkontoMinusBonus_both()
		{
			final I_C_Payment payment = createPaymentForInvoice("160.39");
			assertThat(payment.getDiscountAmt()).as("DiscountAmt").isEqualByComparingTo("3.33");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("2.78");
		}

		@Test
		void inbound_openMinusSkonto_skontoOnly()
		{
			final I_C_Payment payment = createPaymentForInvoice("163.17");
			assertThat(payment.getDiscountAmt()).as("DiscountAmt").isEqualByComparingTo("3.33");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
		}

		@Test
		void inbound_amountMatchingNoRule_noDeduction()
		{
			final I_C_Payment payment = createPaymentForInvoice("160.00");
			assertThat(payment.getDiscountAmt()).as("DiscountAmt").isEqualByComparingTo("0");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
		}

		@Test
		void outbound_openMinusBonus_noPaymentBonus()
		{
			final I_C_Payment payment = createPaymentForInvoice("-163.72");
			assertThat(payment.isReceipt()).isFalse();
			assertThat(payment.getPayAmt()).isEqualByComparingTo("163.72");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt of an outbound payment").isEqualByComparingTo("0");
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

				assertThat(paymentLinkResult.getBankStatementLineRefId()).isNotNull();
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

			final BankStatementLineMultiPaymentLinkRequest request = BankStatementLineMultiPaymentLinkRequest.builder()
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
		}
	}
}
