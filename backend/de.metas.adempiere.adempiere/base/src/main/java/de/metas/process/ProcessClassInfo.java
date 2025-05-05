package de.metas.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.api.RangeAwareParams;
import org.compiere.SpringContextHolder;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Profiles;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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

/**
 * Contains information about the process class.
 * <p>
 * This instance will be build by introspecting a particular process class and fetching its annotations.
 * <p>
 * To create a new instance, call {@link #of(Class)} builder.
 *
 * @author tsa
 */
public final class ProcessClassInfo
{
	private static final Logger logger = LogManager.getLogger(ProcessClassInfo.class);

	/**
	 * @return process class info or {@link #NULL} in case the given <code>processClass</code> is <code>null</code> or in case of failure.
	 */
	public static ProcessClassInfo of(@Nullable final Class<?> processClass)
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
			logger.error("Failed fetching ProcessClassInfo from cache for {}", processClass, e);
			return ProcessClassInfo.NULL;
		}
	}

	/**
	 * @return process class info or {@link #NULL} in case the given <code>processClass</code> is <code>null</code> or in case of failure.
	 */
	public static ProcessClassInfo ofClassname(@Nullable final String classname)
	{
		if (Check.isBlank(classname))
		{
			return ProcessClassInfo.NULL;
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = ProcessClassInfo.class.getClassLoader();
			}
			final Class<?> processClass = classLoader.loadClass(classname);
			return of(processClass);
		}
		catch (final ClassNotFoundException ex)
		{
			// nothing
			logger.warn("No class found for {}", classname);
			return ProcessClassInfo.NULL;
		}
	}

	public static boolean isNull(final ProcessClassInfo processClassInfo)
	{
		return processClassInfo == null || processClassInfo == NULL;
	}

	/**
	 * Reset {@link ProcessClassInfo} cache
	 */
	public static void resetCache()
	{
		processClassInfoCache.invalidateAll();
		processClassInfoCache.cleanUp();
	}

	/**
	 * "Process class" to {@link ProcessClassInfo} cache
	 */
	private static final LoadingCache<Class<?>, ProcessClassInfo> processClassInfoCache = CacheBuilder.newBuilder()
			.weakKeys() // to prevent ClassLoader memory leaks nightmare
			.build(new CacheLoader<Class<?>, ProcessClassInfo>()
			{
				@Override
				public ProcessClassInfo load(final @NonNull Class<?> processClass)
				{
					return createProcessClassInfoNoFail(processClass);
				}
			});

	/**
	 * Introspect given process class and return info.
	 *
	 * @return process class info or {@link #NULL} in case of failure.
	 */
	private static ProcessClassInfo createProcessClassInfoNoFail(final Class<?> processClass)
	{
		try
		{
			return new ProcessClassInfo(processClass);
		}
		catch (Throwable e)
		{
			logger.error("Failed introspecting process class info: {}. Fallback to defaults: {}", processClass, NULL, e);
			return NULL;
		}
	}

	/**
	 * Retrieves the fields which were marked as process parameters
	 */
	private static Set<Field> retrieveParameterFields(final Class<?> processClass)
	{
		@SuppressWarnings("unchecked") final Set<Field> paramFields = ReflectionUtils.getAllFields(processClass, ReflectionUtils.withAnnotation(Param.class));
		return paramFields;
	}

	/**
	 * Introspect given process class and creates the {@link ProcessClassParamInfo}s
	 */
	private static ImmutableListMultimap<ProcessClassParamInfoKey, ProcessClassParamInfo> createProcessClassParamInfos(final Class<?> processClass)
	{
		final ArrayList<ProcessClassParamInfo> paramsInfo = new ArrayList<>();

		for (final Field field : retrieveParameterFields(processClass))
		{
			paramsInfo.add(prepareProcessClassParamInfo(field).build());
		}

		//noinspection unchecked
		for (final Field nestedParamsField : ReflectionUtils.getAllFields(processClass, ReflectionUtils.withAnnotation(NestedParams.class)))
		{
			final NestedParams nestedParamsAnn = nestedParamsField.getAnnotation(NestedParams.class);
			final HashMap<String, String> externalParameterNamesByInternalParameterNames = new HashMap<>();
			for (NestedParams.ParamMapping paramMappingAnn : nestedParamsAnn.value())
			{
				externalParameterNamesByInternalParameterNames.put(paramMappingAnn.internalParameterName(), paramMappingAnn.externalParameterName());
			}

			final HashSet<String> declaredInternalParameterNames = new HashSet<>();

			final Class<?> nestedParamsClass = nestedParamsField.getType();
			for (final Field field : retrieveParameterFields(nestedParamsClass))
			{
				final Param paramAnn = field.getAnnotation(Param.class);
				final String internalParameterName = paramAnn.parameterName();
				declaredInternalParameterNames.add(internalParameterName);

				final String externalParameterName = externalParameterNamesByInternalParameterNames.getOrDefault(internalParameterName, internalParameterName);
				paramsInfo.add(
						prepareProcessClassParamInfo(field)
								.externalParameterName(externalParameterName)
								.internalParameterName(internalParameterName)
								.nestedParamsField(nestedParamsField)
								.build());
			}

			//
			// Validation: let the developer know that some internal parameter names mapping are not valid
			final Set<String> missingInternalParameterNames = Sets.difference(externalParameterNamesByInternalParameterNames.keySet(), declaredInternalParameterNames);
			if (!missingInternalParameterNames.isEmpty())
			{
				throw new AdempiereException("Following internal parameter names are missing while trying to remap nested params of " + nestedParamsField + ": " + missingInternalParameterNames);
			}
		}

		return Multimaps.index(
				paramsInfo,
				paramInfo -> ProcessClassParamInfoKey.of(paramInfo.getExternalParameterName(), paramInfo.isParameterTo()));
	}

	private static ProcessClassParamInfo.ProcessClassParamInfoBuilder prepareProcessClassParamInfo(@NonNull final Field paramField)
	{
		final Param paramAnn = Check.assumeNotNull(paramField.getAnnotation(Param.class), "@Param annotation shall be present for {}", paramField);

		return ProcessClassParamInfo.builder()
				.field(paramField)
				.externalParameterName(paramAnn.parameterName())
				.internalParameterName(paramAnn.parameterName())
				.parameterTo(paramAnn.parameterTo())
				.mandatory(paramAnn.mandatory())
				.barcodeScannerType(paramAnn.barcodeScannerType().getTypeOrNull());
	}

	private static boolean isRunOutOfTrx(final Class<?> processClass, final Class<?> returnType, final String methodName)
	{
		// Get all methods with given format,
		// from given processClass, and it's super classes,
		// ordered by methods of processClass first, methods from super classes after
		@SuppressWarnings("unchecked") final Set<Method> methods = ReflectionUtils.getAllMethods(processClass, ReflectionUtils.withName(methodName), ReflectionUtils.withParameters(), ReflectionUtils.withReturnType(returnType));

		// No methods of given format were found.
		// This could be OK in case our process is NOT extending JavaProcess but the IProcess interface.
		if (methods.isEmpty())
		{
			logger.info("Method {} with return type {} was not found in {} or in its inherited types. Ignored.", methodName, returnType, processClass);
			// throw new IllegalStateException("Method " + methodName + " with return type " + returnType + " was not found in " + processClass + " or in its inherited types");
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

	private final String classname; // mainly for logging
	private final boolean runPrepareOutOfTransaction;
	private final boolean runDoItOutOfTransaction;
	private final ImmutableListMultimap<ProcessClassParamInfoKey, ProcessClassParamInfo> parameterInfos;

	private static final boolean DEFAULT_ExistingCurrentRecordRequiredWhenCalledFromGear = true;
	private final boolean existingCurrentRecordRequiredWhenCalledFromGear;

	@Nullable private final Profiles onlyForSpringProfiles;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.

	/**
	 * null constructor
	 */
	private ProcessClassInfo()
	{
		classname = null;
		runPrepareOutOfTransaction = false;
		runDoItOutOfTransaction = false;
		parameterInfos = ImmutableListMultimap.of();
		existingCurrentRecordRequiredWhenCalledFromGear = DEFAULT_ExistingCurrentRecordRequiredWhenCalledFromGear;
		onlyForSpringProfiles = null;
	}

	@VisibleForTesting
	public ProcessClassInfo(@NonNull final Class<?> processClass)
	{
		classname = processClass.getName();

		//
		// Load from @RunOutOfTrx annotation
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
		this.parameterInfos = createProcessClassParamInfos(processClass);

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

		//
		// Load from @Profile annotation
		final Profile profile = processClass.getAnnotation(Profile.class);
		this.onlyForSpringProfiles = profile != null && profile.value().length > 0 ? Profiles.of(profile.value()) : null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("classname", classname)
				.add("runPrepareOutOfTransaction", runPrepareOutOfTransaction)
				.add("runDoItOutOfTransaction", runDoItOutOfTransaction)
				.add("parameterInfos", parameterInfos)
				.add("existingCurrentRecordRequiredWhenCalledFromGear", existingCurrentRecordRequiredWhenCalledFromGear)
				.add("onlyForSpringProfiles", onlyForSpringProfiles)
				.toString();
	}

	/**
	 * @return <code>true</code> if we shall run {@link JavaProcess#prepare()} method out of transaction
	 */
	/* package */
	public boolean isRunPrepareOutOfTransaction()
	{
		return runPrepareOutOfTransaction;
	}

	/**
	 * @return <code>true</code> if we shall run {@link JavaProcess#doIt()} method out of transaction
	 */
	/* package */boolean isRunDoItOutOfTransaction()
	{
		return runDoItOutOfTransaction;
	}

	/**
	 * @return <code>true</code> if at least a part of the process shall be executed out of transaction
	 */
	public boolean isRunOutOfTransaction()
	{
		return runPrepareOutOfTransaction || runDoItOutOfTransaction;
	}

	public boolean isParameterMandatory(final String parameterName, final boolean parameterTo)
	{
		return getParameterInfos(parameterName, parameterTo)
				.stream()
				.anyMatch(ProcessClassParamInfo::isMandatory);
	}

	public Collection<ProcessClassParamInfo> getParameterInfos()
	{
		return parameterInfos.values();
	}

	public List<ProcessClassParamInfo> getParameterInfos(final String parameterName, final boolean parameterTo)
	{
		return parameterInfos.get(ProcessClassParamInfoKey.of(parameterName, parameterTo));
	}

	public List<ProcessClassParamInfo> getParameterInfos(final String parameterName)
	{
		final List<ProcessClassParamInfo> params = new ArrayList<>();
		params.addAll(parameterInfos.get(ProcessClassParamInfoKey.of(parameterName, false)));
		params.addAll(parameterInfos.get(ProcessClassParamInfoKey.of(parameterName, true)));
		return params;
	}

	/**
	 * @return true if a current record needs to be selected when this process is called from gear/window.
	 * @see Process annotation
	 */
	public boolean isExistingCurrentRecordRequiredWhenCalledFromGear()
	{
		return existingCurrentRecordRequiredWhenCalledFromGear;
	}

	public boolean isAllowedForCurrentProfiles()
	{
		// No profiles restriction => allowed
		if (onlyForSpringProfiles == null)
		{
			return true;
		}

		// No application context => allowed (but warn)
		final ApplicationContext context = SpringContextHolder.instance.getApplicationContext();
		if (context == null)
		{
			logger.warn("No application context found to determine if {} is allowed for current profiles. Considering allowed", this);
			return true;
		}

		return context.getEnvironment().acceptsProfiles(onlyForSpringProfiles);
	}

	public IRangeAwareParams extractRangeAwareParams(JavaProcess processInstance)
	{
		final HashMap<String, Object> values = new HashMap<>();
		final HashMap<String, Object> valuesTo = new HashMap<>();

		for (final ProcessClassParamInfo paramInfo : getParameterInfos())
		{
			final String externalParameterName = paramInfo.getExternalParameterName();
			final Object fieldValue = paramInfo.getFieldValue(processInstance);
			if (paramInfo.isParameterTo())
			{
				valuesTo.put(externalParameterName, fieldValue);
			}
			else
			{
				values.put(externalParameterName, fieldValue);
			}
		}

		return RangeAwareParams.ofMaps(values, valuesTo);
	}

	public void loadParameterValueNoFail(final JavaProcess processInstance, final String parameterName, final IRangeAwareParams source)
	{
		final Collection<ProcessClassParamInfo> parameterInfos = getParameterInfos(parameterName);
		parameterInfos.forEach(paramInfo -> paramInfo.loadParameterValue(processInstance, source, false));
	}

	public String toInternalParameterName(@NonNull final String externalParameterName)
	{
		final ImmutableList<String> internalParameterNames = getParameterInfos(externalParameterName)
				.stream()
				.map(ProcessClassParamInfo::getInternalParameterName)
				.distinct()
				.collect(ImmutableList.toImmutableList());
		if (internalParameterNames.isEmpty())
		{
			return externalParameterName;
		}
		else if (internalParameterNames.size() == 1)
		{
			return internalParameterNames.get(0);
		}
		else
		{
			throw new AdempiereException("More than one internal parameter name found for external parameter name `" + externalParameterName + "`: " + internalParameterNames);
		}
	}

	//
	//
	//

	@Value(staticConstructor = "of")
	private static class ProcessClassParamInfoKey
	{
		@NonNull String externalParameterName;
		boolean parameterTo;
	}

}
