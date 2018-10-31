package de.metas.vertical.pharma.vendor.gateway.msv3.testconnection;

import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3Client;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import lombok.Builder;
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

public class MSV3TestConnectionClientImpl implements MSV3TestConnectionClient
{
	private static final String URL_SUFFIX_TEST_CONNECTION = "/verbindungTesten";

	private final MSV3Client client;
	private final TestConnectionJAXBConverters jaxbConverters;

	@Builder
	private MSV3TestConnectionClientImpl(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final TestConnectionJAXBConverters jaxbConverters)
	{
		client = MSV3Client.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.urlPrefix(URL_SUFFIX_TEST_CONNECTION)
				.faultInfoExtractor(jaxbConverters::extractFaultInfoOrNull)
				.build();

		this.jaxbConverters = jaxbConverters;

	}

	@Override
	public String testConnection()
	{
		client.sendAndReceive(
				jaxbConverters.encodeRequest(client.getClientSoftwareId()),
				jaxbConverters.getResponseClass());

		return "ok";
	}

}
