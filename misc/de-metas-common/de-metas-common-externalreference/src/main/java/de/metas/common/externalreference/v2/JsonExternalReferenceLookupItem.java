/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Used in both requests and responses.
 */
@Value
public class JsonExternalReferenceLookupItem
{
	@Schema(description = "JsonMetasfreshId of the referenced resource. E.g. a `C_BPartner_ID`. Either this or `externalReference` are required")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@Schema(required = true, description = "Type of the externally referenced resource. E.g. user, issue, timebooking")
	String type;

	@Schema(description = "External identifier of the referenced resource. Either this or `metasfreshId` are required")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@JsonCreator
	@Builder
	private JsonExternalReferenceLookupItem(
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("type") @NonNull final String type,
			@JsonProperty("externalReference") @Nullable final String externalReference)
	{
		if (metasfreshId == null && externalReference == null)
		{
			throw new RuntimeException("metasfreshId && externalReference cannot be both null!");
		}

		this.externalReference = externalReference;
		this.type = type;
		this.metasfreshId = metasfreshId;
	}
}
