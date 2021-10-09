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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize(builder = JsonGenerateOrdersResponse.JsonGenerateOrdersResponseBuilder.class)
public class JsonGenerateOrdersResponse
{
	List<JsonMetasfreshId> orderIds;

	@Builder
	JsonGenerateOrdersResponse(@JsonProperty("orderIds") @NonNull final List<JsonMetasfreshId> orderIds)
	{
		this.orderIds = orderIds;
	}
}
