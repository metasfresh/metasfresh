package de.metas.material.event.commons;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

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

	int demandCandidateId;

	int shipmentScheduleId;

	int forecastId;

	int forecastLineId;

	int orderId;

	int orderLineId;

	int subscriptionProgressId;

	@JsonCreator
	@Builder
	private SupplyRequiredDescriptor(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") MaterialDescriptor materialDescriptor,
			@JsonProperty("demandCandidateId") int demandCandidateId,
			@JsonProperty("shipmentScheduleId") int shipmentScheduleId,
			@JsonProperty("forecastId") int forecastId,
			@JsonProperty("forecastLineId") int forecastLineId,
			@JsonProperty("orderId") int orderId,
			@JsonProperty("orderLineId") int orderLineId,
			@JsonProperty("subscriptionProgressId") int subscriptionProgressId)
	{
		this.demandCandidateId = demandCandidateId;

		this.shipmentScheduleId = shipmentScheduleId > 0 ? shipmentScheduleId : -1;

		this.forecastId = forecastId > 0 ? forecastId : -1;
		this.forecastLineId = forecastLineId > 0 ? forecastLineId : -1;

		this.orderId = orderId > 0 ? orderId : -1;
		this.orderLineId = orderLineId > 0 ? orderLineId : -1;

		this.subscriptionProgressId = subscriptionProgressId;

		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
	}

	public void validate()
	{
		checkIdGreaterThanZero("demandCandidateId", demandCandidateId);
		Check.errorIf(eventDescriptor == null, "eventDescriptor is null; this={}", this);
		Check.errorIf(materialDescriptor == null, "materialDescriptor is null; this={}", this);
	}
}
