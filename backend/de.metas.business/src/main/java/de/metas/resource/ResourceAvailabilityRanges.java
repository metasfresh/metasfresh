package de.metas.resource;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@EqualsAndHashCode
@ToString
public final class ResourceAvailabilityRanges
{
	@Getter private final LocalDateTime startDate;
	@Getter private final LocalDateTime endDate;
	private final ImmutableList<ResourceAvailabilityRange> list;

	private ResourceAvailabilityRanges(final List<ResourceAvailabilityRange> list)
	{
		Check.assume(!list.isEmpty(), "ranges list shall not  be empty");
		this.list = list.stream()
				.sorted(Comparator.comparing(ResourceAvailabilityRange::getStartDate))
				.collect(ImmutableList.toImmutableList());
		this.startDate = this.list.get(0).getStartDate();
		this.endDate = this.list.get(list.size() - 1).getEndDate();
	}

	public static ResourceAvailabilityRanges ofList(@NonNull final List<ResourceAvailabilityRange> list)
	{
		return new ResourceAvailabilityRanges(list);
	}

	public static ResourceAvailabilityRanges ofStartAndEndDate(@NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate)
	{
		return ofList(ImmutableList.of(ResourceAvailabilityRange.ofStartAndEndDate(startDate, endDate)));
	}
}
