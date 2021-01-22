package org.eevolution.api;

import java.time.Duration;
import java.util.List;

import lombok.NonNull;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.validator.PP_Order_BOMLine;

import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;

public interface IPPCostCollectorBL extends ISingletonService
{
	I_PP_Cost_Collector getById(PPCostCollectorId costCollectorId);

	PPCostCollectorQuantities getQuantities(I_PP_Cost_Collector cc);

	void setQuantities(@NonNull I_PP_Cost_Collector cc, @NonNull PPCostCollectorQuantities from);

	Quantity getMovementQty(I_PP_Cost_Collector cc);

	Quantity getMovementQtyInStockingUOM(I_PP_Cost_Collector cc);

	Quantity getTotalDurationReportedAsQuantity(@NonNull I_PP_Cost_Collector cc);

	Duration getTotalDurationReported(I_PP_Cost_Collector cc);

	default boolean isMaterialReceiptOrCoProduct(final I_PP_Cost_Collector cc)
	{
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		return costCollectorType.isMaterialReceiptOrCoProduct();
	}

	default boolean isAnyComponentIssue(final I_PP_Cost_Collector cc)
	{
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());
		return costCollectorType.isAnyComponentIssue(orderBOMLineId);
	}

	default boolean isAnyComponentIssueOrCoProduct(final I_PP_Cost_Collector cc)
	{
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());
		return costCollectorType.isAnyComponentIssueOrCoProduct(orderBOMLineId);
	}

	/**
	 * Create and process material issue cost collector. The given qtys are converted to the UOM of the given <code>orderBOMLine</code>. The Cost collector's type is determined from the given
	 * <code>orderBOMLine</code> alone.
	 * <p>
	 * Note that this method is also used internally to handle the case of a "co-product receipt".
	 *
	 * @return processed cost collector
	 */
	I_PP_Cost_Collector createIssue(ComponentIssueCreateRequest request);

	/**
	 * Create Receipt (finished good or co/by-products). If the given <code>candidate</code>'s {@link PP_Order_BOMLine} is not <code>!= null</code>, then a finished product receipt is created.
	 * Otherwise a co-product receipt is created. Note that under the hood a co-product receipt is a "negative issue".
	 *
	 * @param candidate the candidate to create the receipt from.
	 *
	 * @return processed cost collector
	 */
	I_PP_Cost_Collector createReceipt(ReceiptCostCollectorCandidate candidate);

	void createActivityControl(ActivityControlCreateRequest request);

	void createMaterialUsageVariance(I_PP_Order ppOrder, I_PP_Order_BOMLine line);

	void createResourceUsageVariance(I_PP_Order ppOrder, PPOrderRoutingActivity activity);

	/**
	 * Checks if given cost collector is a reversal.
	 *
	 * We consider given cost collector as a reversal if it's ID is bigger then the Reversal_ID.
	 *
	 * @param cc cost collector
	 * @return true if given cost collector is actually a reversal.
	 */
	boolean isReversal(I_PP_Cost_Collector cc);

	boolean isFloorStock(I_PP_Cost_Collector cc);

	void updateCostCollectorFromOrder(I_PP_Cost_Collector cc, I_PP_Order order);

	List<I_PP_Cost_Collector> getByOrderId(PPOrderId ppOrderId);
}
