package de.metas.vertical.pharma.msv3.server;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import de.metas.vertical.pharma.msv3.server.order.OrderStatusWebServiceV1;
import de.metas.vertical.pharma.msv3.server.order.OrderWebServiceV1;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityWebServiceV1;
import de.metas.vertical.pharma.msv3.server.testconnection.TestConnectionWebServiceV1;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory;
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

public class WebServiceConfigV1
{
	private static final String SCHEMA_RESOURCE_PREFIX = "/de/metas/vertical/pharma/vendor/gateway/msv3/schema/v1";

	@Bean
	public ObjectFactory jaxbObjectFactoryV1()
	{
		return new ObjectFactory();
	}

	// e.g. http://localhost:8080/ws/Msv3VerbindungTestenService.wsdl
	@Bean(name = TestConnectionWebServiceV1.WSDL_BEAN_NAME)
	public Wsdl11Definition testConnectionWebServiceV1()
	{
		return createWsdl(TestConnectionWebServiceV1.WSDL_BEAN_NAME);
	}

	// e.g. http://localhost:8080/ws/Msv3VerfuegbarkeitAnfragenService.wsdl
	@Bean(name = StockAvailabilityWebServiceV1.WSDL_BEAN_NAME)
	public Wsdl11Definition stockAvailabilityWebServiceV1()
	{
		return createWsdl(StockAvailabilityWebServiceV1.WSDL_BEAN_NAME);
	}

	// e.g. http://localhost:8080/ws/Msv3BestellenService.wsdl
	@Bean(name = OrderWebServiceV1.WSDL_BEAN_NAME)
	public Wsdl11Definition orderWebServiceV1()
	{
		return createWsdl(OrderWebServiceV1.WSDL_BEAN_NAME);
	}

	// e.g. http://localhost:8080/ws/Msv3BestellstatusAbfragenService.wsdl
	@Bean(name = OrderStatusWebServiceV1.WSDL_BEAN_NAME)
	public Wsdl11Definition orderStatusWebServiceV1()
	{
		return createWsdl(OrderStatusWebServiceV1.WSDL_BEAN_NAME);
	}

	@Bean("Msv3Service_schema1")
	public XsdSchema msv3serviceSchemaXsdV1()
	{
		return createXsdSchema("Msv3Service_schema1.xsd");
	}

	@Bean("Msv3FachlicheFunktionen")
	public XsdSchema msv3FachlicheFunktionenV1()
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
