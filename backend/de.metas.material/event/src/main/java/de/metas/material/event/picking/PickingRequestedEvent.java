package de.metas.material.event.picking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Set;

import static de.metas.material.event.MaterialEventConstants.M_SHIPMENT_SCHEDULE_TABLE_NAME;
import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class PickingRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "PickingRequestedEvent";

	EventDescriptor eventDescriptor;

	int shipmentScheduleId;

	int pickingSlotId;

	Set<Integer> topLevelHuIdsToPick;

	@Builder
	@JsonCreator
	public PickingRequestedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("shipmentScheduleId") final int shipmentScheduleId,
			@JsonProperty("pickingSlotId") final int pickingSlotId,
			@JsonProperty("topLevelHuIdsToPick") final Set<Integer> topLevelHuIdsToPick)
	{
		this.eventDescriptor = eventDescriptor;
		this.shipmentScheduleId = shipmentScheduleId;
		this.pickingSlotId = pickingSlotId;
		this.topLevelHuIdsToPick = topLevelHuIdsToPick;
	}

	public void assertValid()
	{
		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");

		Check.errorIf(topLevelHuIdsToPick == null, "topLevelHuIdsToPick may not be null");
		Check.errorIf(topLevelHuIdsToPick.isEmpty(), "topLevelHuIdsToPick may not be empty");

		checkIdGreaterThanZero("shipmentScheduleId", shipmentScheduleId);
	}

	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(M_SHIPMENT_SCHEDULE_TABLE_NAME, shipmentScheduleId);
	}

	@Override
	public String getEventName() {return TYPE;}
}
