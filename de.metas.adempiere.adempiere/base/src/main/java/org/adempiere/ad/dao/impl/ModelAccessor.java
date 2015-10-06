package org.adempiere.ad.dao.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.TypedAccessor;

import com.google.common.base.Optional;

public class ModelAccessor<T> implements TypedAccessor<T>
{
	private final String columnName;

	public ModelAccessor(String columnName)
	{
		this.columnName = columnName;
	}

	/**
	 * Note: might as well return <code>null</code>!
	 */
	@Override
	public T getValue(final Object model)
	{
		final Optional<T> value = InterfaceWrapperHelper.getValue(model, columnName);
		return value.orNull();
	}

	@Override
	public String toString()
	{
		return "ModelAccessor [columnName=" + columnName + "]";
	}
}
