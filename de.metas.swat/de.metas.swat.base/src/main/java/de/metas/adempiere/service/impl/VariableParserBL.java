package de.metas.adempiere.service.impl;

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


import java.util.logging.Level;

import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.TableColumnPathException;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.IVariableParserBL;

public class VariableParserBL implements IVariableParserBL
{
	private final CLogger log = CLogger.getCLogger(getClass());

	public VariableParserBL()
	{
		super();
	}

	@Override
	public Object resolveVariable(String variable, PO po, Object defaultValue)
	{
		int index = po.get_ColumnIndex(variable);
		if (index >= 0)
			return po.get_Value(index);

		// Check if is a TableColumnPath
		ITableColumnPathBL tcpathBL = Services.get(ITableColumnPathBL.class);
		try
		{
			return tcpathBL.getValueByPath(po.getCtx(), po.get_TableName(), po.get_ID(), variable);
		}
		catch (TableColumnPathException e)
		{
			// it's not a table column path, just log the error
			if (CLogMgt.isLevelFine())
				log.log(Level.FINE, e.getLocalizedMessage(), e);
		}

		return defaultValue;
	} // translate

}
