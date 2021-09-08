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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize(builder = MultiJsonFilter.MultiJsonFilterBuilder.class)
@JsonPropertyOrder({ "type", "operator", "queries" })
public class MultiJsonFilter
{
	@NonNull
	@JsonProperty("type")
	MultiJsonFilter.MultiFilterType filterType = MultiFilterType.MULTI;

	@NonNull
	@JsonProperty("operator")
	OperatorType operatorType;

	@NonNull
	@JsonProperty("queries")
	List<JsonQuery> jsonQueryList;

	@Builder
	public MultiJsonFilter(
			@NonNull @JsonProperty("operator") final OperatorType operatorType,
			@NonNull @JsonProperty("queries") @Singular final List<JsonQuery> jsonQueries
	)
	{
		this.operatorType = operatorType;
		this.jsonQueryList = jsonQueries;
	}

	@AllArgsConstructor
	@Getter
	public enum MultiFilterType
	{
		MULTI("multi");

		@JsonValue
		private final String value;
	}

	@AllArgsConstructor
	@Getter
	public enum OperatorType
	{
		OR("or"),
		AND("and");

		@JsonValue
		private final String value;
	}
}
