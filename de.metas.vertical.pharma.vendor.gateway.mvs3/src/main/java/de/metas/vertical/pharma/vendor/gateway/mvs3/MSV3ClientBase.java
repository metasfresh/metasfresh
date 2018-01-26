package de.metas.vertical.pharma.vendor.gateway.mvs3;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.ws.client.core.WebServiceTemplate;

import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerbindungTestenResponse;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

public abstract class MSV3ClientBase
{
	private final MSV3ClientConfig config;
	private final WebServiceTemplate webServiceTemplate;

	@Getter
	private final ObjectFactory objectFactory;

	public MSV3ClientBase(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config)
	{
		this.config = config;
		objectFactory = new ObjectFactory();
		webServiceTemplate = connectionFactory.createWebServiceTemplate(config);
	}

	public abstract String getUrlSuffix();

	public String sendMessage(
			final JAXBElement<?> messagePayload)
	{
		final JAXBElement<?> responseElement = //
				(JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(
						config.getBaseUrl() + getUrlSuffix(),
						messagePayload);

		final Object value = responseElement.getValue();
		if (value instanceof VerbindungTestenResponse)
		{
			return "ok";
		}

		throw new AdempiereException(value.toString()).appendParametersToMessage()
				.setParameter("config", config);
	}
}
