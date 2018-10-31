package de.metas.vertical.pharma.vendor.gateway.msv3;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.google.common.annotations.VisibleForTesting;

import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
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

public final class MSV3Client
{
	private final WebServiceTemplate webServiceTemplate;
	private final MSV3ClientConfig config;
	private final String urlPrefix;
	private final FaultInfoExtractor faultInfoExtractor;

	@Builder
	private MSV3Client(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final String urlPrefix,
			@NonNull final FaultInfoExtractor faultInfoExtractor)
	{
		this.config = config;
		this.urlPrefix = urlPrefix;
		this.faultInfoExtractor = faultInfoExtractor;

		webServiceTemplate = connectionFactory.createWebServiceTemplate(config);
	}

	public ClientSoftwareId getClientSoftwareId()
	{
		return config.getClientSoftwareId();
	}

	/**
	 * @param expectedResponseClass if the response is not an instance of this class, the method throws an exception.
	 */
	public <T> T sendAndReceive(
			@NonNull final JAXBElement<?> messagePayload,
			@NonNull final Class<? extends T> expectedResponseClass)
	{
		final String uri = config.getBaseUrl() + urlPrefix;

		final JAXBElement<?> responseElement = (JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(uri, messagePayload);

		final Object responseValue = responseElement.getValue();
		if (expectedResponseClass.isInstance(responseValue))
		{
			return expectedResponseClass.cast(responseValue);
		}

		final FaultInfo faultInfo = faultInfoExtractor.extractFaultInfoOrNull(responseValue);
		if (faultInfo != null)
		{
			throw Msv3ClientException.builder()
					.msv3FaultInfo(faultInfo)
					.build()
					.setParameter("uri", uri)
					.setParameter("config", config);
		}
		else
		{
			throw new AdempiereException("Webservice returned unexpected response")
					.appendParametersToMessage()
					.setParameter("uri", uri)
					.setParameter("config", config)
					.setParameter("response", responseValue);
		}
	}

	@VisibleForTesting
	public WebServiceTemplate getWebServiceTemplate()
	{
		return webServiceTemplate;
	}

	@FunctionalInterface
	public static interface FaultInfoExtractor
	{
		FaultInfo extractFaultInfoOrNull(Object value);
	}
}
