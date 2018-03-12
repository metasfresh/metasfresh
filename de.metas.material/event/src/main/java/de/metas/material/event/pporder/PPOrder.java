package de.metas.material.event.pporder;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.model.I_S_Resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.ProductDescriptor;
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
public class PPOrder
{
	int orgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	int plantId;

	int warehouseId;

	int bPartnerId;

	int productPlanningId;

	/**
	 * Not persisted in the {@code PP_Order} data record, but
	 * when material-dispo posts {@link PPOrderRequestedEvent}, it contains a group-ID,
	 * and the respective {@link PPOrderCreatedEvent} contains the same group-ID.
	 */
	int materialDispoGroupId;

	ProductDescriptor productDescriptor;

	/**
	 * In a build-to-order scenario, this is the ID of the order line which this all is about.
	 */
	int orderLineId;

	/**
	 * Can contain the {@code PP_Order_ID} of the production order document this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderId;

	String docStatus;

	/**
	 * This is usually the respective supply candidates' date value.
	 */
	Date datePromised;

	/**
	 * This is usually the respective demand candiates' date value.
	 */
	Date dateStartSchedule;

	BigDecimal qtyRequired;

	BigDecimal qtyDelivered;

	/**
	 * Attention, might be {@code null}.
	 */
	List<PPOrderLine> lines;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrder(
			@JsonProperty("orgId") final int orgId,
			@JsonProperty("plantId") final int plantId,
			@JsonProperty("warehouseId") final int warehouseId,
			@JsonProperty("bPartnerId") final int bPartnerId,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("docStatus") @Nullable final String docStatus,
			@JsonProperty("datePromised") @NonNull final Date datePromised,
			@JsonProperty("dateStartSchedule") @NonNull final Date dateStartSchedule,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered,
			@JsonProperty("lines") @Singular final List<PPOrderLine> lines,
			@JsonProperty("materialDispoGroupId") final int materialDispoGroupId)
	{
		this.orgId = checkIdGreaterThanZero("orgId", orgId);
		this.plantId = checkIdGreaterThanZero("plantId", plantId);
		this.warehouseId = checkIdGreaterThanZero("warehouseId", warehouseId);

		this.bPartnerId = bPartnerId;
		this.productPlanningId = productPlanningId; // ok to be not set
		this.productDescriptor = productDescriptor;

		this.orderLineId = orderLineId;
		this.ppOrderId = ppOrderId;
		this.docStatus = docStatus;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;

		this.qtyRequired = qtyRequired;
		this.qtyDelivered = qtyDelivered;

		this.lines = lines;

		this.materialDispoGroupId = materialDispoGroupId;
	}
}
