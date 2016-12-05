package de.metas.ui.web.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * #%L
 * metasfresh-webui-vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.apis(basePackages(WebRestApiApplication.class))
				.paths(PathSelectors.any())
				.build();
	}

	@SuppressWarnings("unused")
	private static final Predicate<RequestHandler> basePackages(final Class<?>... classes)
	{
		final Set<Predicate<RequestHandler>> predicates = new HashSet<>(classes.length);
		for (final Class<?> clazz : classes)
		{
			final String packageName = clazz.getPackage().getName();
			predicates.add(RequestHandlerSelectors.basePackage(packageName));
		}
		
		if(predicates.size() == 1)
		{
			return predicates.iterator().next();
		}

		return Predicates.or(predicates);
	}
}