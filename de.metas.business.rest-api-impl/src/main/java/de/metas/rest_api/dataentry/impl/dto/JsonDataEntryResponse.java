package de.metas.rest_api.dataentry.impl.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class JsonDataEntryResponse
{
	public static ResponseEntity<JsonDataEntryResponse> ok(@NonNull final JsonDataEntry result)
	{
		final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
				.result(result)
				.status(HttpStatus.OK.value())
				.build();
		return new ResponseEntity<>(dataEntryResponse, HttpStatus.OK);
	}

	public static ResponseEntity<JsonDataEntryResponse> notFound(@NonNull final String errorMsg)
	{
		final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
				.error(errorMsg)
				.status(HttpStatus.NOT_FOUND.value())
				.build();
		return new ResponseEntity<>(dataEntryResponse, HttpStatus.NOT_FOUND);
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("result")
	JsonDataEntry result;

	@JsonProperty("status")
	int status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("error")
	String error;
}
