package de.metas.rest_api.bpartner.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.common.MetasfreshId;
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
			value = "The bpartnerIdentifier or contactIndentifier from the respective update request.",
			position = 10)
	@NonNull
	String identifier;

	@ApiModelProperty(value = "The metasfresh-ID of the upserted record",//
			position = 20,
			dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	@JsonCreator
	private JsonResponseUpsertItem(
			@JsonProperty("identifier") @NonNull String identifier,
			@JsonProperty("metasfreshId") @NonNull MetasfreshId metasfreshId)
	{
		this.identifier = identifier;
		this.metasfreshId = metasfreshId;
	}
}
