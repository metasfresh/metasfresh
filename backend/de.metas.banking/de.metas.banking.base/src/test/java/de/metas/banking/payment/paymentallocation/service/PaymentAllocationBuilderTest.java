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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

public class PaymentAllocationBuilderTest
{
	// services
	private IAllocationDAO allocationDAO;
	private IPaymentDAO paymentDAO;
	private IInvoiceBL invoiceBL;
	private IInvoiceDAO invoicesDAO;

	private final LocalDate date = LocalDate.parse("2020-04-29");
	private int nextInvoiceId = 1;
	private int nextPaymentId = 1;
	private final OrgId adOrgId = OrgId.ofRepoId(1000000); // just a dummy value
	private final BPartnerId bpartnerId = BPartnerId.ofRepoId(1); // dummy value
	private CurrencyId euroCurrencyId;
	private Map<InvoiceDocBaseType, I_C_DocType> invoiceDocTypes;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// services
		allocationDAO = Services.get(IAllocationDAO.class);
		paymentDAO = Services.get(IPaymentDAO.class);
		invoiceBL = Services.get(IInvoiceBL.class);
		invoicesDAO = Services.get(IInvoiceDAO.class);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		invoiceDocTypes = new HashMap<>();
	}

	private Money euro(final String amount)
	{
		final BigDecimal amountBD = !Check.isEmpty(amount) ? new BigDecimal(amount) : BigDecimal.ZERO;
		return Money.of(amountBD, euroCurrencyId);
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

		assertThat(candidatesActual).hasSize(candidatesExpected.size());
		for (int i = 0; i < candidatesExpected.size(); i++)
		{
			assertThat(candidatesActual.get(i))
					.as("candidate with index=" + i)
					.isEqualToComparingFieldByField(candidatesExpected.get(i));
		}
	}

	private PaymentAllocationBuilder newPaymentAllocationBuilder()
	{
		return PaymentAllocationBuilder.newBuilder()
				.dateTrx(date)
				.dateAcct(date);
	}

	private PaymentAllocationBuilder newPaymentAllocationBuilder(
			final Collection<PayableDocument> invoices,
			final Collection<PaymentDocument> payments)
	{
		return newPaymentAllocationBuilder()
				.payableDocuments(invoices)
				.paymentDocuments(payments);
	}

	/**
	 * NOTE: amounts shall be CreditMemo adjusted, but not AP adjusted
	 */
	@Builder(builderMethodName = "invoice", builderClassName = "$PayableDocumentBuilder")
	private PayableDocument newInvoice(
			final InvoiceDocBaseType type,
			final String open,
			final String pay,
			final String discount,
			final String writeOff,
			final String invoiceProcessingFee,
			final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation)
	{
		final Money openAmt = euro(open);

		final AllocationAmounts amountsToAllocate = AllocationAmounts.builder()
				.payAmt(euro(pay))
				.discountAmt(euro(discount))
				.writeOffAmt(euro(writeOff))
				.invoiceProcessingFee(euro(invoiceProcessingFee))
				.build();

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
			invoice.setDateInvoiced(TimeUtil.asTimestamp(date));
			invoice.setC_BPartner_ID(bpartnerId.getRepoId());
			invoice.setC_Currency_ID(invoiceGrandTotal.getCurrencyId().getRepoId());
			invoice.setGrandTotal(invoiceGrandTotal.toBigDecimal());
			invoice.setProcessed(true);
			invoice.setDocStatus(IDocument.STATUS_Completed);
			InterfaceWrapperHelper.saveRecord(invoice);
		}

		return PayableDocument.builder()
				.orgId(adOrgId)
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.documentNo(invoice.getDocumentNo())
				.soTrx(SOTrx.ofBoolean(invoice.isSOTrx()))
				.creditMemo(type.isCreditMemo())
				.openAmt(openAmt)
				.amountsToAllocate(amountsToAllocate)
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
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
			final String amtToAllocate)
	{
		//
		// Create a dummy record (needed for the BL which calculates how much was allocated)
		final int paymentId = nextPaymentId++;
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setC_Payment_ID(paymentId);
		payment.setDocumentNo("PaymentDocNo" + paymentId);
		payment.setC_BPartner_ID(bpartnerId.getRepoId());
		payment.setC_Currency_ID(euroCurrencyId.getRepoId());
		InterfaceWrapperHelper.save(payment);

		return PaymentDocument.builder()
				.orgId(adOrgId)
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.documentNo(payment.getDocumentNo())
				.paymentDirection(direction)
				.openAmt(euro(open)
				// .negateIf(direction.isOutboundPayment())
				)
				.amountToAllocate(euro(amtToAllocate)
				// .negateIf(direction.isOutboundPayment())
				)
				.dateTrx(LocalDate.of(2020, Month.JANUARY, 1))
				.build();
	}

	@Builder(builderMethodName = "allocation", builderClassName = "$AllocationBuilder")
	private AllocationLineCandidate createAllocationCandidate(
			@NonNull final AllocationLineCandidateType type,
			@Nullable final TableRecordReference payableRef,
			@Nullable final TableRecordReference paymentRef,
			@Nullable final String allocatedAmt,
			@Nullable final String discountAmt,
			@Nullable final String writeOffAmt,
			@Nullable final String invoiceProcessingFee,
			@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation,
			@Nullable final String overUnderAmt,
			@Nullable final String paymentOverUnderAmt)
	{
		return AllocationLineCandidate.builder()
				.type(type)
				.orgId(adOrgId)
				.bpartnerId(bpartnerId)
				//
				.payableDocumentRef(payableRef)
				.paymentDocumentRef(paymentRef)
				//
				.dateTrx(date)
				.dateAcct(date)
				//
				.amounts(AllocationAmounts.builder()
						.payAmt(euro(allocatedAmt))
						.discountAmt(euro(discountAmt))
						.writeOffAmt(euro(writeOffAmt))
						.invoiceProcessingFee(euro(invoiceProcessingFee))
						.build())
				.payableOverUnderAmt(euro(overUnderAmt))
				.paymentOverUnderAmt(euro(paymentOverUnderAmt))
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

	@Test
	public void test_NoDocuments()
	{
		assertThatThrownBy(() -> newPaymentAllocationBuilder().build())
				.isInstanceOf(NoDocumentsPaymentAllocationException.class);
	}

	@Test
	public void test_OneVendorInvoice_NoPayments_JustDiscountAndWriteOff()
	{
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice1 = invoice().type(VendorInvoice).open("-8000").pay("0").discount("-100").writeOff("-200").build())
				// Payments
				, ImmutableList.<PaymentDocument> of());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceDiscountOrWriteOff)
						.payableRef(invoice1.getReference())
						.discountAmt("-100").writeOffAmt("-200").overUnderAmt("-7700")
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
						invoice1 = invoice().type(VendorInvoice).open("-8000").pay("-5000").discount("-100").writeOff("-200").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("-5000").discountAmt("-100").writeOffAmt("-200").overUnderAmt("-2700")
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
				Arrays.<PayableDocument> asList()
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").build(),
						payment2 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").build()));

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

	@Test
	public void test_NoInvoices_ThreePayments()
	{
		final PaymentDocument payment1, payment2, payment3;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.<PayableDocument> asList()
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-5000").amtToAllocate("-5000").build(), //
						payment2 = payment().direction(INBOUND).open("3000").amtToAllocate("3000").build(), //
						payment3 = payment().direction(INBOUND).open("2000").amtToAllocate("2000").build() //
				));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InboundPaymentToOutboundPayment)
						.payableRef(payment1.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("-3000").overUnderAmt("-2000")
						.build(),
				allocation().type(InboundPaymentToOutboundPayment)
						.payableRef(payment1.getReference())
						.paymentRef(payment3.getReference())
						.allocatedAmt("-2000")
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
						invoice1 = invoice().type(CustomerInvoice).open("5000").pay("1000").build(),
						invoice2 = invoice().type(CustomerCreditMemo).open("-1000").pay("-1000").build())
				// Payments
				, ImmutableList.<PaymentDocument> of());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice2.getReference())
						.allocatedAmt("1000")
						.overUnderAmt("4000")
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
						invoice = invoice().type(VendorInvoice).open("-5000").pay("-1000").build(),
						creditMemo = invoice().type(VendorCreditMemo).open("1000").pay("1000").build()),
				// Payments
				ImmutableList.<PaymentDocument> of());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice.getReference())
						.paymentRef(creditMemo.getReference())
						.allocatedAmt("-1000")
						.overUnderAmt("-4000")
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
							invoice1 = invoice().type(CustomerInvoice).open("5000").pay("1000").build(),
							invoice2 = invoice().type(VendorInvoice).open("-1000").pay("-1000").build())
					// Payments
					, ImmutableList.<PaymentDocument> of());
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
		final PaymentDocument payment1 = payment().direction(OUTBOUND).open("5000").amtToAllocate("5000").build();
		final PaymentDocument payment2 = payment().direction(OUTBOUND).open("5000").amtToAllocate("5000").build();

		final PayableDocument invoice1 = invoice().type(VendorInvoice).open("8000").pay("6000").discount("1599").writeOff("1").build();
		final PayableDocument invoice2 = invoice().type(VendorInvoice).open("7100").pay("3000").discount("50").writeOff("50").build();

		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(invoice1, invoice2)
				// Payments
				, ImmutableList.of(payment1, payment2));

		assertThatThrownBy(() -> builder.build())
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
						invoice1 = invoice().type(CustomerInvoice).open("8000").pay("6000").discount("1599").writeOff("1").build(),
						invoice2 = invoice().type(CustomerInvoice).open("7100").pay("3000").discount("50").writeOff("50").build(),
						invoice3 = invoice().type(CustomerInvoice).open("1600").pay("1500").discount("100").build(),
						invoice4 = invoice().type(CustomerCreditMemo).open("-500").pay("-500").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").build(),
						payment2 = payment().direction(INBOUND).open("5000").amtToAllocate("5000").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice4.getReference())
						.allocatedAmt("500").discountAmt("1599").writeOffAmt("1").overUnderAmt("5900")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("5000").discountAmt("0").writeOffAmt("0").overUnderAmt("900")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("500").discountAmt("0").writeOffAmt("0").overUnderAmt("400").paymentOverUnderAmt("4500")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice2.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("3000").discountAmt("50").writeOffAmt("50").overUnderAmt("4000").paymentOverUnderAmt("1500")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice3.getReference())
						.paymentRef(payment2.getReference())
						.allocatedAmt("1500").discountAmt("100").writeOffAmt("0").overUnderAmt("0")
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
						invoice1 = invoice().type(VendorCreditMemo).open("100").pay("30").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("50").amtToAllocate("30").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("30")
						.overUnderAmt("70")
						.paymentOverUnderAmt("20")
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
						invoice1 = invoice().type(VendorInvoice).open("-165").pay("-100").discount("-10").writeOff("-5").build(),
						invoice2 = invoice().type(VendorCreditMemo).open("80").pay("80").build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(OUTBOUND).open("-20").amtToAllocate("-20").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToCreditMemo)
						.payableRef(invoice1.getReference())
						.paymentRef(invoice2.getReference())
						.allocatedAmt("-80").discountAmt("-10").writeOffAmt("-5").overUnderAmt("-70")
						.build(),
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("-20").overUnderAmt("-50")
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
	public void test_VendorInvoice_CustomerReceipt_expect_Exception()
	{
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						invoice().type(VendorInvoice).open("-100").pay("-100").build())
				// Payments
				, ImmutableList.of(
						payment().direction(INBOUND).open("100").amtToAllocate("100").build()));

		assertThatThrownBy(() -> builder.build())
				.isInstanceOf(PayableDocumentNotAllocatedException.class);
	}

	@Nested
	public class payableRemainingOpenAmtPolicy
	{
		private PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy;
		private boolean allowPartialAllocations = false;

		private PayableDocument invoice1;
		private PaymentDocument payment1;
		private PaymentAllocationResult result;

		private void setup()
		{
			result = newPaymentAllocationBuilder()
					.payableDocuments(ImmutableList.of(
							invoice1 = invoice().type(CustomerInvoice).open("100").pay("100").build()))
					.paymentDocuments(ImmutableList.of(
							payment1 = payment().direction(INBOUND).open("10").amtToAllocate("10").build()))
					.payableRemainingOpenAmtPolicy(payableRemainingOpenAmtPolicy)
					.allowPartialAllocations(allowPartialAllocations)
					.build();
		}

		@Test
		public void doNothing_disallowPartialAllocations()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DO_NOTHING;
			allowPartialAllocations = false;
			assertThatThrownBy(() -> setup())
					.isInstanceOf(PayableDocumentNotAllocatedException.class);
		}

		@Test
		public void doNothing_allowPartialAllocations()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DO_NOTHING;
			allowPartialAllocations = true;
			setup();

			assertThat(result.getCandidates())
					.hasSize(1)
					.containsExactly(
							allocation().type(InvoiceToPayment)
									.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
									.allocatedAmt("10").overUnderAmt("90")
									.build());
		}

		@Test
		public void discount()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DISCOUNT;
			setup();

			assertThat(result.getCandidates())
					.hasSize(2)
					.containsExactly(
							allocation().type(InvoiceToPayment)
									.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
									.allocatedAmt("10").overUnderAmt("90")
									.build(),
							allocation().type(InvoiceDiscountOrWriteOff)
									.payableRef(invoice1.getReference())
									.discountAmt("90")
									.build());
		}

		@Test
		public void writeOff()
		{
			payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.WRITE_OFF;
			setup();

			assertThat(result.getCandidates())
					.hasSize(2)
					.containsExactly(
							allocation().type(InvoiceToPayment)
									.payableRef(invoice1.getReference()).paymentRef(payment1.getReference())
									.allocatedAmt("10").overUnderAmt("90")
									.build(),
							allocation().type(InvoiceDiscountOrWriteOff)
									.payableRef(invoice1.getReference())
									.writeOffAmt("90")
									.build());
		}

	}

	@Test
	public void test_automaticallyWriteOff()
	{
		final PayableDocument invoice1;
		final PaymentDocument payment1;

		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder()
				.payableDocuments(ImmutableList.of(
						invoice1 = invoice().type(CustomerInvoice).open("100").pay("100").build()))
				.paymentDocuments(ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("10").amtToAllocate("10").build()))
				.payableRemainingOpenAmtPolicy(PayableRemainingOpenAmtPolicy.WRITE_OFF);

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("10")
						.overUnderAmt("90")
						.build(),

				allocation().type(InvoiceDiscountOrWriteOff)
						.payableRef(invoice1.getReference())
						.writeOffAmt("90")
						.build());

		assertExpected(candidatesExpected, builder);
	}

	@Test
	public void test_invoiceProcessingFee()
	{
		PayableDocument invoice1;
		PaymentDocument payment1;

		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = InvoiceProcessingFeeCalculation.builder()
				.orgId(adOrgId)
				.evaluationDate(date.atStartOfDay(ZoneId.of("UTC-8")))
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
						invoice1 = invoice().type(CustomerInvoice).open("100").pay("88").discount("10").invoiceProcessingFee("2").invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation).build())
				// Payments
				, ImmutableList.of(
						payment1 = payment().direction(INBOUND).open("88").amtToAllocate("88").build()));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				allocation().type(AllocationLineCandidateType.InvoiceProcessingFee)
						.payableRef(invoice1.getReference())
						.invoiceProcessingFee("2")
						.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
						.overUnderAmt("98")
						.build(),

				allocation().type(InvoiceToPayment)
						.payableRef(invoice1.getReference())
						.paymentRef(payment1.getReference())
						.allocatedAmt("88")
						.discountAmt("10")
						.build());

		final PaymentAllocationResult result = builder
				.dryRun() // would fail if is not dryRun because we cannot save InvoiceProcessingFee allocations
				.build();
		assertExpected(candidatesExpected, result.getCandidates());
	}
}
