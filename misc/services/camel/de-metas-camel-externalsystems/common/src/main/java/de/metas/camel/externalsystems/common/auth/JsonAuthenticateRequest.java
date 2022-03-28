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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonAuthenticateRequest
{
	@NonNull
	String grantedAuthority;
	@NonNull
	String authKey;
	@NonNull
	JsonMetasfreshId pInstance;
	@NonNull
	String orgCode;
	@Nullable
	String externalSystemValue;
	@Nullable
	String auditTrailEndpoint;

	@Builder
	@JsonCreator
	JsonAuthenticateRequest(
			@JsonProperty("grantedAuthority") @NonNull final String grantedAuthority,
			@JsonProperty("authKey") @NonNull final String authKey,
			@JsonProperty("pinstance") @NonNull final JsonMetasfreshId pInstance,
			@JsonProperty("externalSystemValue") @Nullable final String externalSystemValue,
			@JsonProperty("auditTrailEndpoint") @Nullable final String auditTrailEndpoint,
			@JsonProperty("orgCode") @NonNull final String orgCode)
	{
		this.grantedAuthority = grantedAuthority;
		this.authKey = authKey;
		this.pInstance = pInstance;
		this.externalSystemValue = externalSystemValue;
		this.auditTrailEndpoint = auditTrailEndpoint;
		this.orgCode = orgCode;
	}
}
