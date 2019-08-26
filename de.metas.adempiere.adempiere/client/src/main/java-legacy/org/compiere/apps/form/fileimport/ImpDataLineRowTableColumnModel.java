package org.compiere.apps.form.fileimport;

import java.util.Objects;

import org.compiere.impexp.CellErrorMessage;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormatColumn;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;

class ImpDataLineRowTableColumnModel extends TableColumnModel
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ImpFormatColumn impFormatColumn;
	private final int impFormatColumnIdx;

	public ImpDataLineRowTableColumnModel(final ImpFormatColumn impFormatColumn, final int impFormatColumnIdx, final int columnIndex)
	{
		super(columnIndex);
		this.impFormatColumn = impFormatColumn;
		this.impFormatColumnIdx = impFormatColumnIdx;
	}

	@Override
	public String getColumnDisplayName()
	{
		final StringBuilder displayName = new StringBuilder();
		displayName.append("<html>");

		// Name
		{
			final String name = impFormatColumn.getName();
			displayName.append("<b>").append(StringUtils.maskHTML(name)).append("</b>");
		}

		// Data format
		if (impFormatColumn.isDate() || impFormatColumn.isNumber())
		{
			final String dataFormat = impFormatColumn.getDataFormat();
			if (!Check.isEmpty(dataFormat))
			{
				displayName.append("<br>").append(StringUtils.maskHTML(dataFormat));
			}
		}

		// Decimal separator
		if (impFormatColumn.isNumber())
		{
			final String decimalPointName = msgBL.translate(Env.getCtx(), I_AD_ImpFormat_Row.COLUMNNAME_DecimalPoint);
			final String decimalPoint = impFormatColumn.getDecimalSeparator().getSymbol();
			displayName.append("<br>").append(StringUtils.maskHTML(decimalPointName)).append(": ").append(StringUtils.maskHTML(decimalPoint));
		}

		// Divide by 100
		if (impFormatColumn.isNumber() && impFormatColumn.isDivideBy100())
		{
			final String divideBy100 = msgBL.translate(Env.getCtx(), I_AD_ImpFormat_Row.COLUMNNAME_DivideBy100);
			displayName.append("<br>").append(StringUtils.maskHTML(divideBy100));
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
		return dataLine.getValueOrNull(impFormatColumnIdx);
	}

	@Override
	public void setValue(final ImpDataLine dataLine, final Object value)
	{
		final Object valueOld = getValue(dataLine);
		final boolean flagToImport = !Objects.equals(valueOld, value);
		dataLine.setValue(impFormatColumnIdx, value, flagToImport);
	}

	@Override
	public CellErrorMessage getCellErrorMessage(ImpDataLine dataLine)
	{
		return dataLine.getCellErrorMessage(impFormatColumnIdx);
	}
}
