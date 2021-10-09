package de.metas.material.event.pporder;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_S_Resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrder
{
	ClientAndOrgId clientAndOrgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	ResourceId plantId;

	WarehouseId warehouseId;

	BPartnerId bpartnerId;

	int productPlanningId;

	/**
	 * Not persisted in the {@code PP_Order} data record.
	 * When the material-dispo posts a {@link PPOrderRequestedEvent}, it contains a group-ID,
	 * and the respective {@link PPOrderCreatedEvent} contains the same group-ID.
	 */
	MaterialDispoGroupId materialDispoGroupId;

	ProductDescriptor productDescriptor;

	/**
	 * In a build-to-order scenario, this is the ID of the order line which this all is about.
	 */
	int orderLineId;

	/**
	 * Can contain the {@code PP_Order_ID} of the production order document this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderId;

	DocStatus docStatus;

	/**
	 * This is usually the respective supply candidates' date value.
	 */
	Instant datePromised;

	/**
	 * This is usually the respective demand candidates' date value.
	 */
	Instant dateStartSchedule;

	/** qty in stocking UOM */
	BigDecimal qtyRequired;

	/** qty in stocking UOM */
	BigDecimal qtyDelivered;

	/**
	 * Attention, might be {@code null}.
	 */
	List<PPOrderLine> lines;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrder(
			@JsonProperty("clientAndOrgId") @NonNull final ClientAndOrgId clientAndOrgId,
			@JsonProperty("plantId") @NonNull final ResourceId plantId,
			@JsonProperty("warehouseId") @NonNull final WarehouseId warehouseId,
			@JsonProperty("bpartnerId") @Nullable final BPartnerId bpartnerId,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("docStatus") @Nullable final DocStatus docStatus,
			@JsonProperty("datePromised") @NonNull final Instant datePromised,
			@JsonProperty("dateStartSchedule") @NonNull final Instant dateStartSchedule,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered,
			@JsonProperty("lines") @Singular final List<PPOrderLine> lines,
			@JsonProperty("materialDispoGroupId") final MaterialDispoGroupId materialDispoGroupId)
	{
		this.clientAndOrgId = clientAndOrgId;
		this.plantId = plantId;
		this.warehouseId = warehouseId;

		this.bpartnerId = bpartnerId;
		this.productPlanningId = productPlanningId; // ok to be not set
		this.productDescriptor = productDescriptor;

		this.orderLineId = orderLineId;
		this.ppOrderId = ppOrderId;
		this.docStatus = docStatus;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;

		this.qtyRequired = qtyRequired;
		this.qtyDelivered = CoalesceUtil.coalesce(qtyDelivered, ZERO);

		this.lines = lines;

		this.materialDispoGroupId = materialDispoGroupId;
	}

	public BigDecimal getQtyOpen()
	{
		return getQtyRequired().subtract(getQtyDelivered());
	}
}
