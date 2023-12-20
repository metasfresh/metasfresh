package de.metas.calendar.plan_optimizer.domain;

import de.metas.product.ResourceId;
import de.metas.resource.HumanResourceTestGroupId;
import lombok.NonNull;
import lombok.Value;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.annotation.Nullable;

@Value
public class Resource
{
	@PlanningId @NonNull ResourceId id;
	@NonNull String name;

	@Nullable HumanResourceTestGroupId humanResourceTestGroupId;

	@Override
	public String toString() {return name;}
}
