package de.metas.frontend_testing.expectations.assertions;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class Assertions
{
	public static <T> AssertThat<T> assertThat(@Nullable final T actual)
	{
		return new AssertThat<>(actual);
	}

	public static SoftAssertions newSoftAssertions()
	{
		return new SoftAssertions();
	}
}
