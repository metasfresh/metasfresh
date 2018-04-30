package de.metas.shipper.gateway.derkurier.restapi.models;

import java.util.List;

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
public class Participant
{
	String stationFormatted;
	String station;
	String country;
	String zipCode;
	String zone;
	List<String> sector;
	List<String> dayType;
	boolean island;
	int term;
	String earliestTimeOfDelivery;
	String saturdayDeliveryUntil;
	String sundayDeliveryUntil;
	String pickupUntil;
	String partnerManager;

	@Builder
	private Participant(
			String stationFormatted,
			String station,
			String country,
			String zipCode,
			String zone,
			List<String> sector,
			List<String> dayType,
			Boolean island,
			Integer term,
			String earliestTimeOfDelivery,
			String saturdayDeliveryUntil,
			String sundayDeliveryUntil,
			String pickupUntil,
			String partnerManager)
	{
		this.stationFormatted = stationFormatted;
		this.station = Check.assumeNotEmpty(station, "Parameter station may not be empty");
		this.country = Check.assumeNotEmpty(country, "Parameter country may not be empty");
		this.zipCode = Check.assumeNotEmpty(zipCode, "Parameter zipCode may not be empty");
		this.zone = Check.assumeNotEmpty(zone, "Parameter zone may not be empty");
		this.sector = Check.assumeNotEmpty(sector, "Parameter sector may not be empty");
		this.dayType = Check.assumeNotEmpty(dayType, "Parameter dayType may not be empty");
		this.island = Check.assumeNotNull(island, "Parameter island may not be null");
		this.term = Check.assumeNotNull(term, "Parameter term may not be null");
		this.earliestTimeOfDelivery = Check.assumeNotEmpty(earliestTimeOfDelivery, "Parameter earliestTimeOfDelivery may not be empty");
		this.saturdayDeliveryUntil = Check.assumeNotEmpty(saturdayDeliveryUntil, "Parameter saturdayDeliveryUntil may not be empty");
		this.sundayDeliveryUntil = Check.assumeNotEmpty(sundayDeliveryUntil, "Parameter sundayDeliveryUntil may not be empty");
		this.pickupUntil = Check.assumeNotEmpty(pickupUntil, "Parameter pickupUntil may not be empty");
		this.partnerManager = Check.assumeNotEmpty(partnerManager, "Parameter partnerManager may not be empty");
	}
}
