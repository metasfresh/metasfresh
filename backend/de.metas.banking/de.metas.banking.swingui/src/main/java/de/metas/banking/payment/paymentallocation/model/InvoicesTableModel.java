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


import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

public class InvoicesTableModel extends AbstractAllocableDocTableModel<IInvoiceRow>
{
	private static final long serialVersionUID = 1L;

	// NOTE: order is important because some BL wants to apply the types in the same order they were enabled!
	private final LinkedHashSet<InvoiceWriteOffAmountType> allowedWriteOffTypes = new LinkedHashSet<>();

	public InvoicesTableModel()
	{
		super(IInvoiceRow.class);

		// Initialize allowed write-off types
		for (final InvoiceWriteOffAmountType type : InvoiceWriteOffAmountType.values())
		{
			final boolean allowed = allowedWriteOffTypes.contains(type);
			setAllowWriteOffAmountOfType(type, allowed);
		}
	}

	public final void setAllowWriteOffAmountOfType(final InvoiceWriteOffAmountType type, final boolean allowed)
	{
		Check.assumeNotNull(type, "type not null");

		//
		// Update the internal set
		if (allowed)
		{
			allowedWriteOffTypes.add(type);
		}
		else
		{
			allowedWriteOffTypes.remove(type);
		}

		//
		// Update column's editable status
		final String columnName = type.columnName();
		getTableColumnInfo(columnName).setEditable(allowed);
		fireTableColumnChanged(columnName);
	}

	public final boolean isAllowWriteOffAmountOfType(final InvoiceWriteOffAmountType type)
	{
		return allowedWriteOffTypes.contains(type);
	}

	/**
	 * @return allowed write-off types, in the same order they were enabled
	 */
	public final Set<InvoiceWriteOffAmountType> getAllowedWriteOffTypes()
	{
		return ImmutableSet.copyOf(allowedWriteOffTypes);
	}

	public void setMultiCurrency(final boolean multiCurrency)
	{
		getTableColumnInfo(IInvoiceRow.PROPERTY_ConvertedAmt).setVisible(multiCurrency);
		getTableColumnInfo(IInvoiceRow.PROPERTY_DocumentCurrency).setVisible(multiCurrency);
	}
}
