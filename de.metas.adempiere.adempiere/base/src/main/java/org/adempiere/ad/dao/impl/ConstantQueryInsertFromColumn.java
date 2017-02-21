package org.adempiere.ad.dao.impl;

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


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.DB;

/**
 * Set the column in target model to a constant value.
 * 
 * @author tsa
 *
 */
class ConstantQueryInsertFromColumn implements IQueryInsertFromColumn
{
	private final Object constantValue;

	public ConstantQueryInsertFromColumn(final Object constantValue)
	{
		super();
		this.constantValue = constantValue;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getSql(List<Object> sqlParams)
	{
		// Case: we are not collecting parameters => render parameter inside the SQL query
		if (sqlParams == null)
		{
			return DB.TO_SQL(constantValue);
		}
		
		sqlParams.add(constantValue);
		return "?";
	}

	@Override
	public boolean update(final Object toModel, String toColumnName, final Object fromModel)
	{
		InterfaceWrapperHelper.setValue(toModel, toColumnName, constantValue);
		return true;
	}

}
