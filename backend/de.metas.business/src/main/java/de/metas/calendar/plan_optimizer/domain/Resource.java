package de.metas.calendar.plan_optimizer.domain;

import de.metas.product.ResourceId;
import lombok.NonNull;
import lombok.Value;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Value
public class Resource
{
	@PlanningId @NonNull ResourceId id;
	@NonNull String name;

	@Override
	public String toString() {return name;}
}
