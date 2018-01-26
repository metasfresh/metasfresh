package de.metas.vertical.pharma.vendor.gateway.mvs3.testconnection;

import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientBase;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerbindungTesten;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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

public class MSV3TestConnectionClient extends MSV3ClientBase
{
	private static final String URL_SUFFIX_TEST_CONNECTION = "/verbindungTesten";

	public MSV3TestConnectionClient(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config)
	{
		super(connectionFactory, config);
	}

	public String testConnection()
	{
		final ObjectFactory objectFactory = getObjectFactory();

		final VerbindungTesten verbindungTesten = objectFactory.createVerbindungTesten();
		verbindungTesten.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());

		return sendMessage(objectFactory.createVerbindungTesten(verbindungTesten));
	}

	@Override
	public String getUrlSuffix()
	{
		return URL_SUFFIX_TEST_CONNECTION;
	}
}
