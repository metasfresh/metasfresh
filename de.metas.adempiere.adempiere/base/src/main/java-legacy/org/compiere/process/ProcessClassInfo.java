package org.compiere.process;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util.ArrayKey;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.process.RunOutOfTrx;

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

/**
 * Contains informations about the process class.
 *
 * This instance will be build by introspecting a particular process class and fetching it's annotations.
 *
 * To create a new instance, call {@link #of(Class)} builder.
 *
 * @author tsa
 */
public final class ProcessClassInfo
{
	private static final transient Logger logger = SvrProcess.s_log;

	/**
	 * @return process class info or {@link #NULL} in case the given <code>processClass</code> is <code>null</code> or in case of failure.
	 */
	public static final ProcessClassInfo of(final Class<?> processClass)
	{
		if (processClass == null)
		{
			return ProcessClassInfo.NULL;
		}
		try
		{
			return processClassInfoCache.get(processClass);
		}
		catch (ExecutionException e)
		{
			// shall never happen
			logger.error("Failed fetching ProcessClassInfo from cache for " + processClass, e);
		}
		return ProcessClassInfo.NULL;
	}

	/**
	 * @return process class info or {@link #NULL} in case the given <code>processClass</code> is <code>null</code> or in case of failure.
	 */
	public static final ProcessClassInfo ofClassname(final String classname)
	{
		Class<?> processClass = null;
		if (!Check.isEmpty(classname))
		{
			try
			{
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				if (classLoader == null)
				{
					classLoader = ProcessClassInfo.class.getClassLoader();
				}
				processClass = classLoader.loadClass(classname);
			}
			catch (ClassNotFoundException e)
			{
				// nothing
			}
		}
		return of(processClass);
	}

	/** Reset {@link ProcessClassInfo} cache */
	public static final void resetCache()
	{
		processClassInfoCache.invalidateAll();
	}

	/** "Process class" to {@link ProcessClassInfo} cache */
	private static final LoadingCache<Class<?>, ProcessClassInfo> processClassInfoCache = CacheBuilder.newBuilder()
			.weakKeys() // to prevent ClassLoader memory leaks nightmare
			.build(new CacheLoader<Class<?>, ProcessClassInfo>()
			{
				@Override
				public ProcessClassInfo load(final Class<?> processClass) throws Exception
				{
					return createProcessClassInfo(processClass);
				}
			});

	/**
	 * Introspect given process class and return info.
	 *
	 * @param processClass
	 * @return process class info or {@link #NULL} in case of failure.
	 */
	static final ProcessClassInfo createProcessClassInfo(final Class<?> processClass)
	{
		try
		{
			return new ProcessClassInfo(processClass);
		}
		catch (Throwable e)
		{
			logger.error("Failed introspecting process class info: " + processClass + ". Fallback to defaults: " + NULL, e);
			return NULL;
		}
	}

	/** Retrieves the fields which were marked as process parameters */
	private static final Set<Field> retrieveParameterFields(final Class<?> processClass)
	{
		@SuppressWarnings("unchecked")
		final Set<Field> paramFields = ReflectionUtils.getAllFields(processClass, ReflectionUtils.withAnnotation(Param.class));
		return paramFields;
	}

	/** Introspects given process class and creates the {@link ProcessClassParamInfo}s */
	private static final List<ProcessClassParamInfo> createProcessClassParamInfos(final Class<?> processClass)
	{
		final Set<Field> paramFields = retrieveParameterFields(processClass);
		if (paramFields.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<ProcessClassParamInfo> paramInfos = new ArrayList<>(paramFields.size());
		for (final Field paramField : paramFields)
		{
			final ProcessClassParamInfo paramInfo = createProcessClassParamInfo(paramField);
			if (paramInfo == null)
			{
				continue;
			}
			paramInfos.add(paramInfo);
		}

		return paramInfos;
	}

	private static ProcessClassParamInfo createProcessClassParamInfo(final Field paramField)
	{
		final Param paramAnn = paramField.getAnnotation(Param.class);
		if (paramAnn == null)
		{
			// shall not happen...
			return null;
		}

		return ProcessClassParamInfo.builder()
				.setField(paramField)
				.setParameterName(paramAnn.parameterName())
				.setMandatory(paramAnn.mandatory())
				.setParameterTo(paramAnn.parameterTo())
				.build();
	}

	private static final boolean isRunOutOfTrx(final Class<?> processClass, final Class<?> returnType, final String methodName)
	{
		// Get all methods with given format,
		// from given processClass and it's super classes,
		// ordered by methods of processClass first, methods from super classes after
		@SuppressWarnings("unchecked")
		final Set<Method> methods = ReflectionUtils.getAllMethods(processClass
				, ReflectionUtils.withName(methodName)
				, ReflectionUtils.withParameters()
				, ReflectionUtils.withReturnType(returnType));

		// No methods of given format were found.
		// This could be OK in case our process is NOT extending SvrProcess but the ProcessCall interface.
		if (methods.isEmpty())
		{
			logger.info("Method {} with return type {} was not found in {} or in its inerited types. Ignored.", new Object[] { methodName, returnType, processClass });
			// throw new IllegalStateException("Method " + methodName + " with return type " + returnType + " was not found in " + processClass + " or in its inerited types");
			return false;
		}

		// Iterate all methods and return on first RunOutOfTrx annotation found.
		for (final Method method : methods)
		{
			final RunOutOfTrx runOutOfTrxAnnotation = method.getAnnotation(RunOutOfTrx.class);
			if (runOutOfTrxAnnotation != null)
			{
				return true;
			}
		}

		// Fallback: no RunOutOfTrx annotation found
		return false;
	}

	public static final ProcessClassInfo NULL = new ProcessClassInfo();

	private final boolean runPrepareOutOfTransaction;
	private final boolean runDoItOutOfTransaction;
	private final boolean clientOnly;
	private final List<ProcessClassParamInfo> parameterInfos;

	private static final boolean DEFAULT_ExistingCurrentRecordRequiredWhenCalledFromGear = true;
	private final boolean existingCurrentRecordRequiredWhenCalledFromGear;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.

	/** null constructor */
	private ProcessClassInfo()
	{
		super();
		runPrepareOutOfTransaction = false;
		runDoItOutOfTransaction = false;
		clientOnly = false;
		parameterInfos = ImmutableList.of();
		existingCurrentRecordRequiredWhenCalledFromGear = DEFAULT_ExistingCurrentRecordRequiredWhenCalledFromGear;
	}

	private ProcessClassInfo(final Class<?> processClass)
	{
		super();

		//
		// Load from @RunOutOfTrx annnotation
		boolean runPrepareOutOfTransaction = isRunOutOfTrx(processClass, void.class, "prepare");
		final boolean runDoItOutOfTransaction = isRunOutOfTrx(processClass, String.class, "doIt");
		if (runDoItOutOfTransaction)
		{
			runPrepareOutOfTransaction = true;
		}
		this.runPrepareOutOfTransaction = runPrepareOutOfTransaction;
		this.runDoItOutOfTransaction = runDoItOutOfTransaction;

		//
		// Load parameter infos
		this.parameterInfos = ImmutableList.copyOf(createProcessClassParamInfos(processClass));

		//
		// Check ClientProcess marker
		this.clientOnly = ClientProcess.class.isAssignableFrom(processClass);

		//
		// Load from @Process annotation
		final Process processAnn = processClass.getAnnotation(Process.class);
		if (processAnn != null)
		{
			this.existingCurrentRecordRequiredWhenCalledFromGear = processAnn.requiresCurrentRecordWhenCalledFromGear();
		}
		else
		{
			this.existingCurrentRecordRequiredWhenCalledFromGear = DEFAULT_ExistingCurrentRecordRequiredWhenCalledFromGear;
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return <code>true</code> if we shall run {@link SvrProcess#prepare()} method out of transaction */
	public boolean isRunPrepareOutOfTransaction()
	{
		return runPrepareOutOfTransaction;
	}

	/** @return <code>true</code> if we shall run {@link SvrProcess#doIt()} method out of transaction */
	public boolean isRunDoItOutOfTransaction()
	{
		return runDoItOutOfTransaction;
	}
	
	/** @return true if this process shall be executed on client side only */
	public boolean isClientOnly()
	{
		return clientOnly;
	}

	public boolean isParameterMandatory(final String parameterName)
	{
		for (final ProcessClassParamInfo paramInfo : parameterInfos)
		{
			if (!paramInfo.getParameterName().equalsIgnoreCase(parameterName))
			{
				continue;
			}

			if (paramInfo.isMandatory())
			{
				return true;
			}
		}

		return false;
	}

	public List<ProcessClassParamInfo> getParameterInfos()
	{
		return parameterInfos;
	}

	/**
	 *
	 * @param processInstance the process object where we will set the annotated fields to be the loaded parameters. Note that it needs to be an {@link IContextAware}, because we might need to load
	 *            records from the given <code>source</code>.
	 * @param source
	 */
	public void loadParameterValues(final IContextAware processInstance, final IRangeAwareParams source)
	{
		Check.assumeNotNull(processInstance, "processInstance not null");

		// No parameters => nothing to do
		if (parameterInfos.isEmpty())
		{
			return;
		}

		//
		// Retrieve Fields from processInstance's class
		final Map<ArrayKey, Field> processFields = new HashMap<>();
		for (final Field processField : retrieveParameterFields(processInstance.getClass()))
		{
			final ArrayKey fieldKey = ProcessClassParamInfo.createFieldUniqueKey(processField);
			processFields.put(fieldKey, processField);
		}

		//
		// Iterate all process class info parameters and try to update the corresponding field
		for (final ProcessClassParamInfo paramInfo : parameterInfos)
		{
			final ArrayKey fieldKey = paramInfo.getFieldKey();
			final Field processField = processFields.get(fieldKey);
			paramInfo.loadParameterValue(processInstance, processField, source);
		}
	}

	/**
	 * @return true if a current record needs to be selected when this process is called from gear/window.
	 * @see Process annotation
	 */
	public boolean isExistingCurrentRecordRequiredWhenCalledFromGear()
	{
		return existingCurrentRecordRequiredWhenCalledFromGear;
	}
}
