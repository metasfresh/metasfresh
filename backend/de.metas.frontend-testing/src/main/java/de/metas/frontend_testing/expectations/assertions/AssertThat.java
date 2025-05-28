package de.metas.frontend_testing.expectations.assertions;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class AssertThat<T>
{
	private final T actual;
	private String what = "";
	private final LinkedHashMap<String, Object> context = new LinkedHashMap<>();
	@Nullable private ExceptionFactory exceptionFactory;
	@Nullable private ExceptionCollector exceptionCollector;

	AssertThat(@Nullable final T actual)
	{
		this.actual = actual;
	}

	void setExceptionFactory(@Nullable final ExceptionFactory exceptionFactory)
	{
		this.exceptionFactory = exceptionFactory;
	}

	void setExceptionCollector(@Nullable final ExceptionCollector exceptionCollector)
	{
		this.exceptionCollector = exceptionCollector;
	}

	public AssertThat<T> as(@NonNull final String description)
	{
		this.what = description;
		return this;
	}

	@SuppressWarnings("unused")
	public AssertThat<T> putContext(@NonNull final String key, @NonNull final Object value)
	{
		context.put(key, value);
		return this;
	}

	private void fail(final String message)
	{
		fail(message, null);
	}

	private void fail(@NonNull final String message, @Nullable final Consumer<AdempiereException> customizer)
	{
		final AdempiereException exception = newException(message);
		if (customizer != null)
		{
			customizer.accept(exception);
		}

		if (exceptionCollector != null)
		{
			exceptionCollector.collect(exception);
		}
		else
		{
			throw exception;
		}
	}

	private AdempiereException newException(final String message)
	{
		final AdempiereException exception;
		if (exceptionFactory != null)
		{
			exception = exceptionFactory.createException(message);
		}
		else
		{
			exception = new AdempiereException(message)
					.appendParametersToMessage();
		}

		exception.setParameters(context);

		return exception;
	}

	public AssertThat<T> isEqualTo(final T expected)
	{
		if (!Objects.equals(expected, actual))
		{
			fail("Expected " + what + " to be <" + expected + "> but was <" + actual + ">");
		}

		return this;
	}

	@SuppressWarnings("SameParameterValue")
	public AssertThat<T> hasSameSize(@NonNull final Collection<?> expected)
	{
		final int expectedSize = expected.size();
		final int actualSize = getSize();
		if (actualSize != expectedSize)
		{
			fail("Expected " + what + " size to be <" + expectedSize + "> but was <" + actualSize + ">",
					ex -> ex
							.setParameter("expected", expected)
							.setParameter("actual", actual)
			);
		}

		return this;
	}

	private int getSize()
	{
		if (actual instanceof Collection)
		{
			return ((Collection<?>)actual).size();
		}
		else
		{
			throw new AdempiereException("Cannot determine size of " + actual);
		}
	}

	public void isNotNull()
	{
		if (actual == null)
		{
			fail("Expected " + what + " to be not null");
		}
	}
}
