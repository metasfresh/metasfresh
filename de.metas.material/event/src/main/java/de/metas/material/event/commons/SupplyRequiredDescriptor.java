package de.metas.material.event.commons;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
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

@Value // includes @AllArgsConstructor which is used by jackson when it deserializes a string
public class SupplyRequiredDescriptor
{
	@NonNull
	EventDescriptor eventDescr;

	@NonNull
	MaterialDescriptor materialDescriptor;

	int demandCandidateId;

	int shipmentScheduleId;

	int forecastLineId;

	int orderLineId;

	@JsonCreator
	@Builder
	private SupplyRequiredDescriptor(
			@JsonProperty("eventDescr") @NonNull final EventDescriptor eventDescr,
			@JsonProperty("materialDescriptor") @NonNull MaterialDescriptor materialDescriptor,
			@JsonProperty("demandCandidateId") int demandCandidateId,
			@JsonProperty("shipmentScheduleId") int shipmentScheduleId,
			@JsonProperty("forecastLineId") int forecastLineId,
			@JsonProperty("orderLineId") int orderLineId)
	{
		this.demandCandidateId = checkIdGreaterThanZero("demandCandidateId", demandCandidateId);

		this.shipmentScheduleId = shipmentScheduleId > 0 ? shipmentScheduleId : -1;
		this.forecastLineId = forecastLineId > 0 ? forecastLineId : -1;
		this.orderLineId = orderLineId > 0 ? orderLineId : -1;

		this.eventDescr = eventDescr;
		this.materialDescriptor = materialDescriptor;
	}
}
