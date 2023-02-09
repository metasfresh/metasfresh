package de.metas.util.web;

import org.compiere.Adempiere;

import de.metas.util.web.security.UserAuthTokenFilter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public class SwaggerUtil
{
	/**
	 * If you create swagger documentation for an API that is controlled by {@link UserAuthTokenFilter},
	 * then you can add this parameter to the {@link Docket}'s globalOperationParameters
	 */
	public static final Parameter SWAGGER_GLOBAL_AUTH_TOKEN_PARAMETER = new ParameterBuilder()
			.name(UserAuthTokenFilter.HEADER_Authorization)
			.description("Authorization token")
			.modelRef(new ModelRef("string"))
			.parameterType("header")
			.required(false) // not required because we have some endpoints which are excluded (like the /auth one)
			.build();

	public ApiInfo createApiInfo(
			@NonNull final String title,
			@NonNull final String description)
	{
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.version(Adempiere.getBuildAndDateVersion())
				.license("GNU General Public License, version 2")
				.licenseUrl("http://www.gnu.org/licenses/gpl-2.0.html")
				.build();
	}
}
