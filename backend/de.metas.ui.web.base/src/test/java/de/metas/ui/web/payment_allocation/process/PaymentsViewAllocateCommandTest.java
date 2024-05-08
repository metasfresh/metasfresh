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

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.IPaymentDocument.PaymentDocumentType;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceAmtMultiplier;
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
import de.metas.payment.PaymentAmtMultiplier;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class PaymentsViewAllocateCommandTest
{
	private static final boolean INVOICE_AMT_IsSOTrxAdjusted = false;
	private static final boolean INVOICE_AMT_IsCreditMemoAdjusted = true;

	private final OrgId orgId = OrgId.ofRepoId(1);
	private final LocalDate dateInvoiced = LocalDate.parse("2020-04-01");
	private final LocalDate paymentDateTrx = LocalDate.parse("2020-04-25");

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

		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(currencyRepo);

		moneyService = new MoneyService(currencyRepo);
		invoiceProcessingServiceCompanyService = new InvoiceProcessingServiceCompanyService(
				new InvoiceProcessingServiceCompanyConfigRepository(),
				moneyService);
		invoicesDAO = Services.get(IInvoiceDAO.class);
		allocationDAO = Services.get(IAllocationDAO.class);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		bpartnerId = createBPartnerId();

		SpringContextHolder.registerJUnitBean(moneyService);
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

	@Builder(builderMethodName = "paymentRow", builderClassName = "$PaymentRowBuilder")
	private PaymentRow createPaymentRow(
			@NonNull final PaymentDirection direction,
			@NonNull final Amount payAmt,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String paymentDateTrx)
	{
		final I_C_Payment paymentRecord = newInstance(I_C_Payment.class);
		saveRecord(paymentRecord);
		final PaymentId paymentId = PaymentId.ofRepoId(paymentRecord.getC_Payment_ID());

		final BPartnerId bpartnerIdEffective = bpartnerId != null ? bpartnerId : this.bpartnerId;

		return PaymentRow.builder()
				.paymentId(paymentId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				.documentNo("paymentNo_" + paymentId.getRepoId())
				.dateTrx(paymentDateTrx != null ? LocalDate.parse(paymentDateTrx) : this.paymentDateTrx)
				.bpartner(IntegerLookupValue.of(bpartnerIdEffective.getRepoId(), "BPartner"))
				.paymentDirection(direction)
				.paymentAmtMultiplier(PaymentAmtMultiplier.builder().paymentDirection(direction).isOutboundAdjusted(false).build())
				.payAmt(payAmt)
				.openAmt(payAmt)
				.build();
	}

	@Builder(builderMethodName = "invoiceRow", builderClassName = "$InvoiceRowBuilder")
	private InvoiceRow createInvoiceRow(
			@NonNull final InvoiceDocBaseType docBaseType,
			@NonNull final Amount openAmt,
			@Nullable final String discountAmt,
			@Nullable final String serviceFeeAmt,
			@Nullable final String dateInvoiced)
	{
		final InvoiceAmtMultiplier invoiceAmtMultiplier = InvoiceAmtMultiplier.builder()
				.soTrx(docBaseType.getSoTrx())
				.isCreditMemo(docBaseType.isCreditMemo())
				.isSOTrxAdjusted(INVOICE_AMT_IsSOTrxAdjusted)
				.isCreditMemoAdjusted(INVOICE_AMT_IsCreditMemoAdjusted)
				.build()
				.intern();

		final InvoiceId invoiceId;
		{
			final Money invoiceGrandTotal = invoiceAmtMultiplier.fromNotAdjustedAmount(moneyService.toMoney(openAmt));

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
				.dateInvoiced(dateInvoiced != null ? LocalDate.parse(dateInvoiced) : this.dateInvoiced)
				.bpartner(IntegerLookupValue.of(bpartnerId.getRepoId(), "BPartner"))
				.docBaseType(docBaseType)
				.invoiceAmtMultiplier(invoiceAmtMultiplier)
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

	@Test
	public void checkTestsAreUsingSameInvoiceAmtMultiplierAsRealLife()
	{
		final InvoiceAmtMultiplier multiplierInRealLife = PaymentAllocationRepository.toInvoiceAmtMultiplier(InvoiceDocBaseType.CustomerInvoice);

		//noinspection AssertThatBooleanCondition
		assertThat(multiplierInRealLife.isSOTrxAdjusted()).isEqualTo(INVOICE_AMT_IsSOTrxAdjusted);

		//noinspection AssertThatBooleanCondition
		assertThat(multiplierInRealLife.isCreditMemoAdjusted()).isEqualTo(INVOICE_AMT_IsCreditMemoAdjusted);
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
			assertThat(payableDocument.isCreditMemo()).isFalse();
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.usingRecursiveComparison()
					.isEqualTo(AllocationAmounts.builder()
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
			assertThat(payableDocument.isCreditMemo()).isTrue();
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(-100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.usingRecursiveComparison()
					.isEqualTo(AllocationAmounts.ofPayAmt(Money.of(-100, euroCurrencyId)));
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
			assertThat(payableDocument.isCreditMemo()).isFalse();
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(-100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.usingRecursiveComparison()
					.isEqualTo(AllocationAmounts.builder()
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
			assertThat(payableDocument.isCreditMemo()).isTrue();
			assertThat(payableDocument.getOpenAmtInitial()).isEqualTo(Money.of(100, euroCurrencyId));
			assertThat(payableDocument.getAmountsToAllocate())
					.usingRecursiveComparison()
					.isEqualTo(AllocationAmounts.ofPayAmt(Money.of(100, euroCurrencyId)));
		}

		@Nested
		public class WithServiceFee
		{
			private DocTypeId serviceInvoiceDocTypeId;
			private ProductId serviceFeeProductId;
			private BPartnerId feeCompanyId1;

			@BeforeEach
			public void beforeEach()
			{
				serviceInvoiceDocTypeId = DocTypeId.ofRepoId(222);
				serviceFeeProductId = ProductId.ofRepoId(333);
				feeCompanyId1 = BPartnerId.ofRepoId(6661);

				processingServiceCompanyConfig()
						.customerId(bpartnerId)
						.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")))
						.feePercentageOfGrandTotal("1")
						.serviceCompanyBPartnerId(feeCompanyId1)
						.build();
			}

			@Builder(builderMethodName = "processingServiceCompanyConfig", builderClassName = "$ConfigBuilder")
			private void createConfig(
					@NonNull final String feePercentageOfGrandTotal,
					@NonNull final BPartnerId customerId,
					@NonNull final ZonedDateTime validFrom,
					@NonNull final BPartnerId serviceCompanyBPartnerId)
			{
				final I_InvoiceProcessingServiceCompany configRecord = newInstance(I_InvoiceProcessingServiceCompany.class);
				configRecord.setIsActive(true);
				configRecord.setServiceCompany_BPartner_ID(serviceCompanyBPartnerId.getRepoId());
				configRecord.setServiceInvoice_DocType_ID(serviceInvoiceDocTypeId.getRepoId());
				configRecord.setServiceFee_Product_ID(serviceFeeProductId.getRepoId());
				configRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
				saveRecord(configRecord);

				final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignmentRecord = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
				assignmentRecord.setIsActive(true);
				assignmentRecord.setInvoiceProcessingServiceCompany_ID(configRecord.getInvoiceProcessingServiceCompany_ID());
				assignmentRecord.setC_BPartner_ID(customerId.getRepoId());
				assignmentRecord.setFeePercentageOfGrandTotal(new BigDecimal(feePercentageOfGrandTotal));
				saveRecord(assignmentRecord);
			}

			@ParameterizedTest
			@ValueSource(strings = { "2020-01-01", "2020-02-11", "2020-08-01" })
			public void salesInvoiceAndInboundPayment(final String paymentDateStr)
			{
				//
				// Create test data
				final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).serviceFeeAmt("10").build();
				final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).bpartnerId(feeCompanyId1).paymentDateTrx(paymentDateStr).build();

				//
				// Run method under test
				final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(
						invoiceRow,
						ImmutableList.of(PaymentsViewAllocateCommand.toPaymentDocument(paymentRow, moneyService)),
						moneyService,
						invoiceProcessingServiceCompanyService);

				assertThat(payableDocument)
						.usingRecursiveComparison()
						.isEqualTo(PayableDocument.builder()
										   .invoiceId(invoiceRow.getInvoiceId())
										   .bpartnerId(bpartnerId)
										   .documentNo(invoiceRow.getDocumentNo())
										   .soTrx(SOTrx.SALES)
										   .creditMemo(false)
										   .openAmt(Money.of(100, euroCurrencyId))
										   .amountsToAllocate(AllocationAmounts.builder()
																	  .payAmt(Money.of(90, euroCurrencyId))
																	  .invoiceProcessingFee(Money.of(10, euroCurrencyId))
																	  .build())
										   .invoiceProcessingFeeCalculation(InvoiceProcessingFeeCalculation.builder()
																					.orgId(orgId)
																					.evaluationDate(LocalDate.parse(paymentDateStr).atStartOfDay(SystemTime.zoneId()))
																					.customerId(bpartnerId)
																					.invoiceId(invoiceRow.getInvoiceId())
																					.serviceCompanyBPartnerId(feeCompanyId1)
																					.serviceInvoiceDocTypeId(serviceInvoiceDocTypeId)
																					.serviceFeeProductId(serviceFeeProductId)
																					.feeAmountIncludingTax(Amount.of(10, CurrencyCode.EUR))
																					.build())
										   .clientAndOrgId(invoiceRow.getClientAndOrgId())
										   .date(invoiceRow.getDateInvoiced())
										   .currencyConversionTypeId(invoiceRow.getCurrencyConversionTypeId())
										   .build());
			}

			@Test
			public void salesCreditMemoInvoice()
			{
				//
				// Create test data
				final ZonedDateTime now = LocalDate.parse("2020-08-01").atStartOfDay(ZoneId.of("UTC+5"));
				SystemTime.setFixedTimeSource(now);
				final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-100)).serviceFeeAmt("10").build();

				//
				// Run method under test
				final PayableDocument payableDocument = PaymentsViewAllocateCommand.toPayableDocument(
						invoiceRow,
						ImmutableList.of(),
						moneyService,
						invoiceProcessingServiceCompanyService);

				//
				// Check output
				assertThat(payableDocument)
						.usingRecursiveComparison()
						.ignoringFields("reference.modelRef.timestamp")
						.isEqualTo(PayableDocument.builder()
										   .invoiceId(invoiceRow.getInvoiceId())
										   .bpartnerId(bpartnerId)
										   .documentNo(invoiceRow.getDocumentNo())
										   .soTrx(SOTrx.SALES)
										   .creditMemo(true)
										   .openAmt(Money.of(-100, euroCurrencyId))
										   .amountsToAllocate(AllocationAmounts.builder()
																	  .payAmt(Money.of(-110, euroCurrencyId))
																	  .invoiceProcessingFee(Money.of(+10, euroCurrencyId))
																	  .build())
										   .invoiceProcessingFeeCalculation(InvoiceProcessingFeeCalculation.builder()
																					.orgId(orgId)
																					.evaluationDate(now)
																					.customerId(bpartnerId)
																					.invoiceId(invoiceRow.getInvoiceId())
																					.serviceCompanyBPartnerId(feeCompanyId1)
																					.serviceInvoiceDocTypeId(serviceInvoiceDocTypeId)
																					.serviceFeeProductId(serviceFeeProductId)
																					.feeAmountIncludingTax(Amount.of(+10, CurrencyCode.EUR))
																					.build())
										   .clientAndOrgId(invoiceRow.getClientAndOrgId())
										   .date(invoiceRow.getDateInvoiced())
										   .currencyConversionTypeId(invoiceRow.getCurrencyConversionTypeId())
										   .build());
			}
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
			assertThat(paymentDocument.getAmountToAllocateInitial()).isEqualTo(Money.of(-100, euroCurrencyId));
			assertThat(paymentDocument.getAmountToAllocate()).isEqualTo(Money.of(-100, euroCurrencyId));
		}
	}

	@Nested
	public class run
	{
		@Test
		public void singleCustomerInvoice_to_singleInboundPayment()
		{
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).paymentDateTrx("2020-04-17").build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).dateInvoiced("2020-04-09").build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.build()
					.run();

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToPayment)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(paymentRow))
									   .dateTrx(LocalDate.parse("2020-04-17"))
									   .dateAcct(LocalDate.parse("2020-04-17"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("100", euroCurrencyId))
														.build())
									   .build());
		}

		@Test
		public void customerInvoice_to_customerCreditMemo()
		{
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).dateInvoiced("2020-04-10").build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).dateInvoiced("2020-04-11").build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToCreditMemo)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(creditMemoRow))
									   .dateTrx(LocalDate.parse("2020-04-11"))
									   .dateAcct(LocalDate.parse("2020-04-11"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("20", euroCurrencyId))
														.build())
									   .payableOverUnderAmt(Money.of("80", euroCurrencyId))
									   .build());
		}

		@Test
		public void vendorInvoice_to_vendorCreditMemo()
		{
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.VendorInvoice).openAmt(euro(100)).dateInvoiced("2020-04-23").build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.VendorCreditMemo).openAmt(euro(-20)).dateInvoiced("2020-04-24").build();

			assertInvoiceAllocatedAmt(invoiceRow.getInvoiceId(), "0");
			assertInvoiceAllocatedAmt(creditMemoRow.getInvoiceId(), "0");

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToCreditMemo)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(creditMemoRow))
									   .dateTrx(LocalDate.parse("2020-04-24"))
									   .dateAcct(LocalDate.parse("2020-04-24"))
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
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(80)).paymentDateTrx("2020-04-19").build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).dateInvoiced("2020-04-10").build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).dateInvoiced("2020-04-11").build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(2);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToCreditMemo)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(creditMemoRow))
									   .dateTrx(LocalDate.parse("2020-04-11"))
									   .dateAcct(LocalDate.parse("2020-04-11"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("20", euroCurrencyId))
														.build())
									   .payableOverUnderAmt(Money.of("80", euroCurrencyId))
									   .build());
			assertThat(result.getCandidates().get(1))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToPayment)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(paymentRow))
									   .dateTrx(LocalDate.parse("2020-04-19"))
									   .dateAcct(LocalDate.parse("2020-04-19"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("80", euroCurrencyId))
														.build())
									   .build());
		}

		@Test
		public void customerInvoice_customerCreditMemo_and_inboundPayment_partial()
		{
			final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(200)).paymentDateTrx("2020-04-22").build();
			final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).dateInvoiced("2020-04-06").build();
			final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).dateInvoiced("2020-04-07").build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(paymentRow)
					.invoiceRow(invoiceRow)
					.invoiceRow(creditMemoRow)
					.allowPurchaseSalesInvoiceCompensation(false)
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(2);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToCreditMemo)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(creditMemoRow))
									   .dateTrx(LocalDate.parse("2020-04-07"))
									   .dateAcct(LocalDate.parse("2020-04-07"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("20", euroCurrencyId))
														.build())
									   .payableOverUnderAmt(Money.of("80", euroCurrencyId))
									   .build());
			assertThat(result.getCandidates().get(1))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InvoiceToPayment)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(invoiceRow))
									   .paymentDocumentRef(toRecordRef(paymentRow))
									   .dateTrx(LocalDate.parse("2020-04-22"))
									   .dateAcct(LocalDate.parse("2020-04-22"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("80", euroCurrencyId))
														.build())
									   .paymentOverUnderAmt(Money.of("120", euroCurrencyId))
									   .build());
		}

		@Test
		public void inboundPayment_outboundPayment()
		{
			final PaymentRow inboundPaymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).paymentDateTrx("2020-04-22").build();
			final PaymentRow outboundPaymentRow = paymentRow().direction(PaymentDirection.OUTBOUND).payAmt(euro(100)).paymentDateTrx("2020-04-22").build();

			final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
					.moneyService(moneyService)
					.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
					.paymentRow(inboundPaymentRow)
					.paymentRow(outboundPaymentRow)
					.allowPurchaseSalesInvoiceCompensation(false) // not relevant
					.build()
					.run();

			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).hasSize(1);
			assertThat(result.getCandidates().get(0))
					.usingRecursiveComparison()
					.ignoringFields("payableDocumentRef.modelRef.timestamp", "paymentDocumentRef.modelRef.timestamp")
					.isEqualTo(AllocationLineCandidate.builder()
									   .type(AllocationLineCandidateType.InboundPaymentToOutboundPayment)
									   .orgId(orgId)
									   .bpartnerId(bpartnerId)
									   .payableDocumentRef(toRecordRef(outboundPaymentRow))
									   .paymentDocumentRef(toRecordRef(inboundPaymentRow))
									   .dateTrx(LocalDate.parse("2020-04-22"))
									   .dateAcct(LocalDate.parse("2020-04-22"))
									   .amounts(AllocationAmounts.builder()
														.payAmt(Money.of("-100", euroCurrencyId))
														.build())
									   .payableOverUnderAmt(Money.of("0", euroCurrencyId))
									   .build());
		}
	}
}
