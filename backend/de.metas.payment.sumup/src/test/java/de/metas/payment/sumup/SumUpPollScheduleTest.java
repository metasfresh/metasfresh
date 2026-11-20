package de.metas.payment.sumup;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpPollScheduleTest
{
	private static final Instant CREATED = Instant.parse("2026-11-16T10:00:00Z");

	/**
	 * @param ageSeconds              the transaction's age at the poller cycle
	 * @param secondsSinceLastLookup the time since its last lookup at the poller cycle, -1 if never looked up
	 */
	@ParameterizedTest(name = "age {0} s, last lookup {1} s ago -> due: {2}")
	@CsvSource({
			// fast phase (age < 5 min): every cycle
			"2, 2, true",
			"299, 1, true",
			// medium phase (5 min <= age < 1 h): once a minute
			"300, 59, false",
			"300, 60, true",
			"3599, 59, false",
			"3599, 60, true",
			// slow phase (age >= 1 h): once every 15 min
			"3600, 60, false",
			"3600, 899, false",
			"3600, 900, true",
			"86400, 899, false",
			"86400, 900, true",
			// never looked up: always due
			"300, -1, true",
			"86400, -1, true",
	})
	void isDue(final long ageSeconds, final long secondsSinceLastLookup, final boolean expectedDue)
	{
		final Instant now = CREATED.plus(Duration.ofSeconds(ageSeconds));
		final Instant lastLookupAt = secondsSinceLastLookup < 0 ? null : now.minus(Duration.ofSeconds(secondsSinceLastLookup));

		assertThat(SumUpPollSchedule.isDue(CREATED, lastLookupAt, now)).isEqualTo(expectedDue);
	}
}
