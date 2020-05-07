package org.compiere.apps.form.fileimport;

import org.compiere.impexp.CellErrorMessage;
import org.compiere.impexp.ImpDataLine;

abstract class TableColumnModel
{
	private final int columnIndex;

	public TableColumnModel(final int columnIndex)
	{
		super();
		this.columnIndex =columnIndex;
	}
	
	public int getColumnIndex()
	{
		return columnIndex;
	}

	/** @return column's display name (could be HTML) */
	public abstract String getColumnDisplayName();

	public abstract Class<?> getColumnClass();

	public boolean isCellEditable(ImpDataLine dataLine)
	{
		return false;
	}

	public abstract Object getValue(final ImpDataLine dataLine);

	public abstract void setValue(ImpDataLine dataLine, Object value);

	/** @return cell's error message or null */
	public CellErrorMessage getCellErrorMessage(ImpDataLine dataLine)
	{
		return null;
	}

	public int getWidth()
	{
		return 0;
	}
}
