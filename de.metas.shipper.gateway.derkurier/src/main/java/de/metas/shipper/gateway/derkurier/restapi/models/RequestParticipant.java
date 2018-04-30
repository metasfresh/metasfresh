package de.metas.shipper.gateway.derkurier.restapi.models;

import org.adempiere.util.Check;

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
public class RequestParticipant
{
	String country;
	String zip;
	String timeFrom;
	String timeTo;
	String desiredStation;

	private RequestParticipant(
			String country,
			String zip,
			String timeFrom,
			String timeTo,
			String desiredStation)
	{
		this.country = Check.assumeNotEmpty(country, "Parameter country may not be empty");
		this.zip = Check.assumeNotEmpty(zip, "Parameter zip may not be empty");
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.desiredStation = desiredStation;
	}

}
