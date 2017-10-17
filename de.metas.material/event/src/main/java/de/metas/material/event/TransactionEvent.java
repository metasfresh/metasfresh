package de.metas.material.event;

import lombok.Builder;
import lombok.Builder.Default;
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

@Value // implies @AllArgsConstructor that is used by jackson when it deserializes a string
@Builder // used by devs to make sure they know with parameter-value goes into which property
public class TransactionEvent implements MaterialEvent
{
	public static final String TYPE = "TransactionEvent";

	@NonNull
	EventDescr eventDescr;

	@NonNull
	MaterialDescriptor materialDescr;

	// ids used to match the transaction to the respective shipment, ddOrder or ppOrder event (demand if qty is negative), supply if qty is positive
	// if *none of those are set* then the transaction will be recorded as "unplanned"
	@Default
	int shipmentScheduleId = -1;
	
	@Default
	int ddOrderLineId = -1;
	
	@Default
	int ppOrderId = -1;
	
	@Default
	int ppOrderBomLineId = -1;

	boolean transactionDeleted;
}
