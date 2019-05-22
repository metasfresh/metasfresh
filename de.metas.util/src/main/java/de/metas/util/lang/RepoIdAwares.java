package de.metas.util.lang;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

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
	public static ImmutableList<Integer> asRepoIds(@NonNull final Collection<? extends RepoIdAware> ids)
	{
		return ids
				.stream()
				.map(RepoIdAware::getRepoId)
				.collect(ImmutableList.toImmutableList());
	}

	public static ImmutableSet<Integer> asRepoIdsSet(@NonNull final Collection<? extends RepoIdAware> ids)
	{
		return ids
				.stream()
				.map(RepoIdAware::getRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static <T extends RepoIdAware> T ofRepoId(final int repoId, Class<T> repoIdClass)
	{
		final RepoIdAwareDescriptor repoIdAwareDescriptor = getRepoIdAwareDescriptor(repoIdClass);

		@SuppressWarnings("unchecked")
		final T id = (T)repoIdAwareDescriptor.getOfRepoIdFunction().apply(repoId);

		return id;
	}

	public static <T extends RepoIdAware> T ofRepoIdOrNull(final int repoId, Class<T> repoIdClass)
	{
		final RepoIdAwareDescriptor repoIdAwareDescriptor = getRepoIdAwareDescriptor(repoIdClass);

		@SuppressWarnings("unchecked")
		final T id = (T)repoIdAwareDescriptor.getOfRepoIdOrNullFunction().apply(repoId);

		return id;
	}

	private static RepoIdAwareDescriptor getRepoIdAwareDescriptor(final Class<? extends RepoIdAware> repoIdClass)
	{
		return repoIdAwareDescriptors.computeIfAbsent(repoIdClass, k -> createRepoIdAwareDescriptor(k));
	}

	private static RepoIdAwareDescriptor createRepoIdAwareDescriptor(final Class<? extends RepoIdAware> repoIdClass)
	{
		try
		{
			final Method ofRepoIdMethod = repoIdClass.getMethod("ofRepoId", int.class);
			final Method ofRepoIdOrNullMethod = repoIdClass.getMethod("ofRepoIdOrNull", int.class);

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
							return (RepoIdAware)ofRepoIdOrNullMethod.invoke(null, repoId);
						}
						catch (final Exception ex)
						{
							throw mkEx("Failed invoking " + ofRepoIdOrNullMethod + " with repoId=" + repoId, ex);
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

	private static final RuntimeException mkEx(final String msg, final Throwable cause)
	{
		final RuntimeException ex = Check.newException(msg);
		if (cause != null)
		{
			ex.initCause(cause);
		}
		return ex;
	}

	private static ConcurrentHashMap<Class<? extends RepoIdAware>, RepoIdAwareDescriptor> repoIdAwareDescriptors = new ConcurrentHashMap<>();

	@Value
	@Builder
	private static class RepoIdAwareDescriptor
	{
		@NonNull
		IntFunction<RepoIdAware> ofRepoIdFunction;
		@NonNull
		IntFunction<RepoIdAware> ofRepoIdOrNullFunction;
	}
}
