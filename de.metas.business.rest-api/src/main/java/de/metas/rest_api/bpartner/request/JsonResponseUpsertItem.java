package de.metas.rest_api.bpartner.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class JsonResponseUpsertItem
{
	@ApiModelProperty(//
			value = "The external id from the respective update request",//
			dataType = "java.lang.String")
	@NonNull
	JsonExternalId externalId;

	@ApiModelProperty(value = "The metasfresh-ID of the upserted record",//
			dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	@JsonCreator
	private JsonResponseUpsertItem(
			@JsonProperty("externalId") @NonNull JsonExternalId externalId,
			@JsonProperty("metasfreshId") @NonNull MetasfreshId metasfreshId)
	{
		this.externalId = externalId;
		this.metasfreshId = metasfreshId;
	}
}
