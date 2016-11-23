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


import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorFactory;
import org.adempiere.ad.modelvalidator.CompositeModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.jmx.JMXPOJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.ModelValidator;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.monitoring.exception.MonitoringException;
import de.metas.process.IADPInstanceDAO;

public final class POJOLookupMap implements IPOJOLookupMap, IModelValidationEngine
{
	// services
	private static final POJOLookupMap instance = new POJOLookupMap("GLOBAL");
	private static final transient Logger logger = LogManager.getLogger(POJOLookupMap.class);
	// NOTE: don't add services here, because in testing we are reseting the Services quite offen

	private static final ThreadLocal<POJOLookupMap> threadInstanceRef = new ThreadLocal<POJOLookupMap>();

	public static POJOLookupMap get()
	{
		final POJOLookupMap threadInstance = threadInstanceRef.get();
		if (threadInstance != null)
		{
			return threadInstance;
		}

		//
		// Default
		if (org.compiere.Adempiere.isUnitTestMode())
		{
			return instance;
		}
		else
		{
			return null;
		}
	}

	public static final POJOLookupMap getInMemoryDatabaseForModel(final Class<?> modelClass)
	{
		final POJOLookupMap database = get();
		if (database == null)
		{
			return null;
		}

		if (!database.appliesToModelClass(modelClass))
		{
			return null;
		}

		return database;
	}

	public static final POJOLookupMap getInMemoryDatabaseForTableName(final String tableName)
	{
		final POJOLookupMap database = get();
		if (database == null)
		{
			return null;
		}

		if (!database.appliesToTableName(tableName))
		{
			return null;
		}

		return database;
	}

	public static POJOLookupMap createThreadLocalStorage()
	{
		POJOLookupMap threadInstance = threadInstanceRef.get();
		if (threadInstance == null)
		{
			final String name = "Thread-" + Thread.currentThread().getName();
			threadInstance = new POJOLookupMap(name);
			threadInstanceRef.set(threadInstance);
		}

		return threadInstance;
	}

	public static void destroyThreadLocalStorage()
	{
		final POJOLookupMap threadInstance = threadInstanceRef.get();
		if (threadInstance == null)
		{
			return;
		}
		threadInstanceRef.set(null);

		threadInstance.unregisterJMX();
		threadInstance.clear();
	}

	/**
	 * Master reset:
	 * <ul>
	 * <li>removes thread instances
	 * <li>resets the global instance
	 * </ul>
	 */
	public static void resetAll()
	{
		destroyThreadLocalStorage();

		//
		// Global instance
		instance.clear();
		instance.setCopyOnSave(true);
		instance.unregisterAllInterceptors();
	}

	// NOTE: because in some tests we are using hardcoded IDs which are like ~50000, we decided to start the IDs sequence from 100k.
	private static final int DEFAULT_FirstId = 100000;
	private int nextId = DEFAULT_FirstId;

	/**
	 * Database name
	 */
	private final String databaseName;

	/**
	 * Map of cached objects (TableName -> Record_ID -> Object)
	 */
	Map<String, Map<Integer, Object>> cachedObjects = new HashMap<String, Map<Integer, Object>>();
	Map<Integer, Set<Integer>> selectionId2selection = new HashMap<>();

	private boolean copyOnSave = true;

	/**
	 * Tool used to track how many object instances are released for a given table record.
	 */
	private final POJOLookupMapInstancesTracker instancesTracker = new POJOLookupMapInstancesTracker();

	private POJOLookupMap(final String name)
	{
		super();
		this.databaseName = name;

		registerJMX();
	}

	@Override
	public int nextId(String tableName)
	{
		nextId++;
		return nextId;
	}

	@Override
	public boolean isCopyOnSave()
	{
		return copyOnSave;
	}

	/**
	 *
	 * @param copyOnSave true if we want that values to be copied on save and not only referenced. Setting to true is like an actual database is working.
	 */
	public void setCopyOnSave(boolean copyOnSave)
	{
		this.copyOnSave = copyOnSave;
	}

	private <T> T copy(final T model)
	{
		if (copyOnSave)
		{
			return POJOWrapper.copy(model);
		}
		else
		{
			return model;
		}
	}

	private Object getCopy(final Map<Integer, Object> tableRecords, final int id)
	{
		final Object model = tableRecords.get(id);
		return copy(model);
	}

	@Override
	public <T> T lookup(Class<T> clazz, int id)
	{
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(clazz);
		if (tableName == null)
		{
			return null;
		}

		Map<Integer, Object> tableRecords = cachedObjects.get(tableName);
		if (tableRecords == null || tableRecords.isEmpty())
		{
			throw new RuntimeException("No cached object found for clazz=" + clazz + ", id=" + id);
		}

		final Object result = getCopy(tableRecords, id);
		if (result == null)
		{
			throw new RuntimeException("No cached object found for clazz=" + clazz + ", id=" + id);
		}

		if (clazz.isAssignableFrom(result.getClass()))
		{
			@SuppressWarnings("unchecked")
			final T resultConv = (T)result;
			return resultConv;
		}

		final T resultConv = POJOWrapper.create(result, clazz);
		return resultConv;
	}

	public <T> T lookup(int tableId, int recordId)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
		Check.assumeNotEmpty(tableName, "tableName shall exist for {}", tableId);

		return lookup(tableName, recordId);
	}

	public <T> T lookup(final String tableName, final int recordId)
	{
		final Map<Integer, Object> tableRecords = cachedObjects.get(tableName);
		if (tableRecords == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final T value = (T)getCopy(tableRecords, recordId);
		return value;
	}

	public <T> T lookup(final int tableId, final int recordId, final Class<T> modelClass)
	{
		final Object o = lookup(tableId, recordId);
		if (o == null)
		{
			return null;
		}
		return POJOWrapper.create(o, modelClass);
	}

	@Override
	public <T> T lookup(final String tableName, final int recordId, final Class<T> modelClass)
	{
		final Object o = lookup(tableName, recordId);
		if (o == null)
		{
			return null;
		}
		return POJOWrapper.create(o, modelClass);
	}

	public <T> T newInstance(Properties ctx, Class<T> interfaceClass)
	{
		final T model = POJOWrapper.create(ctx, interfaceClass, this);
		return model;
	}

	/**
	 * Create a new object using global ctx (Env.getCtx()) and Trx.TRX_None as transaction.
	 *
	 * @param interfaceClass
	 * @return
	 */
	public <T> T newInstance(Class<T> interfaceClass)
	{
		return newInstance(Env.getCtx(), interfaceClass);
	}

	/**
	 * <b>To be used by the API only!</b> Please use {@link org.adempiere.model.InterfaceWrapperHelper#save(Object)} instead.
	 */
	@Override
	public void save(final Object model)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("model is null");
		}
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		if (wrapper == null)
		{
			throw new IllegalArgumentException("model '" + model + "' is not wrapped from " + POJOWrapper.class);
		}

		//
		// Check if saving is allowed
		if (InterfaceWrapperHelper.isSaveDeleteDisabled(model))
		{
			throw new AdempiereException("Save/Delete is disabled for " + model);
		}

		final String tableName = wrapper.getTableName();

		final String trxName = wrapper.getTrxName();
		runInTrx(trxName, new TrxRunnable2()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				wrapper.setTrxName(localTrxName);

				int id = wrapper.getId();
				final boolean isNew = id <= 0;

				fireModelChanged(model, isNew ? ModelChangeType.BEFORE_NEW : ModelChangeType.BEFORE_CHANGE);

				if (isNew)
				{
					id = nextId(tableName);
					wrapper.setId(id);
				}

				Map<Integer, Object> tableRecords = cachedObjects.get(tableName);
				if (tableRecords == null)
				{
					// we use LinkedHashMap to preserve the order in which the objects are saved
					tableRecords = new LinkedHashMap<Integer, Object>();
					cachedObjects.put(tableName, tableRecords);
				}

				putCopy(tableRecords, id, model, isNew);
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				throw e;
			}

			@Override
			public void doFinally()
			{
				wrapper.setTrxName(trxName);
			}
		});
	}

	private final void runInTrx(final String trxName, final TrxRunnable runnable)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (trxManager.isNull(trxName))
		{
			final boolean manageTrx = true;
			trxManager.run(ITrx.TRXNAME_PREFIX_LOCAL, manageTrx, runnable);
		}
		else
		{
			trxManager.run(trxName, runnable);
		}
	}

	private void putCopy(final Map<Integer, Object> tableRecords, final int id, final Object model, final boolean isNew)
	{
		Check.assumeNotNull(model, "model not null");

		if (id <= 0)
		{
			throw new AdempiereException("ID shall be > 0");
		}

		final Object modelCopy = copy(model);
		POJOWrapper.setTrxName(modelCopy, ITrx.TRXNAME_None);

		// Make sure the model's ID is correct
		final int modelCopyId = InterfaceWrapperHelper.getId(modelCopy);
		if (modelCopyId != id)
		{
			throw new AdempiereException("Model's ID (" + modelCopyId + ") does not match expected ID=" + id);
		}

		final Object modelOld = tableRecords.put(id, modelCopy);

		boolean fireModelChangedSucceed = false;
		try
		{
			fireModelChanged(model, isNew ? ModelChangeType.AFTER_NEW : ModelChangeType.AFTER_CHANGE);
			fireModelChangedSucceed = true;
		}
		finally
		{
			if (!fireModelChangedSucceed)
			{
				// put back the old model
				tableRecords.put(id, modelOld);
			}
		}

		//
		// Reset cache
		// Note: doesn't matter if the record is new or not, because prior to its creation, there might have been a cached "null" value and we want to get rid of that null, as we now created an actual
		// record.
		{
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			CacheMgt.get().reset(tableName, id);
		}
	}

	public <T> List<T> getRecords(final Class<T> clazz)
	{
		final IQueryFilter<T> filter = null;
		final Comparator<T> orderByComparator = null;
		return getRecords(clazz, filter, orderByComparator);
	}

	public <T> List<T> getRecords(final Class<T> clazz, final IQueryFilter<T> filter)
	{
		final Comparator<T> orderByComparator = null;
		return getRecords(clazz, filter, orderByComparator);
	}

	public <T> List<T> getRecords(final Class<T> clazz, final IQueryFilter<T> filter, final Comparator<T> orderByComparator)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		return getRecords(tableName, clazz, filter, orderByComparator);
	}

	public <T> List<T> getRecords(final String tableName, final Class<T> clazz, final IQueryFilter<T> filter, final Comparator<T> orderByComparator)
	{
		final String trxName = ITrx.TRXNAME_None;
		return getRecords(tableName, clazz, filter, orderByComparator, trxName);
	}

	public <T> List<T> getRecords(final String tableName, final Class<T> clazz, final IQueryFilter<T> filter, final Comparator<T> orderByComparator, final String trxName)
	{
		assertSameTableName(tableName, clazz);

		final Map<Integer, Object> recordsMap = cachedObjects.get(tableName);
		if (recordsMap == null || recordsMap.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<T> result = filter == null ? new ArrayList<T>() : new ArrayList<T>(recordsMap.size());
		for (Object o : recordsMap.values())
		{
			final T record = copy(POJOWrapper.create(o, clazz));
			if (filter == null // accept everything if filter is null
					|| filter.accept(record))
			{
				POJOWrapper.setTrxName(record, trxName);
				result.add(record);
			}
		}

		if (orderByComparator != null)
		{
			Collections.sort(result, orderByComparator);
		}

		instancesTracker.track(result);

		return result;
	}

	public List<Object> getRawRecords(final String tableName)
	{
		final Map<Integer, Object> recordsMap = cachedObjects.get(tableName);
		if (recordsMap == null || recordsMap.isEmpty())
		{
			return Collections.emptyList();
		}

		// NOTE: in case of raw records we are not doing a copy of models

		final List<Object> records = new ArrayList<Object>(recordsMap.values());
		return records;
	}

	public <T> T getFirstOnly(Class<T> clazz, IQueryFilter<T> filter)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		final Comparator<T> orderByComparator = null;
		final String trxName = ITrx.TRXNAME_None;
		final boolean throwExIfMoreThenOneFound = true;
		return getFirstOnly(tableName, clazz, filter, orderByComparator, throwExIfMoreThenOneFound, trxName);
	}

	public <T> T getFirstOnly(final String tableName,
			final Class<T> clazz,
			final IQueryFilter<T> filter,
			final Comparator<T> orderByComparator,
			final boolean throwExIfMoreThenOneFound,
			final String trxName)
	{
		final List<T> result = getRecords(tableName, clazz, filter, orderByComparator, trxName);
		if (result == null || result.isEmpty())
		{
			return null;
		}
		if (result.size() == 1)
		{
			return result.get(0);
		}

		if (!throwExIfMoreThenOneFound)
		{
			return null;
		}

		throw new DBMoreThenOneRecordsFoundException("More than one record were found for " + clazz + ", filtered by " + filter + ": " + result);
	}

	public <T> T getFirst(Class<T> clazz, IQueryFilter<T> filter, final Comparator<T> orderByComparator)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		return getFirst(tableName, clazz, filter, orderByComparator);
	}

	public <T> T getFirst(final String tableName, Class<T> clazz, IQueryFilter<T> filter, final Comparator<T> orderByComparator)
	{
		final String trxName = ITrx.TRXNAME_None;
		return getFirst(tableName, clazz, filter, orderByComparator, trxName);
	}

	public <T> T getFirst(final String tableName, Class<T> clazz, IQueryFilter<T> filter, final Comparator<T> orderByComparator, final String trxName)
	{
		final List<T> result = getRecords(tableName, clazz, filter, orderByComparator, trxName);
		if (result == null || result.isEmpty())
		{
			return null;
		}
		return result.get(0);
	}

	public <T> boolean match(Class<T> clazz, IQueryFilter<T> filter)
	{
		Check.assumeNotNull(filter, "filter not null");

		final String tableName = InterfaceWrapperHelper.getTableName(clazz);

		final Map<Integer, Object> recordsMap = cachedObjects.get(tableName);
		if (recordsMap == null || recordsMap.isEmpty())
		{
			return false;
		}

		for (final Object o : recordsMap.values())
		{
			final T record = copy(POJOWrapper.create(o, clazz));
			if (filter.accept(record))
			{
				return true;
			}
		}

		return false;
	}

	public void dumpStatus()
	{
		dumpStatus("DATABASE");
	}

	public void dumpStatus(final String message)
	{
		dumpStatus(message, new String[0]);
	}

	public void dumpStatus(final String message, final String... tableNames)
	{
		System.out.println(dumpStatusToString(message, tableNames));
	}

	public String dumpStatusToString()
	{
		return dumpStatusToString("DATABASE");
	}

	public String dumpStatusToString(final String message)
	{
		return dumpStatusToString(message, new String[0]);
	}

	public String dumpStatusToString(final String message, final String... tableNames)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("=====================[ ").append(message).append(" ]============================================================");

		sb.append("\nDatabase Name: ").append(databaseName).append("(").append(getClass()).append(")");

		final List<String> tableNamesToUse = new ArrayList<String>();
		if (tableNames == null || tableNames.length == 0)
		{
			tableNamesToUse.addAll(cachedObjects.keySet());
		}
		else
		{
			tableNamesToUse.addAll(Arrays.asList(tableNames));
		}
		Collections.sort(tableNamesToUse);

		for (String tableName : tableNamesToUse)
		{
			final Map<Integer, Object> map = cachedObjects.get(tableName);
			if (map == null || map.isEmpty())
			{
				continue;
			}

			sb.append("\nTable " + tableName + ": " + map.size() + " records");
			for (final Object o : map.values())
			{
				sb.append("\n\t" + o);
			}
		}

		return sb.toString();
	}

	public void clear()
	{
		nextId = DEFAULT_FirstId;
		cachedObjects.clear();
	}

	@Override
	public boolean delete(final Object model)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("model is null");
		}
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		if (wrapper == null)
		{
			throw new IllegalArgumentException("model '" + model + "' is not wrapped from " + POJOWrapper.class);
		}

		//
		// Check if deleting is allowed
		if (InterfaceWrapperHelper.isSaveDeleteDisabled(model))
		{
			throw new AdempiereException("Save/Delete is disabled for " + model);
		}

		final int id = wrapper.getId();
		if (id < 0)
		{
			// not saved, nothing to delete
			return false;
		}

		final String tableName = wrapper.getTableName();

		final Map<Integer, Object> tableCachedObjects = cachedObjects.get(tableName);
		if (tableCachedObjects == null)
		{
			// not exists
			return false;
		}

		final String trxName = wrapper.getTrxName();

		final IMutable<Boolean> deleted = new Mutable<>(false);
		runInTrx(trxName, new TrxRunnable2()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				wrapper.setTrxName(localTrxName);

				fireModelChanged(model, ModelChangeType.BEFORE_DELETE);

				final Object removedObject = tableCachedObjects.remove(id);
				deleted.setValue(removedObject != null);

				boolean fireModelChangedSucceed = false;
				try
				{
					fireModelChanged(model, ModelChangeType.AFTER_DELETE);
					fireModelChangedSucceed = true;
				}
				finally
				{
					if (!fireModelChangedSucceed)
					{
						tableCachedObjects.put(id, removedObject);
					}
				}

				//
				// Reset cache
				CacheMgt.get().reset(tableName, id);
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				throw e;
			}

			@Override
			public void doFinally()
			{
				wrapper.setTrxName(trxName);
			}
		});

		return deleted.getValue();
	}

	private void assertSameTableName(final String tableName, final Class<?> clazz)
	{
		final String clazzTableName = InterfaceWrapperHelper.getTableNameOrNull(clazz);
		if (clazzTableName == null)
		{
			// accept classes without Table_Name
			return;
		}

		if (!clazzTableName.equals(tableName))
		{
			throw new AdempiereException("Cannot use class " + clazz
					+ " (TableName=" + clazzTableName + ")"
					+ " in a query for table " + tableName);
		}
	}

	private final CopyOnWriteArrayList<IModelInterceptor> interceptors = new CopyOnWriteArrayList<IModelInterceptor>();
	private final Map<String, CompositeModelInterceptor> tableName2interceptors = new HashMap<String, CompositeModelInterceptor>();

	@Override
	public void addModelValidator(Object interceptorObj, I_AD_Client client)
	{
		Check.assumeNotNull(interceptorObj, "interceptorObj not null");
		if (interceptorObj instanceof ModelValidator)
		{
			throw new IllegalArgumentException("ModelValidator registration is not supported. Please use " + IModelInterceptor.class);
		}
		else if (interceptorObj instanceof IModelInterceptor)
		{
			final IModelInterceptor interceptor = (IModelInterceptor)interceptorObj;
			addModelInterceptor(interceptor);
		}
		else
		{
			final IModelInterceptor interceptor = AnnotatedModelInterceptorFactory.get().createModelInterceptor(interceptorObj);
			addModelInterceptor(interceptor);
		}
	}

	public void addModelInterceptor(final IModelInterceptor interceptor)
	{
		Check.assumeNotNull(interceptor, "interceptor not null");
		final boolean added = interceptors.addIfAbsent(interceptor);
		if (!added)
		{
			final AdempiereException ex = new AdempiereException("Interceptor " + interceptor + " was already added so we are not adding it again, but please evaluate if this is a development error.");
			logger.warn(ex.getLocalizedMessage(), ex);
			return;
		}

		final I_AD_Client client = null;
		interceptor.initialize(this, client);
	}

	private final void fireModelChanged(final Object model, final ModelChangeType changeType)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final CompositeModelInterceptor interceptors = tableName2interceptors.get(tableName);
		if (interceptors == null)
		{
			return;
		}

		try
		{
			interceptors.onModelChange(model, changeType);
		}
		catch (AdempiereException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
	}

	public final void fireDocumentChange(final Object doc, final DocTimingType timing)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(doc);
		final CompositeModelInterceptor interceptors = tableName2interceptors.get(tableName);
		if (interceptors == null)
		{
			return;
		}

		try
		{
			interceptors.onDocValidate(doc, timing);
		}
		catch (AdempiereException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void addModelChange(String tableName, IModelInterceptor interceptor)
	{
		addModelInterceptor(tableName, interceptor);
	}

	@Override
	public void addDocValidate(String tableName, IModelInterceptor interceptor)
	{
		addModelInterceptor(tableName, interceptor);
	}

	private void addModelInterceptor(final String tableName, final IModelInterceptor interceptor)
	{
		CompositeModelInterceptor tableInterceptors = tableName2interceptors.get(tableName);
		if (tableInterceptors == null)
		{
			tableInterceptors = new CompositeModelInterceptor();
			tableName2interceptors.put(tableName, tableInterceptors);
		}

		tableInterceptors.addModelInterceptor(interceptor);
	}

	public void unregisterAllInterceptors()
	{
		interceptors.clear();
		tableName2interceptors.clear();
	}

	public String getDatabaseName()
	{
		return databaseName;
	}

	private ObjectName jmxName = null;

	private final void registerJMX()
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final JMXPOJOLookupMap jmxBean = new JMXPOJOLookupMap(this);

		final ObjectName name;
		try
		{
			final String jmxName = jmxBean.getJMXName();
			name = new ObjectName(jmxName);
		}
		catch (MalformedObjectNameException e)
		{
			throw new MonitoringException("Unable to create jmx ObjectName", e);
		}

		try
		{
			synchronized (mbs)
			{
				if (mbs.isRegistered(name))
				{
					mbs.unregisterMBean(name);
				}

				mbs.registerMBean(jmxBean, name);
			}
		}
		catch (InstanceAlreadyExistsException e)
		{
			throw new MonitoringException("Unable to register mbean", e);
		}
		catch (MBeanRegistrationException e)
		{
			throw new MonitoringException("Unable to register mbean", e);
		}
		catch (NotCompliantMBeanException e)
		{
			throw new MonitoringException("Unable to register mbean", e);
		}
		catch (InstanceNotFoundException e)
		{
			throw new MonitoringException("Unable to unregister mbean", e);
		}

		this.jmxName = name;
	}

	private final void unregisterJMX()
	{
		if (jmxName == null)
		{
			return;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			mbs.unregisterMBean(jmxName);
		}
		catch (MBeanRegistrationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstanceNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final Set<String> appliesIncludeOnlyTablePrefixes = new HashSet<String>();
	private final Set<String> appliesExcludeTablePrefixes = new HashSet<String>();

	public void addAppliesIncludeOnlyTablenamePrefix(final String tablenamePrefix)
	{
		appliesIncludeOnlyTablePrefixes.add(tablenamePrefix);
	}

	public void addAppliesExcludeTablenamePrefix(final String tablenamePrefix)
	{
		appliesExcludeTablePrefixes.add(tablenamePrefix);
	}

	public final boolean appliesToTableName(final String tableName)
	{
		//
		// Check excludes
		for (final String tableNamePrefix : appliesExcludeTablePrefixes)
		{
			if (tableName.startsWith(tableNamePrefix))
			{
				return false;
			}
		}

		//
		// If no "include only" specified, apply default policy: accept
		if (appliesIncludeOnlyTablePrefixes.isEmpty())
		{
			return true;
		}

		//
		// Check if is in our "include only" list
		for (final String tablenamePrefix : appliesIncludeOnlyTablePrefixes)
		{
			if (tableName.startsWith(tablenamePrefix))
			{
				return true;
			}
		}

		// Not found in "include only" list => don't accept it
		return false;
	}

	public final boolean appliesToModelClass(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (tableName == null)
		{
			return false;
		}

		return appliesToTableName(tableName);
	}

	public void createSelection(final int selectionId, final Collection<Integer> selection)
	{
		Check.assume(selectionId > 0, "selectionId > 0");

		final Set<Integer> selectionSet = new HashSet<>();
		if (selection != null)
		{
			selectionSet.addAll(selection);
		}

		this.selectionId2selection.put(selectionId, selectionSet);
	}

	public I_AD_PInstance createSelectionPInstance(final Properties ctx)
	{
		final int adProcessId = 0; // N/A
		final int adTableId = 0;
		final int recordId = 0;
		final I_AD_PInstance adPInstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, adProcessId, adTableId, recordId);
		return adPInstance;
	}

	public int createSelection(final Collection<Integer> selection)
	{
		final int selectionId = nextId(I_AD_PInstance.Table_Name);
		createSelection(selectionId, selection);
		return selectionId;
	}

	public <T> I_AD_PInstance createSelectionFromModels(@SuppressWarnings("unchecked") T... models)
	{
		final I_AD_PInstance adPInstance = createSelectionPInstance(Env.getCtx());

		if (models != null)
		{
			createSelectionFromModelsCollection(adPInstance, Arrays.asList(models));
		}

		return adPInstance;
	}

	public <T> I_AD_PInstance createSelectionFromModelsCollection(Collection<T> models)
	{
		final I_AD_PInstance adPInstance = createSelectionPInstance(Env.getCtx());
		createSelectionFromModelsCollection(adPInstance, models);
		return adPInstance;
	}

	public <T> void createSelectionFromModelsCollection(final I_AD_PInstance adPInstance, final Collection<T> models)
	{
		if (models == null || models.isEmpty())
		{
			return;
		}

		final Set<Integer> selection = new HashSet<>(models.size());
		for (final T model : models)
		{
			final int modelId = InterfaceWrapperHelper.getId(model);
			selection.add(modelId);
		}

		final int selectionId = adPInstance.getAD_PInstance_ID();
		createSelection(selectionId, selection);
	}

	public boolean isInSelection(final int selectionId, final int id)
	{
		final Set<Integer> selection = selectionId2selection.get(selectionId);
		if (selection == null)
		{
			return false;
		}
		return selection.contains(id);
	}

	/**
	 * @return new database restore point.
	 */
	public POJOLookupMapRestorePoint createRestorePoint()
	{
		return new POJOLookupMapRestorePoint(this);
	}
}
