package de.metas.material.planning;

import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class MaterialPlanningContext
{
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull WarehouseId warehouseId;
	@NonNull ProductPlanning productPlanning;
	@NonNull ResourceId plantId;
	@NonNull ClientAndOrgId clientAndOrgId;

	@Nullable ProductPlanning ppOrderProductPlanning;

	public void assertContextConsistent()
	{
		if (getProductPlanning().getProductId() != null && !ProductId.equals(productId, productPlanning.getProductId()))
		{
			throw new AdempiereException("Context product is not matching product planning")
					.appendParametersToMessage()
					.setParameter("context", this);
		}
		if (getProductPlanning().getWarehouseId() != null && !WarehouseId.equals(warehouseId, productPlanning.getWarehouseId()))
		{
			throw new AdempiereException("Context warehouse is not matching product planning")
					.appendParametersToMessage()
					.setParameter("context", this);
		}
	}

}
