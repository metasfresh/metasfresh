package de.metas.frontend_testing.expectations.assertions;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SoftAssertions
{
	private final LinkedHashMap<String, Object> context = new LinkedHashMap<>();
	private final ArrayList<AdempiereException> errors = new ArrayList<>();

	@SuppressWarnings("unused")
	public SoftAssertions putContext(@NonNull final String key, @NonNull final Object value)
	{
		context.put(key, value);
		return this;
	}

	public <T> AssertThat<T> assertThat(@Nullable final T actual)
	{
		final AssertThat<T> assertThat = new AssertThat<>(actual);
		assertThat.setExceptionFactory(this::createException);
		assertThat.setExceptionCollector(this::collectError);
		return assertThat;
	}

	private AdempiereException createException(final String message)
	{
		return new AdempiereException(message)
				.appendParametersToMessage()
				.setParameters(context);

	}

	private void collectError(final AdempiereException error)
	{
		errors.add(error);
	}

	public void assertAll()
	{
		if (!errors.isEmpty())
		{
			throw new AdempiereException("Multiple errors occurred:\n" + errors.stream().map(AdempiereException::getLocalizedMessage).collect(java.util.stream.Collectors.joining("\n")));
		}
	}

}
