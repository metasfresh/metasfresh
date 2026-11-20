package de.metas.util.web;

import de.metas.util.web.security.UserAuthTokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.Adempiere;

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
	 * Name of the (optional) auth-token security scheme that mirrors the {@link UserAuthTokenFilter} Authorization
	 * header. Used by APIs controlled by {@link UserAuthTokenFilter}; swagger-ui renders it as an "Authorize" box.
	 */
	public static final String AUTH_TOKEN_SECURITY_SCHEME = "metasfresh-auth-token";

	public Info createApiInfo(
			@NonNull final String title,
			@NonNull final String description)
	{
		return new Info()
				.title(title)
				.description(description)
				.version(Adempiere.getBuildAndDateVersion())
				.license(new License()
						.name("GNU General Public License, version 2")
						.url("http://www.gnu.org/licenses/gpl-2.0.html"));
	}

	public OpenAPI createOpenAPI(
			@NonNull final String title,
			@NonNull final String description)
	{
		return new OpenAPI().info(createApiInfo(title, description));
	}

	/**
	 * Like {@link #createOpenAPI(String, String)} but additionally declares the {@link UserAuthTokenFilter}
	 * Authorization header as an apiKey security scheme, so the token can be entered in swagger-ui.
	 */
	public OpenAPI createOpenAPIWithAuthToken(
			@NonNull final String title,
			@NonNull final String description)
	{
		final SecurityScheme authTokenScheme = new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.HEADER)
				.name(UserAuthTokenFilter.HEADER_Authorization)
				.description("Authorization token");

		return createOpenAPI(title, description)
				.components(new Components().addSecuritySchemes(AUTH_TOKEN_SECURITY_SCHEME, authTokenScheme))
				.addSecurityItem(new SecurityRequirement().addList(AUTH_TOKEN_SECURITY_SCHEME));
	}
}
