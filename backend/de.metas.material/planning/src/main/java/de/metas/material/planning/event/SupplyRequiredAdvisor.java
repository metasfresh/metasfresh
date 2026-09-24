package de.metas.material.planning.event;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.PlanningUsage;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public interface SupplyRequiredAdvisor
{
	/**
	 * @return the {@link PlanningUsage} this advisor handles. Used by {@link SupplyRequiredHandler}
	 * to dispatch the correct per-usage {@link MaterialPlanningContext} and to establish priority:
	 * advisors are invoked in {@link PlanningUsage} declaration order
	 * (distribution -> manufacturing -> purchasing).
	 */
	@NonNull
	PlanningUsage getPlanningUsage();

	/**
	 * Create the advised events for the given demand.
	 *
	 * @param remainingQty the portion of the demand not yet claimed by higher-priority advisors.
	 *                     The advisor must report back (via {@link SupplyAdvice#getConsumedQty()})
	 *                     how much of this quantity it took, so the next advisor in priority order
	 *                     gets a correctly reduced remainder.
	 * @return the advised events + the qty this advisor claimed (see {@link SupplyAdvice}).
	 */
	@NonNull
	SupplyAdvice createAdvisedEvents(
			@NonNull SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull MaterialPlanningContext context,
			@NonNull Quantity remainingQty);

	Quantity handleQuantityDecrease(@NonNull SupplyRequiredDecreasedEvent event,
									Quantity qtyToDistribute);

}
