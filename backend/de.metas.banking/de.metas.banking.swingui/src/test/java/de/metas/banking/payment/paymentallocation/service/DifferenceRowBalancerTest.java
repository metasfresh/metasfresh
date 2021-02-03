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

import static de.metas.banking.payment.paymentallocation.service.InvoiceType.VendorCreditMemo;
import static de.metas.banking.payment.paymentallocation.service.InvoiceType.VendorInvoice;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;

import de.metas.banking.payment.paymentallocation.model.AbstractAllocableDocTableModel;
import de.metas.banking.payment.paymentallocation.model.AllocableDocType;
import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceWriteOffAmountType;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationContext;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;

public class DifferenceRowBalancerTest
{
	private final int bpartnerId = 100;
	private int nextInvoiceId = 1;
	private final Date date = SystemTime.asDayTimestamp();
	private final InvoiceWriteOffAmountType writeOffType = InvoiceWriteOffAmountType.OverUnder;
	//
	private PaymentAllocationContext context;
	private List<IInvoiceRow> invoiceRows;
	private List<IPaymentRow> paymentRows;

	@Rule
	public final TestWatcher testWatcher = new TestWatcher()
	{
		@Override
		protected void failed(final Throwable e, final org.junit.runner.Description description)
		{
			dumpStatus(description.getDisplayName());
		}
	};

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_InvoicesAndCreditMemos01_LessCreditMemoThanInvoiced()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(VendorInvoice, 1000), newInvoiceRow(VendorCreditMemo, -100));
		paymentRows = Arrays.asList();

		balanceRows();

		// Asserts
		assertAppliedAmt(invoiceRows.get(0), 100);
		assertWriteOffAmt(invoiceRows.get(0), 900);
		//
		assertAppliedAmt(invoiceRows.get(1), -100);
		assertWriteOffAmt(invoiceRows.get(1), 0);
	}

	@Test
	public void test_InvoicesAndCreditMemos_MoreCreditMemoThanInvoiced()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(VendorInvoice, 300), newInvoiceRow(VendorCreditMemo, -1000));
		paymentRows = Arrays.asList();

		balanceRows();

		// Asserts
		assertAppliedAmt(invoiceRows.get(0), 300);
		assertWriteOffAmt(invoiceRows.get(0), 0);
		//
		assertAppliedAmt(invoiceRows.get(1), -300);
		assertWriteOffAmt(invoiceRows.get(1), -700);
	}

	@Test
	public void test_InvoicesAndCreditMemos02_LessCreditMemoThanInvoiced()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(VendorInvoice, 100) // 0
				, newInvoiceRow(VendorInvoice, 100) // 1
				, newInvoiceRow(VendorInvoice, 100) // 2
				, newInvoiceRow(VendorInvoice, 100) // 3
				, newInvoiceRow(VendorInvoice, 100) // 4
				, newInvoiceRow(VendorCreditMemo, -250) // 5
				, newInvoiceRow(VendorCreditMemo, -160) // 6
		);
		paymentRows = Arrays.asList();

		balanceRows();

		//
		// Asserts
		// Invoice #0-3
		for (int i = 0; i <= 3; i++)
		{
			assertAppliedAmt(invoiceRows.get(i), 100);
			assertWriteOffAmt(invoiceRows.get(i), 0);
		}
		// Invoice #4
		assertAppliedAmt(invoiceRows.get(4), 10);
		assertWriteOffAmt(invoiceRows.get(4), 90);
		// CM #5
		assertAppliedAmt(invoiceRows.get(5), -250);
		assertWriteOffAmt(invoiceRows.get(5), 0);
		// CM #6
		assertAppliedAmt(invoiceRows.get(6), -160);
		assertWriteOffAmt(invoiceRows.get(6), 0);
	}

	@Test
	public void test_InvoicesAndCreditMemos02_MoreCreditMemoThanInvoiced()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(VendorInvoice, 100) // 0
				, newInvoiceRow(VendorInvoice, 100) // 1
				, newInvoiceRow(VendorInvoice, 100) // 2
				, newInvoiceRow(VendorInvoice, 100) // 3
				, newInvoiceRow(VendorInvoice, 100) // 4
				, newInvoiceRow(VendorCreditMemo, -250) // 5
				, newInvoiceRow(VendorCreditMemo, -450) // 6
		);
		paymentRows = Arrays.asList();

		balanceRows();

		//
		// Asserts
		// Invoice #0-4
		for (int i = 0; i <= 3; i++)
		{
			assertAppliedAmt(invoiceRows.get(i), 100);
			assertWriteOffAmt(invoiceRows.get(i), 0);
		}
		// CM #5
		assertAppliedAmt(invoiceRows.get(5), -250);
		assertWriteOffAmt(invoiceRows.get(5), 0);
		// CM #6
		assertAppliedAmt(invoiceRows.get(6), -250);
		assertWriteOffAmt(invoiceRows.get(6), -200);
	}

	@Test
	public void test_InvoicesAndCreditMemos_OneLockedRow()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(VendorInvoice, 100), newInvoiceRow(VendorInvoice, 100), newInvoiceRow(VendorInvoice, 100), newInvoiceRow(VendorCreditMemo, -110));
		paymentRows = Arrays.asList();

		invoiceRows.get(1).setTaboo(true); // lock!

		balanceRows();

		// Asserts
		// #0
		assertAppliedAmt(invoiceRows.get(0), 100);
		assertWriteOffAmt(invoiceRows.get(0), 0);
		// #1 - untouched
		assertAppliedAmt(invoiceRows.get(1), 100);
		assertWriteOffAmt(invoiceRows.get(1), 0);
		// #2
		assertAppliedAmt(invoiceRows.get(2), 10);
		assertWriteOffAmt(invoiceRows.get(2), 90);
		// CM #2
		assertAppliedAmt(invoiceRows.get(3), -110);
		assertWriteOffAmt(invoiceRows.get(3), 0);
	}

	@Test
	public void test_SaleInvoicesAndPurchaseInvoices01_LessPurchaseInvoiceThanInvoiced()
	{
		context = prepareContext()
				.allowPurchaseSalesInvoiceCompensation(true)
				.build();

		invoiceRows = Arrays.asList(
				newInvoiceRow(InvoiceType.CustomerInvoice, 1000), newInvoiceRow(VendorInvoice, 600));
		paymentRows = Arrays.asList();

		balanceRows();

		// Asserts
		assertAppliedAmt(invoiceRows.get(0), 600);
		assertOverUnderAmt(invoiceRows.get(0), 400);
		//
		assertAppliedAmt(invoiceRows.get(1), 600);
		assertOverUnderAmt(invoiceRows.get(1), 0);
	}

	@Test
	public void test_SaleInvoicesAndPurchaseInvoices02_MorePurchaseInvoiceThanInvoiced()
	{
		context = prepareContext()
				.allowPurchaseSalesInvoiceCompensation(true)
				.build();

		invoiceRows = Arrays.asList(
				newInvoiceRow(InvoiceType.CustomerInvoice, 1000), newInvoiceRow(VendorInvoice, 1600));
		paymentRows = Arrays.asList();

		balanceRows();

		// Asserts
		assertAppliedAmt(invoiceRows.get(0), 1000);
		assertOverUnderAmt(invoiceRows.get(0), 0);
		//
		assertAppliedAmt(invoiceRows.get(1), 1000);
		assertOverUnderAmt(invoiceRows.get(1), 600);
	}

	@Test
	public void test_SaleInvoicesAndPurchaseInvoices_CompensationNotAllowed()
	{
		context = createContext();

		invoiceRows = Arrays.asList(
				newInvoiceRow(InvoiceType.CustomerInvoice, 1000), newInvoiceRow(VendorInvoice, 1600));
		paymentRows = Arrays.asList();

		balanceRows();

		// Asserts
		assertAppliedAmt(invoiceRows.get(0), 1000); // full amount
		assertOverUnderAmt(invoiceRows.get(0), 0);
		//
		assertAppliedAmt(invoiceRows.get(1), 1600); // full amount
		assertOverUnderAmt(invoiceRows.get(1), 0);
	}

	private PaymentAllocationContext createContext()
	{
		return prepareContext().build();
	}

	private PaymentAllocationContext.Builder prepareContext()
	{
		return PaymentAllocationContext.builder()
				.setMultiCurrency(false)
				.setDocumentIdsToIncludeWhenQuering(ArrayListMultimap.<AllocableDocType, Integer> create())
				.setC_BPartner_ID(bpartnerId)
				.addAllowedWriteOffType(writeOffType)
				.allowPurchaseSalesInvoiceCompensation(false);
	}

	private void balanceRows()
	{
		DifferenceRowBalancer.start()
				.setContext(context)
				.setTotals(createPaymentAllocationTotals())
				.setInvoiceRows(invoiceRows)
				.setPaymentRows(paymentRows)
				.balance();

	}

	private final InvoiceRow.Builder newInvoiceRow()
	{
		final int invoiceId = nextInvoiceId++;

		// we are setting the documentNo on each invoice to make sure the BL is sorting them in the order we created.
		// else, assertions could fail
		String documentNo = String.format("%05d", invoiceId);

		return InvoiceRow.builder()
				.setC_Invoice_ID(invoiceId)
				.setC_BPartner_ID(bpartnerId)
				.setBPartnerName("BP" + bpartnerId)
				.setDocumentNo(documentNo)
				.setDateInvoiced(date)
		//
		;
	}

	private final IInvoiceRow newInvoiceRow(final InvoiceType type, final int openAndAppliedAmt)
	{
		final BigDecimal openAndAppliedAmtBD = BigDecimal.valueOf(openAndAppliedAmt);
		final InvoiceRow invoiceRow = newInvoiceRow()
				.setMultiplierAP(type.getMultiplierAP())
				.setCreditMemo(type.isCreditMemo())
				.setGrandTotal(openAndAppliedAmtBD)
				.setGrandTotalConv(openAndAppliedAmtBD)
				.setOpenAmtConv(openAndAppliedAmtBD)
				.build();

		invoiceRow.setSelected(true);
		invoiceRow.setTaboo(false);
		invoiceRow.setAppliedAmt(openAndAppliedAmtBD);
		return invoiceRow;
	}

	private final Supplier<PaymentAllocationTotals> createPaymentAllocationTotals()
	{
		return () -> PaymentAllocationTotals.builder()
				.setInvoicedAmt(AbstractAllocableDocTableModel.calculateTotalAppliedAmt(invoiceRows))
				.setPaymentExistingAmt(AbstractAllocableDocTableModel.calculateTotalAppliedAmt(paymentRows))
				.build();
	}

	private void dump(final String title, final Iterable<? extends IAllocableDocRow> rows)
	{
		System.out.println("=== " + title + " ==============================");
		if (rows == null)
		{
			return;
		}

		for (final IAllocableDocRow row : rows)
		{
			System.out.println(row);
		}
	}

	protected void dumpStatus(String title)
	{
		System.out.println("================ " + title + " ========================================================");
		System.out.println("Context: " + context);
		dump("invoices", invoiceRows);
	}

	private final void assertAppliedAmt(final IAllocableDocRow row, final int expectedAppliedAmt)
	{
		Assert.assertThat("row's appliedAmt: " + row, row.getAppliedAmt(), Matchers.comparesEqualTo(BigDecimal.valueOf(expectedAppliedAmt)));
	}

	private final void assertWriteOffAmt(final IInvoiceRow row, final int expectedAmt)
	{
		Assert.assertThat("row's " + writeOffType + ": " + row, row.getWriteOffAmtOfType(writeOffType), Matchers.comparesEqualTo(BigDecimal.valueOf(expectedAmt)));
	}

	private final void assertOverUnderAmt(final IInvoiceRow row, final int expectedAmt)
	{
		Assert.assertThat("row's " + writeOffType + ": " + row, row.getOverUnderAmt(), Matchers.comparesEqualTo(BigDecimal.valueOf(expectedAmt)));
	}

}
