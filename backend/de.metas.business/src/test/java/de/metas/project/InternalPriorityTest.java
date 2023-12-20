package de.metas.project;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InternalPriorityTest
{
	@Nested
	class isHigherThan
	{
		@Test
		void LOW_isHigherThan_HIGH_expect_false() {assertThat(InternalPriority.LOW.isHigherThan(InternalPriority.HIGH)).isFalse();}

		@Test
		void HIGH_isHigherThan_LOW_expect_false() {assertThat(InternalPriority.HIGH.isHigherThan(InternalPriority.LOW)).isTrue();}

		@Test
		void HIGH_isHigherThan_HIGH_expect_false() {assertThat(InternalPriority.HIGH.isHigherThan(InternalPriority.HIGH)).isFalse();}
	}
}