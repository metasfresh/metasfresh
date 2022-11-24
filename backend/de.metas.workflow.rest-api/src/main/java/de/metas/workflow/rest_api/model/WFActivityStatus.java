package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Collection;
import java.util.function.Function;

public enum WFActivityStatus
{
	NOT_STARTED,
	IN_PROGRESS,
	COMPLETED;

	public boolean isCompleted() {return this.equals(COMPLETED);}

	public static <T> WFActivityStatus computeStatusFromLines(@NonNull final Collection<T> lines, @NonNull final Function<T, WFActivityStatus> statusExtractor)
	{
		final ImmutableSet<WFActivityStatus> lineStatuses = lines.stream().map(statusExtractor).collect(ImmutableSet.toImmutableSet());
		if (lineStatuses.isEmpty())
		{
			// Corner case: if no lines, consider the activity completed
			return COMPLETED;
		}
		else if (lineStatuses.size() == 1)
		{
			return lineStatuses.iterator().next();
		}
		else
		{
			return IN_PROGRESS;
		}
	}
}
