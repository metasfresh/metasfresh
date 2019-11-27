package de.metas.banking.payment.paymentallocation.service;

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

import static de.metas.banking.payment.paymentallocation.service.InvoiceType.CustomerCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.InvoiceType.CustomerInvoice;
import static de.metas.banking.payment.paymentallocation.service.InvoiceType.VendorCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.InvoiceType.VendorInvoice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
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
import org.junit.jupiter.api.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.payment.paymentallocation.impl.PaymentAllocationBL;
import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceRow;
import de.metas.banking.payment.paymentallocation.model.PaymentRow;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class PaymentAllocationBuilderTest
{
	// services
	private IAllocationDAO allocationDAO;
	private IPaymentDAO paymentDAO;
	private IInvoiceBL invoiceBL;
	private IInvoiceDAO invoicesDAO;
	private CurrencyRepository currenciesRepo;

	private final LocalDate date = SystemTime.asLocalDate();
	private int nextInvoiceId = 1;
	private int nextPaymentId = 1;
	private final OrgId adOrgId = OrgId.ofRepoId(1000000); // just a dummy value
	final BPartnerId bpartnerId = BPartnerId.ofRepoId(1); // dummy value

	private Currency currency;

	private static final boolean IsReceipt_Yes = true;
	private static final boolean IsReceipt_No = false;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// services
		allocationDAO = Services.get(IAllocationDAO.class);
		paymentDAO = Services.get(IPaymentDAO.class);
		invoiceBL = Services.get(IInvoiceBL.class);
		invoicesDAO = Services.get(IInvoiceDAO.class);
		currenciesRepo = new CurrencyRepository();

		currency = PlainCurrencyDAO.createCurrency(CurrencyCode.CHF);

		setAllowSalesPurchaseInvoiceCompensation(true);
	}

	private final void setAllowSalesPurchaseInvoiceCompensation(final boolean allow)
	{
		Services.get(ISysConfigBL.class).setValue(PaymentAllocationBL.SYSCONFIG_AllowAllocationOfPurchaseInvoiceAgainstSaleInvoice, allow, 0);
	}

	@Test
	public void test_NoDocuments()
	{
		assertThatThrownBy(() -> newPaymentAllocationBuilder().build())
				.isInstanceOf(NoDocumentsPaymentAllocationException.class);
	}

	@Test
	public void test_OneInvoice_NoPayments_JustDiscountAndWriteOff() throws Exception
	{
		final IInvoiceRow invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(VendorInvoice, "8000", "0", "100", "200"))
				// Payments
				, Arrays.<IPaymentRow> asList(
				// PaymentId / IsReceipt / OpenAmt / AppliedAmt
				));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, null, "0", "-100", "-200", "-7700", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), -(100 + 200));
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
	}

	@Test
	public void test_OneVendorInvoice_OneVendorPayment() throws Exception
	{
		final IPaymentRow payment1;
		final IInvoiceRow invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(VendorInvoice, "8000", "5000", "100", "200"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, payment1, "-5000", "-100", "-200", "-2700", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), -(5000 + 100 + 200));
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), -5000);
	}

	@Test
	public void test_NoInvoices_TwoPayments()
	{
		final IPaymentRow payment1, payment2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.<IInvoiceRow> asList()
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"),
						payment2 = newPaymentRow(IsReceipt_Yes, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(payment1, payment2, "-5000", "0", "0", "0", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), -5000);
		assertPaymentAllocatedAmt(payment2.getC_Payment_ID(), +5000);
	}

	@Test
	public void test_NoInvoices_ThreePayments()
	{
		final IPaymentRow payment1, payment2, payment3;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.<IInvoiceRow> asList()
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"), payment2 = newPaymentRow(IsReceipt_Yes, "3000", "3000"), payment3 = newPaymentRow(IsReceipt_Yes, "2000", "2000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(payment1, payment2, "-3000", "0", "0", "-2000", "0"), newAllocationCandidate(payment1, payment3, "-2000", "0", "0", "0", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), -5000);
		assertPaymentAllocatedAmt(payment2.getC_Payment_ID(), +3000);
		assertPaymentAllocatedAmt(payment3.getC_Payment_ID(), +2000);
	}

	@Test
	public void test_InvoiceAndCreditMemo_NoPayments() throws Exception
	{
		final IInvoiceRow invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoiceRow(CustomerCreditMemo, "-1000", "-1000", "0", "0") // CreditMemo
				)
				// Payments
				, Arrays.<IPaymentRow> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, invoice2, "1000", "0", "0", "4000", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), +1000);
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getC_Invoice_ID(), -1000);
		assertInvoiceAllocated(invoice2.getC_Invoice_ID(), true);
	}

	@Test
	public void test_SalesInvoiceAndPurchaseInvoice_NoPayments() throws Exception
	{
		final IInvoiceRow invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoiceRow(VendorInvoice, "1000", "1000", "0", "0") //
				)
				// Payments
				, Arrays.<IPaymentRow> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, invoice2, "1000", "0", "0", "4000", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), +1000);
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getC_Invoice_ID(), -1000);
		assertInvoiceAllocated(invoice2.getC_Invoice_ID(), true);
	}

	@Test
	public void test_Vendor_MultiInvoice_MultiPayment() throws Exception
	{
		// PaymentId / IsReceipt / OpenAmt / AppliedAmt
		final IPaymentRow payment1 = newPaymentRow(IsReceipt_No, "5000", "5000");
		final IPaymentRow payment2 = newPaymentRow(IsReceipt_No, "5000", "5000");

		// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
		final IInvoiceRow invoice1 = newInvoiceRow(VendorInvoice, "8000", "6000", "1599", "1");
		final IInvoiceRow invoice2 = newInvoiceRow(VendorInvoice, "7100", "3000", "50", "50");

		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(invoice1, invoice2)
				// Payments
				, Arrays.asList(payment1, payment2));

		assertThatThrownBy(() -> builder.build())
				.isInstanceOf(MultipleVendorDocumentsException.class);
	}

	@Test
	public void test_Sales_MultiInvoice_MultiPayment() throws Exception
	{
		final IPaymentRow payment1, payment2;
		final IInvoiceRow invoice1, invoice2, invoice3, invoice4;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(CustomerInvoice, "8000", "6000", "1599", "1"),
						invoice2 = newInvoiceRow(CustomerInvoice, "7100", "3000", "50", "50"),
						invoice3 = newInvoiceRow(CustomerInvoice, "1600", "1500", "100", "0"),
						invoice4 = newInvoiceRow(CustomerCreditMemo, "-500", "-500", "0", "0") // i.e. CreditMemo
				)
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_Yes, "5000", "5000"),
						payment2 = newPaymentRow(IsReceipt_Yes, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, invoice4, "500", "1599", "1", "5900", "0")
				//
				, newAllocationCandidate(invoice1, payment1, "5000", "0", "0", "900", "0"), newAllocationCandidate(invoice1, payment2, "500", "0", "0", "400", "4500")
				//
				, newAllocationCandidate(invoice2, payment2, "3000", "50", "50", "4000", "1500")
				//
				, newAllocationCandidate(invoice3, payment2, "1500", "100", "0", "0", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), 500 + 1599 + 1 + 5000 + 500);
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getC_Invoice_ID(), 3000 + 50 + 50);
		assertInvoiceAllocated(invoice2.getC_Invoice_ID(), false);
		//
		assertInvoiceAllocatedAmt(invoice3.getC_Invoice_ID(), 1500 + 100);
		assertInvoiceAllocated(invoice3.getC_Invoice_ID(), true);
		//
		assertInvoiceAllocatedAmt(invoice4.getC_Invoice_ID(), -500); // credit memo
		assertInvoiceAllocated(invoice4.getC_Invoice_ID(), true);
		//
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), 5000);
		assertPaymentAllocatedAmt(payment2.getC_Payment_ID(), 5000);
	}

	@Test
	public void test_CreditMemoInvoice_Payment() throws Exception
	{
		final IPaymentRow payment1;
		final IInvoiceRow invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(VendorCreditMemo, "-100", "-30", "0", "0"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_Yes, "50", "30")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, payment1, "30", "0", "0", "70", "20"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), 30); // credit memo
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		//
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), 30);
	}

	@Test
	public void test_RegularInvoice_CreditMemoInvoice_Payment() throws Exception
	{
		final IPaymentRow payment1;
		final IInvoiceRow invoice1;
		final IInvoiceRow invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoiceRow(VendorInvoice, "165", "100", "10", "5"), invoice2 = newInvoiceRow(VendorCreditMemo, "-80", "-80", "0", "0"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "20", "20")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1, invoice2, "-80", "-10", "-5", "-70", "0"), newAllocationCandidate(invoice1, payment1, "-20", "0", "0", "-50", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		//
		assertInvoiceAllocatedAmt(invoice1.getC_Invoice_ID(), -(100 + 10 + 5));
		assertInvoiceAllocated(invoice1.getC_Invoice_ID(), false);
		//
		assertInvoiceAllocatedAmt(invoice2.getC_Invoice_ID(), +80); // CM
		assertInvoiceAllocated(invoice2.getC_Invoice_ID(), true);
		//
		assertPaymentAllocatedAmt(payment1.getC_Payment_ID(), -20);
	}

	@Test
	public void test_VendorInvoice_CustomerReceipt() throws Exception
	{
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						newInvoiceRow(VendorInvoice, "100", "100", "0", "0"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						newPaymentRow(IsReceipt_Yes, "100", "100")));

		assertThatThrownBy(() -> builder.build())
				.isInstanceOf(PayableDocumentNotAllocatedException.class);
	}

	private final void assertExpected(final List<AllocationLineCandidate> candidatesExpected, final PaymentAllocationBuilder builder)
	{
		builder.build();
		final List<AllocationLineCandidate> candidatesActual = builder.getAllocationLineCandidates();
		assertExpected(candidatesExpected, candidatesActual);

		assertAllocationHdrCount(candidatesExpected.size());
	}

	private final void assertExpected(final List<AllocationLineCandidate> candidatesExpected, final List<AllocationLineCandidate> candidatesActual)
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

	private PaymentAllocationBuilder newPaymentAllocationBuilder(final Collection<IInvoiceRow> invoices, final Collection<IPaymentRow> payments)
	{
		final List<PayableDocument> payableDocuments = new ArrayList<>();
		for (final IInvoiceRow invoice : invoices)
		{
			payableDocuments.add(invoice.copyAsPayableDocument());
		}

		final List<IPaymentDocument> paymentDocuments = new ArrayList<>();
		for (final IPaymentRow payment : payments)
		{
			paymentDocuments.add(payment.copyAsPaymentDocument());
		}

		return newPaymentAllocationBuilder()
				.payableDocuments(payableDocuments)
				.paymentDocuments(paymentDocuments);
	}

	/**
	 * NOTE: amounts shall be CreditMemo adjusted, but not AP adjusted
	 */
	private IInvoiceRow newInvoiceRow(
			final InvoiceType invoiceType, final String openAmtStr, final String appliedAmtStr, final String discountAmtStr, final String writeOffAmt) throws ExecutionException
	{
		final BigDecimal openAmt_CMAdjusted = new BigDecimal(openAmtStr);
		final BigDecimal openAmt = invoiceType.isCreditMemo() ? openAmt_CMAdjusted.negate() : openAmt_CMAdjusted;
		final int invoiceId = nextInvoiceId++;

		//
		// Create the invoice record (needed for the BL which calculates how much was allocated)
		final I_C_DocType docType = invoiceType2docType.get(invoiceType);
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(invoiceId);
		invoice.setDocumentNo("Doc" + invoiceId);
		invoice.setC_DocType(docType);
		invoice.setIsSOTrx(docType.isSOTrx());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(date));
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_Currency_ID(currency.getId().getRepoId());
		invoice.setGrandTotal(openAmt);
		invoice.setProcessed(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		InterfaceWrapperHelper.save(invoice);

		final InvoiceRow invoiceRow = InvoiceRow.builder()
				.setC_BPartner_ID(invoice.getC_BPartner_ID())
				.setBPartnerName("BP" + invoice.getC_BPartner_ID())
				//
				.setDateInvoiced(invoice.getDateInvoiced())
				.setC_Invoice_ID(invoiceId)
				.setDocumentNo(invoice.getDocumentNo())
				.setDocTypeName(invoice.getC_DocType().getName())
				//
				.setMultiplierAP(invoiceType.getMultiplierAP()) // Vendor/Customer multiplier
				.setCreditMemo(invoiceType.isCreditMemo())
				//
				.setCurrencyISOCode(extractCurrencyCode(invoice))
				.setGrandTotal(openAmt_CMAdjusted)
				.setGrandTotalConv(openAmt_CMAdjusted)
				.setOpenAmtConv(openAmt_CMAdjusted)
				.setPaymentRequestAmt(BigDecimal.ZERO)
				//
				.build();

		invoiceRow.setAppliedAmt(new BigDecimal(appliedAmtStr));
		invoiceRow.setDiscount(new BigDecimal(discountAmtStr));
		invoiceRow.setWriteOffAmt(new BigDecimal(writeOffAmt));

		return invoiceRow;
	}

	private CurrencyCode extractCurrencyCode(final I_C_Invoice invoiceRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	private final LoadingCache<InvoiceType, I_C_DocType> invoiceType2docType = CacheBuilder.newBuilder()
			.build(new CacheLoader<InvoiceType, I_C_DocType>()
			{
				@Override
				public I_C_DocType load(final InvoiceType invoiceType) throws Exception
				{
					return createInvoiceDocType(invoiceType);
				}
			});

	private final I_C_DocType createInvoiceDocType(final InvoiceType invoiceType)
	{
		final String docBaseType;
		final boolean isSOTrx;
		if (invoiceType.getMultiplierAP().signum() > 0)
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

	private IPaymentRow newPaymentRow(final boolean isReceipt, final String openAmtStr, final String appliedAmtStr)
	{
		final BigDecimal multiplierAP = isReceipt ? BigDecimal.ONE : BigDecimal.ONE.negate();
		final BigDecimal openAmt = new BigDecimal(openAmtStr);

		final int paymentId = nextPaymentId++;
		final PaymentRow paymentRow = PaymentRow.builder()
				.setC_BPartner_ID(bpartnerId.getRepoId())
				.setBPartnerName("BP" + bpartnerId)
				//
				.setPaymentDate(TimeUtil.asDate(date))
				.setC_Payment_ID(paymentId)
				.setDocumentNo("Doc" + paymentId)
				.setDocTypeName(isReceipt ? "Receipt" : "Payment")
				.setMultiplierAP(multiplierAP)
				//
				.setCurrencyISOCode(currency.getCurrencyCode())
				.setPayAmt(openAmt)
				.setPayAmtConv(openAmt)
				.setOpenAmtConv(openAmt)
				//
				.build();

		paymentRow.setAppliedAmt(new BigDecimal(appliedAmtStr));

		//
		// Create a dummy record (needed for the BL which calculates how much was allocated)
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setC_Payment_ID(paymentId);
		payment.setC_Currency_ID(currency.getId().getRepoId());
		InterfaceWrapperHelper.save(payment);

		return paymentRow;
	}

	private final AllocationLineCandidate newAllocationCandidate(
			final IAllocableDocRow payableRow, final IAllocableDocRow paymentRow, final String allocatedAmtStr, final String discountAmtStr, final String writeOffAmtStr, final String overUnderAmtStr, final String paymentOverUnderAmtStr)
	{
		return AllocationLineCandidate.builder()
				.bpartnerId(bpartnerId)
				//
				.payableDocumentRef(extractReference(payableRow))
				.paymentDocumentRef(extractReference(paymentRow))
				//
				.amount(new BigDecimal(allocatedAmtStr))
				.discountAmt(new BigDecimal(discountAmtStr))
				.writeOffAmt(new BigDecimal(writeOffAmtStr))
				.payableOverUnderAmt(new BigDecimal(overUnderAmtStr))
				.paymentOverUnderAmt(new BigDecimal(paymentOverUnderAmtStr))
				//
				.build();
	}

	private static final TableRecordReference extractReference(final IAllocableDocRow row)
	{
		if (row instanceof IPaymentRow)
		{
			return new TableRecordReference(org.compiere.model.I_C_Payment.Table_Name, ((IPaymentRow)row).getC_Payment_ID());
		}
		else if (row instanceof IInvoiceRow)
		{
			return new TableRecordReference(I_C_Invoice.Table_Name, ((IInvoiceRow)row).getC_Invoice_ID());
		}
		else
		{
			return null;
		}
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

	private final void assertInvoiceAllocatedAmt(final int invoiceRepoId, final BigDecimal expectedAllocatedAmt)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRepoId);
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);

		final BigDecimal actualAllocatedAmt = allocationDAO.retrieveAllocatedAmt(invoice);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for invoice " + invoiceId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private final void assertInvoiceAllocatedAmt(final int invoiceId, final int expectedAllocatedAmtInt)
	{
		assertInvoiceAllocatedAmt(invoiceId, BigDecimal.valueOf(expectedAllocatedAmtInt));
	}

	private final void assertInvoiceAllocated(final int invoiceRepoId, final boolean expectedAllocated)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRepoId);
		final I_C_Invoice invoice = invoicesDAO.getByIdInTrx(invoiceId);
		final boolean ignoreProcessed = false;
		invoiceBL.testAllocation(invoice, ignoreProcessed);

		assertThat(invoice.isPaid())
				.as("Invoice allocated: " + invoiceId)
				.isEqualTo(expectedAllocated);
	}

	private final void assertPaymentAllocatedAmt(final int paymentRepoId, final BigDecimal expectedAllocatedAmt)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(paymentRepoId);
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		final BigDecimal actualAllocatedAmt = paymentDAO.getAllocatedAmt(payment);

		assertThat(actualAllocatedAmt)
				.as("Allocated amount for payment " + paymentId)
				.isEqualByComparingTo(expectedAllocatedAmt);
	}

	private final void assertPaymentAllocatedAmt(final int paymentId, final int expectedAllocatedAmtInt)
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
}
