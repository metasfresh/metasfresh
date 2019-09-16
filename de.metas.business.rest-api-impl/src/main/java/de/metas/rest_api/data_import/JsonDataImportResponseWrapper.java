package de.metas.rest_api.data_import;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.metas.rest_api.utils.JsonError;
import de.metas.rest_api.utils.JsonErrors;
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
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	JsonError error;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	JsonDataImportResponse response;

	public static JsonDataImportResponseWrapper ok(final JsonDataImportResponse response)
	{
		final JsonError error = null;
		return new JsonDataImportResponseWrapper(error, response);
	}

	public static JsonDataImportResponseWrapper error(final Throwable throwable, final String adLanguage)
	{
		final JsonError error = JsonErrors.ofThrowable(throwable, adLanguage);
		final JsonDataImportResponse response = null;
		return new JsonDataImportResponseWrapper(error, response);
	}
}
