package de.metas.resource;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;
import shadow.org.assertj.core.api.Assertions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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
		Assertions.assertThat(ranges("2023-11-03T00:00", "2023-11-03T10:00").getEndDate())
				.isEqualTo("2023-11-03T10:00");
		Assertions.assertThat(ranges("2023-11-03T00:00", "2023-11-03T10:00",
						"2023-11-06T17:00", "2023-11-06T18:00").getEndDate())
				.isEqualTo("2023-11-06T18:00");
	}
}