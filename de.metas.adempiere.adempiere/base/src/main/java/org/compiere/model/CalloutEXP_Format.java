package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;

public class CalloutEXP_Format extends CalloutEngine
{
	public String onAD_Table_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_EXP_Format format = InterfaceWrapperHelper.create(mTab, I_EXP_Format.class);
		if (format.getAD_Table_ID() > 0)
		{
			String tableName = MTable.getTableName(ctx, format.getAD_Table_ID());
			format.setValue(tableName);
			format.setName(tableName);
		}
		return "";
	}

	public String setLineValueName(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_EXP_FormatLine line = InterfaceWrapperHelper.create(mTab, I_EXP_FormatLine.class);
		if (line.getEXP_EmbeddedFormat_ID() > 0
				&& X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()))
		{
			I_EXP_Format format = line.getEXP_EmbeddedFormat();
			line.setValue(format.getValue());
			line.setName(format.getName());
		}
		else if (line.getAD_Column_ID() > 0)
		{
			MColumn column = MColumn.get(ctx, line.getAD_Column_ID());
			String columnName = column.getColumnName();
			line.setValue(columnName);
			line.setName(columnName);
			if (column.isMandatory())
				line.setIsMandatory(true);
		}
		return "";
	}
	
}
