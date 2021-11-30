/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderCreateRequest;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@JsonDeserialize(builder = PPOrderCreateRequest.PPOrderCreateRequestBuilder.class)
public class PPOrderCandidateCreateRequest
{
	ClientAndOrgId clientAndOrgId;
	ProductPlanningId productPlanningId;
	MaterialDispoGroupId materialDispoGroupId;
	ResourceId plantId;
	WarehouseId warehouseId;
	ProductId productId;
	AttributeSetInstanceId attributeSetInstanceId;
	Quantity qtyRequired;
	Instant datePromised;
	Instant dateStartSchedule;
	OrderLineId salesOrderLineId;

	@Builder
	public PPOrderCandidateCreateRequest(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@Nullable final ProductPlanningId productPlanningId,
			@Nullable final MaterialDispoGroupId materialDispoGroupId,
			@NonNull final ResourceId plantId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final Quantity qtyRequired,
			@NonNull final Instant datePromised,
			@NonNull final Instant dateStartSchedule,
			@Nullable final OrderLineId salesOrderLineId)
	{
		Check.assume(!qtyRequired.isZero(), "qtyRequired shall not be zero");

		this.clientAndOrgId = clientAndOrgId;
		this.productPlanningId = productPlanningId;
		this.materialDispoGroupId = materialDispoGroupId;
		this.plantId = plantId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.qtyRequired = qtyRequired;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;
		this.salesOrderLineId = salesOrderLineId;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class PPOrderCreateRequestBuilder
	{
	}
}