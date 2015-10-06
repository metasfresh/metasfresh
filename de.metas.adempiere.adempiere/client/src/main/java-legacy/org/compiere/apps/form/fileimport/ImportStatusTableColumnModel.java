package org.compiere.apps.form.fileimport;

import org.compiere.impexp.ImpDataLine;

class ImportStatusTableColumnModel extends TableColumnModel
{

	public ImportStatusTableColumnModel(int columnIndex)
	{
		super(columnIndex);
	}

	@Override
	public String getColumnName()
	{
		return "Status";
	}

	@Override
	public Class<?> getColumnClass()
	{
		return String.class;
	}

	@Override
	public Object getValue(ImpDataLine dataLine)
	{
		return dataLine.getImportStatus().toString();
	}

	@Override
	public void setValue(ImpDataLine dataLine, Object value)
	{
	}

}
