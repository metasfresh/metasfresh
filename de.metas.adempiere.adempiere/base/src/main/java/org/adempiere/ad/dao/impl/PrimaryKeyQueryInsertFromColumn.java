package org.adempiere.ad.dao.impl;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class PrimaryKeyQueryInsertFromColumn implements IQueryInsertFromColumn
{
	private final String tableName;

	public PrimaryKeyQueryInsertFromColumn(final String tableName)
	{
		super();
		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;
	}

	@Override
	public String getSql(final List<Object> sqlParams)
	{
		return DB.TO_TABLESEQUENCE_NEXTVAL(tableName);
	}

	@Override
	public boolean update(Object toModel, String toColumnName, Object fromModel)
	{
		final int id = POJOLookupMap.get().nextId(toColumnName);
		InterfaceWrapperHelper.setValue(toModel, toColumnName, id);
		return true;
	}

}
