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

package de.metas.banking.payment.paymentallocation.service;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InboundPaymentToOutboundPayment;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceDiscountOrWriteOff;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceToCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceToPayment;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice;
import static de.metas.invoice.InvoiceDocBaseType.CustomerCreditMemo;
import static de.metas.invoice.InvoiceDocBaseType.CustomerInvoice;
import static de.metas.invoice.InvoiceDocBaseType.VendorCreditMemo;
import static de.metas.invoice.InvoiceDocBaseType.VendorInvoice;
import static de.metas.payment.PaymentDirection.INBOUND;
import static de.metas.payment.PaymentDirection.OUTBOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({ "NestedAssignment", "SameParameterValue" })
public class PaymentAllocationBuilderTest
{
	// services
	private IAllocationDAO allocationDAO;
	private IPaymentDAO paymentDAO;
	private IInvoiceBL invoiceBL;
	private IInvoiceDAO invoicesDAO;

	private int nextInvoiceId = 1;
	private int nextPaymentId = 1;
	private final ZoneId adOrgTimeZone = ZoneId.of("Europe/Berlin");
	private OrgId adOrgId;
	private final ClientId clientId = ClientId.ofRepoId(1000000); // just a dummy value
	private final BPartnerId bpartnerId = BPartnerId.ofRepoId(1); // dummy value
	private CurrencyId euroCurrencyId;
	private CurrencyId chfCurrencyId;
	private Map<InvoiceDocBaseType, I_C_DocType> invoiceDocTypes;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// services
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		allocationDAO = Services.get(IAllocationDAO.class);
		paymentDAO = Services.get(IPaymentDAO.class);
		invoiceBL = Services.get(IInvoiceBL.class);
		invoicesDAO = Services.get(IInvoiceDAO.class);

		adOrgId = AdempiereTestHelper.createOrgWithTimeZone(adOrgTimeZone);
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chfCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
		invoiceDocTypes = new HashMap<>();

		SpringContextHolder.registerJUnitBean(MoneyService.class, new MoneyService(new CurrencyRepository()));
	}

	private Money money(@Nullable final String amount, @Nullable CurrencyId currencyId)
	{
		final BigDecimal amountBD = amount != null && Check.isNotBlank(amount) ? new BigDecimal(amount) : BigDecimal.ZERO;
		currencyId = currencyId == null ? euroCurrencyId : currencyId;
		return Money.of(amountBD, currencyId);
	}

	private void assertExpected(final List<AllocationLineCandidate> candidatesExpected, final PaymentAllocationBuilder builder)
	{
		final PaymentAllocationResult result = builder.build();

		final ImmutableList<PayableDocument> invoices = builder.getPayableDocuments();
		if (!invoices.isEmpty())
		{
			System.out.println("Payable documents: -------------------------------------------------------------- ");
			for (final PayableDocument invoice : invoices)
			{
				System.out.println("" + invoice);
			}
		}

		final ImmutableList<PaymentDocument> payments = builder.getPaymentDocuments();
		if (!payments.isEmpty())
		{
			System.out.println("Payment documents: -------------------------------------------------------------- ");
			for (final PaymentDocument payment : payments)
			{
				System.out.println("" + payment);
			}
		}

		assertExpected(candidatesExpected, result.getCandidates());

		assertAllocationHdrCount(candidatesExpected.size());
	}

	private void assertExpected(
			final List<AllocationLineCandidate> candidatesExpected,
			final List<AllocationLineCandidate> candidatesActual)
	{
		dumpCandidates("Actual candidates", candidatesActual);
		dumpCandidates("Expected candidates", candidatesExpected);

		final int expectedSize = candidatesExpected.size();
		assertThat(candidatesActual).hasSize(expectedSize);
		for (int i = 0; i < expectedSize; i++)
		{
			assertThat(candidatesActual.get(i))
					.as("candidate " + (i + 1) + "/" + expectedSize)
					.isEqualToComparingFieldByField(candidatesExpected.get(i));
		}
	}

	private PaymentAllocationBuilder newPaymentAllocationBuilder(
			final Collection<PayableDocument> invoices,
			final Collection<PaymentDocument> payments)
	{
		return PaymentAllocationBuilder.newBuilder()
				.payableDocuments(invoices)
				.paymentDocuments(payments);
	}

	/**
	 * NOTE: amounts shall be CreditMemo adjusted and AP adjusted
	 */
	@Builder(builderMethodName = "invoice", builderClassName = "$PayableDocumentBuilder")
	private PayableDocument newInvoice(
			final InvoiceDocBaseType type,
			final String date,
			final String open,
			final String pay,
			final String discount,
			final String writeOff,
			final String invoiceProcessingFee,
			final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation,
			@Nullable final CurrencyId currency)
	{
		final Money openAmt = money(open, currency);

		final AllocationAmounts amountsToAllocate = AllocationAmounts.builder()
				.payAmt(money(pay, currency))
				.discountAmt(money(discount, currency))
				.writeOffAmt(money(writeOff, currency))
				.invoiceProcessingFee(money(invoiceProcessingFee, currency))
				.build();

		final LocalDate acctDate = LocalDate.parse(date);

		//
		// Create the invoice record (needed for the BL which calculates how much was allocated)
		final I_C_Invoice invoice;
		{
			final Money invoiceGrandTotal = openAmt
					.negateIf(type.isCreditMemo())
					.negateIf(!type.isSales());

			final int invoiceId = nextInvoiceId++;
			final I_C_DocType docType = getInvoiceDocType(type);
			invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
			invoice.setC_Invoice_ID(invoiceId);
			invoice.setDocumentNo("InvoiceDocNo" + invoiceId);
			invoice.setC_DocType_ID(docType.getC_DocType_ID());
			invoice.setIsSOTrx(docType.isSOTrx());
			invoice.setDateInvoiced(TimeUtil.asTimestamp(acctDate));
			invoice.setC_BPartner_ID(bpartnerId.getRepoId());
			invoice.setC_Currency_ID(invoiceGrandTotal.getCurrencyId().getRepoId());
			invoice.setGrandTotal(invoiceGrandTotal.toBigDecimal());
			invoice.setProcessed(true);
			invoice.setDocStatus(IDocument.STATUS_Completed);
			invoice.setDateAcct(TimeUtil.asTimestamp(acctDate));
			InterfaceWrapperHelper.saveRecord(invoice);
		}

		return PayableDocument.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.documentNo(invoice.getDocumentNo())
				.soTrx(SOTrx.ofBoolean(invoice.isSOTrx()))
				.creditMemo(type.isCreditMemo())
				.openAmt(openAmt)
				.amountsToAllocate(amountsToAllocate)
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(clientId, adOrgId))
				.date(acctDate)
				.build();
	}

	private I_C_DocType getInvoiceDocType(final InvoiceDocBaseType InvoiceDocBaseType)
	{
		return invoiceDocTypes.computeIfAbsent(InvoiceDocBaseType, this::createInvoiceDocType);
	}

	private I_C_DocType createInvoiceDocType(final InvoiceDocBaseType invoiceDocBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(invoiceDocBaseType.getCode());
		docType.setName(invoiceDocBaseType.toString());
		docType.setIsSOTrx(invoiceDocBaseType.isSales());
		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	@Builder(builderMethodName = "payment", builderClassName = "$PaymentDocumentBuilder")
	private PaymentDocument newPayment0(
			@NonNull final PaymentDirection direction,
			final String open,
			final String amtToAllocate,
			@Nullable final CurrencyId currency,
			@NonNull final String date)
	{
		final CurrencyId currencyEffective = currency == null ? euroCurrencyId : currency;

		//
		// Create a dummy record (needed for the BL which calculates how much was allocated)
		final int paymentId = nextPaymentId++;
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setC_Payment_ID(paymentId);
		payment.setDocumentNo("PaymentDocNo" + paymentId);
		payment.setC_BPartner_ID(bpartnerId.getRepoId());
		payment.setC_Currency_ID(currencyEffective.getRepoId());
		InterfaceWrapperHelper.save(payment);

		return PaymentDocument.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.documentNo(payment.getDocumentNo())
				.paymentDirection(direction)
				.openAmt(money(open, currencyEffective)
						// .negateIf(direction.isOutboundPayment())
				)
				.amountToAllocate(money(amtToAllocate, currencyEffective)
						// .negateIf(direction.isOutboundPayment())
				)
				.dateTrx(LocalDate.parse(date))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(clientId, adOrgId))
				.paymentCurrencyContext(PaymentCurrencyContext.NONE)
				.build();
	}

	@Builder(builderMethodName = "allocation", builderClassName = "$AllocationBuilder")
	private AllocationLineCandidate createAllocationCandidate(
			@NonNull final AllocationLineCandidateType type,
			@Nullable final TableRecordReference payableRef,
			@Nullable final TableRecordReference paymentRef,
			@NonNull final String date,
			@Nullable final String allocatedAmt,
			@Nullable final String discountAmt,
			@Nullable final String writeOffAmt,
			@Nullable final String invoiceProcessingFee,
			@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation,
			@Nullable final String overUnderAmt,
			@Nullable final String paymentOverUnderAmt,
			@Nullable final CurrencyId currency
	)
	{
		return AllocationLineCandidate.builder()
				.type(type)
				.orgId(adOrgId)
				.bpartnerId(bpartnerId)
				//
				.payableDocumentRef(payableRef)
				.paymentDocumentRef(paymentRef)
				//
				.dateTrx(LocalDate.parse(date))
				.dateAcct(LocalDate.parse(date))
				//
				.amounts(AllocationAmounts.builder()
								 .payAmt(money(allocatedAmt, currency))
								 .discountAmt(money(discountAmt, currency))
								 .writeOffAmt(money(writeOffAmt, currency))
								 .invoiceProcessingFee(money(invoiceProcessingFee, currency))
								 .build())
				.payableOverUnderAmt(money(overUnderAmt, currency))
				.paymentOverUnderAmt(money(paymentOverUnderAmt, currency))
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				//
				.build();
	}

	private void dumpCandidates(final String title, final Iterable<AllocationLineCandidate> candidates)
	{
		System.out.println(" " + title + ": -----------------------------");
		int index = 0;
		for (final AllocationLineCandidate cand : candidates)
		{
			index++;
			System.out.println(index + ". " + cand);
		}
	}

	private void assertInvoiceAllocatedAmt(final InvoiceId invoiceId, final BigDecimal expectedAllocatedAmt)
	{
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);

		final BigDecimal actualAllocatedAmt = allocationDAO.retrieveAllocatedAmt(invoice);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for invoice " + invoiceId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private void assertInvoiceAllocatedAmt(final InvoiceId invoiceId, final int expectedAllocatedAmtInt)
	{
		assertInvoiceAllocatedAmt(invoiceId, BigDecimal.valueOf(expectedAllocatedAmtInt));
	}

	private void assertInvoiceAllocated(final InvoiceId invoiceId, final boolean expectedAllocated)
	{
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);
		final boolean ignoreProcessed = false;
		invoiceBL.testAllocation(invoice, ignoreProcessed);

		assertThat(invoice.isPaid())
				.as("Invoice allocated: " + invoiceId)
				.isEqualTo(expectedAllocated);
	}

	private void assertPaymentAllocatedAmt(final PaymentId paymentId, final BigDecimal expectedAllocatedAmt)
	{
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		final BigDecimal actualAllocatedAmt = paymentDAO.getAllocatedAmt(payment);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for payment " + paymentId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private void assertPaymentAllocatedAmt(final PaymentId paymentId, final int expectedAllocatedAmtInt)
	{
		assertPaymentAllocatedAmt(paymentId, BigDecimal.valueOf(expectedAllocatedAmtInt));
	}

	private void assertAllocationHdrCount(final int expectedCount)
	{
		final int actualCount = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_AllocationHdr.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
		assertThat(actualCount)
				.as("C_AllocationHdr count")
				.isEqualTo(expectedCount);
	}

	//
	//
	// -----------------------------------------------
	//
	//

	@Nested
	class DifferentCurrencies
	{
		@Test
		void oneSalesInvoice_OnePayment()
		{
			createConversionRates(3);

			final PaymentDocument payment;
			final PayableDocument invoice;
			final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of(
							invoice = invoice().type(CustomerInvoice).currency(euroCurrencyId).open("9000").pay("6000").discount("1599").writeOff("401").date("2021-01-08").build()
					),
					// Payments
					ImmutableList.of(
							payment = payment().direction(INBOUND).currency(chfCurrencyId).open("18000").amtToAllocate("18000").date("2021-01-09").build()
					));

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(InvoiceToPayment)
							.payableRef(invoice.getReference())
							.paymentRef(payment.getReference())
							.allocatedAmt("6000").discountAmt("1599").writeOffAmt("401").overUnderAmt("1000")
							.paymentOverUnderAmt("0")
							.date("2021-01-09")
							.build()
			);

			//
			// Check
			assertExpected(candidatesExpected, builder);
			//
			assertInvoiceAllocatedAmt(invoice.getInvoiceId(), 6000 + 1599 + 401);
			assertInvoiceAllocated(invoice.getInvoiceId(), false);
			//
			assertPaymentAllocatedAmt(payment.getPaymentId(), 18000);
		}

		@Test
		public void test_CustomerInvoiceAndCreditMemo_NoPayments()
		{
			createConversionRates(3);

			final PayableDocument invoice1, invoice2;
			final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of(
							invoice1 = invoice().type(CustomerInvoice).currency(chfCurrencyId).open("5000").pay("999").date("2021-01-05").build(),
							invoice2 = invoice().type(CustomerCreditMemo).currency(euroCurrencyId).open("-333").pay("-333").date("2021-01-06").build())
					// Payments
					, ImmutableList.of());

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(InvoiceToCreditMemo)
							.currency(chfCurrencyId)
							.payableRef(invoice1.getReference())
							.paymentRef(invoice2.getReference())
							.allocatedAmt("999")
							.overUnderAmt("4001")
							.date("2021-01-06")
							.build());

			//
			// Check
			assertExpected(candidatesExpected, builder);
			//
			assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), +999);
			assertInvoiceAllocated(invoice1.getInvoiceId(), false);
			//
			assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), -333);
			assertInvoiceAllocated(invoice2.getInvoiceId(), true);
		}

		@Test
		@Disabled("Should be fixed in https://github.com/metasfresh/metasfresh/issues/9910")
		public void test_NoInvoices_TwoPayments()
		{
			/*
			 * Note to future developer:
			 * This is just copy-pasta from a different test.
			 * The expected numbers here are wrong and should be fixed.
			 */
			final PaymentDocument payment1, payment2;
			final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of()
					// Payments
					, ImmutableList.of(
							payment1 = payment().direction(OUTBOUND).currency(chfCurrencyId).open("-5000").amtToAllocate("-5000").build(),
							payment2 = payment().direction(INBOUND).currency(euroCurrencyId).open("5000").amtToAllocate("5000").build()));

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(InboundPaymentToOutboundPayment)
							.payableRef(payment1.getReference())
							.paymentRef(payment2.getReference())
							.allocatedAmt("-5000")
							.build());

			//
			// Check
			assertExpected(candidatesExpected, builder);
			assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
			assertPaymentAllocatedAmt(payment2.getPaymentId(), +5000);
		}

		/**
		 * multiplyRate for EURO is set; multiplyRate for CHF is derived
		 * <p>
		 * example:
		 * 1 euro = 10 chf and
		 * 1 chf  = 0.1 euro (1/10)
		 */
		void createConversionRates(final double multiplyRate)
		{
			final LocalDate dateFrom = LocalDate.parse("2000-01-01");
			final LocalDate dateTo = LocalDate.parse("2025-12-31");
			final CurrencyConversionTypeId conversionType = Services.get(ICurrencyDAO.class).getConversionTypeId(ConversionTypeMethod.Spot);

			final I_C_Conversion_Rate euroChfRate = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
			euroChfRate.setC_ConversionType_ID(conversionType.getRepoId());
			euroChfRate.setC_Currency_ID(euroCurrencyId.getRepoId());
			euroChfRate.setC_Currency_ID_To(chfCurrencyId.getRepoId());
			euroChfRate.setMultiplyRate(BigDecimal.valueOf(multiplyRate));
			euroChfRate.setValidFrom(TimeUtil.asTimestamp(dateFrom));
			euroChfRate.setValidTo(TimeUtil.asTimestamp(dateTo));
			InterfaceWrapperHelper.save(euroChfRate);

			final I_C_Conversion_Rate chfEuroRate = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
			chfEuroRate.setC_ConversionType_ID(conversionType.getRepoId());
			chfEuroRate.setC_Currency_ID(chfCurrencyId.getRepoId());
			chfEuroRate.setC_Currency_ID_To(euroCurrencyId.getRepoId());
			chfEuroRate.setMultiplyRate(BigDecimal.ONE.divide(BigDecimal.valueOf(multiplyRate), 13, RoundingMode.HALF_UP));
			chfEuroRate.setValidFrom(TimeUtil.asTimestamp(dateFrom));
			chfEuroRate.setValidTo(TimeUtil.asTimestamp(dateTo));
			InterfaceWrapperHelper.save(chfEuroRate);
		}
	}

	@Test
	public void test_NoDocuments()
	{
		assertThatThrownBy(() -> PaymentAllocationBuilder.newBuilder().build())
				.isInstanceOf(NoDocumentsPaymentAllocationException.class);
	}

	@Test
	public void test_OneVendorInvoice_NoPayments_JustDiscountAndWriteOff()
	{
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = PaymentAllocationBuilder.newBuilder()
				.defaultDateTrx(LocalDate.parse("2021-01-31"))
				.payableDocuments(ImmutableList.of(
						invoice1 = invoice().type(VendorInvoice).open("-8000").pay("0").discount("-100").writeOff("-200").date("2021-01-22").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceDiscountOrWriteOff)
						.payableRef(invoice1.getReference())
						.discountAmt("-100").writeOffAmt("-200").overUnderAmt("-7700")
						.date("2021-01-31")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), -(100 + 200));
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
	}

	@Test
	public void test_OneVendorInvoice_OneVendorPayment()
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(VendorInvoice).open("-8000").pay("-5000").discount("-100").writeOff("-200").date("2021-02-10").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").date("2021-02-11").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("-5000").discountAmt("-100").writeOffAmt("-200").overUnderAmt("-2700")
						.date("2021-02-11")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), -(5000 + 100 + 200));
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
	}

	@Test
	public void test_NoInvoices_TwoPayments()
	{
		final PaymentDocument payment1, payment2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(),
				// Payments
				ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").date("2021-02-10").build(),
						payment2 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").date("2021-02-11").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InboundPaymentToOutboundPayment)
						.payableRef(payment1.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("-5000")
						.date("2021-02-11")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
		assertPaymentAllocatedAmt(payment2.getPaymentId(), +5000);
	}

	@Test
	public void test_NoInvoices_ThreePayments()
	{
		final PaymentDocument payment1, payment2, payment3;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(),
				// Payments
				ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").date("2021-01-11").build(), //
						payment2 = payment().direction(INBOUND).open("3000").amtToAllocate("3000").date("2021-01-12").build(), //
						payment3 = payment().direction(INBOUND).open("2000").amtToAllocate("2000").date("2021-01-13").build() //
				));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InboundPaymentToOutboundPayment)
						.payableRef(payment1.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("-3000").overUnderAmt("-2000")
						.date("2021-01-12")
						.build(),
				allocation().type(InboundPaymentToOutboundPayment)
						.payableRef(payment1.getReference())
						.paymentRef(payment3.getReference())
						.allocatedAmt("-2000")
						.date("2021-01-13")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
		assertPaymentAllocatedAmt(payment2.getPaymentId(), +3000);
		assertPaymentAllocatedAmt(payment3.getPaymentId(), +2000);
	}

	@Test
	public void test_CustomerInvoiceAndCreditMemo_NoPayments()
	{
		final PayableDocument invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(CustomerInvoice).open("5000").pay("1000").date("2021-01-11").build(),
						invoice2 = invoice().type(CustomerCreditMemo).open("-1000").pay("-1000").date("2021-01-12").build()),
				// Payments
				ImmutableList.of());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice2.getReference())
						.allocatedAmt("1000")
						.overUnderAmt("4000")
						.date("2021-01-12")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), +1000);
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), -1000);
		assertInvoiceAllocated(invoice2.getInvoiceId(), true);
	}

	@Test
	public void test_VendorInvoiceAndCreditMemo_NoPayments()
	{
		final PayableDocument invoice, creditMemo;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice = invoice().type(VendorInvoice).open("-5000").pay("-1000").date("2021-01-11").build(),
						creditMemo = invoice().type(VendorCreditMemo).open("1000").pay("1000").date("2021-01-12").build()),
				// Payments
				ImmutableList.of());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice.getReference())
						.paymentRef(creditMemo.getReference())
						.allocatedAmt("-1000")
						.overUnderAmt("-4000")
						.date("2021-01-12")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice.getInvoiceId(), -1000);
		assertInvoiceAllocated(invoice.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(creditMemo.getInvoiceId(), +1000);
		assertInvoiceAllocated(creditMemo.getInvoiceId(), true);
	}

	@Nested
	public class test_SalesInvoiceAndPurchaseInvoice_NoPayments
	{
		private PayableDocument invoice1;
		private PayableDocument invoice2;
		private PaymentAllocationBuilder builder;

		@BeforeEach
		public void beforeEach()
		{
			builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of(
							invoice1 = invoice().type(CustomerInvoice).open("5000").pay("1000").date("2021-01-21").build(),
							invoice2 = invoice().type(VendorInvoice).open("-1000").pay("-1000").date("2021-01-22").build()),
					// Payments
					ImmutableList.of());
		}

		@Test
		public void purchaseSalesInvoiceCompensation_allowed()
		{
			builder.allowPurchaseSalesInvoiceCompensation(true);

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(SalesInvoiceToPurchaseInvoice)
							.payableRef(invoice1.getReference())
							.paymentRef(invoice2.getReference())
							.allocatedAmt("1000")
							.overUnderAmt("4000")
							.date("2021-01-22")
							.build());

			//
			// Check
			assertExpected(candidatesExpected, builder);
			//
			assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), +1000);
			assertInvoiceAllocated(invoice1.getInvoiceId(), false);
			//
			assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), -1000);
			assertInvoiceAllocated(invoice2.getInvoiceId(), true);
		}

		@Test
		public void purchaseSalesInvoiceCompensation_notAllowed()
		{
			builder
					.allowPurchaseSalesInvoiceCompensation(false)
					.allowPartialAllocations(true);

			final PaymentAllocationResult result = builder.build();
			System.out.println(result);

			assertThat(result.isOK()).isTrue();
			assertThat(result.getCandidates()).isEmpty();
		}
	}

	@Test
	public void test_Vendor_MultiInvoice_MultiPayment()
	{
		final PaymentDocument payment1 = payment().direction(OUTBOUND).open("5000").amtToAllocate("5000").date("2999-01-01").build();
		final PaymentDocument payment2 = payment().direction(OUTBOUND).open("5000").amtToAllocate("5000").date("2999-01-01").build();

		final PayableDocument invoice1 = invoice().type(VendorInvoice).open("8000").pay("6000").discount("1599").writeOff("1").date("2999-01-01").build();
		final PayableDocument invoice2 = invoice().type(VendorInvoice).open("7100").pay("3000").discount("50").writeOff("50").date("2999-01-01").build();

		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(invoice1, invoice2)
				// Payments
				, ImmutableList.of(payment1, payment2));

		assertThatThrownBy(builder::build)
				.isInstanceOf(MultipleVendorDocumentsException.class);
	}

	@Test
	public void test_Sales_MultiInvoice_MultiPayment()
	{
		final PaymentDocument payment1, payment2;
		final PayableDocument invoice1, invoice2, invoice3, invoice4;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(CustomerInvoice).open("8000").pay("6000").discount("1599").writeOff("1").date("2021-01-11").build(),
						invoice2 = invoice().type(CustomerInvoice).open("7100").pay("3000").discount("50").writeOff("50").date("2021-01-12").build(),
						invoice3 = invoice().type(CustomerInvoice).open("1600").pay("1500").discount("100").date("2021-01-13").build(),
						invoice4 = invoice().type(CustomerCreditMemo).open("-500").pay("-500").date("2021-01-14").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").date("2021-01-21").build(),
						payment2 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").date("2021-01-22").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice4.getReference())
						.allocatedAmt("500").discountAmt("0").writeOffAmt("1").overUnderAmt("7499")
						.date("2021-01-14")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("5000").discountAmt("0").writeOffAmt("0").overUnderAmt("2499")
						.date("2021-01-21")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("500").discountAmt("1599").writeOffAmt("0").overUnderAmt("400").paymentOverUnderAmt("4500")
						.date("2021-01-22")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice2.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("3000").discountAmt("50").writeOffAmt("50").overUnderAmt("4000").paymentOverUnderAmt("1500")
						.date("2021-01-22")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice3.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("1500").discountAmt("100").writeOffAmt("0").overUnderAmt("0")
						.date("2021-01-22")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), 500 + 1599 + 1 + 5000 + 500);
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), 3000 + 50 + 50);
		assertInvoiceAllocated(invoice2.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(invoice3.getInvoiceId(), 1500 + 100);
		assertInvoiceAllocated(invoice3.getInvoiceId(), true);
		//
		assertInvoiceAllocatedAmt(invoice4.getInvoiceId(), -500); // credit memo
		assertInvoiceAllocated(invoice4.getInvoiceId(), true);
		//
		assertPaymentAllocatedAmt(payment1.getPaymentId(), 5000);
		assertPaymentAllocatedAmt(payment2.getPaymentId(), 5000);
	}

	@Test
	public void test_CreditMemoInvoice_Payment()
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(VendorCreditMemo).open("100").pay("30").date("2021-02-10").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("50").amtToAllocate("30").date("2021-02-11").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("30")
						.overUnderAmt("70")
						.paymentOverUnderAmt("20")
						.date("2021-02-11")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), 30); // credit memo
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		//
		assertPaymentAllocatedAmt(payment1.getPaymentId(), 30);
	}

	@Test
	public void test_RegularVendorInvoice_CreditMemoVendorInvoice_OutboundPayment()
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PayableDocument invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(VendorInvoice).open("-165").pay("-100").discount("-10").writeOff("-5").date("2021-02-10").build(),
						invoice2 = invoice().type(VendorCreditMemo).open("80").pay("80").date("2021-02-11").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-20").amtToAllocate("-20").date("2021-02-12").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice2.getReference())
						.allocatedAmt("-80").writeOffAmt("-5").overUnderAmt("-80")
						.date("2021-02-11")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("-20").discountAmt("-10").overUnderAmt("-50")
						.date("2021-02-12")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), -(100 + 10 + 5));
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), +80); // CM
		assertInvoiceAllocated(invoice2.getInvoiceId(), true);
		//
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -20);
	}

	@Test
	public void test_OneRegularVendorInvoice_TwoCreditMemoVendorInvoices_OneOutboundPayment()
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PayableDocument invoice2;
		final PayableDocument invoice3;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(VendorInvoice).open("-165").pay("-100").discount("-10").writeOff("-5").date("2021-02-10").build(),
						invoice2 = invoice().type(VendorCreditMemo).open("80").pay("80").date("2021-02-11").build(),
						invoice3 = invoice().type(VendorCreditMemo).open("10").pay("10").date("2021-02-12").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-10").amtToAllocate("-10").date("2021-02-13").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice2.getReference())
						.allocatedAmt("-80").writeOffAmt("-5").overUnderAmt("-80")
						.date("2021-02-11")
						.build(),
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice3.getReference())
						.allocatedAmt("-10").discountAmt("0").writeOffAmt("0").overUnderAmt("-70")
						.date("2021-02-12")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("-10").discountAmt("-10").overUnderAmt("-50")
						.date("2021-02-13")
						.build());

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), -(100 + 10 + 5));
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getInvoiceId(), +80); // CM
		assertInvoiceAllocated(invoice2.getInvoiceId(), true);
		//
		assertInvoiceAllocatedAmt(invoice3.getInvoiceId(), +10); // CM
		assertInvoiceAllocated(invoice3.getInvoiceId(), true);
		//
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -10);
	}

	@Test
	public void test_VendorInvoice_CustomerReceipt_expect_Exception()
	{
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice().type(VendorInvoice).open("-100").pay("-100").date("2999-01-01").build())
				// Payments
				, ImmutableList.of(
						payment().direction(INBOUND).open("100").amtToAllocate("100").date("2999-01-01").build()));

		assertThatThrownBy(builder::build)
				.isInstanceOf(PayableDocumentNotAllocatedException.class);
	}

	@Nested
	public class payableRemainingOpenAmtPolicy
	{
		private PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy;
		private boolean allowPartialAllocations = false;

		private final LocalDate defaultDateTrx = LocalDate.parse("2021-01-15");
		private PayableDocument invoice1;
		private PaymentDocument payment1;
		private PaymentAllocationResult result;

		private void setup()
		{
			result = PaymentAllocationBuilder.newBuilder()
					.defaultDateTrx(defaultDateTrx)
					.payableDocuments(ImmutableList.of(
							invoice1 = invoice().type(CustomerInvoice).open("100").pay("100").date("2021-01-10").build()))
					.paymentDocuments(ImmutableList.of(
							payment1 = payment().direction(INBOUND).open("10").amtToAllocate("10").date("2021-01-11").build()))
					.payableRemainingOpenAmtPolicy(payableRemainingOpenAmtPolicy)
					.allowPartialAllocations(allowPartialAllocations)
					.build();
		}

		private void assertExpected(final AllocationLineCandidate... expected)
		{
			PaymentAllocationBuilderTest.this.assertExpected(ImmutableList.copyOf(expected), result.getCandidates());
		}

		@Test
		public void doNothing_disallowPartialAllocations()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DO_NOTHING;
			allowPartialAllocations = false;
			assertThatThrownBy(this::setup)
					.isInstanceOf(PayableDocumentNotAllocatedException.class);
		}

		@Test
		public void doNothing_allowPartialAllocations()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DO_NOTHING;
			allowPartialAllocations = true;
			setup();

			assertExpected(
					allocation().type(InvoiceToPayment)
							.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
							.allocatedAmt("10").overUnderAmt("90")
							.date("2021-01-11")
							.build());
		}

		@Test
		public void discount()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DISCOUNT;
			setup();

			assertExpected(
					allocation().type(InvoiceToPayment)
							.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
							.allocatedAmt("10").overUnderAmt("90")
							.date("2021-01-11")
							.build(),
					allocation().type(InvoiceDiscountOrWriteOff)
							.payableRef(invoice1.getReference())
							.discountAmt("90")
							.date(defaultDateTrx.toString())
							.build());
		}

		@Test
		public void writeOff()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.WRITE_OFF;
			setup();

			assertExpected(
					allocation().type(InvoiceToPayment)
							.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
							.allocatedAmt("10").overUnderAmt("90")
							.date("2021-01-11")
							.build(),
					allocation().type(InvoiceDiscountOrWriteOff)
							.payableRef(invoice1.getReference())
							.writeOffAmt("90")
							.date(defaultDateTrx.toString())
							.build());
		}
	}

	@Test
	public void test_automaticallyWriteOff()
	{
		final PayableDocument invoice1;
		final PaymentDocument payment1;

		final PaymentAllocationBuilder builder = PaymentAllocationBuilder.newBuilder()
				.defaultDateTrx(LocalDate.parse("2021-02-14"))
				.payableDocuments(ImmutableList.of(
						invoice1 = invoice().type(CustomerInvoice).open("100").pay("100").date("2021-02-10").build()))
				.paymentDocuments(ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("10").amtToAllocate("10").date("2021-02-11").build()))
				.payableRemainingOpenAmtPolicy(PayableRemainingOpenAmtPolicy.WRITE_OFF);

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("10")
						.overUnderAmt("90")
						.date("2021-02-11")
						.build(),

				allocation().type(InvoiceDiscountOrWriteOff)
						.payableRef(invoice1.getReference())
						.writeOffAmt("90")
						.date("2021-02-14")
						.build());

		assertExpected(candidatesExpected, builder);
	}

	@Nested
	public class InvoiceProcessingFee
	{
		@Test
		public void customerInvoice_and_inboundPayment()
		{
			final PayableDocument invoice1;
			final PaymentDocument payment1;

			final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = InvoiceProcessingFeeCalculation.builder()
					.orgId(adOrgId)
					.evaluationDate(LocalDate.parse("2021-01-23").atStartOfDay(adOrgTimeZone))
					.customerId(bpartnerId)
					.invoiceId(InvoiceId.ofRepoId(1111))
					.serviceCompanyBPartnerId(BPartnerId.ofRepoId(2222))
					.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(3333))
					.serviceFeeProductId(ProductId.ofRepoId(4444))
					.feeAmountIncludingTax(Amount.of(666, CurrencyCode.EUR)) // does not matter
					.build();

			final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of(
							invoice1 = invoice().type(CustomerInvoice).open("100").pay("88").discount("10").invoiceProcessingFee("2").invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation).date("2021-01-11").build())
					// Payments
					, ImmutableList.of(
							payment1 = payment().direction(INBOUND).open("88").amtToAllocate("88").date("2021-01-12").build()));

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(AllocationLineCandidateType.InvoiceProcessingFee)
							.payableRef(invoice1.getReference())
							.invoiceProcessingFee("2")
							.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
							.overUnderAmt("98")
							.date("2021-01-23")
							.build(),

					allocation().type(InvoiceToPayment)
							.payableRef(invoice1.getReference())
							.paymentRef(payment1.getReference())
							.allocatedAmt("88")
							.discountAmt("10")
							.date("2021-01-12")
							.build());

			final PaymentAllocationResult result = builder
					.dryRun() // would fail if is not dryRun because we cannot save InvoiceProcessingFee allocations
					.build();
			assertExpected(candidatesExpected, result.getCandidates());
		}

		/**
		 * Story: given a sales invoice of 100 EUR and a credit memo of 100 EUR.
		 * Each of them are processed by invoice processing company.
		 * For each of them, the invoice processing company is retaining 10%, i.e. 10 EUR.
		 * We allocate each other.
		 * After allocation we expect:
		 * Sales Invoice of 100 EUR to be fully allocated => zero open amount.
		 * Sales Credit Memo of 100 EUR (i.e. actually -100 EUR because we have to given 100 EUR back) to have -20 EUR open amount,
		 * because we still have to give 20 EUR back.
		 */
		@Test
		public void salesInvoice_and_salesCreditMemo_sameAmount()
		{
			final PayableDocument salesInvoice;
			final PayableDocument creditMemo;

			final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = InvoiceProcessingFeeCalculation.builder()
					.orgId(adOrgId)
					.evaluationDate(LocalDate.parse("2021-01-23").atStartOfDay(adOrgTimeZone))
					.customerId(bpartnerId)
					.invoiceId(InvoiceId.ofRepoId(1111))
					.serviceCompanyBPartnerId(BPartnerId.ofRepoId(2222))
					.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(3333))
					.serviceFeeProductId(ProductId.ofRepoId(4444))
					.feeAmountIncludingTax(Amount.of(666, CurrencyCode.EUR)) // does not matter
					.build();

			final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
					// Invoices
					ImmutableList.of(
							salesInvoice = invoice().type(CustomerInvoice).open("100").pay("90").discount("0").invoiceProcessingFee("10").invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation).date("2021-01-03").build(),
							creditMemo = invoice().type(CustomerCreditMemo).open("-100").pay("-110"/* -100 + 10*/).discount("0").invoiceProcessingFee("10").invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation).date("2021-01-04").build()
					)
					// Payments
					, ImmutableList.of());

			//
			// Define expected candidates
			final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
					allocation().type(AllocationLineCandidateType.InvoiceProcessingFee)
							.payableRef(salesInvoice.getReference())
							.invoiceProcessingFee("10")
							.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
							.overUnderAmt("90")
							.date("2021-01-23")
							.build(),
					allocation().type(AllocationLineCandidateType.InvoiceProcessingFee)
							.payableRef(creditMemo.getReference())
							.invoiceProcessingFee("10")
							.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
							.overUnderAmt("-110")
							.date("2021-01-23")
							.build(),
					allocation().type(InvoiceToCreditMemo)
							.payableRef(salesInvoice.getReference())
							.paymentRef(creditMemo.getReference())
							.allocatedAmt("90")
							.discountAmt("0")
							.paymentOverUnderAmt("-20")
							.date("2021-01-04")
							.build());

			final PaymentAllocationResult result = builder
					.dryRun() // would fail if is not dryRun because we cannot save InvoiceProcessingFee allocations
					.build();
			assertExpected(candidatesExpected, result.getCandidates());
		}
	}
}
