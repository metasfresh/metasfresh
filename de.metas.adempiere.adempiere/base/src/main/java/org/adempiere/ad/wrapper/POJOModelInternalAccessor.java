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
import org.adempiere.exceptions.AdempiereException;

/**
 * Implementation of {@link IModelInternalAccessor} which wraps a {@link POJOWrapper}.
 * 
 * @author tsa
 *
 */
class POJOModelInternalAccessor implements IModelInternalAccessor
{
	private final POJOWrapper pojoWrapper;

	POJOModelInternalAccessor(final POJOWrapper pojoWrapper)
	{
		super();
		this.pojoWrapper = pojoWrapper;
	}

	@Override
	public Set<String> getColumnNames()
	{
		return pojoWrapper.getColumnNames();
	}

	@Override
	public int getColumnIndex(final String propertyName)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("POJOWrapper has no supported for column indexes");
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		return pojoWrapper.isCalculated(columnName);
	}

	@Override
	public Object getValue(final String propertyName, final int idx, final Class<?> returnType)
	{
		return getValue(propertyName, returnType);
	}

	@Override
	public Object getValue(final String propertyName, final Class<?> returnType)
	{
		return pojoWrapper.getValue(propertyName, returnType);
	}

	@Override
	public boolean setValue(final String propertyName, final Object value)
	{
		pojoWrapper.setValue(propertyName, value);
		return true;
	}

	@Override
	public boolean setValueNoCheck(String columnName, Object value)
	{
		pojoWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public Object getReferencedObject(final String propertyName, final Method interfaceMethod) throws Exception
	{
		return pojoWrapper.getReferencedObject(propertyName, interfaceMethod);
	}

	@Override
	public void setValueFromPO(final String idPropertyName, final Class<?> parameterType, final Object value)
	{
		final String propertyName;
		if (idPropertyName.endsWith("_ID"))
		{
			propertyName = idPropertyName.substring(0, idPropertyName.length() - 3);
		}
		else
		{
			throw new AdempiereException("Invalid idPropertyName: " + idPropertyName);
		}
		pojoWrapper.setReferencedObject(propertyName, value);
	}

	@Override
	public boolean invokeEquals(final Object[] methodArgs)
	{
		return pojoWrapper.invokeEquals(methodArgs);
	}

	@Override
	public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
	{
		throw new IllegalStateException("Invoking parent method is not supported");
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		return pojoWrapper.isKeyColumnName(columnName);
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		return pojoWrapper.isCalculated(columnName);
	}

	@Override
	public boolean hasColumnName(String columnName)
	{
		return pojoWrapper.hasColumnName(columnName);
	}
}
