/**
 * 
 */
package de.metas.esb.callout;

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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DisplayType;

import de.metas.esb.interfaces.I_EXP_FormatLine;

/**
 * @author tsa
 *
 */
public class CalloutEXP_FormatLine extends CalloutEngine
{
	public String column(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_EXP_FormatLine formatLine = InterfaceWrapperHelper.create(mTab, I_EXP_FormatLine.class);
		I_AD_Column column = formatLine.getAD_Column();
		if (column == null || column.getAD_Column_ID() <= 0)
			return "";
		
		formatLine.setValue(column.getColumnName());
		formatLine.setName(column.getColumnName());
		formatLine.setAD_Reference_ID(column.getAD_Reference_ID());
		if (column.isMandatory())
			formatLine.setIsMandatory(true);
		if (column.isIdentifier() || column.isParent() || column.isKey())
		{
			formatLine.setIsMandatory(true);
			formatLine.setIsPartUniqueIndex(true);
		}
		if (!"D".equals(column.getEntityType()))
		{
			formatLine.setEntityType(column.getEntityType());
		}
		
		if (DisplayType.isLookup(column.getAD_Reference_ID()))
		{
			formatLine.setType(X_EXP_FormatLine.TYPE_ReferencedEXPFormat);
		}
		
		return "";
	}

}
