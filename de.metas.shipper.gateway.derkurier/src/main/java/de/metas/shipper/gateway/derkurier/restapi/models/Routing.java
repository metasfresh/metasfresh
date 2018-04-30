package de.metas.shipper.gateway.derkurier.restapi.models;

import java.time.ZonedDateTime;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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
public class Routing
{
	ZonedDateTime sendDate;
	ZonedDateTime deliveryDate;
	Participant sender;
	Participant consignee;
	String viaHubs;
	String labelContent;
	String message;

	@Builder
	private Routing(
			ZonedDateTime sendDate,
			ZonedDateTime deliveryDate,
			Participant sender,
			Participant consignee,
			String viaHubs,
			String labelContent,
			String message)
	{
		this.sendDate = Check.assumeNotNull(sendDate, "Parameter sendDate may not be null");
		this.deliveryDate = deliveryDate;

		this.sender = sender;
		this.consignee = consignee;

		this.viaHubs = Check.assumeNotEmpty(viaHubs, "Parameter viaHubs may not be empty");
		this.labelContent = Check.assumeNotEmpty(labelContent, "Parameter labelContent may not be empty");
		this.message = Check.assumeNotEmpty(message, "Parameter message may not be empty");
	}
}
