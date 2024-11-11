package org.eevolution.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderCreateRequest.PPOrderCreateRequestBuilder;

import javax.annotation.Nullable;
import java.time.Instant;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@JsonDeserialize(builder = PPOrderCreateRequestBuilder.class)
public class PPOrderCreateRequest
{
	@NonNull PPOrderDocBaseType docBaseType;
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable ProductPlanningId productPlanningId;
	@Nullable MaterialDispoGroupId materialDispoGroupId;
	@NonNull ResourceId plantId;
	@Nullable ResourceId workstationId;
	@NonNull WarehouseId warehouseId;
	@Nullable UserId plannerId;

	@Nullable ProductBOMId bomId;
	@Nullable PPRoutingId routingId;
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull Quantity qtyRequired;

	@NonNull Instant dateOrdered;
	@NonNull Instant datePromised;
	@NonNull Instant dateStartSchedule;

	@Nullable OrderLineId salesOrderLineId;
	@Nullable ShipmentScheduleId shipmentScheduleId;
	@Nullable BPartnerId customerId;
	@Nullable ProjectId projectId;

	@Nullable Boolean completeDocument;

	@Nullable
	HUPIItemProductId packingMaterialId;

	@Builder(toBuilder = true)
	PPOrderCreateRequest(
			@Nullable final PPOrderDocBaseType docBaseType,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@Nullable final ProductPlanningId productPlanningId,
			@Nullable final MaterialDispoGroupId materialDispoGroupId,
			@NonNull final ResourceId plantId,
			@Nullable final ResourceId workstationId,
			@NonNull final WarehouseId warehouseId,
			@Nullable final UserId plannerId,
			//
			@Nullable final ProductBOMId bomId,
			@Nullable final PPRoutingId routingId,
			//
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final Quantity qtyRequired,
			//
			@NonNull final Instant dateOrdered,
			@NonNull final Instant datePromised,
			@NonNull final Instant dateStartSchedule,
			//
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final ShipmentScheduleId shipmentScheduleId,
			@Nullable final BPartnerId customerId,
			@Nullable final ProjectId projectId,
			//
			@Nullable final Boolean completeDocument,
			@Nullable final HUPIItemProductId packingMaterialId)
	{
		Check.assume(!qtyRequired.isZero(), "qtyRequired shall not be zero");

		this.docBaseType = docBaseType != null ? docBaseType : PPOrderDocBaseType.MANUFACTURING_ORDER;
		this.clientAndOrgId = clientAndOrgId;
		this.productPlanningId = productPlanningId;
		this.materialDispoGroupId = materialDispoGroupId;
		this.plantId = plantId;
		this.workstationId = workstationId;
		this.warehouseId = warehouseId;
		this.plannerId = plannerId;

		this.bomId = bomId;
		this.routingId = routingId;

		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.qtyRequired = qtyRequired;

		this.dateOrdered = dateOrdered;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;

		this.salesOrderLineId = salesOrderLineId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.customerId = customerId;
		this.projectId = projectId;

		this.completeDocument = completeDocument;
		this.packingMaterialId = packingMaterialId;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class PPOrderCreateRequestBuilder
	{
	}
}
