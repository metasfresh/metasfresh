/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;

@Value
public class ApiResponse
{
	int statusCode;

	@Nullable
	HttpHeaders httpHeaders;

	@Nullable
	Object body;

	@NonNull
	public static ApiResponse of(final int statusCode, @Nullable final HttpHeaders httpHeaders, @Nullable final String bodyCandidate)
	{
		final Object body;
		if (bodyCandidate != null
				&& httpHeaders != null
				&& httpHeaders.getContentType() != null
				&& httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON))
		{
			try
			{
				body = JsonObjectMapperHolder.sharedJsonObjectMapper()
						.readValue(bodyCandidate, Object.class);
			}
			catch (final JsonProcessingException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
		else
		{
			body = bodyCandidate;
		}

		return new ApiResponse(statusCode, httpHeaders, body);
	}

	public boolean hasStatus2xx()
	{
		return getStatusCode() / 100 == HttpStatus.OK.series().value();
	}
}
