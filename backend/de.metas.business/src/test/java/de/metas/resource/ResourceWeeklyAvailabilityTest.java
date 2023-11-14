package de.metas.resource;

import com.google.common.collect.ImmutableSet;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResourceWeeklyAvailabilityTest
{
	static ResourceAvailabilityRanges ranges(String... dates)
	{
		if (dates.length % 2 != 0)
		{
			throw new AdempiereException("Even number of dates is expected: " + Arrays.toString(dates));
		}

		final ArrayList<ResourceAvailabilityRange> list = new ArrayList<>();
		for (int i = 0; i < dates.length; i += 2)
		{
			list.add(ResourceAvailabilityRange.ofStartAndEndDate(
					LocalDateTime.parse(dates[i]),
					LocalDateTime.parse(dates[i + 1])));
		}

		return ResourceAvailabilityRanges.ofList(list);
	}

	@Nested
	class computeAvailabilityRanges
	{
		@Test
		void MondayToSunday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
					.build();

			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T10:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T10:00", "2023-11-03T12:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T16:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T16:00", "2023-11-03T18:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T23:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T23:00", "2023-11-04T01:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
		}

		@Test
		void MondayToFriday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.build();

			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T10:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T10:00", "2023-11-03T12:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T16:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T16:00", "2023-11-03T18:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T23:00"), Duration.ofHours(2)))
					.contains(ranges(
							"2023-11-03T23:00", "2023-11-04T00:00",
							"2023-11-06T00:00", "2023-11-06T01:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
		}

		@Test
		void MondayToFriday_09To17()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("17:00"))
					.build();

			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T10:00"), Duration.ofHours(2)))
					.contains(ranges("2023-11-03T10:00", "2023-11-03T12:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T16:00"), Duration.ofHours(2)))
					.contains(ranges(
							"2023-11-03T16:00", "2023-11-03T17:00",
							"2023-11-06T09:00", "2023-11-06T10:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(2);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T00:00"), Duration.ofHours(10)))
					.contains(ranges(
							"2023-11-03T09:00", "2023-11-03T17:00",
							"2023-11-06T09:00", "2023-11-06T11:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(10);
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T00:00"), Duration.ofHours(16)))
					.contains(ranges(
							"2023-11-03T09:00", "2023-11-03T17:00",
							"2023-11-06T09:00", "2023-11-06T17:00"));
			assertThat(availability.computeAvailabilityRanges(LocalDateTime.parse("2023-11-03T00:00"), Duration.ofHours(17)))
					.contains(ranges(
							"2023-11-03T09:00", "2023-11-03T17:00",
							"2023-11-06T09:00", "2023-11-06T17:00",
							"2023-11-07T09:00", "2023-11-07T10:00"))
					.get().extracting(ResourceAvailabilityRanges::getDuration, InstanceOfAssertFactories.DURATION).hasHours(17);

		}
	}

	@Nested
	class findNextSlotStart
	{
		@Test
		void MondayToSunday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
					.build();

			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T09:00"))).isEqualTo("2023-11-03T09:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-06T00:00"))).isEqualTo("2023-11-06T00:00");
		}

		@Test
		void MondayToFriday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.build();

			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T18:00"))).isEqualTo("2023-11-03T18:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-04T00:00"))).isEqualTo("2023-11-06T00:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-04T09:00"))).isEqualTo("2023-11-06T09:00");
		}

		@Test
		void MondayToFriday_09To17()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("17:00"))
					.build();

			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-02T23:00"))).isEqualTo("2023-11-03T09:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T00:00"))).isEqualTo("2023-11-03T09:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T09:00"))).isEqualTo("2023-11-03T09:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T10:00"))).isEqualTo("2023-11-03T10:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T16:59"))).isEqualTo("2023-11-03T16:59");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T17:00"))).isEqualTo("2023-11-06T09:00");
			assertThat(availability.findNextSlotStart(LocalDateTime.parse("2023-11-03T18:00"))).isEqualTo("2023-11-06T09:00");
		}
	}

	@Nested
	class findNextSlotEnd
	{
		@Test
		void MondayToSunday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
					.build();

			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T09:00"))).isEqualTo(LocalDate.MAX.atStartOfDay());
			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-06T00:00"))).isEqualTo(LocalDate.MAX.atStartOfDay());
		}

		@Test
		void MondayToFriday()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.build();

			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T09:00"))).isEqualTo("2023-11-04T00:00");
			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-06T00:00"))).isEqualTo("2023-11-11T00:00");
		}

		@Test
		void MondayToFriday_09To17()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("17:00"))
					.build();

			assertThatThrownBy(() -> availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T08:59"))).hasMessageStartingWith("Expected 2023-11-03T08:59 to be in an available slot");
			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T09:00"))).isEqualTo("2023-11-03T17:00");
			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T10:00"))).isEqualTo("2023-11-03T17:00");
			assertThat(availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T16:59"))).isEqualTo("2023-11-03T17:00");
			assertThatThrownBy(() -> availability.findNextSlotEnd(LocalDateTime.parse("2023-11-03T17:00"))).hasMessageStartingWith("Expected 2023-11-03T17:00 to be in an available slot");
		}
	}

	@Nested
	class timeSlotTruncatedTo
	{
		@Test
		void noTimeSlot()
		{
			var availability = ResourceWeeklyAvailability.builder().availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY)).build();
			assertThat(availability.timeSlotTruncatedTo(ChronoUnit.HOURS)).isSameAs(availability);
		}

		@Test
		void alreadyTruncated()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("23:00"))
					.build();

			assertThat(availability.timeSlotTruncatedTo(ChronoUnit.HOURS)).isSameAs(availability);
		}

		@Test
		void hours()
		{
			var availability = ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:59")).timeSlotEnd(LocalTime.parse("23:59"))
					.build();

			final var truncated = availability.timeSlotTruncatedTo(ChronoUnit.HOURS);
			assertThat(truncated).isEqualTo(ResourceWeeklyAvailability.builder()
					.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY))
					.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("23:00"))
					.build());
		}
	}
}