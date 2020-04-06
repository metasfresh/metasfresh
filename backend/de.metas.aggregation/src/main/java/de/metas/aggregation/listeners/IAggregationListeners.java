package de.metas.aggregation.listeners;

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.util.ISingletonService;

/**
 * Aggregation master data listeners service.
 * 
 * Used for registering listeners and listeners firing.
 * 
 * @author tsa
 *
 */
public interface IAggregationListeners extends ISingletonService
{
	void addListener(final IAggregationListener listener);

	void fireAggregationCreated(final I_C_Aggregation aggregation);

	void fireAggregationChanged(final I_C_Aggregation aggregation);

	void fireAggregationDeleted(final I_C_Aggregation aggregation);
}
