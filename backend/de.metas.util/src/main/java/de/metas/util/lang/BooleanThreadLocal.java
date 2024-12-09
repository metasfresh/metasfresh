package de.metas.util.lang;

import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

public class BooleanThreadLocal
{
	@NonNull private final ThreadLocal<Boolean> threadLocal;

	public BooleanThreadLocal(final boolean initialValue)
	{
		threadLocal = ThreadLocal.withInitial(() -> initialValue);
	}

	public String toString()
	{
		return String.valueOf(isTrue());
	}

	public boolean isTrue()
	{
		final Boolean value = threadLocal.get();
		return value != null && value;
	}

	public IAutoCloseable temporarySetToTrueIf(final boolean condition)
	{
		return condition ? temporarySetToTrue() : IAutoCloseable.NOOP;
	}

	public IAutoCloseable temporarySetToTrue()
	{
		return temporarySetTo(true);
	}

	@SuppressWarnings("SameParameterValue")
	private IAutoCloseable temporarySetTo(final boolean value)
	{
		final Boolean valuePrev = threadLocal.get();
		threadLocal.set(value);
		return () -> threadLocal.set(valuePrev);
	}
}
