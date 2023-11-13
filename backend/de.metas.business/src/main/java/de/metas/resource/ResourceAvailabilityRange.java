package de.metas.resource;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.LocalDateTime;

@Value(staticConstructor = "ofStartAndEndDate")
public class ResourceAvailabilityRange
{
	@NonNull LocalDateTime startDate;
	@NonNull LocalDateTime endDate;

	private ResourceAvailabilityRange(@NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate)
	{
		if (!startDate.isBefore(endDate))
		{
			throw new AdempiereException("Start date `" + startDate + "` shall be before end date `" + endDate + "`");
		}

		this.startDate = startDate;
		this.endDate = endDate;
	}
}
