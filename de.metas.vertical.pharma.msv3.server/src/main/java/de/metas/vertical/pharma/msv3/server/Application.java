package de.metas.vertical.pharma.msv3.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

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
	// @Autowired
	// private ApplicationContext applicationContext;

	public static void main(final String[] args)
	{
		new SpringApplicationBuilder(Application.class)
				.headless(true)
				.web(true)
				.run(args);
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
	@Bean(name = "Msv3VerfuegbarkeitAnfragenService")
	public Wsdl11Definition defaultWsdl11Definition()
	{
		final SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/de/metas/vertical/pharma/vendor/gateway/msv3/schema/Msv3VerfuegbarkeitAnfragenService.wsdl"));
		return wsdl11Definition;
	}
}
