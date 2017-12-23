package de.metas.shipper.gateway.go;

import org.adempiere.util.Check;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import de.metas.shipper.gateway.api.service.CountryCodeFactory;
import de.metas.shipper.gateway.api.service.DefaultCountryCodeFactory;
import de.metas.shipper.gateway.go.schema.ObjectFactory;

/*
 * #%L
 * de.metas.shipper.go
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

@Configuration
public class GOConfiguration
{
	private static final Logger logger = LoggerFactory.getLogger(GOConfiguration.class);

	@Value("${de.metas.shipper.go.url}")
	private String url;
	@Value("${de.metas.shipper.go.auth.username}")
	private String authUsername;
	@Value("${de.metas.shipper.go.auth.password}")
	private String authPassword;
	@Value("${de.metas.shipper.go.request.username}")
	private String requestUsername;
	@Value("${de.metas.shipper.go.request.senderId}")
	private String requestSenderId;

	@Bean
	public GOClient goClient(final Jaxb2Marshaller marshaller)
	{
		Check.assumeNotEmpty(url, "url is not empty");

		final GOClient client = GOClient.builder()
				.url(url)
				.messageSender(goClientMessageSender())
				.marshaller(marshaller)
				.requestUsername(requestUsername)
				.requestSenderId(requestSenderId)
				.build();
		logger.info("GO Client initialized: {}", client);
		return client;
	}

	@Bean
	public Jaxb2Marshaller goClientMarshaller()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(ObjectFactory.class.getPackage().getName());
		return marshaller;
	}

	@Bean
	public HttpComponentsMessageSender goClientMessageSender()
	{
		final HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(goUsernamePasswordCredentials());
		return messageSender;
	}

	@Bean
	public UsernamePasswordCredentials goUsernamePasswordCredentials()
	{
		Check.assumeNotEmpty(authUsername, "username is not empty");
		Check.assumeNotEmpty(authPassword, "password is not empty");

		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(authUsername, authPassword);
		logger.info("Using GO credentials: {}", credentials);

		return credentials;
	}

	@Bean
	public CountryCodeFactory countryCodeFactory()
	{
		return new DefaultCountryCodeFactory();
	}
}
