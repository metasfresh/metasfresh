package org.eevolution.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.eevolution.model.I_PP_Cost_Collector;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface IPPCostCollectorDAO extends ISingletonService
{
	BigDecimal getQtyUsageVariance(PPOrderBOMLineId orderBOMLineId);

	List<I_PP_Cost_Collector> getByParent(I_PP_Cost_Collector parentCostCollector);

	I_PP_Cost_Collector getById(PPCostCollectorId costCollectorId);

	<T extends I_PP_Cost_Collector> T getById(PPCostCollectorId costCollectorId, Class<T> modelClass);

	List<I_PP_Cost_Collector> getByIds(Set<PPCostCollectorId> costCollectorIds);

	<T extends I_PP_Cost_Collector> List<T> getByIds(Set<PPCostCollectorId> costCollectorIds, Class<T> modelClass);

	List<I_PP_Cost_Collector> getByOrderId(PPOrderId ppOrderId);

	/**
	 * Retrieve the cost collectors of the given <code>order</code> that are active and are either completed or closed.
	 */
	List<I_PP_Cost_Collector> getCompletedOrClosedByOrderId(PPOrderId order);

	/**
	 * Retrieve the cost collectors for the given ppOrder. The cost collectors must have:
	 * <ul>
	 * <li>ppOrder's id
	 * <li>status completed
	 * <li>type Material receipt
	 * </ul>
	 */
	List<I_PP_Cost_Collector> getReceiptsByOrderId(PPOrderId ppOrderId);

	Duration getTotalSetupTimeReal(PPOrderRoutingActivity activity, CostCollectorType costCollectorType);

	Duration getDurationReal(PPOrderRoutingActivity activity, CostCollectorType costCollectorType);

	void save(I_PP_Cost_Collector cc);

	Stream<I_PP_Cost_Collector> stream(@NonNull IQueryFilter<I_PP_Cost_Collector> filter);
}
