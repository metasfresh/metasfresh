package de.metas.project;

import org.assertj.core.api.SoftAssertions;
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

	@Test
	@SuppressWarnings("AssertThatIsZeroOne")
	void toIntUrgentToMinor()
	{
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(InternalPriority.URGENT.toIntUrgentToMinor()).isEqualTo(1);
		softly.assertThat(InternalPriority.HIGH.toIntUrgentToMinor()).isEqualTo(3);
		softly.assertThat(InternalPriority.MEDIUM.toIntUrgentToMinor()).isEqualTo(5);
		softly.assertThat(InternalPriority.LOW.toIntUrgentToMinor()).isEqualTo(7);
		softly.assertThat(InternalPriority.MINOR.toIntUrgentToMinor()).isEqualTo(9);
		softly.assertAll();
	}

	@Test
	@SuppressWarnings("AssertThatIsZeroOne")
	void toIntMinorToUrgent()
	{
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(InternalPriority.MINOR.toIntMinorToUrgent()).isEqualTo(1);
		softly.assertThat(InternalPriority.LOW.toIntMinorToUrgent()).isEqualTo(3);
		softly.assertThat(InternalPriority.MEDIUM.toIntMinorToUrgent()).isEqualTo(5);
		softly.assertThat(InternalPriority.HIGH.toIntMinorToUrgent()).isEqualTo(7);
		softly.assertThat(InternalPriority.URGENT.toIntMinorToUrgent()).isEqualTo(9);
		softly.assertAll();
	}
}