package de.metas.adempiere.service.impl;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

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


import org.slf4j.Logger;

import de.metas.adempiere.model.TableColumnPathException;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.IVariableParserBL;
import de.metas.logging.LogManager;

public class VariableParserBL implements IVariableParserBL
{
	private final Logger log = LogManager.getLogger(getClass());

	public VariableParserBL()
	{
		super();
	}

	@Override
	public Object resolveVariable(String variable, Object model, Object defaultValue)
	{
		if (InterfaceWrapperHelper.hasModelColumnName(model, variable))
		{
			return InterfaceWrapperHelper.getValue(model, variable).orNull();
		}

		// Check if is a TableColumnPath
		final ITableColumnPathBL tcpathBL = Services.get(ITableColumnPathBL.class);
		try
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(model);
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			final int id = InterfaceWrapperHelper.getId(model);
			return tcpathBL.getValueByPath(ctx, tableName, id, variable);
		}
		catch (TableColumnPathException e)
		{
			// it's not a table column path, just log the error
			if (LogManager.isLevelFine())
				log.debug(e.getLocalizedMessage(), e);
		}

		return defaultValue;
	} // translate

}
