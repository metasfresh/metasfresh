package de.metas.cucumber.stepdefs.context;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.IAutoCloseable;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class SharedTestContext
{
	private static final InheritableThreadLocal<HashMap<String, Object>> threadLocal = new InheritableThreadLocal<HashMap<String, Object>>()
	{
		@Override
		protected HashMap<String, Object> childValue(final HashMap<String, Object> parentValue)
		{
			return parentValue == null ? null : new HashMap<>(parentValue);
		}
	};

	public static IAutoCloseable put(@NonNull final String key, @Nullable final Object value)
	{
		put0(key, value);
		return () -> remove(key);
	}

	public static IAutoCloseable put(
			@NonNull final String key1, @Nullable final Object value1,
			@NonNull final String key2, @Nullable final Object value2)
	{
		put0(key1, value1);
		put0(key2, value2);
		return () -> {
			remove(key1);
			remove(key2);
		};
	}

	private static void put0(@NonNull final String key, @Nullable final Object value)
	{
		HashMap<String, Object> context = threadLocal.get();
		if (context == null)
		{
			threadLocal.set(context = new HashMap<>());
		}
		context.put(key, value);
	}

	private static void remove(@NonNull final String key)
	{
		final HashMap<String, Object> context = threadLocal.get();
		if (context != null)
		{
			context.remove(key);
		}
	}

	public Map<String, Object> getCopyOfContextMap()
	{
		final HashMap<String, Object> context = threadLocal.get();
		return context != null ? new HashMap<>(context) : ImmutableMap.of();
	}
}
