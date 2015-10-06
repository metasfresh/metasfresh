package org.compiere.apps.form.fileimport;

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

	public abstract String getColumnName();

	public abstract Class<?> getColumnClass();

	public boolean isCellEditable(ImpDataLine dataLine)
	{
		return false;
	}

	public abstract Object getValue(final ImpDataLine dataLine);

	public abstract void setValue(ImpDataLine dataLine, Object value);

	public int getWidth()
	{
		return 0;
	}
}
