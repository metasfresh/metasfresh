package de.metas.ui.web.config;

import de.metas.util.web.SwaggerUtil;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

// NOTE: no @EnableWebMvc here. springdoc relies on Spring Boot's MVC auto-configuration (incl. the
// swagger-ui resource handling); @EnableWebMvc would disable it. The app's MVC customizations live in
// WebConfig (a WebMvcConfigurer), which works with Boot's auto-config.
@Configuration
public class SwaggerConfig
{
	@Bean
	public OpenAPI api()
	{
		return SwaggerUtil.createOpenAPI(
				"metasfresh webui REST API" /* title */,
				"REST API backend for metasfresh UIs"/* description */);
	}
}
