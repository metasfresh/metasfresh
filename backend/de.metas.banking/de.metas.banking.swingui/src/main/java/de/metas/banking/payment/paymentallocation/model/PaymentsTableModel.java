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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PaymentsTableModel extends AbstractAllocableDocTableModel<IPaymentRow>
{
	private static final long serialVersionUID = 1L;

	// NOTE: using cast() is discouraged
	// public static final PaymentsTableModel cast(final TableModel model)
	// {
	// Check.assumeNotNull(model, "model not null");
	// return (PaymentsTableModel)model;
	// }

	public PaymentsTableModel()
	{
		super(IPaymentRow.class);

		setAllowWriteOffAmountOfType(InvoiceWriteOffAmountType.Discount, false);
	}

	/**
	 * DiscountAmt values are made editable if the Discount flag is set on true and made read-only if it is set on false
	 * 
	 * @param type
	 * @param allowed
	 */
	public final void setAllowWriteOffAmountOfType(final InvoiceWriteOffAmountType type, final boolean allowed)
	{
		if (type == InvoiceWriteOffAmountType.Discount)
		{
			// Update column's editable status
			final String columnName = IPaymentRow.PROPERTY_DiscountAmt;
			getTableColumnInfo(columnName).setEditable(allowed);
			fireTableColumnChanged(columnName);
		}
	}

	public void setMultiCurrency(final boolean multiCurrency)
	{
		getTableColumnInfo(IPaymentRow.PROPERTY_ConvertedAmt).setVisible(multiCurrency);
		getTableColumnInfo(IPaymentRow.PROPERTY_DocumentCurrency).setVisible(multiCurrency);
	}
}
