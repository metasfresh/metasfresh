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
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonExternalReferenceLookupItem
{
	@ApiModelProperty(value = "JsonMetasfreshId of the referenced resource")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty(required = true, value = "Type of the externally referenced resource. E.g. user, issue, timebooking")
	String type;

	@ApiModelProperty(value = "External identifier of the referenced resource")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String id;

	@JsonCreator
	@Builder
	private JsonExternalReferenceLookupItem(
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("type") @NonNull final String type,
			@JsonProperty("id") @Nullable final String id)
	{
		if (metasfreshId == null && id == null)
		{
			throw new RuntimeException("metasfreshId && externalReference cannot be both null!");
		}

		this.id = id;
		this.type = type;
		this.metasfreshId = metasfreshId;
	}
}
