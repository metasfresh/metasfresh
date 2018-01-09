package de.metas.material.event.receiptschedule;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
public class ReceiptScheduleCreatedEvent extends AbstractReceiptScheduleEvent
{
	public static final String TYPE = "ReceiptScheduleCreatedEvent";

	private final OrderLineDescriptor orderLineDescriptor;

	@Builder
	@JsonCreator
	public ReceiptScheduleCreatedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("orderLineDescriptor") final OrderLineDescriptor orderLineDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("reservedQuantity") final BigDecimal reservedQuantity,
			@JsonProperty("receiptScheduleId") int receiptScheduleId)
	{
		super(eventDescriptor,
				materialDescriptor,
				reservedQuantity,
				receiptScheduleId);

		this.orderLineDescriptor = orderLineDescriptor;
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
		Check.errorIf(orderLineDescriptor == null, "orderLineDescriptor may not be null");
		orderLineDescriptor.validate();
	}

}
