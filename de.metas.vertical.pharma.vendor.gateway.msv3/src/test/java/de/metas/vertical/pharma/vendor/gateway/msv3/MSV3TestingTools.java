package de.metas.vertical.pharma.vendor.gateway.msv3;

import java.net.MalformedURLException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.Version;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public final class MSV3TestingTools
{
	private MSV3TestingTools()
	{
	};

	public static MSV3ClientConfig createMSV3ClientConfig(@NonNull final Version version)
	{
		return MSV3ClientConfig.builder()
				.baseUrl(parseURL("http://localhost:8089/msv3/v2.0"))
				.authUsername("PLA\\apotheke1")
				.authPassword("passwort")
				.bpartnerId(BPartnerId.of(999))
				.version(version)
				.clientSoftwareId(ClientSoftwareId.of("junit-test"))
				.build();
	}

	private static final URL parseURL(final String str)
	{
		try
		{
			return new URL(str);
		}
		catch (MalformedURLException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
