/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.outboundendpoint;

import de.metas.common.externalsystem.endpoint.JsonExternalSystemOutboundEndpoint;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Builder
@Value
public class ExternalSystemOutboundEndpoint
{
	@NonNull ExternalSystemOutboundEndpointId id;

	@NonNull String value;

	@NonNull String endpointUrl;

	@NonNull String method;

	@NonNull OutboundEndpointAuthType authType;

	@Nullable String clientId;

	@Nullable String clientSecret;

	@Nullable String token;

	@Nullable String user;

	@Nullable String password;
	
	@Nullable String sasSignature;

	@NonNull
	public JsonExternalSystemOutboundEndpoint toJson()
	{
		return JsonExternalSystemOutboundEndpoint.builder()
				.value(value)
				.endpointUrl(endpointUrl)
				.method(method)
				.authType(authType.toJson())
				.clientId(clientId)
				.clientSecret(clientSecret)
				.token(token)
				.user(user)
				.password(password)
				.sasSignature(sasSignature)
				.build();
	}
}
