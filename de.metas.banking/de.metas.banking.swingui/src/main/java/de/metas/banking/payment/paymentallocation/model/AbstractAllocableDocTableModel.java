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


import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.swing.table.AnnotatedTableModel;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.FluentIterable;

@VisibleForTesting
public class AbstractAllocableDocTableModel<ModelType extends IAllocableDocRow> extends AnnotatedTableModel<ModelType>
{
	private static final long serialVersionUID = 1L;

	public AbstractAllocableDocTableModel(final Class<ModelType> modelClass)
	{
		super(modelClass);
	}

	/**
	 * @return true (editable) if {@link IAllocableDocRow#isSelected()}
	 */
	@Override
	protected boolean isCellEditableExt(final int modelRowIndex, final int modelColumnIndex)
	{
		// Consider the "Selected" checkbox column as always editable.
		final int selectedColumnIndex = getColumnIndexByColumnName(IAllocableDocRow.PROPERTY_Selected);
		if (modelColumnIndex == selectedColumnIndex)
		{
			return true;
		}

		// If the row is not selected, don't allow user to edit it.
		final ModelType row = getRow(modelRowIndex);
		if (!row.isSelected())
		{
			return false;
		}

		return true;
	}

	/**
	 * @return "red" if {@link IAllocableDocRow#getMultiplierAP()} is negative
	 */
	@Override
	protected Color getCellForegroundColor(final int modelRowIndex, final int modelColumnIndex)
	{
		final ModelType row = getRow(modelRowIndex);
		BigDecimal multiplier = row.getMultiplierAP();
		if (row.isCreditMemo())
		{
			multiplier = multiplier.negate();
		}

		if (multiplier.signum() < 0)
		{
			return AdempierePLAF.getTextColor_Issue();		// Red
		}

		return null;
	}

	/** @return sum of {@link IAllocableDocRow#getAppliedAmt()} (AP Adjusted) of all selected rows */
	public final BigDecimal getTotalAppliedAmt()
	{
		return calculateTotalAppliedAmt(getRowsInnerList());
	}

	@VisibleForTesting
	public static final BigDecimal calculateTotalAppliedAmt(final Iterable<? extends IAllocableDocRow> rows)
	{
		BigDecimal totalAppliedAmt = BigDecimal.ZERO;

		for (final IAllocableDocRow row : rows)
		{
			if (!row.isSelected())
			{
				continue;
			}

			final BigDecimal rowAppliedAmt = row.getAppliedAmt_APAdjusted();
			totalAppliedAmt = totalAppliedAmt.add(rowAppliedAmt);
		}
		
		return totalAppliedAmt;
	}

	/** @return latest {@link IAllocableDocRow#getDocumentDate()} of all selected rows */
	public final Date getLatestDocumentDate()
	{
		Date latestDocumentDate = null;
		for (final IAllocableDocRow row : getRowsInnerList())
		{
			if (!row.isSelected())
			{
				continue;
			}

			final Date documentDate = row.getDocumentDate();
			latestDocumentDate = TimeUtil.max(latestDocumentDate, documentDate);
		}

		return latestDocumentDate;
	}
	
	/** @return latest {@link IAllocableDocRow#getDateAcct()} of all selected rows */
	public final Date getLatestDateAcct()
	{
		Date latestDateAcct = null;
		for (final IAllocableDocRow row : getRowsInnerList())
		{
			if (!row.isSelected())
			{
				continue;
			}

			final Date dateAcct = row.getDateAcct();
			latestDateAcct = TimeUtil.max(latestDateAcct, dateAcct);
		}

		return latestDateAcct;
	}

	public final List<ModelType> getRowsSelected()
	{
		return FluentIterable.from(getRowsInnerList())
				.filter(IAllocableDocRow.PREDICATE_Selected)
				.toList();
	}
	
	public final List<ModelType> getRowsSelectedNoTaboo()
	{
		return FluentIterable.from(getRowsInnerList())
				.filter(IAllocableDocRow.PREDICATE_Selected)
				.filter(IAllocableDocRow.PREDICATE_NoTaboo)
				.toList();
	}

}
