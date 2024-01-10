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

import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
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

	//
	// Selector
	@Nullable ProductId productId;
	@Nullable WarehouseId warehouseId;
	@NonNull @Builder.Default OrgId orgId = OrgId.ANY;
	@Nullable ResourceId plantId;
	boolean isAttributeDependant;
	@NonNull @Builder.Default AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.NONE;
	@Nullable String storageAttributesKey;
	int seqNo;

	//
	// Common
	@Nullable UserId plannerId;
	boolean isCreatePlan;
	int transferTimeDays;
	int leadTimeDays;
	boolean isDocComplete;
	boolean isLotForLot;

	//
	// Manufacturing
	boolean isManufactured;
	boolean isMatured;
	@Nullable ProductBOMVersionsId bomVersionsId;
	@Nullable PPRoutingId workflowId;
	@Nullable Quantity maxManufacturedQtyPerOrderDispo;
	@Nullable BigDecimal qtyProcessed_OnDate;
	@Nullable MaturingConfigId maturingConfigId;
	@Nullable MaturingConfigLineId maturingConfigLineId;
	// Picking
	boolean isPickingOrder;
	boolean isPickDirectlyIfFeasible;

	//
	// Purchasing
	boolean isPurchased;
	@Nullable OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse;

	//
	// Distribution
	@Nullable DistributionNetworkId distributionNetworkId;

	public ProductPlanningId getIdNotNull() {return Check.assumeNotNull(id, "product planning is saved: {}", this);}
}
