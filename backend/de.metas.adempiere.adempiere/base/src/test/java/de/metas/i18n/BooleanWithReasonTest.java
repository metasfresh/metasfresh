package de.metas.i18n;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanWithReasonTest
{
	@Nested
	class and_Supplier
	{
		@Test
		void false_and_false()
		{
			final BooleanWithReason result = BooleanWithReason.falseBecause("1")
					.and(() -> BooleanWithReason.falseBecause("2"));
			assertThat(result).isEqualTo(BooleanWithReason.falseBecause("1"));
		}

		@Test
		void false_and_true()
		{
			final BooleanWithReason result = BooleanWithReason.falseBecause("1")
					.and(() -> BooleanWithReason.trueBecause("2"));
			assertThat(result).isEqualTo(BooleanWithReason.falseBecause("1"));
		}

		@Test
		void true_and_false()
		{
			final BooleanWithReason result = BooleanWithReason.trueBecause("1")
					.and(() -> BooleanWithReason.falseBecause("2"));
			assertThat(result).isEqualTo(BooleanWithReason.falseBecause("2"));
		}

		@Test
		void true_and_true()
		{
			final BooleanWithReason result = BooleanWithReason.trueBecause("1")
					.and(() -> BooleanWithReason.trueBecause("2"));
			assertThat(result).isEqualTo(BooleanWithReason.trueBecause("2"));
		}
	}
}