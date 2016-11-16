/**
 * 
 */
package de.metas.esb.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MColumn;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DisplayType;

import de.metas.esb.interfaces.I_EXP_Format;
import de.metas.esb.interfaces.I_EXP_FormatLine;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * @author tsa
 * 
 */
public class EXPFormatUpdateFromTable extends SvrProcess
{
	private int p_EXP_Format_ID = -1;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("EXP_Format_ID"))
				p_EXP_Format_ID = para.getParameterAsInt();
		}

		if (getTable_ID() == MTable.getTable_ID(I_EXP_Format.Table_Name))
		{
			p_EXP_Format_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_EXP_Format_ID <= 0)
			throw new FillMandatoryException("EXP_Format_ID");

		I_EXP_Format format = InterfaceWrapperHelper.create(new MEXPFormat(getCtx(), p_EXP_Format_ID, get_TrxName()), I_EXP_Format.class);
		MTable table = (MTable)format.getAD_Table();
		for (MColumn column : table.getColumns(true))
		{
			if (isColumnDefined(format, column))
				continue;
			I_EXP_FormatLine line = createFormatLine(format, column, -1, false);
			addLog("Added "+format.getValue()+" - "+line.getValue());
		}

		return null;
	}

	private boolean isColumnDefined(I_EXP_Format format, I_AD_Column column)
	{
		final String whereClause = I_EXP_FormatLine.COLUMNNAME_EXP_Format_ID + "=?"
				+ " AND " + I_EXP_FormatLine.COLUMNNAME_AD_Column_ID + "=?";
		return new Query(getCtx(), I_EXP_FormatLine.Table_Name, whereClause, get_TrxName())
				.setParameters(format.getEXP_Format_ID(), column.getAD_Column_ID())
				.match();
	}

	private I_EXP_FormatLine createFormatLine(I_EXP_Format format, I_AD_Column col, int position, boolean force) throws Exception
	{
		final I_EXP_FormatLine formatLine = InterfaceWrapperHelper.create(getCtx(), I_EXP_FormatLine.class, get_TrxName());

		formatLine.setAD_Org_ID(format.getAD_Org_ID());
		formatLine.setEXP_Format_ID(format.getEXP_Format_ID());
		formatLine.setAD_Column_ID(col.getAD_Column_ID());
		formatLine.setValue(col.getColumnName());
		formatLine.setName(col.getName());
		formatLine.setDescription(col.getDescription());
		formatLine.setHelp(col.getHelp());
		formatLine.setIsMandatory(col.isMandatory());
		if (!"D".equals(format.getEntityType()))
			formatLine.setEntityType(format.getEntityType());
		if (position > 0)
		{
			formatLine.setPosition(position);
		}
		else
		{
			final String whereClause = I_EXP_FormatLine.COLUMNNAME_EXP_Format_ID + "=?";
			int newPosition = new Query(getCtx(), I_EXP_FormatLine.Table_Name, whereClause, get_TrxName())
					.setParameters(format.getEXP_Format_ID())
					.aggregate(I_EXP_FormatLine.COLUMNNAME_Position, Query.AGGREGATE_MAX, Integer.class);
			newPosition += 10;
			formatLine.setPosition(newPosition);
		}

		if (force || (col.isIdentifier() && !col.isKey()))
		{
			formatLine.setIsPartUniqueIndex(true);
			formatLine.setIsActive(true);
		}
		else
		{
			formatLine.setIsActive(false);
		}

		if (DisplayType.isID(col.getAD_Reference_ID()))
		{
			formatLine.setType(X_EXP_FormatLine.TYPE_ReferencedEXPFormat);
		}
		else
		{
			formatLine.setType(X_EXP_FormatLine.TYPE_XMLElement);
		}
		InterfaceWrapperHelper.save(formatLine);
		return formatLine;
	}
}
