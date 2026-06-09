package de.metas.distribution.mobileui.launchers;

import org.eevolution.model.I_DD_Order;

import java.util.List;

public interface DistributionOrderCollector<T>
{
	void collect(I_DD_Order ddOrder);

	List<T> getCollectedItems();
}
