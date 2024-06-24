package de.metas.i18n;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("deprecation")
class MsgTest
{
	@Nested
	class normalizeToJavaMessageFormat
	{
		@Test
		void empty()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("")).isEmpty();
		}

		@Test
		void noPlaceholder()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("some text without placeholders"))
					.isEqualTo("some text without placeholders");
		}

		@Test
		void placeholderAtStringStart()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("{} bla")).isEqualTo("{0} bla");
		}

		@Test
		void placeholderAtStringEnd()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("bla {}")).isEqualTo("bla {0}");
		}

		@Test
		void multiplePlaceholders()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("{} then {} and then {}"))
					.isEqualTo("{0} then {1} and then {2}");
		}

		@Test
		void multiplePlaceholdersMixedWithNormalizedPlaceholders()
		{
			assertThat(Msg.normalizeToJavaMessageFormat("{} vs {2} then {} vs {1} and then {} vs {0}"))
					.isEqualTo("{0} vs {2} then {1} vs {1} and then {2} vs {0}");
		}

	}
}