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


import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

/** Invoice write-off amount type */
public enum InvoiceWriteOffAmountType
{
	/** 1. Over/Under amount */
	OverUnder(IInvoiceRow.PROPERTY_OverUnderAmt, "PaymentAllocationForm.allowOverUnderPayment"),
	/** 2. Discount amount */
	Discount(IInvoiceRow.PROPERTY_Discount, "PaymentAllocationForm.allowCashDiscount"),
	/** 3. Actual WriteOff amount */
	WriteOff(IInvoiceRow.PROPERTY_WriteOffAmt, "PaymentAllocationForm.allowWriteOff"),
	//
	;

	private final String _columnName;
	private final String _adMessage;

	InvoiceWriteOffAmountType(final String columnName, final String adMessage)
	{
		this._columnName = columnName;
		this._adMessage = adMessage;
	}

	public String columnName()
	{
		return this._columnName;
	}

	public String getAD_Message()
	{
		return this._adMessage;
	}

	/** @return column names, in the same order as the enum constants are defined in this class */
	public static Set<String> columnNames()
	{
		return getColumnName2typesMap().keySet();
	}

	public static final InvoiceWriteOffAmountType valueOfColumnNameOrNull(final String columnName)
	{
		final InvoiceWriteOffAmountType type = getColumnName2typesMap().get(columnName);
		return type;
	}

	public static final InvoiceWriteOffAmountType valueOfColumnName(final String columnName)
	{
		final InvoiceWriteOffAmountType type = getColumnName2typesMap().get(columnName);
		if (type == null)
		{
			throw new IllegalArgumentException("No type for " + columnName);
		}
		return type;
	}

	private static Map<String, InvoiceWriteOffAmountType> _columnName2types;

	private static final Map<String, InvoiceWriteOffAmountType> getColumnName2typesMap()
	{
		if (_columnName2types == null)
		{
			// NOTE: we preserve the same order as the values() are.
			final ImmutableMap.Builder<String, InvoiceWriteOffAmountType> columnName2types = ImmutableMap.builder();
			for (final InvoiceWriteOffAmountType type : values())
			{
				final String columnName = type.columnName();
				columnName2types.put(columnName, type);
			}

			_columnName2types = columnName2types.build();
		}
		return _columnName2types;
	}
}
