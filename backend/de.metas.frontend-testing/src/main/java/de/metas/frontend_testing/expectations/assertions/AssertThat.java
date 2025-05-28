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
	@Nullable private FailuresCollector failuresCollector;

	AssertThat(@Nullable final T actual)
	{
		this.actual = actual;
	}

	void setFailuresCollector(@Nullable final FailuresCollector failuresCollector)
	{
		this.failuresCollector = failuresCollector;
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

	private void fail(@NonNull final String message, @Nullable final Consumer<Failure> customizer)
	{
		final Failure failure = Failure.builder()
				.message(message)
				.context(context)
				.build();

		if (customizer != null)
		{
			customizer.accept(failure);
		}

		if (failuresCollector != null)
		{
			failuresCollector.collect(failure);
		}
		else
		{
			throw failure.toException();
		}
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
					failure -> failure
							.putContext("expected", expected)
							.putContext("actual", actual)
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

	public void isNull()
	{
		if (actual != null)
		{
			fail("Expected " + what + " to be null");
		}
	}
}
