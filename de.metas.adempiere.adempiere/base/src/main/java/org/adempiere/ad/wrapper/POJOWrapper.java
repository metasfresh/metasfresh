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
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nullable;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.persistence.ModelClassIntrospector;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Table;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee2;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocument;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

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
	public static final BigDecimal DEFAULT_VALUE_BigDecimal = BigDecimal.ZERO;

	public static final int ID_ZERO = 0;
	public static final int ID_MINUS_ONE = -1;

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

	public static <T> List<T> loadByIds(final Set<Integer> ids, final Class<T> modelClass, final String trxName)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		final Properties ctx = Env.getCtx();

		return ids.stream()
				.map(id -> create(ctx, id, modelClass, trxName))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
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
		if (Check.isEmpty(tableName) || Objects.equals(I_AD_Table.Table_Name, tableName))
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
				wrapper.interfaceClass.getClassLoader(),      // FIXME: better store the class loader as class field and access it; check on InterfaceWrapperHelper and do the same
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
		final Map<String, Object> valuesCopy = new HashMap<>(values);
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

		this.values = new HashMap<>();
		valuesRO = Collections.unmodifiableMap(this.values);
		valuesOld = new HashMap<>();

		// Standard values:
		this.values.put("IsActive", true);

		if (!"AD_Client_ID".equals(idColumnName))
		{
			this.values.put("AD_Client_ID", Env.getAD_Client_ID(ctx));
		}

		if (!"AD_Org_ID".equals(idColumnName))
		{
			this.values.put("AD_Org_ID", Env.getAD_Org_ID(ctx));
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
	public Object invoke(
			final Object proxy,
			@NonNull final Method method,
			@Nullable final Object[] args) throws Throwable
	{
		try
		{
			return invoke0(method, args);
		}
		catch (final AdempiereException e)
		{
			throw new AdempiereException("Error invoking method=\"" + method.getName() + "\"; proxy=" + proxy + "; args=" + args, e);
		}
	}

	private Object invoke0(
			@NonNull final Method method,
			@Nullable final Object[] args)
	{
		final String methodName = method.getName();

		if (methodName.equals("set_TrxName") && args.length == 1)
		{
			final String trxName = (String)args[0];
			this.setTrxName(trxName);
			return null;
		}
		else if (methodName.startsWith(IDocument.METHOD_NAME_getDocumentModel) && (args == null || args.length == 0))
		{
			return this; // return this instance; similar to the default implementation of IDocument.getDocumentModel()
		}
		else if (methodName.startsWith("set") && args.length == 1)
		{
			return invokeSet(method, args, methodName);
		}
		else if (methodName.equals("get_TrxName") && (args == null || args.length == 0))
		{
			return this.getTrxName();
		}
		else if (methodName.startsWith("get") && (args == null || args.length == 0))
		{
			return invokeGet(method, methodName);
		}
		else if (methodName.startsWith("is") && (args == null || args.length == 0))
		{
			return invokeIs(method, methodName);
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

	private Object invokeSet(
			@NonNull final Method method,
			@NonNull final Object[] args,
			@NonNull final String methodName)
	{
		final String propertyNameLowerCase = methodName.substring(3);
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

	private Object invokeGet(final Method method, final String methodName)
	{
		final String propertyNameLowerCase = methodName.substring(3);

		if (propertyNameLowerCase.equals(idColumnName))
		{
			return getId();
		}
		if (isModelInterface(method.getReturnType()))
		{
			final Object referencedObject = getReferencedObject(propertyNameLowerCase, method);
			if (referencedObject != null)
			{
				final String referencedObjectTrxName = getTrxName(referencedObject);
				Check.assume(Objects.equals(this.getTrxName(), referencedObjectTrxName), "Invalid transaction"); // shall not happen, never ever
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
			if (propertyNameLowerCase.endsWith("_ID"))
			{
				// e.g. if a PP_Order has no ASI, we still return 0 because that's the "NO-ASI"-asi's ID
				// value = idForNewModel(propertyNameLowerCase);
				value = ID_ZERO;
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

	private Object invokeIs(final Method method, final String methodName)
	{
		final Map<String, Object> values = getInnerValues();
		final String propertyNameLowerCase = methodName.substring(2);
		final Object value;
		if (values.containsKey(propertyNameLowerCase))
		{
			value = getValue(propertyNameLowerCase, method.getReturnType());
		}
		else if (values.containsKey("Is" + propertyNameLowerCase))
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

	void setReferencedObject(final String propertyName, final Object value)
	{
		final String idPropertyName = propertyName + "_ID";
		if (value == null)
		{
			final Map<String, Object> values = getInnerValuesToSet();
			values.remove(propertyName);
			// also for e.g. the M_AttributeSetInstance, we put null as zero (and not -1), because there is a "no-asi"-ASI with ID=null
			values.put(idPropertyName, ID_ZERO);
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
		if (!Check.isEmpty(instanceName, true))
		{
			sb.append("instanceName=").append(instanceName).append(", ");
		}

		sb.append(interfaceClass);

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

			final List<String> columnNames = new ArrayList<>(values.keySet());
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
						final List<String> trace2 = new ArrayList<>(trace);
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
		if (propertyName.endsWith("_ID"))
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
		final String modelIdColumnName;
		if (propertyName.endsWith("_ID"))
		{
			modelIdColumnName = propertyName;
		}
		else
		{
			modelIdColumnName = propertyName + "_ID";
		}

		//
		// Get ID column value
		final int id;
		if (hasColumnName(modelIdColumnName))
		{
			final Integer idObj = (Integer)getValue(modelIdColumnName, Integer.class);
			if (idObj == null)
			{
				id = idForNewModel(modelIdColumnName);
			}
			else if (idObj <= idForNewModel(modelIdColumnName))
			{
				id = idForNewModel(modelIdColumnName);
			}
			else
			{
				id = idObj;
			}
		}
		else
		{
			id = idForNewModel(modelIdColumnName);
		}

		//
		// Get ValueCached and ID
		final Object valueCachedObj = getValue(propertyName, Object.class, false); // enforceStrictValues=false
		final T valueCached;
		final POJOWrapper valueCachedWrapper;
		final int valueCachedId;
		if (valueCachedObj == null)
		{
			valueCachedId = idForNewModel(modelIdColumnName);
			valueCached = null;
			valueCachedWrapper = null;
		}
		else
		{
			final POJOWrapper wrapper = getWrapper(valueCachedObj);
			if (wrapper == null)
			{
				valueCachedId = idForNewModel(modelIdColumnName);
				valueCached = null;
				valueCachedWrapper = wrapper;
			}
			else if (wrapper.getId() <= idForNewModel(modelIdColumnName))
			{
				valueCachedId = idForNewModel(modelIdColumnName);
				if (id > idForNewModel(modelIdColumnName))
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
				&& Objects.equals(this.getTrxName(), valueCachedWrapper.getTrxName()))
		{
			if (valueCachedId > idForNewModel(modelIdColumnName) && !valueCachedWrapper.hasChanges())
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

		if (id <= idForNewModel(modelIdColumnName))
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

		if (enforceStrictValues && !values.containsKey(propertyName))
		{
			throw new IllegalStateException("No property " + propertyName + " was defined for bean " + this + "."
					+ "\n\n You can:"
					+ "\n * figure it out why " + propertyName + " was not set previously in your test."
					+ "\n * if it's not relevant you can turn off strict values in our test: POJOWrapper.setDefaultStrictValues(true);");
		}

		if (useOldValues && valuesOld.containsKey(propertyName))
		{
			return valuesOld.get(propertyName);
		}

		return values.get(propertyName);
	}

	public void setValue(
			@NonNull final String propertyName,
			@Nullable final Object value)
	{
		final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(propertyName);

		if (propertyName.equalsIgnoreCase(idColumnName))
		{
			final int idOld = getId();
			final int id = toId(value, propertyName);
			if (idOld == id)
			{
				return;
			}

			if (idOld > idForNewModel(propertyName) && id < firstValidId)
			{
				throw new AdempiereException("Cannot reset ID for " + this);
			}
		}

		final Map<String, Object> values = getInnerValuesToSet();
		final Object valueOld = values.put(propertyName, value);

		if (!valuesOld.containsKey(propertyName)
				&& !Objects.equals(valueOld, value))
		{
			valuesOld.put(propertyName, valueOld);
		}

		//
		// Reset cached model if any and id value is "-1"
		if (propertyName.endsWith("_ID"))
		{
			final int id = toId(value, propertyName);
			if (id < firstValidId)
			{
				final String modelPropertyName = propertyName.substring(0, propertyName.length() - 3);
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
		if (id != null)
		{
			return id;
		}
		return idForNewModel();
	}

	/**
	 * @return zero for most new models,
	 *         but e.g. for an unsaved {@code I_AD_User} record, it will return {@code -1},
	 *         because there actually is a saved AD_User with ID=0 (system user).
	 *
	 * @see InterfaceWrapperHelper#getFirstValidIdByColumnName(String)
	 */
	public int idForNewModel()
	{
		return idForNewModel(idColumnName);
	}

	private static int idForNewModel(@NonNull final String idColumnName)
	{
		return InterfaceWrapperHelper.getFirstValidIdByColumnName(idColumnName) - 1;
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
		if (getInnerValues().containsKey(columnName))
		{
			return true; // we have the column name and it is actually set to a value
		}

		// Do we have COLUMNNAME_* field?
		final boolean hasColumnName = ModelClassIntrospector
				.getInstance()
				.getModelClassInfo(interfaceClass)
				.getDefinedColumnNames()
				.stream()
				.filter(definedColName -> definedColName.equalsIgnoreCase(columnName))
				.findFirst()
				.isPresent();

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
		else if (!Objects.equals(copyValues(), other.copyValues()))
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
		if (columnName.endsWith("_ID") && value instanceof Integer)
		{
			final int id = (Integer)value;
			final boolean idIsNotValidForGivenColumnName = POWrapper.getFirstValidIdByColumnName(columnName) > id;
			if (idIsNotValidForGivenColumnName)
			{
				return true;
			}
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
				+ "\nIf you really want to allow this in your tests, then try setting " + POJOWrapper.class.getName() + ".setAllowRefreshingChangedModels(true)."
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
		if (record == null)
		{
			throw new AdempiereException("No record found for tableName=" + tableName + " and ID=" + getId()
					+ "\ninterfaceClass: " + interfaceClass
					+ "\nthis=" + this);
		}
		final POJOWrapper recordWrapper = getWrapper(record);
		if (recordWrapper == null)
		{
			throw new AdempiereException("No POJOWrapper found for tableName=" + tableName + " and ID=" + getId()
					+ "\ninterfaceClass: " + interfaceClass
					+ "\nrecord: " + record
					+ "\nthis=" + this);

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
			dynAttrs = new HashMap<>();
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

	private final int toId(final Object value, final String columnName)
	{
		if (value == null)
		{
			return idForNewModel();
		}
		else if (value instanceof Integer)
		{
			final Integer valueInt = (Integer)value;
			return Integer.max(valueInt, idForNewModel(columnName));
		}
		else if (value instanceof String)
		{
			// Case: C_BPartner.AD_OrgBP_ID
			final int valueInt = Integer.parseInt(value.toString());
			return Integer.max(valueInt, idForNewModel(columnName));
		}
		else if(value instanceof RepoIdAware)
		{
			return ((RepoIdAware)value).getRepoId();
		}
		else
		{
			throw new AdempiereException("Cannot convert value '" + value + "' to ID");
		}
	}

	public static boolean hasColumnName(final Class<?> modelClass, final String columnName)
	{
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
			else if (columnName.startsWith("is") && methodName.equalsIgnoreCase(columnName))
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
		final boolean changed = !Objects.equals(valueOld, value);
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

		final boolean changed = !Objects.equals(valueOld, value);
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
