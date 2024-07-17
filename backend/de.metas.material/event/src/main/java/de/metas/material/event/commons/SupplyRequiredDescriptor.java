package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

/*
 * #%L
 * metasfresh-material-event
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
public class SupplyRequiredDescriptor
{
	EventDescriptor eventDescriptor;
	MaterialDescriptor materialDescriptor;
	/**
	 * the MD_Candidate_ID of the record which the required supply is about.
	 */
	int demandCandidateId;
	/**
	 * The MD_Candidate_ID of the still "unspecific" supply-record that was already optimistically created.
	 * It shall be updated by the response to this descriptor.
	 */
	int supplyCandidateId;
	int shipmentScheduleId;
	int forecastId;
	int forecastLineId;
	int orderId;
	int orderLineId;
	int subscriptionProgressId;
	boolean simulated;
	BigDecimal fullDemandQty;

	@JsonCreator
	@Builder(toBuilder = true)
	private SupplyRequiredDescriptor(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") @NonNull final MaterialDescriptor materialDescriptor,
			@JsonProperty("demandCandidateId") final int demandCandidateId,
			@JsonProperty("supplyCandidateId") final int supplyCandidateId,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("forecastId") final int forecastId,
			@JsonProperty("forecastLineId") final int forecastLineId,
			@JsonProperty("orderId") final int orderId,
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("subscriptionProgressId") final int subscriptionProgressId,
			@JsonProperty("simulated") final boolean simulated,
			@JsonProperty("fullDemandQty") final BigDecimal fullDemandQty)
	{
		this.demandCandidateId = checkIdGreaterThanZero("demandCandidateId", demandCandidateId);
		this.supplyCandidateId = supplyCandidateId;
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;

		this.shipmentScheduleId = shipmentScheduleId > 0 ? shipmentScheduleId : -1;

		this.forecastId = forecastId > 0 ? forecastId : -1;
		this.forecastLineId = forecastLineId > 0 ? forecastLineId : -1;

		this.orderId = orderId > 0 ? orderId : -1;
		this.orderLineId = orderLineId > 0 ? orderLineId : -1;

		this.subscriptionProgressId = subscriptionProgressId > 0 ? subscriptionProgressId : -1;
		this.simulated = simulated;
		this.fullDemandQty = fullDemandQty;
	}

	@NonNull
	@JsonIgnore
	public OrgId getOrgId() {return getEventDescriptor().getOrgId();}

	@NonNull
	@JsonIgnore
	public EventDescriptor newEventDescriptor() {return getEventDescriptor().withNewEventId();}

	@NonNull
	@JsonIgnore
	public Instant getDemandDate() {return getMaterialDescriptor().getDate();}

	@JsonIgnore
	public int getProductId() {return getMaterialDescriptor().getProductId();}

	@JsonIgnore
	public BigDecimal getQtyToSupplyBD() {return getMaterialDescriptor().getQuantity();}

	@Nullable
	@JsonIgnore
	public BPartnerId getCustomerId() {return getMaterialDescriptor().getCustomerId();}

	@JsonIgnore
	public WarehouseId getWarehouseId() {return getMaterialDescriptor().getWarehouseId();}

	@JsonIgnore
	public int getAttributeSetInstanceId() {return getMaterialDescriptor().getAttributeSetInstanceId();}

	@JsonIgnore
	public AttributesKey getStorageAttributesKey() {return getMaterialDescriptor().getStorageAttributesKey();}
}
