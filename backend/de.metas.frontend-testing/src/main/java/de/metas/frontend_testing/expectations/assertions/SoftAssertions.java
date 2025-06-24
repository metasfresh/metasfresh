package de.metas.frontend_testing.expectations.assertions;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedReturnValue")
public class SoftAssertions
{
	private final LinkedHashMap<String, Object> context = new LinkedHashMap<>();
	private final ArrayList<Failure> failures = new ArrayList<>();

	public SoftAssertions(@Nullable final SoftAssertions parent)
	{
		if (parent != null)
		{
			this.context.putAll(parent.context);
		}
	}

	public SoftAssertions putContext(@NonNull final String key, @NonNull final Object value)
	{
		context.put(key, value);
		return this;
	}

	public <T> AssertThat<T> assertThat(@Nullable final T actual)
	{
		final AssertThat<T> assertThat = new AssertThat<>(actual);
		assertThat.setFailuresCollector(this::collectFailure);
		return assertThat;
	}

	private void collectFailure(@NonNull final Failure failure)
	{
		failure.putContext(context);
		failures.add(failure);
	}

	void collectFailures(final SoftAssertions other)
	{
		other.toSingleFailure().ifPresent(this::collectFailure);
	}

	public void assertAll()
	{
		final Failure failure = toSingleFailure().orElse(null);
		if (failure == null)
		{
			return;
		}

		throw failure.toException();
	}

	private Optional<Failure> toSingleFailure()
	{
		if (failures.isEmpty())
		{
			return Optional.empty();
		}
		else if (failures.size() == 1)
		{
			final Failure failure = failures.get(0);
			return Optional.of(failure);
		}
		else
		{
			return Optional.of(
					Failure.builder()
							.message("Multiple errors occurred")
							.context(context)
							.causes(failures.stream()
									.map(failure -> failure.removeContext(context))
									.collect(Collectors.toList()))
							.build()
			);
		}
	}

	public void fail(@NonNull final String message)
	{
		collectFailure(
				Failure.builder()
						.message(message)
						.context(context)
						.build()
		);
	}
}
