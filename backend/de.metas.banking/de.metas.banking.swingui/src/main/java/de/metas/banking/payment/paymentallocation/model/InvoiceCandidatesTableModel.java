package de.metas.banking.payment.paymentallocation.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.compiere.swing.table.AnnotatedTableModel;
import org.compiere.util.TimeUtil;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class InvoiceCandidatesTableModel extends AnnotatedTableModel<IInvoiceCandidateRow>
{
	@Serial
	private static final long serialVersionUID = 1L;

	private static final Predicate<IInvoiceCandidateRow> PREDICATE_Selected = new Predicate<IInvoiceCandidateRow>()
	{

		@Override
		public boolean apply(IInvoiceCandidateRow row)
		{
			return row.isSelected();
		}
	};

	public InvoiceCandidatesTableModel()
	{
		super(IInvoiceCandidateRow.class);
	}

	public final List<IInvoiceCandidateRow> getRowsSelected()
	{
		return FluentIterable.from(getRowsInnerList())
				.filter(PREDICATE_Selected)
				.toList();
	}

	public final Set<Integer> getSelectedInvoiceCandidateIds()
	{
		final Set<Integer> invoiceCandidateIds = new HashSet<>();

		for (final IInvoiceCandidateRow row : getRowsSelected())
		{
			final int invoiceCandidateId = row.getC_Invoice_Candidate_ID();
			if (invoiceCandidateId <= 0)
			{
				continue;
			}

			invoiceCandidateIds.add(invoiceCandidateId);
		}

		return invoiceCandidateIds;
	}

	public final void selectRowsByInvoiceCandidateIds(final Collection<Integer> invoiceCandidateIds)
	{
		if (invoiceCandidateIds == null || invoiceCandidateIds.isEmpty())
		{
			return;
		}

		final List<IInvoiceCandidateRow> rowsChanged = new ArrayList<>();
		for (final IInvoiceCandidateRow row : getRowsInnerList())
		{
			// Skip if already selected
			if (row.isSelected())
			{
				continue;
			}

			if (invoiceCandidateIds.contains(row.getC_Invoice_Candidate_ID()))
			{
				row.setSelected(true);
				rowsChanged.add(row);
			}
		}

		fireTableRowsUpdated(rowsChanged);
	}

	/** @return latest {@link IInvoiceCandidateRow#getDocumentDate()} of selected rows */
	public final Date getLatestDocumentDateOfSelectedRows()
	{
		Date latestDocumentDate = null;

		for (final IInvoiceCandidateRow row : getRowsSelected())
		{
			final Date documentDate = row.getDocumentDate();
			latestDocumentDate = TimeUtil.max(latestDocumentDate, documentDate);
		}

		return latestDocumentDate;
	}

	/**
	 * @return latest {@link IInvoiceCandidateRow#getDateAcct()} of selected rows
	 */
	public final Date getLatestDateAcctOfSelectedRows()
	{
		Date latestDateAcct = null;

		for (final IInvoiceCandidateRow row : getRowsSelected())
		{
			final Date dateAcct = row.getDateAcct();
			latestDateAcct = TimeUtil.max(latestDateAcct, dateAcct);
		}

		return latestDateAcct;
	}

	public final BigDecimal getTotalNetAmtToInvoiceOfSelectedRows()
	{
		BigDecimal totalNetAmtToInvoiced = BigDecimal.ZERO;
		for (final IInvoiceCandidateRow row : getRowsSelected())
		{
			final BigDecimal netAmtToInvoice = row.getNetAmtToInvoice();
			totalNetAmtToInvoiced = totalNetAmtToInvoiced.add(netAmtToInvoice);
		}
		return totalNetAmtToInvoiced;
	}

}
