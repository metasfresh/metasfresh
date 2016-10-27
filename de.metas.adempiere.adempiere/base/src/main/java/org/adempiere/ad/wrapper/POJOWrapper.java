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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.persistence.ModelClassIntrospector;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Table;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee2;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Simple implementation which binds an given interface to a internal Map.
 *
 * @author tsa
 *
 */
public class POJOWrapper implements InvocationHandler, IInterfaceWrapper
{
	private static final transient Logger log = LogManager.getLogger(POJOWrapper.class);

	public static final int DEFAULT_VALUE_int = 0;
	public static final int DEFAULT_VALUE_ID = -1;
	public static final BigDecimal DEFAULT_VALUE_BigDecimal = BigDecimal.ZERO;
	private static final AtomicLong nextInstanceId = new AtomicLong(0);

	private static final String DYNATTR_IsJustCreated = POJOWrapper.class.getName() + "#IsJustCreated";

	public static <T> T create(final Properties ctx, final Class<T> cl)
	{
		return create(ctx, cl, POJOLookupMap.get());
	}

	/**
	 *
	 * @param ctx
	 * @param cl
	 * @param trxName
	 * @return
	 */
	public static <T> T create(final Properties ctx, final Class<T> cl, final String trxName)
	{
		final T object = create(ctx, cl);

		// task 05468: setting trxName to that this wrapper instance can be used as context provider
		final POJOWrapper wrapper = getWrapper(object);
		wrapper.setTrxName(trxName);

		return object;
	}

	public static <T> T create(final Properties ctx, final String tableName, final Class<T> interfaceClass, final String trxName)
	{
		final POJOLookupMap lookup = POJOLookupMap.get();
		final Map<String, Object> values = null;
		final POJOWrapper wrapper = new POJOWrapper(ctx, tableName, interfaceClass, values, lookup);

		@SuppressWarnings("unchecked")
		final T object = (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass },
				wrapper);

		setTrxName(object, trxName);
		return object;
	}

	public static <T> T create(final Properties ctx, final int id, final Class<T> cl, final String trxName)
	{
		final T result = POJOLookupMap.get().lookup(cl, id);

		// task 05468: setting trxName to that this wrapper instance can be used as context provider
		final POJOWrapper wrapper = getWrapper(result);
		wrapper.setTrxName(trxName);

		return result;
	}

	public static <T> T create(final Properties ctx, final String tableName, final int id, final Class<T> cl, final String trxName)
	{
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final Object model = POJOLookupMap.get().lookup(tableId, id);
		return create(model, cl);
	}

	public static <T> T create(final Properties ctx, final Class<T> cl, final IPOJOLookupMap lookup)
	{
		@SuppressWarnings("unchecked")
		final T object = (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				new POJOWrapper(ctx, cl, lookup));

		createADTableInstanceIfNeccesary(ctx, cl); // see the method's javadoc

		return object;
	}

	public static <T> T create(final Object model, final Class<T> cl)
	{
		final Boolean useOldValues = null; // take this flag from model
		return create(model, cl, useOldValues);
	}

	public static <T> T create(final Object model, final Class<T> cl, final Boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}

		boolean useOldValuesEffective = useOldValues == null ? false : useOldValues;

		final boolean modelIsInstaceOfGivenClass = cl.isInstance(model);
		if (!useOldValuesEffective && modelIsInstaceOfGivenClass)
		{
			@SuppressWarnings("unchecked")
			final T result = (T)model;
			return result;
		}

		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			throw new RuntimeException("Model not supported: " + model);
		}

		if (useOldValues == null)
		{
			useOldValuesEffective = wrapper.useOldValues;
		}

		if (wrapper.useOldValues == useOldValuesEffective && modelIsInstaceOfGivenClass)
		{
			@SuppressWarnings("unchecked")
			final T result = (T)model;
			return result;
		}

		final POJOWrapper wrapperNew = new POJOWrapper(cl, wrapper);
		wrapperNew.useOldValues = useOldValuesEffective;

		@SuppressWarnings("unchecked")
		final T result = (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				wrapperNew);

		setTrxName(result, InterfaceWrapperHelper.getTrxName(model)); // make sure that the model's trxName is forwarded to the new wrapper

		createADTableInstanceIfNeccesary(getCtx(result, false), cl); // see the method's javadoc

		return result;
	}

	/**
	 * If the given <code>cl</code> has a table name (see {@link #getTableNameOrNull(Class)}), then this method makes sure that there is also an <code>I_AD_Table</code> POJO. This can generally be
	 * assumed when running against a DB and should also be made sure when running unti tests in decoupled mode.
	 *
	 * @param ctx
	 * @param cl
	 */
	private static <T> void createADTableInstanceIfNeccesary(final Properties ctx, final Class<T> cl)
	{
		if (!Adempiere.isUnitTestMode())
		{
			return; // we are not in unit test mode. Don't create in-memory objects on the fly.
		}
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
		if (Check.isEmpty(tableName) || Check.equals(I_AD_Table.Table_Name, tableName))
		{
			return; // the given class has no table name.
		}

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final I_AD_Table existingInstance = create(ctx, I_AD_Table.Table_Name, tableId, I_AD_Table.class, ITrx.TRXNAME_None);
		if (existingInstance != null)
		{
			return; // there is already an I_AD_Table instance.
		}

		final ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);
		trxConstraintsBL.saveConstraints();
		try
		{
			trxConstraintsBL.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);
			final I_AD_Table newInstance = create(ctx, I_AD_Table.class, ITrx.TRXNAME_None);
			newInstance.setName(tableName);
			newInstance.setTableName(tableName);
			newInstance.setAD_Table_ID(tableId);
			save(newInstance);
		}
		finally
		{
			trxConstraintsBL.restoreConstraints();
		}
	}

	public static <T> T copy(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			throw new RuntimeException("Model not supported: " + model);
		}

		//
		// Delete model objects and DYNATTRs
		final Map<String, Object> valuesCopy = wrapper.copyValues();

		final POJOWrapper wrapperCopy = new POJOWrapper(wrapper.ctx,
				wrapper.tableName,
				wrapper.interfaceClass,
				valuesCopy,
				wrapper.lookup);
		wrapperCopy.instanceName = wrapper.instanceName;
		wrapperCopy.dynAttrs = null;

		@SuppressWarnings("unchecked")
		final T modelCopy = (T)Proxy.newProxyInstance(
				wrapper.interfaceClass.getClassLoader(),     // FIXME: better store the class loader as class field and access it; check on InterfaceWrapperHelper and do the same
				new Class<?>[] { wrapper.interfaceClass },
				wrapperCopy);

		return modelCopy;
	}

	/**
	 * Creates a copy of {@link #values} but remove dynamic attributes and cached models from inside.
	 *
	 * @return copy of {@link #values} which contains only column name values.
	 */
	private Map<String, Object> copyValues()
	{
		final Map<String, Object> values = getInnerValues();
		final Map<String, Object> valuesCopy = new HashMap<String, Object>(values);
		for (final Iterator<Entry<String, Object>> it = valuesCopy.entrySet().iterator(); it.hasNext();)
		{
			final Entry<String, Object> e = it.next();
			final Object value = e.getValue();

			// Remove models
			if (isHandled(value))
			{
				it.remove();
			}
		}

		return valuesCopy;
	}

	public static Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		final POJOWrapper wrapper = getWrapper(model);

		if (wrapper != null)
		{
			final Properties ctxPO = wrapper.getCtx();
			final Properties ctx;
			if (useClientOrgFromModel)
			{
				final int adClientId = wrapper.getAD_Client_ID();
				final int adOrgId = wrapper.getAD_Org_ID();

				if (Env.getAD_Client_ID(ctxPO) != adClientId
						|| Env.getAD_Org_ID(ctxPO) != adOrgId)
				{
					ctx = Env.deriveCtx(ctxPO); // won't change the original.
					Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
					Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
				}
				else
				{
					// we have the same AD_Client_ID and AD_Org_ID
					// => we can use PO's context directly, no need to create a new instance
					ctx = ctxPO;
				}
			}
			else
			{
				ctx = ctxPO;
			}
			return ctx;
		}

		// Notify developer that (s)he is using wrong models
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException e = new AdempiereException("Cannot get context from model " + model + " because is not supported. Returning global context.");
			log.warn(e.getLocalizedMessage(), e);
		}

		return Env.getCtx();
	}

	public int getAD_Client_ID()
	{
		final Integer adClientId = (Integer)getValue("AD_Client_ID", Integer.class);
		return adClientId == null ? 0 : adClientId;
	}

	public int getAD_Org_ID()
	{
		final Integer adOrgId = (Integer)getValue("AD_Org_ID", Integer.class);
		return adOrgId == null ? 0 : adOrgId;
	}

	// task 05468: adding this field to get and set, just so that the pojoWrapper acts like a "real" ctx-provider that can pass on a trxName
	private String _trxName = ITrx.TRXNAME_None;

	public static String getTrxName(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return ITrx.TRXNAME_None;
		}
		return wrapper.getTrxName();
	}

	public static void setTrxName(final Object model, final String trxName)
	{
		final POJOWrapper wrapper = getWrapper(model);
		Check.assumeNotNull(wrapper, "Wrapper for model {} is not null", model);
		wrapper.setTrxName(trxName);
	}

	public static POJOWrapper getWrapper(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (Proxy.isProxyClass(model.getClass()))
		{
			final InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof POJOWrapper)
			{
				final POJOWrapper wrapper = (POJOWrapper)ih;
				return wrapper;
			}
			return null;
		}
		else if (model instanceof POJOWrapper)
		{
			return (POJOWrapper)model;
		}

		return null;

	}

	public static boolean isHandled(final Object model)
	{
		return getWrapper(model) != null;
	}

	public static void save(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		wrapper.getLookupMap().save(model);

		// Reset Old values
		wrapper.valuesOld.clear();
	}

	// private final transient Logger log = CLogMgt.getLogger(getClass());
	private final long instanceId;
	private final Properties ctx;
	private final IPOJOLookupMap lookup;
	private final String tableName;
	private final Class<?> interfaceClass;
	private final Map<String, Object> values;
	private final Map<String, Object> valuesRO;
	private int loadCount = 0;

	/**
	 * Old Values map.
	 *
	 * NOTE: this map will contain values only for those properties that were changed
	 */
	private final Map<String, Object> valuesOld;
	private boolean useOldValues = false;
	private final String idColumnName;

	private boolean strictValues = false;
	public static final boolean DEFAULT_StrictValues = false;
	private static boolean strictValuesDefault = DEFAULT_StrictValues;
	private static boolean allowRefreshingChangedModels = false;

	/**
	 * Optional instance's name to be used in debugging. If set, it will be used when converting this object to string
	 */
	private String instanceName;

	private POJOWrapper(final Properties ctx,
			final String tableName, final Class<?> interfaceClass,
			final Map<String, Object> values,
			final IPOJOLookupMap lookup)
	{
		super();

		Check.assumeNotNull(tableName, "tableName not null (interfaceClass={})", interfaceClass);

		instanceId = nextInstanceId.incrementAndGet();
		this.ctx = ctx;
		this.interfaceClass = interfaceClass;
		this.tableName = tableName; // InterfaceWrapperHelper.getTableName(interfaceClass);
		idColumnName = tableName + "_ID";
		this.lookup = lookup;

		this.values = new HashMap<String, Object>();
		valuesRO = Collections.unmodifiableMap(this.values);
		valuesOld = new HashMap<String, Object>();

		// Standard values:
		this.values.put("IsActive".toLowerCase(), true);

		if (!"AD_Client_ID".equals(idColumnName))
		{
			this.values.put("AD_Client_ID".toLowerCase(), Env.getAD_Client_ID(ctx));
		}

		if (!"AD_Org_ID".equals(idColumnName))
		{
			this.values.put("AD_Org_ID".toLowerCase(), Env.getAD_Org_ID(ctx));
		}

		if (values != null)
		{
			this.values.putAll(values);
		}

		strictValues = strictValuesDefault;

		if (isNew())
		{
			setJustCreated();
		}
	}

	private POJOWrapper(final Properties ctx, final Class<?> interfaceClass, final IPOJOLookupMap lookup)
	{
		this(ctx, InterfaceWrapperHelper.getTableName(interfaceClass), interfaceClass, null, lookup); // values = null
	}

	private POJOWrapper(final Class<?> interfaceClass, final POJOWrapper parentWrapper)
	{
		super();
		instanceId = nextInstanceId.incrementAndGet();
		ctx = parentWrapper.getCtx();
		this.interfaceClass = interfaceClass;

		final String interfaceTableName = InterfaceWrapperHelper.getTableNameOrNull(interfaceClass);
		if (interfaceTableName == null)
		{
			tableName = parentWrapper.tableName;
		}
		else
		{
			Check.assume(interfaceTableName.equals(parentWrapper.tableName), "Parent wrapper must use tablename '{}' instead of '{}'", interfaceTableName, parentWrapper.tableName);
			tableName = interfaceTableName;
		}
		Check.assumeNotNull(tableName, "No TableName found for {} using {}", interfaceClass, parentWrapper);

		idColumnName = parentWrapper.idColumnName;

		lookup = parentWrapper.lookup;

		// instead of copying the Map, we are directly linking to it, to avoid duplicating the data
		values = parentWrapper.values;
		valuesRO = parentWrapper.valuesRO;
		valuesOld = parentWrapper.valuesOld;
		strictValues = strictValuesDefault;

		if (parentWrapper.isJustCreated())
		{
			setJustCreated();
		}
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public Class<?> getInterfaceClass()
	{
		return interfaceClass;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
	{
		final String methodName = method.getName();

		if (methodName.equals("set_TrxName") && args.length == 1)
		{
			final String trxName = (String)args[0];
			this.setTrxName(trxName);
			return null;
		}
		else if (methodName.startsWith("set") && args.length == 1)
		{
			final String propertyNameLowerCase = methodName.substring(3).toLowerCase();
			final Class<?> paramType = method.getParameterTypes()[0];
			final Object value = args[0];
			if (isModelInterface(paramType))
			{
				setReferencedObject(propertyNameLowerCase, value);
				// throw new AdempiereException("Object setter not supported: " + method);
				// setValueFromPO(propertyName + "_ID", paramType, value);
			}
			else
			{
				setValue(propertyNameLowerCase, value);
			}
			return null;
		}
		else if (methodName.equals("get_TrxName") && (args == null || args.length == 0))
		{
			return this.getTrxName();
		}
		else if (methodName.startsWith("get") && (args == null || args.length == 0))
		{
			final String propertyNameLowerCase = methodName.substring(3).toLowerCase();

			if (propertyNameLowerCase.equals(idColumnName.toLowerCase()))
			{
				return getId();
			}
			if (isModelInterface(method.getReturnType()))
			{
				final Object referencedObject = getReferencedObject(propertyNameLowerCase, method);
				if (referencedObject != null)
				{
					final String referencedObjectTrxName = getTrxName(referencedObject);
					Check.assume(Check.equals(this.getTrxName(), referencedObjectTrxName), "Invalid transaction"); // shall not happen, never ever
					// POJOWrapper.setTrxName(referencedObject, POJOWrapper.getTrxName(this));
				}
				return referencedObject;
			}

			Object value = getValue(propertyNameLowerCase, method.getReturnType());
			if (value != null)
			{
				return value;
			}
			//
			if (method.getReturnType() == int.class)
			{
				if (propertyNameLowerCase.endsWith("_ID".toLowerCase()))
				{
					value = DEFAULT_VALUE_ID;
				}
				else
				{
					value = DEFAULT_VALUE_int;
				}
			}
			else if (method.getReturnType() == BigDecimal.class)
			{
				value = DEFAULT_VALUE_BigDecimal;
			}
			else if (PO.class.isAssignableFrom(method.getReturnType()))
			{
				throw new IllegalArgumentException("Method not supported - " + methodName);
			}
			return value;
		}
		else if (methodName.startsWith("is") && (args == null || args.length == 0))
		{
			final Map<String, Object> values = getInnerValues();
			final String propertyNameLowerCase = methodName.substring(2).toLowerCase();
			final Object value;
			if (values.containsKey(propertyNameLowerCase))
			{
				value = getValue(propertyNameLowerCase, method.getReturnType());
			}
			else if (values.containsKey("Is".toLowerCase() + propertyNameLowerCase))
			{
				value = getValue("Is" + propertyNameLowerCase, method.getReturnType());
			}
			else
			{
				if (strictValues)
				{
					throw new IllegalStateException("No property " + propertyNameLowerCase + " was defined for bean " + this);
				}

				value = Boolean.FALSE;
			}

			// System.out.println("values="+values+", propertyName="+propertyName+" => value="+value);
			return value == null ? false : (Boolean)value;
		}
		else if (methodName.equals("equals") && args.length == 1)
		{
			return invokeEquals(args);
		}
		else if (methodName.equals("hashCode") && (args == null || args.length == 0))
		{
			return invokeHashCode();
		}
		else if (methodName.equals("toString") && (args == null || args.length == 0))
		{
			return toString();
		}
		else
		{
			throw new IllegalStateException("Method not supported: " + method);
		}
	}

	void setReferencedObject(final String propertyName, final Object value)
	{
		final String idPropertyName = propertyName + "_ID";
		if (value == null)
		{
			final Map<String, Object> values = getInnerValuesToSet();
			values.remove(propertyName);
			values.put(idPropertyName, DEFAULT_VALUE_ID);
			return;
		}

		final int valueId = InterfaceWrapperHelper.getId(value);
		setValue(idPropertyName, valueId);
		setValue(propertyName, value);
	}

	@Override
	public String toString()
	{
		return toString(new ArrayList<String>());
	}

	private String toString(final List<String> trace)
	{
		final StringBuilder sb = new StringBuilder("POJOWrapper[");
		sb.append(interfaceClass);

		if (!Check.isEmpty(instanceName, true))
		{
			sb.append(", instanceName=").append(instanceName);
		}

		sb.append(", instanceId=").append(instanceId);

		sb.append(", ID=").append(getId());

		sb.append(", trxName=").append(_trxName);

		if (useOldValues)
		{
			sb.append(", OLD VALUES");
		}

		final boolean printReferencedModels = !useOldValues && isPrintReferencedModels();

		final Map<String, Object> values = getInnerValues();
		if (values != null && !values.isEmpty())
		{
			final StringBuilder sbValues = new StringBuilder();

			final List<String> columnNames = new ArrayList<String>(values.keySet());
			Collections.sort(columnNames);
			for (final String name : columnNames)
			{
				final Object value = values.get(name);
				final String valueStr;
				final POJOWrapper wrapper = getWrapper(value);
				if (wrapper == null)
				{
					if (useOldValues && valuesOld.containsKey(name))
					{
						valueStr = String.valueOf(valuesOld.get(name));
					}
					else
					{
						valueStr = String.valueOf(value);
					}
				}
				else if (printReferencedModels)
				{
					final String traceKey = wrapper.getTableName() + "#" + wrapper.getId();
					if (trace.contains(traceKey))
					{
						valueStr = "<cycle=" + traceKey + ">";
					}
					else
					{
						final List<String> trace2 = new ArrayList<String>(trace);
						trace2.add(traceKey);
						valueStr = wrapper.toString(trace2);
					}
				}
				else
				{
					// we are skipping this value because we are not printing referenced models
					continue;
				}

				if (sbValues.length() > 0)
				{
					sbValues.append(", ");
				}
				sbValues.append(name).append("=").append(valueStr);

				if ("AD_Table_ID".equals(name) && value != null && value instanceof Number)
				{
					final int valueInt = ((Number)value).intValue();
					if (valueInt > 0)
					{
						final String tableName = Services.get(IADTableDAO.class).retrieveTableName(valueInt);
						sbValues.append("(").append(tableName).append(")");
					}
				}
			}
			sb.append(", values={").append(sbValues).append("}");
		}

		// sb.append(", values=").append(values);
		sb.append("]");

		return sb.toString();
	}

	Object getReferencedObject(final String columnName, final Method method)
	{
		String propertyName = columnName;
		if (propertyName.toLowerCase().endsWith("_ID".toLowerCase()))
		{
			propertyName = propertyName.substring(0, propertyName.length() - 3);
		}

		final Class<?> returnType = method.getReturnType();

		return getModelValue(propertyName, returnType);
	}

	public <T> T getModelValue(final String propertyName, final Class<T> returnType)
	{
		//
		// Get the column name which contains the ID of model that we are going to retrieve
		// NOTE: atm we are supporting both cases (e.g. M_Product and M_Product_ID) to be backward compatible, but in future we shall support only the "M_Product_ID" like columns.
		final String idColumnName;
		if (propertyName.toLowerCase().endsWith("_ID".toLowerCase()))
		{
			idColumnName = propertyName;
		}
		else
		{
			idColumnName = propertyName + "_ID";
		}

		//
		// Get ID column value
		final int id;
		if (hasColumnName(idColumnName))
		{
			final Integer idObj = (Integer)getValue(idColumnName, Integer.class);
			if (idObj == null)
			{
				id = -1;
			}
			else if (idObj < 0)
			{
				id = -1;
			}
			else
			{
				id = idObj;
			}
		}
		else
		{
			id = -1;
		}

		//
		// Get ValueCached and ID
		final Object valueCachedObj = getValue(propertyName, Object.class, false); // enforceStrictValues=false
		final T valueCached;
		final POJOWrapper valueCachedWrapper;
		final int valueCachedId;
		if (valueCachedObj == null)
		{
			valueCachedId = -1;
			valueCached = null;
			valueCachedWrapper = null;
		}
		else
		{
			final POJOWrapper wrapper = getWrapper(valueCachedObj);
			if (wrapper == null)
			{
				valueCachedId = -1;
				valueCached = null;
				valueCachedWrapper = wrapper;
			}
			else if (wrapper.getId() <= 0)
			{
				valueCachedId = -1;
				if (id >= 0)
				{
					// Case: we have a cached object which was not saved, but in meantime we have an ID set there
					valueCached = null;
					valueCachedWrapper = null;
				}
				else
				{
					valueCached = InterfaceWrapperHelper.create(valueCachedObj, returnType);
					valueCachedWrapper = wrapper;
				}
			}
			else
			{
				valueCachedId = wrapper.getId();
				valueCached = InterfaceWrapperHelper.create(valueCachedObj, returnType);
				valueCachedWrapper = wrapper;
			}
		}

		//
		// Value cached has same ID
		if (valueCachedId == id
				&& valueCachedWrapper != null
				&& Check.equals(this.getTrxName(), valueCachedWrapper.getTrxName()))
		{
			if (valueCachedId > 0 && !valueCachedWrapper.hasChanges())
			{
				final boolean discardChanges = false;

				final int loadCountOld = valueCachedWrapper.loadCount;
				valueCachedWrapper.refresh(discardChanges, this.getTrxName());

				// Restore the loadCount because this wasn't an actual load
				// We do this because business logic that depends on this flag to check if an object was reloaded in meantime would get false alarms in case this is changed here.
				valueCachedWrapper.loadCount = loadCountOld;
			}
			return valueCached;
		}

		if (id < 0)
		{
			return null;
		}

		final T valueLoaded = InterfaceWrapperHelper.create(ctx, id, returnType, getTrxName());

		if (!useOldValues)
		{
			final Map<String, Object> valuesRW = getInnerValuesToSet();
			valuesRW.put(propertyName, valueLoaded);
		}

		return valueLoaded;
	}

	public Object getValue(final String propertyName, final Class<?> returnType)
	{
		return getValue(propertyName, returnType, strictValues);
	}

	private Object getValue(final String propertyName, final Class<?> returnType, final boolean enforceStrictValues)
	{
		final Map<String, Object> values = getInnerValues();

		final String propertyNameToUse = propertyName.toLowerCase();

		if (enforceStrictValues && !values.containsKey(propertyNameToUse))
		{
			throw new IllegalStateException("No property " + propertyName + " was defined for bean " + this + "."
					+ "\n\n You can:"
					+ "\n * figure it out why " + propertyName + " was not set previously in your test."
					+ "\n * if it's not relevant you can turn off strict values in our test: POJOWrapper.setDefaultStrictValues(true);");
		}

		if (useOldValues && valuesOld.containsKey(propertyNameToUse))
		{
			return valuesOld.get(propertyNameToUse);
		}

		return values.get(propertyNameToUse);
	}

	public void setValue(final String propertyName, final Object value)
	{
		final String propertyNameToUse = propertyName.toLowerCase();

		if (propertyNameToUse.equalsIgnoreCase(idColumnName))
		{
			final int idOld = getId();
			final int id = toId(value);
			if (idOld == id)
			{
				return;
			}

			if (idOld > 0 && id <= 0)
			{
				throw new AdempiereException("Cannot reset ID for " + this);
			}
		}

		final Map<String, Object> values = getInnerValuesToSet();
		final Object valueOld = values.put(propertyNameToUse, value);

		if (!valuesOld.containsKey(propertyNameToUse)
				&& !Check.equals(valueOld, value))
		{
			valuesOld.put(propertyNameToUse, valueOld);
		}

		//
		// Reset cached model if any and if value is "-1"
		if (propertyNameToUse.endsWith("_ID".toLowerCase()))
		{
			final int id = toId(value);
			if (id < 0)
			{
				final String modelPropertyName = propertyNameToUse.substring(0, propertyNameToUse.length() - 3);
				values.put(modelPropertyName, null);
			}
		}
	}

	private boolean isModelInterface(final Class<?> cl)
	{
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
		return tableName != null;
	}

	boolean invokeEquals(final Object[] args)
	{
		final POJOWrapper wrapper2 = getWrapper(args[0]);
		final boolean result = equals(wrapper2);

		return result;
	}

	private Object invokeHashCode()
	{
		final int result = hashCode();
		return result;
	}

	public int getId()
	{
		final Integer id = (Integer)getValue(idColumnName, Integer.class, false); // enforceStrictValues=false
		return id == null ? -1 : id.intValue();
	}

	public void setId(final int id)
	{
		setValue(idColumnName, id);
	}

	public String getTableName()
	{
		return tableName;
	}

	public Set<String> getColumnNames()
	{
		return ModelClassIntrospector.getInstance()
				.getModelClassInfo(interfaceClass)
				.getDefinedColumnNames();
	}

	public boolean hasColumnName(final String columnName)
	{
		if (getInnerValues().containsKey(columnName.toLowerCase()))
		{
			return true;
		}

		// Do we have COLUMNNAME_* field?
		final boolean hasColumnName = ModelClassIntrospector
				.getInstance()
				.getModelClassInfo(interfaceClass)
				.getDefinedColumnNames()
				.contains(columnName);
		return hasColumnName;
	}

	public boolean isKeyColumnName(final String columnName)
	{
		return columnName != null && columnName.equals(idColumnName);
	}

	public boolean isCalculated(final String columnName)
	{
		// TODO: implement support for IsCalculated
		return false;
	}

	public boolean isVirtualColumn(final String columnName)
	{
		// TODO implement support for virtual columns
		return false;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (idColumnName == null ? 0 : idColumnName.hashCode());
		result = prime * result + (tableName == null ? 0 : tableName.hashCode());
		result = prime * result + (values == null ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}

		//
		// ID Column Name
		final POJOWrapper other = (POJOWrapper)obj;
		if (idColumnName == null)
		{
			if (other.idColumnName != null)
			{
				return false;
			}
		}
		else if (!idColumnName.equals(other.idColumnName))
		{
			return false;
		}

		//
		// Table Name
		if (tableName == null)
		{
			if (other.tableName != null)
			{
				return false;
			}
		}
		else if (!tableName.equals(other.tableName))
		{
			return false;
		}

		//
		// Values
		if (values == null)
		{
			if (other.values != null)
			{
				return false;
			}
		}
		else if (!Check.equals(copyValues(), other.copyValues()))
		{
			return false;
		}
		return true;
	}

	public IPOJOLookupMap getLookupMap()
	{
		return lookup;
	}

	/**
	 *
	 * @return a copy of column values. This map does not contain dynamic attributes or models.
	 */
	public Map<String, Object> getValuesMap()
	{
		return copyValues();
	}

	/**
	 *
	 * @return inner {@link #valuesRO} map (readonly!) which contains column values, dynamic attributes and cached models.
	 */
	private Map<String, Object> getInnerValues()
	{
		return valuesRO;
	}

	/**
	 * Get inner values for changing it.
	 *
	 * @return inner {@link #values} map (read-write)
	 */
	private Map<String, Object> getInnerValuesToSet()
	{
		Check.assume(!useOldValues, "useOldValues shall be false");
		return values;
	}

	public static void delete(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		wrapper.getLookupMap().delete(model);
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
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return true;
		}

		final Object value = wrapper.getValue(columnName, Object.class);
		if (value == null)
		{
			return true;
		}

		//
		// Special case: lookup columns
		if (columnName.toLowerCase().endsWith("_ID".toLowerCase())
				&& value instanceof Integer
				&& (Integer)value == DEFAULT_VALUE_ID)
		{
			return true;
		}

		return false;
	}

	/**
	 *
	 * @param model
	 * @param discardChanges
	 * @param trxName
	 */
	public static void refresh(final Object model, final boolean discardChanges, final String trxName)
	{
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			throw new AdempiereException("Not supported model: " + model);
		}

		wrapper.refresh(discardChanges, trxName);
	}

	private final void assertNoChangesBeforeRefresh()
	{
		if (allowRefreshingChangedModels)
		{
			return;
		}

		if (!hasChanges())
		{
			return;
		}

		//
		// Gather more info and build a nice exception
		final Map<String, Object> values = getInnerValues();
		final StringBuilder changedColumns = new StringBuilder();
		for (final Map.Entry<String, Object> e : valuesOld.entrySet())
		{
			final String propertyName = e.getKey();
			final Object valueOld = e.getValue();
			final Object valueNew = values.get(propertyName);

			if (changedColumns.length() > 0)
			{
				changedColumns.append("\n");
			}
			changedColumns.append(propertyName).append(": '").append(valueOld).append("' -> '").append(valueNew).append("'");
		}

		throw new AdempiereException("Refreshing object when it has changes is not allowed."
				+ "\nIf you relly want to allow this in your tests, then try setting " + POJOWrapper.class.getName() + ".setAllowRefreshingChangedModels(true)."
				+ "\nModel: " + this
				+ "\n" + changedColumns);
	}

	public void refresh(final String trxName)
	{
		final boolean discardChanges = false;
		refresh(discardChanges, trxName);
	}

	/**
	 * Refresh this POJO.
	 *
	 * Design contract:
	 * <ul>
	 * <li>this method shall fail if the underlying database row does not exists anymore. Please keep it like this because there are tests were we rely on this to make sure that a given record still
	 * exists and it's not deleted.
	 * </ul>
	 *
	 * @param discardChanges
	 * @param trxName
	 */
	public void refresh(final boolean discardChanges, final String trxName)
	{
		if (!discardChanges)
		{
			assertNoChangesBeforeRefresh();
		}

		final Map<String, Object> values = getInnerValuesToSet();

		setTrxName(this, trxName); // doing it because this is also a side effect of PO.load(trxName) which is called when a PO is refreshed

		final Object record = lookup.lookup(tableName, getId(), interfaceClass);
		final POJOWrapper recordWrapper = getWrapper(record);
		if (recordWrapper == null)
		{
			throw new AdempiereException("No POJOWrapper was found in database for " + this
					+ "\ninterfaceClass: " + interfaceClass
					+ "\nTableName: " + tableName
					+ "\nID=" + getId());
		}
		else if (this == recordWrapper)
		{
			// same record, we assume CopyOnSave is not activated
			Check.assume(!lookup.isCopyOnSave(), "We got the same instance on refresh(). We assume CopyOnSave=false");
			return;
		}

		values.clear();
		valuesOld.clear();
		values.putAll(recordWrapper.getInnerValues());
		loadComplete();
	}

	private final void loadComplete()
	{
		loadCount++;
	}

	public void copyFrom(final POJOWrapper from)
	{
		final Map<String, Object> fromValues = from.getInnerValues();
		if (fromValues == null)
		{
			return;
		}

		final Map<String, Object> toValues = getInnerValuesToSet();

		for (final Map.Entry<String, Object> e : fromValues.entrySet())
		{
			final String columnName = e.getKey();
			if (idColumnName.equalsIgnoreCase(columnName))
			{
				// don't copy the ID column
				continue;
			}
			final Object value = e.getValue();
			toValues.put(columnName, value);
		}
	}

	public static void setInstanceName(final Object model, final String instanceName)
	{
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return;
		}

		wrapper.instanceName = instanceName;
	}

	public static String getInstanceName(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}

		return wrapper.instanceName;
	}

	/**
	 * Sets if the values shall be strict. By strict values we mean that if a value was not found in the map when the getter is called we will throw an exception.
	 *
	 * @param strictValues
	 */
	public void setStrictValues(final boolean strictValues)
	{
		this.strictValues = strictValues;
	}

	public boolean isStrictValues()
	{
		return strictValues;
	}

	public static void enableStrictValues(final Object model)
	{
		POJOWrapper.getWrapper(model).setStrictValues(true);
	}

	public static void disableStrictValues(final Object model)
	{
		POJOWrapper.getWrapper(model).setStrictValues(false);
	}

	public static void setDefaultStrictValues(final boolean enabled)
	{
		strictValuesDefault = enabled;
	}

	/**
	 * Set if we shall allow to {@link #refresh(Object)} an object which has changes.
	 *
	 * @param allow
	 */
	public static void setAllowRefreshingChangedModels(final boolean allow)
	{
		allowRefreshingChangedModels = allow;
	}

	private Map<String, Object> dynAttrs = null;

	public Object setDynAttribute(final String attributeName, final Object value)
	{
		if (dynAttrs == null)
		{
			dynAttrs = new HashMap<String, Object>();
		}
		return dynAttrs.put(attributeName, value);
	}

	public <T> T getDynAttribute(final String attributeName)
	{
		if (dynAttrs == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final T value = (T)dynAttrs.get(attributeName);
		return value;
	}

	public static Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		final POJOWrapper wrapper = getWrapper(model);
		final Object valueOld = wrapper.setDynAttribute(attributeName, value);
		return valueOld;
	}

	public static <T> T getDynAttribute(final Object model, final String attributeName)
	{
		final POJOWrapper wrapper = getWrapper(model);
		final T value = wrapper.getDynAttribute(attributeName);
		return value;
	}

	public static boolean isNew(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		return wrapper.isNew();
	}

	private boolean isNew()
	{
		return getId() <= 0;
	}

	public boolean isJustCreated()
	{
		final Boolean isJustCreated = getDynAttribute(DYNATTR_IsJustCreated);
		return isJustCreated != null && isJustCreated.booleanValue() == true;
	}

	private final void setJustCreated()
	{
		setDynAttribute(DYNATTR_IsJustCreated, true);
	}

	private static boolean printReferencedModels = true;

	public static void setPrintReferencedModels(final boolean print)
	{
		printReferencedModels = print;
	}

	/**
	 *
	 * @return if true then toString() method will also print referenced models (if available) and not just the IDs
	 */
	public static boolean isPrintReferencedModels()
	{
		return printReferencedModels;
	}

	private static final int toId(final Object value)
	{
		if (value == null)
		{
			return -1;
		}
		else if (value instanceof Integer)
		{
			final Integer valueInt = (Integer)value;
			if (valueInt <= 0)
			{
				return -1;
			}
			else
			{
				return valueInt;
			}
		}
		else if (value instanceof String)
		{
			// Case: C_BPartner.AD_OrgBP_ID
			final int valueInt = Integer.parseInt(value.toString());
			return valueInt <= 0 ? -1 : valueInt;
		}
		else
		{
			throw new AdempiereException("Cannot convert value '" + value + "' to ID");
		}
	}

	public static boolean hasColumnName(final Class<?> modelClass, final String columnName)
	{
		final String columnNameLC = columnName.toLowerCase();

		for (final Method method : modelClass.getMethods())
		{
			final String methodName = method.getName();
			if (methodName.equalsIgnoreCase("get" + columnName))
			{
				return true;
			}
			else if (methodName.equalsIgnoreCase("is" + columnName))
			{
				return true;
			}
			else if (columnNameLC.startsWith("is") && methodName.equalsIgnoreCase(columnName))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean isValueChanged(final Object model, final String propertyName)
	{
		final POJOWrapper wrapper = getWrapper(model);
		Check.assumeNotNull(wrapper, "wrapper not null for model {}", model);
		return isWrapperValueChanged(wrapper, propertyName);
	}

	/**
	 * @param model
	 * @param columnNames
	 * @return true if any of given column names where changed
	 */
	public static boolean isValueChanged(final Object model, final Set<String> propertyNames)
	{
		final POJOWrapper wrapper = getWrapper(model);
		Check.assumeNotNull(wrapper, "wrapper not null for model {}", model);

		for (final String propertyName : propertyNames)
		{
			if (isWrapperValueChanged(wrapper, propertyName))
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isWrapperValueChanged(final POJOWrapper wrapper, final String propertyName)
	{
		if (!wrapper.valuesOld.containsKey(propertyName))
		{
			return false;
		}
		final Object valueOld = wrapper.valuesOld.get(propertyName);
		final Object value = wrapper.getValue(propertyName, Object.class);
		final boolean changed = !Check.equals(valueOld, value);
		return changed;
	}

	public static boolean isRecordChanged(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		Check.assumeNotNull(wrapper, "wrapper not null for model {}", model);

		return wrapper.isRecordChanged();
	}

	public boolean isRecordChanged()
	{
		final Map<String, Object> values = getInnerValues();
		for (final String propertyName : values.keySet())
		{
			if (isValueChanged(propertyName))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isValueChanged(final String propertyName)
	{
		final Map<String, Object> values = getInnerValues();
		if (!valuesOld.containsKey(propertyName))
		{
			return false;
		}
		final Object valueOld = valuesOld.get(propertyName);
		final Object value = values.get(propertyName);

		// NOTE: in case we are dealing with a referenced model, return false because a change will be notified on it's ID column
		if (isReferencedObjectValue(propertyName, valueOld, value))
		{
			return false;
		}

		final boolean changed = !Check.equals(valueOld, value);
		return changed;
	}

	private boolean isReferencedObjectValue(final String propertyName, final Object valueOld, final Object valueNew)
	{
		// TODO: optimize this method

		final POJOWrapper wrapperOld = getWrapper(valueOld);
		if (wrapperOld != null)
		{
			return true;
		}

		final POJOWrapper wrapper = getWrapper(valueNew);
		if (wrapper != null)
		{
			return true;
		}

		return false;
	}

	public boolean hasChanges()
	{
		if (isNew())
		{
			return true;
		}

		final Set<String> changedPropertyNames = valuesOld.keySet();
		for (final String propertyName : changedPropertyNames)
		{
			if (isValueChanged(propertyName))
			{
				return true;
			}
		}

		return false;
	}

	public int getLoadCount()
	{
		return loadCount;
	}

	public final String getTrxName()
	{
		return _trxName;
	}

	public final String setTrxName(final String trxName)
	{
		final String trxNameOld = _trxName;
		_trxName = trxName;
		return trxNameOld;
	}

	public final boolean isOldValues()
	{
		return useOldValues;
	}

	public static final boolean isOldValues(final Object model)
	{
		return getWrapper(model).isOldValues();
	}

	public Evaluatee2 asEvaluatee()
	{
		return new Evaluatee2()
		{

			@Override
			public String get_ValueAsString(final String variableName)
			{
				if (!has_Variable(variableName))
				{
					return "";
				}
				final Object value = POJOWrapper.this.getValuesMap().get(variableName);
				return value == null ? "" : value.toString();
			}

			@Override
			public boolean has_Variable(final String variableName)
			{
				return POJOWrapper.this.hasColumnName(variableName);
			}

			@Override
			public String get_ValueOldAsString(final String variableName)
			{
				throw new UnsupportedOperationException("not implemented");
			}
		};
	}

	public IModelInternalAccessor getModelInternalAccessor()
	{
		if (_modelInternalAccessor == null)
		{
			_modelInternalAccessor = new POJOModelInternalAccessor(this);
		}
		return _modelInternalAccessor;
	}

	POJOModelInternalAccessor _modelInternalAccessor = null;

	public static IModelInternalAccessor getModelInternalAccessor(final Object model)
	{
		final POJOWrapper wrapper = getWrapper(model);
		if (wrapper == null)
		{
			return null;
		}
		return wrapper.getModelInternalAccessor();
	}
}
