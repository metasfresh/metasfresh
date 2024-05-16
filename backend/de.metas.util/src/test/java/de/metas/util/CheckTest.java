package de.metas.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckTest
{
	@Test
	void mkEx_with_message()
	{
		final RuntimeException ex = Check.mkEx("test message");
		Assertions.assertThat(ex).hasMessage("test message");
	}

	@Test
	void mkEx_with_message_and_cause()
	{
		final RuntimeException cause = new RuntimeException("cause");
		final RuntimeException ex = Check.mkEx("test message", cause);
		Assertions.assertThat(ex)
				.hasMessage("test message")
				.hasCause(cause);
	}

}