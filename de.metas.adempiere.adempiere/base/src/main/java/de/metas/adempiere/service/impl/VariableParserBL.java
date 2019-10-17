package de.metas.adempiere.service.impl;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;

import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.IVariableParserBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;

public class VariableParserBL implements IVariableParserBL
{
	private static final Logger logger = LogManager.getLogger(VariableParserBL.class);

	@Override
	public Object resolveVariable(final String variable, final Object model, final Object defaultValue)
	{
		if (InterfaceWrapperHelper.hasModelColumnName(model, variable))
		{
			return InterfaceWrapperHelper.getValue(model, variable).orElse(null);
		}

		// Check if is a TableColumnPath
		return resolveVariableByParsingColumnPath(variable, model, defaultValue);

	} // translate

	private Object resolveVariableByParsingColumnPath(final String variable, final Object model, final Object defaultValue)
	{
		// NOTE: column path parsing is not supported in unit tests mode
		if (Adempiere.isUnitTestMode())
		{
			logger.warn("Parsing column path variables is not supported in JUnit test mode. Returning default value: {}", defaultValue);
			return defaultValue;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int id = InterfaceWrapperHelper.getId(model);

		final ITableColumnPathBL tcpathBL = Services.get(ITableColumnPathBL.class);
		try
		{
			return tcpathBL.getValueByPath(ctx, tableName, id, variable);
		}
		catch (final Exception ex)
		{
			// it's not a table column path, just log the error
			logger.debug("Failed parsing", ex);
			return defaultValue;
		}
	}

}
