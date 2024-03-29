package de.metas.cucumber.stepdefs.context;

import de.metas.common.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.MDC;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@UtilityClass
public class SharedTestContext
{
	private static final ThreadLocal<Context> threadLocal = new ThreadLocal<>();

	public static IAutoCloseable temporaryPut(@NonNull final String key, @Nullable final Object value)
	{
		put(key, value);
		return () -> remove(key);
	}

	public static void put(@NonNull final String key, @Nullable final Supplier<?> valueSupplier)
	{
		put(key, (Object)valueSupplier);
	}

	public static void put(@NonNull final String key, @Nullable final Object value)
	{
		Context context = threadLocal.get();
		if (context == null)
		{
			threadLocal.set(context = new Context());
		}
		context.put(key, value);
	}

	public static void remove(@NonNull final String key)
	{
		Context context = threadLocal.get();
		if (context != null)
		{
			context.remove(key);
		}
	}

	public Map<String, Object> getCopyOfContextMap()
	{
		final Context context = threadLocal.get();
		return context != null ? context.toFlatMap() : ImmutableMap.of();
	}

	@FunctionalInterface
	public interface ThrowingRunnable
	{
		void run() throws Throwable;
	}

	/**
	 * Runs given <code>runnable</code> and in case of exception appends the test context to thrown exeception.
	 * <p>
	 * To add values to current context you can use {@link #put(String, Object)}, {@link #put(String, Supplier)} etc.
	 */
	public void run(@NonNull final ThrowingRunnable runnable) throws Throwable
	{
		final Context parent = threadLocal.get();
		threadLocal.set(new Context(parent));

		try
		{
			runnable.run();
		}
		catch (final java.lang.AssertionError error)
		{
			throw wrapWithContext(error);
		}
		catch (final AdempiereException ex)
		{
			final LinkedHashMap<String, Object> context = snapshotContext();
			if (context != null && !context.isEmpty())
			{
				ex.setParameter("sharedTestContext", context);
				ex.appendParametersToMessage();
			}
			throw ex;
		}
		catch (final Throwable throwable)
		{
			// Make sure we don't lose test context which might be a valuable information for troubleshooting
			final Map<String, Object> context = snapshotContext();
			if (context != null && !context.isEmpty())
			{
				System.out.println(throwable.getClass().getSimpleName() + " having following message and context caught and will be thrown forward: "
						+ buildExceptionMessage(throwable.getMessage(), context));
			}

			throw throwable;
		}
		finally
		{
			threadLocal.set(parent);
		}
	}

	private static class Context
	{
		@Nullable private final Context parent;
		private LinkedHashMap<String, Object> map;

		Context() {this(null);}

		Context(@Nullable final Context parent) {this.parent = parent;}

		public void put(@NonNull final String key, @Nullable final Object value)
		{
			LinkedHashMap<String, Object> map = this.map;
			if (map == null)
			{
				map = this.map = new LinkedHashMap<>();
			}
			map.put(key, value);
		}

		public void remove(final String key)
		{
			final LinkedHashMap<String, Object> map = this.map;
			if (map != null)
			{
				map.remove(key);
			}
		}

		public Map<String, Object> toFlatMap()
		{
			final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
			if (parent != null)
			{
				result.putAll(parent.toFlatMap());
			}

			final LinkedHashMap<String, Object> map = this.map;
			if (map != null)
			{
				result.putAll(map);
			}

			return result;
		}
	}

	private static java.lang.AssertionError wrapWithContext(@NonNull final java.lang.AssertionError error)
	{
		if (error instanceof AssertionFailedWithContextInfoError)
		{
			return error;
		}
		else if (error instanceof AssertionWithContextInfoError)
		{
			return error;
		}
		else if (error instanceof org.opentest4j.AssertionFailedError)
		{
			final LinkedHashMap<String, Object> context = snapshotContext();
			return context != null && !context.isEmpty()
					? new AssertionFailedWithContextInfoError((org.opentest4j.AssertionFailedError)error, context)
					: error;
		}
		else
		{
			final LinkedHashMap<String, Object> context = snapshotContext();
			return context != null && !context.isEmpty()
					? new AssertionWithContextInfoError(error, context)
					: error;
		}
	}

	@Nullable
	private static LinkedHashMap<String, Object> snapshotContext()
	{
		LinkedHashMap<String, Object> result = resolveAndAppend(null, SharedTestContext.getCopyOfContextMap());
		result = resolveAndAppend(result, MDC.getCopyOfContextMap());
		return result;
	}

	private static LinkedHashMap<String, Object> resolveAndAppend(@Nullable LinkedHashMap<String, Object> target, @Nullable final Map<String, ?> from)
	{
		if (from == null || from.isEmpty())
		{
			return target;
		}

		final LinkedHashMap<String, Object> targetNew = target != null ? target : new LinkedHashMap<>(from.size());

		from.forEach((key, value) -> {
			final Object valueResolved = snapshotContextValue(value);
			targetNew.put(key, valueResolved);
		});
		return targetNew;
	}

	private static String snapshotContextValue(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			if (value instanceof Supplier<?>)
			{
				final Supplier<?> supplier = (Supplier<?>)value;
				return snapshotContextValue(supplier.get());
			}
			else if (value instanceof Iterable<?>)
			{
				final Iterable<?> iterable = (Iterable<?>)value;
				final StringBuilder result = new StringBuilder();
				int count = 0;
				for (final Object item : iterable)
				{
					count++;
					result.append("\n\t").append(count).append(". ").append(item);
				}

				if (result.length() == 0)
				{
					result.append("(empty)");
				}
				else
				{
					result.insert(0, "\n\t(" + count + " items)");
				}

				return result.toString();
			}
			else
			{
				return value.toString();
			}
		}
		catch (Exception ex)
		{
			final String message = "*** Failed resolving snapshot value `" + value + "` because " + ex.getMessage();
			System.out.println(message);
			ex.printStackTrace();
			return message;
		}
	}

	@NonNull
	private static String buildExceptionMessage(@Nullable final String originalMessage, final Map<String, Object> context)
	{
		final StringBuilder contextInfo = new StringBuilder();
		if (context != null && !context.isEmpty())
		{
			context.forEach((key, value) -> {
				if (contextInfo.length() > 0)
				{
					contextInfo.append("\n");
				}

				contextInfo.append(key).append(": ");

				final String valueStr = String.valueOf(value);
				if (valueStr.length() < 80 || !valueStr.contains("\n"))
				{
					contextInfo.append(valueStr);
				}
				else
				{
					contextInfo.append("\n").append(StringUtils.ident(valueStr, 1));
				}
			});
		}

		final StringBuilder message = new StringBuilder();
		if (originalMessage != null)
		{
			message.append(originalMessage);
		}
		if (contextInfo.length() > 0)
		{
			if (message.length() > 0)
			{
				message.append("\n");
			}
			message.append("\n\n======= Context ===============================================\n");
			message.append(contextInfo);
			message.append("\n\n======= Context end ===========================================\n");
		}

		return message.toString();
	}

	private static class AssertionFailedWithContextInfoError extends org.opentest4j.AssertionFailedError
	{
		private final String message;

		AssertionFailedWithContextInfoError(@NonNull org.opentest4j.AssertionFailedError original, @NonNull final LinkedHashMap<String, Object> context)
		{
			super(original.getMessage(), original.getExpected(), original.getActual(), original);
			this.message = buildExceptionMessage(original.getMessage(), context);
		}

		@Override
		public String getMessage() {return message;}
	}

	private static class AssertionWithContextInfoError extends java.lang.AssertionError
	{
		private final String message;

		AssertionWithContextInfoError(@NonNull java.lang.AssertionError original, @NonNull final LinkedHashMap<String, Object> context)
		{
			super(original.getMessage(), original);
			this.message = buildExceptionMessage(original.getMessage(), context);
		}

		@Override
		public String getMessage() {return message;}
	}
}
