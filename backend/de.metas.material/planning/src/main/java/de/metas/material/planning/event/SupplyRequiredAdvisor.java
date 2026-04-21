package de.metas.material.planning.event;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.PlanningUsage;
import de.metas.quantity.Quantity;
import lombok.NonNull;

import java.util.List;

public interface SupplyRequiredAdvisor
{
	/**
	 * @return the {@link PlanningUsage} this advisor handles. Used by {@link SupplyRequiredHandler}
	 * to dispatch the correct per-usage {@link MaterialPlanningContext}, so that two product plannings
	 * with different usages both get a chance to produce advised events.
	 */
	@NonNull
	PlanningUsage getPlanningUsage();

	List<? extends MaterialEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context);

	Quantity handleQuantityDecrease(final @NonNull SupplyRequiredDecreasedEvent event,
									final Quantity qtyToDistribute);

}
