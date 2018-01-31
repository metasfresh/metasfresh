package de.metas.vertical.pharma.vendor.gateway.mvs3;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.ws.client.core.WebServiceTemplate;

import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.mvs3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class MSV3ClientBase
{
	private final WebServiceTemplate webServiceTemplate;
	@Getter
	private final MSV3ClientConfig config;

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

	/**
	 * @param expectedResponseClass if the response is not an instance of this class, the method throws an exception.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T sendAndReceive(
			@NonNull final JAXBElement<?> messagePayload,
			@NonNull final Class<? extends T> expectedResponseClass)
	{
		final String uri = config.getBaseUrl() + getUrlSuffix();
		final JAXBElement<?> responseElement = (JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(uri, messagePayload);

		final Object responseValue = responseElement.getValue();
		if (expectedResponseClass.isInstance(responseValue))
		{
			return (T)responseValue;
		}
		else if (Msv3FaultInfo.class.isInstance(responseValue))
		{
			throw Msv3ClientException.builder()
					.msv3FaultInfo((Msv3FaultInfo)responseValue).build()
					.setParameter("uri", uri)
					.setParameter("config", config);
		}
		throw new AdempiereException("Webservice returned unexpected response")
				.appendParametersToMessage()
				.setParameter("uri", uri)
				.setParameter("config", config)
				.setParameter("response", responseValue);
	}
}
