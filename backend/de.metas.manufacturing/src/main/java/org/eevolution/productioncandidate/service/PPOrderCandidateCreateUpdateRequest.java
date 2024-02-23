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

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
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
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Jacksonized
@Builder
public class PPOrderCandidateCreateUpdateRequest
{
	@Nullable PPOrderCandidateId ppOrderCandidateId;
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable ProductPlanningId productPlanningId;
	@Nullable MaterialDispoGroupId materialDispoGroupId;
	@NonNull ResourceId plantId;
	@NonNull WarehouseId warehouseId;
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull Quantity qtyRequired;
	@NonNull Instant datePromised;
	@NonNull Instant dateStartSchedule;
	@Nullable OrderLineId salesOrderLineId;
	@Nullable ShipmentScheduleId shipmentScheduleId;
	boolean simulated;
	boolean isMaturing;
	@Nullable String traceId;
	@Nullable HUPIItemProductId packingMaterialId;
	@Nullable MaturingConfigLineId maturingConfigLineId;
	@Nullable MaturingConfigId maturingConfigId;
	@Nullable HuId issueHuId;

	public PPOrderCandidateCreateUpdateRequest(
			@Nullable final PPOrderCandidateId ppOrderCandidateId,
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
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final ShipmentScheduleId shipmentScheduleId,
			final boolean simulated,
			@Nullable final Boolean isMaturing,
			@Nullable final String traceId,
			@Nullable final HUPIItemProductId packingMaterialId,
			@Nullable final MaturingConfigLineId maturingConfigLineId,
			@Nullable final MaturingConfigId maturingConfigId,
			@Nullable final HuId issueHuId)
	{

		Check.assume(!qtyRequired.isZero(), "qtyRequired shall not be zero");
		final boolean isMaturingActual = isMaturing != null && isMaturing;
		if (isMaturingActual)
		{
			Check.assume(maturingConfigId != null, "maturing configuration is mandatory");
			Check.assume(maturingConfigLineId != null, "maturing configuration line is mandatory");
			Check.assume(issueHuId != null, "issueHUId is mandatory");

		}
		this.ppOrderCandidateId = ppOrderCandidateId;
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
		this.shipmentScheduleId = shipmentScheduleId;
		this.simulated = simulated;
		this.isMaturing = isMaturingActual;
		this.traceId = traceId;
		this.packingMaterialId = packingMaterialId;
		this.maturingConfigLineId = maturingConfigLineId;
		this.maturingConfigId = maturingConfigId;
		this.issueHuId = issueHuId;
	}
}