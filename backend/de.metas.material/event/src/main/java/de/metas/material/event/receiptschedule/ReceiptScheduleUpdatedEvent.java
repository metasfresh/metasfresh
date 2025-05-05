package de.metas.material.event.receiptschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ReceiptScheduleUpdatedEvent extends AbstractReceiptScheduleEvent
{
	public static ReceiptScheduleUpdatedEvent cast(@NonNull final AbstractReceiptScheduleEvent event)
	{
		return (ReceiptScheduleUpdatedEvent)event;
	}

	public static final String TYPE = "ReceiptScheduleUpdatedEvent";

	private final BigDecimal orderedQuantityDelta;

	private final BigDecimal reservedQuantityDelta;

	@JsonCreator
	@Builder
	public ReceiptScheduleUpdatedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("oldReceiptScheduleData") final OldReceiptScheduleData oldReceiptScheduleData,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("orderedQuantityDelta") final BigDecimal orderedQuantityDelta,
			@JsonProperty("reservedQuantity") final BigDecimal reservedQuantity,
			@JsonProperty("reservedQuantityDelta") final BigDecimal reservedQuantityDelta,
			@JsonProperty("receiptScheduleId") final int receiptScheduleId)
	{
		super(eventDescriptor,
				materialDescriptor,
				oldReceiptScheduleData,
				minMaxDescriptor,
				reservedQuantity,
				receiptScheduleId);

		this.orderedQuantityDelta = orderedQuantityDelta;
		this.reservedQuantityDelta = reservedQuantityDelta;
	}

	@Override
	public ReceiptScheduleUpdatedEvent validate()
	{
		super.validate();
		Check.errorIf(reservedQuantityDelta == null, "reservedQuantityDelta may not be null");
		Check.errorIf(orderedQuantityDelta == null, "orderedQuantityDelta may not be null");

		return this;
	}
}
