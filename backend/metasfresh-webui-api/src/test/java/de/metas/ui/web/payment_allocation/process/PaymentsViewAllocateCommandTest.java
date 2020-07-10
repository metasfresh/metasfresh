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

package de.metas.ui.web.payment_allocation.process;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.IPaymentDocument.PaymentDocumentType;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class PaymentsViewAllocateCommandTest
{

	private static final OrgId orgId = OrgId.ofRepoId(1);
	private static final LocalDate dateInvoiced = LocalDate.of(2020, Month.APRIL, 1);
	private static final LocalDate paymentDateTrx = LocalDate.of(2020, Month.APRIL, 25);

	private MoneyService moneyService;
	private InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;
	private IInvoiceDAO invoicesDAO;
	private IAllocationDAO allocationDAO;

	private CurrencyId euroCurrencyId;
	private BPartnerId bpartnerId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		moneyService = new MoneyService(new CurrencyRepository());
		invoiceProcessingServiceCompanyService = new InvoiceProcessingServiceCompanyService(
				new InvoiceProcessingServiceCompanyConfigRepository(),
				moneyService);
		invoicesDAO = Services.get(IInvoiceDAO.class);
		allocationDAO = Services.get(IAllocationDAO.class);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		bpartnerId = createBPartnerId();
	}

	private BPartnerId createBPartnerId()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private Amount euro(final int amount)
	{
		return Amount.of(amount, CurrencyCode.EUR);
	}

	private TableRecordReference toRecordRef(final PaymentRow paymentRow)
	{
		return TableRecordReference.of(I_C_Payment.Table_Name, paymentRow.getPaymentId());
	}

	private TableRecordReference toRecordRef(final InvoiceRow invoiceRow)
	{
		return TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRow.getInvoiceId());
	}

	private void assertInvoiceAllocatedAmt(final InvoiceId invoiceId, final String expectedAllocatedAmt)
	{
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);

		final BigDecimal actualAllocatedAmt = allocationDAO.retrieveAllocatedAmt(invoice);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for invoice " + invoiceId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	@Builder(builderMethodName = "paymentRow", builderClassName = "PaymentRowBuilder")
	private PaymentRow createPaymentRow(
			@NonNull final PaymentDirection direction,
			@NonNull final Amount payAmt,
			@Nullable final BPartnerId bpartnerIdOverride,
			@Nullable final LocalDate paymentDateTrxOverride)
	{
		final I_C_Payment paymentRecord = newInstance(I_C_Payment.class);
		saveRecord(paymentRecord);
		final PaymentId paymentId = PaymentId.ofRepoId(paymentRecord.getC_Payment_ID());

		return PaymentRow.builder()
				.paymentId(paymentId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				.documentNo("paymentNo_" + paymentId.getRepoId())
				.dateTrx(CoalesceUtil.coalesce(paymentDateTrxOverride, paymentDateTrx))
				.bpartner(IntegerLookupValue.of(CoalesceUtil.coalesce(bpartnerIdOverride, bpartnerId).getRepoId(), "BPartner"))
				.payAmt(payAmt)
				.openAmt(payAmt)
				.paymentDirection(direction)
				.build();
	}

	@Builder(builderMethodName = "invoiceRow", builderClassName = "InvoiceRowBuilder")
	private InvoiceRow createInvoiceRow(
			@NonNull final InvoiceDocBaseType docBaseType,
			@NonNull final Amount openAmt,
			@Nullable final String discountAmt,
			@Nullable final String serviceFeeAmt)
	{
		final InvoiceId invoiceId;
		{
			final Money invoiceGrandTotal = moneyService.toMoney(openAmt)
					.negateIf(docBaseType.isCreditMemo())
					// .negateIf(!docBaseType.isSales()) // NOP, it's already adjusted
					;

			final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
			invoiceRecord.setC_Currency_ID(invoiceGrandTotal.getCurrencyId().getRepoId());
			invoiceRecord.setGrandTotal(invoiceGrandTotal.toBigDecimal());
			saveRecord(invoiceRecord);
			invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		}

		return InvoiceRow.builder()
				.invoiceId(invoiceId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				.docTypeName(TranslatableStrings.anyLanguage("invoice doc type"))
				.documentNo("invoiceNo_" + invoiceId.getRepoId())
				.dateInvoiced(dateInvoiced)
				.bpartner(IntegerLookupValue.of(bpartnerId.getRepoId(), "BPartner"))
				.docBaseType(docBaseType)
				.grandTotal(openAmt)
				.openAmt(openAmt)
				.discountAmt(discountAmt != null
						? Amount.of(discountAmt, openAmt.getCurrencyCode())
						: Amount.zero(openAmt.getCurrencyCode()))
				.serviceFeeAmt(serviceFeeAmt != null
						? Amount.of(serviceFeeAmt, openAmt.getCurrencyCode())
						: Amount.zero(openAmt.getCurrencyCode()))
				.build();
	}

	@Nested
	public class toPayableDocumentWithServiceFee
	{
		private DocTypeId serviceInvoiceDocTypeId;
		private ProductId serviceFeeProductId;
		private BPartnerId feeCompanyBPid1;
		private BPartnerId feeCompanyBPid2;

		@BeforeEach
		public void beforeEach()
		{
			serviceInvoiceDocTypeId = DocTypeId.ofRepoId(222);
			serviceFeeProductId = ProductId.ofRepoId(333);
			feeCompanyBPid1 = BPartnerId.ofRepoId(6661);
			feeCompanyBPid2 = BPartnerId.ofRepoId(6662);

			createMultipleProcessingServiceCompanyConfigs();
		}

		private void createMultipleProcessingServiceCompanyConfigs()
		{
			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("3")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid1)
					.build();

			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("2")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid1)
					.build();

			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("1")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-01-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid1)
					.build();

			//

			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("3")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid2)
					.build();

			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("2")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid2)
					.build();

			ProcessingServiceCompanyConfig()
					.feePercentageOfGrandTotal("1")
					.customerId(bpartnerId)
					.validFrom(LocalDate.parse("2020-01-11").atStartOfDay(ZoneId.of("UTC+5")))
					.serviceCompanyBPartnerId(feeCompanyBPid2)
					.build();
		}

		@Builder(builderMethodName = "ProcessingServiceCompanyConfig", builderClassName = "ConfigBuilder")
		private void createConfig(
				@NonNull final String feePercentageOfGrandTotal,
				@NonNull @Singular final Set<BPartnerId> customerIds,
				@NonNull final ZonedDateTime validFrom,
				@NonNull final BPartnerId serviceCompanyBPartnerId)
		{
			Check.assumeNotEmpty(customerIds, "customerIds is not empty");

			final I_InvoiceProcessingServiceCompany configRecord = newInstance(I_InvoiceProcessingServiceCompany.class);
			configRecord.setIsActive(true);
			configRecord.setServiceCompany_BPartner_ID(serviceCompanyBPartnerId.getRepoId());
			configRecord.setServiceInvoice_DocType_ID(serviceInvoiceDocTypeId.getRepoId());
			configRecord.setServiceFee_Product_ID(serviceFeeProductId.getRepoId());
			configRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
			saveRecord(configRecord);

			for (final BPartnerId customerId : customerIds)
			{
				final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignmentRecord = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
				assignmentRecord.setIsActive(true);
				assignmentRecord.setInvoiceProcessingServiceCompany_ID(configRecord.getInvoiceProcessingServiceCompany_ID());
				assignmentRecord.setC_BPartner_ID(customerId.getRepoId());
				assignmentRecord.setFeePercentageOfGrandTotal(new BigDecimal(feePercentageOfGrandTotal));
				saveRecord(assignmentRecord);
			}
		}


		@Test
		public void paymentDateInValidFrom()
		{
			//
			// Create test data
			final LocalDate paymentDate = LocalDate.of(2020, Month.FEBRUARY, 11);

			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).serviceFeeAmt("10").build();
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).bpartnerIdOverride(feeCompanyBPid2).paymentDateTrxOverride(paymentDate).build();

			final List<PaymentDocument> paymentDocuments = Collections.singletonList(PaymentsViewAllocateCommand.toPaymentDocument(paymentRow, moneyService));

			//
			// Run method under test
			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(invoiceRow, paymentDocuments, moneyService, invoiceProcessingServiceCompanyService);

			//
			// Check output
			checkPayableDocument(payableDocument, invoiceRow.getInvoiceId(), paymentDate, feeCompanyBPid2);
		}


		@Test
		public void paymentDateBeforeValidFrom()
		{
			//
			// Create test data
			final LocalDate paymentDate = LocalDate.of(1999, Month.JANUARY, 1);

			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).serviceFeeAmt("10").build();
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).bpartnerIdOverride(feeCompanyBPid1).paymentDateTrxOverride(paymentDate).build();

			final List<PaymentDocument> paymentDocuments = Collections.singletonList(PaymentsViewAllocateCommand.toPaymentDocument(paymentRow, moneyService));

			//
			// Run method under test
			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(invoiceRow, paymentDocuments, moneyService, invoiceProcessingServiceCompanyService);

			//
			// Check output
			checkPayableDocument(payableDocument, invoiceRow.getInvoiceId(), paymentDate, feeCompanyBPid1);
		}

		private void checkPayableDocument(final PayableDocument payableDocument, final InvoiceId invoiceId, final LocalDate evaluationDate, final BPartnerId feeCompanyBPartnerid)
		{
			assertThat(payableDocument.getSoTrx()).isEqualTo(SOTrx.SALES);
			assertThat(payableDocument.isCreditMemo()).isEqualTo(false);
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.isEqualToComparingFieldByField(AllocationAmounts.builder()
							.payAmt(Money.of(90, euroCurrencyId))
							.discountAmt(Money.zero(euroCurrencyId))
							.invoiceProcessingFee(Money.of(10, euroCurrencyId))
							.writeOffAmt(Money.zero(euroCurrencyId))
							.build());

			assertThat(payableDocument.getInvoiceProcessingFeeCalculation())
					.isEqualToComparingFieldByField(InvoiceProcessingFeeCalculation.builder()
							.orgId(orgId)
							.evaluationDate(TimeUtil.asZonedDateTime(evaluationDate))
							//
							.customerId(bpartnerId)
							.invoiceId(invoiceId)
							//
							.serviceCompanyBPartnerId(feeCompanyBPartnerid)
							.serviceInvoiceDocTypeId(serviceInvoiceDocTypeId)
							.serviceFeeProductId(serviceFeeProductId)
							.feeAmountIncludingTax(Amount.of(10, CurrencyCode.EUR))
							//

							.build());
		}
	}

	@Nested
	public class toPayableDocument
	{
		@Test
		public void customerInvoice()
		{
			final InvoiceRow row = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice)
					.openAmt(euro(100))
					.discountAmt("20")
					.build();

			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(row, Collections.emptyList(), moneyService, invoiceProcessingServiceCompanyService);
			assertThat(payableDocument.getSoTrx()).isEqualTo(SOTrx.SALES);
			assertThat(payableDocument.isCreditMemo()).isEqualTo(false);
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.isEqualToComparingFieldByField(AllocationAmounts.builder()
							.payAmt(Money.of(100 - 20, euroCurrencyId))
							.discountAmt(Money.of(20, euroCurrencyId))
							.build());
		}

		@Test
		public void customerCreditMemo()
		{
			final InvoiceRow row = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-100)).build();

			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(row, Collections.emptyList(), moneyService, invoiceProcessingServiceCompanyService);
			assertThat(payableDocument.getSoTrx()).isEqualTo(SOTrx.SALES);
			assertThat(payableDocument.isCreditMemo()).isEqualTo(true);
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(-100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.isEqualToComparingFieldByField(AllocationAmounts.ofPayAmt(Money.of(-100, euroCurrencyId)));
		}

		@Test
		public void vendorInvoice()
		{
			final InvoiceRow row = invoiceRow().docBaseType(InvoiceDocBaseType.VendorInvoice)
					.openAmt(euro(100))
					.discountAmt("20")
					.build();

			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(row, Collections.emptyList(), moneyService, invoiceProcessingServiceCompanyService);
			assertThat(payableDocument.getSoTrx()).isEqualTo(SOTrx.PURCHASE);
			assertThat(payableDocument.isCreditMemo()).isEqualTo(false);
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(-100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.isEqualToComparingFieldByField(AllocationAmounts.builder()
							.payAmt(Money.of(-100 + 20, euroCurrencyId))
							.discountAmt(Money.of(-20, euroCurrencyId))
							.build());
		}

		@Test
		public void vendorCreditMemo()
		{
			final InvoiceRow row = invoiceRow().docBaseType(InvoiceDocBaseType.VendorCreditMemo).openAmt(euro(-100)).build();

			final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(row, Collections.emptyList(), moneyService, invoiceProcessingServiceCompanyService);
			assertThat(payableDocument.getSoTrx()).isEqualTo(SOTrx.PURCHASE);
			assertThat(payableDocument.isCreditMemo()).isEqualTo(true);
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.isEqualToComparingFieldByField(AllocationAmounts.ofPayAmt(Money.of(100, euroCurrencyId)));
		}
	}

	@Nested
	public class toPaymentDocument
	{
		@Test
		public void inboundPayment()
		{
			final PaymentRow row = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).build();

			final PaymentDocument paymentDocument = PaymentsViewAllocateCommand.toPaymentDocument(row, moneyService);
			assertThat(paymentDocument.getType()).isEqualTo(PaymentDocumentType.RegularPayment);
			assertThat(paymentDocument.getPaymentDirection()).isEqualTo(PaymentDirection.INBOUND);
			assertThat(paymentDocument.getAmountToAllocateInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(paymentDocument.getAmountToAllocate()).isEqualTo(Money.of(100, euroCurrencyId));
		}

		@Test
		public void outboundPayment()
		{
			final PaymentRow row = paymentRow().direction(PaymentDirection.OUTBOUND).payAmt(euro(100)).build();

			final PaymentDocument paymentDocument = PaymentsViewAllocateCommand.toPaymentDocument(row, moneyService);
			assertThat(paymentDocument.getType()).isEqualTo(PaymentDocumentType.RegularPayment);
			assertThat(paymentDocument.getPaymentDirection()).isEqualTo(PaymentDirection.OUTBOUND);
			assertThat(paymentDocument.getAmountToAllocateInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(paymentDocument.getAmountToAllocate()).isEqualTo(Money.of(100, euroCurrencyId));
		}
	}

	@Nested
	public class run
	{
		@Test
		public void singleCustomerInvoice_to_singleInboundPayment()
		{
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.dateTrx(LocalDate.parse("2020-04-30"))
					.build()
					.run();

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToPayment)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(paymentRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("100", euroCurrencyId))
									.build())
							.build());
		}

		@Test
		public void customerInvoice_to_customerCreditMemo()
		{
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.dateTrx(LocalDate.parse("2020-04-30"))
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToCreditMemo)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(creditMemoRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("20", euroCurrencyId))
									.build())
							.payableOverUnderAmt(Money.of("80", euroCurrencyId))
							.build());
		}

		@Test
		public void vendorInvoice_to_vendorCreditMemo()
		{
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.VendorInvoice).openAmt(euro(100)).build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.VendorCreditMemo).openAmt(euro(-20)).build();

			assertInvoiceAllocatedAmt(invoiceRow.getInvoiceId(), "0");
			assertInvoiceAllocatedAmt(creditMemoRow.getInvoiceId(), "0");

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.dateTrx(LocalDate.parse("2020-04-30"))
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToCreditMemo)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(creditMemoRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("-20", euroCurrencyId))
									.build())
							.payableOverUnderAmt(Money.of("-80", euroCurrencyId))
							.build());

			assertInvoiceAllocatedAmt(invoiceRow.getInvoiceId(), "-20");
			assertInvoiceAllocatedAmt(creditMemoRow.getInvoiceId(), "+20");
		}

		@Test
		public void customerInvoice_customerCreditMemo_and_inboundPayment()
		{
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(80)).build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.dateTrx(LocalDate.parse("2020-04-30"))
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(2);
			assertThat(result.getCandidates().get(0))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToCreditMemo)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(creditMemoRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("20", euroCurrencyId))
									.build())
							.payableOverUnderAmt(Money.of("80", euroCurrencyId))
							.build());
			assertThat(result.getCandidates().get(1))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToPayment)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(paymentRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("80", euroCurrencyId))
									.build())
							.build());
		}

		@Test
		public void customerInvoice_customerCreditMemo_and_inboundPayment_partial()
		{
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(200)).build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.dateTrx(LocalDate.parse("2020-04-30"))
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(2);
			assertThat(result.getCandidates().get(0))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToCreditMemo)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(creditMemoRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("20", euroCurrencyId))
									.build())
							.payableOverUnderAmt(Money.of("80", euroCurrencyId))
							.build());
			assertThat(result.getCandidates().get(1))
					.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
							.type(AllocationLineCandidateType.InvoiceToPayment)
							.orgId(orgId)
							.bpartnerId(bpartnerId)
							.payableDocumentRef(toRecordRef(invoiceRow))
							.paymentDocumentRef(toRecordRef(paymentRow))
							.dateTrx(LocalDate.parse("2020-04-30"))
							.dateAcct(LocalDate.parse("2020-04-30"))
							.amounts(AllocationAmounts.builder()
									.payAmt(Money.of("80", euroCurrencyId))
									.build())
							.paymentOverUnderAmt(Money.of("120", euroCurrencyId))
							.build());
		}
	}
}
