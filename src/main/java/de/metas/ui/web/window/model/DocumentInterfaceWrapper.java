package de.metas.ui.web.window.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.wrapper.IInterfaceWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.exceptions.DocumentFieldNotFoundException;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

/*
 * #%L
 * metasfresh-webui-api
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

public class DocumentInterfaceWrapper implements InvocationHandler, IInterfaceWrapper, IModelInternalAccessor
{
	private static final transient Logger logger = LogManager.getLogger(DocumentInterfaceWrapper.class);

	public static <T> T wrap(final Object model, final Class<T> modelClass)
	{
		final Boolean useOldValues = null; // preserve it from "model"
		return wrap(model, modelClass, useOldValues);
	}

	public static <T> T wrapUsingOldValues(final Object model, final Class<T> modelClass)
	{
		final Boolean useOldValues = Boolean.TRUE;
		return wrap(model, modelClass, useOldValues);
	}

	/**
	 * Wraps given model to given model class
	 *
	 * @param model
	 * @param modelClass
	 * @param useOldValues
	 *            <ul>
	 *            <li>true if we shall ALWAYS use old values
	 *            <li>false if we shall NOT use old values
	 *            <li><code>null</code> if we shall preserve model's "old values" flag
	 *            </ul>
	 * @return wrapped model or null
	 */
	private static <T> T wrap(final Object model, final Class<T> modelClass, final Boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}

		if (modelClass.isInstance(model) && (useOldValues == null || useOldValues == Boolean.FALSE))
		{
			@SuppressWarnings("unchecked")
			final T modelCasted = (T)model;
			return modelCasted;
		}

		Document document = null;
		Boolean useOldValuesDefault = null;
		if (model instanceof Document)
		{
			document = (Document)model;
			useOldValuesDefault = false;
		}
		else if (model instanceof IDocumentAware)
		{
			document = ((IDocumentAware)model).getDocument();
			useOldValuesDefault = false;
		}
		
		if (document == null)
		{
			final DocumentInterfaceWrapper wrapper = getWrapper(model);
			if (wrapper != null)
			{
				document = wrapper.getDocument();
				useOldValuesDefault = wrapper.isOldValues(); // by default, preserve it
			}
		}
		if (document == null)
		{
			throw new AdempiereException("Cannot wrap " + model + " (class:" + model.getClass());
		}
		// NOTE: if document is not null then "useOldValuesDefault" shall not be null

		//
		// Shall we use old values?
		final boolean useOldValuesEffective = useOldValues == null ? useOldValuesDefault : useOldValues;

		//
		// Check if given interface is compatible with document, i.e.
		// * interface does not define a Table_Name field
		// * interface has a Table_Name static field, which is equal with document's Table_Name
		final String interfaceTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (interfaceTableName != null)
		{
			final String documentTableName = document.getEntityDescriptor().getTableName();
			if (!interfaceTableName.equals(documentTableName))
			{
				throw new AdempiereException("Interface " + modelClass + " (tableName=" + interfaceTableName + ") is not compatible with " + document + " (tableName=" + documentTableName + ")");
			}
		}

		@SuppressWarnings("unchecked")
		final T result = (T)Proxy.newProxyInstance(modelClass.getClassLoader(),
				new Class<?>[] { modelClass },
				new DocumentInterfaceWrapper(document, useOldValuesEffective));
		return result;
	}

	/**
	 * Unwrap given object and return the underlying {@link Document}.
	 *
	 * @param model
	 * @return {@link Document} or null if model is not a {@link Document} or is not wrapping a {@link Document}
	 */
	/* package */ static Document getDocument(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof Document)
		{
			return (Document)model;
		}

		if (model instanceof IDocumentAware)
		{
			return ((IDocumentAware)model).getDocument();
		}

		final DocumentInterfaceWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}

		return wrapper.getDocument();
	}

	/**
	 * @param model
	 * @return {@link DocumentInterfaceWrapper} or null if no {@link DocumentInterfaceWrapper} can be extracted from given model
	 */
	/* package */static DocumentInterfaceWrapper getWrapper(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (Proxy.isProxyClass(model.getClass()))
		{
			final InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof DocumentInterfaceWrapper)
			{
				final DocumentInterfaceWrapper wrapper = (DocumentInterfaceWrapper)ih;
				return wrapper;
			}
			return null;
		}
		else if (model instanceof DocumentInterfaceWrapper)
		{
			return (DocumentInterfaceWrapper)model;
		}

		return null;
	}

	public static int getWindowNo(final Object model)
	{
		final Document document = getDocument(model);
		if (document == null)
		{
			return Env.WINDOW_None;
		}
		return document.getWindowNo();
	}

	public static void refresh(final Object model)
	{
		final Document document = getDocument(model);
		if (document != null)
		{
			document.refreshFromRepository();
		}
		else
		{
			logger.debug("Wrapped object is not a Document [SKIP]");
		}
	}

	private static final ReasonSupplier REASON_Value_DirectSetFromDocumentWrapper = () -> "direct set from document wrapper";

	private final Document document;
	private final boolean useOldValues;

	private final boolean failOnColumnNotFound = false;

	private DocumentInterfaceWrapper(final Document document, final boolean useOldValues)
	{
		super();

		Check.assumeNotNull(document, "document not null");
		this.document = document;
		this.useOldValues = useOldValues;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("document", document)
				.add("useOldValues", useOldValues ? Boolean.TRUE : null)
				.toString();
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
				value = InterfaceWrapperHelper.checkZeroIdValue(propertyName, args[0]);
			}

			setValue(propertyName, value);
			return null;
		}
		else if (methodName.startsWith("get") && (args == null || args.length == 0)
		// metas: Document direct calls should be forwarded to Document directly
				&& !methodName.startsWith("get_"))
		{
			final String propertyName = methodName.substring(3);
			final IDocumentFieldView documentField = document.getFieldViewOrNull(propertyName);
			if (documentField != null)
			{
				final Object value = getValue(documentField, method.getReturnType());
				if (value != null)
				{
					return value;
				}
			}
			else if (documentField == null && isModelInterface(method.getReturnType()))
			{
				return getReferencedObject(propertyName, method);
			}
			else
			{
				logger.warn("Field " + propertyName + " not found for " + document + ". Assuming default value.");
			}

			//
			// Return default value
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
			IDocumentFieldView field = document.getFieldViewOrNull(propertyName);
			if (field != null)
			{
				final Object value = getValue(field, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			field = document.getFieldViewOrNull("Is" + propertyName);
			if (field != null)
			{
				final Object value = getValue(field, method.getReturnType());
				return DisplayType.toBoolean(value);
			}
			//
			throw new IllegalArgumentException("Method not supported - " + methodName);
		}
		else
		{
			// TODO: this is not working; we need to find the similar method in document's class
			return method.invoke(document, args);
		}
	}

	@Override
	public final Object getValue(final String columnName, final Class<?> returnType)
	{
		final IDocumentFieldView field = getDocument().getFieldView(columnName);
		return getValue(field, returnType);
	}

	@Override
	public Object getValue(final String columnName, final int columnIndex, final Class<?> returnType)
	{
		return getValue(columnName, returnType);
	}

	private Object getValue(final IDocumentFieldView field, final Class<?> returnType)
	{
		final Object value;
		if (useOldValues)
		{
			value = field.getOldValue();
		}
		else
		{
			value = field.getValue();
		}

		return convertValueToType(value, field, returnType);
	}

	private static final Object convertValueToType(final Object value, final IDocumentFieldView field, final Class<?> returnType)
	{
		if (Object.class.equals(returnType))
		{
			if (value == null)
			{
				return null;
			}
			else if (value instanceof LookupValue)
			{
				final LookupValue lookupValue = (LookupValue)value;
				return lookupValue.getId();
			}
		}

		if (value != null && returnType.isAssignableFrom(value.getClass()))
		{
			return value;
		}

		if (boolean.class.equals(returnType))
		{
			return DisplayType.toBoolean(value);
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
			else if (value instanceof LookupValue)
			{
				final LookupValue lookupValue = (LookupValue)value;
				return lookupValue.getIdAsInt();
			}
		}
		else if (String.class.equals(returnType))
		{
			if (value == null)
			{
				return null;
			}
			else if (value instanceof LookupValue)
			{
				return ((LookupValue)value).getIdAsString();
			}
			else
			{
				return value.toString();
			}
		}
		else if (java.sql.Timestamp.class.equals(returnType))
		{
			if (value == null)
			{
				return null;
			}
			else if (value instanceof java.util.Date)
			{
				return TimeUtil.asTimestamp((java.util.Date)value);
			}
		}
		else
		{
			return value;
		}

		throw new AdempiereException("Cannot convert value to type."
				+ "\n Field: " + field
				+ "\n Return type: " + returnType
				+ "\n Value: " + value + " (" + (value == null ? "null" : value.getClass()) + ")"
				+ "\n");

	}

	@Override
	public final boolean setValue(final String propertyName, final Object value)
	{
		if (useOldValues)
		{
			throw new AdempiereException("Setting values in an old object is not allowed");
		}

		return setValue(document, propertyName, value, failOnColumnNotFound);
	}

	/* package */static final boolean setValue(final Document document, final String propertyName, final Object value, final boolean failOnColumnNotFound)
	{
		final Object valueFixed = InterfaceWrapperHelper.checkZeroIdValue(propertyName, value);
		try
		{
			document.setValue(propertyName, valueFixed, REASON_Value_DirectSetFromDocumentWrapper);
			return true;
		}
		catch (final DocumentFieldNotFoundException e)
		{
			final String errorMsg = e.getLocalizedMessage();
			final String msg = "Attempt to set field " + propertyName
					+ " of document " + document
					+ " to value '" + valueFixed + "' (original: '" + value + "')"
					+ "' returned an error message: " + errorMsg;
			if (failOnColumnNotFound)
			{
				throw new AdempiereException(msg, e);
			}
			else
			{
				logger.error(msg, e);
				return false;
			}
		}
	}

	public final Document getDocument()
	{
		return document;
	}

	private final Properties getCtx()
	{
		return document.getCtx();
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
	@Override
	public final Object getReferencedObject(final String propertyName, final Method method)
	{
		final String idPropertyName = propertyName + "_ID";
		final IDocumentFieldView idField = document.getFieldViewOrNull(idPropertyName);
		if (idField == null)
		{
			logger.warn("Field {} not found for {}. Assuming null value.", idPropertyName, document);
			return null;
		}

		//
		// Parent link
		if (idField.isParentLink())
		{
			final Class<?> returnType = method.getReturnType();
			final Integer record_id = (Integer)getValue(idField, Integer.class);
			final Object parentModel = getReferencedObjectFromParentDocument(returnType, record_id);
			return parentModel;
		}

		// Fetch Record_ID
		final Integer record_id = (Integer)getValue(idField, Integer.class);
		if (record_id == null || record_id <= 0)
		{
			return null;
		}

		// Load and return
		final Class<?> returnType = method.getReturnType();
		final Object model = InterfaceWrapperHelper.create(getCtx(), record_id, returnType, getTrxName());
		return model;
	}

	private Object getReferencedObjectFromParentDocument(final Class<?> modelClass, final Integer parentRecordId)
	{
		final Document parentDocument = document.getParentDocument();
		if (parentDocument == null)
		{
			logger.warn("Failed fetching the parent link document because there is none for {}", document);
			return null;
		}

		//
		// Make sure the parent has the expected ID.
		// In case the parentRecordId is null, we assume the parent is NEW and that's why it was not set.
		if (parentRecordId != null && parentDocument.getDocumentIdAsInt() != parentRecordId)
		{
			logger.warn("Failed fetching the parent link document because parent document does not have the expected ID. \n Document: {} \n Parent document: {} \n Expected parent ID: {}", document,
					parentDocument, parentRecordId);
			return null;
		}

		final String modelTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (modelTableName == null)
		{
			logger.warn("Failed fetching the parent link document because required model class {} has no TableName", modelClass);
			return null;
		}

		final String parentTableName = parentDocument.getEntityDescriptor().getTableName();
		if (!modelTableName.equals(parentTableName))
		{
			logger.warn("Failed fetching the parent link document because parent document's table name ({}) is not matching the expected table name ({})", parentTableName, modelTableName);
			return null;
		}

		return wrap(parentDocument, modelClass);
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
		return getDocument(model) != null;
	}

	public static void save(final Object model)
	{
		final Document document = getDocument(model);
		document.saveIfHasChanges();
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
		final Document document = getDocument(model);
		if (document == null)
		{
			return true;
		}

		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		if (field == null)
		{
			return true;
		}

		final Object value = field.getValue();
		return value == null;
	}

	public static boolean hasColumnName(final Object model, final String columnName)
	{
		final Document document = getDocument(model);
		if (document == null)
		{
			return false;
		}

		final IDocumentFieldView documentField = document.getFieldViewOrNull(columnName);
		return documentField != null;
	}

	@Override
	public final boolean hasColumnName(final String columnName)
	{
		return getDocument().hasField(columnName);
	}

	public static int getId(final Object model)
	{
		return getDocument(model).getDocumentIdAsInt();
	}

	public static boolean isNew(final Object model)
	{
		return getDocument(model).isNew();
	}

	public <T> T getDynAttribute(final String attributeName)
	{
		return getDocument().getDynAttribute(attributeName);
	}

	public Object setDynAttribute(final String attributeName, final Object value)
	{
		return getDocument().setDynAttribute(attributeName, value);
	}

	public final boolean isOldValues()
	{
		return useOldValues;
	}

	public static final boolean isOldValues(final Object model)
	{
		final DocumentInterfaceWrapper wrapper = getWrapper(model);
		return wrapper == null ? false : wrapper.isOldValues();
	}

	@Override
	public Set<String> getColumnNames()
	{
		return document.getFieldNames();
	}

	@Override
	public int getColumnIndex(final String columnName)
	{
		throw new UnsupportedOperationException("DocumentInterfaceWrapper has no supported for column indexes");
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		return field != null && field.isVirtualField();
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		return field != null && field.isKey();
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		return field != null && field.isCalculated();
	}

	@Override
	public boolean setValueNoCheck(final String columnName, final Object value)
	{
		return setValue(columnName, value);
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
