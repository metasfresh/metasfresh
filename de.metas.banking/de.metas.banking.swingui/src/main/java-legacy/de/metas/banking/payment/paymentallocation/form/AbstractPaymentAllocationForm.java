/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin *
 * Copyright (C) 2009 Idalica Corporation *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package de.metas.banking.payment.paymentallocation.form;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.IProcessor;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.TimeUtil;

import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.metas.adempiere.form.IClientUI;
import de.metas.banking.payment.paymentallocation.model.AllocableDocType;
import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceWriteOffAmountType;
import de.metas.banking.payment.paymentallocation.model.InvoicesTableModel;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationContext;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;
import de.metas.banking.payment.paymentallocation.model.PaymentRow;
import de.metas.banking.payment.paymentallocation.model.PaymentsTableModel;
import de.metas.banking.payment.paymentallocation.service.DifferenceRowBalancer;
import de.metas.banking.payment.paymentallocation.service.IPaymentAllocationFormDAO;

/* package */abstract class AbstractPaymentAllocationForm extends AbstractPaymentForm
{
	// services
	private final transient IPaymentAllocationFormDAO dao = Services.get(IPaymentAllocationFormDAO.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	protected final transient IClientUI clientUI = Services.get(IClientUI.class);

	public static final String MSG_PREFIX = PaymentAllocationForm.class.getSimpleName() + ".";

	// @formatter:off
	public static final String PROPERTY_MultiCurrency = "MultiCurrency";
	private boolean _isMultiCurrency = false;
	private int _filterPaymentId = -1;
	private String _filterPOReference = null;

	private InvoicesTableModel invoicesTableModel = new InvoicesTableModel();
	private PaymentsTableModel paymentsTableModel = new PaymentsTableModel();
	//
	private final Multimap<AllocableDocType, Integer> foreignDocumentIds = ArrayListMultimap.create();

	private BigDecimal totalPaymentCandidatesAmt = BigDecimal.ZERO;
	// @formatter:on

	private final IProcessor<Exception> contextWarningsConsumer = new IProcessor<Exception>()
	{
		@Override
		public void process(final Exception ex)
		{
			clientUI.warn(getWindowNo(), ex);
		}
	};

	public AbstractPaymentAllocationForm()
	{
		super();

		paymentsTableModel.setMultiCurrency(_isMultiCurrency);
		invoicesTableModel.setMultiCurrency(_isMultiCurrency);

	}

	protected final void addForeignDocumentIds(final AllocableDocType docType, final Object[] documentIds)
	{
		if (documentIds == null || documentIds.length <= 0)
		{
			return;
		}

		final Collection<Integer> foreigners = foreignDocumentIds.get(docType);
		for (final Object foreignIdObj : documentIds)
		{
			if (!(foreignIdObj instanceof Integer))
			{
				continue;
			}
			final int foreignId = (int)foreignIdObj;
			if (foreignId <= 0)
			{
				continue;
			}
			foreigners.add(foreignId);
		}
	}

	protected final void addForeignDocumentId(final AllocableDocType docType, final int documentId)
	{
		final Object[] documentIds = new Object[] { documentId };
		addForeignDocumentIds(docType, documentIds);
	}

	/**
	 * Drops all Documents in the Foreigners List.
	 *
	 * This is called when a new BP is chosen, so that the old Documents do not appear in the tables of the next chosen BP.
	 */
	private void clearForeignDocuments()
	{
		foreignDocumentIds.clear();
	}

	/**
	 * Sets a value and calculates allocation and over-/under payment amounts.
	 *
	 * Invoice sum and payment sum are recalculated, too.
	 *
	 * If a row is being selected, Applied amount is set to maximum, if its deselected, Applied amount is set to zero.
	 *
	 * @param modelRowIndex
	 * @param column
	 * @param value
	 * @param isInvoice
	 * @return info message or null
	 */
	protected final void onTableRowChanged(final IAllocableDocRow row, final String modelColumnName)
	{
		if (IAllocableDocRow.PROPERTY_Selected.equals(modelColumnName))
		{
			onRowSelectedChanged(row);
		}
		// changed applied amt.
		else if (IAllocableDocRow.PROPERTY_AppliedAmt.equals(modelColumnName))
		{
			onRowAppliedAmtChanged(row);
		}
		// changed one of the Write off columns.
		else if ((row instanceof IInvoiceRow)
				&& InvoiceWriteOffAmountType.columnNames().contains(modelColumnName))
		{
			final IInvoiceRow invoiceRow = InvoiceRow.castOrNull(row);
			final InvoiceWriteOffAmountType writeOffType = InvoiceWriteOffAmountType.valueOfColumnName(modelColumnName);
			onRowWriteOffAmtChanged(invoiceRow, writeOffType);
		}

		else if ((row instanceof IPaymentRow)
				&& PaymentRow.PROPERTY_DiscountAmt.equals(modelColumnName))
		{
			final IPaymentRow paymentRow = PaymentRow.castOrNull(row);
			onRowDiscountAmtChanged(paymentRow);
		}

		return;
	}

	/**
	 * Called when user selected/deselected a row.
	 *
	 * @param row
	 */
	private void onRowSelectedChanged(final IAllocableDocRow row)
	{
		//
		// Row was just selected:
		if (row.isSelected())
		{
			row.setAppliedAmt(row.getOpenAmtConv());
		}
		//
		// Row was just un-selected:
		else
		{
			row.setAppliedAmt(BigDecimal.ZERO);
			// Reset invoice's all write off amounts
			final IInvoiceRow invoiceRow = InvoiceRow.castOrNull(row);
			if (invoiceRow != null)
			{
				invoiceRow.resetAllWriteOffAmounts();
			}

			// Reset payment's discount amount
			final IPaymentRow paymentRow = PaymentRow.castOrNull(row);
			if (paymentRow != null)
			{
				paymentRow.resetDiscountAmount();
			}
		}

		// Make sure this row will not be excluded from future calculations
		// ... until the user will not manually change an amount
		row.setTaboo(false);

	}

	/**
	 * Called when user changed row's AppliedAmt
	 */
	private final void onRowAppliedAmtChanged(final IAllocableDocRow row)
	{
		// Because the user manually changed the AppliedAmt of this row,
		// we exclude this row from automatic calculations
		// because we want to keep the amounts as the user set them.
		row.setTaboo(true);

		//
		// Invoice: distribute the not applied amount to allowed write-off amounts.
		final IInvoiceRow invoiceRow = InvoiceRow.castOrNull(row);
		if (invoiceRow != null)
		{
			final PaymentAllocationContext context = createPaymentAllocationContext();
			invoiceRow.distributeNotAppliedAmtToWriteOffs(context);
		}
	}

	/**
	 * Called when user changed one of the write-off amounts
	 */
	private void onRowWriteOffAmtChanged(IInvoiceRow invoiceRow, InvoiceWriteOffAmountType writeOffType)
	{
		invoiceRow.setTaboo(true); // exclude the row from future automatic calculations

		final BigDecimal writeOffAmt = invoiceRow.getWriteOffAmtOfType(writeOffType);
		final PaymentAllocationContext context = createPaymentAllocationContext();
		invoiceRow.setWriteOffManual(context, writeOffType, writeOffAmt);

		// Notify the UI that we changed this row
		invoicesTableModel.fireTableRowsUpdated(Collections.singleton(invoiceRow));
	}

	/**
	 * Called when user changed discount amount
	 */
	private void onRowDiscountAmtChanged(final IPaymentRow paymentRow)
	{
		paymentRow.setTaboo(true); // exclude the row from future automatic calculations

		final BigDecimal discountAmt = paymentRow.getDiscountAmt();
		final PaymentAllocationContext context = createPaymentAllocationContext();
		paymentRow.setDiscountManual(context, discountAmt);

		// Notify the UI that we changed this row
		paymentsTableModel.fireTableRowsUpdated(Collections.singleton(paymentRow));
	}

	/**
	 * Called when user enabled/disabled one of the allow write-off flags.
	 *
	 * @param type write-off type of which status was changed
	 * @param allowed new status (true if allowed, false if not allowed)
	 */
	protected final void onAllowWriteOffFlagChanged(final InvoiceWriteOffAmountType type, final boolean allowed)
	{
		invoicesTableModel.setAllowWriteOffAmountOfType(type, allowed);
		paymentsTableModel.setAllowWriteOffAmountOfType(type, allowed);

		final int selectedInvoices = getInvoiceRowsSelected().size();

		// Only allow a DiscountAmt in payments if at least one invoice is selected.
		if (selectedInvoices > 0)
		{
			paymentsTableModel.setAllowWriteOffAmountOfType(type, allowed);
		}

		final PaymentAllocationContext context = createPaymentAllocationContext();

		//
		// Payment rows:
		// * Set DiscountAmt=0 if it does not apply anymore
		// * update AppliedAmt=Open-Discount if discount applies.
		final List<IPaymentRow> paymentRows = getPaymentRowsSelected();
		for (final IPaymentRow row : paymentRows)
		{
			row.recalculateDiscountAmount(context);
		}
		paymentsTableModel.fireTableRowsUpdated(paymentRows);

		//
		// Invoice rows: Recalculate the write-off amounts on each invoice row
		final List<IInvoiceRow> invoiceRows = getInvoiceRowsSelected();
		for (final IInvoiceRow row : invoiceRows)
		{
			row.recalculateWriteOffAmounts(context);
		}
		invoicesTableModel.fireTableRowsUpdated(invoiceRows);
	}

	/**
	 * @return true if given write-off type is allowed
	 */
	protected final boolean isAllowWriteOffAmountOfType(final InvoiceWriteOffAmountType type)
	{
		return invoicesTableModel.isAllowWriteOffAmountOfType(type);
	}

	protected final void setIsMultiCurrency(final boolean multiCurrency)
	{
		this._isMultiCurrency = multiCurrency;
		paymentsTableModel.setMultiCurrency(multiCurrency);
		invoicesTableModel.setMultiCurrency(multiCurrency);
	}

	protected final boolean isMultiCurrency()
	{
		return _isMultiCurrency;
	}

	@Override
	public final boolean setC_BPartner_ID(final int value)
	{
		final boolean changed = super.setC_BPartner_ID(value);
		clearForeignDocuments();

		return changed;
	}

	protected final boolean setFilter_Payment_ID(final int filterPaymentId)
	{
		final int filterPaymentIdOld = this._filterPaymentId;
		final int filterPaymentIdNew = filterPaymentId > 0 ? filterPaymentId : 0;
		if (filterPaymentIdOld == filterPaymentIdNew)
		{
			return false;
		}
		this._filterPaymentId = filterPaymentIdNew;
		return true;
	}

	protected final boolean setFilter_POReference(final String poReference)
	{
		final String filterPOReferenceOld = _filterPOReference;
		final String filterPOReferenceNew = Check.isEmpty(poReference, true) ? null : poReference.trim();

		if (Check.equals(filterPOReferenceOld, filterPOReferenceNew))
		{
			return false;
		}

		this._filterPOReference = filterPOReferenceNew;
		return true;
	}

	protected final PaymentsTableModel getPaymentsTableModel()
	{
		return paymentsTableModel;
	}

	protected final List<IPaymentRow> getPaymentRowsSelected()
	{
		return paymentsTableModel.getRowsSelected();
	}

	protected final List<IPaymentRow> getPaymentRowsSelectedNoTaboo()
	{
		return paymentsTableModel.getRowsSelectedNoTaboo();
	}

	/**
	 * @param requery true if we need to re-query the invoice and payment rows from database.
	 */
	protected final void updateAllocableDocRows(final boolean requery)
	{
		//
		// Retrieve the rows from database if asked.
		if (requery)
		{
			final PaymentAllocationContext context = createPaymentAllocationContext();

			final List<IPaymentRow> paymentRows = dao.retrievePaymentRows(context);
			paymentsTableModel.setRows(IAllocableDocRow.ORDERING_DocumentDate_DocumentNo.sortedCopy(paymentRows));

			final List<IInvoiceRow> invoiceRows = dao.retrieveInvoiceRows(context);
			invoicesTableModel.setRows(IAllocableDocRow.ORDERING_DocumentDate_DocumentNo.sortedCopy(invoiceRows));
		}

		//
		// Re-calculate the AppliedAmt on payments and invoices.
		// NOTE: we have to run this calculation only if we did not query the rows,
		// because if we just queried, it means nothing is selected atm and there is NO point to do this calculation.
		if (!requery)
		{
			distributeTotalDifferenceOnAppliedAmounts();
		}
	}

	protected final InvoicesTableModel getInvoicesTableModel()
	{
		return invoicesTableModel;
	}

	protected final List<IInvoiceRow> getInvoiceRowsSelected()
	{
		return invoicesTableModel.getRowsSelected();
	}

	protected final List<IInvoiceRow> getInvoiceRowsSelectedNoTaboo()
	{
		return invoicesTableModel.getRowsSelectedNoTaboo();
	}

	private PaymentAllocationContext.Builder newPaymentAllocationContextBuilder()
	{
		return PaymentAllocationContext.builder()
				.setAD_Org_ID(getAD_Org_ID())
				.setC_BPartner_ID(getC_BPartner_ID())
				.setC_Currency_ID(getC_Currency_ID())
				.setDate(getDate())
				.setFilter_Payment_ID(_filterPaymentId)
				.setFilter_POReference(_filterPOReference)
				.setDocumentIdsToIncludeWhenQuering(foreignDocumentIds)
				.setMultiCurrency(isMultiCurrency())
				.setAllowedWriteOffTypes(invoicesTableModel.getAllowedWriteOffTypes())
				.setWarningsConsumer(contextWarningsConsumer)
				//
				;
	}

	private PaymentAllocationContext createPaymentAllocationContext()
	{
		return newPaymentAllocationContextBuilder().build();
	}

	private final void distributeTotalDifferenceOnAppliedAmounts()
	{
		final PaymentAllocationContext context = createPaymentAllocationContext();

		//
		// Balance AppliedAmt by distributing the difference
		final DifferenceRowBalancer balancer = DifferenceRowBalancer.start();
		balancer.setContext(context)
				.setTotals(getTotalsSupplier())
				.setInvoiceRows(getInvoiceRowsSelected())
				.setPaymentRows(getPaymentRowsSelected())
				.balance();

		//
		// Notify table model listeners (the UI) that our that has been changed
		paymentsTableModel.fireTableRowsUpdated(balancer.getPaymentRowsChanged());
		invoicesTableModel.fireTableRowsUpdated(balancer.getInvoiceRowsChanged());
	}

	/**
	 * Returns the latest document date of all invoices and payments, or <code>null</code> if {@link #isMultiCurrency()} returns <code>true</code>.
	 */
	@Override
	protected final Date calculateAllocationDate()
	{
		if (isMultiCurrency())
		{
			return null;
		}

		final Date invoicesLatestDocumentDate = invoicesTableModel.getLatestDocumentDate();
		final Date payementsLatestDocumentDate = paymentsTableModel.getLatestDocumentDate();
		return TimeUtil.max(invoicesLatestDocumentDate, payementsLatestDocumentDate);
	}

	/**
	 * Returns the latest document date of all invoices and payments, or <code>null</code> if {@link #isMultiCurrency()} returns <code>true</code>.
	 */
	@Override
	protected final Date calculateDateAcct()
	{
		if (isMultiCurrency())
		{
			return null;
		}

		final Date invoicesLatestDocumentDate = invoicesTableModel.getLatestDateAcct();
		final Date payementsLatestDocumentDate = paymentsTableModel.getLatestDateAcct();
		return TimeUtil.max(invoicesLatestDocumentDate, payementsLatestDocumentDate);
	}

	@Override
	protected final PaymentAllocationTotals getTotals()
	{
		return getTotalsSupplier().get();
	}

	/**
	 * @return totals supplier which calculates the totals just when it is called
	 */
	private final Supplier<PaymentAllocationTotals> getTotalsSupplier()
	{
		return new Supplier<PaymentAllocationTotals>()
		{
			@Override
			public PaymentAllocationTotals get()
			{
				return PaymentAllocationTotals.builder()
						.setInvoicedAmt(invoicesTableModel.getTotalAppliedAmt())
						.setPaymentExistingAmt(paymentsTableModel.getTotalAppliedAmt())
						.setPaymentCandidatesAmt(totalPaymentCandidatesAmt)
						.build();
			}
		};
	}

	protected final void setTotalPaymentCandidatesAmt(final BigDecimal totalPaymentCandidatesAmt)
	{
		this.totalPaymentCandidatesAmt = totalPaymentCandidatesAmt;
	}
}
