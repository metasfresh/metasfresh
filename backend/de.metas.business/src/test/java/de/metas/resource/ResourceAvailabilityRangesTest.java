package de.metas.resource;

import com.google.common.collect.ImmutableMap;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;
import org.threeten.extra.YearWeek;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceAvailabilityRangesTest
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

	@Test
	void getEndDate()
	{
		assertThat(ranges("2023-11-03T00:00", "2023-11-03T10:00").getEndDate())
				.isEqualTo("2023-11-03T10:00");
		assertThat(ranges("2023-11-03T00:00", "2023-11-03T10:00",
				"2023-11-06T17:00", "2023-11-06T18:00").getEndDate())
				.isEqualTo("2023-11-06T18:00");
	}

	@Test
	void getDurationByWeek()
	{
		assertThat(ranges(
						"2023-11-05T23:00", "2023-11-06T01:00", // W44 - 1h, W45 - 1h
						"2023-11-06T09:00", "2023-11-06T10:00", // W45 - 1h
						"2023-11-12T23:00", "2023-11-13T01:00" // W45 - 1h, W46 - 1h
				).getDurationByWeek()
		)
				.isEqualTo(ImmutableMap.of(
						YearWeek.of(2023, 44), Duration.ofHours(1),
						YearWeek.of(2023, 45), Duration.ofHours(3),
						YearWeek.of(2023, 46), Duration.ofHours(1)
				));
	}
}