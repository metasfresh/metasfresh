/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.rest_api;

import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableMap;

import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@ApiModel(description = "Error informations")
@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonErrorItem.JsonErrorItemBuilder.class)
public class JsonErrorItem
{
	String message;

	@JsonInclude(Include.NON_EMPTY)
	String detail;

	@JsonInclude(Include.NON_EMPTY)
	String stackTrace;

	Map<String, String> parameters;

	@JsonInclude(Include.NON_EMPTY)
	JsonMetasfreshId adIssueId;

	/**
	 * Local exception.
	 * It won't be serialized. It's just used for local troubleshooting.
	 */
	@JsonIgnore
	transient Throwable throwable;

	@JsonCreator
	@Builder
	private JsonErrorItem(
			@JsonProperty("message") @Nullable final String message,
			@JsonProperty("detail") @Nullable final String detail,
			@JsonProperty("stackTrace") @Nullable final String stackTrace,
			@JsonProperty("parameters") @Nullable @Singular final Map<String, String> parameters,
			@JsonProperty("adIssueId") @Nullable final JsonMetasfreshId adIssueId,
			@Nullable final Throwable throwable)
	{
		this.message = message;
		this.detail = detail;
		this.stackTrace = stackTrace;
		this.parameters = CoalesceUtil.coalesce(parameters, ImmutableMap.of());
		this.adIssueId = adIssueId;
		this.throwable = throwable;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonErrorItemBuilder
	{
	}
}
