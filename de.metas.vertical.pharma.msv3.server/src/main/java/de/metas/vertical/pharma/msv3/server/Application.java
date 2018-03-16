package de.metas.vertical.pharma.msv3.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
import lombok.NonNull;

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

@SpringBootApplication
@EnableWs
public class Application
{
	// private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private static final String SCHEMA_RESOURCE_PREFIX = "/de/metas/vertical/pharma/vendor/gateway/msv3/schema";

	public static void main(final String[] args)
	{
		new SpringApplicationBuilder(Application.class)
				.headless(true)
				.web(true)
				.run(args);
	}

	@Bean
	public ObjectFactory jaxbObjectFactory()
	{
		return new ObjectFactory();
	}

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
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	// http://localhost:8080/ws/Msv3VerfuegbarkeitAnfragenService.wsdl
	@Bean(name = StockAvailabilityWebService.WSDL_BEAN_NAME)
	public Wsdl11Definition msv3VerfuegbarkeitAnfragenService()
	{
		return createWsdl(StockAvailabilityWebService.WSDL_BEAN_NAME);
	}

	@Bean("Msv3Service_schema1")
	public XsdSchema msv3serviceSchemaXsd()
	{
		return createXsdSchema("Msv3Service_schema1.xsd");
	}

	@Bean("Msv3FachlicheFunktionen")
	public XsdSchema msv3FachlicheFunktionen()
	{
		return createXsdSchema("Msv3FachlicheFunktionen.xsd");
	}

	private static Wsdl11Definition createWsdl(@NonNull final String beanName)
	{
		return new SimpleWsdl11Definition(createSchemaResource(beanName + ".wsdl"));
	}

	private static XsdSchema createXsdSchema(@NonNull final String resourceName)
	{
		return new SimpleXsdSchema(createSchemaResource(resourceName));
	}

	private static ClassPathResource createSchemaResource(@NonNull final String resourceName)
	{
		return new ClassPathResource(SCHEMA_RESOURCE_PREFIX + "/" + resourceName);
	}
}
