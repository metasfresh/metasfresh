/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.util.lang;

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

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

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

	@Nullable
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

	public static <T extends ReferenceListAwareEnum> int getAD_Reference_ID(@NonNull final T obj)
	{
		return getDescriptor(obj.getClass()).getAdReferenceId();
	}

	private static ReferenceListAwareDescriptor getDescriptor(final Class<? extends ReferenceListAwareEnum> clazz)
	{
		return descriptors.computeIfAbsent(clazz, ReferenceListAwareEnums::createReferenceListAwareDescriptor);
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

			final int adReferenceId = getAD_Reference_ID(clazz);

			final ImmutableSet<ReferenceListAwareEnum> values = extractValuesOrNull(clazz);

			return ReferenceListAwareDescriptor.builder()
					.ofCodeFunction(code -> (ReferenceListAwareEnum)invokeStaticMethod(ofCodeMethod, code))
					.adReferenceId(adReferenceId)
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

	@Nullable
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

	private int getAD_Reference_ID(final Class<?> clazz) throws Exception
	{
		for (final Field field : clazz.getFields())
		{
			if (field.getName().equals("AD_REFERENCE_ID"))
			{
				if (!field.isAccessible())
				{
					field.setAccessible(true);
				}
				if (!Modifier.isStatic(field.getModifiers()))
				{
					throw Check.mkEx("Field " + field.getName() + " is expected to be static");
				}

				final int adReferenceId = field.getInt(null);
				if (adReferenceId <= 0)
				{
					throw Check.mkEx("Field " + field.getName() + "is expected to have a positive value");
				}

				return adReferenceId;
			}
		}

		return -1;
	}

	@Nullable
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
		catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
		{
			final String argsInfo = args != null && args.length > 0
					? "arguments=" + Arrays.asList(args)
					: "no arguments";
			throw Check.newException("Failed invoking " + method + " with " + argsInfo, ex);
		}
	}

	private static final ConcurrentHashMap<Class<? extends ReferenceListAwareEnum>, ReferenceListAwareDescriptor> //
			descriptors = new ConcurrentHashMap<>();

	@Value
	@Builder
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static class ReferenceListAwareDescriptor
	{
		@NonNull Function<String, ReferenceListAwareEnum> ofCodeFunction;
		int adReferenceId;
		@NonNull Optional<ImmutableSet<ReferenceListAwareEnum>> values;
	}

	public static final class ValuesIndex<T extends ReferenceListAwareEnum>
	{
		private final String typeName;
		private final ImmutableMap<String, T> typesByCode;
		private final ImmutableMap<String, T> typesByName;

		private ValuesIndex(@NonNull final T[] values)
		{
			if (values.length == 0)
			{
				throw new IllegalArgumentException("values not allowed to be empty");
			}
			this.typeName = values[0].getClass().getSimpleName();

			typesByCode = Maps.uniqueIndex(Arrays.asList(values), ReferenceListAwareEnum::getCode);
			this.typesByName = indexByName(values);
		}

		@NonNull
		private static <T extends ReferenceListAwareEnum> ImmutableMap<String, T> indexByName(@NonNull final T @NonNull [] values)
		{
			final ImmutableMap.Builder<String, T> typesByName = ImmutableMap.builder();
			for (final T value : values)
			{
				if (value instanceof Enum)
				{
					final String name = ((Enum<?>)value).name();
					typesByName.put(name, value);
				}
			}
			return typesByName.build();
		}

		@Nullable
		public T ofNullableCode(@Nullable final String code)
		{
			return code != null && Check.isNotBlank(code) ? ofCode(code) : null;
		}

		public Optional<T> optionalOfNullableCode(@Nullable final String code)
		{
			return Optional.ofNullable(ofNullableCode(code));
		}

		public T ofCode(@NonNull final String code)
		{
			final T type = typesByCode.get(code);
			if (type == null)
			{
				throw Check.mkEx("No " + typeName + " found for code: " + code);
			}
			return type;
		}

		public T ofCodeOrName(@NonNull final String code)
		{
			T type = typesByCode.get(code);
			if (type == null)
			{
				type = typesByName.get(code);
			}
			if (type == null)
			{
				throw Check.mkEx("No " + typeName + " found for code or name: " + code);
			}
			return type;
		}

		@NonNull
		public Stream<T> streamValues()
		{
			return typesByCode.values().stream();
		}
	}
}
