package de.metas.material.planning.event;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.MaterialPlanningContext;
import lombok.NonNull;

import java.util.List;

public interface SupplyRequiredAdvisor
{
	List<? extends MaterialEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context);

}
