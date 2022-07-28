/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.common.auth;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class TokenCredentials
{
	@NonNull
	JsonMetasfreshId pInstance;
	@Nullable
	String externalSystemValue;
	@Nullable
	String auditTrailEndpoint;
	@NonNull
	String orgCode;

	@Builder
	TokenCredentials(
			@NonNull final JsonMetasfreshId pInstance,
			@Nullable final String externalSystemValue,
			@Nullable final String auditTrailEndpoint,
			@NonNull final String orgCode)
	{
		this.pInstance = pInstance;
		this.externalSystemValue = externalSystemValue;
		this.auditTrailEndpoint = auditTrailEndpoint;
		this.orgCode = orgCode;
	}
}
