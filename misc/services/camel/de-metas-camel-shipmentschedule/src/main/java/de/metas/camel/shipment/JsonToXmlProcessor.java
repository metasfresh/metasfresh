/*
 * #%L
 * de-metas-camel-shipmentschedule
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipment;

import de.metas.common .shipmentschedule.JsonResponseShipmentScheduleList;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonToXmlProcessor implements Processor
{
	private final Log log = LogFactory.getLog(JsonToXmlProcessor.class);

	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final JsonResponseShipmentScheduleList scheduleList = exchange.getIn().getBody(JsonResponseShipmentScheduleList.class);
		log.info("process method called; scheduleList=" + scheduleList);
	}
}
