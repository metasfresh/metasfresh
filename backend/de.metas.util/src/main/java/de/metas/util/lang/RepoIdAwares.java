package de.metas.util.lang;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class RepoIdAwares
{
	/**
	 * If an {@link de.metas.util.lang.RepoIdAware} instance is annotated with this,
	 * then it will be skipped by automated tests which are checking if the repo ID is valid.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface SkipTest {}

	public static ImmutableList<Integer> asRepoIds(@NonNull final Collection<? extends RepoIdAware> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		return ids
				.stream()
				.map(RepoIdAware::getRepoId)
				.collect(ImmutableList.toImmutableList());
	}

	public static ImmutableSet<Integer> asRepoIdsSet(@NonNull final Collection<? extends RepoIdAware> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids
				.stream()
				.map(RepoIdAware::getRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static <T extends RepoIdAware> T ofRepoId(final int repoId, final Class<T> repoIdClass)
	{
		return getOfRepoIdFunction(repoIdClass).apply(repoId);
	}

	public static <T extends RepoIdAware> IntFunction<T> getOfRepoIdFunction(final Class<T> repoIdClass)
	{
		final RepoIdAwareDescriptor repoIdAwareDescriptor = getRepoIdAwareDescriptor(repoIdClass);
		//noinspection unchecked
		return (IntFunction<T>)repoIdAwareDescriptor.getOfRepoIdFunction();
	}


	public static <T extends RepoIdAware> T ofObject(@NonNull final Object repoIdObj, final Class<T> repoIdClass)
	{
		final IntFunction<T> ofRepoIdFunction = getOfRepoIdFunction(repoIdClass);
		return ofObject(repoIdObj, repoIdClass, ofRepoIdFunction);
	}

	public static <T extends RepoIdAware> T ofObject(
			@NonNull final Object repoIdObj,
			@NonNull final Class<T> repoIdClass,
			@NonNull final IntFunction<T> ofRepoIdFunction)
	{
		if (repoIdClass.isInstance(repoIdObj))
		{
			return repoIdClass.cast(repoIdObj);
		}

		final Integer repoId = NumberUtils.asIntegerOrNull(repoIdObj);
		if (repoId == null)
		{
			throw Check.mkEx("Cannot convert `" + repoIdObj + "` (" + repoIdObj.getClass() + ") to " + repoIdClass.getSimpleName());
		}

		return ofRepoIdFunction.apply(repoId);
	}


	public static <T extends RepoIdAware> T ofRepoIdOrNull(final int repoId, final Class<T> repoIdClass)
	{
		final RepoIdAwareDescriptor repoIdAwareDescriptor = getRepoIdAwareDescriptor(repoIdClass);

		@SuppressWarnings("unchecked") final T id = (T)repoIdAwareDescriptor.getOfRepoIdOrNullFunction().apply(repoId);

		return id;
	}

	public static <T extends RepoIdAware> ImmutableList<T> ofCommaSeparatedList(
			@Nullable final String commaSeparatedStr,
			@NonNull final Class<T> repoIdClass)
	{
		final IntFunction<T> ofRepoIdFunction = getOfRepoIdFunction(repoIdClass);
		return CollectionUtils.ofCommaSeparatedList(
				commaSeparatedStr,
				repoIdStr -> ofObject(repoIdStr, repoIdClass, ofRepoIdFunction));
	}

	public static <T extends RepoIdAware> ImmutableSet<T> ofCommaSeparatedSet(
			@Nullable final String commaSeparatedStr,
			@NonNull final Class<T> repoIdClass)
	{
		final IntFunction<T> ofRepoIdFunction = getOfRepoIdFunction(repoIdClass);
		return CollectionUtils.ofCommaSeparatedSet(
				commaSeparatedStr,
				repoIdStr -> ofObject(repoIdStr, repoIdClass, ofRepoIdFunction));
	}


	public static int toRepoId(@Nullable final RepoIdAware repoIdAware)
	{
		if (repoIdAware == null)
		{
			return -1;
		}
		return repoIdAware.getRepoId();
	}

	@VisibleForTesting
	static RepoIdAwareDescriptor getRepoIdAwareDescriptor(final Class<? extends RepoIdAware> repoIdClass)
	{
		return repoIdAwareDescriptors.computeIfAbsent(repoIdClass, RepoIdAwares::createRepoIdAwareDescriptor);
	}

	private static RepoIdAwareDescriptor createRepoIdAwareDescriptor(final Class<? extends RepoIdAware> repoIdClass)
	{
		try
		{
			final Method ofRepoIdMethod = getMethodOrNull(repoIdClass, "ofRepoId", int.class);
			if (ofRepoIdMethod == null)
			{
				throw Check.newException("No method ofRepoId(int) found for " + repoIdClass);
			}

			Method ofRepoIdOrNullMethod = getMethodOrNull(repoIdClass, "ofRepoIdOrNull", int.class);
			if (ofRepoIdOrNullMethod == null)
			{
				ofRepoIdOrNullMethod = getMethodOrNull(repoIdClass, "ofRepoIdOrNull", Integer.class);
			}
			if (ofRepoIdOrNullMethod == null)
			{
				throw Check.newException("No method ofRepoIdOrNull(int) or ofRepoIdOrNull(Integer) found for " + repoIdClass);
			}
			final Method ofRepoIdOrNullMethodFinal = ofRepoIdOrNullMethod;

			return RepoIdAwareDescriptor.builder()
					.ofRepoIdFunction(repoId -> {
						try
						{
							return (RepoIdAware)ofRepoIdMethod.invoke(null, repoId);
						}
						catch (final Exception ex)
						{
							throw mkEx("Failed invoking " + ofRepoIdMethod + " with repoId=" + repoId, ex);
						}
					})
					.ofRepoIdOrNullFunction(repoId -> {
						try
						{
							return (RepoIdAware)ofRepoIdOrNullMethodFinal.invoke(null, repoId);
						}
						catch (final Exception ex)
						{
							throw mkEx("Failed invoking " + ofRepoIdOrNullMethodFinal + " with repoId=" + repoId, ex);
						}
					})
					.build();
		}
		catch (final Exception ex)
		{
			final RuntimeException ex2 = Check.newException("Failed extracting " + RepoIdAwareDescriptor.class + " from " + repoIdClass);
			ex2.initCause(ex);
			throw ex2;
		}
	}

	@Nullable
	private static Method getMethodOrNull(
			@NonNull final Class<? extends RepoIdAware> repoIdClass,
			@NonNull final String methodName,
			final Class<?>... parameterTypes)
	{
		try
		{
			return repoIdClass.getMethod(methodName, parameterTypes);
		}
		catch (final NoSuchMethodException e)
		{
			return null;
		}
		catch (final SecurityException e)
		{
			throw new RuntimeException(e);
		}

	}

	private static RuntimeException mkEx(final String msg, final Throwable cause)
	{
		final RuntimeException ex = Check.newException(msg);
		if (cause != null)
		{
			ex.initCause(cause);
		}
		return ex;
	}

	private static final ConcurrentHashMap<Class<? extends RepoIdAware>, RepoIdAwareDescriptor> repoIdAwareDescriptors = new ConcurrentHashMap<>();

	@Value
	@Builder
	@VisibleForTesting
	static class RepoIdAwareDescriptor
	{
		@NonNull
		IntFunction<RepoIdAware> ofRepoIdFunction;
		@NonNull
		IntFunction<RepoIdAware> ofRepoIdOrNullFunction;
	}

	public static <T, R extends RepoIdAware> Comparator<T> comparingNullsLast(@NonNull final Function<T, R> keyMapper)
	{
		return Comparator.comparing(keyMapper, Comparator.nullsLast(Comparator.naturalOrder()));
	}
}
