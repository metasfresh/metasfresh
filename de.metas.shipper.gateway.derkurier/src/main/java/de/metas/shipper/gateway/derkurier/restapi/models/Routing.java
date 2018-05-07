package de.metas.shipper.gateway.derkurier.restapi.models;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.DATE_FORMAT;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	LocalDate sendDate;
	LocalDate deliveryDate;
	Participant sender;
	Participant consignee;
	List<String> viaHubs;
	String labelContent;
	String message;

	@Builder
	@JsonCreator
	public Routing(
			@JsonProperty("sendDate") @JsonFormat(shape = Shape.STRING, pattern = DATE_FORMAT) LocalDate sendDate,
			@JsonProperty("deliveryDate") @JsonFormat(shape = Shape.STRING, pattern = DATE_FORMAT) LocalDate deliveryDate,
			@JsonProperty("sender") Participant sender,
			@JsonProperty("consignee") Participant consignee,
			@JsonProperty("viaHubs") List<String> viaHubs,
			@JsonProperty("labelContent") String labelContent,
			@JsonProperty("message") String message)
	{
		this.sendDate = Check.assumeNotNull(sendDate, "Parameter sendDate may not be null");
		this.deliveryDate = deliveryDate;

		this.sender = sender;
		this.consignee = consignee;

		this.viaHubs = Check.assumeNotNull(viaHubs, "Parameter viaHubs may not be null"); // note: may be empty
		this.labelContent = Check.assumeNotNull(labelContent, "Parameter labelContent may not be empty"); // note: may be empty
		this.message = Check.assumeNotEmpty(message, "Parameter message may not be empty");
	}
}
