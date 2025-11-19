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

import de.metas.common.externalsystem.endpoint.JsonEndpointAuthType;
import de.metas.externalsystem.model.X_ExternalSystem_Outbound_Endpoint;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Make sure to keep in sync with {@link de.metas.common.externalsystem.endpoint.JsonEndpointAuthType}.
 */
@RequiredArgsConstructor
@Getter
public enum OutboundEndpointAuthType implements ReferenceListAwareEnum
{
	Token(X_ExternalSystem_Outbound_Endpoint.AUTHTYPE_Token),
	OAuth(X_ExternalSystem_Outbound_Endpoint.AUTHTYPE_OAuth),
	SAS(X_ExternalSystem_Outbound_Endpoint.AUTHTYPE_SAS)
	;

	private static final ReferenceListAwareEnums.ValuesIndex<OutboundEndpointAuthType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static OutboundEndpointAuthType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public JsonEndpointAuthType toJson()
	{
		return JsonEndpointAuthType.valueOf(code);
	}
}
