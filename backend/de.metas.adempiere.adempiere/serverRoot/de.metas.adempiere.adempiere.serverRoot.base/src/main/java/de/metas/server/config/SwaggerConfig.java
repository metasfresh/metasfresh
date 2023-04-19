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

package de.metas.server.config;

import de.metas.util.web.SwaggerUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static de.metas.util.web.SwaggerUtil.SWAGGER_GLOBAL_AUTH_TOKEN_PARAMETER;

@Configuration
@EnableWebMvc
public class SwaggerConfig
{
	@Bean
	public OperationCustomizer customize() {
		return (operation, handlerMethod) -> operation.addParametersItem(SWAGGER_GLOBAL_AUTH_TOKEN_PARAMETER);
	}

	@Bean
	public OpenAPI appOpenAPI() {
		return SwaggerUtil.createApiInfo("metasfresh application server REST API",
										 "metasfresh REST API ( to switch to webui REST API on instances change above to /v3/api-docs ) <br>" +
											"Currently can't be executed because OpenAPI doesn't allow custom Authorization-header with name Authorization.")
				.addServersItem(new Server().url("/app").description("default - used on instances to add /app after base-url"))
				.addServersItem(new Server().url("/").description("for local development"));
	}
}
