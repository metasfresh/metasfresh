package org.adempiere.ad.validationRule.impl;

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

import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.validationRule.IValidationContext;

/**
 * Simple validation context. Can be used for testing.
 * 
 *
 */
public class PlainValidationContext implements IValidationContext
{
	private String contextTableName;
	private String tableName;
	private final Map<String, String> values = new HashMap<String, String>();

	@Override
	public String get_ValueAsString(String variableName)
	{
		return values.get(variableName);
	}

	public void setValue(String variableName, String value)
	{
		values.put(variableName, value);
	}

	public void setContextTableName(String contextTableName)
	{
		values.put(PARAMETER_ContextTableName, contextTableName);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	@Override
	public String toString()
	{
		return String.format("PlainValidationContext [contextTableName=%s, tableName=%s, values=%s]", contextTableName, tableName, values);
	}

}
