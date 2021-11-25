package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.util.Check;
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

/**
 * Fired when a manufacturing order was created:
 * <ul>
 * <li>manually by user
 * <li>as a response to an {@link PPOrderRequestedEvent}
 * </ul>
 */
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrderCreatedEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;
	private final PPOrder ppOrder;

	public static final String TYPE = "PPOrderCreatedEvent";

	@JsonCreator
	@Builder
	public PPOrderCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrder") final @NonNull PPOrder ppOrder)
	{
		this.eventDescriptor = eventDescriptor;
		this.ppOrder = ppOrder;
	}

	public void validate()
	{
		final PPOrder ppOrder = getPpOrder();
		final int ppOrderId = ppOrder.getPpOrderId();
		Check.errorIf(ppOrderId <= 0, "The given ppOrderCreatedEvent event has a ppOrder with ppOrderId={}", ppOrderId);

		ppOrder.getLines().forEach(this::validateLine);
	}

	private void validateLine(final PPOrderLine ppOrderLine)
	{
		final int ppOrderLineId = ppOrderLine.getPpOrderLineId();
		Check.errorIf(ppOrderLineId <= 0,
				"The given ppOrderCreatedEvent event has a ppOrderLine with ppOrderLineId={}; ppOrderLine={}",
				ppOrderLineId, ppOrderLine);
	}
}
