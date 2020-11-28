package de.metas.banking.payment.paymentallocation.model;

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
