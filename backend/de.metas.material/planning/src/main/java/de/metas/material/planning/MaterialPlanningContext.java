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

import java.util.Objects;

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

	public void assertContextConsistent()
	{
		final ProductId contextProductId = getProductId();
		final ProductId productPlanningProductId = getProductPlanning().getProductId();

		if (!Objects.equals(contextProductId, productPlanningProductId))
		{
			final String message = String.format("The given IMaterialPlanningContext has M_Product_ID=%s, but its included PP_Product_Planning has M_Product_ID=%s",
					contextProductId, productPlanningProductId);
			throw new AdempiereException(message)
					.appendParametersToMessage()
					.setParameter("IMaterialPlanningContext", this)
					.setParameter("IMaterialPlanningContext.M_Product_ID", contextProductId)
					.setParameter("IMaterialPlanningContext.ProductPlanning", getProductPlanning())
					.setParameter("IMaterialPlanningContext.ProductPlanning.M_Product_ID", productPlanningProductId);
		}
	}

}
