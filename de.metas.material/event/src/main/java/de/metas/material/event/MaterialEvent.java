package de.metas.material.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.ddorder.DistributionPlanEvent;
import de.metas.material.event.forecast.ForecastEvent;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.pporder.ProductionPlanEvent;

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
		@JsonSubTypes.Type(name = MaterialDemandEvent.TYPE, value = MaterialDemandEvent.class),

		@JsonSubTypes.Type(name = DistributionPlanEvent.TYPE, value = DistributionPlanEvent.class),
		@JsonSubTypes.Type(name = ProductionPlanEvent.TYPE, value = ProductionPlanEvent.class),
		@JsonSubTypes.Type(name = DemandHandlerAuditEvent.TYPE, value = DemandHandlerAuditEvent.class),

		@JsonSubTypes.Type(name = PPOrderRequestedEvent.TYPE, value = PPOrderRequestedEvent.class),
		@JsonSubTypes.Type(name = DDOrderRequestedEvent.TYPE, value = DDOrderRequestedEvent.class),
		@JsonSubTypes.Type(name = ReceiptScheduleEvent.TYPE, value = ReceiptScheduleEvent.class),
		@JsonSubTypes.Type(name = ForecastEvent.TYPE, value = ForecastEvent.class),
		
		@JsonSubTypes.Type(name = ShipmentScheduleEvent.TYPE, value = ShipmentScheduleEvent.class),
		@JsonSubTypes.Type(name = TransactionEvent.TYPE, value = TransactionEvent.class)
})
public interface MaterialEvent
{
	EventDescr getEventDescr();
}
