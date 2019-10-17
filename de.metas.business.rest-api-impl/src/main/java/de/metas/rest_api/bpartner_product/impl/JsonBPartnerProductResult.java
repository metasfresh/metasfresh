package de.metas.rest_api.bpartner_product.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonBPartnerProductResult
{
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonBPartnerProduct result;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String error;

	public static ResponseEntity<JsonBPartnerProductResult> ok(@NonNull final JsonBPartnerProduct result)
	{
		final String error = null;
		return ResponseEntity.ok(new JsonBPartnerProductResult(result, error));
	}

	public static ResponseEntity<JsonBPartnerProductResult> notFound(@NonNull final String errorMessage)
	{
		final JsonBPartnerProduct result = null;
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new JsonBPartnerProductResult(result, errorMessage));
	}

	private JsonBPartnerProductResult(
			final JsonBPartnerProduct result,
			final String error)
	{
		this.result = result;
		this.error = error;
	}
}
