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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@JsonDeserialize(builder = JsonQuery.JsonQueryBuilder.class)
@JsonPropertyOrder({ "field", "type", "parameters", "value" })
public class JsonQuery
{
	@NonNull
	@JsonProperty("field")
	String field;

	@NonNull
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	QueryType queryType;

	@ApiModelProperty("Depending on the query-type, you can have either a `value` or `parameters`.")
	@Nullable
	@JsonProperty("parameters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Map<String, String> parameters;

	@ApiModelProperty("Depending on the query-type, you can have either a `value` or `parameters`.")
	@Nullable
	@JsonProperty("value")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String value;

	@AllArgsConstructor
	@Getter
	public enum QueryType
	{
		RANGE("range"),
		EQUALS("equals");

		@JsonValue
		private final String value;
	}
}
