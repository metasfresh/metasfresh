package de.metas.banking.payment.paymentallocation.service;

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
// import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
// import de.metas.banking.payment.paymentallocation.model.PayableDocument;
// import de.metas.banking.payment.paymentallocation.model.PaymentDocument;
// import de.metas.banking.payment.paymentallocation.model.InvoiceRow;
// import de.metas.banking.payment.paymentallocation.model.PaymentRow;
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
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Getter;

public class PaymentAllocationBuilderTest
{
	// services
	private IAllocationDAO allocationDAO;
	private IPaymentDAO paymentDAO;
	private IInvoiceBL invoiceBL;
	private IInvoiceDAO invoicesDAO;

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
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "8000", "0", "100", "200"))
				// Payments
				, Arrays.<IPaymentDocument> asList(
				// PaymentId / IsReceipt / OpenAmt / AppliedAmt
				));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), null, "0", "-100", "-200", "-7700", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertInvoiceAllocatedAmt(invoice1.getInvoiceId(), -(100 + 200));
		assertInvoiceAllocated(invoice1.getInvoiceId(), false);
	}

	@Test
	public void test_OneVendorInvoice_OneVendorPayment() throws Exception
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "8000", "5000", "100", "200"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), payment1.getReference(), "-5000", "-100", "-200", "-2700", "0"));

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
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"),
						payment2 = newPaymentRow(IsReceipt_Yes, "5000", "5000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(payment1.getReference(), payment2.getReference(), "-5000", "0", "0", "0", "0"));

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
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "5000", "5000"), payment2 = newPaymentRow(IsReceipt_Yes, "3000", "3000"), payment3 = newPaymentRow(IsReceipt_Yes, "2000", "2000")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(payment1.getReference(), payment2.getReference(), "-3000", "0", "0", "-2000", "0"), //
				newAllocationCandidate(payment1.getReference(), payment3.getReference(), "-2000", "0", "0", "0", "0"));

		//
		// Check
		assertExpected(candidatesExpected, builder);
		assertPaymentAllocatedAmt(payment1.getPaymentId(), -5000);
		assertPaymentAllocatedAmt(payment2.getPaymentId(), +3000);
		assertPaymentAllocatedAmt(payment3.getPaymentId(), +2000);
	}

	@Test
	public void test_InvoiceAndCreditMemo_NoPayments() throws Exception
	{
		final PayableDocument invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoice(CustomerCreditMemo, "1000", "1000", "0", "0") // CreditMemo
				)
				// Payments
				, Arrays.<IPaymentDocument> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), invoice2.getReference(), "1000", "0", "0", "4000", "0"));

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
	public void test_SalesInvoiceAndPurchaseInvoice_NoPayments() throws Exception
	{
		final PayableDocument invoice1, invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "5000", "1000", "0", "0"),
						invoice2 = newInvoice(VendorInvoice, "1000", "1000", "0", "0") //
				)
				// Payments
				, Arrays.<IPaymentDocument> asList());

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), invoice2.getReference(), "1000", "0", "0", "4000", "0"));

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
	public void test_Vendor_MultiInvoice_MultiPayment() throws Exception
	{
		// PaymentId / IsReceipt / OpenAmt / AppliedAmt
		final PaymentDocument payment1 = newPaymentRow(IsReceipt_No, "5000", "5000");
		final PaymentDocument payment2 = newPaymentRow(IsReceipt_No, "5000", "5000");

		// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
		final PayableDocument invoice1 = newInvoice(VendorInvoice, "8000", "6000", "1599", "1");
		final PayableDocument invoice2 = newInvoice(VendorInvoice, "7100", "3000", "50", "50");

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
		final PaymentDocument payment1, payment2;
		final PayableDocument invoice1, invoice2, invoice3, invoice4;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(CustomerInvoice, "8000", "6000", "1599", "1"),
						invoice2 = newInvoice(CustomerInvoice, "7100", "3000", "50", "50"),
						invoice3 = newInvoice(CustomerInvoice, "1600", "1500", "100", "0"),
						invoice4 = newInvoice(CustomerCreditMemo, "500", "500", "0", "0") // i.e. CreditMemo
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
				newAllocationCandidate(invoice1.getReference(), invoice4.getReference(), "500", "1599", "1", "5900", "0")
				//
				, newAllocationCandidate(invoice1.getReference(), payment1.getReference(), "5000", "0", "0", "900", "0")
				//
				, newAllocationCandidate(invoice1.getReference(), payment2.getReference(), "500", "0", "0", "400", "4500")
				//
				, newAllocationCandidate(invoice2.getReference(), payment2.getReference(), "3000", "50", "50", "4000", "1500")
				//
				, newAllocationCandidate(invoice3.getReference(), payment2.getReference(), "1500", "100", "0", "0", "0"));

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
	public void test_CreditMemoInvoice_Payment() throws Exception
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorCreditMemo, "100", "30", "0", "0"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_Yes, "50", "30")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), payment1.getReference(), "30", "0", "0", "70", "20"));

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
	public void test_RegularInvoice_CreditMemoInvoice_Payment() throws Exception
	{
		final PaymentDocument payment1;
		final PayableDocument invoice1;
		final PayableDocument invoice2;
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						invoice1 = newInvoice(VendorInvoice, "165", "100", "10", "5"),
						invoice2 = newInvoice(VendorCreditMemo, "80", "80", "0", "0"))
				// Payments
				, Arrays.asList(
						// PaymentId / IsReceipt / OpenAmt / AppliedAmt
						payment1 = newPaymentRow(IsReceipt_No, "20", "20")));

		//
		// Define expected candidates
		final List<AllocationLineCandidate> candidatesExpected = Arrays.asList(
				// invoiceId / paymentId / AllocatedAmt / DiscountAmt / WriteOffAmt / OverUnderAmt / PaymentOverUnderAmt
				newAllocationCandidate(invoice1.getReference(), invoice2.getReference(), "-80", "-10", "-5", "-70", "0"),
				newAllocationCandidate(invoice1.getReference(), payment1.getReference(), "-20", "0", "0", "-50", "0"));

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
	public void test_VendorInvoice_CustomerReceipt() throws Exception
	{
		final PaymentAllocationBuilder builder = newPaymentAllocationBuilder(
				// Invoices
				Arrays.asList(
						// InvoiceType / OpenAmt / AppliedAmt / Discount / WriteOff
						newInvoice(VendorInvoice, "100", "100", "0", "0"))
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
			final String appliedAmtStr,
			final String discountAmtStr,
			final String writeOffAmtStr)
			throws ExecutionException
	{
		final CurrencyId currencyId = currency.getId();

		final Money openAmt_CMAdjusted_APAdjusted = Money.of(openAmtStr, currencyId);
		final Money openAmt = openAmt_CMAdjusted_APAdjusted
				.negateIf(invoiceType.isCreditMemo())
				.negateIf(!invoiceType.isSoTrx());

		final AllocationAmounts amountsToAllocate_CMAdjusted_APAdjusted = AllocationAmounts.builder()
				.payAmt(Money.of(appliedAmtStr, currencyId))
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
		final I_C_DocType docType = invoiceType2docType.get(invoiceType);
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

	private final LoadingCache<InvoiceType, I_C_DocType> invoiceType2docType = CacheBuilder.newBuilder()
			.build(new CacheLoader<InvoiceType, I_C_DocType>()
			{
				@Override
				public I_C_DocType load(final InvoiceType invoiceType)
				{
					return createInvoiceDocType(invoiceType);
				}
			});

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

	private PaymentDocument newPaymentRow(final boolean isReceipt, final String openAmtStr, final String appliedAmtStr)
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
				.amountToAllocate(Money.of(appliedAmtStr, currencyId).negateIf(!isReceipt))
				.build();
	}

	private final AllocationLineCandidate newAllocationCandidate(
			final TableRecordReference payableRef,
			final TableRecordReference paymentRef,
			final String allocatedAmtStr,
			final String discountAmtStr,
			final String writeOffAmtStr,
			final String overUnderAmtStr,
			final String paymentOverUnderAmtStr)
	{
		return AllocationLineCandidate.builder()
				.bpartnerId(bpartnerId)
				//
				.payableDocumentRef(payableRef)
				.paymentDocumentRef(paymentRef)
				//
				.amount(new BigDecimal(allocatedAmtStr))
				.discountAmt(new BigDecimal(discountAmtStr))
				.writeOffAmt(new BigDecimal(writeOffAmtStr))
				.payableOverUnderAmt(new BigDecimal(overUnderAmtStr))
				.paymentOverUnderAmt(new BigDecimal(paymentOverUnderAmtStr))
				//
				.build();
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

	@Getter
	public enum InvoiceType
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
	}

}
