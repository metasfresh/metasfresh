package org.adempiere.model.engines;

import org.compiere.model.I_M_Transaction;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.costing.CostAmount;
import de.metas.product.ResourceId;

public interface CostEngine
{
	void createActivityControl(I_PP_Cost_Collector cc);

	void createUsageVariances(I_PP_Cost_Collector ccuv);

	void createRateVariances(I_PP_Cost_Collector cc);

	void createMethodVariances(I_PP_Cost_Collector cc);

	void createReversals(I_PP_Cost_Collector reversalCostCollector);

	void createOrUpdateCostDetail(I_PP_Cost_Collector cc, I_M_Transaction mtrx);

	CostAmount getResourceActualCostRate(ResourceId resourceId, CostDimension d);
}
