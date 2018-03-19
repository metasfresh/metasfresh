package de.metas.material.event.shipmentschedule;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.DocumentLineDescriptor;
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
public class ShipmentScheduleCreatedEvent extends AbstractShipmentScheduleEvent
{
	public static final String TYPE = "ShipmentScheduleCreatedEvent";

	private final DocumentLineDescriptor documentLineDescriptor;

	@Builder
	public ShipmentScheduleCreatedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("reservedQuantity") @NonNull final BigDecimal reservedQuantity,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("documentLineDescriptor") final DocumentLineDescriptor documentLineDescriptor)
	{
		super(
				eventDescriptor,
				materialDescriptor,
				reservedQuantity,
				shipmentScheduleId);

		this.documentLineDescriptor = documentLineDescriptor;
	}

	@Override
	public BigDecimal getOrderedQuantityDelta()
	{
		return getMaterialDescriptor().getQuantity();
	}

	@Override
	public BigDecimal getReservedQuantityDelta()
	{
		return getReservedQuantity();
	}

	@Override
	public void validate()
	{
		super.validate();

		Check.errorIf(documentLineDescriptor == null, "documentLineDescriptor may not be null");
		documentLineDescriptor.validate();
	}
}
