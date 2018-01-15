package de.metas.material.event.transactions;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractTransactionEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	// ids used to match the transaction to the respective shipmentSchedule
	// if *none of those are set* then the transaction will be recorded as "unplanned"
	private final int shipmentScheduleId;

	private final int transactionId;

	private final boolean directMovementWarehouse;

	private final int ppOrderId;

	private final int ppOrderLineId;

	private int ddOrderId;

	private int ddOrderLineId;

	public AbstractTransactionEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor,
			final int shipmentScheduleId,
			final int ppOrderId,
			final int ppOrderLineId,
			final int ddOrderId,
			final int ddOrderLineId,
			final int transactionId,
			final boolean directMovementWarehouse)
	{
		this.transactionId = checkIdGreaterThanZero("transactionId", transactionId);

		this.eventDescriptor = eventDescriptor;

		materialDescriptor.asssertMaterialDescriptorComplete();
		this.materialDescriptor = materialDescriptor;

		this.shipmentScheduleId = shipmentScheduleId;

		this.ddOrderLineId = ddOrderLineId;
		this.ddOrderId = ddOrderId;

		this.ppOrderLineId = ppOrderLineId;
		this.ppOrderId = ppOrderId;

		this.directMovementWarehouse = directMovementWarehouse;
	}

	public abstract BigDecimal getQuantity();
	public abstract BigDecimal getQuantityDelta();
}
