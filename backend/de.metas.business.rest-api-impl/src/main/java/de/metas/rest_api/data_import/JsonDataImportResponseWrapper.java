package de.metas.rest_api.data_import;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.JsonErrorItem;
import de.metas.rest_api.utils.JsonErrors;
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

/** Wraps the actual response and the error */
@Value
public class JsonDataImportResponseWrapper
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	List<JsonErrorItem> errors;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	JsonDataImportResponse response;

	public static JsonDataImportResponseWrapper ok(final JsonDataImportResponse response)
	{
		final List<JsonErrorItem> errors = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(@NonNull final List<JsonErrorItem> errors)
	{
		final JsonDataImportResponse response = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(@NonNull final JsonDataImportResponse response)
	{
		final List<JsonErrorItem> errors = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}

	public static JsonDataImportResponseWrapper error(final Throwable throwable, final String adLanguage)
	{
		final List<JsonErrorItem> errors = ImmutableList.of(JsonErrors.ofThrowable(throwable, adLanguage));
		final JsonDataImportResponse response = null;
		return new JsonDataImportResponseWrapper(errors, response);
	}
}
