package de.metas.vertical.pharma.msv3.server;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@EnableWs
@Configuration
@Import({ WebServiceConfigV1.class
		// , WebServiceConfigV2.class
})
public class WebServiceConfig
{
	@Bean(MessageDispatcherServlet.DEFAULT_MESSAGE_FACTORY_BEAN_NAME)
	public SoapMessageFactory soapMessageFactory()
	{
		final SaajSoapMessageFactory soapMessageFactory = new SaajSoapMessageFactory();
		soapMessageFactory.setSoapVersion(SoapVersion.SOAP_12);
		return soapMessageFactory;
	}

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(final ApplicationContext applicationContext)
	{
		final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);

		return new ServletRegistrationBean(servlet,
				MSV3ServerConstantsV1.WEBSERVICE_ENDPOINT_PATH + "/*"
		// , MSV3ServerConstantsV2.WEBSERVICE_ENDPOINT_PATH + "/*"
		);
	}
}
