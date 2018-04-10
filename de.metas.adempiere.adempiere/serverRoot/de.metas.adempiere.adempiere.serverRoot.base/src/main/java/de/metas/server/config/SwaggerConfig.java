package de.metas.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;

import de.metas.util.web.security.UserAuthTokenFilter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(ImmutableList.of(new ParameterBuilder()
						.name(UserAuthTokenFilter.HEADER_Authorization)
						.description("Authorization token")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()))
				.select()
				.paths(PathSelectors.any())
				.build();
	}
}
