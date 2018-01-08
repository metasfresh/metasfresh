package de.metas.material.event.transactions;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;

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

public class TransactionDeletedEvent extends AbstractTransactionEvent
{
	public static final String TYPE = "TransactionDeletedEvent";

	@JsonCreator
	@Builder
	public TransactionDeletedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("ppOrderLineId") final int ppOrderLineId,
			@JsonProperty("ddOrderId") final int ddOrderId,
			@JsonProperty("ddOrderLineId") final int ddOrderLineId,
			@JsonProperty("transactionId") final int transactionId,
			@JsonProperty("directMovementWarehouse") final boolean directMovementWarehouse)
	{
		super(eventDescriptor,
				materialDescriptor,
				shipmentScheduleId,
				ppOrderId,
				ppOrderLineId,
				ddOrderId,
				ddOrderLineId,
				transactionId,
				directMovementWarehouse);
	}

	/**
	 * @return zero.
	 */
	@Override
	public BigDecimal getQuantity()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * @return our material descriptor's <b>negated</b> quantity, i.e. the negated {@code MovementQty} if the underlying {@code M_Transaction}.
	 */
	@Override
	public BigDecimal getQuantityDelta()
	{
		return getMaterialDescriptor().getQuantity().negate();
	}
}
