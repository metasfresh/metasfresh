package de.metas.ui.web.process.descriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessClassInfo;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.ListLookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Decorates a {@link ProcessClassInfo} and contains webui related informations too.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class WebuiProcessClassInfo
{
	public static final WebuiProcessClassInfo of(@Nullable final Class<?> processClass)
	{
		if (processClass == null)
		{
			return WebuiProcessClassInfo.NULL;
		}
		return cache.getUnchecked(processClass);
	}

	public static final WebuiProcessClassInfo of(@Nullable final String processClassname)
	{
		if (Check.isEmpty(processClassname, true))
		{
			return NULL;
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = WebuiProcessClassInfo.class.getClassLoader();
		}

		try
		{
			final Class<?> processClass = classLoader.loadClass(processClassname.trim());
			return of(processClass);
		}
		catch (final ClassNotFoundException e)
		{
			logger.info("Could not load process class for {}. IGNORED", processClassname);
			return NULL;
		}
	}

	private static final Logger logger = LogManager.getLogger(WebuiProcessClassInfo.class);

	/** "Process class" to {@link WebuiProcessClassInfo} cache */
	private static final LoadingCache<Class<?>, WebuiProcessClassInfo> cache = CacheBuilder.newBuilder()
			.weakKeys() // to prevent ClassLoader memory leaks nightmare
			.build(new CacheLoader<Class<?>, WebuiProcessClassInfo>()
			{
				@Override
				public WebuiProcessClassInfo load(final Class<?> processClass)
				{
					try
					{
						return createWebuiProcessClassInfo(processClass);
					}
					catch (final Throwable e)
					{
						logger.error("Failed introspecting process class info: {}. Fallback to defaults: {}", processClass, NULL, e);
						return NULL;
					}
				}
			});

	private static WebuiProcessClassInfo createWebuiProcessClassInfo(final Class<?> processClass) throws Exception
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(processClass);

		@SuppressWarnings("unchecked")
		final Set<Method> lookupValuesProviderMethods = ReflectionUtils.getAllMethods(processClass, ReflectionUtils.withAnnotation(ProcessParamLookupValuesProvider.class));
		final ImmutableMap<String, LookupDescriptorProvider> paramLookupValuesProviders = lookupValuesProviderMethods.stream()
				.map(method -> createParamLookupValuesProvider(method))
				.collect(GuavaCollectors.toImmutableMap());

		//
		// Check is there were no settings at all so we could return our NULL instance
		if (ProcessClassInfo.isNull(processClassInfo)
				&& paramLookupValuesProviders.isEmpty())
		{
			return NULL;
		}

		return new WebuiProcessClassInfo(processClassInfo, paramLookupValuesProviders);
	}

	private static final WebuiProcessClassInfo NULL = new WebuiProcessClassInfo();

	private final ProcessClassInfo processClassInfo;
	private final ImmutableMap<String, LookupDescriptorProvider> paramLookupValuesProviders;

	/** Null constructor */
	private WebuiProcessClassInfo()
	{
		processClassInfo = ProcessClassInfo.NULL;
		paramLookupValuesProviders = ImmutableMap.of();
	}

	private WebuiProcessClassInfo(final ProcessClassInfo processClassInfo, final ImmutableMap<String, LookupDescriptorProvider> paramLookupValuesProviders)
	{
		this.processClassInfo = processClassInfo;
		this.paramLookupValuesProviders = paramLookupValuesProviders;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("paramLookupValuesProviders", paramLookupValuesProviders)
				.add("processClassInfo", processClassInfo)
				.toString();
	}

	public LookupDescriptorProvider getLookupDescriptorProviderOrNull(final String parameterName)
	{
		return paramLookupValuesProviders.get(parameterName);
	}

	public boolean isForwardValueToJavaProcessInstance(final String parameterName, final boolean isParameterTo)
	{
		return !processClassInfo.getParameterInfos(parameterName, isParameterTo).isEmpty();
	}

	//
	//
	// ----
	//
	//

	/** @return parameterName and provider */
	private static Map.Entry<String, LookupDescriptorProvider> createParamLookupValuesProvider(final Method method)
	{
		final ProcessParamLookupValuesProvider ann = method.getAnnotation(ProcessParamLookupValuesProvider.class);

		if (!LookupValuesList.class.isAssignableFrom(method.getReturnType()))
		{
			throw new AdempiereException("Method's return type shall be " + LookupValuesList.class + ": " + method);
		}

		final ImmutableList<Function<LookupDataSourceContext, Object>> parameterValueProviders = Stream.of(method.getParameterTypes())
				.map(parameterType -> {
					final Function<LookupDataSourceContext, Object> parameterValueProvider;
					if (LookupDataSourceContext.class.isAssignableFrom(parameterType))
					{
						parameterValueProvider = evalCtx -> evalCtx;
					}
					else
					{
						throw new AdempiereException("Parameter " + parameterType + " not supported for " + method);
					}
					return parameterValueProvider;
				})
				.collect(ImmutableList.toImmutableList());

		final Method methodToInvoke = method; // FIXME: holding a hard reference to method may introduce ClassLoader memory leaks

		final LookupDescriptor lookupDescriptor = ListLookupDescriptor.builder()
				.setDependsOnFieldNames(ann.dependsOn())
				.setLookupValues(ann.numericKey(), evalCtx -> retriveLookupValues(methodToInvoke, parameterValueProviders, evalCtx))
				.build();

		final LookupDescriptorProvider lookupDescriptorProvider = LookupDescriptorProvider.singleton(lookupDescriptor);
		return GuavaCollectors.entry(ann.parameterName(), lookupDescriptorProvider);
	}

	private static final LookupValuesList retriveLookupValues(final Method method, final List<Function<LookupDataSourceContext, Object>> parameterValueProviders, final LookupDataSourceContext evalCtx)
	{
		Check.assumeNotNull(method, "Parameter method is not null");
		final JavaProcess processClassInstance = JavaProcess.currentInstance();

		final Object[] methodParams = parameterValueProviders.stream()
				.map(paramValueProvider -> paramValueProvider.apply(evalCtx))
				.toArray();

		try
		{
			if (!method.isAccessible())
			{
				method.setAccessible(true);
			}

			final LookupValuesList lookupValues = (LookupValuesList)method.invoke(processClassInstance, methodParams);
			return lookupValues;
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			final Throwable cause = AdempiereException.extractCause(e);
			throw new AdempiereException("Failed invoking " + method + " using " + methodParams, cause);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
