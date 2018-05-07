package de.metas.shipper.gateway.spi.model;

import java.util.List;

import org.adempiere.util.Check;

import com.google.common.base.Splitter;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

@Value
public class PhoneNumber
{
	public static PhoneNumber fromString(@NonNull final String phoneNoAsStr)
	{
		final List<String> parts = Splitter.on("-").splitToList(phoneNoAsStr.trim());
		if (parts.size() != 3)
		{
			throw new IllegalArgumentException("Invalid PhoneNo string: " + phoneNoAsStr);
		}

		String countryCode = parts.get(0);
		if (countryCode.startsWith("+"))
		{
			countryCode = countryCode.substring(1);
		}

		String areaCode = parts.get(1);
		String phoneNumber = parts.get(2);

		return builder()
				.countryCode(countryCode)
				.areaCode(areaCode)
				.phoneNumber(phoneNumber)
				.build();
	}

	String countryCode;
	String areaCode;
	String phoneNumber;

	@Builder
	private PhoneNumber(
			final String countryCode,
			final String areaCode,
			final String phoneNumber)
	{
		Check.assumeNotEmpty(countryCode, "countryCode is not empty");
		Check.assumeNotEmpty(areaCode, "areaCode is not empty");
		Check.assumeNotEmpty(phoneNumber, "phoneNumber is not empty");

		this.countryCode = countryCode.trim();
		this.areaCode = areaCode.trim();
		this.phoneNumber = phoneNumber.trim();
	}

	public String getAsString()
	{
		return "+" + countryCode + "-" + areaCode + "-" + phoneNumber;
	}
}
