package org.adempiere.ad.wrapper;

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


import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.model.POWrapper;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

import de.metas.util.Check;

/**
 * Implementation of {@link IModelInternalAccessor} which directly wraps a given {@link PO}.
 *
 * @author tsa
 *
 */
public class POModelInternalAccessor implements IModelInternalAccessor
{
	private final PO po;

	public POModelInternalAccessor(final PO po)
	{
		super();

		Check.assumeNotNull(po, "po not null");
		this.po = po;
	}

	private final POInfo getPOInfo()
	{
		return po.getPOInfo();
	}

	@Override
	public Set<String> getColumnNames()
	{
		return getPOInfo().getColumnNames();
	}

	@Override
	public int getColumnIndex(final String propertyName)
	{
		return getPOInfo().getColumnIndex(propertyName);
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		return getPOInfo().isVirtualColumn(columnName);
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		return getPOInfo().isKey(columnName);
	}

	@Override
	public Object getValue(final String propertyName, final int idx, final Class<?> returnType)
	{
		return po.get_Value(idx);
	}

	@Override
	public Object getValue(final String propertyName, final Class<?> returnType)
	{
		return po.get_Value(propertyName);
	}

	@Override
	public boolean setValue(final String columnName, final Object value)
	{
		final Object valueToSet = POWrapper.checkZeroIdValue(columnName, value);

		final POInfo poInfo = po.getPOInfo();
		if (!poInfo.isColumnUpdateable(columnName))
		{
			// If the column is not updateable we need to use set_ValueNoCheck
			// because else is not consistent with how the generated classes were created
			// see org.adempiere.util.ModelClassGenerator.createColumnMethods
			return po.set_ValueNoCheck(columnName, valueToSet);
		}
		else
		{
			return po.set_ValueOfColumn(columnName, valueToSet);
		}
	}

	@Override
	public boolean setValueNoCheck(String propertyName, Object value)
	{
		return po.set_ValueOfColumn(propertyName, value);
	}

	@Override
	public Object getReferencedObject(final String propertyName, final Method interfaceMethod) throws Exception
	{
		final Class<?> returnType = interfaceMethod.getReturnType();
		return po.get_ValueAsPO(propertyName, returnType);
	}

	@Override
	public void setValueFromPO(final String idPropertyName, final Class<?> parameterType, final Object value)
	{
		po.set_ValueFromPO(idPropertyName, parameterType, value);
	}

	@Override
	public boolean invokeEquals(final Object[] methodArgs)
	{
		if (methodArgs == null || methodArgs.length != 1)
		{
			throw new IllegalArgumentException("Invalid method arguments to be used for equals(): " + methodArgs);
		}
		return po.equals(methodArgs[0]);
	}

	@Override
	public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
	{
		return method.invoke(po, methodArgs);
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		return getPOInfo().isCalculated(columnName);
	}

	@Override
	public boolean hasColumnName(final String columnName)
	{
		return getPOInfo().hasColumnName(columnName);
	}
}
