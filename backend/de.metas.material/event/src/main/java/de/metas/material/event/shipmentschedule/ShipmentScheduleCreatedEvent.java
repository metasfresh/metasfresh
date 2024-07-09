package de.metas.material.event.shipmentschedule;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ShipmentScheduleCreatedEvent extends AbstractShipmentScheduleEvent
{
	public static final String TYPE = "ShipmentScheduleCreatedEvent";

	@Builder
	public ShipmentScheduleCreatedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("shipmentScheduleDetail") @NonNull final ShipmentScheduleDetail shipmentScheduleDetail,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("documentLineDescriptor") final DocumentLineDescriptor documentLineDescriptor)
	{
		super(
				eventDescriptor,
				materialDescriptor,
				minMaxDescriptor,
				shipmentScheduleDetail,
				shipmentScheduleId,
				documentLineDescriptor);
	}

	@Override
	@NonNull
	public BigDecimal getReservedQuantityDelta()
	{
		return getShipmentScheduleDetail().getReservedQuantity();
	}

	@Override
	@NonNull
	public BigDecimal getOrderedQuantityDelta()
	{
		return getShipmentScheduleDetail().getOrderedQuantity();
	}

	@Override
	public void validate()
	{
		super.validate();

		Check.errorIf(getDocumentLineDescriptor() == null, "documentLineDescriptor may not be null");
		getDocumentLineDescriptor().validate();
	}
}
