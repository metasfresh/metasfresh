package org.adempiere.model;

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


import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.base.Function;

/**
 * Class used to describe a column in a particular model.
 *
 * Please note all COLUMN_ fields from generated interfaces.
 *
 * @author tsa
 *
 * @param <MT> table model class
 * @param <CMT> column's model class
 */
public final class ModelColumn<MT, CMT>
{
	private final String columnName;
	private final Class<MT> modelClass;
	private String _modelTableName = null; // lazy loaded on demand
	private final Class<CMT> columnModelType;

	public ModelColumn(final Class<MT> modelClass, final String columnName, final Class<CMT> columnModelType)
	{
		super();
		this.modelClass = modelClass;
		this.columnName = columnName;
		this.columnModelType = columnModelType;
	}

	@Override
	public String toString()
	{
		return "ModelColumn ["
				+ "columnName=" + columnName
				+ ", modelClass=" + modelClass
				+ ", columnModelType=" + columnModelType
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(columnName)
				.append(modelClass)
				.append(columnModelType)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final ModelColumn<MT, CMT> other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.columnName, other.columnName)
				.append(this.modelClass, other.modelClass)
				.append(this.columnModelType, other.columnModelType)
				.isEqual();
	}

	/**
	 * Gets model class.
	 *
	 * e.g. if we are talking about C_Invoice.C_BPartner_ID field then this method will return I_C_Invoice.class
	 *
	 * @return model class
	 */
	public Class<MT> getModelClass()
	{
		return modelClass;
	}

	/**
	 * Gets model table name.
	 *
	 * e.g. if we are talking about I_C_Invoice model class then this method will return "C_Invoice" (i.e. I_C_Invoice.Table_Name)
	 *
	 * @return model table name
	 */
	public synchronized String getTableName()
	{
		if (_modelTableName == null)
		{
			_modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		}
		return _modelTableName;
	}

	/**
	 * Gets column name
	 *
	 * e.g. if we are talking about C_Invoice.C_BPartner_ID field then this method will return "C_BPartner_ID"
	 *
	 * @return column name
	 */
	public String getColumnName()
	{
		return columnName;
	}

	/**
	 * Gets column's model class or null
	 *
	 * e.g. if we are talking about C_Invoice.C_BPartner_ID field then this method will return I_C_BPartner.class
	 *
	 * @return column's model class or null
	 */
	public Class<CMT> getColumnModelType()
	{
		return columnModelType;
	}

	/**
	 * @return function which gets the column value from model.
	 */
	public final <ValueType> Function<MT, ValueType> asValueFunction()
	{
		// NOTE: we are returning a new instance each time, because this method is not used so offen
		// and because storing an instace for each ModelColumn will consume way more memory.

		final Function<MT, Object> valueFunction = new ValueFunction();
		@SuppressWarnings("unchecked")
		final Function<MT, ValueType> valueFunctionCasted = (Function<MT, ValueType>)valueFunction;
		return valueFunctionCasted;
	}

	/**
	 * Function which gets the Value of this model column.
	 *
	 * @author tsa
	 *
	 */
	private final class ValueFunction implements Function<MT, Object>
	{

		@Override
		public Object apply(final MT model)
		{
			final Object value = InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
			return value;
		}

		@Override
		public String toString()
		{
			return "ValueFunction[" + modelClass + " -> " + columnName + "]";
		}
	}
}
