package de.metas.manufacturing.issue.plan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;

@Value
@Builder
public class PPOrderIssuePlanStep
{
	@NonNull PPOrderBOMLineId orderBOMLineId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyToIssue;

	@NonNull LocatorId pickFromLocatorId;
	@NonNull I_M_HU pickFromTopLevelHU;

	boolean isAlternative;

	public HuId getPickFromTopLevelHUId() {return HuId.ofRepoId(pickFromTopLevelHU.getM_HU_ID());}
}
