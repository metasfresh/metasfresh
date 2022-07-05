/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.core.authorization.provider;

import de.metas.common.util.Check;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_API_AUTHORIZATION_TOKEN_PROPERTY;

@Component
public class MetasfreshAuthProvider
{
	@Value("${" + MF_API_AUTHORIZATION_TOKEN_PROPERTY + ":}")
	@Nullable
	private String defaultAuthToken;

	@Nullable
	@Setter
	private String authToken;

	@Nullable
	public String getAuthToken()
	{
		if (Check.isNotBlank(authToken))
		{
			return this.authToken;
		}

		return this.defaultAuthToken;
	}
}
