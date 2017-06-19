package org.compiere.apps.form.fileimport;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.impexp.CellErrorMessage;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormatRow;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.i18n.IMsgBL;

class ImpDataLineRowTableColumnModel extends TableColumnModel
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	
	private final ImpFormatRow impFormatRow;
	private final int impFormatRowIdx;

	public ImpDataLineRowTableColumnModel(final ImpFormatRow impFormatRow, final int impFormatRowIdx, final int columnIndex)
	{
		super(columnIndex);
		this.impFormatRow = impFormatRow;
		this.impFormatRowIdx = impFormatRowIdx;
	}

	@Override
	public String getColumnDisplayName()
	{
		final StringBuilder displayName = new StringBuilder();
		displayName.append("<html>");

		// Name
		{
			final String name = impFormatRow.getName();
			displayName.append("<b>").append(Util.maskHTML(name)).append("</b>");
		}

		// Data format
		if (impFormatRow.isDate() || impFormatRow.isNumber())
		{
			final String dataFormat = impFormatRow.getDataFormat();
			if (!Check.isEmpty(dataFormat))
			{
				displayName.append("<br>").append(Util.maskHTML(dataFormat));
			}
		}

		// Decimal separator
		if (impFormatRow.isNumber())
		{
			final String decimalPointName = msgBL.translate(Env.getCtx(), I_AD_ImpFormat_Row.COLUMNNAME_DecimalPoint);
			final String decimalPoint = impFormatRow.getDecimalPoint();
			displayName.append("<br>").append(Util.maskHTML(decimalPointName)).append(": ").append(Util.maskHTML(decimalPoint));
		}
		
		// Divide by 100
		if (impFormatRow.isNumber() && impFormatRow.isDivideBy100())
		{
			final String divideBy100 = msgBL.translate(Env.getCtx(), I_AD_ImpFormat_Row.COLUMNNAME_DivideBy100);
			displayName.append("<br>").append(Util.maskHTML(divideBy100));
		}
		
		displayName.append("</html>");
		
		return displayName.toString();
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
	public void setValue(final ImpDataLine dataLine, final Object value)
	{
		final Object valueOld = getValue(dataLine);
		final boolean flagToImport = !Check.equals(valueOld, value);
		dataLine.setValue(impFormatRowIdx, value, flagToImport);
	}

	@Override
	public CellErrorMessage getCellErrorMessage(ImpDataLine dataLine)
	{
		return dataLine.getCellErrorMessage(impFormatRowIdx);
	}
}
