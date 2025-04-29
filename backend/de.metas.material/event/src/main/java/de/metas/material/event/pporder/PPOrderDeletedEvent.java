package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Order;

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

@Value
@Builder
public class PPOrderDeletedEvent implements MaterialEvent
{
	public static final String TYPE = "PPOrderDeletedEvent";

	EventDescriptor eventDescriptor;

	@NonNull PPOrder ppOrder;

	@JsonCreator
	@Builder
	public PPOrderDeletedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@NonNull @JsonProperty("ppOrder") final PPOrder ppOrder)
	{
		this.eventDescriptor = eventDescriptor;
		this.ppOrder = ppOrder;
	}

	@NonNull
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(I_PP_Order.Table_Name, ppOrder.getPpOrderId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
