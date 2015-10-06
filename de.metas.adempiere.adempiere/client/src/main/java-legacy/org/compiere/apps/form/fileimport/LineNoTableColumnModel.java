package org.compiere.apps.form.fileimport;

import org.compiere.impexp.ImpDataLine;

class LineNoTableColumnModel extends TableColumnModel
{

	public LineNoTableColumnModel(final int columnIndex)
	{
		super(columnIndex);
	}

	@Override
	public String getColumnName()
	{
		return "Line";
	}

	@Override
	public Class<?> getColumnClass()
	{
		return Integer.class;
	}
	
	@Override
	public int getWidth()
	{
		return 50;
	}

	@Override
	public Object getValue(final ImpDataLine dataLine)
	{
		return dataLine.getFileLineNo();
	}

	@Override
	public void setValue(final ImpDataLine dataLine, final Object value)
	{
	}

}
