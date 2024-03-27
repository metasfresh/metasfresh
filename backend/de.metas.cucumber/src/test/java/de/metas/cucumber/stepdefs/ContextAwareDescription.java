package de.metas.cucumber.stepdefs;

import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.assertj.core.description.Description;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ContextAwareDescription extends Description
{
	private static final InheritableThreadLocal<HashMap<String, Object>> contextThreadLocal = new InheritableThreadLocal<HashMap<String, Object>>()
	{
		@Override
		protected HashMap<String, Object> childValue(final HashMap<String, Object> parentValue)
		{
			return parentValue == null ? null : new HashMap<>(parentValue);
		}
	};

	public static IAutoCloseable putContext(@NonNull final String key, @Nullable final Object value)
	{
		putContext0(key, value);
		return () -> removeContext0(key);
	}

	public static IAutoCloseable putContext(
			@NonNull final String key1, @Nullable final Object value1,
			@NonNull final String key2, @Nullable final Object value2)
	{
		putContext0(key1, value1);
		putContext0(key2, value2);
		return () -> {
			removeContext0(key1);
			removeContext0(key2);
		};
	}

	private static void putContext0(@NonNull final String key, @Nullable final Object value)
	{
		HashMap<String, Object> context = contextThreadLocal.get();
		if (context == null)
		{
			contextThreadLocal.set(context = new HashMap<>());
		}
		context.put(key, value);
	}

	private static void removeContext0(@NonNull final String key)
	{
		final HashMap<String, Object> context = contextThreadLocal.get();
		if (context != null)
		{
			context.remove(key);
		}
	}

	private final String message;

	public static ContextAwareDescription ofString(String message)
	{
		return new ContextAwareDescription(message);
	}

	private ContextAwareDescription(final String message)
	{
		this.message = StringUtils.trimBlankToNull(message);
	}

	@Override
	public String value()
	{
		final StringBuilder result = new StringBuilder();
		if (message != null)
		{
			result.append(message);
		}

		final HashMap<String, Object> context = contextThreadLocal.get();
		if (context != null && !context.isEmpty())
		{
			context.forEach((key, value) -> {
				if (result.length() > 0)
				{
					result.append("\n\t");
				}
				result.append(key).append(": ").append(value);
			});
		}

		final Map<String, String> mdcMap = MDC.getCopyOfContextMap();
		if (mdcMap != null && !mdcMap.isEmpty())
		{
			mdcMap.forEach((key, value) -> {
				if (result.length() > 0)
				{
					result.append("\n\t");
				}
				result.append(key).append(": ").append(value);
			});
		}
		return result.toString();
	}
}
