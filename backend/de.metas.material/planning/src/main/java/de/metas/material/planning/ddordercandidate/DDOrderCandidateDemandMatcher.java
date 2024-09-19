package de.metas.material.planning.ddordercandidate;

import de.metas.material.planning.IMaterialDemandMatcher;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.util.Loggables;
import org.springframework.stereotype.Service;

@Service
class DDOrderCandidateDemandMatcher implements IMaterialDemandMatcher
{
	@Override
	public boolean matches(final MaterialPlanningContext context)
	{
		final ProductPlanning productPlanning = context.getProductPlanning();

		// Check if there is a distribution network
		if (productPlanning.getDistributionNetworkId() == null)
		{
			Loggables.addLog(
					"No distribution network configured in product data planning of the given mrp context; DDOrderDemandMatcher returns false; productPlanning={}; context={}",
					productPlanning, context);
			return false;
		}

		return true;
	}
}
