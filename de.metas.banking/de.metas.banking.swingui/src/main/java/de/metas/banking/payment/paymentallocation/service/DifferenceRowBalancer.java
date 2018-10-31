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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceWriteOffAmountType;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationContext;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;
import de.metas.util.Check;
import de.metas.util.collections.IdentityHashSet;
import de.metas.util.collections.ListUtils;

/**
 * Distribute the {@link PaymentAllocationTotals#getDiffInvoiceMinusPay()} on given {@link IInvoiceRow}s and {@link IPaymentRow}s.
 *
 * When distributing, only selected rows which are not {@link IAllocableDocRow#isTaboo()} are considered.
 *
 * @author tsa
 *
 */
public class DifferenceRowBalancer
{
	public static final DifferenceRowBalancer start()
	{
		return new DifferenceRowBalancer();
	}

	//
	// Parameters
	private PaymentAllocationContext _context;
	private Supplier<PaymentAllocationTotals> _totalsSupplier;
	private List<IInvoiceRow> invoiceRows;
	private List<IPaymentRow> paymentRows;

	//
	// State
	private final IdentityHashSet<IInvoiceRow> invoiceRowsChanged = new IdentityHashSet<>();
	private final IdentityHashSet<IPaymentRow> paymentRowsChanged = new IdentityHashSet<>();

	private static final Predicate<IAllocableDocRow> PREDICATE_CustomerDocument = new Predicate<IAllocableDocRow>()
	{

		@Override
		public boolean apply(final IAllocableDocRow row)
		{
			return row != null && row.isCustomerDocument();
		}
	};
	private static final Predicate<IAllocableDocRow> PREDICATE_VendorDocument = new Predicate<IAllocableDocRow>()
	{

		@Override
		public boolean apply(final IAllocableDocRow row)
		{
			return row != null && row.isVendorDocument();
		}
	};

	private static final Ordering<IAllocableDocRow> ORDERING_DocumentDate_DocumentNo = IAllocableDocRow.ORDERING_DocumentDate_DocumentNo;
	private static final Ordering<IAllocableDocRow> ORDERING_DocumentDate_DocumentNo_DESC = ORDERING_DocumentDate_DocumentNo.reverse();

	private DifferenceRowBalancer()
	{
		super();
	}

	public void balance()
	{
		final PaymentAllocationContext context = getContext();

		// Exit if Calculation does not make sense
		// i.e. none of the "write off" fields are editable.
		// FIXME: not sure if this checking is really needed....
		if (!context.isAllowAnyWriteOffAmount())
		{
			return;
		}

		//
		// Get the invoice and payment rows on which we will distribute the difference.
		final List<IInvoiceRow> invoiceRows = getInvoiceRowsCopy();
		final List<IPaymentRow> paymentRows = getPaymentRowsCopy();
		// Make sure we are working only with selected rows
		filter(invoiceRows, IAllocableDocRow.PREDICATE_Selected);
		filter(paymentRows, IAllocableDocRow.PREDICATE_Selected);

		//
		// Prepare the invoice rows: set AppliedAmt=OpenAmt => WriteOff amounts will be set to ZERO
		// This step is needed because it will change the total invoiced amount to the maximum which we can change.
		// If this step would miss, in some cases the totals difference would be zero (because not the whole OpenAmt is considered) and the algorithm would do nothing.
		setAppliedAmtAsOpenAmt(invoiceRows);
		setAppliedAmtAsOpenAmt(paymentRows);

		//
		// Calculate the totals only now, after we prepared the invoice rows,
		// and in case there is no difference to distribute, no future calculations are needed.
		final PaymentAllocationTotals totals = calculateTotals();
		if (!totals.hasDifferences())
		{
			return;
		}

		//
		// Special case: only invoices were selected, so we need to allocate between them
		if (!invoiceRows.isEmpty() && paymentRows.isEmpty())
		{
			balance_RegularInvoices_to_CreditMemos(copyAndFilter(invoiceRows, PREDICATE_CustomerDocument));
			balance_RegularInvoices_to_CreditMemos(copyAndFilter(invoiceRows, PREDICATE_VendorDocument));
			balance_PurchaseInvoices_to_SalesInvoices(invoiceRows);
			return;
		}

		//
		// Prepare for distributing the amounts
		// * take out the locked rows (taboos)
		// * order the rows by DocumentDate and DocumentNo descending
		filter(invoiceRows, IAllocableDocRow.PREDICATE_NoTaboo);
		filter(paymentRows, IAllocableDocRow.PREDICATE_NoTaboo);
		Collections.sort(invoiceRows, ORDERING_DocumentDate_DocumentNo_DESC);
		Collections.sort(paymentRows, ORDERING_DocumentDate_DocumentNo_DESC);

		//
		// define a smaller Table where the applied amounts are raised
		// and a greater Table where the applied Amounts are lowered.
		final boolean isInvGreater = totals.isMoreInvoicedThanPaid();
		final List<? extends IAllocableDocRow> greaterTable = isInvGreater ? invoiceRows : paymentRows;
		final List<? extends IAllocableDocRow> smallerTable = isInvGreater ? paymentRows : invoiceRows;

		BigDecimal calcDiff = totals.getDiffInvoiceMinusPay().abs();

		// To nullify the difference the following order is used:
		// 1. Raise smaller table (Ignore negative rows)
		// 2. Lower negative rows of the greater table to their open amount
		// 3. Lower greater table (Ignore negative rows)
		// 4. Raise negative Rows of the smaller table to zero.
		// This order first put documents to their original state and then change documents to fit the difference, second.

		//
		// Step 1: Raise the applied amounts of each positive row in the smaller Table. Exit when difference is depleted.
		for (final IAllocableDocRow row : smallerTable)
		{
			// only selected rows, only positive rows, no taboo rows
			final int sign = row.getOpenAmtConv().signum();
			if (sign < 0)
			{
				continue;
			}

			final BigDecimal rowOpen = row.getOpenAmtConv();
			BigDecimal rowAppliedAmt = row.getAppliedAmt();
			final BigDecimal rowLimit = rowOpen.subtract(rowAppliedAmt);			// rowLimit = open - applied -> never Add More than this

			final BigDecimal calcValue = rowLimit.min(calcDiff); 				// get the minimum of rowLimit and leftover difference
			rowAppliedAmt = rowAppliedAmt.add(calcValue);				// Raise the applied amount by calcValue

			row.setAppliedAmtAndUpdate(context, rowAppliedAmt);
			notifyRowChanged(row);

			calcDiff = calcDiff.subtract(calcValue); 			// reduce the leftover difference by calcValue
			if (calcDiff.signum() == 0)
			{
				return;
			}
		}

		//
		// Step 2: Lower the applied amount of each negative row in the greater table to its open amount. Exit when difference is depleted.
		for (final IAllocableDocRow row : greaterTable)
		{
			final int sign = row.getOpenAmtConv().signum();
			if (sign > 0)
			{
				continue;
			}

			final BigDecimal rowOpen = row.getOpenAmtConv();
			BigDecimal rowAppliedAmt = row.getAppliedAmt();
			final BigDecimal rowLimit = rowOpen.subtract(rowAppliedAmt).abs();	// rowLimit needs to be Positive because it is compared with calcDiff

			final BigDecimal calcValue = rowLimit.min(calcDiff);
			rowAppliedAmt = rowAppliedAmt.subtract(calcValue);

			row.setAppliedAmtAndUpdate(context, rowAppliedAmt);
			notifyRowChanged(row);

			calcDiff = calcDiff.subtract(calcValue);
			if (calcDiff.signum() == 0)
			{
				return;
			}
		}

		//
		// Step 3: Lower the applied amount of each positive row in the greater table to Zero. Exit when difference is depleted.
		for (final IAllocableDocRow row : greaterTable)
		{
			final int sign = row.getOpenAmtConv().signum();
			if (sign < 0)
			{
				continue;
			}

			BigDecimal rowAppliedAmt = row.getAppliedAmt();			// applied amount

			final BigDecimal calcValue = rowAppliedAmt.min(calcDiff); 				// get the minimum of applied and leftover difference
			rowAppliedAmt = rowAppliedAmt.subtract(calcValue);		// reduce the applied amount by calcValue

			row.setAppliedAmtAndUpdate(context, rowAppliedAmt);
			notifyRowChanged(row);

			calcDiff = calcDiff.subtract(calcValue);
			if (calcDiff.signum() == 0)
			{
				return;
			}
		}

		//
		// Step 4: Raise the applied amount of each negative row in the smaller table to Zero. Exit when difference is depleted.
		for (final IAllocableDocRow row : smallerTable)
		{
			final int sign = row.getOpenAmtConv().signum();
			if (sign > 0)
			{
				continue;
			}

			BigDecimal rowAppliedAmt = row.getAppliedAmt().abs(); // rowApplied needs to be >0 because it is compared with calcDiff

			final BigDecimal calcValue = rowAppliedAmt.min(calcDiff);
			rowAppliedAmt = rowAppliedAmt.subtract(calcValue).multiply(BigDecimal.valueOf(sign));

			row.setAppliedAmtAndUpdate(context, rowAppliedAmt);
			notifyRowChanged(row);

			calcDiff = calcDiff.subtract(calcValue);
			if (calcDiff.equals(BigDecimal.ZERO))
			{
				return;
			}
		}
	}

	/**
	 * Balance purchase invoices applied amounts over sales invoices.
	 * 
	 * As a result,
	 * <ul>
	 * <li> sale invoice's WriteOff amount type is adjusted when there is too less purchase invoice amount to distribute
	 * <li> sale invoice's Over/Under amount type is adjusted when there is too much purchase invoice amount to distribute
	 * </ul>
	 * @param invoiceRows
	 */
	private final void balance_PurchaseInvoices_to_SalesInvoices(final List<IInvoiceRow> invoiceRows)
	{
		final PaymentAllocationContext context = getContext();
		if (!context.isAllowPurchaseSalesInvoiceCompensation())
		{
			return;
		}
		
		final List<IInvoiceRow> saleInvoiceRows = copyAndFilter(invoiceRows, PREDICATE_CustomerDocument);
		final List<IInvoiceRow> purchaseInvoiceRows = copyAndFilter(invoiceRows, PREDICATE_VendorDocument);
		
		if (saleInvoiceRows.isEmpty() || purchaseInvoiceRows.isEmpty())
		{
			return;
		}
		
		//
		// Prepare:
		// * sort by DocumentDate, DocumentNo ascending!
		Collections.sort(saleInvoiceRows, ORDERING_DocumentDate_DocumentNo);
		Collections.sort(purchaseInvoiceRows, ORDERING_DocumentDate_DocumentNo);
		
		//
		// Build initial informations
		// * sale invoices list
		// * purchase invoices list
		// * how much purchase amount we need to distribute to sales invoices
		BigDecimal amtTotalPurchaseInvoiceToDistribute = BigDecimal.ZERO;
		final List<IInvoiceRow> purchaseInvoices = new ArrayList<>();
		for (final IInvoiceRow invoiceRow : purchaseInvoiceRows)
		{
			final BigDecimal amtApplied = invoiceRow.getAppliedAmt();
			if (!invoiceRow.isCreditMemo()	&& amtApplied.signum() > 0 )
			{
				amtTotalPurchaseInvoiceToDistribute = amtTotalPurchaseInvoiceToDistribute.add(amtApplied);

				// collect only those invoices which are not locked, because we are not touching the locked ones
				if (!invoiceRow.isTaboo())
				{
					purchaseInvoices.add(invoiceRow);
				}
			}
		}
		
		// If there is nothing to distribute, then stop here
		if (amtTotalPurchaseInvoiceToDistribute.signum() == 0)
		{
			return;
		}
		
		// collect sales invoices
		final List<IInvoiceRow> salesInvoices = new ArrayList<>();
		for (final IInvoiceRow invoiceRow : saleInvoiceRows)
		{
			if (!invoiceRow.isCreditMemo())
			{

				// collect only those invoices which are not locked, because we are not touching the locked ones
				if (!invoiceRow.isTaboo())
				{
					salesInvoices.add(invoiceRow);
				}
			}
		}
		
		
		//
		// Iterate the sales invoices and distribute the total credit memo amount on them.
		for (final IInvoiceRow salesInvoice : salesInvoices)
		{
			final BigDecimal amtAppliedInitial = salesInvoice.getAppliedAmt();
			final BigDecimal amtToApply = amtAppliedInitial.min(amtTotalPurchaseInvoiceToDistribute);
			
			salesInvoice.setAppliedAmtAndUpdate(context, amtToApply);
			notifyRowChanged(salesInvoice);
			
			amtTotalPurchaseInvoiceToDistribute = amtTotalPurchaseInvoiceToDistribute.subtract(amtToApply);
		}

		//
		// If we still have purchase invoice to distribute and OverUnderAmt is allowed, adjust the purchase (from last to first),
		// by distributing the remaining amount to their over/under.
		final boolean overUnderAmtAllowed = context.getAllowedWriteOffTypes().contains(InvoiceWriteOffAmountType.OverUnder);
		if (overUnderAmtAllowed && amtTotalPurchaseInvoiceToDistribute.signum() != 0)
		{
			
			for (final IInvoiceRow purchase : Lists.reverse(purchaseInvoices))
			{
				final BigDecimal amtAppliedInitial = purchase.getAppliedAmt();
				final BigDecimal amtAppliedToSubtract = amtAppliedInitial.min(amtTotalPurchaseInvoiceToDistribute);
				final BigDecimal amtToApply = amtAppliedInitial.subtract(amtAppliedToSubtract);

				purchase.setAppliedAmt(amtToApply);
				purchase.distributeNotAppliedAmtToWriteOffs(InvoiceWriteOffAmountType.OverUnder);
				notifyRowChanged(purchase);

				amtTotalPurchaseInvoiceToDistribute = amtTotalPurchaseInvoiceToDistribute.subtract(amtAppliedToSubtract);
			}
		}
		
	}
	
	/**
	 * Balance credit memos applied amounts over regular invoices.
	 * 
	 * As a result,
	 * <ul>
	 * <li> regular invoice's WriteOff amount type is adjusted when there is too less credit memo amount to distribute
	 * <li> credit memo invoice's Over/Under amount type is adjusted when there is too much credit memo amount to distribute
	 * </ul>
	 * @param invoiceRows
	 */
	private final void balance_RegularInvoices_to_CreditMemos(final List<IInvoiceRow> invoiceRows)
	{
		//
		// Prepare:
		// * sort by DocumentDate, DocumentNo ascending!
		Collections.sort(invoiceRows, ORDERING_DocumentDate_DocumentNo);

		//
		// Build initial informations
		// * regular invoices list
		// * credit memo invoices list
		// * how much credit memo amount we need to distribute to regular invoices
		BigDecimal amtTotalCreditMemoToDistribute = BigDecimal.ZERO;
		final List<IInvoiceRow> regularInvoices = new ArrayList<>();
		final List<IInvoiceRow> creditMemoInvoices = new ArrayList<>();
		for (final IInvoiceRow invoiceRow : invoiceRows)
		{
			final BigDecimal amtApplied = invoiceRow.getAppliedAmt();
			if (invoiceRow.isCreditMemo()
					|| amtApplied.signum() < 0 // we also consider negative invoices as credit memos because in our case they are the same
			)
			{
				amtTotalCreditMemoToDistribute = amtTotalCreditMemoToDistribute.add(amtApplied.negate());

				// collect only those invoices which are not locked, because we are not touching the locked ones
				if (!invoiceRow.isTaboo())
				{
					creditMemoInvoices.add(invoiceRow);
				}
			}
			else
			{
				// collect only those invoices which are not locked, because we are not touching the locked ones
				if (!invoiceRow.isTaboo())
				{
					regularInvoices.add(invoiceRow);
				}
			}
		}
		// If there is nothing to distribute, then stop here
		if (amtTotalCreditMemoToDistribute.signum() == 0)
		{
			return;
		}

		//
		// Iterate the regular invoices and distribute the total credit memo amount on them.
		for (final IInvoiceRow regularInvoice : regularInvoices)
		{
			final BigDecimal amtAppliedInitial = regularInvoice.getAppliedAmt();
			final BigDecimal amtToApply = amtAppliedInitial.min(amtTotalCreditMemoToDistribute);
			
			regularInvoice.setAppliedAmtAndUpdate(getContext(), amtToApply);
			notifyRowChanged(regularInvoice);
			
			amtTotalCreditMemoToDistribute = amtTotalCreditMemoToDistribute.subtract(amtToApply);
		}

		//
		// If we still have credit memo to distribute and OverUnderAmt is allowed, adjust the credit memos (from last to first),
		// by distributing the remaining amount to their over/under.
		final boolean overUnderAmtAllowed = getContext().getAllowedWriteOffTypes().contains(InvoiceWriteOffAmountType.OverUnder);
		if (overUnderAmtAllowed && amtTotalCreditMemoToDistribute.signum() != 0)
		{
			
			for (final IInvoiceRow creditMemo : Lists.reverse(creditMemoInvoices))
			{
				final BigDecimal amtAppliedInitial = creditMemo.getAppliedAmt().negate();
				final BigDecimal amtAppliedToSubtract = amtAppliedInitial.min(amtTotalCreditMemoToDistribute);
				final BigDecimal amtToApply = amtAppliedInitial.subtract(amtAppliedToSubtract);

				creditMemo.setAppliedAmt(amtToApply.negate());
				creditMemo.distributeNotAppliedAmtToWriteOffs(InvoiceWriteOffAmountType.OverUnder);
				notifyRowChanged(creditMemo);

				amtTotalCreditMemoToDistribute = amtTotalCreditMemoToDistribute.subtract(amtAppliedToSubtract);
			}
		}
	}

	private final void setAppliedAmtAsOpenAmt(final Iterable<? extends IAllocableDocRow> rows)
	{
		final PaymentAllocationContext context = getContext();
		for (final IAllocableDocRow row : rows)
		{
			// Don't touch the locked rows
			if (row.isTaboo())
			{
				continue;
			}

			row.setAppliedAmtAndUpdate(context, row.getOpenAmtConv());
			notifyRowChanged(row);
		}

	}

	/**
	 * @return set of {@link IInvoiceRow}s which were updated after {@link #balance()} was executed
	 */
	public final Set<IInvoiceRow> getInvoiceRowsChanged()
	{
		return invoiceRowsChanged;
	}

	/**
	 * @return set of {@link IPaymentRow}s which were updated after {@link #balance()} was executed
	 */
	public final Set<IPaymentRow> getPaymentRowsChanged()
	{
		return paymentRowsChanged;
	}

	private static final <RowType extends IAllocableDocRow> void filter(final List<RowType> rows, final Predicate<IAllocableDocRow> predicate)
	{
		for (final Iterator<RowType> it = rows.iterator(); it.hasNext();)
		{
			final RowType row = it.next();
			if (!predicate.apply(row))
			{
				it.remove();
			}
		}
	}

	private static final <RowType extends IAllocableDocRow> List<RowType> copyAndFilter(final List<? extends RowType> rows, final Predicate<IAllocableDocRow> predicate)
	{
		return ListUtils.copyAndFilter(rows, predicate);
	}

	/**
	 * Add given row to the list of rows which were changed.
	 *
	 * Later, these lists could be used to notify UI about which rows were changed.
	 *
	 * @param row
	 * @see #getInvoiceRowsChanged()
	 * @see #getPaymentRowsChanged()
	 */
	private void notifyRowChanged(final IAllocableDocRow row)
	{
		if (row instanceof IInvoiceRow)
		{
			invoiceRowsChanged.add((IInvoiceRow)row);
		}
		else if (row instanceof IPaymentRow)
		{
			paymentRowsChanged.add((IPaymentRow)row);
		}
		else
		{
			// shall not happen
			throw new IllegalStateException("Unknown row type: " + row);
		}
	}

	public DifferenceRowBalancer setContext(final PaymentAllocationContext context)
	{
		_context = context;
		return this;
	}

	private final PaymentAllocationContext getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	private final PaymentAllocationTotals calculateTotals()
	{
		Check.assumeNotNull(_totalsSupplier, "totals not null");
		final PaymentAllocationTotals totals = _totalsSupplier.get();
		Check.assumeNotNull(totals, "totals not null");
		return totals;
	}

	public DifferenceRowBalancer setTotals(final Supplier<PaymentAllocationTotals> totalsSupplier)
	{
		_totalsSupplier = totalsSupplier;
		return this;
	}

	private final List<IInvoiceRow> getInvoiceRowsCopy()
	{
		Check.assumeNotNull(invoiceRows, "invoiceRows not null");
		return new ArrayList<>(invoiceRows);
	}

	public DifferenceRowBalancer setInvoiceRows(final List<IInvoiceRow> invoiceRows)
	{
		this.invoiceRows = invoiceRows;
		return this;
	}

	private final List<IPaymentRow> getPaymentRowsCopy()
	{
		Check.assumeNotNull(paymentRows, "paymentRows not null");
		return new ArrayList<>(paymentRows);
	}

	public DifferenceRowBalancer setPaymentRows(final List<IPaymentRow> paymentRows)
	{
		this.paymentRows = paymentRows;
		return this;
	}
}
