package org.compiere.apps.form.fileimport;

import org.adempiere.util.Check;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormatRow;

class ImpDataLineRowTableColumnModel extends TableColumnModel
{
	private final ImpFormatRow impFormatRow;
	private final int impFormatRowIdx;

	public ImpDataLineRowTableColumnModel(final ImpFormatRow impFormatRow, final int impFormatRowIdx, final int columnIndex)
	{
		super(columnIndex);
		this.impFormatRow = impFormatRow;
		this.impFormatRowIdx = impFormatRowIdx;
	}

	@Override
	public String getColumnName()
	{
		return impFormatRow.getName();
	}

	@Override
	public Class<?> getColumnClass()
	{
		return String.class;
	}

	@Override
	public boolean isCellEditable(final ImpDataLine dataLine)
	{
		return true;
	}

	@Override
	public Object getValue(final ImpDataLine dataLine)
	{
		return dataLine.getValueOrNull(impFormatRowIdx);
	}

	@Override
	public void setValue(ImpDataLine dataLine, Object value)
	{
		final Object valueOld = getValue(dataLine);
		dataLine.setValue(impFormatRowIdx, value);
		
		if (!Check.equals(valueOld, value))
		{
			dataLine.setToImport(true);
		}
	}
}
