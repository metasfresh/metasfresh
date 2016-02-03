package org.compiere.apps.form.fileimport;

import org.compiere.impexp.ImpDataLine;

class RawDataTableColumnModel extends TableColumnModel
{

	public RawDataTableColumnModel(int columnIndex)
	{
		super(columnIndex);
	}

	@Override
	public String getColumnDisplayName()
	{
		return "Raw line";
	}

	@Override
	public Class<?> getColumnClass()
	{
		return String.class;
	}

	@Override
	public Object getValue(ImpDataLine dataLine)
	{
		return dataLine.getLineString();
	}

	@Override
	public void setValue(ImpDataLine dataLine, Object value)
	{
	}

}
