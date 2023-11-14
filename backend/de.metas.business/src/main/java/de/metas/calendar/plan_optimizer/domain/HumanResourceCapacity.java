package de.metas.calendar.plan_optimizer.domain;

import de.metas.resource.HumanResourceTestGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
public class HumanResourceCapacity
{
	@NonNull HumanResourceTestGroupId id;
	@NonNull Duration weeklyCapacity;
}
