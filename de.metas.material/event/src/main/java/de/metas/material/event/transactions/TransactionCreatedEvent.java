package de.metas.material.event.transactions;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class TransactionCreatedEvent implements MaterialEvent
{
	public static final String TYPE = "TransactionCreatedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@NonNull
	MaterialDescriptor materialDescriptor;

	// ids used to match the transaction to the respective shipment, ddOrder or ppOrder event (demand if qty is negative), supply if qty is positive
	// if *none of those are set* then the transaction will be recorded as "unplanned"
	int shipmentScheduleId;

	int transactionId;

	@JsonCreator
	@Builder
	public TransactionCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") @NonNull final MaterialDescriptor materialDescriptor,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("transactionId") final int transactionId)
	{
		this.transactionId = checkIdGreaterThanZero("transactionId",transactionId);

		this.eventDescriptor = eventDescriptor;

		materialDescriptor.asssertMaterialDescriptorComplete();
		this.materialDescriptor = materialDescriptor;

		this.shipmentScheduleId = shipmentScheduleId;
	}
}
