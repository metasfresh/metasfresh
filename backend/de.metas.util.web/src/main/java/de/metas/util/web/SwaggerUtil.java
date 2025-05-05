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

package de.metas.util.web;

import de.metas.util.web.security.UserAuthTokenFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.Adempiere;

@UtilityClass
public class SwaggerUtil
{
	public static final Parameter SWAGGER_GLOBAL_AUTH_TOKEN_PARAMETER = new Parameter()
			.name(UserAuthTokenFilter.HEADER_Authorization)
			.description("Authorization token")
			.in("header")
			.allowEmptyValue(true)
			.required(false);

	public OpenAPI createApiInfo(
			@NonNull final String title,
			@NonNull final String description)
	{
		return new OpenAPI().info(new Info()
						  .title(title)
						  .description(description)
						  .version(Adempiere.getBuildAndDateVersion())
						  .license(new License().name("GNU General Public License, version 2")
										   .url("http://www.gnu.org/licenses/gpl-2.0.html")));
	}
}
