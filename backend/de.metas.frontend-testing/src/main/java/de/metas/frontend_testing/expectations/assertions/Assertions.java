package de.metas.frontend_testing.expectations.assertions;

import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

import javax.annotation.Nullable;

@UtilityClass
public class Assertions
{
	private static final Logger logger = LogManager.getLogger(Assertions.class);
	private static final ThreadLocal<SoftAssertions> softAssertionsHolder = new ThreadLocal<>();

	public static <T> AssertThat<T> assertThat(@Nullable final T actual)
	{
		final SoftAssertions softly = softAssertionsHolder.get();
		if (softly != null)
		{
			return softly.assertThat(actual);
		}
		else
		{
			return new AssertThat<>(actual);
		}
	}

	public static AutoCloseableSoftAssertions temporarySoftly()
	{
		return AutoCloseableSoftAssertions.createAndOpen(softAssertionsHolder);
	}

	public static void softly(@NonNull final Runnable runnable)
	{
		try (final AutoCloseableSoftAssertions ignored = temporarySoftly())
		{
			runnable.run();
		}
	}

	public static void softlyPutContext(@NonNull final String key, @NonNull final Object value)
	{
		final SoftAssertions softAssertions = softAssertionsHolder.get();
		if (softAssertions != null)
		{
			softAssertions.putContext(key, value);
		}
		else
		{
			logger.warn("No SoftAssertions available. Ignoring context: {}={}", key, value);
		}
	}

}
