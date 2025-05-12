/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
public class APIRequest
{
	@NonNull String endpointPath;
	@NonNull String method;
	@Nullable String payload;
	@NonNull String authToken;
	@Nullable Map<String, String> additionalHeaders;

	@Nullable @Builder.Default Integer expectedStatusCode = 200;
	@Nullable String expectedErrorMessageContaining;
	@Nullable Boolean expectErrorUserFriendly;

	public static class APIRequestBuilder
	{
		public APIRequestBuilder payload(@Nullable final Object payloadObj)
		{
			this.payload = convertPayloadToString(payloadObj);
			return this;
		}

		@Nullable
		private static String convertPayloadToString(@Nullable final Object requestPayload)
		{
			if (requestPayload == null)
			{
				return null;
			}
			else if (requestPayload instanceof String)
			{
				return (String)requestPayload;
			}
			else
			{
				try
				{
					final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
					return jsonObjectMapper.writeValueAsString(requestPayload);
				}
				catch (JsonProcessingException e)
				{
					throw new AdempiereException("Failed converting to json string: " + requestPayload, e);
				}
			}
		}
	}
}
