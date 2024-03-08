/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.IdConstants;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

import static java.math.BigDecimal.ZERO;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrderData
{
	ClientAndOrgId clientAndOrgId;

	/**
	 * The {@link ResourceId} of the plant, as specified by the respective product planning record.
	 */
	ResourceId plantId;
	@Nullable ResourceId workstationId;

	WarehouseId warehouseId;

	BPartnerId bpartnerId;

	int productPlanningId;

	ProductDescriptor productDescriptor;

	/**
	 * In a build-to-order scenario, this is the ID of the order line which this all is about.
	 */
	int orderLineId;

	int shipmentScheduleId;

	/**
	 * This is usually the respective supply candidates' date value.
	 */
	Instant datePromised;

	MaterialDispoGroupId materialDispoGroupId;

	/**
	 * This is usually the respective demand candidates' date value.
	 */
	Instant dateStartSchedule;

	/**
	 * qty in stocking UOM
	 */
	BigDecimal qtyRequired;

	/**
	 * qty in stocking UOM
	 */
	BigDecimal qtyDelivered;

	HUPIItemProductId packingMaterialId;

	String lotForLot;


	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrderData(
			@JsonProperty("clientAndOrgId") @NonNull final ClientAndOrgId clientAndOrgId,
			@JsonProperty("plantId") @NonNull final ResourceId plantId,
			@JsonProperty("workstationId") @Nullable ResourceId workstationId,
			@JsonProperty("warehouseId") @NonNull final WarehouseId warehouseId,
			@JsonProperty("bpartnerId") @Nullable final BPartnerId bpartnerId,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("datePromised") @NonNull final Instant datePromised,
			@JsonProperty("dateStartSchedule") @NonNull final Instant dateStartSchedule,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered,
			@JsonProperty("materialDispoGroupId") final MaterialDispoGroupId materialDispoGroupId,
			@JsonProperty("packingMaterialId") @Nullable final HUPIItemProductId packingMaterialId,
			@JsonProperty("lotForLot") final String lotForLot)
	{
		this.clientAndOrgId = clientAndOrgId;
		this.plantId = plantId;
		this.workstationId = workstationId;
		this.warehouseId = warehouseId;
		this.bpartnerId = bpartnerId;
		this.productPlanningId = productPlanningId; // ok to be not set
		this.productDescriptor = productDescriptor;
		this.orderLineId = orderLineId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;
		this.qtyRequired = qtyRequired;
		this.qtyDelivered = CoalesceUtil.coalesce(qtyDelivered, ZERO);
		this.materialDispoGroupId = materialDispoGroupId;
		this.packingMaterialId = packingMaterialId;
		this.lotForLot = lotForLot;
	}

	@JsonIgnore
	public BigDecimal getQtyOpen()
	{
		return getQtyRequired().subtract(getQtyDelivered());
	}

	public int getShipmentScheduleIdAsRepoId()
	{
		return IdConstants.toRepoId(shipmentScheduleId);
	}

	public int getOrderLineIdAsRepoId()
	{
		return IdConstants.toRepoId(orderLineId);
	}
}
