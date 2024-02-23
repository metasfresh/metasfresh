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
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

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
	@NonNull OrgId orgId;
	@Nullable ResourceId plantId;
	boolean isAttributeDependant;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@Nullable String storageAttributesKey;
	int seqNo;

	//
	// Common
	@Nullable UserId plannerId;
	boolean isCreatePlan;
	int transferTimeDays;
	int leadTimeDays;
	boolean isDocComplete;

	//
	// Manufacturing
	boolean isManufactured;
	boolean isMatured;
	@Nullable ProductBOMVersionsId bomVersionsId;
	@Nullable PPRoutingId workflowId;
	@Nullable Quantity maxManufacturedQtyPerOrderDispo;
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

	public ProductPlanning(final boolean disallowSaving,
			@Nullable final ProductPlanningId id,
			@Nullable final ProductPlanningSchemaId productPlanningSchemaId,
			@Nullable final ProductId productId,
			@Nullable final WarehouseId warehouseId,
			@Nullable final OrgId orgId,
			@Nullable final ResourceId plantId,
			final boolean isAttributeDependant,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final String storageAttributesKey,
			final int seqNo,
			@Nullable final UserId plannerId,
			final boolean isCreatePlan,
			final int transferTimeDays,
			final int leadTimeDays,
			final boolean isDocComplete,
			final boolean isManufactured,
			final boolean isMatured,
			@Nullable final ProductBOMVersionsId bomVersionsId,
			@Nullable final PPRoutingId workflowId,
			@Nullable final Quantity maxManufacturedQtyPerOrderDispo,
			@Nullable final MaturingConfigId maturingConfigId,
			@Nullable final MaturingConfigLineId maturingConfigLineId,
			final boolean isPickingOrder,
			final boolean isPickDirectlyIfFeasible,
			final boolean isPurchased,
			@Nullable final OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse,
			@Nullable final DistributionNetworkId distributionNetworkId)
	{
		if (isMatured)
		{
			FillMandatoryException.assumeNotNull(maturingConfigId, I_PP_Product_Planning.COLUMNNAME_M_Maturing_Configuration_ID);
			FillMandatoryException.assumeNotNull(maturingConfigLineId, I_PP_Product_Planning.COLUMNNAME_M_Maturing_Configuration_Line_ID);
			FillMandatoryException.assumeNotNull(warehouseId, I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID);
			FillMandatoryException.assumeNotNull(plantId, I_PP_Product_Planning.COLUMNNAME_S_Resource_ID);
		}
		this.disallowSaving = disallowSaving;
		this.id = id;
		this.productPlanningSchemaId = productPlanningSchemaId;
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.orgId = coalesceNotNull(orgId, OrgId.ANY);
		this.plantId = plantId;
		this.isAttributeDependant = isAttributeDependant;
		this.attributeSetInstanceId = coalesceNotNull(attributeSetInstanceId,AttributeSetInstanceId.NONE);
		this.storageAttributesKey = storageAttributesKey;
		this.seqNo = seqNo;
		this.plannerId = plannerId;
		this.isCreatePlan = isCreatePlan;
		this.transferTimeDays = transferTimeDays;
		this.leadTimeDays = leadTimeDays;
		this.isDocComplete = isDocComplete;
		this.isManufactured = isManufactured;
		this.isMatured = isMatured;
		this.bomVersionsId = bomVersionsId;
		this.workflowId = workflowId;
		this.maxManufacturedQtyPerOrderDispo = maxManufacturedQtyPerOrderDispo;
		this.maturingConfigId = maturingConfigId;
		this.maturingConfigLineId = maturingConfigLineId;
		this.isPickingOrder = isPickingOrder;
		this.isPickDirectlyIfFeasible = isPickDirectlyIfFeasible;
		this.isPurchased = isPurchased;
		this.onMaterialReceiptWithDestWarehouse = onMaterialReceiptWithDestWarehouse;
		this.distributionNetworkId = distributionNetworkId;
	}

	public ProductPlanningId getIdNotNull()
	{
		return Check.assumeNotNull(id, "product planning is saved: {}", this);
	}
}
