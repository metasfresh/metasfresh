package de.metas.ui.web.window.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.wrapper.IInterfaceWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;

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

public class DocumentInterfaceWrapper implements InvocationHandler, IInterfaceWrapper
{
	private static final Logger log = LogManager.getLogger(DocumentInterfaceWrapper.class);

	public static <T> T create(final Object model, final Class<T> cl)
	{
		final Boolean useOldValues = null; // preserve it from "model"
		return create(model, cl, useOldValues);
	}
	
	public static <T> T createOld(final Object model, final Class<T> cl)
	{
		final Boolean useOldValues = Boolean.TRUE;
		return create(model, cl, useOldValues);
	}

	/**
	 * Wraps given model to given model class
	 *
	 * @param model
	 * @param cl
	 * @param useOldValues
	 *            <ul>
	 *            <li>true if we shall ALWAYS use old values
	 *            <li>false if we shall NOT use old values
	 *            <li><code>null</code> if we shall preserve model's "old values" flag
	 *            </ul>
	 * @return wrapped model or null
	 */
	private static <T> T create(final Object model, final Class<T> cl, final Boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}

		Document document = null;
		Boolean useOldValuesDefault = null;
		if (model instanceof Document)
		{
			document = (Document)model;
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
		final String interfaceTableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
		if (interfaceTableName != null)
		{
			final String documentTableName = SqlDocumentEntityDataBindingDescriptor.getTableName(document);
			if (!interfaceTableName.equals(documentTableName))
			{
				throw new AdempiereException("Interface " + cl + " (tableName=" + interfaceTableName + ") is not compatible with " + document + " (tableName=" + documentTableName + ")");
			}
		}

		@SuppressWarnings("unchecked")
		final T result = (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				new DocumentInterfaceWrapper(document, useOldValuesEffective));
		return result;
	}

	/**
	 *
	 * @param model
	 * @return Document or null if model is not a {@link Document} or is not wrapping a {@link Document}
	 */
	public static Document getDocument(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof Document)
		{
			return (Document)model;
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
	public static DocumentInterfaceWrapper getWrapper(final Object model)
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
			document.getDocumentRepository().refresh(document);
		}
		else
		{
			log.debug("Wrapped object is not a Document [SKIP]");
		}
	}

	private final Document document;
	private final boolean useOldValues;
	private final Map<Integer, Map<String, Object>> recordId2dynAttributes = new HashMap<>();

	private final boolean failOnColumnNotFound = false;

	private DocumentInterfaceWrapper(final Document document, final boolean useOldValues)
	{
		super();

		Check.assumeNotNull(document, "document not null");
		this.document = document;
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
		// metas: Document direct calls should be forwarded to Document directly
				&& !methodName.startsWith("get_"))
		{
			final String propertyName = methodName.substring(3);
			final DocumentField gridField = document.getFieldOrNull(propertyName);
			if (gridField != null)
			{
				final Object value = getValue(gridField, method.getReturnType());
				if (value != null)
				{
					return value;
				}
			}
			else if (gridField == null && isModelInterface(method.getReturnType()))
			{
				return getReferencedObject(propertyName, method);
			}
			else
			{
				log.warn("Field " + propertyName + " not found for " + document + ". Assuming default value.");
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
			DocumentField field = document.getFieldOrNull(propertyName);
			if (field != null)
			{
				final Object value = getValue(field, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			field = document.getFieldOrNull("Is" + propertyName);
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

	public final Object getValue(final String columnName, final Class<?> returnType)
	{
		final DocumentField field = getDocument().getField(columnName);
		return getValue(field, returnType);
	}

	private Object getValue(final DocumentField field, final Class<?> returnType)
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

	private static final Object convertValueToType(final Object value, final DocumentField field, final Class<?> returnType)
	{
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
			else
			{
				throw new AdempiereException("Invalid field value type returned."
						+ "\n Field: " + field
						+ "\n Expected type: " + returnType
						+ "\n Value: " + value + " (class: " + value.getClass() + ")"
						+ "\n");
			}
		}
		else if (String.class.equals(returnType))
		{
			if(value == null)
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
		FieldChangedEventCollector eventsCollector = FieldChangedEventCollector.newInstance(); // TODO forward to some thread local?
		try
		{
			document.setValueFromJsonObject(propertyName, valueFixed, eventsCollector);
		}
		catch (Exception e)
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
				log.error(msg, e);
			}
		}
	}

	public final Document getDocument()
	{
		return document;
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
		final DocumentField idField = document.getFieldOrNull(idPropertyName);
		if (idField == null)
		{
			log.warn("Field " + idPropertyName + " not found for " + document + ". Assuming null value.");
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
		if (idField.getDescriptor().isParentLink())
		{
			retValue = getReferencedObjectFromParentTab(returnType, record_id);
		}

		if (retValue == null)
		{
			retValue = InterfaceWrapperHelper.create(getCtx(), record_id, returnType, getTrxName());
		}
		return retValue;
	}

	private Object getReferencedObjectFromParentTab(final Class<?> modelClass, final int parentRecordId)
	{
		final Document parentDocument = document.getParentDocument();
		if (parentDocument == null)
		{
			return null;
		}

		if (parentDocument.getDocumentId() != parentRecordId)
		{
			return null;
		}

		final String modelTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (modelTableName == null)
		{
			return null;
		}

		if (!modelTableName.equals(SqlDocumentEntityDataBindingDescriptor.getTableName(parentDocument)))
		{
			return null;
		}

		return create(parentDocument, modelClass);
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
		document.getDocumentRepository().save(document);
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

		final DocumentField field = document.getFieldOrNull(columnName);
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

		final DocumentField gridField = document.getFieldOrNull(columnName);
		return gridField != null;
	}

	public final boolean hasColumnName(final String columnName)
	{
		final DocumentField gridField = getDocument().getFieldOrNull(columnName);
		return gridField != null;
	}

	public static int getId(final Object model)
	{
		return getDocument(model).getDocumentId();
	}

	public static boolean isNew(final Object model)
	{
		return getDocument(model).isNew();
	}

	public <T> T getDynAttribute(final String attributeName)
	{
		if (recordId2dynAttributes == null)
		{
			return null;
		}

		final int recordId = getDocument().getDocumentId();
		final Map<String, Object> dynAttributes = recordId2dynAttributes.get(recordId);

		// Cleanup old entries to avoid weird cases
		// e.g. dynattributes shall be destroyed when user is switching to another record
		removeOldDynAttributesEntries(recordId);

		if (dynAttributes == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final T value = (T)dynAttributes.get(attributeName);

		return value;
	}

	public Object setDynAttribute(final String attributeName, final Object value)
	{
		Check.assumeNotEmpty(attributeName, "attributeName not empty");

		final int recordId = getDocument().getDocumentId();
		Map<String, Object> dynAttributes = recordId2dynAttributes.get(recordId);
		if (dynAttributes == null)
		{
			dynAttributes = new HashMap<>();
			recordId2dynAttributes.put(recordId, dynAttributes);
		}

		final Object valueOld = dynAttributes.put(attributeName, value);

		// Cleanup old entries because in most of the cases we won't use them
		removeOldDynAttributesEntries(recordId);

		//
		// return the old value
		return valueOld;
	}

	private void removeOldDynAttributesEntries(final int recordIdToKeep)
	{
		for (final Iterator<Integer> recordIds = recordId2dynAttributes.keySet().iterator(); recordIds.hasNext();)
		{
			final Integer dynAttribute_recordId = recordIds.next();
			if (dynAttribute_recordId != null && dynAttribute_recordId == recordIdToKeep)
			{
				continue;
			}

			recordIds.remove();
		}
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

}
