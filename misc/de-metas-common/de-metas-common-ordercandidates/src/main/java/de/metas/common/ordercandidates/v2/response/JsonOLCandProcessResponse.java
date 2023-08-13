/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonOLCandProcessResponse.JsonOLCandProcessResponseBuilder.class)
public class JsonOLCandProcessResponse
{
	@JsonProperty("jsonOLCandClearingResponse")
	JsonOLCandClearingResponse jsonOLCandClearingResponse;

	@JsonProperty("jsonGenerateOrdersResponse")
	JsonGenerateOrdersResponse jsonGenerateOrdersResponse;

	@Builder
	JsonOLCandProcessResponse(
			@JsonProperty("jsonOLCandClearingResponse") @NonNull final JsonOLCandClearingResponse jsonOLCandClearingResponse,
			@JsonProperty("jsonGenerateOrdersResponse") @Nullable final JsonGenerateOrdersResponse jsonGenerateOrdersResponse)
	{
		this.jsonOLCandClearingResponse = jsonOLCandClearingResponse;
		this.jsonGenerateOrdersResponse = jsonGenerateOrdersResponse;
	}
}
