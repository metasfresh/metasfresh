package de.metas.banking.payment.paymentallocation.service;

import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InboundPaymentToOutboundPayment;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceDiscountOrWriteOff;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceToCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.InvoiceToPayment;
import static de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice;
import static de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilderTest.InvoiceType.CustomerCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilderTest.InvoiceType.CustomerInvoice;
import static de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilderTest.InvoiceType.VendorCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilderTest.InvoiceType.VendorInvoice;

/*
 * #%L
 * de.metas.banking.swingui
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.impl.PaymentAllocationBL;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;

public class PaymentAllocationBuilderTest
{
	// services
	private ISysConfigBL sysConfigs;
	private IAllocationDAO allocationDAO;
	private IPaymentDAO paymentDAO;
	private IInvoiceBL invoiceBL;
	private IInvoiceDAO invoicesDAO;

	private final LocalDate date = SystemTime.asLocalDate();
	private int nextInvoiceId = 1;
	private int nextPaymentId = 1;
	private final OrgId adOrgId = OrgId.ofRepoId(1000000); // just a dummy value
	private final BPartnerId bpartnerId = BPartnerId.ofRepoId(1); // dummy value
	private Currency currency;
	private Map<InvoiceType, I_C_DocType> invoiceDocTypes;

	private static final boolean IsReceipt_Yes = true;
	private static final boolean IsReceipt_No = false;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// services
		sysConfigs = Services.get(ISysConfigBL.class);
		allocationDAO = Services.get(IAllocationDAO.class);
		paymentDAO = Services.get(IPaymentDAO.class);
		invoiceBL = Services.get(IInvoiceBL.class);
		invoicesDAO = Services.get(IInvoiceDAO.class);

		currency = PlainCurrencyDAO.createCurrency(CurrencyCode.CHF);
		invoiceDocTypes = new HashMap<>();

		setAllowSalesPurchaseInvoiceCompensation(true);
	}

	private final void setAllowSalesPurchaseInvoiceCompensation(final boolean allow)
	{
		sysConfigs.setValue(PaymentAllocationBL.SYSCONFIG_AllowAllocationOfPurchaseInvoiceAgainstSaleInvoice, allow, ClientId.SYSTEM, OrgId.ANY);
	}

	@Test
	public void test_NoDocuments()
	{
		assertThatThrownBy(() -> newPaymentAllocationBuilder().build())
				.isInstanceOf(NoDocumentsPaymentAllocationException.class);
	}

	@Test
	public void test_OneInvoice_NoPayments_JustDiscountAndWriteOff()
	{
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "8000", "0", "100", "200"))
				// Payments
				, Arrays.<IPaymentDocument> asList(
				// PaymentId / IsReceipt / OpenAmt / AppliedAmt
				));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceDiscountOrWriteOff, invoice1.getReference(), null, "0", "-100", "-200", "-7700", "0"));

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
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "8000", "5000", "100", "200"))
				// Payments
				, ImmutableList.of(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment1.getReference(), "-5000", "-100", "-200", "-2700", "0"));

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
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"),
						payment2 = newPaymentRow(IsReceipt_Yes, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InboundPaymentToOutboundPayment, payment1.getReference(), payment2.getReference(), "-5000", "0", "0", "0", "0"));

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
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"), payment2 = newPaymentRow(IsReceipt_Yes, "3000", "3000"), payment3 = newPaymentRow(IsReceipt_Yes, "2000", "2000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InboundPaymentToOutboundPayment, payment1.getReference(), payment2.getReference(), "-3000", "0", "0", "-2000", "0"), //
				createAllocationCandidate(InboundPaymentToOutboundPayment, payment1.getReference(), payment3.getReference(), "-2000", "0", "0", "0", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
		assertPaymentAllocatedAmt(payment2.getPaymentId(), +3000);
		assertPaymentAllocatedAmt(payment3.getPaymentId(), +2000);
	}

	@Test
	public void test_InvoiceAndCreditMemo_NoPayments()
	{
		final PayableDocument invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoice(CustomerCreditMemo, "1000", "1000", "0", "0") // CreditMemo
				)
				// Payments
				, Arrays.<IPaymentDocument> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToCreditMemo, invoice1.getReference(), invoice2.getReference(), "1000", "0", "0", "4000", "0"));

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
	public void test_SalesInvoiceAndPurchaseInvoice_NoPayments()
	{
		final PayableDocument invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoice(VendorInvoice, "1000", "1000", "0", "0") //
				)
				// Payments
				, Arrays.<IPaymentDocument> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(SalesInvoiceToPurchaseInvoice, invoice1.getReference(), invoice2.getReference(), "1000", "0", "0", "4000", "0"));

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
	public void test_Vendor_MultiInvoice_MultiPayment()
	{
		// PaymentId / IsReceipt / OpenAmt / AppliedAmt
		final PaymentDocument payment1 = newPaymentRow(IsReceipt_No, "5000", "5000");
		final PaymentDocument payment2 = newPaymentRow(IsReceipt_No, "5000", "5000");

		// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
		final PayableDocument invoice1 = newInvoice(VendorInvoice, "8000", "6000", "1599", "1");
		final PayableDocument invoice2 = newInvoice(VendorInvoice, "7100", "3000", "50", "50");

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
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "8000", "6000", "1599", "1"),
						invoice2 = newInvoice(CustomerInvoice, "7100", "3000", "50", "50"),
						invoice3 = newInvoice(CustomerInvoice, "1600", "1500", "100", "0"),
						invoice4 = newInvoice(CustomerCreditMemo, "500", "500", "0", "0") // i.e. CreditMemo
				)
				// Payments
				, ImmutableList.of(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_Yes, "5000", "5000"),
						payment2 = newPaymentRow(IsReceipt_Yes, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToCreditMemo, invoice1.getReference(), invoice4.getReference(), "500", "1599", "1", "5900", "0")
				//
				, createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment1.getReference(), "5000", "0", "0", "900", "0")
				//
				, createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment2.getReference(), "500", "0", "0", "400", "4500")
				//
				, createAllocationCandidate(InvoiceToPayment, invoice2.getReference(), payment2.getReference(), "3000", "50", "50", "4000", "1500")
				//
				, createAllocationCandidate(InvoiceToPayment, invoice3.getReference(), payment2.getReference(), "1500", "100", "0", "0", "0"));

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
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorCreditMemo, "100", "30", "0", "0"))
				// Payments
				, ImmutableList.of(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_Yes, "50", "30")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment1.getReference(), "30", "0", "0", "70", "20"));

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
	public void test_RegularInvoice_CreditMemoInvoice_Payment()
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PayableDocument invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "165", "100", "10", "5"),
						invoice2 = newInvoice(VendorCreditMemo, "80", "80", "0", "0"))
				// Payments
				, ImmutableList.of(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "20", "20")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToCreditMemo, invoice1.getReference(), invoice2.getReference(), "-80", "-10", "-5", "-70", "0"),
				createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment1.getReference(), "-20", "0", "0", "-50", "0"));

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
	public void test_VendorInvoice_CustomerReceipt()
	{
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				ImmutableList.of(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						newInvoice(VendorInvoice, "100", "100", "0", "0"))
				// Payments
				, ImmutableList.of(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						newPaymentRow(IsReceipt_Yes, "100", "100")));

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
							// InvoiceType / OpenAmt / PayAmt / Discount / WriteOff
							invoice1 = newInvoice(CustomerInvoice, "100", "100", "0", "0")))
					.paymentDocuments(ImmutableList.of(
							// IsReceipt / OpenAmt / AmountToAllocate
							payment1 = newPaymentRow(IsReceipt_Yes, "10", "10")))
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
						// InvoiceType / OpenAmt / PayAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "100", "100", "0", "0")))
				.paymentDocuments(ImmutableList.of(
						// IsReceipt / OpenAmt / AmountToAllocate
						payment1 = newPaymentRow(IsReceipt_Yes, "10", "10")))
				.payableRemainingOpenAmtPolicy(PayableRemainingOpenAmtPolicy.WRITE_OFF);

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = ImmutableList.of(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				createAllocationCandidate(InvoiceToPayment, invoice1.getReference(), payment1.getReference(), "10", "0", "0", "90", "0"),
				createAllocationCandidate(InvoiceDiscountOrWriteOff, invoice1.getReference(), null, "0", "0", "90", "0", "0"));

		assertExpected(candidatesExpected, builder);
	}

	private final void assertExpected(final List<AllocationLineCandidate> candidatesExpected, final PaymentAllocationBuilder builder)
	{
		final PaymentAllocationResult result = builder.build();
		assertExpected(candidatesExpected, result.getCandidates());

		assertAllocationHdrCount(candidatesExpected.size());
	}

	private final void assertExpected(
			final List<AllocationLineCandidate> candidatesExpected,
			final List<AllocationLineCandidate> candidatesActual)
	{
		dumpCandidates("Actual candidates", candidatesActual);
		dumpCandidates("Expected candidates", candidatesExpected);
		assertThat(candidatesActual).isEqualTo(candidatesExpected);
	}

	private PaymentAllocationBuilder newPaymentAllocationBuilder()
	{
		return PaymentAllocationBuilder.newBuilder()
				.orgId(adOrgId)
				.currencyId(currency.getId())
				.dateTrx(date)
				.dateAcct(date)  // task 09643. Leaving this date also as current date. will be changed later if needed
		;
	}

	private PaymentAllocationBuilder newPaymentAllocationBuilder(
			final Collection<PayableDocument> invoices,
			final Collection<IPaymentDocument> payments)
	{
		return newPaymentAllocationBuilder()
				.payableDocuments(invoices)
				.paymentDocuments(payments);
	}

	/**
	 * NOTE: amounts shall be CreditMemo adjusted, but not AP adjusted
	 */
	private PayableDocument newInvoice(
			final InvoiceType invoiceType,
			final String openAmtStr,
			final String payAmtStr,
			final String discountAmtStr,
			final String writeOffAmtStr)
	{
		final CurrencyId currencyId = currency.getId();

		final Money openAmt_CMAdjusted_APAdjusted = Money.of(openAmtStr, currencyId);
		final Money openAmt = openAmt_CMAdjusted_APAdjusted
				.negateIf(invoiceType.isCreditMemo())
				.negateIf(!invoiceType.isSoTrx());

		final AllocationAmounts amountsToAllocate_CMAdjusted_APAdjusted = AllocationAmounts.builder()
				.payAmt(Money.of(payAmtStr, currencyId))
				.discountAmt(Money.of(discountAmtStr, currencyId))
				.writeOffAmt(Money.of(writeOffAmtStr, currencyId))
				.build();
		final AllocationAmounts amountsToAllocate = amountsToAllocate_CMAdjusted_APAdjusted
				.negateIf(invoiceType.isCreditMemo())
				.negateIf(!invoiceType.isSoTrx());

		// invoiceType.isCreditMemo() ? amountsToAllocate_CMAdjusted_APAdjusted.negate() : amountsToAllocate_CMAdjusted_APAdjusted;

		//
		// Create the invoice record (needed for the BL which calculates how much was allocated)
		final int invoiceId = nextInvoiceId++;
		final I_C_DocType docType = getInvoiceDocType(invoiceType);
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(invoiceId);
		invoice.setDocumentNo("Doc" + invoiceId);
		invoice.setC_DocType(docType);
		invoice.setIsSOTrx(docType.isSOTrx());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(date));
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_Currency_ID(openAmt_CMAdjusted_APAdjusted.getCurrencyId().getRepoId());
		invoice.setGrandTotal(openAmt_CMAdjusted_APAdjusted.toBigDecimal());
		invoice.setProcessed(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		InterfaceWrapperHelper.saveRecord(invoice);

		return PayableDocument.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.documentNo(invoice.getDocumentNo())
				.isSOTrx(invoice.isSOTrx())
				.creditMemo(invoiceType.isCreditMemo())
				.openAmt(openAmt)
				.amountsToAllocate(amountsToAllocate)
				.build();
	}

	private I_C_DocType getInvoiceDocType(final InvoiceType invoiceType)
	{
		return invoiceDocTypes.computeIfAbsent(invoiceType, this::createInvoiceDocType);
	}

	private final I_C_DocType createInvoiceDocType(final InvoiceType invoiceType)
	{
		final String docBaseType;
		final boolean isSOTrx;
		if (invoiceType.isSoTrx())
		{
			docBaseType = invoiceType.isCreditMemo() ? X_C_DocType.DOCBASETYPE_ARCreditMemo : X_C_DocType.DOCBASETYPE_ARInvoice;
			isSOTrx = true;
		}
		else
		{
			docBaseType = invoiceType.isCreditMemo() ? X_C_DocType.DOCBASETYPE_APCreditMemo : X_C_DocType.DOCBASETYPE_APInvoice;
			isSOTrx = false;
		}

		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		docType.setName(invoiceType.toString());
		docType.setIsSOTrx(isSOTrx);
		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	private PaymentDocument newPaymentRow(
			final boolean isReceipt,
			final String openAmtStr,
			final String amountToAllocateStr)
	{
		final CurrencyId currencyId = currency.getId();

		//
		// Create a dummy record (needed for the BL which calculates how much was allocated)
		final int paymentId = nextPaymentId++;
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setC_Payment_ID(paymentId);
		payment.setDocumentNo("Doc" + paymentId);
		payment.setC_BPartner_ID(bpartnerId.getRepoId());
		payment.setC_Currency_ID(currencyId.getRepoId());
		InterfaceWrapperHelper.save(payment);

		return PaymentDocument.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.documentNo(payment.getDocumentNo())
				.isSOTrx(isReceipt)
				.openAmt(Money.of(openAmtStr, currencyId).negateIf(!isReceipt))
				.amountToAllocate(Money.of(amountToAllocateStr, currencyId).negateIf(!isReceipt))
				.build();
	}

	@Builder(builderMethodName = "allocation", builderClassName = "AllocationBuilder")
	private final AllocationLineCandidate createAllocationCandidate(
			@NonNull final AllocationLineCandidateType type,
			@Nullable final TableRecordReference payableRef,
			@Nullable final TableRecordReference paymentRef,
			@Nullable final String allocatedAmt,
			@Nullable final String discountAmt,
			@Nullable final String writeOffAmt,
			@Nullable final String overUnderAmt,
			@Nullable final String paymentOverUnderAmt)
	{
		return AllocationLineCandidate.builder()
				.type(type)
				.bpartnerId(bpartnerId)
				//
				.payableDocumentRef(payableRef)
				.paymentDocumentRef(paymentRef)
				//
				.amount(toBigDecimalOrZero(allocatedAmt))
				.discountAmt(toBigDecimalOrZero(discountAmt))
				.writeOffAmt(toBigDecimalOrZero(writeOffAmt))
				.payableOverUnderAmt(toBigDecimalOrZero(overUnderAmt))
				.paymentOverUnderAmt(toBigDecimalOrZero(paymentOverUnderAmt))
				//
				.build();
	}

	private static BigDecimal toBigDecimalOrZero(final String str)
	{
		return !Check.isEmpty(str) ? new BigDecimal(str) : BigDecimal.ZERO;
	}

	private final void dumpCandidates(final String title, final Iterable<AllocationLineCandidate> candidates)
	{
		System.out.println(" " + title + ": -----------------------------");
		int index = 0;
		for (final AllocationLineCandidate cand : candidates)
		{
			index++;
			System.out.println(index + ". " + cand);
		}
	}

	private final void assertInvoiceAllocatedAmt(final InvoiceId invoiceId, final BigDecimal expectedAllocatedAmt)
	{
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);

		final BigDecimal actualAllocatedAmt = allocationDAO.retrieveAllocatedAmt(invoice);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for invoice " + invoiceId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private final void assertInvoiceAllocatedAmt(final InvoiceId invoiceId, final int expectedAllocatedAmtInt)
	{
		assertInvoiceAllocatedAmt(invoiceId, BigDecimal.valueOf(expectedAllocatedAmtInt));
	}

	private final void assertInvoiceAllocated(final InvoiceId invoiceId, final boolean expectedAllocated)
	{
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);
		final boolean ignoreProcessed = false;
		invoiceBL.testAllocation(invoice, ignoreProcessed);

		assertThat(invoice.isPaid())
				.as("Invoice allocated: " + invoiceId)
				.isEqualTo(expectedAllocated);
	}

	private final void assertPaymentAllocatedAmt(final PaymentId paymentId, final BigDecimal expectedAllocatedAmt)
	{
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		final BigDecimal actualAllocatedAmt = paymentDAO.getAllocatedAmt(payment);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for payment " + paymentId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private final void assertPaymentAllocatedAmt(final PaymentId paymentId, final int expectedAllocatedAmtInt)
	{
		assertPaymentAllocatedAmt(paymentId, BigDecimal.valueOf(expectedAllocatedAmtInt));
	}

	private final void assertAllocationHdrCount(final int expectedCount)
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

	enum InvoiceType
	{
		VendorInvoice(SOTrx.PURCHASE, false),//
		VendorCreditMemo(SOTrx.PURCHASE, true),//
		CustomerInvoice(SOTrx.SALES, false),//
		CustomerCreditMemo(SOTrx.SALES, true) //
		;

		private final boolean soTrx;
		private final boolean creditMemo;

		InvoiceType(final SOTrx soTrx, final boolean creditMemo)
		{
			this.soTrx = soTrx.toBoolean();
			this.creditMemo = creditMemo;
		}

		public boolean isSoTrx()
		{
			return soTrx;
		}

		public boolean isCreditMemo()
		{
			return creditMemo;
		}
	}

}
