package org.compiere.apps.form.fileimport;

import org.compiere.impexp.ImpDataLine;

class ImportErrorTableColumnModel extends TableColumnModel
{
	public ImportErrorTableColumnModel(int columnIndex)
	{
		super(columnIndex);
	}

	@Override
	public String getColumnDisplayName()
	{
		return "ErrMsg";
	}

	@Override
	public Class<?> getColumnClass()
	{
		return String.class;
	}

	@Override
	public Object getValue(ImpDataLine dataLine)
	{
		return dataLine.getImportErrorMessage();
	}

	@Override
	public void setValue(ImpDataLine dataLine, Object value)
	{
	}

}
