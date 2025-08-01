package de.metas.banking.payment.paymentallocation.model;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

public class InvoicesTableModel extends AbstractAllocableDocTableModel<IInvoiceRow>
{
	@Serial
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
