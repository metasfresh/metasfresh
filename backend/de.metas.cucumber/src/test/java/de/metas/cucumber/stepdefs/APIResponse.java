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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
@Builder(toBuilder = true)
public class APIResponse
{
	@Nullable Integer statusCode;
	@Nullable String content;
	@Nullable String contentType;
	@Nullable ApiRequestAuditId requestId;

	public <T> T getContentAs(@NonNull final Class<T> type) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		return mapper.readValue(content, type);
	}

	public int getByJsonPathAsInt(@NonNull final String jsonPath) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final JsonNode rootNode = mapper.readTree(content);
		final JsonNode node = rootNode.at(jsonPath);
		return node.asInt();
	}

	public APIResponse withContent(@NonNull final UnaryOperator<String> mapper)
	{
		final String contentChanged = mapper.apply(content);
		return !Objects.equals(this.content, contentChanged)
				? toBuilder().content(contentChanged).build()
				: this;
	}
}