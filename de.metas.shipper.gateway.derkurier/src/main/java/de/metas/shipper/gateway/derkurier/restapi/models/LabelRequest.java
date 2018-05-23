package de.metas.shipper.gateway.derkurier.restapi.models;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class LabelRequest
{
	String parcelNumber;
	String orderNumber;
	String clientStationNo;

	LabelParticipant consignor;

	LabelParticipant consignee;

	Appointment appointment;

	int parcelAmount;
	int parcelPosition;
	BigDecimal weight;
	List<String> services;

	@Builder
	private LabelRequest(
			String parcelNumber,
			String orderNumber,
			String clientStationNo,
			LabelParticipant consignor,
			LabelParticipant consignee,
			Appointment appointment,
			int parcelAmount,
			int parcelPosition,
			BigDecimal weight,
			List<String> services)
	{
		this.parcelNumber = Check.assumeNotEmpty(parcelNumber, "Parameter parcelNumber may not be empty");
		this.orderNumber = Check.assumeNotEmpty(orderNumber, "Parameter orderNumber may not be empty");
		this.clientStationNo = Check.assumeNotEmpty(clientStationNo, "Parameter clientStationNo may not be empty");

		this.consignor = Check.assumeNotNull(consignor, "Parameter consignor may not be null");
		this.consignee = Check.assumeNotNull(consignee, "Parameter consignee may not be null");
		this.appointment = Check.assumeNotNull(appointment, "Parameter appointment may not be null");

		this.parcelAmount = parcelAmount;
		this.parcelPosition = parcelPosition;
		this.weight = Check.assumeNotNull(weight, "Parameter weight may not be null");
		this.services = services;
	}


}
