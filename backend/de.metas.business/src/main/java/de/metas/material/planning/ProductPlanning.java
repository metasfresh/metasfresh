/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.material.planning;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.OnMaterialReceiptWithDestWarehouse;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.ProductBOMVersionsId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class ProductPlanning
{
	boolean disallowSaving;
	@Nullable ProductPlanningId id;
	@Nullable ProductPlanningSchemaId productPlanningSchemaId;
	@Nullable ProductId productId; // = ProductId.ofRepoId(getProductPlanning().getM_Product_ID());
	@Nullable WarehouseId warehouseId;
	@NonNull @Builder.Default OrgId orgId = OrgId.ANY;

	@Nullable ResourceId plantId;  // = ResourceId.ofRepoIdOrNull(productPlanningRecord.getS_Resource_ID());
	@Nullable PPRoutingId workflowId; // PPRoutingId.ofRepoIdOrNull(productPlanning.getAD_Workflow_ID())

	boolean isAttributeDependant;
	@NonNull @Builder.Default AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.NONE;

	@Nullable UserId plannerId; // UserId.ofRepoIdOrNull(productPlanning.getPlanner_ID());
	boolean isCreatePlan;
	@Nullable ProductBOMVersionsId bomVersionsId;
	boolean isPickingOrder;
	boolean isPickDirectlyIfFeasible;
	boolean isDocComplete;
	int seqNo;
	int transferTimeDays; // = productPlanningData.getTransfertTime().intValueExact();
	int leadTimeDays; //= productPlanningRecord.getDeliveryTime_Promised().intValueExact();

	boolean isManufactured; // =StringUtils.toBoolean(productPlanning.getIsManufactured());
	boolean isPurchased;

	@Nullable Quantity maxManufacturedQtyPerOrderDispo;
	// if (productPlanning.getMaxManufacturedQtyPerOrderDispo().signum() > 0 && productPlanning.getMaxManufacturedQtyPerOrderDispo_UOM_ID() > 0)
	// {
	// 	maxQtyPerOrder = Quantitys.create(
	// 			productPlanning.getMaxManufacturedQtyPerOrderDispo(),
	// 			UomId.ofRepoId(productPlanning.getMaxManufacturedQtyPerOrderDispo_UOM_ID()));
	// }

	@Nullable DistributionNetworkId distributionNetworkId;

	@Nullable OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse;

	public ProductPlanningId getIdNotNull() {return Check.assumeNotNull(id, "product planning is saved: {}", this);}

	@Deprecated
	public BigDecimal getDeliveryTime_Promised() {return BigDecimal.valueOf(leadTimeDays);}

	@Deprecated
	public BigDecimal getTransfertTime() {return BigDecimal.valueOf(transferTimeDays);}

	public ProductPlanning withId(final ProductPlanningId id)
	{
		return ProductPlanningId.equals(this.id, id)
				? this
				: toBuilder().id(id).build();
	}
}
