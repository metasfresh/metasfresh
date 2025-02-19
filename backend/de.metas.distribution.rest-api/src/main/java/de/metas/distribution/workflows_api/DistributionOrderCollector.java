package de.metas.distribution.workflows_api;

import org.eevolution.model.I_DD_Order;

import java.util.Collection;

public interface DistributionOrderCollector<T>
{
	void collect(I_DD_Order ddOrder, boolean isJobStarted);

	Collection<T> getCollectedItems();
}
