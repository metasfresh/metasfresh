package de.metas.ui.web.bankstatement_reconciliation.actions;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.AbstractBooleanAssert;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Trace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.attachments.AttachmentEntryService;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.PaySelectionId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.model.validator.PaySelectionBankStatementListener;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.impl.BankStatementPaymentBL;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.banking.service.impl.BankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.IMsgBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_Import;
import de.metas.payment.esr.model.validator.ESRBankStatementListener;
import de.metas.ui.web.bankstatement_reconciliation.BankStatementLineAndPaymentsToReconcileRepository;
import de.metas.ui.web.bankstatement_reconciliation.BankStatementLineRow;
import de.metas.ui.web.bankstatement_reconciliation.PaymentToReconcileRow;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
public class ReconcilePaymentsCommandTest
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private BankStatementPaymentBL bankStatmentPaymentBL;
	private BankStatementLineAndPaymentsToReconcileRepository rowsRepo;

	private static final LocalDate statementDate = LocalDate.parse("2020-03-21");
	private static final LocalDate paymentDate = LocalDate.parse("2020-03-10");
	private CurrencyId euroCurrencyId;
	private CurrencyId chfCurrencyId;
	private BankAccountId euroOrgBankAccountId;
	private BPartnerId customerId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final MoneyService moneyService = new MoneyService(currencyRepository);
		SpringContextHolder.registerJUnitBean(moneyService);

		final IBankStatementListenerService bankStatementListenerService = Services.get(IBankStatementListenerService.class);
		final ESRImportBL esrImportBL = new ESRImportBL(AttachmentEntryService.createInstanceForUnitTesting());
		bankStatementListenerService.addListener(new ESRBankStatementListener(esrImportBL));

		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
		bankStatementListenerService.addListener(new PaySelectionBankStatementListener(paySelectionBL));

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

		bankStatmentPaymentBL = new BankStatementPaymentBL(bankStatementBL, moneyService);
		SpringContextHolder.registerJUnitBean(IBankStatementPaymentBL.class, bankStatmentPaymentBL);

		this.rowsRepo = new BankStatementLineAndPaymentsToReconcileRepository(bankStatementBL, currencyRepository);
		rowsRepo.setBpartnerLookup(new MockedBPartnerLookupDataSource());

		createMasterdata();
	}

	private void createMasterdata()
	{
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chfCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
		euroOrgBankAccountId = createOrgBankAccount(euroCurrencyId);
		customerId = createCustomer();
	}

	private void executeReconcilePaymentsCommand(final ReconcilePaymentsRequest request)
	{
		ReconcilePaymentsCommand.builder()
				.msgBL(msgBL)
				.bankStatmentPaymentBL(bankStatmentPaymentBL)
				.request(request)
				.build()
				.execute();
	}

	private Money euro(final String amount)
	{
		return Money.of(amount, euroCurrencyId);
	}

	private Money chf(final String amount)
	{
		return Money.of(amount, chfCurrencyId);
	}

	private BPartnerId createCustomer()
	{
		I_C_BPartner customer = BusinessTestHelper.createBPartner("le customer");
		return BPartnerId.ofRepoId(customer.getC_BPartner_ID());
	}

	private BankAccountId createOrgBankAccount(final CurrencyId eurCurrencyId)
	{
		final String metasfreshIban = "123456";
		final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
		final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()), eurCurrencyId, metasfreshIban);
		return BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());
	}

	@Builder(builderMethodName = "bankStatement", builderClassName = "BankStatementBuilder")
	private BankStatementId createBankStatement(@NonNull final DocStatus docStatus)
	{
		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
				.orgId(OrgId.ANY)
				.orgBankAccountId(euroOrgBankAccountId)
				.name("Bank Statement 1")
				.statementDate(statementDate)
				.build());

		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		bankStatement.setDocStatus(docStatus.getCode());
		bankStatementDAO.save(bankStatement);

		return bankStatementId;
	}

	@Builder(builderMethodName = "bankStatementLineRow", builderClassName = "BankStatementLineRowBuilder")
	private BankStatementLineRow createBankStatementLineRow(
			@NonNull DocStatus docStatus,
			@NonNull final Money statementAmt)
	{
		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatement().docStatus(docStatus).build())
				.orgId(OrgId.ANY)
				.lineNo(10)
				.statementLineDate(statementDate)
				.statementAmt(statementAmt)
				.build());

		return rowsRepo.getBankStatementLineRowsByIds(ImmutableSet.of(bankStatementLineId)).get(0);
	}

	@Builder(builderMethodName = "paymentRow", builderClassName = "PaymentRowBuilder")
	private PaymentToReconcileRow createPaymentRow(
			@NonNull final Boolean inboundPayment,
			@NonNull final BPartnerId customerId,
			@NonNull final Money paymentAmt,
			@Nullable final PaymentReconcileReference reconcileRef)
	{
		final DefaultPaymentBuilder builder = inboundPayment
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		final I_C_Payment payment = builder
				.adOrgId(OrgId.ANY)
				.bpartnerId(customerId)
				.orgBankAccountId(euroOrgBankAccountId)
				.currencyId(paymentAmt.getCurrencyId())
				.payAmt(paymentAmt.toBigDecimal())
				.dateAcct(paymentDate)
				.dateTrx(paymentDate)
				.tenderType(TenderType.Check)
				.createAndProcess();

		payment.setDocumentNo("documentNo-" + payment.getC_Payment_ID());
		paymentDAO.save(payment);

		if (reconcileRef != null)
		{
			paymentBL.markReconciledAndSave(payment, reconcileRef);
		}

		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());

		return rowsRepo.getPaymentToReconcileRowsByIds(ImmutableSet.of(paymentId)).get(0);
	}

	private PaymentToReconcileRow retrievePaymentRow(@NonNull final PaymentToReconcileRow paymentRow)
	{
		return rowsRepo.getPaymentToReconcileRowsByIds(ImmutableSet.of(paymentRow.getPaymentId())).get(0);
	}

	private void assertMultiplePayments(final BankStatementLineId bankStatementLineId)
	{
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);

		assertThat(bankStatementLine.isMultiplePaymentOrInvoice()).isTrue();
		assertThat(bankStatementLine.isMultiplePayment()).isTrue();
		assertThat(bankStatementLine.getC_Payment_ID()).isLessThanOrEqualTo(0);
	}

	private PaySelectionId createPaySelection()
	{
		final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
		saveRecord(paySelection);
		return PaySelectionId.ofRepoId(paySelection.getC_PaySelection_ID());
	}

	private I_C_PaySelectionLine createPaySelectionLine(
			@NonNull final PaySelectionId paySelectionId,
			@NonNull final PaymentId paymentId)
	{
		final I_C_PaySelectionLine paySelectionLine = newInstance(I_C_PaySelectionLine.class);
		paySelectionLine.setC_PaySelection_ID(paySelectionId.getRepoId());
		paySelectionLine.setC_Payment_ID(paymentId.getRepoId());
		saveRecord(paySelectionLine);
		return paySelectionLine;
	}

	private I_ESR_ImportLine createESRImportLine(@NonNull final PaymentId paymentId)
	{
		final I_ESR_Import esrImport = newInstance(I_ESR_Import.class);
		esrImport.setDataType(X_ESR_Import.DATATYPE_V11);
		save(esrImport);
		
		final I_ESR_ImportLine esrImportLine = newInstance(I_ESR_ImportLine.class);
		esrImportLine.setESR_Import_ID(esrImport.getESR_Import_ID());
		esrImportLine.setC_Payment_ID(paymentId.getRepoId());
		saveRecord(esrImportLine);
		return esrImportLine;
	}

	@Test
	public void bankStatementLineIsLinkedToPaySelection()
	{
		final BankStatementLineRow bankStatementLineRow = bankStatementLineRow()
				.docStatus(DocStatus.Drafted)
				.statementAmt(euro("1000"))
				.build();

		final PaymentToReconcileRow paymentRow = paymentRow()
				.inboundPayment(true)
				.customerId(customerId)
				.paymentAmt(euro("1000"))
				.build();
		final PaymentId paymentId = paymentRow.getPaymentId();

		final PaySelectionId paySelectionId = createPaySelection();
		final I_C_PaySelectionLine paySelectionLine = createPaySelectionLine(paySelectionId, paymentId);
		assertThat(paySelectionLine.getC_Payment_ID()).isEqualTo(paymentId.getRepoId());
		assertThat(paySelectionLine.getC_BankStatement_ID()).isLessThanOrEqualTo(0);
		assertThat(paySelectionLine.getC_BankStatementLine_ID()).isLessThanOrEqualTo(0);
		assertThat(paySelectionLine.getC_BankStatementLine_Ref_ID()).isLessThanOrEqualTo(0);
		assertReconciled(paySelectionId).isFalse();

		executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
				.selectedBankStatementLine(bankStatementLineRow)
				.selectedPaymentToReconcile(paymentRow)
				.build());

		InterfaceWrapperHelper.refresh(paySelectionLine);
		assertThat(paySelectionLine.getC_BankStatement_ID()).isGreaterThan(0);
		assertThat(paySelectionLine.getC_BankStatementLine_ID()).isEqualTo(bankStatementLineRow.getBankStatementLineId().getRepoId());
		assertThat(paySelectionLine.getC_BankStatementLine_Ref_ID()).isGreaterThan(0);
		assertReconciled(paySelectionId).isTrue();
	}

	private AbstractBooleanAssert<?> assertReconciled(final PaySelectionId paySelectionId)
	{
		final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
		return assertThat(paySelection.isReconciled())
				.as("Is " + paySelectionId + " reconciled?");
	}

	@Test
	public void bankStatementLineIsLinkedToESRImportLine()
	{
		final BankStatementLineRow bankStatementLineRow = bankStatementLineRow()
				.docStatus(DocStatus.Drafted)
				.statementAmt(euro("1000"))
				.build();

		final PaymentToReconcileRow paymentRow = paymentRow()
				.inboundPayment(true)
				.customerId(customerId)
				.paymentAmt(euro("1000"))
				.build();
		final PaymentId paymentId = paymentRow.getPaymentId();

		final I_ESR_ImportLine esrImportLine = createESRImportLine(paymentId);
		assertThat(esrImportLine.getC_Payment_ID()).isEqualTo(paymentId.getRepoId());
		assertThat(esrImportLine.getC_BankStatement_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_BankStatementLine_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_BankStatementLine_Ref_ID()).isLessThanOrEqualTo(0);

		executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
				.selectedBankStatementLine(bankStatementLineRow)
				.selectedPaymentToReconcile(paymentRow)
				.build());

		InterfaceWrapperHelper.refresh(esrImportLine);
		assertThat(esrImportLine.getC_BankStatement_ID()).isGreaterThan(0);
		assertThat(esrImportLine.getC_BankStatementLine_ID()).isEqualTo(bankStatementLineRow.getBankStatementLineId().getRepoId());
		assertThat(esrImportLine.getC_BankStatementLine_Ref_ID()).isGreaterThan(0);
	}

	@Nested
	public class inboundStatementLineAmount
	{
		@Nested
		public class draftStatement
		{
			@Test
			public void singleMatchingPayment()
			{
				final BankStatementLineRow bankStatementLineRow = bankStatementLineRow()
						.docStatus(DocStatus.Drafted)
						.statementAmt(euro("1000"))
						.build();

				PaymentToReconcileRow paymentRow = paymentRow().inboundPayment(true).customerId(customerId).paymentAmt(euro("1000")).build();
				assertThat(paymentRow.isInboundPayment()).isTrue();
				assertThat(paymentRow.isReconciled()).isFalse();

				executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow)
						.build());

				paymentRow = retrievePaymentRow(paymentRow);

				//
				// Assertions
				{
					final BankStatementLineId bankStatementLineId = bankStatementLineRow.getBankStatementLineId();
					assertMultiplePayments(bankStatementLineId);

					final ImmutableList<BankStatementLineReference> lineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));
					assertThat(lineReferences).hasSize(1);
					assertThat(lineReferences).element(0)
							.returns(paymentRow.getPaymentId(), BankStatementLineReference::getPaymentId)
							.returns(customerId, BankStatementLineReference::getBpartnerId)
							.returns(euro("1000"), BankStatementLineReference::getTrxAmt);

					assertThat(paymentRow.isInboundPayment()).isTrue();
					assertThat(paymentRow.isReconciled()).isFalse();
				}
			}
		}

		@Nested
		public class completedStatement
		{
			private BankStatementLineRow bankStatementLineRow;

			@BeforeEach
			public void beforeEach()
			{
				bankStatementLineRow = bankStatementLineRow()
						.docStatus(DocStatus.Completed)
						.statementAmt(euro("1000"))
						.build();
			}

			@Test
			public void singleMatchingPayment()
			{
				PaymentToReconcileRow paymentRow = paymentRow().inboundPayment(true).customerId(customerId).paymentAmt(euro("1000")).build();
				assertThat(paymentRow.isInboundPayment()).isTrue();
				assertThat(paymentRow.isReconciled()).isFalse();

				executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow)
						.build());

				paymentRow = retrievePaymentRow(paymentRow);

				//
				// Assertions
				{
					final BankStatementLineId bankStatementLineId = bankStatementLineRow.getBankStatementLineId();
					assertMultiplePayments(bankStatementLineId);

					final ImmutableList<BankStatementLineReference> lineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));
					assertThat(lineReferences).hasSize(1);
					assertThat(lineReferences).element(0)
							.returns(paymentRow.getPaymentId(), BankStatementLineReference::getPaymentId)
							.returns(customerId, BankStatementLineReference::getBpartnerId)
							.returns(euro("1000"), BankStatementLineReference::getTrxAmt);

					assertThat(paymentRow.isInboundPayment()).isTrue();
					assertThat(paymentRow.isReconciled()).isTrue();
				}
			}

			@Test
			public void amountNotMatching()
			{
				final PaymentToReconcileRow paymentRow = paymentRow().inboundPayment(true).customerId(customerId).paymentAmt(euro("900")).build();

				assertThatThrownBy(() -> executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow)
						.build()))
								.isInstanceOf(AdempiereException.class)
								.hasMessageContaining(ReconcilePaymentsCommand.MSG_StatementLineAmtToReconcileIs.toAD_Message());
			}

			@Test
			public void currencyNotMatching()
			{
				final PaymentToReconcileRow paymentRow = paymentRow().inboundPayment(true).customerId(customerId).paymentAmt(chf("1000")).build();

				assertThatThrownBy(() -> executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow)
						.build()))
								.isInstanceOf(AdempiereException.class)
								.hasMessageContaining("shall be in `EUR` instead of `CHF`");
			}

			@Test
			public void alreadyReconciledPayment()
			{
				final PaymentToReconcileRow paymentRow = paymentRow()
						.inboundPayment(true)
						.customerId(customerId)
						.paymentAmt(euro("1000"))
						.reconcileRef(PaymentReconcileReference.bankStatementLine(
								BankStatementId.ofRepoId(666),
								BankStatementLineId.ofRepoId(666)))
						.build();

				assertThatThrownBy(() -> executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow)
						.build()))
								.isInstanceOf(AdempiereException.class)
								.hasMessageContaining("was already reconciled");
			}

			@Test
			public void multipleMatchingPayments()
			{
				final PaymentRowBuilder paymentRowBuilder = paymentRow().inboundPayment(true).customerId(customerId);
				PaymentToReconcileRow paymentRow1 = paymentRowBuilder.paymentAmt(euro("700")).build();
				PaymentToReconcileRow paymentRow2 = paymentRowBuilder.paymentAmt(euro("300")).build();

				executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
						.selectedBankStatementLine(bankStatementLineRow)
						.selectedPaymentToReconcile(paymentRow1)
						.selectedPaymentToReconcile(paymentRow2)
						.build());

				paymentRow1 = retrievePaymentRow(paymentRow1);
				paymentRow2 = retrievePaymentRow(paymentRow2);

				//
				// Assertions
				{
					final BankStatementLineId bankStatementLineId = bankStatementLineRow.getBankStatementLineId();
					assertMultiplePayments(bankStatementLineId);

					final ImmutableList<BankStatementLineReference> lineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));
					assertThat(lineReferences).hasSize(2);
					assertThat(lineReferences).element(0)
							.returns(paymentRow1.getPaymentId(), BankStatementLineReference::getPaymentId)
							.returns(customerId, BankStatementLineReference::getBpartnerId)
							.returns(euro("700"), BankStatementLineReference::getTrxAmt);
					assertThat(lineReferences).element(1)
							.returns(paymentRow2.getPaymentId(), BankStatementLineReference::getPaymentId)
							.returns(customerId, BankStatementLineReference::getBpartnerId)
							.returns(euro("300"), BankStatementLineReference::getTrxAmt);

					assertThat(paymentRow1.isReconciled()).isTrue();
					assertThat(paymentRow2.isReconciled()).isTrue();
				}
			}
		}
	}

	@Nested
	public class outboundStatementLineAmount
	{
		private BankStatementLineRow bankStatementLineRow;

		@BeforeEach
		public void beforeTest()
		{
			bankStatementLineRow = bankStatementLineRow()
					.docStatus(DocStatus.Drafted)
					.statementAmt(euro("-1000"))
					.build();
		}

		@Test
		public void singleMatchingPayment()
		{
			PaymentToReconcileRow paymentRow = paymentRow().inboundPayment(false).customerId(customerId).paymentAmt(euro("1000")).build();

			executeReconcilePaymentsCommand(ReconcilePaymentsRequest.builder()
					.selectedBankStatementLine(bankStatementLineRow)
					.selectedPaymentToReconcile(paymentRow)
					.build());

			paymentRow = retrievePaymentRow(paymentRow);

			//
			// Assertions
			{
				final BankStatementLineId bankStatementLineId = bankStatementLineRow.getBankStatementLineId();
				assertMultiplePayments(bankStatementLineId);

				final ImmutableList<BankStatementLineReference> lineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));
				assertThat(lineReferences).hasSize(1);
				assertThat(lineReferences).element(0)
						.returns(paymentRow.getPaymentId(), BankStatementLineReference::getPaymentId)
						.returns(customerId, BankStatementLineReference::getBpartnerId)
						.returns(euro("-1000"), BankStatementLineReference::getTrxAmt);

				final I_C_Payment payment = paymentDAO.getById(paymentRow.getPaymentId());
				assertThat(payment.isReceipt()).isFalse();
				assertThat(payment.isReconciled()).isFalse();
			}
		}
	}
}
