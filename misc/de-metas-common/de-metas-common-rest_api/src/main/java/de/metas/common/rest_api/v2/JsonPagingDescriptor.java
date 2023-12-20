/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Schema(description = "Included in various responses that are so big that they require pagination")
@Value
@Builder
public class JsonPagingDescriptor
{
	@Schema(description = "Epoch timestamp in ms. Can be stored by the API client and used as `since` value in a later invocation.")
	long resultTimestamp;

	@Schema(description = "Total number of results. If bigger than `pageSize`, the API client can use the given `nextPage` to retrieve the next page.")
	int totalSize;

	@Schema(description = "Number of results in the current page.")
	int pageSize;

	@Schema(description = "If provided, then the overal result has further pages and the next page can be retrieved using the given value")
	@JsonInclude(Include.NON_EMPTY)
	String nextPage;
}
