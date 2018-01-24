package de.metas.vertical.pharma.vendor.gateway.mvs3;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.adempiere.exceptions.AdempiereException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

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

@Service
public class MSV3ConnectionFactory
{
	public WebServiceTemplate createWebServiceTemplate(@NonNull final MSV3ClientConfig config)
	{
		final HttpComponentsMessageSender messageSender = //
				createMessageSender(config.getAuthUsername(), config.getAuthPassword());

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(de.metas.vendor.gateway.msv3.schema.ObjectFactory.class.getPackage().getName());

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		try
		{
			// Using the default messageFactory, we end up with SOAP 1.1. and the following error on the server side
			// Content-Type: text/xml; charset=utf-8 Supported ones are: [application/soap+xml]
			// ..which results in a http error "Unsupported Media Type [415]" on the client side
			webServiceTemplate
					.setMessageFactory(
							new SaajSoapMessageFactory(
									MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL)));
		}
		catch (final SOAPException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return webServiceTemplate;
	}

	private static HttpComponentsMessageSender createMessageSender(
			@NonNull final String authUsername,
			@NonNull final String authPassword)
	{
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(authUsername, authPassword);

		final HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(credentials);
		try
		{
			messageSender.afterPropertiesSet(); // to make sure credentials are set to HttpClient
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		return messageSender;
	}

}
