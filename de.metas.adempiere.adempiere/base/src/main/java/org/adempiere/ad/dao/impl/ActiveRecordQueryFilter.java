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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;

public class ActiveRecordQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	public static <T> IQueryFilter<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		final ActiveRecordQueryFilter<T> instanceCasted = instance;
		return instanceCasted;
	}

	public static <T> IQueryFilter<T> getInstance(final Class<T> clazz)
	{
		return getInstance();
	}

	@SuppressWarnings("rawtypes")
	private static final ActiveRecordQueryFilter instance = new ActiveRecordQueryFilter();

	private static final String COLUMNNAME_IsActive = "IsActive";

	private final String sql;
	private final List<Object> sqlParams;

	private ActiveRecordQueryFilter()
	{
		this.sql = COLUMNNAME_IsActive + "=?";
		this.sqlParams = Arrays.asList((Object)true);
	}

	@Override
	public String toString()
	{
		return "Active";
	}

	@Override
	public String getSql()
	{
		return sql;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return sqlParams;
	}

	@Override
	public boolean accept(final T model)
	{
		final Object isActiveObj = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_IsActive);
		final boolean isActive = DisplayType.toBoolean(isActiveObj);
		return isActive;
	}

}
