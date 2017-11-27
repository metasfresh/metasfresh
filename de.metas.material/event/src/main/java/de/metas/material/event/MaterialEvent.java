package de.metas.material.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.ddorder.DDOrderAdvisedOrCreatedEvent;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;

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

/**
 * These are the high-level event pojos. We serialize and deserialize them and let them ride inside {@link de.metas.event.Event} instances.
 * <p>
 * Thanks to <a href="https://spring.io/blog/2016/11/08/cqrs-and-event-sourcing-with-jakub-pilimon">https://spring.io/blog/2016/11/08/cqrs-and-event-sourcing-with-jakub-pilimon</a> for the samples and hints on how to use jackson.
 * Also thanks to <a href="https://reinhard.codes/2015/09/16/lomboks-builder-annotation-and-inheritance">https://reinhard.codes/2015/09/16/lomboks-builder-annotation-and-inheritance/</a> for the hint about using builder with inheritance, but I didn't get it to fly when i also had to provide allargsconstructors to the concrete sub classes
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = SupplyRequiredEvent.TYPE, value = SupplyRequiredEvent.class),

		@JsonSubTypes.Type(name = DDOrderAdvisedOrCreatedEvent.TYPE, value = DDOrderAdvisedOrCreatedEvent.class),
		@JsonSubTypes.Type(name = PPOrderAdvisedOrCreatedEvent.TYPE, value = PPOrderAdvisedOrCreatedEvent.class),
		@JsonSubTypes.Type(name = DemandHandlerAuditEvent.TYPE, value = DemandHandlerAuditEvent.class),

		@JsonSubTypes.Type(name = PPOrderRequestedEvent.TYPE, value = PPOrderRequestedEvent.class),
		@JsonSubTypes.Type(name = DDOrderRequestedEvent.TYPE, value = DDOrderRequestedEvent.class),
		@JsonSubTypes.Type(name = ForecastCreatedEvent.TYPE, value = ForecastCreatedEvent.class),
		
		@JsonSubTypes.Type(name = ShipmentScheduleCreatedEvent.TYPE, value = ShipmentScheduleCreatedEvent.class),
		@JsonSubTypes.Type(name = TransactionCreatedEvent.TYPE, value = TransactionCreatedEvent.class)
})
public interface MaterialEvent
{
	EventDescriptor getEventDescriptor();
}
