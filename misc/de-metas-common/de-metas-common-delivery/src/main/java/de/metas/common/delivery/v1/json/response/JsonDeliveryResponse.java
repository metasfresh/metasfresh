/*
 * #%L
 * de-metas-common-delivery
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

package de.metas.common.delivery.v1.json.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
@Jacksonized
public class JsonDeliveryResponse
{
	@NonNull String requestId;

	@Nullable String errorMessage;

	@NonNull List<JsonDeliveryResponseItem> items;

	@JsonIgnore
	public boolean isError()
	{
		return items.stream().anyMatch(item -> item.getErrorMessage() != null && !item.getErrorMessage().isEmpty());
	}
}
