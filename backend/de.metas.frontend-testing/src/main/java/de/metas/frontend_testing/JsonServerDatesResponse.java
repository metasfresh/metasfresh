package de.metas.frontend_testing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Noon of yesterday/today/tomorrow in the server's own time zone, i.e. the days that server-side "today" filters
 * (e.g. the manufacturing jobs list) use. Lets a test place records on a day without relying on the time zone of
 * the machine that runs the test.
 */
@Value
@Builder
public class JsonServerDatesResponse
{
	@NonNull String zoneId;
	@NonNull String yesterdayNoon; // ISO instant
	@NonNull String todayNoon; // ISO instant
	@NonNull String tomorrowNoon; // ISO instant
}
