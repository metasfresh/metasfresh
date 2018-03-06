package de.metas.vertical.pharma.vendor.gateway.msv3.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;

import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
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
@Builder
@ToString(exclude = "authPassword")
public class MSV3ClientConfig
{
	public static MSV3ClientConfig ofdataRecord(
			@NonNull final I_MSV3_Vendor_Config configDataRecord)
	{

		final URL baseUrl = toURL(configDataRecord);

		return MSV3ClientConfig.builder()
				.authPassword(configDataRecord.getPassword())
				.authUsername(configDataRecord.getUserID())
				.baseUrl(baseUrl)
				.build();
	}

	private static URL toURL(@NonNull final I_MSV3_Vendor_Config configDataRecord)
	{
		try
		{
			return new URL(configDataRecord.getMSV3_BaseUrl());
		}
		catch (MalformedURLException e)
		{
			throw new AdempiereException("The MSV3_BaseUrl value of the given MSV3_Vendor_Config can't be parsed as URL", e)
					.appendParametersToMessage()
					.setParameter("MSV3_BaseUrl", configDataRecord.getMSV3_BaseUrl())
					.setParameter("MSV3_Vendor_Config", configDataRecord);
		}
	}

	@NonNull
	URL baseUrl;
	@NonNull
	String authUsername;
	@NonNull
	String authPassword;
}
