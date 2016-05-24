package de.metas.ui.web.vaadin.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.wrapper.IInterfaceWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import com.vaadin.data.Property;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.components.IFieldGroup;
import de.metas.ui.web.vaadin.window.model.DataRowItem;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class FieldGroupModelWrapper
		implements IInterfaceWrapper, InvocationHandler
{
	private static final Logger logger = LogManager.getLogger(DataRowItem.class);

	public static <T> T wrap(final Object model, final Class<T> cl)
	{
		final Boolean useOldValues = null;
		return wrap(model, cl, useOldValues);
	}

	/**
	 * Wraps given model to given model class
	 *
	 * @param model
	 * @param cl
	 * @param useOldValues <ul>
	 *            <li>true if we shall ALWAYS use old values
	 *            <li>false if we shall NOT use old values
	 *            <li><code>null</code> if we shall preserve model's "old values" flag
	 *            </ul>
	 * @return wrapped model or null
	 */
	/* package */static <T> T wrap(final Object model, final Class<T> cl, final Boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}

		IFieldGroup fieldGroup = null;
		Boolean useOldValuesDefault = null;
		if (model instanceof IFieldGroup)
		{
			fieldGroup = (IFieldGroup)model;
			useOldValuesDefault = false;
		}
		if (fieldGroup == null)
		{
			final FieldGroupModelWrapper wrapper = getWrapper(model);
			if (wrapper != null)
			{
				fieldGroup = wrapper.getFieldGroup();
				useOldValuesDefault = wrapper.isOldValues(); // by default, preserve it
			}
		}
		if (fieldGroup == null)
		{
			throw new AdempiereException("Cannot wrap " + model + " (class:" + model.getClass());
		}
		// NOTE: if fieldGroup is not null then "useOldValuesDefault" shall not be null

		//
		// Shall we use old values?
		final boolean useOldValuesEffective = useOldValues == null ? useOldValuesDefault : useOldValues;

		//
		// Check if given interface is compatible with fieldGroup, i.e.
		// * interface does not define a Table_Name field
		// * interface has a Table_Name static field, which is equal with fieldGroup's Table_Name
		// FIXME:
//		final String interfaceTableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
//		if (interfaceTableName != null && !interfaceTableName.equals(fieldGroup.getTableName()))
//		{
//			throw new AdempiereException("Interface " + cl + " (tableName=" + interfaceTableName + ") is not compatible with " + fieldGroup + " (tableName=" + fieldGroup.getTableName() + ")");
//		}

		@SuppressWarnings("unchecked")
		final T result = (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				new FieldGroupModelWrapper(fieldGroup, useOldValuesEffective));
		return result;
	}

	/**
	 *
	 * @param model
	 * @return {@link IFieldGroup} or null if model is not a {@link IFieldGroup} or is not wrapping a {@link IFieldGroup}
	 */
	public static IFieldGroup getFieldGroup(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof IFieldGroup)
		{
			return (IFieldGroup)model;
		}

		final FieldGroupModelWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}

		return wrapper.getFieldGroup();
	}

	/**
	 * @param model
	 * @return {@link FieldGroupModelWrapper} or null if no {@link FieldGroupModelWrapper} can be extracted from given model
	 */
	public static FieldGroupModelWrapper getWrapper(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (Proxy.isProxyClass(model.getClass()))
		{
			final InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof FieldGroupModelWrapper)
			{
				final FieldGroupModelWrapper wrapper = (FieldGroupModelWrapper)ih;
				return wrapper;
			}
			return null;
		}
		else if (model instanceof FieldGroupModelWrapper)
		{
			return (FieldGroupModelWrapper)model;
		}

		return null;
	}

	private final IFieldGroup fieldGroup;
	private final boolean useOldValues;

	private final boolean failOnColumnNotFound = false;

	private FieldGroupModelWrapper(final IFieldGroup fieldGroup, final boolean useOldValues)
	{
		super();

		Check.assumeNotNull(fieldGroup, "fieldGroup not null");
		this.fieldGroup = fieldGroup;
		this.useOldValues = useOldValues;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
	{
		final String methodName = method.getName();
		if (methodName.startsWith("set") && args.length == 1)
		{
			final Class<?> paramType = method.getParameterTypes()[0];

			final String propertyName;
			final Object value;
			if (isModelInterface(paramType))
			{
				// Model setter - 03374
				propertyName = methodName.substring(3) + "_ID";
				value = InterfaceWrapperHelper.getId(args[0]);
			}
			else
			{
				propertyName = methodName.substring(3);
				value = POWrapper.checkZeroIdValue(propertyName, args[0]);
			}

			setValue(propertyName, value);
			return null;
		}
		else if (methodName.startsWith("get") && (args == null || args.length == 0)
				// metas: IFieldGroup direct calls should be forwarded directly
				&& !methodName.startsWith("get_"))
		{
			final String propertyName = methodName.substring(3);
			final Property<?> property = fieldGroup.getProperty(propertyName);
			if (property != null)
			{
				final Object value = getValue(property, method.getReturnType());
				if (value != null)
				{
					return value;
				}
			}
			else if (property == null && isModelInterface(method.getReturnType()))
			{
				return getReferencedObject(propertyName, method);
			}
			else
			{
				logger.warn("Field " + propertyName + " not found for " + fieldGroup + ". Assuming default value.");
			}
			//
			Object defaultValue = null;
			if (method.getReturnType() == int.class)
			{
				defaultValue = Integer.valueOf(0);
			}
			else if (method.getReturnType() == BigDecimal.class)
			{
				defaultValue = BigDecimal.ZERO;
			}
			else if (PO.class.isAssignableFrom(method.getReturnType()))
			{
				throw new IllegalArgumentException("Method not supported - " + methodName);
			}
			return defaultValue;
		}
		else if (methodName.startsWith("is") && (args == null || args.length == 0))
		{
			final String propertyName = methodName.substring(2);
			Property<?> property = getProperty(propertyName);
			if (property != null)
			{
				final Object value = getValue(property, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			property = getProperty("Is" + propertyName);
			if (property != null)
			{
				final Object value = getValue(property, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			throw new IllegalArgumentException("Method not supported - " + methodName);
		}
//		else if (method.getName().equals("get_TableName"))
//		{
//			return fieldGroup.get_TableName();
//		}
		else
		{
			// TODO: this is not working; we need to find the similar method in fieldGroup.getClass() class
			return method.invoke(fieldGroup, args);
		}
	}

	public final Object getValue(final String columnName, final Class<?> returnType)
	{
		final Property<?> property = getFieldGroup().getProperty(columnName);
		return getValue(property, returnType);
	}

	private Object getValue(final Property<?> field, final Class<?> returnType)
	{
		final Object value;
		if (useOldValues)
		{
			throw new UnsupportedOperationException(); // TODO
//			value = field.getOldValue();
		}
		else
		{
			value = field.getValue();
		}

		return convertValueToType(value, field, returnType);
	}

	private static final Object convertValueToType(final Object value, final Property<?> property, final Class<?> returnType)
	{
		if (boolean.class.equals(returnType))
		{
			return DisplayType.toBoolean(value, false);
		}
		else if (Integer.class.equals(returnType) || int.class.equals(returnType))
		{
			if (value == null)
			{
				return null;
			}
			else if (value instanceof Number)
			{
				return ((Number)value).intValue();
			}
			else if (value instanceof String)
			{
				return Integer.parseInt(value.toString());
			}
			else if (value instanceof KeyNamePair)
			{
				return ((KeyNamePair)value).getKey();
			}
			else
			{
				throw new AdempiereException("Invalid field value type returned."
						+ "\n Field: " + property
						+ "\n Expected type: " + returnType
						+ "\n Value: " + value + " (class: " + value.getClass() + ")"
						+ "\n");
			}
		}
		else
		{
			return value;
		}

	}

	public final void setValue(final String propertyName, final Object value)
	{
		if (useOldValues)
		{
			throw new AdempiereException("Setting values in an old object is not allowed");
		}

		final Object valueFixed = POWrapper.checkZeroIdValue(propertyName, value);

		try
		{
			final Property<Object> property = getProperty(propertyName);
			if (property == null)
			{
				throw new AdempiereException("No property found");
			}
			property.setValue(value);
		}
		catch (Exception e)
		{
			final String msg = "Attempt to set field " + propertyName
					+ " of " + fieldGroup
					+ " to value '" + valueFixed + " (original: '" + value + "')"
					+ "' returned an error message: " + e.getLocalizedMessage();
			if (failOnColumnNotFound)
			{
				throw new AdempiereException(msg, e);
			}
			else
			{
				logger.error(msg, e);
			}
		}
	}

	public final IFieldGroup getFieldGroup()
	{
		return fieldGroup;
	}

	private final Properties getCtx()
	{
		return Env.getCtx();
	}

	private final String getTrxName()
	{
		return null;
	}

	/**
	 * Load object that is referenced by given property. Example: getReferencedObject("M_Product", method) should load the M_Product record with ID given by M_Product_ID property name;
	 *
	 * @param propertyName
	 * @param method
	 * @return
	 */
	private final Object getReferencedObject(final String propertyName, final Method method)
	{
		final String idPropertyName = propertyName + "_ID";
		final Property<?> idField = fieldGroup.getProperty(idPropertyName);
		if (idField == null)
		{
			logger.warn("Field " + idPropertyName + " not found for " + fieldGroup + ". Assuming null value.");
			return null;
		}

		// Fetch Record_ID
		final Integer record_id = (Integer)getValue(idField, Integer.class);
		if (record_id == null || record_id <= 0)
		{
			return null;
		}

		// Load and return
		final Class<?> returnType = method.getReturnType();

		Object retValue = null;

		// FIXME: implement
//		if (idField.getVO().IsParent)
//		{
//			retValue = getReferencedObjectFromParentTab(returnType, record_id);
//		}

		if (retValue == null)
		{
			retValue = InterfaceWrapperHelper.create(getCtx(), record_id, returnType, getTrxName());
		}
		return retValue;
	}

	private Object getReferencedObjectFromParentTab(final Class<?> modelClass, final int parentRecordId)
	{
		final IFieldGroup parentFieldGroup = null; // FIXME: fieldGroup.getParentFieldGroup();
		if (parentFieldGroup == null)
		{
			return null;
		}

		if (getIdFromFieldGroup(parentFieldGroup) != parentRecordId)
		{
			return null;
		}

		final String modelTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (modelTableName == null)
		{
			return null;
		}

		// FIXME: validate if tableName is matching
//		if (!modelTableName.equals(parentFieldGroup.getTableName()))
//		{
//			return null;
//		}

		return wrap(parentFieldGroup, modelClass);
	}

	private boolean isModelInterface(final Class<?> cl)
	{
		try
		{
			final String tableName = (String)cl.getField("Table_Name").get(null);
			return tableName != null;
		}
		catch (final Exception e)
		{
			return false;
		}

	}

	public static boolean isHandled(final Object model)
	{
		return getFieldGroup(model) != null;
	}

	private static Property<?> getProperty(final Object model, final String columnName)
	{
		final IFieldGroup fieldGroup = getFieldGroup(model);
		if (fieldGroup == null)
		{
			return null;
		}

		return fieldGroup.getProperty(columnName);
	}

	private <T> Property<T> getProperty(final String columnName)
	{
		@SuppressWarnings("unchecked")
		final Property<T> property = (Property<T>)getFieldGroup().getProperty(columnName);
		return property;
	}

	/**
	 * Check if given columnName's value is null
	 *
	 * @param model
	 * @param columnName
	 * @return true if columnName's value is null
	 */
	public static boolean isNull(final Object model, final String columnName)
	{
		final Property<?> property = getProperty(model, columnName);
		if (property == null)
		{
			return true;
		}

		final Object value = property.getValue();
		return value == null;
	}

	public static boolean hasColumnName(final Object model, final String columnName)
	{
		final Property<?> property = getProperty(model, columnName);
		return property != null;
	}

	public final boolean hasColumnName(final String columnName)
	{
		final Property<?> property = getProperty(columnName);
		return property != null;
	}

	public static int getId(final Object model)
	{
		final IFieldGroup fieldGroup = getFieldGroup(model);
		return fieldGroup == null ? -1 : getIdFromFieldGroup(fieldGroup);
	}

	private static final int getIdFromFieldGroup(final IFieldGroup fieldGroup)
	{
		// TODO
		return -1;
	}

	public static boolean isNew(final Object model)
	{
		return getId(model) <= 0;
	}

	public final boolean isOldValues()
	{
		return useOldValues;
	}

	public static final boolean isOldValues(final Object model)
	{
		final FieldGroupModelWrapper wrapper = getWrapper(model);
		return wrapper == null ? false : wrapper.isOldValues();
	}
}
