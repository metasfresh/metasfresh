/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.GridTabModelInternalAccessor;
import org.adempiere.ad.wrapper.IInterfaceWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.logging.LogManager;

/**
 * Wrap GridTab to ADempiere Bean Interface (i.e. generated interfaces). Usage example:
 * 
 * <pre>
 * I_A_Asset_Disposed bean = GridTabWrapper.create(mTab, I_A_Asset_Disposed.class);
 * Timestamp dateDoc = (Timestamp)value;
 * bean.setDateAcct(dateDoc);
 * bean.setA_Disposed_Date(dateDoc);
 * </pre>
 * 
 * @author Teo Sarca, www.arhipac.ro
 */
public class GridTabWrapper implements InvocationHandler, IInterfaceWrapper
{
	private static final Logger log = LogManager.getLogger(GridTabWrapper.class);

	public static <T> T create(Object model, Class<T> cl)
	{
		final Boolean useOldValues = null;
		return create(model, cl, useOldValues);
	}

	/**
	 * Wraps given model to given model class
	 * 
	 * @param model
	 * @param cl
	 * @param useOldValues
	 * <ul>
	 * <li>true if we shall ALWAYS use old values
	 * <li>false if we shall NOT use old values
	 * <li><code>null</code> if we shall preserve model's "old values" flag
	 * </ul>
	 * @return wrapped model or null
	 * 
	 * @deprecated Please don't call it directly
	 */
	@Deprecated
	public static <T> T create(final Object model, final Class<T> cl, final Boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}

		GridTab gridTab = null;
		Boolean useOldValuesDefault = null;
		if (model instanceof GridTab)
		{
			gridTab = (GridTab)model;
			useOldValuesDefault = false;
		}
		if (gridTab == null)
		{
			final GridTabWrapper wrapper = getWrapper(model);
			if (wrapper != null)
			{
				gridTab = wrapper.getGridTab();
				useOldValuesDefault = wrapper.isOldValues(); // by default, preserve it
			}
		}
		if (gridTab == null)
		{
			throw new AdempiereException("Cannot wrap " + model + " (class:" + model.getClass());
		}
		// NOTE: if gridTab is not null then "useOldValuesDefault" shall not be null

		//
		// Shall we use old values?
		final boolean useOldValuesEffective = useOldValues == null ? useOldValuesDefault : useOldValues;

		//
		// Check if given interface is compatible with gridTab, i.e.
		// * interface does not define a Table_Name field
		// * interface has a Table_Name static field, which is equal with gridTab's Table_Name
		final String interfaceTableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
		if (interfaceTableName != null && !interfaceTableName.equals(gridTab.getTableName()))
		{
			throw new AdempiereException("Interface " + cl + " (tableName=" + interfaceTableName + ") is not compatible with " + gridTab + " (tableName=" + gridTab.getTableName() + ")");
		}

		@SuppressWarnings("unchecked")
		final T result = (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				new GridTabWrapper(gridTab, useOldValuesEffective));
		return result;
	}

	/**
	 * 
	 * @param model
	 * @return GridTab or null if model is not a {@link GridTab} or is not wrapping a {@link GridTab}
	 */
	public static GridTab getGridTab(Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof GridTab)
		{
			return (GridTab)model;
		}

		final GridTabWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}

		return wrapper.getGridTab();
	}

	/**
	 * @param model
	 * @return {@link GridTabWrapper} or null if no {@link GridTabWrapper} can be extracted from given model
	 */
	public static GridTabWrapper getWrapper(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (Proxy.isProxyClass(model.getClass()))
		{
			InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof GridTabWrapper)
			{
				GridTabWrapper wrapper = (GridTabWrapper)ih;
				return wrapper;
			}
			return null;
		}
		else if (model instanceof GridTabWrapper)
		{
			return (GridTabWrapper)model;
		}

		return null;
	}
	
	public <T extends PO> T getPO()
	{
		// using the grid tab wrapper to load the PO
		final GridTab gridTab = getGridTab();
		final String tableName = gridTab.get_TableName();
		final int recordID = gridTab.getKeyID(gridTab.getCurrentRow());
		final Properties ctx = getCtx();
		
		@SuppressWarnings("unchecked")
		final T po = (T)TableModelLoader.instance.getPO(ctx, tableName, recordID, ITrx.TRXNAME_None);
		
		return po;
	}

	public static final IModelInternalAccessor getModelInternalAccessor(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof GridTab)
		{
			final GridTab gridTab = (GridTab)model;
			final GridTabWrapper gridTabWrapper = new GridTabWrapper(gridTab, false);
			return gridTabWrapper.getModelInternalAccessor();
		}

		final GridTabWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}

		return wrapper.getModelInternalAccessor();
	}


	public static int getWindowNo(final Object model)
	{
		final GridTab gridTab = getGridTab(model);
		if (gridTab == null)
		{
			return Env.WINDOW_None;
		}
		return gridTab.getWindowNo();
	}

	public static void refresh(Object model)
	{
		GridTab gridTab = getGridTab(model);
		if (gridTab != null)
		{
			gridTab.dataRefresh();
		}
		else
		{
			log.debug("Wrapped object is not a GridTab [SKIP]");
		}
	}

	private final GridTab m_gridTab;
	private final boolean useOldValues;
	private final Map<Integer, Map<String, Object>> recordId2dynAttributes = new HashMap<>();

	private boolean failOnColumnNotFound = false;

	private Supplier<IModelInternalAccessor> modelInternalAccessorSupplier = Suppliers.memoize(new Supplier<IModelInternalAccessor>()
	{
		@Override
		public IModelInternalAccessor get()
		{
			return new GridTabModelInternalAccessor(GridTabWrapper.this);
		}
	});

	private GridTabWrapper(final GridTab gridTab, final boolean useOldValues)
	{
		super();

		Check.assumeNotNull(gridTab, "gridTab not null");
		this.m_gridTab = gridTab;
		this.useOldValues = useOldValues;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		String methodName = method.getName();
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
				// metas: GridTab direct calls should be forwarded to GridTab directly
				&& !methodName.startsWith("get_"))
		{
			final String propertyName = methodName.substring(3);
			final GridField gridField = m_gridTab.getField(propertyName);
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
				log.warn("Field " + propertyName + " not found for " + m_gridTab + ". Assuming default value.");
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
			String propertyName = methodName.substring(2);
			GridField field = m_gridTab.getField(propertyName);
			if (field != null)
			{
				final Object value = getValue(field, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			field = m_gridTab.getField("Is" + propertyName);
			if (field != null)
			{
				final Object value = getValue(field, method.getReturnType());
				return value instanceof Boolean ? value : "Y".equals(value);
			}
			//
			throw new IllegalArgumentException("Method not supported - " + methodName);
		}
		else if (method.getName().equals("get_TableName"))
		{
			return m_gridTab.get_TableName();
		}
		else
		{
			// TODO: this is not working; we need to find the similar method in m_gridTab.getClass() class
			return method.invoke(m_gridTab, args);
		}
	}

	public final Object getValue(final String columnName, final Class<?> returnType)
	{
		final GridField field = getGridTab().getField(columnName);
		return getValue(field, returnType);
	}

	private Object getValue(GridField field, Class<?> returnType)
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

	private static final Object convertValueToType(final Object value, final GridField field, final Class<?> returnType)
	{
		if (boolean.class.equals(returnType))
		{
			if (value == null)
				return false;
			else
				return value instanceof Boolean ? value : "Y".equals(value);
		}
		else if (Integer.class.equals(returnType))
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
			else
			{
				throw new AdempiereException("Invalid field value type returned."
						+ "\n Field: " + field
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
		final String errorMsg = m_gridTab.setValue(propertyName, value);
		if (!Check.isEmpty(errorMsg))
		{
			final String msg = "Attempt to set field " + propertyName
					+ " of grid tab " + m_gridTab
					+ " to value '" + valueFixed + " (original: '" + value + "')"
					+ "' returned an error message: " + errorMsg;
			if (failOnColumnNotFound)
			{
				throw new AdempiereException(msg);
			}
			else
			{
				log.error(msg);
			}
		}
	}

	public final GridTab getGridTab()
	{
		return this.m_gridTab;
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
	private final Object getReferencedObject(String propertyName, Method method)
	{
		final String idPropertyName = propertyName + "_ID";
		final GridField idField = m_gridTab.getField(idPropertyName);
		if (idField == null)
		{
			log.warn("Field " + idPropertyName + " not found for " + m_gridTab + ". Assuming null value.");
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
		if (idField.getVO().isParentLink())
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
		final GridTab parentGridTab = m_gridTab.getParentGridTab();
		if (parentGridTab == null)
		{
			return null;
		}

		if (parentGridTab.getRecord_ID() != parentRecordId)
		{
			return null;
		}

		final String modelTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (modelTableName == null)
		{
			return null;
		}

		if (!modelTableName.equals(parentGridTab.getTableName()))
		{
			return null;
		}

		return create(parentGridTab, modelClass);
	}

	private boolean isModelInterface(Class<?> cl)
	{
		try
		{
			String tableName = (String)cl.getField("Table_Name").get(null);
			return tableName != null;
		}
		catch (Exception e)
		{
			return false;
		}

	}

	public static boolean isHandled(Object model)
	{
		return getGridTab(model) != null;
	}

	public static void save(Object model)
	{
		GridTab gridTab = getGridTab(model);

		// We save the GridTab because else, even if there is no change it will return false
		boolean manualCmd = false;
		boolean ok = gridTab.dataSave(manualCmd);
		if (!ok)
			throw new AdempiereException("Error saving " + model); // TODO: fetch the actual error message
	}

	/**
	 * Check if given columnName's value is null
	 * 
	 * @param model
	 * @param columnName
	 * @return true if columnName's value is null
	 */
	public static boolean isNull(Object model, String columnName)
	{
		final GridTab gridTab = getGridTab(model);
		if (gridTab == null)
		{
			return true;
		}

		final Object value = gridTab.getValue(columnName);
		return value == null;
	}

	public static boolean hasColumnName(final Object model, final String columnName)
	{
		final GridTab gridTab = getGridTab(model);
		if (gridTab == null)
		{
			return false;
		}

		final GridField gridField = gridTab.getField(columnName);
		return gridField != null;
	}

	public final boolean hasColumnName(final String columnName)
	{
		final GridField gridField = getGridTab().getField(columnName);
		return gridField != null;
	}

	public static int getId(Object model)
	{
		return getGridTab(model).getRecord_ID();
	}

	public static boolean isNew(Object model)
	{
		return getGridTab(model).getRecord_ID() <= 0;
	}

	public <T> T getDynAttribute(final String attributeName)
	{
		if (recordId2dynAttributes == null)
		{
			return null;
		}

		final int recordId = getGridTab().getRecord_ID();
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

		final int recordId = getGridTab().getRecord_ID();
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

	public final IModelInternalAccessor getModelInternalAccessor()
	{
		return modelInternalAccessorSupplier.get();
	}

	public final boolean isOldValues()
	{
		return useOldValues;
	}
	
	public static final boolean isOldValues(final Object model)
	{
		final GridTabWrapper wrapper = getWrapper(model);
		return wrapper == null ? false : wrapper.isOldValues();
	}

}
