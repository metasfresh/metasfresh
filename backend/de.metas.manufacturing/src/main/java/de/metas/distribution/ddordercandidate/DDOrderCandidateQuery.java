package de.metas.distribution.ddordercandidate;

import de.metas.impexp.InputDataSourceId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import javax.annotation.Nullable;

@Value
@Builder
public class DDOrderCandidateQuery
{
	@Nullable ProductId productId;
	@Nullable WarehouseId sourceWarehouseId;
	@Nullable WarehouseId targetWarehouseId;
	@Nullable OrderLineId salesOrderLineId;
	@Nullable Boolean processed;
	@Nullable Boolean isSimulated;
	@Nullable PPOrderId excludePPOrderId;
	@Nullable PPOrderCandidateId ppOrderCandidateId;
	@Nullable DDOrderCandidateId ddOrderCandidateId;
	@Nullable PPOrderBOMLineId ppOrderBOMLineId;
	@Nullable InputDataSourceId inputDataSourceId;
	@Nullable IQueryFilter<I_DD_Order_Candidate> userSelectionFilter;
	@Nullable Boolean onlyPositiveQtyToProcess;
}
