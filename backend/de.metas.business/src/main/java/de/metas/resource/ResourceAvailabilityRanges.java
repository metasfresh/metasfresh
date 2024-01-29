package de.metas.resource;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.threeten.extra.YearWeek;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class ResourceAvailabilityRanges
{
	@NonNull private final ImmutableList<ResourceAvailabilityRange> list;
	@NonNull @Getter private final LocalDateTime startDate;
	@NonNull @Getter private final LocalDateTime endDate;
	@NonNull @Getter private final Duration duration;

	private ResourceAvailabilityRanges(final List<ResourceAvailabilityRange> list)
	{
		Check.assume(!list.isEmpty(), "ranges list shall not  be empty");
		this.list = list.stream()
				.sorted(Comparator.comparing(ResourceAvailabilityRange::getStartDate))
				.collect(ImmutableList.toImmutableList());
		this.startDate = this.list.get(0).getStartDate();
		this.endDate = this.list.get(list.size() - 1).getEndDate();
		this.duration = list.stream()
				.map(ResourceAvailabilityRange::getDuration)
				.reduce(Duration.ZERO, Duration::plus);
	}

	public static ResourceAvailabilityRanges ofList(@NonNull final List<ResourceAvailabilityRange> list)
	{
		return new ResourceAvailabilityRanges(list);
	}

	public static ResourceAvailabilityRanges ofStartAndEndDate(@NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate)
	{
		return ofList(ImmutableList.of(ResourceAvailabilityRange.ofStartAndEndDate(startDate, endDate)));
	}

	public List<ResourceAvailabilityRange> toList() {return list;}

	public Stream<ResourceAvailabilityRange> stream() {return list.stream();}

	public Map<YearWeek, Duration> getDurationByWeek()
	{
		final HashMap<YearWeek, Duration> result = new HashMap<>();
		for (final ResourceAvailabilityRange range : this.list)
		{
			final Map<YearWeek, Duration> partialResult = range.getDurationByWeek();
			partialResult.forEach((week, duration) -> result.merge(week, duration, Duration::plus));
		}
		return result;
	}

	public CalendarDateRange toCalendarDateRange(@NonNull final ZoneId timeZone)
	{
		return CalendarDateRange.builder()
				.startDate(startDate.atZone(timeZone).toInstant())
				.endDate(endDate.atZone(timeZone).toInstant())
				.build();
	}
}
