package de.metas.material.event.shipmentschedule;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class ShipmentScheduleUpdatedEvent extends AbstractShipmentScheduleEvent
{
	public static final String TYPE = "ShipmentScheduleUpdatedEvent";

	private final BigDecimal orderedQuantityDelta;

	private final BigDecimal reservedQuantityDelta;

	@JsonCreator
	@Builder
	public ShipmentScheduleUpdatedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("orderedQuantityDelta") @NonNull final BigDecimal orderedQuantityDelta,
			@JsonProperty("reservedQuantity") final BigDecimal reservedQuantity,
			@JsonProperty("reservedQuantityDelta") @NonNull final BigDecimal reservedQuantityDelta,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId)
	{
		super(eventDescriptor,
				materialDescriptor,
				reservedQuantity,
				shipmentScheduleId);

		this.orderedQuantityDelta = orderedQuantityDelta;
		this.reservedQuantityDelta = reservedQuantityDelta;
	}
}
