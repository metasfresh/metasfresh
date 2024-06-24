package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public enum WFProcessStatus
{
	NOT_STARTED,
	IN_PROGRESS,
	COMPLETED,
	;

	public boolean isNotStarted() {return NOT_STARTED.equals(this);}

	public static WFProcessStatus computeFromActivityStatuses(@NonNull final ImmutableSet<WFActivityStatus> activityStatuses)
	{
		if (activityStatuses.isEmpty())
		{
			// shall never happen
			return WFProcessStatus.COMPLETED;
		}
		else if (activityStatuses.size() == 1)
		{
			final WFActivityStatus activityStatus = activityStatuses.iterator().next();
			switch (activityStatus)
			{
				case NOT_STARTED:
					return WFProcessStatus.NOT_STARTED;
				case IN_PROGRESS:
					return WFProcessStatus.IN_PROGRESS;
				case COMPLETED:
					return WFProcessStatus.COMPLETED;
				default:
					throw new AdempiereException("Unknown activity status: " + activityStatus);
			}
		}
		else
		{
			return WFProcessStatus.IN_PROGRESS;
		}
	}
}
