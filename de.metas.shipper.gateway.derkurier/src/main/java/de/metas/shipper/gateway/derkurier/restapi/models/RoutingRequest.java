package de.metas.shipper.gateway.derkurier.restapi.models;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.DATE_FORMAT;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.adempiere.util.Check;

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
public class RoutingRequest
{
	@JsonProperty("sendDate")
	@JsonFormat(shape = Shape.STRING, pattern = DATE_FORMAT)
	LocalDate sendDate;

	@JsonProperty("desiredDeliveryDate")
	@JsonFormat(shape = Shape.STRING, pattern = DATE_FORMAT)
	LocalDate desiredDeliveryDate;

	int services;
	BigDecimal weight;
	RequestParticipant sender;
	RequestParticipant consignee;

	@Builder
	private RoutingRequest(
			LocalDate sendDate,
			LocalDate desiredDeliveryDate,
			int services,
			BigDecimal weight,
			RequestParticipant sender,
			RequestParticipant consignee)
	{
		Check.assumeNotNull(sendDate, "Parameter sendDate may not be null");
		Check.errorIf(sender == null && consignee == null, "At least one of the given sender and consignee parameters has to be not-null");

		this.sendDate = sendDate;
		this.desiredDeliveryDate = desiredDeliveryDate;

		this.services = services;
		this.weight = weight;
		this.sender = sender;
		this.consignee = consignee;
	}

	public String toLocalDateOrNull(ZonedDateTime desiredDeliveryDate)
	{
		return desiredDeliveryDate == null ? null : desiredDeliveryDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

}
