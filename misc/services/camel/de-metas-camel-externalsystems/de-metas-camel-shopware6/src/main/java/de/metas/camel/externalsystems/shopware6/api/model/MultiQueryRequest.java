/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonDeserialize(builder = MultiQueryRequest.MultiQueryRequestBuilder.class)
public class MultiQueryRequest implements Shopware6QueryRequest
{
	@NonNull
	@JsonProperty("filters")
	List<MultiJsonFilter> filterList;

	@JsonIgnore
	Boolean isQueryByDate;

	@Builder
	public MultiQueryRequest(
			@NonNull @Singular @JsonProperty("filters") final List<MultiJsonFilter> filters,
			@Nullable final Boolean isQueryByDate)
	{
		this.filterList = filters;
		this.isQueryByDate = isQueryByDate;
	}
}
