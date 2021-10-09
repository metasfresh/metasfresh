package de.metas.material.event.receiptschedule;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ReceiptScheduleCreatedEvent extends AbstractReceiptScheduleEvent
{
	public static ReceiptScheduleCreatedEvent cast(@NonNull final AbstractReceiptScheduleEvent event)
	{
		return (ReceiptScheduleCreatedEvent)event;
	}

	public static final String TYPE = "ReceiptScheduleCreatedEvent";

	private final OrderLineDescriptor orderLineDescriptor;

	private final int purchaseCandidateRepoId;

	private final int vendorId;

	@Builder
	@JsonCreator
	public ReceiptScheduleCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("orderLineDescriptor") @NonNull final OrderLineDescriptor orderLineDescriptor,
			@JsonProperty("purchaseCandidateRepoId") final int purchaseCandidateRepoId,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("reservedQuantity") final BigDecimal reservedQuantity,
			@JsonProperty("receiptScheduleId") final int receiptScheduleId,
			@JsonProperty("vendorId") final int vendorId
			)
	{
		super(eventDescriptor,
				materialDescriptor,
				null, // no replenish descriptor needed because this event can't trigger a new supply-request
				reservedQuantity,
				receiptScheduleId);

		this.purchaseCandidateRepoId = purchaseCandidateRepoId;
		this.orderLineDescriptor = orderLineDescriptor;
		this.vendorId = vendorId;
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
	public ReceiptScheduleCreatedEvent validate()
	{
		super.validate();
		orderLineDescriptor.validate();
		return this;
	}

}
