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

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.common.util.time.SystemTime;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.logging.LogManager;
import de.metas.monitoring.exception.MonitoringException;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
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

public final class POJOLookupMap implements IPOJOLookupMap, IModelValidationEngine
{
	// services
	private static final POJOLookupMap instance = new POJOLookupMap("GLOBAL");
	private static final transient Logger logger = LogManager.getLogger(POJOLookupMap.class);
	// NOTE: don't add services here, because in testing we are reseting the Services quite offen

	private static final ThreadLocal<POJOLookupMap> threadInstanceRef = new ThreadLocal<>();

	@NonNull
	public static POJOLookupMap get()
	{
		return Check.assumeNotNull(getOrNull(), "POJOLookupMap.get() shall not return null at this point");
	}

	@Nullable
	private static POJOLookupMap getOrNull()
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

	public static POJOLookupMap getInMemoryDatabaseForModel(final Class<?> modelClass)
	{
		final POJOLookupMap database = getOrNull();
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

	public static POJOLookupMap getInMemoryDatabaseForTableName(final String tableName)
	{
		final POJOLookupMap database = getOrNull();
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

	private static void destroyThreadLocalStorage()
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
		instance.setManageCreatedByAndUpdatedBy(true);
		instance.unregisterAllInterceptors();
	}

	private POJONextIdSupplier nextIdSupplier = newNextIdSupplier();

	/**
	 * Database name
	 */
	private final String databaseName;

	/**
	 * Map of cached objects (TableName -> Record_ID -> Object)
	 */
	Map<String, Map<Integer, Object>> cachedObjects = new HashMap<>();
	Map<PInstanceId, ImmutableSet<Integer>> selectionId2selection = new HashMap<>();

	/**
	 * true if we want that values to be copied on save and not only referenced. Setting to true is like an actual database is working.
	 */
	@Getter
	@Setter
	private boolean copyOnSave = true;

	@Getter
	@Setter
	private boolean manageCreatedByAndUpdatedBy = true;

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

	private static POJONextIdSupplier newNextIdSupplier()
	{
		return POJONextIdSuppliers.newSingleSharedSequence();
	}

	public static void resetToDefaultNextIdSupplier()
	{
		setNextIdSupplier(newNextIdSupplier());
	}

	public static void setNextIdSupplier(@NonNull final POJONextIdSupplier nextIdSupplier)
	{
		final POJONextIdSupplier nextIdSupplierOld = instance.nextIdSupplier;
		instance.nextIdSupplier = nextIdSupplier;

		System.out.println("Changed nextIdSupplier from " + nextIdSupplierOld + " to " + instance.nextIdSupplier);
	}

	@Override
	public int nextId(String tableName)
	{
		return nextIdSupplier.nextId(tableName);
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

		final Map<Integer, Object> tableRecords = cachedObjects.get(tableName);
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
			@SuppressWarnings("unchecked") final T resultConv = (T)result;
			return resultConv;
		}

		return POJOWrapper.create(result, clazz);
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

		@SuppressWarnings("unchecked") final T value = (T)getCopy(tableRecords, recordId);
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
		return POJOWrapper.create(ctx, interfaceClass, this);
	}

	/**
	 * Create a new object using global ctx (Env.getCtx()) and Trx.TRX_None as transaction.
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
			public void run(String localTrxName)
			{
				wrapper.setTrxName(localTrxName);

				int id = wrapper.getId();
				final boolean isNew = id <= wrapper.idForNewModel();

				fireModelChanged(model, isNew ? ModelChangeType.BEFORE_NEW : ModelChangeType.BEFORE_CHANGE);

				final Timestamp now = SystemTime.asTimestamp();
				if (isNew)
				{
					id = nextId(tableName);
					wrapper.setId(id);
				}

				//
				// Set Created/Updated
				final boolean hasChanges = wrapper.hasChanges();
				if (isNew || wrapper.isNullNotStrict(POJOWrapper.COLUMNNAME_Created))
				{
					wrapper.setValue(POJOWrapper.COLUMNNAME_Created, now);
				}
				if (isNew || hasChanges || wrapper.isNullNotStrict(POJOWrapper.COLUMNNAME_Updated))
				{
					wrapper.setValue(POJOWrapper.COLUMNNAME_Updated, now);
				}
				if (manageCreatedByAndUpdatedBy)
				{
					final Integer loggedUserRepoId = Env.getLoggedUserIdIfExists(wrapper.getCtx())
							.map(UserId::getRepoId)
							.orElse(null);
					if (isNew || wrapper.isNullNotStrict(POJOWrapper.COLUMNNAME_CreatedBy))
					{
						wrapper.setValue(POJOWrapper.COLUMNNAME_CreatedBy, loggedUserRepoId);
					}
					if (isNew || hasChanges || wrapper.isNullNotStrict(POJOWrapper.COLUMNNAME_UpdatedBy))
					{
						wrapper.setValue(POJOWrapper.COLUMNNAME_UpdatedBy, loggedUserRepoId);
					}
				}

				// we use LinkedHashMap to preserve the order in which the objects are saved
				Map<Integer, Object> tableRecords = cachedObjects.computeIfAbsent(tableName, k -> new LinkedHashMap<>());

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

	private void runInTrx(final String trxName, final TrxRunnable runnable)
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

	private void putCopy(
			final Map<Integer, Object> tableRecords,
			final int id,
			@NonNull final Object model,
			final boolean isNew)
	{
		final int defaultId = POJOWrapper.getWrapper(model).idForNewModel();
		Check.errorIf(id <= defaultId, "The given model needs to have an ID > {}; actual id={}; isNew={}; model={}", defaultId, id, isNew, model);

		final Object modelCopy = copy(model);
		POJOWrapper.setTrxName(modelCopy, ITrx.TRXNAME_None);

		// Make sure the model's ID is correct
		final int modelCopyId = InterfaceWrapperHelper.getId(modelCopy);
		Check.errorIf(modelCopyId != id, "Model's ID ({}) does not match expected ID={}", modelCopy, id);

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

		final ArrayList<T> result = filter == null ? new ArrayList<>() : new ArrayList<>(recordsMap.size());
		for (final Object o : recordsMap.values())
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
			result.sort(orderByComparator);
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

		return new ArrayList<>(recordsMap.values());
	}

	public <T> T getFirstOnly(Class<T> clazz)
	{
		return getFirstOnly(clazz, null);
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
		if (result.isEmpty())
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

		throw new DBMoreThanOneRecordsFoundException("More than one record was found for " + clazz + ", filtered by " + filter + ": " + result);
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
		if (result.isEmpty())
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

	public String dumpStatusToString(final String message)
	{
		return dumpStatusToString(message, new String[0]);
	}

	public String dumpStatusToString(final String message, final String... tableNames)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("=====================[ ").append(message).append(" ]============================================================");

		sb.append("\nDatabase Name: ").append(databaseName).append("(").append(getClass()).append(")");

		final List<String> tableNamesToUse = new ArrayList<>();
		if (tableNames == null || tableNames.length == 0)
		{
			tableNamesToUse.addAll(cachedObjects.keySet());
		}
		else
		{
			tableNamesToUse.addAll(Arrays.asList(tableNames));
		}
		Collections.sort(tableNamesToUse);

		for (final String tableName : tableNamesToUse)
		{
			final Map<Integer, Object> map = cachedObjects.get(tableName);
			if (map == null || map.isEmpty())
			{
				continue;
			}

			sb.append("\nTable ").append(tableName).append(": ").append(map.size()).append(" records");
			for (final Object o : map.values())
			{
				sb.append("\n\t").append(o);
			}
		}

		return sb.toString();
	}

	public void clear()
	{
		POJOLookupMap.resetToDefaultNextIdSupplier();
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
			public void run(String localTrxName)
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

		return Boolean.TRUE.equals(deleted.getValue());
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

	private final CopyOnWriteArrayList<IModelInterceptor> interceptors = new CopyOnWriteArrayList<>();
	private final Map<String, CompositeModelInterceptor> tableName2interceptors = new HashMap<>();

	@Override
	public void addModelValidator(@NonNull final Object interceptorObj, @Nullable final I_AD_Client client)
	{
		Check.assumeNull(client, "client shall be null but it was {}", client);
		addModelValidator(interceptorObj);
	}

	@Override
	public void addModelValidator(@NonNull final Object interceptorObj)
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

	private void fireModelChanged(final Object model, final ModelChangeType changeType)
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
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
	}

	public void fireDocumentChange(final Object doc, final DocTimingType timing)
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
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception e)
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
	public void removeModelChange(String tableName, IModelInterceptor interceptor)
	{
		final CompositeModelInterceptor tableInterceptors = tableName2interceptors.get(tableName);
		if (tableInterceptors == null)
		{
			return; // nothing to do
		}
		tableInterceptors.removeModelInterceptor(interceptor);
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

	private void registerJMX()
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final JMXPOJOLookupMap jmxBean = new JMXPOJOLookupMap(this);

		final ObjectName name;
		try
		{
			final String jmxName = jmxBean.getJMXName();
			name = new ObjectName(jmxName);
		}
		catch (final MalformedObjectNameException e)
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
		catch (final InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e)
		{
			throw new MonitoringException("Unable to register mbean", e);
		}
		catch (final InstanceNotFoundException e)
		{
			throw new MonitoringException("Unable to unregister mbean", e);
		}

		this.jmxName = name;
	}

	private void unregisterJMX()
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
		catch (final MBeanRegistrationException | InstanceNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final Set<String> appliesIncludeOnlyTablePrefixes = new HashSet<>();
	private final Set<String> appliesExcludeTablePrefixes = new HashSet<>();

	public boolean appliesToTableName(final String tableName)
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

	public boolean appliesToModelClass(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (tableName == null)
		{
			return false;
		}

		return appliesToTableName(tableName);
	}

	public void createSelection(@NonNull final PInstanceId selectionId, final Collection<Integer> selection)
	{
		final ImmutableSet<Integer> selectionSet = selection != null ? ImmutableSet.copyOf(selection) : ImmutableSet.of();

		final ImmutableSet<Integer> existingSelectionSet = this.selectionId2selection.get(selectionId);
		if (existingSelectionSet == null)
		{
			this.selectionId2selection.put(selectionId, selectionSet);
		}
		else
		{
			final ImmutableSet<Integer> combinedSelectionSet = ImmutableSet.<Integer>builder()
					.addAll(existingSelectionSet)
					.addAll(selectionSet).build();
			this.selectionId2selection.put(selectionId, combinedSelectionSet);
		}
	}

	private PInstanceId createSelectionPInstanceId()
	{
		return Services.get(IADPInstanceDAO.class).createSelectionId();
	}

	public PInstanceId createSelection(final Collection<Integer> selection)
	{
		final PInstanceId selectionId = PInstanceId.ofRepoId(nextId(I_AD_PInstance.Table_Name));
		createSelection(selectionId, selection);
		return selectionId;
	}

	@SafeVarargs
	public final <T> PInstanceId createSelectionFromModels(T... models)
	{
		final PInstanceId adPInstanceId = createSelectionPInstanceId();

		if (models != null)
		{
			createSelectionFromModelsCollection(adPInstanceId, Arrays.asList(models));
		}

		return adPInstanceId;
	}

	public <T> PInstanceId createSelectionFromModelsCollection(Collection<T> models)
	{
		final PInstanceId adPInstanceId = createSelectionPInstanceId();
		createSelectionFromModelsCollection(adPInstanceId, models);
		return adPInstanceId;
	}

	public <T> void createSelectionFromModelsCollection(final PInstanceId selectionId, final Collection<T> models)
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

		createSelection(selectionId, selection);
	}

	public boolean isInSelection(final PInstanceId selectionId, final int id)
	{
		return getSelectionIds(selectionId).contains(id);
	}

	public Set<Integer> getSelectionIds(final PInstanceId selectionId)
	{
		final Set<Integer> selection = selectionId2selection.get(selectionId);
		return selection != null ? selection : ImmutableSet.of();
	}

	public void dumpSelections()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("=====================[ SELECTIONS ]============================================================");
		for (final PInstanceId selectionId : selectionId2selection.keySet())
		{
			sb.append("\n\t").append(selectionId).append(": ").append(selectionId2selection.get(selectionId));
		}
		sb.append("\n");

		System.out.println(sb.toString());
	}

	/**
	 * @return new database restore point.
	 */
	public POJOLookupMapRestorePoint createRestorePoint()
	{
		return new POJOLookupMapRestorePoint(this);
	}

	@Override
	public void addImportInterceptor(String importTableName, IImportInterceptor listener)
	{
		// nothing
	}
}
