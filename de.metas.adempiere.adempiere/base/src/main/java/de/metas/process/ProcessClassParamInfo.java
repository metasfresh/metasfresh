package de.metas.process;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.util.DisplayType;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;

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

/**
 * Contains information about process class fields which were marked as parameters (i.e. annotated with {@link Param}).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ProcessClassParamInfo
{
	public static final Builder builder()
	{
		return new Builder();
	}

	static final ArrayKey createFieldUniqueKey(final Field field)
	{
		// NOTE: when building the make, make sure we don't have any references to Class, Field or other java reflection classes
		return ArrayKey.of(field.getType().getName(), field.getDeclaringClass().getName(), field.getName());
	}
	
	static final ArrayKey createParameterUniqueKey(final String parameterName, final boolean parameterTo)
	{
		return ArrayKey.of(parameterName, parameterTo);
	}

	private boolean parameterTo;
	private final String parameterName;
	private final ArrayKey parameterKey;
	
	private final boolean mandatory;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.
	private final ArrayKey fieldKey;
	private final Class<?> fieldType; // FIXME: get rid of this to avoid class loader memory leaks


	private ProcessClassParamInfo(final Builder builder)
	{
		super();

		Check.assumeNotEmpty(builder.parameterName, "parameter name not empty");
		Check.assumeNotNull(builder.field, "field not null");
		
		parameterName = builder.parameterName;
		parameterTo = builder.parameterTo;
		parameterKey = createParameterUniqueKey(parameterName, parameterTo);

		fieldKey = createFieldUniqueKey(builder.field);
		fieldType = builder.field.getType();

		mandatory = builder.mandatory;
	}

	/**
	 * Loads process instance's parameter value from source.
	 *
	 * @param processInstance the process object where we will set the annotated fields to be the loaded parameters. Note that it needs to be an {@link IContextAware}, because we might need to load
	 *            records from the given <code>source</code>
	 * @param processField
	 * @param source
	 * @param failIfNotValid
	 */
	public void loadParameterValue(final JavaProcess processInstance, final Field processField, final IRangeAwareParams source, final boolean failIfNotValid)
	{
		Check.assumeNotNull(processField, "processField not null");

		//
		// Get the parameter value from source
		final Object value = extractParameterValue(processInstance, processField, source);

		//
		// Handle the case when the value is null
		if (value == null)
		{
			if (mandatory)
			{
				if(failIfNotValid)
				{
					throw new FillMandatoryException(parameterName);
				}
				else
				{
					return;
				}
			}
			if (fieldType.isPrimitive())
			{
				// don't set a primitive type to null
				return;
			}
		}

		//
		// Set the value to given process instance
		try
		{
			if (!processField.isAccessible())
			{
				processField.setAccessible(true);
			}
			processField.set(processInstance, value);
		}
		catch (final Throwable e)
		{
			throw new IllegalStateException("Failed setting the value of " + processField
					+ "\n Parameter info: " + this
					+ "\n Process instance: " + processInstance, e);
		}
	}

	private final Object extractParameterValue(
			final JavaProcess processInstance,
			final Field processField,
			final IRangeAwareParams source)
	{
		if (!source.hasParameter(parameterName))
		{
			return null;
		}

		//
		// Get the parameter value from source
		final Object value;
		final Class<?> fieldType = processField.getType();
		if (fieldType.isAssignableFrom(BigDecimal.class))
		{
			value = parameterTo ? source.getParameter_ToAsBigDecimal(parameterName) : source.getParameterAsBigDecimal(parameterName);
		}
		else if (fieldType.isAssignableFrom(int.class))
		{
			value = parameterTo ? source.getParameter_ToAsInt(parameterName) : source.getParameterAsInt(parameterName);
		}
		else if (boolean.class.equals(fieldType))
		{
			value = parameterTo ? source.getParameter_ToAsBool(parameterName) : source.getParameterAsBool(parameterName);
		}
		else if (Boolean.class.equals(fieldType))
		{
			final String valueStr = parameterTo ? source.getParameter_ToAsString(parameterName) : source.getParameterAsString(parameterName);
			value = DisplayType.toBoolean(valueStr, (Boolean)null);
		}
		else if (java.util.Date.class.isAssignableFrom(fieldType))
		{
			// this catches both Date and Timestamp
			value = parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName);
		}
		else if (fieldType.isAssignableFrom(String.class))
		{
			value = parameterTo ? source.getParameter_ToAsString(parameterName) : source.getParameterAsString(parameterName);
		}
		else if (InterfaceWrapperHelper.isModelInterface(fieldType))
		{
			final int id = parameterTo ? source.getParameter_ToAsInt(parameterName) : source.getParameterAsInt(parameterName);
			if (id <= 0)
			{
				value = null;
			}
			else
			{
				Object valueOld;
				try
				{
					if(!processField.isAccessible())
					{
						processField.setAccessible(true);
					}
					valueOld = processField.get(processInstance);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					// shall not happen
					throw AdempiereException.wrapIfNeeded(e);
				}
				
				final int idOld = valueOld == null ? -1 : InterfaceWrapperHelper.getId(valueOld);
				if (id != idOld)
				{
					value = InterfaceWrapperHelper.create(processInstance.getCtx(), id, fieldType, ITrx.TRXNAME_ThreadInherited);
				}
				else
				{
					value = valueOld;
				}
			}
		}
		else
		{

			throw new IllegalStateException("Field type " + fieldType + " is not supported for " + this);
		}

		return value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("parameterName", parameterName)
				.add("mandatory", mandatory)
				.add("parameterTo", parameterTo)
				.add("field", fieldKey)
				.toString();
	}
	
	public ArrayKey getKey()
	{
		return parameterKey;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public ArrayKey getFieldKey()
	{
		return fieldKey;
	}

	public Class<?> getFieldType()
	{
		return fieldType;
	}

	public static final class Builder
	{
		private String parameterName;
		private Field field;
		private boolean mandatory;
		private boolean parameterTo;

		private Builder()
		{
			super();
		}

		public ProcessClassParamInfo build()
		{
			return new ProcessClassParamInfo(this);
		}

		public Builder setParameterName(final String parameterName)
		{
			this.parameterName = parameterName;
			return this;
		}

		public Builder setField(final Field field)
		{
			this.field = field;
			return this;
		}

		public Builder setMandatory(final boolean mandatory)
		{
			this.mandatory = mandatory;
			return this;
		}

		public Builder setParameterTo(boolean parameterTo)
		{
			this.parameterTo = parameterTo;
			return this;
		}
	}
}
