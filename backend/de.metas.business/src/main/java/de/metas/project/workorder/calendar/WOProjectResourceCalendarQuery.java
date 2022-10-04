package de.metas.project.workorder.calendar;

import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class WOProjectResourceCalendarQuery
{
	@NonNull @Builder.Default InSetPredicate<ResourceId> resourceIds = InSetPredicate.any();
	@NonNull @Builder.Default InSetPredicate<ProjectId> projectIds = InSetPredicate.any();

	@Nullable Instant startDate;
	@Nullable Instant endDate;

	public boolean isAny()
	{
		return resourceIds.isAny()
				&& projectIds.isAny()
				&& startDate == null
				&& endDate == null;
	}
}
