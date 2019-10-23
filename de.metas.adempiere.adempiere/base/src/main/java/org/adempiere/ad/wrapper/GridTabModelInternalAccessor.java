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
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.google.common.collect.ImmutableSet;

/**
 * Adapts {@link GridTabWrapper} to {@link IModelInternalAccessor}.
 * 
 * @author tsa
 *
 */
public class GridTabModelInternalAccessor implements IModelInternalAccessor
{
	private final GridTabWrapper gridTabWrapper;

	public GridTabModelInternalAccessor(final GridTabWrapper gridTabWrapper)
	{
		super();

		this.gridTabWrapper = gridTabWrapper;
	}

	private final GridTab getGridTab()
	{
		return gridTabWrapper.getGridTab();
	}

	private final GridField getGridField(final String columnName)
	{
		return getGridTab().getField(columnName);
	}

	@Override
	public Set<String> getColumnNames()
	{
		final ImmutableSet.Builder<String> columnNames = ImmutableSet.builder();
		for (final GridField gridField : getGridTab().getFields())
		{
			columnNames.add(gridField.getColumnName());
		}
		return columnNames.build();
	}

	@Override
	public int getColumnIndex(final String columnName)
	{
		throw new UnsupportedOperationException("GridTabWrapper has no supported for column indexes");
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.isVirtualColumn();
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.isKey();
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.getVO().isCalculated();
	}

	@Override
	public boolean hasColumnName(final String columnName)
	{
		return gridTabWrapper.hasColumnName(columnName);
	}

	@Override
	public Object getValue(final String columnName, final int columnIndex, final Class<?> returnType)
	{
		return gridTabWrapper.getValue(columnName, returnType);
	}

	@Override
	public Object getValue(final String columnName, final Class<?> returnType)
	{
		return gridTabWrapper.getValue(columnName, returnType);
	}

	@Override
	public boolean setValue(final String columnName, final Object value)
	{
		gridTabWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public boolean setValueNoCheck(final String columnName, final Object value)
	{
		gridTabWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public Object getReferencedObject(final String columnName, final Method interfaceMethod) throws Exception
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueFromPO(final String idColumnName, final Class<?> parameterType, final Object value)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean invokeEquals(final Object[] methodArgs)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}
}
