package de.metas.shipper.gateway.derkurier.misc;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
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
public class DerKurierShipperConfig
{
	String restApiBaseUrl;

	String customerNumber;

	ParcelNumberGenerator parcelNumberGenerator;

	@Builder
	public DerKurierShipperConfig(
			@NonNull final String restApiBaseUrl,
			@NonNull final String customerNumber,
			final int parcelNumberAdSequenceId)
	{
		this.restApiBaseUrl = Check.assumeNotEmpty(restApiBaseUrl, "Parameter restApiBaseUrl is not empty");
		this.customerNumber = Check.assumeNotEmpty(customerNumber, "Parameter customerNumber is not empty");

		Check.assume(parcelNumberAdSequenceId > 0, "Parameter parcelNumberAdSequenceId is > 0");
		this.parcelNumberGenerator = new ParcelNumberGenerator(parcelNumberAdSequenceId);
	}
}
