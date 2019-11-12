package de.metas.rest_api.common;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@ApiModel(description = "Error informations")
@Value
public class JsonErrorItem
{
	String message;

	@JsonInclude(Include.NON_EMPTY)
	String detail;

	String stackTrace;

	Map<String, String> parameters;

	/**
	 * Local exception.
	 * It won't be serialized. It's just used for local troubleshooting.
	 */
	@JsonIgnore
	transient Throwable throwable;

	@JsonCreator
	@Builder
	private JsonErrorItem(
			@JsonProperty("message") @NonNull final String message,
			@JsonProperty("detail") @Nullable final String detail,
			@JsonProperty("stackTrace") @Nullable final String stackTrace,
			@JsonProperty("parameters") @Nullable final Map<String, String> parameters,
			@JsonProperty("throwable") @Nullable final Throwable throwable)
	{
		this.message = message;
		this.detail = detail;
		this.stackTrace = stackTrace;
		this.parameters = coalesce(parameters, ImmutableMap.of());
		this.throwable = throwable;
	}
}
