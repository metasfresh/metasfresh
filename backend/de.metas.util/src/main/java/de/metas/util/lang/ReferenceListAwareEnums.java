package de.metas.util.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util
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
public class ReferenceListAwareEnums
{
	public static <T extends ReferenceListAwareEnum> ValuesIndex<T> index(final T[] values)
	{
		return new ValuesIndex<>(values);
	}

	public static <T extends ReferenceListAwareEnum> ImmutableMap<String, T> indexByCode(final T[] values)
	{
		return Maps.uniqueIndex(Arrays.asList(values), ReferenceListAwareEnum::getCode);
	}

	public static <T extends ReferenceListAwareEnum> T ofNullableCode(final String code, final Class<T> clazz)
	{
		return !Check.isEmpty(code) ? ofCode(code, clazz) : null;
	}

	public static <T extends ReferenceListAwareEnum> T ofCode(final String code, final Class<T> clazz)
	{
		final ReferenceListAwareDescriptor descriptor = getDescriptor(clazz);

		@SuppressWarnings("unchecked")
		final T enumObj = (T)descriptor.getOfCodeFunction().apply(code);

		return enumObj;
	}

	public static <T extends Enum<T>> T ofEnumCode(@NonNull final String code, @NonNull final Class<T> enumType)
	{
		if (ReferenceListAwareEnum.class.isAssignableFrom(enumType))
		{
			@SuppressWarnings("unchecked")
			final Class<? extends ReferenceListAwareEnum> referenceListAwareEnumType = (Class<? extends ReferenceListAwareEnum>)enumType;

			@SuppressWarnings("unchecked")
			final T result = (T)ofCode(code, referenceListAwareEnumType);

			return result;
		}
		else
		{
			return Enum.valueOf(enumType, code);
		}
	}

	public static <T extends ReferenceListAwareEnum> ImmutableList<T> ofCommaSeparatedList(
			@NonNull final String commaSeparatedStr,
			@NonNull final Class<T> clazz)
	{
		return CollectionUtils.ofCommaSeparatedList(
				commaSeparatedStr,
				code -> ofCode(code, clazz));
	}

	public static <T extends ReferenceListAwareEnum> Set<T> values(final Class<T> clazz)
	{
		final ReferenceListAwareDescriptor descriptor = getDescriptor(clazz);
		final Set<ReferenceListAwareEnum> values = descriptor.getValues()
				.orElseThrow(() -> Check.newException("Cannot extract values for " + clazz));

		@SuppressWarnings("unchecked")
		final Set<T> retValue = (Set<T>)(values);
		return retValue;
	}

	private static ReferenceListAwareDescriptor getDescriptor(final Class<? extends ReferenceListAwareEnum> clazz)
	{
		return descriptors.computeIfAbsent(clazz, k -> createReferenceListAwareDescriptor(k));
	}

	private static ReferenceListAwareDescriptor createReferenceListAwareDescriptor(final Class<? extends ReferenceListAwareEnum> clazz)
	{
		try
		{
			final Method ofCodeMethod = getMethodOrNull(clazz, "ofCode", String.class);
			if (ofCodeMethod == null)
			{
				throw Check.newException("No static method ofCode(String) found for " + clazz);
			}

			final ImmutableSet<ReferenceListAwareEnum> values = extractValuesOrNull(clazz);

			return ReferenceListAwareDescriptor.builder()
					.ofCodeFunction(code -> (ReferenceListAwareEnum)invokeStaticMethod(ofCodeMethod, code))
					.values(Optional.ofNullable(values))
					.build();
		}
		catch (final Exception ex)
		{
			final RuntimeException ex2 = Check.newException("Failed extracting " + ReferenceListAwareDescriptor.class + " from " + clazz);
			ex2.initCause(ex);
			throw ex2;
		}
	}

	private static Method getMethodOrNull(
			@NonNull final Class<? extends ReferenceListAwareEnum> clazz,
			@NonNull final String methodName,
			final Class<?>... parameterTypes)
	{
		try
		{
			return clazz.getMethod(methodName, parameterTypes);
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

	private static <T extends ReferenceListAwareEnum> ImmutableSet<ReferenceListAwareEnum> extractValuesOrNull(final Class<T> clazz)
	{
		//
		// Enum type
		final T[] enumConstants = clazz.getEnumConstants();
		if (enumConstants != null)
		{
			return ImmutableSet.copyOf(enumConstants);
		}

		//
		// static values() method
		final Method valuesMethod = getMethodOrNull(clazz, "values");
		if (valuesMethod != null)
		{
			final Class<?> returnType = valuesMethod.getReturnType();
			if (returnType.isArray())
			{
				@SuppressWarnings("unchecked")
				final T[] valuesArr = (T[])invokeStaticMethod(valuesMethod);
				return ImmutableSet.copyOf(valuesArr);
			}
			else if (Collection.class.isAssignableFrom(returnType))
			{
				@SuppressWarnings("unchecked")
				final Collection<T> valuesCollection = (Collection<T>)invokeStaticMethod(valuesMethod);
				return ImmutableSet.copyOf(valuesCollection);
			}
			else
			{
				throw Check.newException("Invalid values method: " + valuesMethod);
			}
		}

		//
		// cannot extract values
		return null;
	}

	private static Object invokeStaticMethod(final Method method, final Object... args)
	{
		try
		{
			final Object obj = null;
			return method.invoke(obj, args);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
		{
			final String argsInfo = args != null && args.length > 0
					? "arguments=" + Arrays.asList(args)
					: "no arguments";
			throw Check.newException("Failed invoking " + method + " with " + argsInfo, ex);
		}
	}

	private static ConcurrentHashMap<Class<? extends ReferenceListAwareEnum>, ReferenceListAwareDescriptor> //
	descriptors = new ConcurrentHashMap<>();

	@Value
	@Builder
	private static final class ReferenceListAwareDescriptor
	{
		@NonNull
		Function<String, ReferenceListAwareEnum> ofCodeFunction;

		@Nullable
		final Optional<ImmutableSet<ReferenceListAwareEnum>> values;
	}

	public static final class ValuesIndex<T extends ReferenceListAwareEnum>
	{
		private final String typeName;
		private final ImmutableMap<String, T> typesByCode;

		private ValuesIndex(@NonNull final T[] values)
		{
			if (values.length == 0)
			{
				throw new IllegalArgumentException("values not allowed to be empty");
			}
			this.typeName = values[0].getClass().getSimpleName();
			typesByCode = Maps.uniqueIndex(Arrays.asList(values), ReferenceListAwareEnum::getCode);
		}

		public T ofNullableCode(@Nullable final String code)
		{
			return code != null ? ofCode(code) : null;
		}

		public T ofCode(@NonNull final String code)
		{
			T type = typesByCode.get(code);
			if (type == null)
			{
				throw Check.mkEx("No " + typeName + " found for code: " + code);
			}
			return type;
		}
	}
}
