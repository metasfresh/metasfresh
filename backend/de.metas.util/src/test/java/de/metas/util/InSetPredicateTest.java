package de.metas.util;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InSetPredicateTest
{
	@Nested
	class test
	{
		@Test
		void any()
		{
			assertThat(InSetPredicate.<Integer>any().test(null)).isTrue();
			assertThat(InSetPredicate.<Integer>any().test(1)).isTrue();
		}

		@Test
		void none()
		{
			assertThat(InSetPredicate.<Integer>none().test(null)).isFalse();
			assertThat(InSetPredicate.<Integer>none().test(1)).isFalse();
		}

		@Test
		void only()
		{
			final InSetPredicate<Integer> predicate = InSetPredicate.only(1, 2, 3, null);
			assertThat(predicate.test(null)).isTrue();
			assertThat(predicate.test(1)).isTrue();
			assertThat(predicate.test(2)).isTrue();
			assertThat(predicate.test(3)).isTrue();
			assertThat(predicate.test(4)).isFalse();
		}
	}

	@Nested
	class toSet
	{
		@Test
		void any()
		{
			assertThatThrownBy(() -> InSetPredicate.any().toSet())
					.hasMessageStartingWith("Expected predicate to not be ANY");
		}

		@Test
		void none()
		{
			assertThat(InSetPredicate.none().toSet()).isEmpty();
		}

		@Test
		void only()
		{
			assertThat(InSetPredicate.only(1, 2, null).toSet())
					.containsExactlyInAnyOrder(1, 2, null);
		}
	}

	@Test
	void only_of_empty_is_none()
	{
		assertThat(InSetPredicate.only(ImmutableSet.of()).isNone()).isTrue();
	}

}